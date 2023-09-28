package com.github.monster;

import com.benjaminwan.ocrlibrary.OcrResult;

/**
 * OCR工具类
 */
public class OcrUtil extends OcrBase {

    /**
     * 使用Ncnn进行OCR识别
     *
     * @param imagePath 要识别的图片路径
     * @return 识别结果
     */
    public static OcrResult runOcr(String imagePath) {
        OcrConfigLoader ocrConfigLoader = OcrConfigLoader.getInstance();
        // 自动识别配置
        OcrConfig config = ocrConfigLoader.getBaseConfig();
        // 调用
        return runOcr(imagePath, config);
    }

    /**
     * 使用Ncnn进行OCR识别
     *
     * @param imagePath 要识别的图片路径
     * @param config    ocr配置
     * @return 识别结果
     */
    public static OcrResult runOcr(String imagePath, OcrConfig config) {
        return ocr(imagePath, config);
    }
}
