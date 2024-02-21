package com.benjaminwan.ocrlibrary;

/**
 * 支持传入bitmap二进制数据和传输的字节数据图片
 */
public class OcrInput {
    /**
     *文件或者bitmap字节数据
     */
    private final byte[] data;
    /**
     * 0 bitmap 1 bytes
     */
    private final int type;
    /**
     * 通道数 图片通道bitmap需要传递具体的通道数，file bytes 只需要传入1或者>1表示灰度或者彩色
     */
    private final int channels;
    /**
     * 图片宽度
     */
    private int width;
    /**
     * 图片高度
     */
    private int height;

    public OcrInput(byte[] data, int channels, int width, int height) {
        this.data = data;
        this.type = 0;
        this.channels = channels;
        this.width = width;
        this.height = height;
    }

    public OcrInput(byte[] data) {
        this.data = data;
        this.type = 1;
        this.channels = 4;
    }

    public byte[] getData() {
        return data;
    }

    public int getType() {
        return type;
    }

    public int getChannels() {
        return channels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
