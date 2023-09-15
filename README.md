# RapidOcr-Java

## 项目起源

RapidOcr提供了Kotlin和Java混合版本的[Demo-onnx](https://github.com/RapidAI/RapidOcrOnnxJvm)和[Demo-ncnn](https://github.com/RapidAI/RapidOcrNcnnJvm)，而实际使用过程中，项目中并不想再引入Kotlin。

## 项目特点

- 纯Java代码调用RapidOcr
- 集成ncnn和onnx，并编写了简单工具类

## 快速测试

1⃣️ 下载源代码

```shell
git clone https://github.com/RapidAI/RapidOcrOnnxJvm.git
```

2⃣️ 运行测试类

![](https://monster-note.oss-cn-hangzhou.aliyuncs.com/img/sql/mysql/202309151824613.png)

运行成功如下

![](https://monster-note.oss-cn-hangzhou.aliyuncs.com/img/sql/mysql/202309151615945.png)

## 目录说明

### ncnn目录

这里面存放RapidOcr使用[ncnn](https://github.com/Tencent/ncnn)编译后的结果，

- models: 模型文件，想要更新请[前往下载相应版本的文件](https://github.com/RapidAI/RapidOcrNcnn/releases)

- libRapidOcrNcnn.dylib: 使用Mac系统 **M1架构**编译
- libRapidOcrNcnn-intel.dylib: 使用Mac系统 **Intel架构**编译，‼️ 如果使用其进行测试，请将其重命名为`libRapidOcrNcnn.dylib`
- libRapidOcrNcnn.so: 使用Linux系统编译
- RapidOcrNcnn.dll: 使用Windows系统编译

### onnx目录

这里面存放RapidOcr使用onnx编译后的结果，

- models: 模型文件，想要更新请[前往下载相应版本的文件](https://github.com/RapidAI/RapidOcrOnnx/releases)

- libRapidOcrOnnx.dylib: 使用Mac系统 **M1架构**编译
- libRapidOcrOnnx-intel.dylib: 使用Mac系统 **Intel架构**编译，‼️ 如果使用其进行测试，请将其重命名为`libRapidOcrOnnx`
- libRapidOcrOnnx.so: 使用Linux系统编译
- RapidOcrOnnx.dll: 使用Windows系统编译

### src目录

- com.benjaminwan.ocrlibrary: 核心代码，用来与dll、so、dylib进行交互。‼️ 千万不要改包名！！！
- com.github.monster.OcrUtil: 调用核心代码工具类

## RapidOcr升级

方式一：按照目录说明中，自行下载相应版本jni

方式二：前往[整合后的jni库](https://github.com/MyMonsterCat/Model-DLL)，找到相应版本下载

## OcrUtil使用说明

> ⚠️ 如果要使用OcrUtil，请确保ncc目录和onnx目录严格按照下面格式（以ncxx目录为例）
>
> ncnn
>
> - models
>   - ch_PP-OCRv3_det_infer.onnx/ch_PP-OCRv3_det_infer.bin
>   - 其他文件此处省略...
>   - ...
> - xxx.dylib
> - xxx.so
> - Xxx.dll

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

## Maven打包引入其他项目

1⃣️ Maven打包

![](https://monster-note.oss-cn-hangzhou.aliyuncs.com/img/sql/mysql/202309151651157.png)

2⃣️ Maven坐标引入

```xml
<dependency>
    <groupId>com.github.monster</groupId>
    <artifactId>RapidOcr-Java</artifactId>
    <version>0.0.1</version>
</dependency>
```

3⃣️ 引入jni文件

按照OcrUtil使用说明中要求的格式，放入工程

![](https://monster-note.oss-cn-hangzhou.aliyuncs.com/img/sql/mysql/202309151819190.png)

4⃣️ 调用

```java
        // 获取jni的绝对路径
        String dirPath = System.getProperty("user.dir") +  "/ncnn";
        // 调用
        // Mac调用示例
         OcrResult ocrResult = OcrUtil.NcnnOcr(dirPath + "/libRapidOcrNcnn.dylib", dirPath + "/models", "images/1.jpg");
        // windows调用
				//  OcrResult ocrResult = OcrUtil.NcnnOcr(dirPath + "/RapidOcrNcnn.dll", "ncnn/models", "images/1.jpg");
        System.out.println(ocrResult);
```

5⃣️ 识别效果与时间

![](https://monster-note.oss-cn-hangzhou.aliyuncs.com/img/sql/mysql/202309151825366.png)

## 鸣谢

- [RapidOCR](https://github.com/RapidAI/RapidOCR)
- [PaddleOCR](https://github.com/PaddlePaddle/PaddleOCR)

## 开源许可

使用 [Apache License 2.0](https://github.com/MyMonsterCat/DeviceTouch/blob/main/LICENSE)

Copyright © The bingosam Project. All Rights Reserved.
