package io.github.mymonstercat;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * @author Monster
 */
@Slf4j
public class JarFileUtil {

    public static final String TEMP_DIR = System.getProperty("java.io.tmpdir") + "ocrJava";

    private JarFileUtil() {
    }

    static File tempDir = null;

    /**
     * @param filePath     resources下的文件路径
     * @param aimDir
     * @param load
     * @param deleteOnExit
     * @throws IOException
     */
    public static synchronized void copyFileFromJar(String filePath, String aimDir, boolean load, boolean deleteOnExit) throws IOException {

        // 获取文件名并校验
        String[] parts = filePath.split("/");
        String filename = (parts.length > 1) ? parts[parts.length - 1] : null;
        if (filename == null || filename.length() < 3) {
            throw new IllegalArgumentException("文件名必须至少有3个字符长.");
        }
        // 检查目标文件夹是否存在
        if (tempDir == null) {
            tempDir = new File(TEMP_DIR, aimDir);
            if (!tempDir.exists() && !tempDir.mkdirs()) {
                throw new IOException("无法在临时目录创建文件夹" + tempDir);
            }
        }
        // 在临时文件夹下创建文件
        File temp = new File(tempDir, filename.startsWith("/") ? filename : "/" + filename);
        if (!temp.exists()) {
            // 从jar包中复制文件到系统临时文件夹
            try (InputStream is = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(filePath)) {
                if (is != null) {
                    Files.copy(is, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } else {
                    throw new NullPointerException();
                }
            } catch (IOException e) {
                Files.delete(temp.toPath());
                throw e;
            } catch (NullPointerException e) {
                throw new FileNotFoundException("文件 " + filePath + " 在JAR中未找到.");
            }
        }
        // 加载临时文件夹中的动态库
        if (load) {
            System.load(temp.getAbsolutePath());
        }
        // JVM结束时删除临时文件和临时文件夹
        if (deleteOnExit) {
            temp.deleteOnExit();
            tempDir.deleteOnExit();
        }
        log.debug("将文件{}复制到{}，加载此文件：{}，JVM退出时删除此文件：{}", filePath, aimDir, load, deleteOnExit);
    }

    /**
     * 从jar包中复制models文件夹下的内容
     *
     * @param model 要使用的模型
     */
    public static void copyModelsFromJar(Model model, boolean isDelOnExit) throws IOException {
        String modelsDir = model.getModelsDir();
        String noStart = modelsDir.startsWith("/") ? modelsDir.substring(1, modelsDir.length()) : modelsDir;
        String base = noStart.endsWith("/") ? noStart : noStart + "/";
        for (final String path : model.getModelFileArray()) {
            copyFileFromJar(base + path, "/" + model.getModelType(), Boolean.FALSE, isDelOnExit);
        }
    }
}
