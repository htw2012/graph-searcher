package com.buaa.edu.basic.stringappend;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 长字符长连接 StringBuffer 和StringBuilder比较
 * @author Administrator
 *
 */
public class TestStringBuffer {
	static int CIRCLE=500000;
	
	@Test
	public void testStringPlus() {
		String str="";
		long begintime=System.currentTimeMillis();
	     
	     for(int i=0;i<CIRCLE;i++){
	    	 str=str+i;
	    	 //str+=i;
	     }
		long endtime=System.currentTimeMillis();
		
		System.out.println("testStringPlus 花费:"+(endtime-begintime)+" ms");
	}
	
	//@Test
	public void testStringConcat() {
	    long startTime = System.currentTimeMillis();
	    String result="";
	    int c=CIRCLE;///10;
	    for(int i=0;i<CIRCLE;i++){
	    	result=result.concat(String.valueOf(i));
	    } 
	    long endTime = System.currentTimeMillis();
	    System.out.println("testStringConcat 花费 ："+(endTime-startTime));
	}
	
	@Test
	public void testStringBuffer() {
		StringBuffer sb=new StringBuffer();
		long begintime=System.currentTimeMillis();
	     
	     for(int i=0;i<CIRCLE;i++){
	    	 sb.append(i);
	     }
	     sb.toString();
		long endtime=System.currentTimeMillis();
		
		System.out.println("testStringBuffer 花费:"+(endtime-begintime)+" ms");
	}

	@Test
	public void testStringBuilder() {
		StringBuilder sb=new StringBuilder();
		long begintime=System.currentTimeMillis();
	     
	     for(int i=0;i<CIRCLE;i++){
	    	 sb.append(i);
	     }
	     sb.toString();
		long endtime=System.currentTimeMillis();
		
		System.out.println("testStringBuilder 花费:"+(endtime-begintime)+" ms");
	}
	
	@Test
	public void testStringBuffer10() {
		StringBuffer sb=new StringBuffer(5888890);
		long begintime=System.currentTimeMillis();
	     for(int i=0;i<CIRCLE;i++){
	    	 sb.append(i);
	     }
	     sb.toString();
		long endtime=System.currentTimeMillis();
		System.out.println("testStringBuffer10 花费:"+(endtime-begintime)+" ms");
	}

	@Test
	public void testStringBuilder10() {
		StringBuilder sb=new StringBuilder(5888890);
		long begintime=System.currentTimeMillis();
	     
	     for(int i=0;i<CIRCLE;i++){
	    	 sb.append(i);
	     }
	     sb.toString();
		long endtime=System.currentTimeMillis();
		
		System.out.println("testStringBuilder10  花费:"+(endtime-begintime)+" ms");
	}
}
