package com.benjaminwan.ocrlibrary;

import com.github.monster.JarFileUtils;
import com.github.monster.OcrUtil;

import java.io.IOException;

/**
 * OCR引擎对象，负责接收参数并执行OCR
 */
public class OcrEngine {

    public OcrEngine(String libraryPath, String modelsDir, boolean deleteOnExit) {
        try {
            JarFileUtils.copyFileFromJar(libraryPath, null, true, deleteOnExit);
            JarFileUtils.copyModelsFromJar(modelsDir, deleteOnExit);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public native boolean setNumThread(int numThread);

    public native void initLogger(boolean isConsole, boolean isPartImg, boolean isResultImg);

    public native void enableResultText(String imagePath);

    public native boolean initModels(String modelsDir, String detName, String clsName, String recName, String keysName);

    public native void setGpuIndex(int gpuIndex);

    public native String getVersion();

    public native OcrResult detect(
            String input, int padding, int maxSideLen,
            float boxScoreThresh, float boxThresh,
            float unClipRatio, boolean doAngle, boolean mostAngle
    );
}
