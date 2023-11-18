package io.github.monster.ocr;

import com.benjaminwan.ocrlibrary.OcrEngine;
import com.benjaminwan.ocrlibrary.OcrResult;
import io.github.monster.ocr.config.HardwareConfig;
import io.github.monster.ocr.config.ParamConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * ONNX推理框架
 */
@Slf4j
public class InferenceEngine extends OcrEngine{
    private static Model model = Model.ONNX_PPOCR_V3;
    private HardwareConfig hardwareConfig = HardwareConfig.getOnnxConfig();
    private static InferenceEngine inferenceEngine;


    private InferenceEngine() {
    }

    private InferenceEngine(Model model, HardwareConfig hardwareConfig) {
        this.model = model;
        this.hardwareConfig = hardwareConfig;
        InferenceUtil.copyFile(model.getModelType() + InferenceUtil.getLibraryName(),false, model);
    }

    public static InferenceEngine getInstance(Model model) {
        if (inferenceEngine == null) {
            inferenceEngine = new InferenceEngine(model, HardwareConfig.getOnnxConfig());
        }
        return inferenceEngine;
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
        initEngine(model, hardwareConfig);
        log.info("图片路径：{}， 参数配置：{}", imagePath, config);
        OcrResult result = detect(imagePath, config.getPadding(), config.getMaxSideLen(), config.getBoxScoreThresh(), config.getBoxThresh(), config.getUnClipRatio(), config.isDoAngle(), config.isMostAngle());
        log.info("识别结果为：{}，耗时{}ms", result.getStrRes().replace("\n", ""), result.getDetectTime());
        log.debug("文本块：{}，DbNet耗时{}ms", result.getTextBlocks(), result.getDbNetTime());
        return result;
    }
}
