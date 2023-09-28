package com.github.monster;

/**
 * 配置加载器
 */
public class OcrConfigLoader {

    //单例
    private static OcrConfigLoader ocrConfigLoader;

    private OcrConfigLoader() {
    }

    public static synchronized OcrConfigLoader getInstance() {
        if (ocrConfigLoader == null) {
            ocrConfigLoader = new OcrConfigLoader();
        }
        return ocrConfigLoader;
    }


    /**
     * 获取基础配置
     * 自动加载动态库、复制模型文件，以及设置CPU线程数，并且模型文件在第一次加载后会被缓存
     */
    public OcrConfig getBaseConfig() {
        // 获取文件夹
        OcrConfig ocrConfig = new OcrConfig(getLibraryName(), PathConstants.MODEL_PATH, false);
        // 设置系统CPU
        ocrConfig.setNumThread(getCPUNumber() / 2);
        return ocrConfig;
    }

    /**
     * 根据系统决定要加载的动态库
     */
    private String getLibraryName() {
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

    /**
     * 获取CPU数量
     */
    public int getCPUNumber() {
        return Runtime.getRuntime().availableProcessors();
    }
}
