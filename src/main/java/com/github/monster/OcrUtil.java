package com.github.monster;

import com.benjaminwan.ocrlibrary.OcrEngine;
import com.benjaminwan.ocrlibrary.OcrResult;

import java.io.File;

public class OcrUtil {

    public static OcrResult NcnnOcr(String dir, String imagePath) {
        return Ocr(dir + "/libRapidOcrNcnn", dir + "/models", "ch_PP-OCRv3_det_infer", "ch_ppocr_mobile_v2.0_cls_infer", "ch_PP-OCRv3_rec_infer", "ppocr_keys_v1.txt", imagePath, 4, null, null, null, null, null, null, null, 0);
    }

    public static OcrResult OnnxOcr(String dir, String imagePath) {
        return Ocr(dir + "/libRapidOcrOnnx", dir + "/models", "ch_PP-OCRv3_det_infer.onnx", "ch_ppocr_mobile_v2.0_cls_infer.onnx", "ch_PP-OCRv3_rec_infer.onnx", "ppocr_keys_v1.txt", imagePath, 4, null, null, null, null, null, null, null, null);
    }


    /**
     * @param library        动态链接库路径，例如绝对路径/User/Monster/projectName/a/b/xx.dll，只需要传a/b/xx
     * @param modelsDir      例如绝对路径/User/Monster/projectName/a/b/models，只需要传a/b/models
     * @param detName        ncnn传ch_PP-OCRv3_det_infer
     * @param clsName        ncnn传ch_ppocr_mobile_v2.0_cls_infer
     * @param recName        ncnn传ch_PP-OCRv3_rec_infer
     * @param keysName       ppocr_keys_v1.txt
     * @param imagePath      待识别的图片地址：例如run-test/images/1.jpg
     * @param numThread      CPU 核心数量
     * @param padding        图像外接白框，用于提升识别率，文字框没有正确框住所有文字时，增加此值。默认50。
     * @param maxSideLen     按图像长边进行总体缩放，放大增加识别耗时但精度更高，缩小减小耗时但精度降低，maxSideLen=0代表不缩放
     * @param boxScoreThresh 文字框置信度门限，文字框没有正确框住所有文字时，减小此值
     * @param boxThresh      同上，自行试验
     * @param unClipRatio    单个文字框大小倍率，越大时单个文字框越大
     * @param doAngleFlag    启用(1)/禁用(0) 文字方向检测，只有图片倒置的情况下(旋转90~270度的图片)，才需要启用文字方向检测
     * @param mostAngleFlag  启用(1)/禁用(0) 角度投票(整张图片以最大可能文字方向来识别)，当禁用文字方向检测时，此项也不起作用
     * @param gpuIndex       GPU0一般为默认GPU，参数选项：使用CPU(-1)/使用GPU0(0)/使用GPU1(1)/...    重要：onnx不可使用CPU/onnx可使用GPU
     */
    public static OcrResult Ocr(String library, String modelsDir, String detName, String clsName, String recName, String keysName, String imagePath,
                                Integer numThread, Integer padding, Integer maxSideLen, Float boxScoreThresh, Float boxThresh,
                                Float unClipRatio, Integer doAngleFlag, Integer mostAngleFlag, Integer gpuIndex) {

        if (numThread == null) {
            numThread = Runtime.getRuntime().availableProcessors();
        }
        if (padding == null) {
            padding = 50;
        }
        if (maxSideLen == null) {
            maxSideLen = 1024;
        }
        if (boxScoreThresh == null) {
            boxScoreThresh = 0.5f;
        }
        if (boxThresh == null) {
            boxThresh = 0.3f;
        }
        if (unClipRatio == null) {
            unClipRatio = 1.6f;
        }
        if (doAngleFlag == null) {
            doAngleFlag = 1;
        }
        boolean doAngle = (doAngleFlag == 1);
        if (mostAngleFlag == null) {
            mostAngleFlag = 1;
        }
        boolean mostAngle = (mostAngleFlag == 1);

        OcrEngine ocrEngine = new OcrEngine(library);
        ocrEngine.setNumThread(numThread);
        ocrEngine.initLogger(
                false,
                false,
                false
        );
        ocrEngine.enableResultText(imagePath);

        if (gpuIndex != null) {
            ocrEngine.setGpuIndex(gpuIndex);
        }
        if (!ocrEngine.initModels(modelsDir, detName, clsName, recName, keysName)) {
            throw new RuntimeException("模型初始化错误，请检查models/keys路径！");
        }
        ocrEngine.setPadding(padding);
        ocrEngine.setBoxScoreThresh(boxScoreThresh);
        ocrEngine.setBoxThresh(boxThresh);
        ocrEngine.setUnClipRatio(unClipRatio);
        ocrEngine.setDoAngle(doAngle);
        ocrEngine.setMostAngle(mostAngle);

        System.out.println("Version=" + ocrEngine.getVersion());
        OcrResult ocrResult = ocrEngine.detect(imagePath, maxSideLen);

        //使用native方法，可以让OcrEngine成为单例
//        OcrResult ocrResult = ocrEngine.detect(imagePath, padding, maxSideLen, boxScoreThresh, boxThresh, unClipRatio, doAngle, mostAngle);

        return ocrResult;
    }
}
