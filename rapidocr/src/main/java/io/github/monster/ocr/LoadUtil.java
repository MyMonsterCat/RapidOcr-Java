package io.github.monster.ocr;

import io.github.mymonstercat.ModelsLoader;
import io.github.mymonstercat.LibraryLoader;

import java.util.ServiceLoader;

/**
 * @author Monster
 */
public class LoadUtil {
    private LoadUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static LibraryLoader findSupportedNativeLoader(ServiceLoader<LibraryLoader> serviceLoader) {
        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();
        for (LibraryLoader loader : serviceLoader) {
            if (loader.isSupportedPlatform(osName, osArch)) {
                return loader;
            }
        }
        return null;
    }

    public static ModelsLoader findSupportedModelsLoader(ServiceLoader<ModelsLoader> serviceLoader) {
        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();
        for (ModelsLoader loader : serviceLoader) {
            if (loader.isSupportedPlatform(osName, osArch)) {
                return loader;
            }
        }
        return null;
    }
}
