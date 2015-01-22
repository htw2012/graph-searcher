/*
 * Copyright Lenovo  @ 2015 版权所有
 */
package com.pic.dao;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.tooling.GlobalGraphOperations;

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
    
    
    /**
     * 根据图片文件名，保存主目录下的图片
     * @param fileName 如：shose100001.jpg
     * @return
     */
    public boolean saveOne(String fileName)
    {
        File file = new File(System.getProperty("user.dir")+"\\"+fileName);
        if (!file.exists()) {
            System.out.println("目标图片不存在");
            return false;
        }
        
        try ( Transaction tx = db.beginTx() )
        {
            Label label = DynamicLabel.label( "Image" );
        
            Node node = db.createNode( label );
            node.setProperty("fileName", fileName);
            
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
        
        }
         
        return true;
    }
    
    /**
     * 根据图片名查找图片，返回包含图片信息的ImageInfo对象
     * 
     * @param fileName 如: shose100001.jpg
     * @return
     */
    public ImageInfo findOne(String fileName) {
        ImageInfo image = new ImageInfo();
        
        try (Transaction tx = db.beginTx()){
            Label imageLabel = DynamicLabel.label( "Image" );
            Node node = db.findNode(imageLabel, "fileName", fileName);
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
        } 
        return image;
    }
    
    
    public List<ImageInfo> findByArray(String[] fileNameArray)
    {
        List<ImageInfo> list = new ArrayList<ImageInfo>();
        try (Transaction tx = db.beginTx())
        {
            Label imageLabel = DynamicLabel.label( "Image" );
            for(String fileName : fileNameArray)
            {
                ImageInfo image = new ImageInfo();
                Node node = db.findNode(imageLabel, "fileName", fileName);
                if (node == null) {
                    System.out.println(fileName + " 不存在");
                    continue;
                }
                
                image.setFileName(fileName);
                image.setFormatType((String)(node.getProperty("formatType")));
                image.setImageType((int)(node.getProperty("imageType")));
                image.setWidth((int)(node.getProperty("width")));
                image.setHeight((int)(node.getProperty("height")));
                image.setMinx((int)(node.getProperty("minx")));
                image.setMiny((int)(node.getProperty("miny")));
                image.setPixelArray((int[])(node.getProperty("pixelArray"))); 
                
                list.add(image);
            }
            tx.success();
        }
        
        return list;
    }
    
    /**
     * 该方法暂时不提供
     */
    public void getAllNodes(){
        try(Transaction tx = db.beginTx())
        {
            Iterable<Node> nodesIt = GlobalGraphOperations.at(db).getAllNodes();
            Iterator<Node> nodes = nodesIt.iterator();
            while(nodes.hasNext())
            {
                Node node = nodes.next();
                String fileName = (String) node.getProperty("fileName");
                System.out.println(fileName);
            }
            tx.success();
        }
    }

    /**
     * 根据文件名，查找数据库中是否存在   
     * @param fileName  如：shose100001.jpg
     * @return
     */
    public boolean isExsit(String fileName) {
        try(Transaction tx = db.beginTx())
        {
            Label imageLabel = DynamicLabel.label( "Image" );
            Node node = db.findNode(imageLabel, "fileName", fileName);

            if (node == null) {
                System.out.println(fileName + " 不存在");
                return false;
            }
        } 
        return true;
    }
    
    /**
     * 根据文件名，查找数据库中是否存在,如果存在删除对应节点   
     * @param fileName 如：shose100001.jpg
     * @return
     */
    public boolean delete(String fileName)
    {
        
        try (Transaction tx = db.beginTx())
        {
            Label imageLabel = DynamicLabel.label( "Image" );
            Node node = db.findNode(imageLabel, "fileName", fileName);
            if (node == null) {
                System.out.println(fileName + " 不存在");
                return false;
            }else{
                node.delete();
            }
            
        } 
        return true;
    }
    
    public void checkIndex()
    {
        try ( Transaction tx = db.beginTx() )
        {
        Label label = DynamicLabel.label( "Image" );
        for ( IndexDefinition indexDefinition : db.schema()
        .getIndexes( label ) )
        {
            String indexName = indexDefinition.getLabel().name();
            System.out.println(indexName);
        }
        tx.success();
        }
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
