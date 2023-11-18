package io.github.mymonstercat.loader;

import java.io.IOException;

/**
 * @author Monster
 */
public interface LibraryLoader {

    void loadLibrary() throws IOException;

    boolean isSupportedPlatform(String osName, String osArch);
}
