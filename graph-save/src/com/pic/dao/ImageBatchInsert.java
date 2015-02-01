/*
 * Copyright Lenovo @ 2015 ��Ȩ����
 */
package com.pic.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserters;

import com.pic.model.RelTypes;
import com.pic.util.ImageConfiguration;

/**
 * <p>
 * (����������ļ�������û������֤�����Ը÷�������ȫ)
 * </p>
 * 
 * @author jiahc1
 * @Email jiahc1@lenovo.com 2015-1-21����4:25:24
 *
 * @version V1.0
 */
public class ImageBatchInsert {

    public BatchInserter db;

    public ImageBatchInsert() {

        db = BatchInserters.inserter(ImageConfiguration.ImgStoreDir);
    }

    /**
     * 根据图片名存储图片，或者根据文件夹名存储文件夹下的所有图片,生成与model下对应的Node节点
     * 
     * @param folder 如：shose100001.jpg 、contest_data
     * @return
     */
    public boolean saveAll(String folder) {
        File headFile = new File(System.getProperty("user.dir") + "\\" + folder);
        if (!headFile.exists()) {
            System.out.println("目标文件夹不存在");
            return false;
        }

        Label imageLabel = DynamicLabel.label("Image");
        Label pixArrLabel = DynamicLabel.label("PixelArray");
        Label pixMatLabel = DynamicLabel.label("pixelMatix");
        db.createDeferredSchemaIndex(imageLabel).on("fileName").create();
        db.createDeferredSchemaIndex(pixArrLabel).on("fileName").create();
        db.createDeferredSchemaIndex(pixMatLabel).on("fileName").create();

      //  RelationshipType contain = DynamicRelationshipType.withName("CONTAIN");

        saveAll(headFile, imageLabel, pixArrLabel,pixMatLabel);

        return true;
    }


    // 目标文件存储，file可以为目录和文件
    private boolean saveAll(File file, Label imageLabel, Label pixArrLabel,Label pixMatLabel) {

        if (file.isFile()) {
            String fileName = file.getName();
            System.out.println(fileName);
            Map<String, Object> imageProperties = new HashMap<>();
            Map<String, Object> pixArrProperties = new HashMap<>();
            Map<String, Object> pixMatProperties = new HashMap<>();
           
            imageProperties.put("fileName", fileName);
            pixArrProperties.put("fileName", fileName);
            pixMatProperties.put("fileName", fileName);
            
            String formatType = fileName.substring(fileName.lastIndexOf(".") + 1);
            BufferedImage image = null;
            try {
                image = ImageIO.read(file);
                if (image == null) {
                    System.out.println("图片读取失败，请检查图片");
                    return false;
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, formatType, baos);
                byte[] imageByteArray = baos.toByteArray();
                imageProperties.put("imageByteArray", imageByteArray);

                int imageType = image.getType();
                pixArrProperties.put("imageType", imageType);
                int width = image.getWidth();
                int height = image.getHeight();
                pixArrProperties.put("width", width);
                pixArrProperties.put("height", height);
                int minx = image.getMinX();
                int miny = image.getMinY();
                pixArrProperties.put("minx", minx);
                pixArrProperties.put("miny", miny);
                int offset = 0;
                int scansize = width;
                int[] pixelArray = new int[offset + (height - miny) * scansize];
                image.getRGB(minx, miny, width, height, pixelArray, offset, scansize);
                pixArrProperties.put("pixelArray", pixelArray);
                
                int[][] pixelMatrix = new int[width][height];
//                int k = 0;
                for(int i=0;i<width;i++){  
                    for(int j=0;j<height;j++){ 
                    	
//                        pixelMatrix[i][j] = pixelArray[k];  
//                        k++; 
                    	pixelMatrix[i][j] = image.getRGB(i, j);
                    }  
                }
                pixMatProperties.put("pixelMatix", pixelMatrix);
                
                long imagel = db.createNode(imageProperties, imageLabel);
                long pixArr = db.createNode(pixArrProperties, pixArrLabel);
                long pixMat = db.createNode(pixMatProperties, pixMatLabel);
                db.createRelationship(imagel, pixArr, RelTypes.PIXELARRAY, null);
                db.createRelationship(imagel, pixMat, RelTypes.PIXELMATRIX, null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(file.getName() + ":文件夹正在存储");
            File[] subFiles = file.listFiles();
            for (File subFile : subFiles) {
                saveAll(subFile, imageLabel, pixArrLabel,pixMatLabel);
            }
        }
        return true;
    }

    // 关闭数据库
    public void shutDown() {
        System.out.println();
        System.out.println("Shutting down database ...");
        // START SNIPPET: shutdownServer
        db.shutdown();
        // END SNIPPET: shutdownServer
    }

}
