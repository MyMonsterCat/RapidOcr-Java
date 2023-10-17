# RapidOcr-Java

## 🪧 项目起源

- 现有的JavaOCR工具包识别效果差强人意，PaddleOCR在实现前沿算法的基础上，考虑精度与速度的平衡，进行模型瘦身和深度优化，使其尽可能满足产业落地需求。

- PaddleOCR官方并未提供Java版本，而[RapidOcr](https://github.com/RapidAI/RapidOCR)解决了这个问题，其提供了Kotlin和Java混合版本的[Demo-onnx](https://github.com/RapidAI/RapidOcrOnnxJvm)和[Demo-ncnn](https://github.com/RapidAI/RapidOcrNcnnJvm)

  >RapidOcr是完全开源、免费并支持多平台、多语言OCR离线部署

- 而实际使用过程中，项目中并不想再引入Kotlin，因此本项目将Kotlin部分移除，并在此基础上加以完善，使调用者方便使用

## 👏 项目特点

- 纯Java代码调用RapidOcr
- 使用ncnn和onnx推理引擎方式，并编写了简单工具类，默认使用Onnx推理方式

> ⚠️ 注意：当前JVM启动时只能同时启动一种推理引擎


## 🎉 快速开始

1⃣️ 引入jar包(需要自己下载项目打包，过程略)

```xml
<dependency>
    <groupId>com.github.monster</groupId>
    <artifactId>RapidOcr-Java</artifactId>
    <version>latest.version</version>
</dependency>
```

2⃣️ 调用

```java
public class OcrUtilTest {

    @Test
    public void NcnnTest() {
        // 使用NCNN引擎进行识别
        OcrResult NCNNResult = OcrUtil.runOcr("images/40.png", LibConfig.getNcnnConfig());
        Assert.assertEquals("40",NCNNResult.getStrRes().trim().toString());
    }

    @Test
    public void OnnxTest() {
        // 使用ONNX推理引擎进行识别
        OcrResult ONNXResult = OcrUtil.runOcr("images/40.png", LibConfig.getOnnxConfig());
        Assert.assertEquals("40",ONNXResult.getStrRes().trim().toString());
    }

    @Test
    public void paramTest() {
        // 配置参数
        ParamConfig paramConfig = new ParamConfig();
        paramConfig.setDoAngle(true);
        paramConfig.setMostAngle(true);
        // 开始识别
        OcrResult ocrResult = OcrUtil.runOcr("images/1.jpg", LibConfig.getNcnnConfig(), paramConfig);
        System.out.println(ocrResult);
    }

    @Test
    public void hardWareTest() {
        // 配置可变参数
        ParamConfig paramConfig = new ParamConfig();
        paramConfig.setDoAngle(true);
        paramConfig.setMostAngle(true);
        // 配置硬件参数：4核CPU，使用GPU0
        HardwareConfig hardwareConfig = new HardwareConfig(4, 0);
        // 开始识别
        OcrResult ocrResult = OcrUtil.runOcr("images/1.jpg", LibConfig.getNcnnConfig(), paramConfig, hardwareConfig);
        System.out.println(ocrResult);
    }

    @Test
    public void repeatOcr() {
        String real = "40";
        System.out.println("NCNN 1>>>>>>>> ");
        OcrResult NCNN_1 = OcrUtil.runOcr("images/40.png", LibConfig.getNcnnConfig());
        Assert.assertEquals(real,NCNN_1.getStrRes().trim().toString());

        System.out.println("NCNN 2>>>>>>>> ");
        OcrResult NCNN_2 = OcrUtil.runOcr("images/40.png");
        Assert.assertEquals(real,NCNN_2.getStrRes().trim().toString());

        System.out.println("NCNN 3>>>>>>>> ");
        OcrResult NCNN_3 = OcrUtil.runOcr("images/40.png");
        Assert.assertEquals(real,NCNN_3.getStrRes().trim().toString());

        System.out.println("NCNN 4>>>>>>>> ");
        OcrResult NCNN_4 = OcrUtil.runOcr("images/40.png");
        Assert.assertEquals(real,NCNN_4.getStrRes().trim().toString());

        System.out.println("NCNN 5>>>>>>>> ");
        OcrResult NCNN_5 = OcrUtil.runOcr("images/40.png");
        Assert.assertEquals(real,NCNN_5.getStrRes().trim().toString());
    }
}
```

## 🔝 OcrConfig参数调优

**LibConfig**参数说明如下：

|    参数名    |                  说明                   |  类型   | 权限 |
| :----------: | :-------------------------------------: | :-----: | :--: |
|  libraryDir  |             动态链接库路径              | String  | 读写 |
|  modelsDir   |                模型路径                 | String  | 读写 |
| deleteOnExit | 是否在JVM退出时删除动态链接库和模型文件 | boolean | 读写 |
|   detName    |               Det文件名称               | String  | 只读 |
|   clsName    |               Cls文件名称               | String  | 只读 |
|   recName    |               Rec文件名称               | String  | 只读 |
|   keysName   |                  词单                   | String  | 只读 |

**HardWareConfig**参数说明如下：

|  参数名   |                             说明                             | 类型 | 权限 |
| :-------: | :----------------------------------------------------------: | :--: | :--: |
| numThread |                         CPU 核心数量                         | int  | 读写 |
| gpuIndex  | GPU0一般为默认GPU，参数选项：使用CPU(-1)/使用GPU0(0)/使用GPU1(1)/...，默认0 | int  | 读写 |

> ⚠️ ONNX不使用GPU，即使用-1

**ParamConfig**参数说明如下：

|     参数名     |                             说明                             |  类型   | 权限 |
| :------------: | :----------------------------------------------------------: | :-----: | :--: |
|    padding     | 图像外接白框，用于提升识别率，文字框没有正确框住所有文字时，增加此值。默认50。 |   int   | 读写 |
|   maxSideLen   | 按图像长边进行总体缩放，放大增加识别耗时但精度更高，缩小减小耗时但精度降低，maxSideLen为0表示不缩放 |   int   | 读写 |
| boxScoreThresh | 文字框置信度门限，文字框没有正确框住所有文字时，减小此值，默认0.5f |  float  | 读写 |
|   boxThresh    |               值越大，文字部分会越小，默认0.3f               |  float  | 读写 |
|  unClipRatio   |      单个文字框大小倍率，越大时单个文字框越大，默认1.6f      |  float  | 读写 |
|  doAngleFlag   | 启用(1)/禁用(0) 文字方向检测，只有图片倒置的情况下(旋转90~270度的图片)，才需要启用文字方向检测，默认关闭 | boolean | 读写 |
| mostAngleFlag  | 启用(1)/禁用(0) 角度投票(整张图片以最大可能文字方向来识别)，当禁用文字方向检测时，此项也不起作用，默认关闭 | boolean | 读写 |

> ✍️ 想更深入了解，请移步[config.yaml参数解释](https://rapidai.github.io/RapidOCRDocs/docs/blog/config_parameter/)

## 🗣️ 目录说明

### src目录

- com.benjaminwan.ocrlibrary: 核心代码，用来与dll、so、dylib进行交互。‼️ 千万不要改包名！！！
- com.github.monster.ocr.OcrUtil: 调用核心代码工具类

## ⚠️ 版本说明

当前使用版本如下

### ncnn

[自行更新](https://github.com/RapidAI/RapidOcrNcnn)

- Mac-Arm64: `v1.2.0`

- Mac-x86_64、Linux、Win：`v1.1.2`
- win7可能存在问题，未经测试

### onnx

[自行更新](https://github.com/RapidAI/RapidOcrOnnx)

- Mac-Arm64: `暂不支持`

- Mac-x86_64、Linux、Win：`v1.2.2`
- win7可能存在问题，未经测试

## 📌 TODO

- [x] 根据系统版本自适应加载动态库
- [x] 动态库集成到jar中
- [x] 是否删除临时文件夹配置为可选项
- [x] jvm未退出场景连续调用识别结果集乱码[#1](https://github.com/MyMonsterCat/RapidOcr-Java/issues/1)
- [x] 集成ONNX[#2](https://github.com/MyMonsterCat/RapidOcr-Java/issues/2)
- [ ] 添加日志，规范日志打印
- [ ] ONNX支持Mac-Arm64
- [ ] 同时加载多个引擎，当前JVM启动时只能同时启动一种推理引擎

## 鸣谢

- [RapidOCR](https://github.com/RapidAI/RapidOCR)
- [PaddleOCR](https://github.com/PaddlePaddle/PaddleOCR)

## 开源许可

使用 [Apache License 2.0](https://github.com/MyMonsterCat/DeviceTouch/blob/main/LICENSE)

Copyright © The bingosam Project. All Rights Reserved.
