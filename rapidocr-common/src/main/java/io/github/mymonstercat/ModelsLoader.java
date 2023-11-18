package io.github.mymonstercat;

import java.io.IOException;

/**
 * @author Monster
 */
public interface ModelsLoader {

    void loadModels(Model model) throws IOException;

    boolean isSupportedPlatform(String osName, String osArch);
}
