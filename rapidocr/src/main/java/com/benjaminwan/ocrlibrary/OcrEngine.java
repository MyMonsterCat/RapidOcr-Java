package com.benjaminwan.ocrlibrary;

import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.config.HardwareConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * OCR engine object that interacts with library files through JNI.
 */
@Slf4j
public class OcrEngine {

    public void initEngine(Model model, HardwareConfig hardwareConfig) {
        if (!isInit) {
            synchronized (this) {
                if (!isInit) {
                    initLogger(false, false, false);
                    setNumThread(hardwareConfig.getNumThread());
                    if (hardwareConfig.getGpuIndex() != -1) {
                        setGpuIndex(hardwareConfig.getGpuIndex());
                    }
                    if (!initModels(model.getTempDirPath(), model.getDetName(), model.getClsName(), model.getRecName(), model.getKeysName())) {
                        log.error("Model initialization error, please check the models path! Model: {}", model);
                        throw new IllegalArgumentException("Model initialization error, please check the models/keys path!");
                    }
                    inferType = model.getModelType();
                    log.info("Inference engine initialized successfully, currently using inference engine: {}-v{}", inferType, getVersion());
                    log.info("Model configuration during initialization: {}, Hardware configuration: {}", model, hardwareConfig);
                    isInit = true;
                }
            }
        } else {
            if (!Objects.equals(model.getModelType(), inferType)) {
                log.warn("Engine has been initialized already; switching engines post-initialization is not supported, continuing to use {} inference engine", inferType);
            } else {
                log.info("Currently using inference engine: {}-v{}", inferType, getVersion());
            }
        }
    }

    private volatile boolean isInit = false;
    private String inferType;

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
