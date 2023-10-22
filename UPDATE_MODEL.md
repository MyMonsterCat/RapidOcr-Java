# 如何进行模型更新



1.前往[官方模型地址](https://github.com/PaddlePaddle/PaddleOCR/blob/release/2.7/doc/doc_ch/models_list.md)选择想转换的模型

2.根据[Rapid官方说明文档](https://rapidai.github.io/RapidOCRDocs/docs/about_model/convert_model/)

- 前往[魔搭](https://www.modelscope.cn/studios/liekkas/PaddleOCRModelConverter/summary)
- 输入模型地址
- 输入txt地址
- 点击covert
- 将该模型放入`src\main\resources\onnx\models`
- 修改`PathConstants.MODEL_REC_NAME`为你模型的名称