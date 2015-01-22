/*
 * Copyright �������պ����ѧ  @ 2015 ��Ȩ����
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
 * <p>ͼƬ�����ݵĴ洢����ѯ�İ�װ��</p>
 * @author towan
 * @Email  tongwenzide@163.com
 * 2015��1��21������9:26:48
 *
 * @version V1.0  
 */
public class ImgWrapper {
    
    /**
     * ��������ͼƬ�����ݿ���
     * @param folderDir �洢ͼƬ�ļ���·��
     * @author towan	
     * @Email  tongwenzide@163.com
     * 2015��1��21������9:28:27
     */
    public static void loadImages(String folderDir){
        ImageBatchInsert dao = new ImageBatchInsert();
        dao.saveAll(folderDir);
        dao.shutDown();
    }
    /**
     * ����ͼƬ�Ĳ�ѯ
     * @param imgName ͼƬ����
     * @return
     * @author towan	
     * @Email  tongwenzide@163.com
     * 2015��1��21������9:35:59
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
