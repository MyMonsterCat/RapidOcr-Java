package com.benjaminwan.ocrlibrary;

import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.config.HardwareConfig;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * OCR引擎对象，通过JNI与库文件交互
 */
@Slf4j
public class OcrEngine {

    protected void initEngine(Model model, HardwareConfig hardwareConfig) {
        if (!isInit) {
            synchronized (this) {
                if (!isInit) {
                    initLogger(false, false, false);
                    setNumThread(hardwareConfig.getNumThread());
                    if (hardwareConfig.getGpuIndex() != -1) {
                        setGpuIndex(hardwareConfig.getGpuIndex());
                    }
                    if (!initModels(model.getTempDirPath(), model.getDetName(), model.getClsName(), model.getRecName(), model.getKeysName())) {
                        log.error("模型初始化错误，请检查models路径！model: {}", model);
                        throw new IllegalArgumentException("模型初始化错误，请检查models/keys路径！");
                    }
                    inferType = model.getModelType();
                    log.info("推理引擎初始化完成，当前使用的推理引擎为：{}-v{}", inferType, getVersion());
                    log.info("初始化时模型配置为：{}， 硬件配置为：{}", model, hardwareConfig);
                    isInit = true;
                }
            }
        } else {
            if (!Objects.equals(model.getModelType(), inferType)) {
                log.warn("引擎已初始化，初始化后不可更换为其他引擎，将继续使用{}推理引擎工作", inferType);
            } else {
                log.info("当前使用的推理引擎为：{}-v{}", inferType, getVersion());
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
