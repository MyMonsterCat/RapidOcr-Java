package io.github.mymonstercat;

import io.github.mymonstercat.loader.NcnnLibraryLoader;

import java.io.IOException;

/**
 * @author Monster
 */
public class NcnnLinuxX8664LibraryLoader implements NcnnLibraryLoader {

    @Override
    public void loadLibrary() throws IOException {
        JarFileUtil.copyFileFromJar("lib/libRapidOcr.dylib", "/onnx", true, false);
    }

    @Override
    public boolean isSupportedPlatform(String osName, String osArch) {
        return osName.contains("linux") && (osArch.contains("x86") || osArch.contains("amd64"));
    }
}
