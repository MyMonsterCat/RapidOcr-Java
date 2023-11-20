package io.github.mymonstercat;

import lombok.Getter;
import lombok.ToString;

/**
 * @author Monster
 */
@Getter
@ToString
public enum Model {
    ONNX_PPOCR_V3("/models", "onnx", "ch_PP-OCRv3_det_infer.onnx", "ch_ppocr_mobile_v2.0_cls_infer.onnx", "ch_PP-OCRv3_rec_infer.onnx", "ppocr_keys_v1.txt"),
    ONNX_PPOCR_V4("/models", "onnx", "ch_PP-OCRv4_det_infer.onnx", "ch_ppocr_mobile_v2.0_cls_infer.onnx", "ch_PP-OCRv4_rec_infer.onnx", "ppocr_keys_v1.txt"),
    NCNN_PPOCR_V3("/models", "ncnn", "ch_PP-OCRv3_det_infer", "ch_ppocr_mobile_v2.0_cls_infer", "ch_PP-OCRv3_rec_infer", "ppocr_keys_v1.txt"),
    ;


    Model(String modelsDir, String modelType, String detName, String clsName, String recName, String keysName) {
        this.modelsDir = modelsDir;
        this.modelType = modelType;
        this.detName = detName;
        this.clsName = clsName;
        this.recName = recName;
        this.keysName = keysName;
    }

    private final String modelsDir;
    private final String modelType;
    private final String detName;
    private final String clsName;
    private final String recName;
    private final String keysName;

    public String getTempDirPath() {
        return JarFileUtil.TEMP_DIR + "/" + modelType;
    }

    public String[] getModelFileArray() {
        if (modelType.equals("onnx")) {
            return new String[]{detName, clsName, recName, keysName};
        } else {
            final String bin = ".bin";
            final String param = ".param";
            return new String[]{
                    detName + bin, detName + param,
                    clsName + bin, clsName + param,
                    recName + bin, recName + param,
                    keysName
            };
        }

    }

}
