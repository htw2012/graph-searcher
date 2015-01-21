/*
 * Copyright Lenovo  @ 2015 ��Ȩ����
 */
package com.pic.dao;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;

import com.pic.model.ImageInfo;
import com.pic.util.ImageConfiguration;

/**
 * <p>(��һ�仰�������ļ���ʲô)</p>
 * @author jiahc1
 * @Email  jiahc1@lenovo.com
 * 2015-1-17����7:52:57
 *
 * @version V1.0  
 */
public class ImageDao {
    
    private static final String DB_PATH = ImageConfiguration.ImgStoreDir;
    public static GraphDatabaseService db ;
    
    public static ImageDao dao;
    
    //����ģʽ���ӳټ���
    public static ImageDao getInstance(){
    	return ImageDaoHolder.dao;
    }
    
    static class ImageDaoHolder{
    	static ImageDao dao = new ImageDao();
    }

    private ImageDao() {
        db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        registerShutdownHook(db);
    }
    
    
    //�����ļ��������洢���ļ����ڵ�����ͼƬ
    public boolean saveAll(String folder){
    	File headFile = new File(System.getProperty("user.dir")+"\\"+folder);
    	if(!headFile.exists())
    	{
    		 System.out.println("Ŀ���ļ��в�����");
             return false;
    	}
    	
    	saveAll(headFile);
//    	
//        Transaction tx = db.beginTx();
//        try {
//       
//        	saveAll(headFile);
//            tx.success();
//
//        }
//        finally {
//            tx.finish();
//        }
    	
    	return true;
    }
    
    //Ŀ���ļ��洢��file����ΪĿ¼���ļ�
    public boolean saveAll(File file)
    {
    	if(file.isFile())
    	{
//    		Node node = db.createNode();
//            // ΪfileName�����������
//            Index<Node> index = db.index().forNodes("nodes");
            String fileName = file.getName();
            if(!isExsit(fileName))
            {
            	System.out.println(fileName+"���ڴ洢");
            	saveOne(file);
//                node.setProperty("fileName", fileName);
//                index.add(node, "fileName", fileName);
//
//                String formatType = fileName.substring(fileName.lastIndexOf(".") + 1);
//                node.setProperty("formatType", formatType);
//                BufferedImage image = null;
//                try {
//                    image = ImageIO.read(file);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                if (image == null) {
//                    System.out.println("ͼƬ��ȡʧ�ܣ�����ͼƬ");
//                    return false;
//                }
//                int imageType = image.getType();
//                node.setProperty("imageType", imageType);
//
//                int width = image.getWidth();
//                int height = image.getHeight();
//                node.setProperty("width", width);
//                node.setProperty("height", height);
//
//                int minx = image.getMinX();
//                int miny = image.getMinY();
//                node.setProperty("minx", minx);
//                node.setProperty("miny", miny);
//
//                int offset = 0;
//                int scansize = width;
//
//                int[] pixelArray = new int[offset + (height - miny) * scansize];
//                image.getRGB(minx, miny, width, height, pixelArray, offset, scansize);
//
//                node.setProperty("pixelArray", pixelArray);
            }else
            {
            	System.out.println(fileName+"�Ѵ���");
            }
            
    	}else{
    		System.out.println(file.getName()+":�ļ������ڴ洢");
    		File[] subFiles = file.listFiles();
    		for(File subFile : subFiles)
    		{
    			saveAll(subFile);
    		}	
    	}
    	
    	return true;
    	
    }
    
    //�����ļ��洢
    public boolean saveOne(File file)
    {
    	Transaction tx = db.beginTx();
    	try{
    	Node node = db.createNode();
        // ΪfileName�����������
        Index<Node> index = db.index().forNodes("nodes");
        String fileName = file.getName();
        System.out.println(fileName);
        node.setProperty("fileName", fileName);
        index.add(node, "fileName", fileName);

        String formatType = fileName.substring(fileName.lastIndexOf(".") + 1);
        node.setProperty("formatType", formatType);
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (image == null) {
            System.out.println("ͼƬ��ȡʧ�ܣ�����ͼƬ");
            return false;
        }
        int imageType = image.getType();
        node.setProperty("imageType", imageType);

        int width = image.getWidth();
        int height = image.getHeight();
        node.setProperty("width", width);
        node.setProperty("height", height);

        int minx = image.getMinX();
        int miny = image.getMinY();
        node.setProperty("minx", minx);
        node.setProperty("miny", miny);

        int offset = 0;
        int scansize = width;

        int[] pixelArray = new int[offset + (height - miny) * scansize];
        image.getRGB(minx, miny, width, height, pixelArray, offset, scansize);

        node.setProperty("pixelArray", pixelArray);
    	
        tx.success();
    	}finally{
    		tx.finish();
    	}
    	
    	return true;
    }
    

