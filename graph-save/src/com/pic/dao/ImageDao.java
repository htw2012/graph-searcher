/*
 * Copyright Lenovo @ 2015 ��Ȩ����
 */
package com.pic.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.neo4j.backup.OnlineBackup;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.tooling.GlobalGraphOperations;

import com.pic.model.ImageByte;
import com.pic.model.ImagePixArray;
import com.pic.model.ImagePixMatrix;
import com.pic.model.RelTypes;
import com.pic.util.ImageConfiguration;

/**
 * <p>
 * (��һ�仰�������ļ���ʲô)
 * </p>
 * 
 * @author jiahc1
 * @Email jiahc1@lenovo.com 2015-1-17����7:52:57
 *
 * @version V1.0
 */
public class ImageDao {

    private static final String DB_PATH = ImageConfiguration.ImgStoreDir;
    public static GraphDatabaseService db;

    public static ImageDao dao;

    public static ImageDao getInstance() {
        return ImageDaoHolder.dao;
    }

    static class ImageDaoHolder {
        static ImageDao dao = new ImageDao();
    }

    private ImageDao() {
        db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        registerShutdownHook(db);
    }

    /**
     * 该方法会根据文件名存储主目录下的单张图片，在数据库中生成三个节点，并建立关系
     * @param fileName
     * @return
     */
    public boolean saveOne(String fileName) {
        File file = new File(System.getProperty("user.dir") + "\\" + fileName);
        if (!file.exists()) {
            System.out.println("目标图片不存在");
            return false;
        }

        try (Transaction tx = db.beginTx()) {
            Label imageLabel = DynamicLabel.label("Image");
            Label pixelArrayLabel = DynamicLabel.label("PixelArray");
            Label pixelMatrixLabel = DynamicLabel.label("PixelMatrix");

            Node imageNode = db.createNode(imageLabel);
            Node pixArrNode = db.createNode(pixelArrayLabel);
            Node pixMatNode = db.createNode(pixelMatrixLabel); 
            
            imageNode.setProperty("fileName", fileName);
            pixArrNode.setProperty("fileName", fileName);
            pixMatNode.setProperty("fileName", fileName);

            String formatType = fileName.substring(fileName.lastIndexOf(".") + 1);
            BufferedImage image = null;

            image = ImageIO.read(file);

            if (image == null) {
                System.out.println("图片读取失败，请检查图片");
                return false;
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, formatType, baos);
            byte[] imageByteArray = baos.toByteArray();
            imageNode.setProperty("imageByteArray", imageByteArray);

            int imageType = image.getType();
            pixArrNode.setProperty("imageType", imageType);
            int width = image.getWidth();
            int height = image.getHeight();
            pixArrNode.setProperty("width", width);
            pixArrNode.setProperty("height", height);
            int minx = image.getMinX();
            int miny = image.getMinY();
            pixArrNode.setProperty("minx", minx);
            pixArrNode.setProperty("miny", miny);

            int offset = 0;
            int scansize = width;
            int[] pixelArray = new int[offset + (height - miny) * scansize];
            image.getRGB(minx, miny, width, height, pixelArray, offset, scansize);
            pixArrNode.setProperty("pixelArray", pixelArray);
            
            int[][] pixelMatrix = new int[width][height];
            int k = 0;
            for(int i=0;i<width;i++){  
                for(int j=0;j<height;j++){  
                    pixelMatrix[i][j] = pixelArray[k];  
                    k++;  
                }  
            }
            pixMatNode.setProperty("pixelMatix", pixelMatrix);
            
            imageNode.createRelationshipTo(pixArrNode, RelTypes.PIXELARRAY);
            imageNode.createRelationshipTo(pixMatNode, RelTypes.PIXELMATRIX);
            
            tx.success();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return true;
    }

    /**
     * 根据文件名，标签名，返回对应节点，对节点的操作需要在事务中进行
     * 
     * @param fileName
     * @param labelName
     * @return
     */
    public Node findOneNode(String fileName,String labelName) {
        Node node = null;
        try (Transaction tx = db.beginTx()) {
            Label imageLabel = DynamicLabel.label(labelName);
            node = db.findNode(imageLabel, "fileName", fileName);
            tx.success();
        }
        return node;
    }

    /**
     * 根据文件名查找，返回ImageByte对象
     * @param fileName
     * @return
     */
    public ImageByte findOneByte(String fileName) {
        ImageByte image = new ImageByte();

        try (Transaction tx = db.beginTx()) {
            Label imageLabel = DynamicLabel.label("Image");
            Node node = db.findNode(imageLabel, "fileName", fileName);
            if (node == null) {
                System.out.println(fileName + " 不存在");
                return null;
            }

            image.setFileName(fileName);
            image.setImageByteArray((byte[]) (node.getProperty("imageByteArray")));
           
            tx.success();
        } 
        return image;
    }
    
    /**
     * 根据文件名查找，返回ImagePixArray对象
     * @param fileName
     * @return
     */
    public ImagePixArray findOnePixArr(String fileName) {
        ImagePixArray image = new ImagePixArray();

        try (Transaction tx = db.beginTx()) {
            Label pixArrLabel = DynamicLabel.label("PixelArray");
            Node node = db.findNode(pixArrLabel, "fileName", fileName);
            if (node == null) {
                System.out.println(fileName + " 不存在");
                return null;
            }
            image.setFileName(fileName);
            image.setImageType((int)node.getProperty("imageType"));
            image.setWidth((int)node.getProperty("width"));
            image.setHeight((int)node.getProperty("height"));
            image.setPixelArray((int[])node.getProperty("pixelArray"));
            
            tx.success();
        } 
        
        return image;
    }
    
    /**
     * 根据文件名查找，返回ImagePixMatrix对象
     * @param fileName
     * @return
     */
    public ImagePixMatrix findOnePixMat(String fileName) {
        ImagePixMatrix image = new ImagePixMatrix();

        try (Transaction tx = db.beginTx()) {
            Label pixMatLabel = DynamicLabel.label("PixelMatrix");
            Node node = db.findNode(pixMatLabel, "fileName", fileName);
            if (node == null) {
                System.out.println(fileName + " 不存在");
                return null;
            }
            image.setFileName(fileName);
            image.setPixelMatrix((int[][])node.getProperty("pixelMatix"));
        
            tx.success();
        } 
        return image;
    }

    /**
     * 根据包含文件名的数组，返回包含ImageByte对象的list
     * @param fileNameArray
     * @return
     */
    public List<ImageByte> findImgByteList(String[] fileNameArray) {
        List<ImageByte> list = new ArrayList<ImageByte>();
        try (Transaction tx = db.beginTx()) {
            Label imageLabel = DynamicLabel.label("Image");
            for (String fileName : fileNameArray) {
                ImageByte image = new ImageByte();
                Node node = db.findNode(imageLabel, "fileName", fileName);
                if (node == null) {
                    System.out.println(fileName + " 不存在");
                    continue;
                }

                image.setFileName(fileName);
                image.setImageByteArray((byte[]) (node.getProperty("imageByteArray")));

                list.add(image);
            }
            tx.success();
        }

        return list;
    }
    
    /**
     * 根据包含文件名的数组，返回包含ImagePixArray对象的list
     * @param fileNameArray
     * @return
     */
    public List<ImagePixArray> findImgPixArrList(String[] fileNameArray) {
        List<ImagePixArray> list = new ArrayList<ImagePixArray>();
        try (Transaction tx = db.beginTx()) {
        	Label pixArrLabel = DynamicLabel.label("PixelArray");
            for (String fileName : fileNameArray) {
            	ImagePixArray image = new ImagePixArray();
                Node node = db.findNode(pixArrLabel, "fileName", fileName);
                if (node == null) {
                    System.out.println(fileName + " 不存在");
                    continue;
                }

                image.setFileName(fileName);
                image.setImageType((int)node.getProperty("imageType"));
                image.setWidth((int)node.getProperty("width"));
                image.setHeight((int)node.getProperty("height"));
                image.setPixelArray((int[])node.getProperty("pixelArray"));

                list.add(image);
            }
            tx.success();
        }

        return list;
    }
    
    /**
     * 根据包含文件名的数组，返回包含ImagePixMatrix对象的list
     * @param fileNameArray
     * @return
     */
    public List<ImagePixMatrix> findImgPixMatList(String[] fileNameArray) {
        List<ImagePixMatrix> list = new ArrayList<ImagePixMatrix>();
        try (Transaction tx = db.beginTx()) {
        	Label pixArrLabel = DynamicLabel.label("PixelArray");
            for (String fileName : fileNameArray) {
            	ImagePixMatrix image = new ImagePixMatrix();
                Node node = db.findNode(pixArrLabel, "fileName", fileName);
                if (node == null) {
                    System.out.println(fileName + " 不存在");
                    continue;
                }

                image.setFileName(fileName);
                image.setPixelMatrix((int[][])node.getProperty("pixelMatix"));

                list.add(image);
            }
            tx.success();
        }

        return list;
    }
    

    /**
     * 该方法暂时不提供
     */
    public List<String> getAllNodesName() {
        List<String> list = new ArrayList<String>();
        try (Transaction tx = db.beginTx()) {
            Iterable<Node> nodesIt = GlobalGraphOperations.at(db).getAllNodes();
            Iterator<Node> nodes = nodesIt.iterator();
            while (nodes.hasNext()) {
                Node node = nodes.next();
                String fileName = (String) node.getProperty("fileName");
                list.add(fileName);
                System.out.println(fileName);
            }
            tx.success();
        }
        return list;
    }

    public List<String> getAllNodesNameWithLabel(String labelName) {
        List<String> list = new ArrayList<String>();
        try (Transaction tx = db.beginTx()) {
            Label label = DynamicLabel.label(labelName);
            Iterable<Node> nodesIt = GlobalGraphOperations.at(db).getAllNodesWithLabel(label);
            Iterator<Node> nodes = nodesIt.iterator();
            while (nodes.hasNext()) {
                Node node = nodes.next();
                String fileName = (String) node.getProperty("fileName");
                list.add(fileName);
                // System.out.println(fileName);
            }
            tx.success();
        }
        return list;
    }

    /**
     * 根据文件名，查找数据库中对应节点是否存在
     * 
     * @param fileName 如：shose100001.jpg
     * @return
     */
    public boolean isExsit(String fileName,String labelName) {
        try (Transaction tx = db.beginTx()) {
            Label imageLabel = DynamicLabel.label(labelName);
            Node node = db.findNode(imageLabel, "fileName", fileName);
            if (node == null) {
                System.out.println(fileName + " 不存在");
                return false;
            }
            tx.success();
        }
        return true;
    }


    /**
     * 根据文件名，查找数据库中对应节点是否存在,如果存在删除对应节点
     * 
     * @param fileName 如：shose100001.jpg
     * @return
     */
    public boolean delete(String fileName,String labelName) {

        try (Transaction tx = db.beginTx()) {
            Label imageLabel = DynamicLabel.label(labelName);
            Node node = db.findNode(imageLabel, "fileName", fileName);
            if (node == null) {
                System.out.println(fileName + " 不存在");
                return false;
            } else {
                node.delete();
            }
            tx.success();
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
    /**
     *  ������ݿ�
     * @param dbAddr ��Ҫ����DB���ַ
     * @param backupPath ����Ŀ¼
     * @author towan	
     * @Email  tongwenzide@163.com
     * 2015��1��24������5:15:44
     */
    public void  backup(String dbAddr,String backupPath){
        OnlineBackup backup = OnlineBackup.from( dbAddr );
        backup.full( backupPath );
        Assert.assertTrue( "Should be consistent", backup.isConsistent() );
        backup.incremental( backupPath );
    }
    public static void main(String[] args) {
        // ImageDao dao = new ImageDao();
        // boolean isSave = dao.save("D:/1.jpg");
        // if (isSave == true) {
        // ImageInfo image = dao.load("1.jpg");
        // if (image != null) {
        // boolean isCreate = createImage(image, "D:/copy" + image.getFileName());
        // System.out.println(isCreate);
        // }
        // }
        //
        // dao.shutDown();

        System.out.println(System.getProperty("user.dir"));


    }


}
