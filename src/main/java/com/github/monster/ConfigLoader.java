package com.github.monster;

/**
 * @author Monster
 */
public class ConfigLoader {

    public OcrConfig getConfig() {
        // 获取文件夹
        OcrConfig ocrConfig = new OcrConfig();
        // 根据系统选择不同的加载文件
        String libraryName = getLibraryName();
        ocrConfig.setLibraryDir(libraryName);
        // 默认模型路径
        ocrConfig.setModelsDir(ModelConstants.MODEL_PATH);
        return ocrConfig;
    }

    private String getLibraryName() {
        String os = System.getProperty("os.name").toLowerCase();
        String cpu = System.getProperty("os.arch").toLowerCase();
        String libraryName;

        if (os.contains("win")) {
            libraryName = ModelConstants.OS_WINDOWS;
        } else if (os.contains("mac")) {
            if (cpu.contains("arch64")) {
                libraryName = ModelConstants.OS_MAC_SILICON;
            } else {
                libraryName = ModelConstants.OS_MAC_INTEL;
            }
        } else if (os.contains("nix") || os.contains("nux") || os.contains("linux")) {
            libraryName = ModelConstants.OS_LINUX;
        } else {
            throw new UnsupportedOperationException("Unsupported operating system: " + os);
        }
        return libraryName;
    }
}
