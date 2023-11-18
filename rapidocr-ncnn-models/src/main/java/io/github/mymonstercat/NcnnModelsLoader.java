package io.github.mymonstercat;


import io.github.mymonstercat.loader.NcnnModelLoader;

import java.io.IOException;

/**
 * @author Monster
 */
public class NcnnModelsLoader implements NcnnModelLoader {
    @Override
    public void loadModels(Model model) throws IOException {
        JarFileUtil.copyModelsFromJar(model, false);
    }
}
