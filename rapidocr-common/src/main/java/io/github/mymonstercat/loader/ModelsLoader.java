package io.github.mymonstercat.loader;

import io.github.mymonstercat.Model;

import java.io.IOException;

/**
 * @author Monster
 */
public interface ModelsLoader {
    void loadModels(Model model) throws IOException;
}
