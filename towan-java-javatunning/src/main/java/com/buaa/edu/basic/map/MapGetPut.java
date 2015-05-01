package com.buaa.edu.basic.map;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.junit.Test;

public class MapGetPut {
	Map map;
	final int CIRCLE1=100000;
	
	protected void testGet(String funcname){
		for(int i=0;i<CIRCLE1;i++){
			String key=Double.toString(Math.random());
			map.put(key,key);
		}
		long starttime=System.currentTimeMillis();
		for(int i=0;i<CIRCLE1;i++){
			String key=Double.toString(Math.random());
			map.get(key);
		}
		long endtime=System.currentTimeMillis();
		System.out.println(funcname+": "+(endtime-starttime));
	}
	
	@Test
	public void testHashtableGet(){
		map=new Hashtable();
		testGet("testHashtableGet");
	}
	
	@Test
	public void testSyncHashMapGet(){
		map=Collections.synchronizedMap(new HashMap());
		testGet("testSyncHashMapGet");
	}
	
	@Test
	public void testHashMapGet(){
		map=new HashMap();
		testGet("testHashMapGet");
	}
}
