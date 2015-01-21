/*
 * Copyright Lenovo  @ 2015 版权所有
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
 * <p>(用一句话描述该文件做什么)</p>
 * @author jiahc1
 * @Email  jiahc1@lenovo.com
 * 2015-1-17下午7:52:57
 *
 * @version V1.0  
 */
public class ImageDao {
    
    private static final String DB_PATH = ImageConfiguration.ImgStoreDir;
    public static GraphDatabaseService db ;
    
    public static ImageDao dao;
    
    //单例模式，延迟加载
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
    
    
    //根据文件夹名，存储该文件夹内的所有图片
    public boolean saveAll(String folder){
    	File headFile = new File(System.getProperty("user.dir")+"\\"+folder);
    	if(!headFile.exists())
    	{
    		 System.out.println("目标文件夹不存在");
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
    
    //目标文件存储，file可以为目录和文件
    public boolean saveAll(File file)
    {
    	if(file.isFile())
    	{
//    		Node node = db.createNode();
//            // 为fileName属性添加索引
//            Index<Node> index = db.index().forNodes("nodes");
            String fileName = file.getName();
            if(!isExsit(fileName))
            {
            	System.out.println(fileName+"正在存储");
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
//                    System.out.println("图片读取失败，请检查图片");
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
            	System.out.println(fileName+"已存在");
            }
            
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
    
    //单个文件存储
    public boolean saveOne(File file)
    {
    	Transaction tx = db.beginTx();
    	try{
    	Node node = db.createNode();
        // 为fileName属性添加索引
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
            System.out.println("图片读取失败，请检查图片");
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
    

    // 根据图片路径存储图片
    // 如D:/pic/test.jpg
    public boolean saveOne(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.out.println("目标图片不存在");
            return false;
        }
        Transaction tx = db.beginTx();
        try {
            Node node = db.createNode();
            // 为fileName属性添加索引
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
                System.out.println("图片读取失败，请检查图片");
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

    // 根据图片名查找图片，返回包含图片信息的map对象
    // 如 test.jpg
    public ImageInfo load(String fileName) {
        ImageInfo image = new ImageInfo();
        Transaction tx = db.beginTx();
        try {
            Index<Node> index = db.index().forNodes("nodes");
            Node node = index.get("fileName", fileName).getSingle();
            if (node == null) {
                System.out.println(fileName + " 不存在");
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
                System.out.println("属性读取错误");
                return null;
            }
        } finally {
            tx.finish();
        }
        return image;
    }

    // 根据文件名，查找数据库中是否存在
    public boolean isExsit(String fileName) {
        Transaction tx = db.beginTx();
        try {
            Index<Node> index = db.index().forNodes("nodes");
            Node node = index.get("fileName", fileName).getSingle();
            if (node == null) {
                System.out.println(fileName + " 不存在");
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
                System.out.println(fileName + " 不存在");
                return false;
            }else{
                node.delete();
            }
            
        } finally {
            tx.finish();
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

    // 注册关闭事件
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
