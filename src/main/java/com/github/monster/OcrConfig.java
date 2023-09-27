package com.github.monster;

/**
 * @author Monster
 */
public class OcrConfig {

    private String libraryDir;
    private String modelsDir;

    private Integer numThread = 4;
    private Integer padding = null;
    private Integer maxSideLen = null;
    private Float boxScoreThresh = null;
    private Float boxThresh = null;
    private Float unClipRatio = null;
    private Integer doAngleFlag = null;
    private Integer mostAngleFlag = null;
    private Integer gpuIndex = 0;


    public OcrConfig() {
    }

    public OcrConfig(Integer numThread, Integer padding, Integer maxSideLen, Float boxScoreThresh, Float boxThresh, Float unClipRatio, Integer doAngleFlag, Integer mostAngleFlag, Integer gpuIndex) {
        this.numThread = numThread;
        this.padding = padding;
        this.maxSideLen = maxSideLen;
        this.boxScoreThresh = boxScoreThresh;
        this.boxThresh = boxThresh;
        this.unClipRatio = unClipRatio;
        this.doAngleFlag = doAngleFlag;
        this.mostAngleFlag = mostAngleFlag;
        this.gpuIndex = gpuIndex;
    }

    public Integer getNumThread() {
        return numThread;
    }

    public void setNumThread(Integer numThread) {
        this.numThread = numThread;
    }

    public Integer getPadding() {
        return padding;
    }

    public void setPadding(Integer padding) {
        this.padding = padding;
    }

    public Integer getMaxSideLen() {
        return maxSideLen;
    }

    public void setMaxSideLen(Integer maxSideLen) {
        this.maxSideLen = maxSideLen;
    }

    public Float getBoxScoreThresh() {
        return boxScoreThresh;
    }

    public void setBoxScoreThresh(Float boxScoreThresh) {
        this.boxScoreThresh = boxScoreThresh;
    }

    public Float getBoxThresh() {
        return boxThresh;
    }

    public void setBoxThresh(Float boxThresh) {
        this.boxThresh = boxThresh;
    }

    public Float getUnClipRatio() {
        return unClipRatio;
    }

    public void setUnClipRatio(Float unClipRatio) {
        this.unClipRatio = unClipRatio;
    }

    public Integer getDoAngleFlag() {
        return doAngleFlag;
    }

    public void setDoAngleFlag(Integer doAngleFlag) {
        this.doAngleFlag = doAngleFlag;
    }

    public Integer getMostAngleFlag() {
        return mostAngleFlag;
    }

    public void setMostAngleFlag(Integer mostAngleFlag) {
        this.mostAngleFlag = mostAngleFlag;
    }

    public Integer getGpuIndex() {
        return gpuIndex;
    }

    public void setGpuIndex(Integer gpuIndex) {
        this.gpuIndex = gpuIndex;
    }

    public String getLibraryDir() {
        return libraryDir;
    }

    public void setLibraryDir(String libraryDir) {
        this.libraryDir = libraryDir;
    }

    public String getModelsDir() {
        return modelsDir;
    }

    public void setModelsDir(String modelsDir) {
        this.modelsDir = modelsDir;
    }
}
