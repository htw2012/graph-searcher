package com.pic.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.pic.model.ImageByte;

public class ImageUtil {

	 /**
     * 根据image对象包含的图片信息重建图片
     * @param image
     * @param outPath 图片生成的绝对路径 （文件夹）
     * @return
     */
    public static boolean createImage(ImageByte image) {
        if (image == null) {
            System.out.println("map 输入为 null");
            return false;
        }
        String fileName = image.getFileName();
        String formatType = fileName.substring(fileName.lastIndexOf(".") + 1);
        try {
            File file = new File(System.getProperty("user.dir") + "/generateImg/" + fileName);
            if (file.exists()) {
                System.out.println("图片已存在");
                return false;
            }       
            BufferedImage bi =ImageIO.read(new ByteArrayInputStream(image.getImageByteArray()));      
  
            ImageIO.write(bi, formatType, file);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } 
        return true;
    }

    /**
     * 批量重建图像
     * @param imageList
     * @param folder  在项目主目录下生成该文件夹
     * @return
     */
    public static boolean createImageBatch(List<ImageByte> imageList, String folder) {
        if (imageList == null) {
            System.out.println("imageList 输入为 null");
            return false;
        }
        ByteArrayInputStream bais = null;
        try {
            for (ImageByte image : imageList) {
                String fileName = image.getFileName();
                String formatType = fileName.substring(fileName.lastIndexOf(".") + 1);
                byte[] imageByteArray = image.getImageByteArray();
                File file =
                        new File(System.getProperty("user.dir") + "\\" + folder + "\\" + fileName);
                if (!file.exists()) {
                    File dir = file.getParentFile();
                    if (!dir.exists()) dir.mkdirs();
                    file.createNewFile();
                } else {
                    System.out.println("图片已存在");
                    return false;
                }

                bais = new ByteArrayInputStream(imageByteArray);      
                BufferedImage bi =ImageIO.read(bais);      
      
                ImageIO.write(bi, formatType, file);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bais != null) try {
                bais.close();
            } catch (IOException e) {

            }
        }
        return true;
    }


}
