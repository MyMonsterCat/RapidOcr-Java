package com.benjaminwan.ocrlibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class TextBlock {
    private final ArrayList<Point> boxPoint;
    private final float boxScore;
    private final int angleIndex;
    private final float angleScore;
    private final double angleTime;
    private final String text;
    private final float[] charScores;
    private final double crnnTime;
    private final double blockTime;

    public TextBlock(
            ArrayList<Point> boxPoint, float boxScore,
            int angleIndex, float angleScore, double angleTime,
            String text, float[] charScores, double crnnTime, double blockTime
    ) {
        this.boxPoint = boxPoint;
        this.boxScore = boxScore;
        this.angleIndex = angleIndex;
        this.angleScore = angleScore;
        this.angleTime = angleTime;
        this.text = text;
        this.charScores = charScores;
        this.crnnTime = crnnTime;
        this.blockTime = blockTime;
    }

    public ArrayList<Point> getBoxPoint() {
        return boxPoint;
    }

    public float getBoxScore() {
        return boxScore;
    }

    public int getAngleIndex() {
        return angleIndex;
    }

    public float getAngleScore() {
        return angleScore;
    }

    public double getAngleTime() {
        return angleTime;
    }

    public String getText() {
        return text;
    }

    public float[] getCharScores() {
        return charScores;
    }

    public double getCrnnTime() {
        return crnnTime;
    }

    public double getBlockTime() {
        return blockTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextBlock textBlock = (TextBlock) o;
        return Float.compare(textBlock.boxScore, boxScore) == 0 &&
                angleIndex == textBlock.angleIndex &&
                Float.compare(textBlock.angleScore, angleScore) == 0 &&
                Double.compare(textBlock.angleTime, angleTime) == 0 &&
                Double.compare(textBlock.crnnTime, crnnTime) == 0 &&
                Double.compare(textBlock.blockTime, blockTime) == 0 &&
                Objects.equals(boxPoint, textBlock.boxPoint) &&
                Objects.equals(text, textBlock.text) &&
                java.util.Arrays.equals(charScores, textBlock.charScores);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(boxPoint, boxScore, angleIndex, angleScore, angleTime, text, crnnTime, blockTime);
        result = 31 * result + java.util.Arrays.hashCode(charScores);
        return result;
    }

    @Override
    public String toString() {
        return "TextBlock{" +
                "boxPoint=" + boxPoint +
                ", boxScore=" + boxScore +
                ", angleIndex=" + angleIndex +
                ", angleScore=" + angleScore +
                ", angleTime=" + angleTime +
                ", text='" + text + '\'' +
                ", charScores=" + Arrays.toString(charScores) +
                ", crnnTime=" + crnnTime +
                ", blockTime=" + blockTime +
                '}';
    }
}