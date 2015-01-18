package com.pic.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.pic.model.ImageInfo;

public class ImageUtil {
	
	 // 根据map对象包含的图片信息重建图片
    public static boolean createImage(ImageInfo image, String outPath) {
        if (image == null) {
            System.out.println("map 输入为 null");
            return false;
        }
        OutputStream output = null;
        int imageType = image.getImageType();
        int width = image.getWidth();
        int height = image.getHeight();
        int scansize = width;
        int minx = image.getMinx();
        int miny = image.getMiny();
        int[] pixelArray = image.getPixelArray();
        String formatType = image.getFormatType();
        try {
            File file = new File(outPath);
            if (!file.exists()) {
                File dir = file.getParentFile();
                if (!dir.exists()) dir.mkdirs();
                file.createNewFile();
            } else {
                System.out.println("图片已存在");
                return false;
            }

            output = new FileOutputStream(file);
            BufferedImage imgOut = new BufferedImage(width, height, imageType);
            // offset 暂时设定为0
            // imgOut.setRGB(minx, miny, width, height, pixelArray, offset, scansize);
            imgOut.setRGB(minx, miny, width, height, pixelArray, 0, scansize);
            ImageIO.write(imgOut, formatType, output);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (output != null) try {
                output.close();
            } catch (IOException e) {
            	
            }
        }
        return true;
    }


}
