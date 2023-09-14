package com.github.monster;

import com.benjaminwan.ocrlibrary.OcrResult;
import org.junit.Test;


public class OcrUtilTest {

    @Test
    public void ncnnOcr() {
        OcrResult ocrResult = OcrUtil.NcnnOcr("images/1.jpg");
        System.out.println(ocrResult);
    }

    @Test
    public void onnxOcr() {
        OcrResult ocrResult =  OcrUtil.OnnxOcr("images/1.jpg");
        System.out.println(ocrResult);
    }
}