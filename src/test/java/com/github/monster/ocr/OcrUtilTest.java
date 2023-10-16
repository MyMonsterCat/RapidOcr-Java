package com.github.monster.ocr;

import com.benjaminwan.ocrlibrary.OcrResult;
import com.github.monster.ocr.config.HardwareConfig;
import com.github.monster.ocr.config.LibConfig;
import com.github.monster.ocr.config.ParamConfig;
import org.junit.Test;

/**
 * @author Monster
 */
public class OcrUtilTest {

    @Test
    public void libTest() {
        // 使用不同推理引擎进行识别
        OcrResult NCNNResult = OcrUtil.runOcr("images/40.png", LibConfig.getNcnnConfig());
//        OcrResult ONNXResult = OcrUtil.runOcr("images/40.png", LibConfig.getOnnxConfig());
        System.out.println(NCNNResult);
//        System.out.println(ONNXResult);
    }

    @Test
    public void paramTest() {
        // 配置参数
        ParamConfig paramConfig = new ParamConfig();
        paramConfig.setDoAngle(true);
        paramConfig.setMostAngle(true);
        // 开始识别
        OcrResult ocrResult = OcrUtil.runOcr("images/1.jpg", LibConfig.getNcnnConfig(), paramConfig);
        System.out.println(ocrResult);
    }

    @Test
    public void hardWareTest() {
        // 配置可变参数
        ParamConfig paramConfig = new ParamConfig();
        paramConfig.setDoAngle(true);
        paramConfig.setMostAngle(true);
        // 配置硬件参数：4核CPU，不使用GPU
        HardwareConfig hardwareConfig = new HardwareConfig(4, 0);
        // 开始识别
        OcrResult ocrResult = OcrUtil.runOcr("images/1.jpg", LibConfig.getNcnnConfig(), paramConfig, hardwareConfig);
        System.out.println(ocrResult);
    }

    @Test
    public void repeatOcr() {
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
}
