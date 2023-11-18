package io.github.mymonstercat;

import java.io.IOException;

/**
 * @author Monster
 */
public class OnnxWindowsX8664LibraryLoader implements LibraryLoader {

    @Override
    public void loadLibrary() throws IOException {
        JarFileUtil.copyFileFromJar("lib/RapidOcr.dll", "/onnx", true, false);
    }

    @Override
    public boolean isSupportedPlatform(String osName, String osArch) {
        return osName.contains("win") && osArch.contains("amd64");
    }
}
