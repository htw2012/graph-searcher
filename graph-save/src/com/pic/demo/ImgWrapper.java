/*
 * Copyright 北京航空航天大学  @ 2015 版权所有
 */
package com.pic.demo;

import java.io.IOException;
import org.junit.Test;
import com.pic.dao.ImageBatchInsert;
import com.pic.dao.ImageDao;
import com.pic.model.ImageInfo;
import com.pic.util.ImageConfiguration;
import com.pic.util.JsonUtil;

/**
 * <p>图片的数据的存储、查询的包装类</p>
 * @author towan
 * @Email  tongwenzide@163.com
 * 2015年1月21日下午9:26:48
 *
 * @version V1.0  
 */
public class ImgWrapper {
    
    /**
     * 批量加载图片到数据库中
     * @param folderDir 存储图片文件夹路径
     * @author towan	
     * @Email  tongwenzide@163.com
     * 2015年1月21日下午9:28:27
     */
    public static void loadImages(String folderDir){
        ImageBatchInsert dao = new ImageBatchInsert();
        dao.saveAll(folderDir);
        dao.shutDown();
    }
    /**
     * 单张图片的查询
     * @param imgName 图片名称
     * @return
     * @author towan	
     * @Email  tongwenzide@163.com
     * 2015年1月21日下午9:35:59
     */
    public static ImageInfo getImage(String imgName){
        ImageDao imgDao = ImageDao.getInstance();
        return imgDao.findOne(imgName);
    }
    
    @Test
    public void testBatchIntoDB(){
        loadImages(ImageConfiguration.ImgSourceFolder);
    }
    @Test
    public void testQueryImage() throws IOException{
        ImageInfo image = getImage("clothes_250001.jpg");
        String json = JsonUtil.toJson(image);
        System.out.println("ImageInfo:\n"+json);
    }
}
