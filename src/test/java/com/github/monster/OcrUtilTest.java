package com.github.monster;

import com.benjaminwan.ocrlibrary.OcrResult;
import org.junit.Test;

import java.io.File;
import java.util.Objects;


public class OcrUtilTest {

    @Test
    public void ncnnOcr() {
        // 获取jni的绝对路径
        String dirPath = Objects.requireNonNull(OcrUtil.class.getResource("")).getPath().split("RapidOcr-Java")[0] + "RapidOcr-Java/ncnn/";
        String absoluteLibraryPath = dirPath + "libRapidOcrNcnn.dylib";
        // 调用
        OcrResult ocrResult = OcrUtil.NcnnOcr(absoluteLibraryPath, dirPath + "/models", "images/1.jpg");
        System.out.println(ocrResult);
    }

    @Test
    public void onnxOcr() {
        // 获取jni的绝对路径
        String dirPath = Objects.requireNonNull(OcrUtil.class.getResource("")).getPath().split("RapidOcr-Java")[0] + "RapidOcr-Java/onnx/";
        String absoluteLibraryPath = dirPath + "libRapidOcrOnnx.dylib";
        // 调用
        OcrResult ocrResult = OcrUtil.OnnxOcr(absoluteLibraryPath, dirPath + "/models", "images/1.jpg");
        System.out.println(ocrResult);
    }
}
