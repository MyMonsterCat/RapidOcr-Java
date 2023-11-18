package io.github.monster.ocr;

import com.benjaminwan.ocrlibrary.OcrEngine;
import com.benjaminwan.ocrlibrary.OcrResult;
import io.github.monster.ocr.config.HardwareConfig;
import io.github.monster.ocr.config.ParamConfig;
import io.github.mymonstercat.*;
import io.github.mymonstercat.exception.LoadException;
import io.github.mymonstercat.loader.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * ONNX推理框架
 */
@Slf4j
public class InferenceEngine extends OcrEngine {

    private Model model = Model.ONNX_PPOCR_V3;
    private HardwareConfig hardwareConfig = HardwareConfig.getOnnxConfig();
    private static InferenceEngine inferenceEngine;
    private static LibraryLoader nativeLoader;
    private static ModelsLoader modelsLoader;
    private static final AtomicBoolean isLibraryLoaded = new AtomicBoolean(false);

    private InferenceEngine() {
    }

    @SneakyThrows
    private InferenceEngine(Model model, HardwareConfig hardwareConfig) {
        this.model = model;
        this.hardwareConfig = hardwareConfig;
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

    public OcrResult runOcr(String imagePath) {
        return runOcr(imagePath, ParamConfig.getDefaultConfig());
    }


    public OcrResult runOcr(String imagePath, ParamConfig config) {
        loadFileIfNeeded(model);
        initEngine(model, hardwareConfig);
        log.info("图片路径：{}， 参数配置：{}", imagePath, config);
        OcrResult result = detect(imagePath, config.getPadding(), config.getMaxSideLen(), config.getBoxScoreThresh(), config.getBoxThresh(), config.getUnClipRatio(), config.isDoAngle(), config.isMostAngle());
        log.info("识别结果为：{}，耗时{}ms", result.getStrRes().replace("\n", ""), result.getDetectTime());
        log.debug("文本块：{}，DbNet耗时{}ms", result.getTextBlocks(), result.getDbNetTime());
        return result;
    }

    @SneakyThrows
    private static synchronized void loadFileIfNeeded(Model model) {
        String modelType = model.getModelType();
        if (nativeLoader == null && (isLibraryLoaded.compareAndSet(false, true))) {
            if (Objects.equals(modelType, Model.ONNX_PPOCR_V3.getModelType())) {
                ServiceLoader<OnnxLibraryLoader> serviceLoader = ServiceLoader.load(OnnxLibraryLoader.class);
                nativeLoader = LoadUtil.findOnnxNativeLoader(serviceLoader);
            } else {
                ServiceLoader<NcnnLibraryLoader> serviceLoader = ServiceLoader.load(NcnnLibraryLoader.class);
                nativeLoader = LoadUtil.findNcnnNativeLoader(serviceLoader);
            }
            if (nativeLoader == null) {
                throw new LoadException("找不到合适的本机加载程序实现，运行库可能暂时未适配您的机型!");
            }
            log.debug("当前库加载器: {}", nativeLoader.getClass().getSimpleName());
            nativeLoader.loadLibrary();
            isLibraryLoaded.set(true);
        }
        if (modelsLoader == null) {
            if (Objects.equals(modelType, Model.ONNX_PPOCR_V3.getModelType())) {
                ServiceLoader<OnnxModelLoader> serviceLoader = ServiceLoader.load(OnnxModelLoader.class);
                modelsLoader = LoadUtil.findOnnxModelsLoader(serviceLoader);
            } else {
                ServiceLoader<NcnnModelLoader> serviceLoader = ServiceLoader.load(NcnnModelLoader.class);
                modelsLoader = LoadUtil.findNcnnModelsLoader(serviceLoader);
            }
            if (modelsLoader == null) {
                throw new LoadException("未能成功加载模型!");
            }
            log.debug("当前模型加载器: {}", modelsLoader.getClass().getSimpleName());
            modelsLoader.loadModels(model);
        }
    }
}
