package io.github.mymonstercat;


import io.github.mymonstercat.loader.ModelsLoader;

import java.io.IOException;

/**
 * @author Monster
 */
public class OnnxModelsLoader implements ModelsLoader {
    @Override
    public void loadModels(Model model) throws IOException {
        JarFileUtil.copyModelsFromJar(model, false);
    }
}
