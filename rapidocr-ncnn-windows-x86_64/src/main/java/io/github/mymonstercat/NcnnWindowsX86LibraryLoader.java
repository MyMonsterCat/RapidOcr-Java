package io.github.mymonstercat;

import io.github.mymonstercat.loader.LibraryLoader;

import java.io.IOException;

/**
 * @author Monster
 */
public class NcnnWindowsX86LibraryLoader implements LibraryLoader {

    @Override
    public void loadLibrary() throws IOException {
        JarFileUtil.copyFileFromJar("lib/RapidOcr.dll", "/onnx", true, false);
    }
}
