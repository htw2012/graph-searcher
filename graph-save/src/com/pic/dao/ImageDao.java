/*
 * Copyright Lenovo  @ 2015 ��Ȩ����
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
    
    
    /**
     * ����ͼƬ�ļ�����������Ŀ¼�µ�ͼƬ
     * @param fileName �磺shose100001.jpg
     * @return
     */
    public boolean saveOne(String fileName)
    {
        File file = new File(System.getProperty("user.dir")+"\\"+fileName);
        if (!file.exists()) {
            System.out.println("Ŀ��ͼƬ������");
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
        
        }
         
        return true;
    }
    
    /**
     * ����ͼƬ������ͼƬ�����ذ���ͼƬ��Ϣ��ImageInfo����
     * 
     * @param fileName ��: shose100001.jpg
     * @return
     */
    public ImageInfo findOne(String fileName) {
        ImageInfo image = new ImageInfo();
        
        try (Transaction tx = db.beginTx()){
            Label imageLabel = DynamicLabel.label( "Image" );
            Node node = db.findNode(imageLabel, "fileName", fileName);
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
                    System.out.println(fileName + " ������");
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
     * �÷�����ʱ���ṩ
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
     * �����ļ������������ݿ����Ƿ����   
     * @param fileName  �磺shose100001.jpg
     * @return
     */
    public boolean isExsit(String fileName) {
        try(Transaction tx = db.beginTx())
        {
            Label imageLabel = DynamicLabel.label( "Image" );
            Node node = db.findNode(imageLabel, "fileName", fileName);

            if (node == null) {
                System.out.println(fileName + " ������");
                return false;
            }
        } 
        return true;
    }
    
    /**
     * �����ļ������������ݿ����Ƿ����,�������ɾ����Ӧ�ڵ�   
     * @param fileName �磺shose100001.jpg
     * @return
     */
    public boolean delete(String fileName)
    {
        
        try (Transaction tx = db.beginTx())
        {
            Label imageLabel = DynamicLabel.label( "Image" );
            Node node = db.findNode(imageLabel, "fileName", fileName);
            if (node == null) {
                System.out.println(fileName + " ������");
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
