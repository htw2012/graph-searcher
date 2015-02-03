/*
 * Copyright 北京航空航天大学 @ 2015 版权所有
 */
package com.pic.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * <p>
 * RGB数据获得测试
 * </p>
 * 
 * @author towan
 * @Email tongwenzide@163.com 2015年2月3日下午7:53:27
 *
 * @version V1.0
 */
public class TestGetRgb {

    public static void main(String[] args) throws IOException {

        File file = new File("images/clothes_250001.jpg");
        BufferedImage image = ImageIO.read(file);
        getRgbValue(image);
        
    }

    public static void getRgbValue(BufferedImage originalImage) {
        int green = 0, red = 0, blue = 0, rgb,alpha;
        int imageWidth = originalImage.getWidth();
        int imageHeight = originalImage.getHeight();
        for (int i = originalImage.getMinX(); i < imageWidth; i++) {
            for (int j = originalImage.getMinY(); j < imageHeight; j++) {
                // 图片的像素点其实是个矩阵，这里利用两个for循环来对每个像素进行操作
                Object data = originalImage.getRaster().getDataElements(i, j, null);
                // 获取该点像素，并以object类型表示
                red = originalImage.getColorModel().getRed(data);
                blue = originalImage.getColorModel().getBlue(data);
                green = originalImage.getColorModel().getGreen(data);
                alpha = originalImage.getColorModel().getAlpha(data);
                rgb = originalImage.getRGB(i, j);
            }

        }
    }
}
