package com.github.monster;

import com.benjaminwan.ocrlibrary.OcrResult;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Monster
 */
public class OcrUtilTest {

    @Test
    public void ncnnOcr() {
        OcrResult ocrResult = OcrUtil.NcnnOcr("images/1.jpg");
        System.out.println(ocrResult);
    }
}
