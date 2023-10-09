package com.github.monster;

/**
 * @author Monster
 */
public class PathConstants {

    private PathConstants() {
    }

    public static final String NCNN = "/ncnn";

    /**
     * 模型相关
     **/
    public static final String MODEL_PATH = NCNN + "/models";
    public static final String MODEL_BIN = ".bin";
    public static final String MODEL_PARAM = ".param";
    public static final String MODEL_DET_NAME = "ch_PP-OCRv3_det_infer";
    public static final String MODEL_REC_NAME = "ch_PP-OCRv3_rec_infer";
    public static final String MODEL_CLS_NAME = "ch_ppocr_mobile_v2.0_cls_infer";
    public static final String MODEL_KEYS_NAME = "ppocr_keys_v1.txt";

    /**
     * 动态库
     **/
    public static final String OS_WINDOWS = NCNN + "/RapidOcrNcnn.dll";
    public static final String OS_MAC_SILICON = NCNN + "/libRapidOcrNcnn-silicon.dylib";
    public static final String OS_MAC_INTEL = NCNN + "/libRapidOcrNcnn-intel.dylib";
    public static final String OS_LINUX = NCNN + "/libRapidOcrNcnn.so";


}
