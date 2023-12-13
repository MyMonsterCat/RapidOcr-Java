# 如何打包 jar 在Linux系统上运行



**本地开发环境：**`Windos11--x86_64`

问题：项目B引入`rapidocr-onnx-platform`，将项目B打包后，**jar放到CentOS上无法运行**，报错：文件 lib/libRapidOcr.xxx 在JAR中未找到

原因：本项目根据系统自动引入对应平台的依赖，也就是项目B最终依赖的是`rapidocr-onnx-windows-x86_64`，因此在windows上打包也是基于该依赖，自然在linux系统下无法运行windows的依赖库。

**解决方案1：**

1.项目B的pom添加profile

```xml
    <profiles>
        <profile>
            <id>linux-x86_64</id>
            <activation>
                <os>
                    <family>unix</family>
                    <arch>amd64</arch>
                </os>
            </activation>
            <dependencies>
                <dependency>
                    <groupId>io.github.mymonstercat</groupId>
                  	<!--     替换成你需要打包的对应平台      -->
                    <artifactId>rapidocr-onnx-linux-x86_64</artifactId>
                  	<!--     版本请使用最新      -->
                    <version>x.x.x</version>
                </dependency>
            </dependencies>
        </profile>
    </profiles>
```

2.打包命令

```shell
# 下面命令中linux-x86_64对应上面pom文件中id标签
mvn clean package -P linux-x86_64 -Dlinux-build
```

> [Demo](https://github.com/MyMonsterCat/rapidocr-demo)中已集成该功能，请自行查看

**解决方案2：**

项目pom中自行添加相应依赖，例如

```xml
<dependencies>
    <dependency>
        <groupId>io.github.mymonstercat</groupId>
        <artifactId>rapidocr</artifactId>
        <version>x.x.x</version>
    </dependency>
  
    <dependency>
        <groupId>io.github.mymonstercat</groupId>
        <artifactId>rapidocr-onnx-linux-x86_64</artifactId>
         <version>x.x.x</version>
    </dependency>
</dependencies>
```

