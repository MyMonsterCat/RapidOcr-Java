package io.github.mymonstercat.ocr;

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
            nativeLoader = LoadUtil.findLibLoader(modelType);
            if (nativeLoader == null) {
                throw new LoadException("找不到合适的本机加载程序实现，可能的原因：1.运行库可能暂时未适配您的机型! " +
                        "2.使用的模型与引入的jar包不匹配，当前使用的模型为：" + modelType + "，请检查您引入的jar依赖是否正确! " +
                        "3.打包时未正确引入运行库，例如打包的是window依赖却在linux下运行!");
            }
            log.debug("当前库加载器: {}", nativeLoader.getClass().getSimpleName());
            nativeLoader.loadLibrary();
            isLibraryLoaded.set(true);
        }
        if (modelsLoader == null) {
            modelsLoader = LoadUtil.findModelsLoader(modelType);
            if (modelsLoader == null) {
                throw new LoadException("未能成功加载模型!");
            }
            log.debug("当前模型加载器: {}", modelsLoader.getClass().getSimpleName());
            modelsLoader.loadModels(model);
        }
    }
}
