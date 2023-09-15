package com.github.monster;

import com.benjaminwan.ocrlibrary.OcrResult;
import org.junit.Test;

import java.io.File;


public class OcrUtilTest {

    @Test
    public void ncnnOcr() {
        OcrResult ocrResult = OcrUtil.NcnnOcr("ncnn", "images/1.jpg");
        System.out.println(ocrResult);
    }

    @Test
    public void onnxOcr() {
        OcrResult ocrResult = OcrUtil.OnnxOcr("ncnn", "images/1.jpg");
        System.out.println(ocrResult);
    }
}
