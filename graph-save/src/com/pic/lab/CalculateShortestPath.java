/*
 * Copyright 北京航空航天大学 @ 2015 版权所有
 */
package com.pic.lab;

/**
 * <p>
 * 计算最短路
 * </p>
 * 
 * @author towan
 * @Email tongwenzide@163.com 2015年1月24日下午5:29:12
 *
 * @version V1.0
 */
import java.io.File;

import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.traversal.Paths;

import com.pic.util.ImageConfiguration;

public class CalculateShortestPath {
    private static final String DB_PATH = ImageConfiguration.ImgStoreDir+"/short-path";
    private static final String NAME_KEY = "name";
    private static RelationshipType KNOWS = DynamicRelationshipType.withName("KNOWS");
    private static GraphDatabaseService graphDb;
    private static Index<Node> indexService;

    public static void main(final String[] args) {
        deleteFileOrDirectory(new File(DB_PATH));
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
        registerShutdownHook();
        try (Transaction tx = graphDb.beginTx()) {
            indexService = graphDb.index().forNodes("nodes");
            /*
             * (Neo) --> (Trinity) 
             *      \ ^ v / 
             *   (Morpheus) --> (Cypher)
             *              \       |
             *               v      v 
             *               (Agent Smith)
             */
            createChain("Neo", "Trinity");
            createChain("Neo", "Morpheus", "Trinity");
            createChain("Morpheus", "Cypher", "Agent Smith");
            createChain("Morpheus", "Agent Smith");
            tx.success();
        }
        try (Transaction tx = graphDb.beginTx()) {
            // So let's find the shortest path between Neo and Agent Smith
            Node neo = getOrCreateNode("Neo");
            Node agentSmith = getOrCreateNode("Agent Smith");
            // START SNIPPET: shortestPathUsage
            PathFinder<Path> finder =
                    GraphAlgoFactory.shortestPath(
                            PathExpanders.forTypeAndDirection(KNOWS, Direction.BOTH), 4);
            Path foundPath = finder.findSinglePath(neo, agentSmith);
            System.out.println("Path from Neo to Agent Smith: "
                    + Paths.simplePathToString(foundPath, NAME_KEY));
            // END SNIPPET: shortestPathUsage
        }
        System.out.println("Shutting down database ...");
        graphDb.shutdown();
    }

    private static void createChain(String... names) {
        for (int i = 0; i < names.length - 1; i++) {
            Node firstNode = getOrCreateNode(names[i]);
            Node secondNode = getOrCreateNode(names[i + 1]);
            firstNode.createRelationshipTo(secondNode, KNOWS);
        }
    }

    private static Node getOrCreateNode(String name) {
        Node node = indexService.get(NAME_KEY, name).getSingle();
        if (node == null) {
            node = graphDb.createNode();
            node.setProperty(NAME_KEY, name);
            indexService.add(node, NAME_KEY, name);
        }
        return node;
    }

    private static void registerShutdownHook() {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running example before it's completed)
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }

    private static void deleteFileOrDirectory(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File child : file.listFiles()) {
                    deleteFileOrDirectory(child);
                }
            }
            file.delete();
        }
    }
}
