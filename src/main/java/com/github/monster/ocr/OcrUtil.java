package com.github.monster.ocr;

import com.benjaminwan.ocrlibrary.OcrEngine;
import com.benjaminwan.ocrlibrary.OcrResult;
import com.github.monster.ocr.config.HardwareConfig;
import com.github.monster.ocr.config.LibConfig;
import com.github.monster.ocr.config.ParamConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OCR工具类
 */
public class OcrUtil {

    private OcrUtil() {
    }

    private static final Logger logger = LoggerFactory.getLogger(OcrUtil.class);

    /**
     * OCR引擎
     */
    private static OcrEngine ocrEngine;

    public static OcrResult runOcr(String imagePath) {
        return runOcr(imagePath, LibConfig.getOnnxConfig(), ParamConfig.getDefaultConfig(), HardwareConfig.getOnnxConfig());
    }

    public static OcrResult runOcr(String imagePath, LibConfig libConfig, ParamConfig config) {
        return runOcr(imagePath, libConfig, config, HardwareConfig.getOnnxConfig());
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
        logger.info("图片路径：{}， 检测参数：{}", imagePath, config);
        // 获取引擎
        initOcrEngine(libConfig, hardwareConfig);
        // 开始识别
        OcrResult detect = ocrEngine.detect(imagePath, config.getPadding(), config.getMaxSideLen(), config.getBoxScoreThresh(), config.getBoxThresh(), config.getUnClipRatio(), config.isDoAngle(), config.isMostAngle());
        logger.info("识别结果为：{}，耗时{}ms", detect.getStrRes().replace("\n", ""), detect.getDetectTime());
        logger.debug("文本块：{}，DbNet耗时{}ms", detect.getTextBlocks(), detect.getDbNetTime());
        return detect;
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
            if (hardwareConfig.getGpuIndex() != -1) {
                ocrEngine.setGpuIndex(hardwareConfig.getGpuIndex());
            }
            if (!ocrEngine.initModels(libConfig.getTempDirPath(), libConfig.getDetName(), libConfig.getClsName(), libConfig.getRecName(), libConfig.getKeysName())) {
                throw new IllegalArgumentException("模型初始化错误，请检查models/keys路径！");
            }
            String engine = ocrEngine.getInference().replace("/", "");
            logger.info("推理引擎初始化完成，当前使用的推理引擎为：{}，RapidOcr-{} 版本为：{}", engine, engine, ocrEngine.getVersion());
            logger.debug("初始化时动态库配置为：{}， 硬件配置为：{}", libConfig, hardwareConfig);
        } else {
            String engine = ocrEngine.getInference().replace("/", "");
            logger.debug("{}引擎已初始化，初始化后不可更换为其他引擎，将继续使用{}推理引擎工作", engine, engine);
        }

    }

}
