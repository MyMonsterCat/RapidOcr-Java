package com.github.monster.config;

/**
 * OCR引擎启动硬件配置配置
 */
public class HardwareConfig implements IOcrConfig {


    /**
     * CPU 核心数量，默认 1
     */
    private int numThread = 1;

    /**
     * GPU0一般为默认GPU，参数选项：使用CPU(-1)/使用GPU0(0)/使用GPU1(1)/...    重要：ncnn必须设置为0
     */
    private int gpuIndex = 0;

    public int getNumThread() {
        return numThread;
    }

    public void setNumThread(int numThread) {
        this.numThread = numThread;
    }

    public int getGpuIndex() {
        return gpuIndex;
    }

    public void setGpuIndex(int gpuIndex) {
        this.gpuIndex = gpuIndex;
    }

    public HardwareConfig(Integer numThread, Integer gpuIndex) {
        this.numThread = numThread;
        this.gpuIndex = gpuIndex;
    }

    public HardwareConfig() {
    }

    public static HardwareConfig getDefaultConfig() {
        return new HardwareConfig(Runtime.getRuntime().availableProcessors() / 2, 0);
    }


}
