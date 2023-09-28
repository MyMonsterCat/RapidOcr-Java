package com.github.monster;

import com.benjaminwan.ocrlibrary.OcrEngine;
import com.benjaminwan.ocrlibrary.OcrResult;

/**
 * OCR基础类
 * 用于封装OcrEngine的调用
 */
public class OcrBase {

    /**
     * 临时目录
     */
    private static String tempDirPath = System.getProperty("java.io.tmpdir") + JarFileUtils.NATIVE_FOLDER_PATH_PREFIX;

    protected OcrBase() {
    }

    /**
     * OCR识别
     * <p>使用native方法，可以让OcrEngine成为单例</p>
     * <p>OcrResult ocrResult = ocrEngine.detect(imagePath, padding, maxSideLen, boxScoreThresh, boxThresh, unClipRatio, doAngle, mostAngle);</p>
     *
     * @param imagePath 要识别的图片路径
     * @param ocrConfig ocr配置
     */
    public static OcrResult ocr(String imagePath, OcrConfig ocrConfig) {
        String libraryDir = ocrConfig.getLibraryDir();
        String modelsDir = ocrConfig.getModelsDir();
        boolean deleteOnExit = ocrConfig.isDeleteOnExit();
        String detName = PathConstants.MODEL_DET_NAME;
        String clsName = PathConstants.MODEL_CLS_NAME;
        String recName = PathConstants.MODEL_REC_NAME;
        String keysName = PathConstants.MODEL_KEYS_NAME;
        Integer numThread = ocrConfig.getNumThread();
        Integer padding = ocrConfig.getPadding();
        Integer maxSideLen = ocrConfig.getMaxSideLen();
        Float boxScoreThresh = ocrConfig.getBoxScoreThresh();
        Float boxThresh = ocrConfig.getBoxThresh();
        Float unClipRatio = ocrConfig.getUnClipRatio();
        Integer doAngleFlag = ocrConfig.getDoAngleFlag();
        Integer mostAngleFlag = ocrConfig.getMostAngleFlag();
        Integer gpuIndex = ocrConfig.getGpuIndex();

        OcrEngine ocrEngine = new OcrEngine(libraryDir, modelsDir, deleteOnExit);
        ocrEngine.setNumThread(numThread);
        ocrEngine.initLogger(
                true,
                false,
                false
        );
        ocrEngine.enableResultText(imagePath);

        if (gpuIndex != null) {
            ocrEngine.setGpuIndex(gpuIndex);
        }
        if (!ocrEngine.initModels(tempDirPath, detName, clsName, recName, keysName)) {
            throw new IllegalArgumentException("模型初始化错误，请检查models/keys路径！");
        }
        ocrEngine.setPadding(padding);
        ocrEngine.setBoxScoreThresh(boxScoreThresh);
        ocrEngine.setBoxThresh(boxThresh);
        ocrEngine.setUnClipRatio(unClipRatio);
        ocrEngine.setDoAngle(doAngleFlag == 1);
        ocrEngine.setMostAngle(mostAngleFlag == 1);

        return ocrEngine.detect(imagePath, maxSideLen);
    }
}
