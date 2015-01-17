package com.pic.test;

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

public class Test3 {

    private static final String DB_PATH = "D:/pic/db";
    GraphDatabaseService db = null;

    public Test3() {
        db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        registerShutdownHook(db);
    }

    // ����ͼƬ·���洢ͼƬ
    // ��D:/pic/test.jpg
    public boolean save(String path) {
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
    public Map<String, Object> load(String fileName) {
        Map<String, Object> map = new HashMap<String, Object>();
        Transaction tx = db.beginTx();
        try {
            Index<Node> index = db.index().forNodes("nodes");
            Node node = index.get("fileName", fileName).getSingle();
            if (node == null) {
                System.out.println(fileName + " ������");
                return null;
            }


            try {
                map.put("fileName", fileName);
                map.put("formatType", node.getProperty("formatType"));
                map.put("imageType", node.getProperty("imageType"));
                map.put("width", node.getProperty("width"));
                map.put("height", node.getProperty("height"));
                map.put("minx", node.getProperty("minx"));
                map.put("miny", node.getProperty("miny"));
                map.put("pixelArray", node.getProperty("pixelArray"));
            } catch (Exception e) {
                System.out.println("���Զ�ȡ����");
                return null;
            }
        } finally {
            tx.finish();
        }
        return map;
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


    // ����map���������ͼƬ��Ϣ�ؽ�ͼƬ
    public static boolean createImage(Map<String, Object> map, String outPath) {
        if (map == null) {
            System.out.println("map ����Ϊ null");
            return false;
        }
        OutputStream output = null;
        int imageType = (int) map.get("imageType");
        int width = (int) map.get("width");
        int height = (int) map.get("height");
        int scansize = width;
        int minx = (int) map.get("minx");
        int miny = (int) map.get("miny");
        int[] pixelArray = (int[]) map.get("pixelArray");
        String formatType = (String) map.get("formatType");
        try {
            File file = new File(outPath);
            if (!file.exists()) {
                File dir = file.getParentFile();
                if (!dir.exists()) dir.mkdirs();
                file.createNewFile();
            } else {
                System.out.println("ͼƬ�Ѵ���");
                return false;
            }

            output = new FileOutputStream(file);
            BufferedImage imgOut = new BufferedImage(width, height, imageType);
            // offset ��ʱ�趨Ϊ0
            // imgOut.setRGB(minx, miny, width, height, pixelArray, offset, scansize);
            imgOut.setRGB(minx, miny, width, height, pixelArray, 0, scansize);
            ImageIO.write(imgOut, formatType, output);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (output != null) try {
                output.close();
            } catch (IOException e) {}
        }
        return true;
    }

    // �ر����ݿ�
    void shutDown() {
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
        Test3 t = new Test3();
        boolean isSave = t.save("D:/1.jpg");
        if (isSave == true) {
            Map<String, Object> map = t.load("1.jpg");
            if (map != null) {
                boolean isCreate = createImage(map, "D:/copy" + map.get("fileName"));
                System.out.println(isCreate);
            }
        }

        t.shutDown();


    }

}
