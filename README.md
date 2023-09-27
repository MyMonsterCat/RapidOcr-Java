# RapidOcr-Java

## 🪧 项目起源

[RapidOcr](https://github.com/RapidAI/RapidOCR)提供了Kotlin和Java混合版本的[Demo-onnx](https://github.com/RapidAI/RapidOcrOnnxJvm)和[Demo-ncnn](https://github.com/RapidAI/RapidOcrNcnnJvm)，而实际使用过程中，项目中并不想再引入Kotlin。

> RapidOcr是完全开源、免费并支持多平台、多语言OCR离线部署

## 👏 项目特点

- 纯Java代码调用RapidOcr
- 集成ncnn和onnx，并编写了简单工具类

## 🎉 快速开始

1⃣️ 引入jar包(需要自己下载项目打包，过程略)

```xml
<dependency>
    <groupId>com.github.monster</groupId>
    <artifactId>RapidOcr-Java</artifactId>
    <version>0.0.1</version>
</dependency>
```

2⃣️ 调用

```java
  public static void main(String[] args) {
      OcrResult ocrResult = OcrUtil.NcnnOcr("images/40.png");
      System.out.println(ocrResult);
  }
```

## 🗣️ 目录说明

### Resources目录

分别存放RapidOcr使用[ncnn](https://github.com/Tencent/ncnn)和onnx编译后的结果

> 想要更新ncnn请[前往](https://github.com/RapidAI/RapidOcrNcnn/releases)，想要更新onnx请[前往](https://github.com/RapidAI/RapidOcrOnnx/releases)

- models: 模型文件

- libRapidOcrNcnn-silicon.dylib: 使用Mac系统 **M系列架构**编译
- libRapidOcrNcnn-intel.dylib: 使用Mac系统 **Intel架构**编译
- libRapidOcrNcnn.so: 使用Linux系统编译
- RapidOcrNcnn.dll: 使用Windows系统编译

### src目录

- com.benjaminwan.ocrlibrary: 核心代码，用来与dll、so、dylib进行交互。‼️ 千万不要改包名！！！
- com.github.monster.OcrUtil: 调用核心代码工具类

## 🔝 Ocr参数调优

详细参数说明如下：

```java
  /**
   * @param library        动态链接库绝对路径
   * @param modelsDir      模型路径
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
public static OcrResult Ocr(String library, String modelsDir, String detName, String clsName, String recName, String keysName, String imagePath,Integer numThread, Integer padding, Integer maxSideLen, Float boxScoreThresh, Float boxThresh,Float unClipRatio, Integer doAngleFlag, Integer mostAngleFlag, Integer gpuIndex) 
```

## ⚠️ 版本说明

当前使用版本如下

### ncnn

- libRapidOcrNcnn-silicon.dylib: v1.2.0
- libRapidOcrNcnn-intel.dylib: 1.1.2
- libRapidOcrNcnn.so: 1.1.2
- RapidOcrNcnn.dll: 1.1.2

### onnx

- libRapidOcrOnnx-intel.dylib: 1.2.2
- libRapidOcrOnnx.so: 1.2.2
- RapidOcrOnnx.dll: 1.2.2

## 📌 TODO

- [x] 根据系统版本自适应加载动态库
- [ ] 根据系统版本自适应使用配置文件
- [x] 动态库集成到jar中，并能方便使用
- [ ] 集成onnx
- [ ] 将每次都copy到临时文件夹改为只copy一次
- [ ] ...

## 鸣谢

- [RapidOCR](https://github.com/RapidAI/RapidOCR)
- [PaddleOCR](https://github.com/PaddlePaddle/PaddleOCR)

## 开源许可

使用 [Apache License 2.0](https://github.com/MyMonsterCat/DeviceTouch/blob/main/LICENSE)

Copyright © The bingosam Project. All Rights Reserved.
