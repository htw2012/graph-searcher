package com.buaa.edu.basic.map;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import org.junit.Test;

public class MapHashcodeTest {
	public static class GoodHash{
		double d;
		public GoodHash(double d){
			this.d=d;
		}
	}
	public static class BadHash{
		double d;
		public BadHash(double d){
			this.d=d;
		}
		@Override
		public int hashCode(){
			return 1;
		}
	}
	
	Map map;
	final int CIRCLE1=10000;
	
	@Test
	public void testGetGoodHash(){
		map=new HashMap();
		for(int i=0;i<CIRCLE1;i++){
			GoodHash key=new GoodHash(Math.random());
			map.put(key,key);
		}
		long starttime=System.currentTimeMillis();
		for(int i=0;i<CIRCLE1;i++){
			GoodHash key=new GoodHash(Math.random());
			map.get(key);
		}
		long endtime=System.currentTimeMillis();
		System.out.println("testGetGoodHash"+": "+(endtime-starttime));
	}
	
	
	@Test
	public void testGetBadHash(){
		map=new HashMap();
		for(int i=0;i<CIRCLE1;i++){
			BadHash key=new BadHash(Math.random());
			map.put(key,key);
		}
		long starttime=System.currentTimeMillis();
		for(int i=0;i<CIRCLE1;i++){
			BadHash key=new BadHash(Math.random());
			map.get(key);
		}
		long endtime=System.currentTimeMillis();
		System.out.println("testGetBadHash"+": "+(endtime-starttime));
	}
}
