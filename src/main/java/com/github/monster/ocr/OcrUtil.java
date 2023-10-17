package com.github.monster.ocr;

import com.benjaminwan.ocrlibrary.OcrEngine;
import com.benjaminwan.ocrlibrary.OcrResult;
import com.github.monster.ocr.config.HardwareConfig;
import com.github.monster.ocr.config.LibConfig;
import com.github.monster.ocr.config.ParamConfig;

/**
 * OCR工具类
 */
public class OcrUtil {

    /**
     * OCR引擎
     */
    private static OcrEngine ocrEngine;

    public static OcrResult runOcr(String imagePath) {
        // 获取引擎
        initOcrEngine(LibConfig.getNcnnConfig(), HardwareConfig.getOnnxConfig());
        // 获取默认配置
        ParamConfig config = new ParamConfig();
        // 开始识别
        return ocrEngine.detect(imagePath, config.getPadding(), config.getMaxSideLen(), config.getBoxScoreThresh(), config.getBoxThresh(), config.getUnClipRatio(), config.isDoAngle(), config.isMostAngle());
    }

    public static OcrResult runOcr(String imagePath, LibConfig libConfig) {
        // 获取引擎
        initOcrEngine(libConfig, HardwareConfig.getOnnxConfig());
        // 获取默认配置
        ParamConfig config = new ParamConfig();
        // 开始识别
        return ocrEngine.detect(imagePath, config.getPadding(), config.getMaxSideLen(), config.getBoxScoreThresh(), config.getBoxThresh(), config.getUnClipRatio(), config.isDoAngle(), config.isMostAngle());
    }

    public static OcrResult runOcr(String imagePath, LibConfig libConfig, ParamConfig config) {
        // 获取引擎
        initOcrEngine(libConfig, HardwareConfig.getOnnxConfig());
        // 开始识别
        return ocrEngine.detect(imagePath, config.getPadding(), config.getMaxSideLen(), config.getBoxScoreThresh(), config.getBoxThresh(), config.getUnClipRatio(), config.isDoAngle(), config.isMostAngle());
    }

    /**
     * OCR识别
     *
     * @param imagePath      图片路径
     * @param config         可调参数配置
     * @param libConfig      库文件配置
     * @param hardwareConfig 硬件参数配置
     * @return 识别结果
     */
    public static OcrResult runOcr(String imagePath, LibConfig libConfig, ParamConfig config, HardwareConfig hardwareConfig) {
        // 获取引擎
        initOcrEngine(libConfig, hardwareConfig);
        // 开始识别
        return ocrEngine.detect(imagePath, config.getPadding(), config.getMaxSideLen(), config.getBoxScoreThresh(), config.getBoxThresh(), config.getUnClipRatio(), config.isDoAngle(), config.isMostAngle());
    }

    private static void initOcrEngine(LibConfig libConfig, HardwareConfig hardwareConfig) {
        if (ocrEngine == null) {
            ocrEngine = new OcrEngine(libConfig.getLibraryDir(), libConfig.getModelsDir(), libConfig.isDeleteOnExit());
            ocrEngine.initLogger(
                    false,
                    false,
                    false
            );
            ocrEngine.setNumThread(hardwareConfig.getNumThread());
            if (hardwareConfig.getGpuIndex()!=-1) {
                ocrEngine.setGpuIndex(hardwareConfig.getGpuIndex());
            }
            if (!ocrEngine.initModels(libConfig.getTempDirPath(), libConfig.getDetName(), libConfig.getClsName(), libConfig.getRecName(), libConfig.getKeysName())) {
                throw new IllegalArgumentException("模型初始化错误，请检查models/keys路径！");
            }
        }
        System.out.println("当前使用的推理引擎为：" + ocrEngine.getInference().replace("/", "").toUpperCase());
        System.out.println("版本号为：" + ocrEngine.getVersion());
    }

}
