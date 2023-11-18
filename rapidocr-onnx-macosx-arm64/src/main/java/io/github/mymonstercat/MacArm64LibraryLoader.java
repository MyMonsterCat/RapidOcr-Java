package io.github.mymonstercat;

import java.io.IOException;

/**
 * @author Monster
 */
public class MacArm64LibraryLoader implements LibraryLoader {

    @Override
    public void loadLibrary() throws IOException {
        JarFileUtil.copyFileFromJar("lib/libRapidOcr.dylib", "/onnx", true, false);
    }

    @Override
    public boolean isSupportedPlatform() {
        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();
        return osName.contains("mac") && osArch.contains("arch64");
    }
}
