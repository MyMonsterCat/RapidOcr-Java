package io.github.mymonstercat.ocr;

import io.github.mymonstercat.loader.LibraryLoader;
import io.github.mymonstercat.loader.ModelsLoader;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 库文件加载工具类
 */
@Slf4j
public class LoadUtil {
    private LoadUtil() {
        throw new IllegalStateException("Utility class");
    }


    static LibraryLoader findLibLoader(String engine) {
        Properties props = new Properties();
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("load/loaders.properties")) {
            if (input == null) {
                throw new FileNotFoundException("在加载目录中找不到loaders.properties");
            }
            props.load(input);

            String osName = System.getProperty("os.name").toLowerCase();
            String osArch = System.getProperty("os.arch").toLowerCase();
            log.debug("osName: {}, osArch: {}", osName, osArch);
            String loaderClassName = null;
            if (osName.contains("win")) {
                if (osArch.contains("amd64")) {
                    loaderClassName = props.getProperty(engine + ".win-x86_64");
                } else if (osArch.contains("x86")) {
                    loaderClassName = props.getProperty(engine + ".win-x86");
                }
            } else if (osName.contains("mac")) {
                if (osArch.contains("arch64")) {
                    loaderClassName = props.getProperty(engine + ".mac-arm64");
                } else {
                    loaderClassName = props.getProperty(engine + ".mac-x86_64");
                }
            } else if (osName.contains("linux")) {
                if (osArch.contains("x86") || osArch.contains("amd64")) {
                    loaderClassName = props.getProperty(engine + ".linux-x86_64");
                }
//begin sandywalker 2024-04-30 add arm64 linux support
//                else if (osArch.contains("arm")) {
//                    loaderClassName = props.getProperty("linuxArmLoader");
//                }
                else if (osArch.contains("arm") || osArch.contains("arch64")){
                    loaderClassName = props.getProperty(engine + ".linux-arm64");  //Note: only support onnx,  not support ncnn now
                }
//end sandywalker 2024-04-30
            }
            if (loaderClassName != null) {
                return (LibraryLoader) Class.forName(loaderClassName).getDeclaredConstructor().newInstance();
            }
        } catch (Exception e) {
            log.error("获取库文件加载器 {} 失败", e.getMessage());
        }
        return null;
    }

    public static ModelsLoader findModelsLoader(String engine) {
        Properties props = new Properties();
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("load/loaders.properties")) {
            if (input == null) {
                throw new FileNotFoundException("在加载目录中找不到loaders.properties");
            }
            props.load(input);

            return (ModelsLoader) Class.forName(props.getProperty(engine + ".model")).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            log.error("获取模型文件加载器 {} 失败", e.getMessage());
        }
        return null;
    }
}
