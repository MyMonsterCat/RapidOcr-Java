# 进阶使用

## :hourglass_flowing_sand: OcrConfig参数调优

**HardWareConfig**参数说明如下：

|  参数名   |                             说明                             | 类型 | 权限 |
| :-------: | :----------------------------------------------------------: | :--: | :--: |
| numThread |                         CPU 核心数量                         | int  | 读写 |
| gpuIndex  | GPU0一般为默认GPU，参数选项：使用CPU(-1)/使用GPU0(0)/使用GPU1(1)/...，默认0 | int  | 读写 |

> ⚠️ ONNX不使用GPU，填写-1

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

> ✍️ 想更深入了解，请移步[config.yaml参数解释](https://rapidai.github.io/RapidOCRDocs/docs/blog/02_config_parameter/)


## ⚠️ 版本说明

|    系统架构    | 本项目版本 |        Onnx        |        Ncnn        |
| :------------: | :--------: | :----------------: | :----------------: |
|   mac-arm64    |   0.0.5    | 支持，版本`v1.2.2` | 支持，版本`v1.2.0` |
|   mac-x86_64   |   0.0.5    | 支持，版本`v1.2.2` | 支持，版本`v1.1.2` |
|  linux-x86_64  |   0.0.5    | 支持，版本`v1.2.2` | 支持，版本`v1.1.2` |
|   linux-x86    |     /      |       不支持       |       不支持       |
|  linux-arm64   |     /      |       不支持       |       不支持       |
| windows-x86_64 |   0.0.5    | 支持，版本`v1.2.2` | 支持，版本`v1.1.2` |
|  windows-x86   |   0.0.5    | 支持，版本`v1.2.2` |       不支持       |

> - onnx和ncnn的版本指的是[RapidOcrNcnn](https://github.com/RapidAI/RapidOcrNcnn)或者[RapidOcrOnnx](https://github.com/RapidAI/RapidOcrOnnx)的版本
> - win7可能存在问题，未经测试
> - 不支持我的系统？
>   - 请参考README中的FAQ
>   - 如果您成功编译了相应平台的库文件，希望您能提供issue供更多人使用

|        /        |   [RapidOcrNcnn](https://github.com/RapidAI/RapidOcrNcnn)    |   [RapidOcrOnnx](https://github.com/RapidAI/RapidOcrOnnx)    |
| :-------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| PP-OCR支持版本  |                        仅支持PP-OCRv3                        | 支持[PP-OCRv4](https://github.com/PaddlePaddle/PaddleOCR/blob/release/2.7/doc/doc_ch/PP-OCRv4_introduction.md)和PP-OCRv3 |
| 更新模型-方式一 | 前往[自行更新地址](https://github.com/RapidAI/RapidOcrNcnn)，在release下载模型 | 前往[自行更新地址](https://github.com/RapidAI/RapidOcrOnnx)，在release下载最新的模型 |
| 更新模型-方式二 |                 自行[编译](./COMPILE_LIB.md)                 |                 自行[编译](./COMPILE_LIB.md)                 |

## :saxophone: 分支说明

- main: 主分支，多模块打包
- light-0.0.4: 旧版本
- dev: 开发分支
