package io.github.mymonstercat;

import io.github.mymonstercat.loader.LibraryLoader;

import java.io.IOException;

/**
 * @author sandywalker
 * @since 2024-4-30
 */
public class OnnxLinuxArm64LibraryLoader implements LibraryLoader {
    @Override
    public void loadLibrary() throws IOException {
        JarFileUtil.copyFileFromJar("lib/libRapidOcr.so", "/onnx", true, false);
    }
}
