package com.github.monster;

import com.benjaminwan.ocrlibrary.OcrResult;
import org.junit.Test;

import java.io.File;
import java.util.Objects;


public class OcrUtilTest {

    @Test
    public void ncnnOcr() {
        String dirPath = Objects.requireNonNull(OcrUtil.class.getResource("")).getPath().split("RapidOcr-Java")[0] + "RapidOcr-Java/ncnn";
        OcrResult ocrResult = OcrUtil.NcnnOcr(dirPath, "images/1.jpg");
        System.out.println(ocrResult);
    }

    @Test
    public void onnxOcr() {
        String dirPath = Objects.requireNonNull(OcrUtil.class.getResource("")).getPath().split("RapidOcr-Java")[0] + "RapidOcr-Java/onnx";
        OcrResult ocrResult = OcrUtil.OnnxOcr(dirPath, "images/1.jpg");
        System.out.println(ocrResult);
    }
}
