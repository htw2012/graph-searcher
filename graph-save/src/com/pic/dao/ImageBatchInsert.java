/*
 * Copyright Lenovo  @ 2015 版权所有
 */
package com.pic.dao;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserters;

/**
 * <p>(批量插入大文件，有用没有事务保证，所以该方法不安全)</p>
 * @author jiahc1
 * @Email  jiahc1@lenovo.com
 * 2015-1-21下午4:25:24
 *
 * @version V1.0  
 */
public class ImageBatchInsert {
    
    public  BatchInserter db;
    
    public ImageBatchInsert(){
        
        db = BatchInserters.inserter( "D:/target/batchinserter-example" );
    }
    
    public boolean saveAll(String folder)
    {
        File headFile = new File(System.getProperty("user.dir")+"\\"+folder);
        if(!headFile.exists())
        {
             System.out.println("目标文件夹不存在");
             return false;
        }
        
        saveAll(headFile);
        
        return true;
    }
    
    //目标文件存储，file可以为目录和文件
    public boolean saveAll(File file)
    {
        Label imageLable = DynamicLabel.label( "Image" );
        db.createDeferredSchemaIndex( imageLable ).on( "fileName" ).create();
        
        if(file.isFile())
        {
            
            String fileName = file.getName();
           
                System.out.println(fileName+"正在存储");
                Map<String, Object> properties = new HashMap<>();
                properties.put("fileName", fileName);

                String formatType = fileName.substring(fileName.lastIndexOf(".") + 1);
                properties.put("formatType", formatType);
                BufferedImage image = null;
                try {
                    image = ImageIO.read(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (image == null) {
                    System.out.println("图片读取失败，请检查图片");
                    return false;
                }
                int imageType = image.getType();
                properties.put("imageType", imageType);

                int width = image.getWidth();
                int height = image.getHeight();
                properties.put("width", width);
                properties.put("height", height);

                int minx = image.getMinX();
                int miny = image.getMinY();
                properties.put("minx", minx);
                properties.put("miny", miny);

                int offset = 0;
                int scansize = width;

                int[] pixelArray = new int[offset + (height - miny) * scansize];
                image.getRGB(minx, miny, width, height, pixelArray, offset, scansize);

                properties.put("pixelArray", pixelArray); 
                
                db.createNode( properties, imageLable );
            
        }else{
            System.out.println(file.getName()+":文件夹正在存储");
            File[] subFiles = file.listFiles();
            for(File subFile : subFiles)
            {
                saveAll(subFile);
            }   
        }
        
        return true;
        
    }
    

}
