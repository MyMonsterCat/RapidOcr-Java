package io.github.monster.ocr;

import io.github.mymonstercat.exception.LoadException;
import io.github.mymonstercat.loader.NcnnLibraryLoader;
import io.github.mymonstercat.loader.NcnnModelLoader;
import io.github.mymonstercat.loader.OnnxLibraryLoader;
import io.github.mymonstercat.loader.OnnxModelLoader;
import lombok.SneakyThrows;

import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;

/**
 * @author Monster
 */
public class LoadUtil {
    private LoadUtil() {
        throw new IllegalStateException("Utility class");
    }

    @SneakyThrows
    public static OnnxLibraryLoader findOnnxNativeLoader(ServiceLoader<OnnxLibraryLoader> serviceLoader) {
        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();
        try {
            for (OnnxLibraryLoader loader : serviceLoader) {
                if (loader.isSupportedPlatform(osName, osArch)) {
                    return loader;
                }
            }
        } catch (ServiceConfigurationError error) {
            throw new LoadException("加载onnx库文件失败，请检查您使用的模型与引入的jar包是否均为ONNX", error);
        }
        return null;
    }

    @SneakyThrows
    public static NcnnLibraryLoader findNcnnNativeLoader(ServiceLoader<NcnnLibraryLoader> serviceLoader) {
        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();
        try {
            for (NcnnLibraryLoader loader : serviceLoader) {
                if (loader.isSupportedPlatform(osName, osArch)) {
                    return loader;
                }
            }
        }catch (ServiceConfigurationError error) {
            throw new LoadException("加载ncnn库文件失败，请检查您使用的模型与引入的jar包是否为NCNN", error);
        }
        return null;
    }

    public static OnnxModelLoader findOnnxModelsLoader(ServiceLoader<OnnxModelLoader> serviceLoader) {
        for (OnnxModelLoader loader : serviceLoader) {
            return loader;
        }
        return null;
    }

    public static NcnnModelLoader findNcnnModelsLoader(ServiceLoader<NcnnModelLoader> serviceLoader) {
        for (NcnnModelLoader loader : serviceLoader) {
            return loader;
        }
        return null;
    }
}
