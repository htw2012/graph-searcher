package com.buaa.edu.basic.map;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;


public class TestMapCapacityGet implements java.io.Serializable{
	public static class NotSoGoodHash{
		Double d;
		public NotSoGoodHash(Double d){
			this.d=d;
		}
		@Override
		public int hashCode(){
			return 1;
		}
	}
	
	Map map;

	
	protected void testCapacity(String funcname) {
		for(int i=0;i<100000;i++){
			String key=Double.toString(Math.random());
			map.put(key, key);
		}
		
		Field f;
		int s=0;
		try {
			f = (map.getClass().getDeclaredField("table"));
			f.setAccessible(true);
			s=((Object[])f.get(map)).length;
			f.setAccessible(false);
		} catch (SecurityException e) {
		} catch (NoSuchFieldException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		}
		long starttime=System.currentTimeMillis();
		for(int i=0;i<100000;i++){
			String key=Double.toString(Math.random());
			map.get(key);
		}
		long endtime=System.currentTimeMillis();
		System.out.println(funcname+": "+(endtime-starttime)+" map size:"+s);
		
		
	}
	
	@Test
	public void testDefaultHashMap_1() {
		map=new HashMap();
		testCapacity("testDefaultHashMap_1");
	}
	
	//hashmap
	@Test
	public void testDefaultHashMap() {
		map=new HashMap();
		testCapacity("testDefaultHashMap_16_0.75");
	}
	
	@Test
	public void testLoadHashMap_16_01() {
		map=new HashMap(16,0.1f);
		testCapacity("testLoadHashMap_16_01");
	}
	
	@Test
	public void testCapacityHashMap() {
		map=new HashMap(100000*2);
		testCapacity("testCapacityHashMap 200000 0.75");
	}

	@Test
	public void testCapacityHashMap_200000_01() {
		map=new HashMap(100000*2,0.1f);
		testCapacity("testCapacityHashMap 200000 0.1");
	}
	
	@Test
	public void testCapacityHashMap_16_1() {
		map=new HashMap(16,1);
		testCapacity("testCapacityHashMap_16_1");
	}

	@Test
	public void testCapacityHashMap_200000_1() {
		map=new HashMap(100000*2,1);
		testCapacity("testCapacityHashMap_200000_1");
	}
	//treemap
	@Test
	public void testDefaultTreemap() {
		map=new TreeMap();
		testCapacity("testDefaultTreemap");
	}
	
	//LinkedHashMap
	@Test
	public void testDefaultLinkedHashMap() {
		map=new LinkedHashMap();
		testCapacity("testDefaultLinkedHashMap");
	}
	
}
