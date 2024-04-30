package io.github.mymonstercat.ocr;

import java.util.concurrent.atomic.AtomicBoolean;

import com.benjaminwan.ocrlibrary.OcrEngine;
import com.benjaminwan.ocrlibrary.OcrResult;

import io.github.mymonstercat.Model;
import io.github.mymonstercat.exception.LoadException;
import io.github.mymonstercat.loader.LibraryLoader;
import io.github.mymonstercat.loader.ModelsLoader;
import io.github.mymonstercat.ocr.config.HardwareConfig;
import io.github.mymonstercat.ocr.config.ParamConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * Inference framework engine.
 */
@Slf4j
public class InferenceEngine extends OcrEngine {

    private Model model = Model.ONNX_PPOCR_V3;
    private HardwareConfig hardwareConfig = HardwareConfig.getOnnxConfig();
    private static InferenceEngine inferenceEngine;
    private static volatile LibraryLoader nativeLoader;
    private static volatile ModelsLoader modelsLoader;
    private static final AtomicBoolean isLibraryLoaded = new AtomicBoolean(false);

    private InferenceEngine() {
    }

    @SneakyThrows
    private InferenceEngine(Model model, HardwareConfig hardwareConfig) {
        this.model = model;
        this.hardwareConfig = hardwareConfig;
        loadFileIfNeeded(model);
        initEngine(model, hardwareConfig);
    }

    public static InferenceEngine getInstance(Model model) {
        return getInstance(model, HardwareConfig.getOnnxConfig());
    }

    public static InferenceEngine getInstance(Model model, HardwareConfig hardwareConfig) {
        if (inferenceEngine == null) {
            inferenceEngine = new InferenceEngine(model, hardwareConfig);
        }
        return inferenceEngine;
    }
    
    public Model getModel() {
        return model;
    }
    
    public HardwareConfig getHardwareConfig() {
        return hardwareConfig;
    }

    public OcrResult runOcr(String imagePath) {
        return runOcr(imagePath, ParamConfig.getDefaultConfig());
    }

    public OcrResult runOcr(String imagePath, ParamConfig config) {
        log.info("Image path: {}, Parameter configuration: {}", imagePath, config);
        OcrResult result = detect(imagePath, config.getPadding(), config.getMaxSideLen(), config.getBoxScoreThresh(), config.getBoxThresh(), config.getUnClipRatio(), config.isDoAngle(), config.isMostAngle());
        String property = System.getProperty("rapid.ocr.print.result");
        if("true".equals(property)) {
          log.info("Recognition result: {}, Time taken: {}ms", result.getStrRes().replace("\n", ""), result.getDetectTime());
        }
        log.debug("Text blocks: {}, DbNet Time taken: {}ms", result.getTextBlocks(), result.getDbNetTime());
        return result;
    }

    @SneakyThrows
    public static void loadFileIfNeeded(Model model) {
        String modelType = model.getModelType();
        if (InferenceEngine.nativeLoader == null && (isLibraryLoaded.compareAndSet(false, true))) {
            synchronized (InferenceEngine.class) {
                if (InferenceEngine.nativeLoader == null) {
                    LibraryLoader nativeLoader = LoadUtil.findLibLoader(modelType);
                    if (nativeLoader == null) {
                        throw new LoadException("Unable to find a suitable native loader implementation. Possible reasons include: " +
                                "1. The Maven coordinates for " + modelType + " are not included! " +
                                "2. The runtime library might not yet be adapted for your system: " + System.getProperty("os.name").toLowerCase() + System.getProperty("os.arch").toLowerCase() + "! " +
                                "3. The model used does not match the JAR dependency included, currently used model: " + modelType + ", please check your JAR dependencies are correct! " +
                                "4. Incorrect inclusion of the runtime library during packaging, such as packaging Windows dependencies but running on Linux, please refer to the documentation to check if the correct packaging command was used!");
                    }
                    nativeLoader.loadLibrary();
                    isLibraryLoaded.set(true);
                    InferenceEngine.nativeLoader = nativeLoader;
                }
            }
        }
        log.debug("Current library loader: {}", nativeLoader.getClass().getSimpleName());
        if (InferenceEngine.modelsLoader == null) {
            synchronized (InferenceEngine.class) {
                if (InferenceEngine.modelsLoader == null) {
                    ModelsLoader modelsLoader = LoadUtil.findModelsLoader(modelType);
                    if (modelsLoader == null) {
                        throw new LoadException("Failed to load models successfully!");
                    }
                    modelsLoader.loadModels(model);
                    InferenceEngine.modelsLoader = modelsLoader;
                }
            }
        }
        log.debug("Current model loader: {}", modelsLoader.getClass().getSimpleName());
    }
}