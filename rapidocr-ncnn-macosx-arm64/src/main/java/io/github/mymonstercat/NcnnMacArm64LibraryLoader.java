package io.github.mymonstercat;

import io.github.mymonstercat.loader.NcnnLibraryLoader;

import java.io.IOException;

/**
 * @author Monster
 */
public class NcnnMacArm64LibraryLoader implements NcnnLibraryLoader {

    @Override
    public void loadLibrary() throws IOException {
        JarFileUtil.copyFileFromJar("lib/libRapidOcr.dylib", "/ncnn", true, false);
    }

    @Override
    public boolean isSupportedPlatform(String osName, String osArch) {
        return osName.contains("mac") && osArch.contains("arch64");
    }
}
