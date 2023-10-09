package com.github.monster;

import com.benjaminwan.ocrlibrary.OcrResult;
import com.github.monster.config.HardwareConfig;
import com.github.monster.config.LibConfig;
import com.github.monster.config.ParamConfig;
import org.junit.Test;

/**
 * @author Monster
 */
public class OcrUtilTest {

    @Test
    public void runParamConfig() {
        // 配置参数
        ParamConfig paramConfig = new ParamConfig();
        paramConfig.setDoAngle(true);
        paramConfig.setMostAngle(true);
        // 开始识别
        OcrResult ocrResult = OcrUtil.runOcr("images/1.jpg", paramConfig);
        System.out.println(ocrResult);
    }

    @Test
    public void runOcr() {
        // 开始识别
        System.out.println("第一次OCR >>>>>>>> ");
        OcrResult ocrResult1 = OcrUtil.runOcr("images/img.png");
        System.out.println(ocrResult1);
        // 开始识别
        System.out.println("第二次OCR >>>>>>>> ");
        OcrResult ocrResult2 = OcrUtil.runOcr("images/40.png");
        System.out.println(ocrResult2);
        // 开始识别
        System.out.println("第三次OCR >>>>>>>> ");
        OcrResult ocrResult3 = OcrUtil.runOcr("images/40.png");
        System.out.println(ocrResult3);
    }

    @Test
    public void runHardWareConfig() {
        // 配置可变参数
        ParamConfig paramConfig = new ParamConfig();
        paramConfig.setDoAngle(true);
        paramConfig.setMostAngle(true);
        // 配置硬件参数：4核CPU，不使用GPU
        HardwareConfig hardwareConfig = new HardwareConfig(4, 0);
        // 开始识别
        OcrResult ocrResult = OcrUtil.runOcr("images/1.jpg", paramConfig, LibConfig.getDefaultConfig(), hardwareConfig);
        System.out.println(ocrResult);
    }
}
