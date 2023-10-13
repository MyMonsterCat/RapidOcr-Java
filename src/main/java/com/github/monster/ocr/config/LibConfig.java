package com.github.monster.ocr.config;

import com.github.monster.ocr.PathConstants;

/**
 * 库文件配置类
 */
public class LibConfig implements IOcrConfig {

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


    private LibConfig() {
    }

    public LibConfig(String libraryDir, String modelsDir, boolean deleteOnExit) {
        this.libraryDir = libraryDir;
        this.modelsDir = modelsDir;
        this.deleteOnExit = deleteOnExit;
    }

    /**
     * 获取基础配置
     * 自动加载动态库、复制模型文件，以及设置CPU线程数，并且模型文件在第一次加载后会被缓存
     */
    public static LibConfig getDefaultConfig() {
        return new LibConfig(getLibraryName(), PathConstants.MODEL_PATH, false);
    }

    /**
     * 根据系统决定要加载的动态库
     */
    private static String getLibraryName() {
        String os = System.getProperty("os.name").toLowerCase();
        String cpu = System.getProperty("os.arch").toLowerCase();
        String libraryName;

        if (os.contains("win")) {
            libraryName = PathConstants.OS_WINDOWS;
        } else if (os.contains("mac")) {
            if (cpu.contains("arch64")) {
                libraryName = PathConstants.OS_MAC_SILICON;
            } else {
                libraryName = PathConstants.OS_MAC_INTEL;
            }
        } else if (os.contains("nix") || os.contains("nux") || os.contains("linux")) {
            libraryName = PathConstants.OS_LINUX;
        } else {
            throw new UnsupportedOperationException("Unsupported operating system: " + os);
        }
        return libraryName;
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
}