    // ����ͼƬ·���洢ͼƬ
    // ��D:/pic/test.jpg
    public boolean saveOne(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("Ŀ��ͼƬ������");
            return false;
        }
        Transaction tx = db.beginTx();
        try {
            Node node = db.createNode();
            // ΪfileName�����������
            Index<Node> index = db.index().forNodes("nodes");
            String fileName = file.getName();
            System.out.println(fileName);
            node.setProperty("fileName", fileName);
            index.add(node, "fileName", fileName);

            String formatType = fileName.substring(fileName.lastIndexOf(".") + 1);
            node.setProperty("formatType", formatType);
            BufferedImage image = null;
            try {
                image = ImageIO.read(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (image == null) {
                System.out.println("ͼƬ��ȡʧ�ܣ�����ͼƬ");
                return false;
            }
            int imageType = image.getType();
            node.setProperty("imageType", imageType);

            int width = image.getWidth();
            int height = image.getHeight();
            node.setProperty("width", width);
            node.setProperty("height", height);

            int minx = image.getMinX();
            int miny = image.getMinY();
            node.setProperty("minx", minx);
            node.setProperty("miny", miny);

            int offset = 0;
            int scansize = width;

            int[] pixelArray = new int[offset + (height - miny) * scansize];
            image.getRGB(minx, miny, width, height, pixelArray, offset, scansize);

            node.setProperty("pixelArray", pixelArray);
            tx.success();

            return true;
        } finally {
            tx.finish();
        }

    }

    // ����ͼƬ������ͼƬ�����ذ���ͼƬ��Ϣ��map����
    // �� test.jpg
    public ImageInfo load(String fileName) {
        ImageInfo image = new ImageInfo();
        Transaction tx = db.beginTx();
        try {
            Index<Node> index = db.index().forNodes("nodes");
            Node node = index.get("fileName", fileName).getSingle();
            if (node == null) {
                System.out.println(fileName + " ������");
                return null;
            }


            try {
                image.setFileName(fileName);
                image.setFormatType((String)(node.getProperty("formatType")));
                image.setImageType((int)(node.getProperty("imageType")));
                image.setWidth((int)(node.getProperty("width")));
                image.setHeight((int)(node.getProperty("height")));
                image.setMinx((int)(node.getProperty("minx")));
                image.setMiny((int)(node.getProperty("miny")));
                image.setPixelArray((int[])(node.getProperty("pixelArray")));
                
            } catch (Exception e) {
                System.out.println("���Զ�ȡ����");
                return null;
            }
        } finally {
            tx.finish();
        }
        return image;
    }

    // �����ļ������������ݿ����Ƿ����
    public boolean isExsit(String fileName) {
        Transaction tx = db.beginTx();
        try {
            Index<Node> index = db.index().forNodes("nodes");
            Node node = index.get("fileName", fileName).getSingle();
            if (node == null) {
                System.out.println(fileName + " ������");
                return false;
            }
        } finally {
            tx.finish();
        }

        return true;
    }
    
    
    public boolean delete(String fileName)
    {
        Transaction tx = db.beginTx();
        try {
            Index<Node> index = db.index().forNodes("nodes");
            Node node = index.get("fileName", fileName).getSingle();
            if (node == null) {
                System.out.println(fileName + " ������");
                return false;
            }else{
                node.delete();
            }
            
        } finally {
            tx.finish();
        }
        
        return true;

    }

    // �ر����ݿ�
    public void shutDown() {
        System.out.println();
        System.out.println("Shutting down database ...");
        // START SNIPPET: shutdownServer
        db.shutdown();
        // END SNIPPET: shutdownServer
    }

    // ע��ر��¼�
    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }

    public static void main(String[] args) {
//        ImageDao dao = new ImageDao();
//        boolean isSave = dao.save("D:/1.jpg");
//        if (isSave == true) {
//           ImageInfo image = dao.load("1.jpg");
//            if (image != null) {
//                boolean isCreate = createImage(image, "D:/copy" + image.getFileName());
//                System.out.println(isCreate);
//            }
//        }
//
//        dao.shutDown();
    	
    	System.out.println(System.getProperty("user.dir"));


    }
    

}
