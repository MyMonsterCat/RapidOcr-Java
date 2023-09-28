package com.github.monster;

/**
 * OCR配置类
 */
public class OcrConfig {

    /**
     * 动态链接库路径
     */
    private String libraryDir;
    /**
     * 模型路径
     */
    private String modelsDir;

    /**
     * 是否在JVM退出时删除动态链接库和模型文件
     */
    private boolean deleteOnExit = false;

    String detName = PathConstants.MODEL_DET_NAME;
    String clsName = PathConstants.MODEL_CLS_NAME;
    String recName = PathConstants.MODEL_REC_NAME;
    String keysName = PathConstants.MODEL_KEYS_NAME;

    /**
     * CPU 核心数量
     */
    private Integer numThread = 4;
    /**
     * 图像外接白框，用于提升识别率，文字框没有正确框住所有文字时，增加此值。默认50。
     */
    private Integer padding = 50;
    /**
     * 按图像长边进行总体缩放，放大增加识别耗时但精度更高，缩小减小耗时但精度降低，maxSideLen为0表示不缩放
     */
    private Integer maxSideLen = 0;
    /**
     * 文字框置信度门限，文字框没有正确框住所有文字时，减小此值
     */
    private Float boxScoreThresh = 0.5f;
    /**
     * 同上，自行试验
     */
    private Float boxThresh = 0.3f;
    /**
     * 单个文字框大小倍率，越大时单个文字框越大
     */
    private Float unClipRatio = 1.6f;
    /**
     * 启用(1)/禁用(0) 文字方向检测，只有图片倒置的情况下(旋转90~270度的图片)，才需要启用文字方向检测，默认关闭
     */
    private Integer doAngleFlag = 0;
    /**
     * 启用(1)/禁用(0) 角度投票(整张图片以最大可能文字方向来识别)，当禁用文字方向检测时，此项也不起作用，默认关闭
     */
    private Integer mostAngleFlag = 0;
    /**
     * GPU0一般为默认GPU，参数选项：使用CPU(-1)/使用GPU0(0)/使用GPU1(1)/...    重要：ncnn必须设置为0
     */
    private Integer gpuIndex = 0;


    private OcrConfig() {
    }

    public OcrConfig(String libraryDir, String modelsDir, boolean deleteOnExit) {
        this.libraryDir = libraryDir;
        this.modelsDir = modelsDir;
        this.deleteOnExit = deleteOnExit;
    }


    public String getLibraryDir() {
        return libraryDir;
    }

    public String getModelsDir() {
        return modelsDir;
    }

    public boolean isDeleteOnExit() {
        return deleteOnExit;
    }

    public String getDetName() {
        return detName;
    }

    public String getClsName() {
        return clsName;
    }

    public String getRecName() {
        return recName;
    }

    public String getKeysName() {
        return keysName;
    }

    public Integer getNumThread() {
        return numThread;
    }

    public Integer getPadding() {
        return padding;
    }

    public Integer getMaxSideLen() {
        return maxSideLen;
    }

    public Float getBoxScoreThresh() {
        return boxScoreThresh;
    }

    public Float getBoxThresh() {
        return boxThresh;
    }

    public Float getUnClipRatio() {
        return unClipRatio;
    }

    public Integer getDoAngleFlag() {
        return doAngleFlag;
    }

    public Integer getMostAngleFlag() {
        return mostAngleFlag;
    }

    public Integer getGpuIndex() {
        return gpuIndex;
    }

    public void setNumThread(Integer numThread) {
        this.numThread = numThread;
    }

    public void setPadding(Integer padding) {
        this.padding = padding;
    }

    public void setMaxSideLen(Integer maxSideLen) {
        this.maxSideLen = maxSideLen;
    }

    public void setBoxScoreThresh(Float boxScoreThresh) {
        this.boxScoreThresh = boxScoreThresh;
    }

    public void setBoxThresh(Float boxThresh) {
        this.boxThresh = boxThresh;
    }

    public void setUnClipRatio(Float unClipRatio) {
        this.unClipRatio = unClipRatio;
    }

    public void setDoAngleFlag(Integer doAngleFlag) {
        this.doAngleFlag = doAngleFlag;
    }

    public void setMostAngleFlag(Integer mostAngleFlag) {
        this.mostAngleFlag = mostAngleFlag;
    }

    public void setGpuIndex(Integer gpuIndex) {
        this.gpuIndex = gpuIndex;
    }
}
