package com.github.monster.ocr.config;

import com.github.monster.ocr.PathConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 库文件配置类
 */
@Getter
@ToString
public class LibConfig implements IOcrConfig {


    /**
     * 动态链接库路径，决定了使用什么推理引擎
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

    @Setter
    String detName = PathConstants.MODEL_DET_NAME_V3 + PathConstants.MODEL_SUFFIX_ONNX;
    @Setter
    String clsName = PathConstants.MODEL_CLS_NAME + PathConstants.MODEL_SUFFIX_ONNX;
    @Setter
    String recName = PathConstants.MODEL_REC_NAME_V4 + PathConstants.MODEL_SUFFIX_ONNX;
    @Setter
    String keysName = PathConstants.MODEL_KEYS_NAME;


    private LibConfig() {
    }

    public LibConfig(String libraryDir, String modelsDir, boolean deleteOnExit) {
        this.libraryDir = libraryDir;
        this.modelsDir = modelsDir;
        this.deleteOnExit = deleteOnExit;
    }

    /**
     * 使用NCNN推理引擎
     * 自动加载动态库、复制模型文件，以及设置CPU线程数，并且模型文件在第一次加载后会被缓存
     */
    public static LibConfig getNcnnConfig() {
        LibConfig libConfig = new LibConfig(PathConstants.NCNN + getLibraryName(), PathConstants.MODEL_NCNN_PATH, false);
        libConfig.setDetName(PathConstants.MODEL_DET_NAME_V3);
        libConfig.setClsName(PathConstants.MODEL_CLS_NAME);
        libConfig.setRecName(PathConstants.MODEL_REC_NAME_V3);
        return libConfig;
    }

    /**
     * 使用ONNX推理引擎
     * 自动加载动态库、复制模型文件，以及设置CPU线程数，并且模型文件在第一次加载后会被缓存
     */
    public static LibConfig getOnnxConfig() {
        return new LibConfig(PathConstants.ONNX + getLibraryName(), PathConstants.MODEL_ONNX_PATH, false);
    }

    /**
     * 根据系统决定要加载的动态库
     */
    private static String getLibraryName() {
        String os = System.getProperty("os.name").toLowerCase();
        String cpu = System.getProperty("os.arch").toLowerCase();
        String libraryName;
        if (os.contains("win")) {
            if (cpu.contains("amd64")) {
                libraryName = PathConstants.OS_WINDOWS_64;
            } else {
                libraryName = PathConstants.OS_WINDOWS_32;
            }
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
     * 获取临时文件夹路径
     *
     * @return 临时文件夹路径
     */
    public String getTempDirPath() {
        if (this.libraryDir.contains(PathConstants.ONNX)) {
            return PathConstants.TEMP_DIR + PathConstants.ONNX;
        } else {
            return PathConstants.TEMP_DIR + PathConstants.NCNN;
        }
    }
}
