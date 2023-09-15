package com.benjaminwan.ocrlibrary;

import com.github.monster.OcrUtil;

import java.io.File;
import java.util.Objects;

/**
 * OCR引擎对象，负责接收参数并执行OCR
 * 请确保包位置无误，必须在com.benjaminwan.ocrlibrary下
 * 参数说明请看{@link OcrUtil}
 */
public class OcrEngine {

    public OcrEngine(String libraryPath) {
        String library;
        String property = System.getProperty("os.name");
        if (property.contains("Win")) {
            library = libraryPath + ".dll";
        } else if (property.contains("Mac")) {
            library = libraryPath + ".dylib";
        } else if (property.contains("Linux")) {
            library = libraryPath + ".so";
        } else {
            throw new UnsupportedOperationException("不支持的系统: " + property);
        }
        System.load(library);
    }

    private int padding = 50;
    private float boxScoreThresh = 0.5f;
    private float boxThresh = 0.3f;
    private float unClipRatio = 1.6f;
    private boolean doAngle = true;
    private boolean mostAngle = true;

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void setBoxScoreThresh(float boxScoreThresh) {
        this.boxScoreThresh = boxScoreThresh;
    }

    public void setBoxThresh(float boxThresh) {
        this.boxThresh = boxThresh;
    }

    public void setUnClipRatio(float unClipRatio) {
        this.unClipRatio = unClipRatio;
    }

    public void setDoAngle(boolean doAngle) {
        this.doAngle = doAngle;
    }

    public void setMostAngle(boolean mostAngle) {
        this.mostAngle = mostAngle;
    }

    public OcrResult detect(String input, int maxSideLen) {
        return detect(input, padding, maxSideLen, boxScoreThresh, boxThresh, unClipRatio, doAngle, mostAngle);
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
