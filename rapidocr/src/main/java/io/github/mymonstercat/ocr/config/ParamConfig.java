package io.github.mymonstercat.ocr.config;

import lombok.*;

/**
 * 可调节参数配置
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParamConfig implements IOcrConfig{

    /**
     * 图像外接白框，用于提升识别率，文字框没有正确框住所有文字时，增加此值。默认50。
     */
    private int padding = 50;
    /**
     * 按图像长边进行总体缩放，放大增加识别耗时但精度更高，缩小减小耗时但精度降低，maxSideLen为0表示不缩放
     */
    private int maxSideLen = 0;
    /**
     * 文字框置信度门限，文字框没有正确框住所有文字时，减小此值
     */
    private float boxScoreThresh = 0.5f;
    /**
     * 同上，自行试验
     */
    private float boxThresh = 0.3f;
    /**
     * 单个文字框大小倍率，越大时单个文字框越大
     */
    private float unClipRatio = 1.6f;
    /**
     * 启用(true)/禁用(false) 文字方向检测，只有图片倒置的情况下(旋转90~270度的图片)，才需要启用文字方向检测，默认关闭
     */
    private boolean doAngle = false;
    /**
     * 启用(1)/禁用(0) 角度投票(整张图片以最大可能文字方向来识别)，当禁用文字方向检测时，此项也不起作用，默认关闭
     */
    private boolean mostAngle = false;

    public static ParamConfig getDefaultConfig(){
        return new ParamConfig();
    }

}
