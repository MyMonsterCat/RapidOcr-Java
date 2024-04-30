# Build Rapid OCR Onnx Lib on Kylin(arm64 )

### 原始环境信息

操作系统: 银河麒麟 V10(SP1)

CPU： Phytium,D2000/8 E8C    (指令集: arm64)

内存:  8G



gcc 版本：9.3

cmake 版本：3.16.1

缺少 g++



### 编译要求

gcc > 7

g++ > 7

cmake > 3.17





### 环境准备

如果没有银河麒麟麒麟的源，需要配一下

源地址：http://archive.kylinos.cn/kylin/KYLIN-ALL/

```sh          
在系统的/etc/apt/sources.list文件中，根据不同版本填入以下内容

#4.0.2桌面版本:
deb http://archive.kylinos.cn/kylin/KYLIN-ALL 4.0.2-desktop main restricted universe multiverse

#4.0.2-sp1桌面版本:
deb http://archive.kylinos.cn/kylin/KYLIN-ALL 4.0.2sp1-desktop main restricted universe multiverse

#4.0.2-sp2桌面版本:
deb http://archive.kylinos.cn/kylin/KYLIN-ALL 4.0.2sp2-desktop main restricted universe multiverse

#4.0.2服务器版本:
deb http://archive.kylinos.cn/kylin/KYLIN-ALL 4.0.2-server main restricted universe multiverse

#4.0.2-sp1服务器版本:
deb http://archive.kylinos.cn/kylin/KYLIN-ALL 4.0.2sp1-server main restricted universe multiverse

#4.0.2-sp2服务器版本:
deb http://archive.kylinos.cn/kylin/KYLIN-ALL 4.0.2sp2-server main restricted universe multiverse

#4.0.2-sp2 FT2000+服务器版本:
deb http://archive.kylinos.cn/kylin/KYLIN-ALL 4.0.2sp2-server-ft2000 main restricted universe multiverse

#4.0.2-sp3版本:
deb http://archive.kylinos.cn/kylin/KYLIN-ALL 4.0.2sp3 main restricted universe multiverse

#4.0.2-sp4版本:
deb http://archive.kylinos.cn/kylin/KYLIN-ALL 4.0.2sp4 main restricted universe multiverse

#V10版本:
deb http://archive.kylinos.cn/kylin/KYLIN-ALL 10.0 main restricted universe multiverse

#V10.1版本:
deb http://archive.kylinos.cn/kylin/KYLIN-ALL 10.1 main restricted universe multiverse

```





#### 使用 apt install 下载 deb 包

也可以直接 install，下载的目的是方便以后可以把需要的包离线传输到内网机器安装

```sh          
sudo apt install --download-only <package>

# 下载后的包缓存在在 /var/cache/apt/packages 目录下
```



#### 下载并安装 gcc

下载

```sh          
sudo apt install --download-only  gcc

#进入 /var/cache/apt/packages
cd /var/cache/apt/packages

```



下载到的包

```sh
#下载到以下包(不同机器拿到的包应该不一样)
ls
gcc_4%3a9.3.0-11.185.1kylin2k7.5_arm64.deb
```



安装

```sh
#可以复制到其他位置备用
sudo cp *.deb /home/some/pkgs/gcc

#安装 deb 包
sudo dpkg -i *.deb

#安装完成后清一下 /var/cache/apt/packages
sudo rm -rf .  #小心一些，确保当前目录是 /var/cache/apt/packages

#查看 gcc 版本
gcc --version
```







#### 下载并安装 g++

```sh          
sudo apt install --download-only  g++


#进入 /var/cache/apt/packages
cd /var/cache/apt/packages

#下载到以下包(不同机器拿到的包应该不一样)
ls
g++_4%3a9.3.0-11.185.1kylin2k7.5_arm64.deb
g++-9_9.3.0-10kylin2_arm64.deb
libstdc++-9-dev_9.3.0-10kylin2_arm64.deb

#可以复制到其他位置备用
sudo cp *.deb /home/some/pkgs/g++

#安装 deb 包
sudo dpkg -i *.deb

#安装完成后清一下 /var/cache/apt/packages
sudo rm -rf .  #小心一些，确保当前目录是 /var/cache/apt/packages

#查看 g++ 版本
g++ --version
```



g++ 包

