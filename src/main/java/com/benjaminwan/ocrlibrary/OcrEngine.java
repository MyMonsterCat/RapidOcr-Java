package com.benjaminwan.ocrlibrary;

import com.github.monster.ocr.JarFileUtils;
import com.github.monster.ocr.PathConstants;

import java.io.IOException;
import java.util.Objects;

/**
 * OCR引擎对象，负责接收参数并执行OCR
 */
public class OcrEngine {

    public OcrEngine(String libraryPath, String modelsDir, boolean deleteOnExit) {
        try {
            if (Objects.equals(PathConstants.ONNX, libraryPath)) {
                JarFileUtils.copyFileFromJar(libraryPath, PathConstants.ONNX, null, true, deleteOnExit);
                inference = PathConstants.ONNX;
            } else {
                JarFileUtils.copyFileFromJar(libraryPath, PathConstants.NCNN, null, true, deleteOnExit);
                inference = PathConstants.NCNN;
            }
            JarFileUtils.copyModelsFromJar(modelsDir, deleteOnExit);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String inference;

    public String getInference() {
        return inference;
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
