import cn.hutool.core.io.IoUtil;
import com.benjaminwan.ocrlibrary.OcrInput;
import com.benjaminwan.ocrlibrary.OcrResult;
import io.github.mymonstercat.Model;
import io.github.mymonstercat.ocr.InferenceEngine;
import io.github.mymonstercat.ocr.config.ParamConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

class TestOnnxWindow {


    @Test
    void testBytesInput() {
        ParamConfig paramConfig = ParamConfig.getDefaultConfig();
        paramConfig.setDoAngle(true);
        paramConfig.setMostAngle(true);
        InferenceEngine engine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V3);
        byte[] data = IoUtil.readBytes(Thread.currentThread().getContextClassLoader().getResourceAsStream("tool.png"));
        OcrInput input = new OcrInput(data);
        OcrResult ocrResult = engine.runOcr(input, paramConfig);
        Assertions.assertFalse(ocrResult.getTextBlocks().isEmpty());
        System.out.println(ocrResult.getStrRes().trim());
    }

    /**
     * 测试argb格式bitmap数据
     */
    @Test
    void testBitmapInput() throws AWTException, IOException {
        Robot robot = new Robot();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        BufferedImage screenShot = robot.createScreenCapture(screenRectangle);
        BufferedImage rgbaImage = new BufferedImage(screenShot.getWidth(), screenShot.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rgbaImage.createGraphics();
        g2d.drawImage(screenShot, 0, 0, null);
        g2d.dispose();
        byte[] rgbaBytes = new byte[rgbaImage.getWidth() * rgbaImage.getHeight() * 4];
        int[] pixels = rgbaImage.getRGB(0, 0, rgbaImage.getWidth(), rgbaImage.getHeight(), null, 0, rgbaImage.getWidth());
        ByteBuffer.wrap(rgbaBytes).asIntBuffer().put(pixels);

        ParamConfig paramConfig = ParamConfig.getDefaultConfig();
        paramConfig.setDoAngle(true);
        paramConfig.setMostAngle(true);
        InferenceEngine engine = InferenceEngine.getInstance(Model.ONNX_PPOCR_V3);

        OcrInput input = new OcrInput(rgbaBytes, 4, screenShot.getWidth(), screenShot.getHeight());
        OcrResult ocrResult = engine.runOcr(input, paramConfig);
        Assertions.assertFalse(ocrResult.getTextBlocks().isEmpty());
        System.out.println(ocrResult.getStrRes().trim());
    }
}
