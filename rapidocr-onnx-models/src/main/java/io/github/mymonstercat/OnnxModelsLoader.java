package io.github.mymonstercat;


import io.github.mymonstercat.loader.OnnxModelLoader;

import java.io.IOException;

/**
 * @author Monster
 */
public class OnnxModelsLoader implements OnnxModelLoader {
    @Override
    public void loadModels(Model model) throws IOException {
        JarFileUtil.copyModelsFromJar(model, false);
    }
}
