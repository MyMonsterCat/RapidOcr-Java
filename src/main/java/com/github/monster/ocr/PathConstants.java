package com.github.monster.ocr;

import java.io.File;

/**
 * @author Monster
 */
public class PathConstants {

    private PathConstants() {
    }

    /**
     * 推理引擎
     */
    public static final String NCNN = File.separator + "ncnn";
    public static final String ONNX = File.separator + "onnx";

    /**
     * 模型相关
     **/
    public static final String MODEL = File.separator + "models";
    public static final String MODEL_NCNN_PATH = NCNN + MODEL;
    public static final String MODEL_ONNX_PATH = ONNX + MODEL;
    public static final String MODEL_SUFFIX_BIN = ".bin";
    public static final String MODEL_SUFFIX_PARAM = ".param";
    public static final String MODEL_DET_NAME = "ch_PP-OCRv3_det_infer";
    public static final String MODEL_REC_NAME = "ch_PP-OCRv3_rec_infer";
    public static final String MODEL_CLS_NAME = "ch_ppocr_mobile_v2.0_cls_infer";
    public static final String MODEL_KEYS_NAME = "ppocr_keys_v1.txt";

    /**
     * 动态库
     **/
    public static final String OS_WINDOWS_32 = File.separator + "win" + File.separator + "win32" + File.separator + "RapidOcr.dll";
    public static final String OS_WINDOWS_64 = File.separator + "win" + File.separator + "x86_64" + File.separator + "RapidOcr.dll";
    public static final String OS_MAC_SILICON = File.separator + "mac" + File.separator + "arm64" + File.separator + "libRapidOcr.dylib";
    public static final String OS_MAC_INTEL = File.separator + "mac" + File.separator + "x86_64" + File.separator + "libRapidOcr.dylib";
    public static final String OS_LINUX = File.separator + "linux" + File.separator + "libRapidOcr.so";


}
