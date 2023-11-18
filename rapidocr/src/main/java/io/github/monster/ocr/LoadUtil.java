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
        for (LibraryLoader loader : serviceLoader) {
            if (loader.isSupportedPlatform()) {
                return loader;
            }
        }
        return null;
    }

    public static ModelsLoader findSupportedModelsLoader(ServiceLoader<ModelsLoader> serviceLoader) {
        for (ModelsLoader loader : serviceLoader) {
            if (loader.isSupportedPlatform()) {
                return loader;
            }
        }
        return null;
    }
}
