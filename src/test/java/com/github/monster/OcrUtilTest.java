package com.github.monster;

import com.benjaminwan.ocrlibrary.OcrResult;
import org.junit.Test;

/**
 * @author Monster
 */
public class OcrUtilTest {

    @Test
    public void runOcrConfig() {
        // 从配置加载器获取默认配置
        OcrConfigLoader instance = OcrConfigLoader.getInstance();
        OcrConfig ocrConfig = instance.getBaseConfig();
        // 添加自定义配置
        ocrConfig.setDoAngleFlag(1);
        ocrConfig.setMostAngleFlag(1);
        // 开始识别
        OcrResult ocrResult = OcrUtil.runOcr("images/1.jpg", ocrConfig);
        System.out.println(ocrResult);
    }

    @Test
    public void runOcr() {
        // 开始识别
        OcrResult ocrResult = OcrUtil.runOcr("images/1.jpg");
        System.out.println(ocrResult);
    }
}