```sh
g++_4%3a9.3.0-11.185.1kylin2k7.5_arm64.deb
g++-9_9.3.0-10kylin2_arm64.deb
libstdc++-9-dev_9.3.0-10kylin2_arm64.deb
```



#### 下载并安装 cmake

使用 apt install 装的 cmake 最高版本是 3.16 不符合要求

从 cmake 官网下载 https://cmake.org/download/  找到 Linux aarch64 版本下载 tar.gz 包

```sh          
# 将 tar.gz 包复制到用户目录下
# 解压 cmake-3.29.2-linux-aarch64.tar.gz 例如释放到：/home/some/soft/cmake

vim ~/.bashrc
# 在最后加上：
export PATH=$PATH:/home/some/soft/cmake/bin

source ~/.bashrc

which cmake

#显示 cmake 的版本
cmake -version 
```



#### 下载并配置 JVM

jvm 是用来构建 jni 的时候需要的，去 oracle 官网下载 aarch64 版本的压缩包即可。



配置环境变量

```sh
# 将 jdk 解压缩到指定目录,例如 /home/some/soft/jdk
# 在最后加上：
export JAVA_HOME=/home/some/soft/jdk  # JAVA_HOME 一定要设,否则后面编译 JNI 时会找不到
export PATH=$PATH:$JAVA_HOME/bin

source ~/.bashrc


#验证 jdk
java -version
```









### 下载源码并构建

根据 https://github.com/RapidAI/RapidOcrOnnx/blob/main/BUILD.md 编译说明开始编译

下面内容和原文基本符合，着重说明实际银河麒麟 kylin linux 的编译过程。



#### 下载源码

项目地址：https://github.com/RapidAI/RapidOcrOnnx/，下载源码并复制到目标机器上



#### 依赖的第三方库下载

下载opencv，[下载地址](https://github.com/RapidAI/OpenCVBuilder/releases)

- OpenCV静态库：opencv-(版本号)-平台.7z

```sh
opencv-static
├── OpenCVWrapperConfig.cmake
├── linux
```

实际编译下载版本为：opencv-4.8.1-ubuntu-18.04-arm64-java.7z



下载onnxruntime，[下载地址](https://github.com/RapidAI/OnnxruntimeBuilder/releases)

- static为静态库：onnxruntime-(版本号)-平台-static.7z
- shared为动态库：onnxruntime-(版本号)-平台-shared.7z
- 一般情况下使用静态库即可

```
onnxruntime-static
├── OnnxRuntimeWrapper.cmake
├── linux

```

实际编译下载版本为：onnxruntime-v1.15.1-ubuntu-18.04-arm64-static.7z



#### 编译构建

g++>=5，cmake>=3.17[下载地址](https://cmake.org/download/)

* 终端打开项目根目录，`./build.sh`并按照提示输入选项，**最后选择'BIN可执行文件'**



**注意!!!  直接执行很可能会出现编译错误：**

```
fatal error: onnxruntime.core.session.onnxruntime_cxx_api.h 没有那个文件或目录
#include <onnxruntime.core.session.onnxruntime_cxx_api.h>
```



受影响的文件有 include 目录下的文件: `AngleNet.h`  `CrnnNet.h`  `DbNet.h`  `OcrLite.h` `OcrUtils.h`

```c++
// 将下面的代码
#include <onnxruntime/core/session/onnxruntime_cxx_api.h>
//改为
#include <onnxruntime/onnxruntime_cxx_api.h> // rapidOcr 作者建议的版本(暂时没验证)

#include <onnxruntime_cxx_api.h>  // kylin 编译时实际修改的版本
```



这个问题在编译 MacOS 版本的动态库时也会遇到，参考：https://github.com/MyMonsterCat/RapidOcr-Java/blob/main/docs/COMPILE_LIB.md



* 测试：`./run-test.sh`(注意修改脚本内的目标图片路径)

* 编译JNI动态运行库(可选，可用于java调用)

- 下载jdk-8u221并安装配置
- 运行`build.sh`并按照提示输入选项，**最后选择'JNI动态库'**
- **注意：编译JNI时，g++版本要求>=6**



### 参考链接

https://github.com/RapidAI/RapidOCR

https://github.com/RapidAI/RapidOcrOnnx

https://github.com/MyMonsterCat/RapidOcr-Java

https://onnxruntime.ai/

https://github.com/RapidAI/RapidOcrOnnx/blob/main/BUILD.md