package com.pic.test;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import org.neo4j.kernel.EmbeddedGraphDatabase;



public class Test1 {
	
	 private static enum RelationshipTypes implements RelationshipType { 
		    PUBLISH, CONTAIN 
		 } 

		 public void useNodeAndRelationship() { 
//		    GraphDatabaseService db = new EmbeddedGraphDatabase("music",null,null);
			GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase( "music" );
		    Transaction tx = db.beginTx(); 
		    try { 
		        Node node1 = db.createNode(); 
		        node1.setProperty("name", "���� 1"); 
		        Node node2 = db.createNode(); 
		        node2.setProperty("name", "ר�� 1"); 
		        node1.createRelationshipTo(node2, RelationshipTypes.PUBLISH); 
		        Node node3 = db.createNode(); 
		        node3.setProperty("name", "���� 1"); 
		        node2.createRelationshipTo(node3, RelationshipTypes.CONTAIN); 
		        tx.success(); 
		    } finally { 
		        tx.finish(); 
		    } 
		 }

}
