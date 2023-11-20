package io.github.mymonstercat.ocr.config;

import lombok.*;

/**
 * OCR引擎启动硬件配置配置
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HardwareConfig implements IOcrConfig {


    /**
     * CPU 核心数量，默认 1
     */
    private int numThread = 1;

    /**
     * GPU0一般为默认GPU，参数选项：使用CPU(-1)/使用GPU0(0)/使用GPU1(1)/...
     * 重要：ONNX不使用GPU，目前都是使用CPU
     */
    private int gpuIndex = 0;

    public static HardwareConfig getNcnnConfig() {
        return new HardwareConfig(Runtime.getRuntime().availableProcessors() / 2, 0);
    }

    public static HardwareConfig getOnnxConfig() {
        return new HardwareConfig(Runtime.getRuntime().availableProcessors() / 2, -1);
    }


}
