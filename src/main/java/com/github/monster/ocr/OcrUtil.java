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
     * 临时目录
     */
    private static String tempDirPath = System.getProperty("java.io.tmpdir") + JarFileUtils.NATIVE_FOLDER_PATH_PREFIX;
    /**
     * OCR引擎
     */
    private static OcrEngine ocrEngine;

    public static OcrResult runOcr(String imagePath) {
        // 获取引擎
        initOcrEngine(LibConfig.getDefaultConfig(), HardwareConfig.getDefaultConfig());
        // 获取默认配置
        ParamConfig config = new ParamConfig();
        // 开始识别
        return ocrEngine.detect(imagePath, config.getPadding(), config.getMaxSideLen(), config.getBoxScoreThresh(), config.getBoxThresh(), config.getUnClipRatio(), config.isDoAngle(), config.isMostAngle());
    }

    public static OcrResult runOcr(String imagePath, ParamConfig config) {
        // 获取引擎
        initOcrEngine(LibConfig.getDefaultConfig(), HardwareConfig.getDefaultConfig());
        // 开始识别
        return ocrEngine.detect(imagePath, config.getPadding(), config.getMaxSideLen(), config.getBoxScoreThresh(), config.getBoxThresh(), config.getUnClipRatio(), config.isDoAngle(), config.isMostAngle());
    }

    /**
     * OCR识别
     * @param imagePath 图片路径
     * @param config 可调参数配置
     * @param libConfig 库文件配置
     * @param hardwareConfig 硬件参数配置
     * @return 识别结果
     */
    public static OcrResult runOcr(String imagePath, ParamConfig config, LibConfig libConfig, HardwareConfig hardwareConfig) {
        // 获取引擎
        initOcrEngine(libConfig, hardwareConfig);
        // 开始识别
        return ocrEngine.detect(imagePath, config.getPadding(), config.getMaxSideLen(), config.getBoxScoreThresh(), config.getBoxThresh(), config.getUnClipRatio(), config.isDoAngle(), config.isMostAngle());
    }

    private static OcrEngine initOcrEngine(LibConfig libConfig, HardwareConfig hardwareConfig) {
        if (ocrEngine != null) {
            return ocrEngine;
        }
        ocrEngine = new OcrEngine(libConfig.getLibraryDir(), libConfig.getModelsDir(), libConfig.isDeleteOnExit());
        ocrEngine.initLogger(
                false,
                false,
                false
        );
        Integer numThread = hardwareConfig.getNumThread();
        ocrEngine.setNumThread(numThread);
        Integer gpuIndex = hardwareConfig.getGpuIndex();
        if (gpuIndex != null) {
            ocrEngine.setGpuIndex(gpuIndex);
        }
        if (!ocrEngine.initModels(tempDirPath, libConfig.getDetName(), libConfig.getClsName(), libConfig.getRecName(), libConfig.getKeysName())) {
            throw new IllegalArgumentException("模型初始化错误，请检查models/keys路径！");
        }
        return ocrEngine;
    }

}
