package com.benjaminwan.ocrlibrary;

import java.util.ArrayList;

public class OcrResult implements OcrOutput {
    private final double dbNetTime;
    private final ArrayList<TextBlock> textBlocks;
    private double detectTime;
    private String strRes;

    public OcrResult(double dbNetTime, ArrayList<TextBlock> textBlocks, double detectTime, String strRes) {
        this.dbNetTime = dbNetTime;
        this.textBlocks = textBlocks;
        this.detectTime = detectTime;
        this.strRes = strRes;
    }

    public double getDbNetTime() {
        return dbNetTime;
    }

    public ArrayList<TextBlock> getTextBlocks() {
        return textBlocks;
    }

    public double getDetectTime() {
        return detectTime;
    }

    public void setDetectTime(double detectTime) {
        this.detectTime = detectTime;
    }

    public String getStrRes() {
        return strRes;
    }

    public void setStrRes(String strRes) {
        this.strRes = strRes;
    }

    @Override
    public String toString() {
        return "OcrResult{" +
                "dbNetTime=" + dbNetTime +
                ", textBlocks=" + textBlocks +
                ", detectTime=" + detectTime +
                ", strRes='" + strRes + '\'' +
                '}';
    }
}
