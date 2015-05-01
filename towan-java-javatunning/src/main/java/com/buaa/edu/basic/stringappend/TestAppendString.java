package com.buaa.edu.basic.stringappend;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * 短字符串连接 做多次
 * 常量连接
 * @author Administrator
 *
 */
public class TestAppendString {

	private static final int CIRCLE = 50000;

	/**
	 * 常量连接
	 */
	@Test
	public void testStringAppend() {
	    long startTime = System.currentTimeMillis();
	    for(int i=0;i<CIRCLE;i++){
	    	String result = "String"+ "and"+ "String"+"append";
	    } 
	    long endTime = System.currentTimeMillis();
	    System.out.println("testStringAppend 花费 ："+(endTime-startTime));
	}

	@Test
	public void testStringAppend2_i() {
	    long startTime = System.currentTimeMillis();
	    String str1="String";
	    String str2="and";
	    String str3="String";
	    String str4="append";
	    for(int i=0;i<50000;i++){
	    	String result = str1+ str2+ str3+str4;
	    } 
	    long endTime = System.currentTimeMillis();
	    System.out.println("testStringAppend2_i 花费 ："+(endTime-startTime));
	}
	
	@Test
	public void testStringAppend2() {
	    long startTime = System.currentTimeMillis();
	    String str1="String";
	    String str2="and";
	    String str3="String";
	    String str4="append";
	    for(int i=0;i<50000;i++){
	    	String result = str1+ str2+ str3+str4;
	    } 
	    long endTime = System.currentTimeMillis();
	    System.out.println("testStringAppend2 花费 ："+(endTime-startTime));
	}

	@Test
	public void testStringConcat() {
	    long startTime = System.currentTimeMillis();
//	    String result;
	    for(int i=0;i<50000;i++){
	        String result="String".concat("and").concat("String").concat("append");
//	    	result = "String"+ "and"+ "String"+"append";
	    } 
	    long endTime = System.currentTimeMillis();
	    System.out.println("testStringConcat 花费 ："+(endTime-startTime));
	}
	
	@Test
	public void testStringBufferAppend() {
		long startTime = System.currentTimeMillis();
	    for(int i=0;i<50000;i++){
	    	StringBuffer result = new StringBuffer();
	        result.append("String");
	        result.append("and");
	        result.append("String");
	        result.append("append");
	     } 
	    long endTime = System.currentTimeMillis();
	    System.out.println("testStringBufferAppend 花费 ："+(endTime-startTime));
	}
	
	@Test
	public void testStringBuilderAppend() {
		long startTime = System.currentTimeMillis();
	    for(int i=0;i<50000;i++){
	    	StringBuilder result = new StringBuilder();
	        result.append("String");
	        result.append("and");
	        result.append("String");
	        result.append("append");
	     } 
	    long endTime = System.currentTimeMillis();
	    System.out.println("testStringBuilderAppend 花费 ："+(endTime-startTime));
	}
	
}
