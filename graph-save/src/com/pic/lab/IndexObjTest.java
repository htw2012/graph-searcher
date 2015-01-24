/*
 * Copyright 北京航空航天大学 @ 2015 版权所有
 */
package com.pic.lab;

import java.io.File;
import java.io.IOException;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.io.fs.FileUtils;

import com.pic.util.ImageConfiguration;

/**
 * <p>
 * 索引数据添加测试
 * </p>
 * 
 * @author towan
 * @Email tongwenzide@163.com 
 *  2015年1月24日下午3:40:29
 *
 * @version V1.0
 */
public class IndexObjTest {
    private static final String DB_PATH = ImageConfiguration.ImgStoreDir+"/neo4j-store";
    private static final String USERNAME_KEY = "username";
    private static GraphDatabaseService graphDb;
    private static Index<Node> nodeIndex;

    public static void main(final String[] args) throws IOException {
        FileUtils.deleteRecursively(new File(DB_PATH));
        // START SNIPPET: startDb
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        registerShutdownHook();
        // END SNIPPET: startDb
        // START SNIPPET: addUsers
        try (Transaction tx = graphDb.beginTx()) {
            nodeIndex = graphDb.index().forNodes("nodes");
            // Create some users and index their names with the IndexService
            for (int id = 0; id < 100; id++) {
                createAndIndexUser(idToUserName(id));
            }
            // END SNIPPET: addUsers
            // Find a user through the search index
            // START SNIPPET: findUser
            int idToFind = 45;
            String userName = idToUserName(idToFind);
            Node foundUser = nodeIndex.get(USERNAME_KEY, userName).getSingle();
            System.out.println("The username of user " + idToFind + " is "
                    + foundUser.getProperty(USERNAME_KEY));
            // END SNIPPET: findUser
            // Delete the persons and remove them from the index
            for (Node user : nodeIndex.query(USERNAME_KEY, "*")) {
                nodeIndex.remove(user, USERNAME_KEY, user.getProperty(USERNAME_KEY));
                user.delete();
            }
            tx.success();
        }
        shutdown();
    }

    private static void shutdown() {
        graphDb.shutdown();
    }

    // START SNIPPET: helperMethods
    private static String idToUserName(final int id) {
        return "user" + id + "@neo4j.org";
    }

    private static Node createAndIndexUser(final String username) {
        Node node = graphDb.createNode();
        node.setProperty(USERNAME_KEY, username);
        nodeIndex.add(node, USERNAME_KEY, username);
        return node;
    }

    // END SNIPPET: helperMethods
    private static void registerShutdownHook() {
        // Registers a shutdown hook for the Neo4j and index service instances
        // so that it shuts down nicely when the VM exits (even if you
        // "Ctrl-C" the running example before it's completed)
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                shutdown();
            }
        });
    }
}
