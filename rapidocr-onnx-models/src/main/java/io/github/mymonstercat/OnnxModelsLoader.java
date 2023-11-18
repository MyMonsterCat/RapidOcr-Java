package io.github.mymonstercat;


import java.io.IOException;

/**
 * @author Monster
 */
public class OnnxModelsLoader implements ModelsLoader {
    @Override
    public void loadModels(Model model) throws IOException {
        JarFileUtil.copyModelsFromJar(model, false);
    }

    @Override
    public boolean isSupportedPlatform() {
        return true;
    }
}
