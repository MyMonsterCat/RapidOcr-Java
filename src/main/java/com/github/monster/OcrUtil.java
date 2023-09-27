package com.github.monster;

import com.benjaminwan.ocrlibrary.OcrResult;

public class OcrUtil extends OcrBase {

    public static OcrResult NcnnOcr(String imagePath) {
        ConfigLoader configLoader = new ConfigLoader();
        // 自动识别配置
        OcrConfig config = configLoader.getConfig();
        // 调用
        return NcnnOcr(imagePath, config);
    }

    public static OcrResult NcnnOcr(String imagePath, OcrConfig config) {
        return Ocr(config.getLibraryDir(), config.getModelsDir(),
                ModelConstants.MODEL_DET_NAME, ModelConstants.MODEL_CLS_NAME, ModelConstants.MODEL_REC_NAME, ModelConstants.MODEL_KEYS_NAME, imagePath,
                config.getNumThread(), config.getPadding(), config.getMaxSideLen(), config.getBoxScoreThresh(),
                config.getBoxThresh(), config.getUnClipRatio(), config.getDoAngleFlag(), config.getMostAngleFlag(), config.getGpuIndex());
    }
}
