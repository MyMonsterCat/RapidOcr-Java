package com.github.monster.ocr;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import com.benjaminwan.ocrlibrary.OcrResult;
import com.benjaminwan.ocrlibrary.TextBlock;
import io.github.monster.ocr.InferenceEngine;
import io.github.monster.ocr.Model;
import io.github.monster.ocr.config.HardwareConfig;
import io.github.monster.ocr.config.ParamConfig;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;

/**
 * @author Monster
 */
public class OcrUtilTest {

    @Test
    public void NcnnTest() {
        InferenceEngine engine = InferenceEngine.getInstance(Model.NCNN_PPOCR_V3);
        // 使用NCNN引擎进行识别
        OcrResult NCNNResult = engine.runOcr(getResourcePath("/images/40.png"), ParamConfig.getDefaultConfig());
        Assert.assertEquals("40", NCNNResult.getStrRes().trim().toString());
    }

    @Test
    public void OnnxTest() {
        String imgPath = getResourcePath("/images/40.png");
        InferenceEngine engine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V3);
        OcrResult ONNXResult = engine.runOcr(imgPath);
        Assert.assertEquals("40", ONNXResult.getStrRes().trim().toString());
    }

    @Test
    public void OnnxDrawTest() {
        InferenceEngine engine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V4);
        String imgPath = getResourcePath("/images/system.png");
        String drawPath = imgPath.replace("40", "40-draw");
        File drawFile = new File(drawPath);
        // 使用ONNX推理引擎进行识别
        // 配置参数
        ParamConfig paramConfig = ParamConfig.getDefaultConfig();
        paramConfig.setDoAngle(true);
        paramConfig.setMostAngle(true);
        // 开始识别
        OcrResult ONNXResult = engine.runOcr(imgPath, paramConfig);
        // 绘制推理结果
        ArrayList<TextBlock> textBlocks = ONNXResult.getTextBlocks();
        FileUtil.copy(imgPath, drawPath, Boolean.TRUE);
        ByteArrayInputStream in = IoUtil.toStream(ImageUtil.drawImg(drawFile, textBlocks));
        FileUtil.writeFromStream(in, drawFile);
        System.out.println("文件保存在： " + drawPath);
        Assert.assertEquals("System", ONNXResult.getStrRes().trim().toString());
    }


    @Test
    public void paramTest() {
        // 配置可变参数
        ParamConfig paramConfig = ParamConfig.getDefaultConfig();
        paramConfig.setDoAngle(true);
        paramConfig.setMostAngle(true);
        InferenceEngine engine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V3);
        // 开始识别
        OcrResult ocrResult = engine.runOcr(getResourcePath("/images/test.png"), paramConfig);
        System.out.println(ocrResult);
    }

    @Test
    public void hardWareTest() {
        // 配置可变参数
        ParamConfig paramConfig = ParamConfig.getDefaultConfig();
        paramConfig.setDoAngle(true);
        paramConfig.setMostAngle(true);
        // 配置硬件参数：4核CPU，使用GPU0
        HardwareConfig hardwareConfig = new HardwareConfig(4, -1);
        InferenceEngine engine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V4, hardwareConfig);
        // 开始识别
        OcrResult ocrResult = engine.runOcr(getResourcePath("/images/test.png"), paramConfig);
        System.out.println(ocrResult);
    }

    @Test
    public void repeatOcr() {
        InferenceEngine engine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V3);
        String real = "40";
        System.out.println("ONNX 1>>>>>>>> ");
        OcrResult NCNN_1 = engine.runOcr(getResourcePath("/images/40.png"));
        Assert.assertEquals(real, NCNN_1.getStrRes().trim().toString());

        System.out.println("ONNX 2>>>>>>>> ");
        OcrResult NCNN_2 = engine.runOcr(getResourcePath("/images/40.png"));
        Assert.assertEquals(real, NCNN_2.getStrRes().trim().toString());

        System.out.println("ONNX 3>>>>>>>> ");
        OcrResult NCNN_3 = engine.runOcr(getResourcePath("/images/40.png"));
        Assert.assertEquals(real, NCNN_3.getStrRes().trim().toString());

        System.out.println("ONNX 4>>>>>>>> ");
        OcrResult NCNN_4 = engine.runOcr(getResourcePath("/images/system.png"));
        Assert.assertEquals("System", NCNN_4.getStrRes().trim().toString());

        System.out.println("ONNX 5>>>>>>>> ");
        OcrResult NCNN_5 = engine.runOcr(getResourcePath("/images/40.png"));
        Assert.assertEquals(real, NCNN_5.getStrRes().trim().toString());
    }

    private static String getResourcePath(String path) {
        return new File(OcrUtilTest.class.getResource(path).getFile()).toString();
    }

}
