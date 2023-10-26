# 进阶使用

## :hourglass_flowing_sand: OcrConfig参数调优

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

- com.benjaminwan.ocrlibrary: 核心代码，用来与dll、so、dylib进行交互。‼️ 千万不要改包名！！！
- com.github.monster.ocr
  - config
    - HardwareConfig: 硬件配置类
    - LibConfig: 库文件配置类
    - ParamConfig: 可调节参数配置

  - JarFileUtils: 从jar包中加载动态库
  - PathConstants: 模型/文件路径常量
  - OcrUtil: 调用核心代码工具类


## ⚠️ 版本说明

当前使用版本如下（此处的版本指的是[RapidOcrNcnn](https://github.com/RapidAI/RapidOcrNcnn)或者[RapidOcrOnnx](https://github.com/RapidAI/RapidOcrOnnx)的版本）

### ncnn：仅支持PP-OCRv3

- Mac-Arm64: `v1.2.0`

- Mac-x86_64、Linux、Win：`v1.1.2`
- win7可能存在问题，未经测试

> 如何更新模型？
>
> - 方式一：前往[自行更新地址](https://github.com/RapidAI/RapidOcrNcnn)，在release下载模型
> - 方式二：按照编译说明自行[编译](./COMPILE_LIB.md)

### onnx：支持[PP-OCRv4](https://github.com/PaddlePaddle/PaddleOCR/blob/release/2.7/doc/doc_ch/PP-OCRv4_introduction.md)和PP-OCRv3

- Mac-Arm64: `v1.2.2`

- Mac-x86_64、Linux、Win：`v1.2.2`
- win7可能存在问题，未经测试

> 如何更新模型？
>
> - 方式一：前往[自行更新地址](https://github.com/RapidAI/RapidOcrOnnx)，在release下载最新的模型
> - 方式二：按照编译说明自行[编译](./COMPILE_LIB.md)

## :saxophone: 分支说明

- main: 主分支，使用ncnn和onnx进行推理
- dev-ncnn： 仅使用ncnn进行推理
