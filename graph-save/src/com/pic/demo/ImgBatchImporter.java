/*
 * Copyright 北京航空航天大学  @ 2015 版权所有
 */
package com.pic.demo;

import java.util.HashMap;
import java.util.Map;

//import org.neo4j.cypher.internal.compiler.v2_2.symbols.RelationshipType;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.Label;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserters;

import com.google.common.collect.Maps;

/**
 * <p>
 * 图片的批量到导入工具
 * From:
 * <http://neo4j.com/docs/2.1.6/javadocs/org/neo4j/unsafe/batchinsert/BatchInserters.html
 * </p>
 * @author towan
 * @Email  tongwenzide@163.com
 * 2015年1月21日下午8:34:32
 *
 * @version V1.0  
 */
public class ImgBatchImporter {

    public static void main(String[] args) {
        //need do
        Map<String, String> fileSystem = Maps.newHashMap();
        BatchInserter inserter = BatchInserters.inserter( "target/batchinserter-example", fileSystem  );
        Label personLabel = DynamicLabel.label( "Person" );
        inserter.createDeferredSchemaIndex( personLabel ).on( "name" ).create();
        Map<String, Object> properties = new HashMap<>();
        properties.put( "name", "Mattias" );
        long mattiasNode = inserter.createNode( properties, personLabel );
        properties.put( "name", "Chris" );
        long chrisNode = inserter.createNode( properties, personLabel );
        RelationshipType knows = DynamicRelationshipType.withName( "KNOWS" );
        // To set properties on the relationship, use a properties map
        // instead of null as the last parameter.
        inserter.createRelationship( mattiasNode, chrisNode, (org.neo4j.graphdb.RelationshipType) knows, null );
        inserter.shutdown();
    }
}
