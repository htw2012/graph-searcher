package com.buaa.edu.basic.list;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

/**
 *-Xmx512M -Xms512M 使用这个参数对测试结果有一定影响 
 * @author Administrator
 *
 */
public class TestListCapacity {
	protected List list;
	
	protected void testAddTail(String funcname){
		Object obj=new Object();
		long starttime=System.currentTimeMillis();
		
		for(int i=0;i<1000000;i++){
			list.add(obj);
		}
		long endtime=System.currentTimeMillis();
		System.out.println(funcname+": "+(endtime-starttime));
	}
	
	@Test
	public void testAddTailArrayList() {
		list=new ArrayList();
		testAddTail("testAddTailArrayList");
	}	
	
	@Test
	public void testAddTailArrayListCapacity() {
		list=new ArrayList(1000000);
		testAddTail("testAddTailArrayListCapacity");
	}
	
}
