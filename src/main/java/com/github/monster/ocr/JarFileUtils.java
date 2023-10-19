package com.github.monster.ocr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * 从jar包中加载动态库
 */
public class JarFileUtils {

    /**
     * 文件前缀的最小长度
     */
    private static final int MIN_PREFIX_LENGTH = 3;

    /**
     * 临时文件
     */
    private static File temporaryDir;

    private JarFileUtils() {
    }

    /**
     * 从jar包中复制文件
     * 先将jar包中的动态库复制到系统临时文件夹，如果需要加载会进行加载，并且在JVM退出时自动删除。
     *
     * @param path         要复制文件的路径，必须以'/'开始，比如 /lib/monster.so，必须以'/'开始
     * @param dirName      保存的文件夹名称，必须以'/'开始，可为null
     * @param loadClass    用于提供{@link ClassLoader}加载动态库的类，如果为null,则使用JarFileUtils.class
     * @param load         是否加载，有些文件只需要复制到临时文件夹，不需要加载
     * @param deleteOnExit 是否在JVM退出时删除临时文件
     * @throws IOException           动态库读写错误
     * @throws FileNotFoundException 没有在jar包中找到指定的文件
     */
    public static synchronized void copyFileFromJar(String path, String dirName, Class<?> loadClass, boolean load, boolean deleteOnExit) throws IOException {

        String filename = checkFileName(path);

        // 创建临时文件夹
        if (temporaryDir == null) {
            temporaryDir = createTempDirectory(dirName);
            temporaryDir.deleteOnExit();
        }
        // 临时文件夹下的动态库名
        File temp = new File(temporaryDir, filename);
        if (!temp.exists()) {
            Class<?> clazz = loadClass == null ? JarFileUtils.class : loadClass;
            // 从jar包中复制文件到系统临时文件夹
            try (InputStream is = clazz.getResourceAsStream(path)) {
                if (is != null) {
                    Files.copy(is, temp.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } else {
                    throw new NullPointerException("文件 " + path + " 在JAR中未找到.");
                }
            } catch (IOException e) {
                Files.delete(temp.toPath());
                throw e;
            } catch (NullPointerException e) {
                Files.delete(temp.toPath());
                throw new FileNotFoundException("文件 " + path + " 在JAR中未找到.");
            }
        }
        // 加载临时文件夹中的动态库
        if (load) {
            System.load(temp.getAbsolutePath());
        }
        if (deleteOnExit) {
            // 设置在JVM结束时删除临时文件
            temp.deleteOnExit();
        }
    }

    /**
     * 检查文件名是否正确
     *
     * @param path 文件路径
     * @return 文件名
     * @throws IllegalArgumentException 路径必须以'/'开始、文件名必须至少有3个字符长
     */
    private static String checkFileName(String path) {
        if (null == path || !path.startsWith("/")) {
            throw new IllegalArgumentException("路径必须以文件分隔符开始.");
        }

        // 从路径获取文件名
        String[] parts = path.split("/");
        String filename = (parts.length > 1) ? parts[parts.length - 1] : null;

        // 检查文件名是否正确
        if (filename == null || filename.length() < MIN_PREFIX_LENGTH) {
            throw new IllegalArgumentException("文件名必须至少有3个字符长.");
        }
        return filename;
    }

    /**
     * 从jar包中复制models文件夹下的内容
     *
     * @param modelPath 文件夹路径
     */
    public static void copyModelsFromJar(String modelPath, boolean isDelOnExit) throws IOException {
        String base = modelPath.endsWith("/") ? modelPath : modelPath + "/";
        if (modelPath.contains(PathConstants.ONNX)) {
            for (final String path : PathConstants.MODEL_ONNX_FILE_ARRAY) {
                copyFileFromJar(base + path, PathConstants.ONNX, null, Boolean.FALSE, isDelOnExit);
            }
        } else {
            for (final String path : PathConstants.MODEL_NCNN_FILE_ARRAY) {
                copyFileFromJar(base + path, PathConstants.NCNN, null, Boolean.FALSE, isDelOnExit);
            }
        }

    }

    /**
     * 在系统临时文件夹下创建临时文件夹
     */
    private static File createTempDirectory(String dirName) throws IOException {
        File dir = new File(PathConstants.TEMP_DIR, dirName);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IOException("无法在临时目录创建文件" + dir);
            }
        }
        return dir;
    }
}
