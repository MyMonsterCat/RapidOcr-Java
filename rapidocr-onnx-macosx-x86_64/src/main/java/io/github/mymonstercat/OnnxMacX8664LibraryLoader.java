package io.github.mymonstercat;

import java.io.IOException;

/**
 * @author Monster
 */
public class OnnxMacX8664LibraryLoader implements LibraryLoader {

    @Override
    public void loadLibrary() throws IOException {
        JarFileUtil.copyFileFromJar("lib/libRapidOcr.dylib", "/onnx", true, false);
    }

    @Override
    public boolean isSupportedPlatform(String osName, String osArch) {
        return osName.contains("mac") && !osArch.contains("arch64");
    }
}
