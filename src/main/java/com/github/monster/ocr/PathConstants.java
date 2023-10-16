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
    public static final String NCNN = "/ncnn";
    public static final String ONNX = "/onnx";

    /**
     * 模型相关
     **/
    public static final String MODEL = "/models";
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
    public static final String OS_WINDOWS_32 = "/win/win32/RapidOcr.dll";
    public static final String OS_WINDOWS_64 = "/win/x86_64/RapidOcr.dll";
    public static final String OS_MAC_SILICON = "/mac/arm64/libRapidOcr.dylib";
    public static final String OS_MAC_INTEL = "/mac/x86_64/libRapidOcr.dylib";
    public static final String OS_LINUX = "/linux/libRapidOcr.so";


}
