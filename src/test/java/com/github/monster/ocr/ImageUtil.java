package com.github.monster.ocr;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.lang.Assert;
import com.benjaminwan.ocrlibrary.Point;
import com.benjaminwan.ocrlibrary.TextBlock;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;


/**
 * 图片工具类
 * 参考自 <a href="https://github.com/nn200433/tika-server/blob/main/tika-server-paddle-ocr/src/main/java/cn/nn200433/tika/parser/ocr/paddle/utils/ImageUtil.java">ImageUtil</a>
 */
@Slf4j
public class ImageUtil extends ImgUtil {


    /**
     * 绘制图片
     * <p>
     * 根据传入的坐标点绘制矩形框，并在矩形框底下添加文字
     * </p>
     *
     * @param stream    数据流
     * @param blockList 文本块列表
     * @return {@link OutputStream }
     * @author song_jx
     */
    public static ByteArrayOutputStream drawImg(InputStream stream, List<TextBlock> blockList) {
        // 读取输入流中的图片
        BufferedImage image = read(stream);

        // 开启画笔绘制
        Graphics2D g2d = image.createGraphics();
        for (final TextBlock textBlock : blockList) {
            final List<Point> boxPoint = textBlock.getBoxPoint();
            final String      text     = textBlock.getText();
            // 1. 算出矩形框
            final Rectangle box    = calcRectangle(boxPoint);
            final int       x      = box.x;
            final int       y      = box.y;
            final int       width  = box.width;
            final int       height = box.height;
            // 2. 绘制矩形框
            g2d.setColor(Color.RED);
            g2d.drawRect(x, y, width, height);
            // 3. 在矩形框的左下角添加文字
            g2d.setColor(Color.BLACK);
            g2d.drawString(text, x, y + height + 15);
        }
        g2d.dispose();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        write(image, IMAGE_TYPE_PNG, os);
        return os;
    }

    /**
     * 绘制图片
     * <p>
     * 根据传入的坐标点绘制矩形框，并在矩形框底下添加文字
     * </p>
     *
     * @param imageFile 图片文件
     * @param blockList 文本块列表
     * @return {@link OutputStream }
     * @author song_jx
     */
    public static ByteArrayOutputStream drawImg(File imageFile, List<TextBlock> blockList) {
        ByteArrayOutputStream os = null;
        try (InputStream is = new FileInputStream(imageFile)) {
            os = drawImg(is, blockList);
        } catch (Exception e) {
            log.error("图片绘制异常", e);
        }
        return os;
    }

    /**
     * 计算矩形框
     *
     * <p>根据传入的4个坐标点，得出矩形框的左上角及长宽</p>
     *
     * @param pointList 点列表
     * @return {@link Rectangle }
     * @author song_jx
     */
    private static Rectangle calcRectangle(List<Point> pointList) {
        Assert.isFalse(CollUtil.isEmpty(pointList) || pointList.size() != 4, "需要4个点来构成矩形");
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        // 找到最小和最大的 x、y 坐标
        for (Point point : pointList) {
            final int x = point.getX();
            final int y = point.getY();
            if (x < minX) {
                minX = x;
            }
            if (y < minY) {
                minY = y;
            }
            if (x > maxX) {
                maxX = x;
            }
            if (y > maxY) {
                maxY = y;
            }
        }
        // 矩形的左上角坐标即是最小x、y
        final int width  = maxX - minX;
        final int height = maxY - minY;
        return new Rectangle(minX, minY, width, height);
    }



}
