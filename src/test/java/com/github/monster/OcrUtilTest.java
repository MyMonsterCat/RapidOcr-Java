package com.github.monster;

import com.benjaminwan.ocrlibrary.OcrResult;
import org.junit.Test;

import java.io.File;
import java.util.Objects;


public class OcrUtilTest {

    @Test
    public void ncnnOcr() {

        // 获取jni的绝对路径
        String dirPath = System.getProperty("user.dir") +  "/ncnn";
        // 调用
        // Mac调用示例
        // OcrResult ocrResult = OcrUtil.NcnnOcr(dirPath + "/RapidOcrNcnn.dylib", dirPath + "/models", "images/1.jpg");
        // windows调用
        OcrResult ocrResult = OcrUtil.NcnnOcr(dirPath + "/RapidOcrNcnn.dll", "ncnn/models", "images/1.jpg");
        System.out.println(ocrResult);
    }

    @Test
    public void onnxOcr() {
        // 获取jni的绝对路径
        String dirPath = System.getProperty("user.dir") +  "/ncnn";
        // Mac调用示例
        // OcrResult ocrResult = OcrUtil.OnnxOcr(dirPath + "/libRapidOcrOnnx.dylib", dirPath + "/models", "images/1.jpg");
        // windows调用
        OcrResult ocrResult = OcrUtil.OnnxOcr(dirPath + "/RapidOcrOnnx.dll", "onnx/models", "images/1.jpg");
        System.out.println(ocrResult);
    }
}
