package com.buaa.edu.basic.stringappend;

import static org.junit.Assert.*;

import java.util.StringTokenizer;

import org.junit.Test;

public class StrCharAtStartTest {

	@Test
	public void testCharAt() {
		String orgStr=null;
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<1000;i++){
			sb.append(i);
			sb.append(";");
		}
		orgStr=sb.toString();
		int len=orgStr.length();
		
		long begintime=System.currentTimeMillis();
		for(int i=0;i<1000000;i++){
			if(orgStr.charAt(0)=='a'
					&&orgStr.charAt(1)=='b'
					&&orgStr.charAt(2)=='c');
			if(orgStr.charAt(len-1)=='a'
					&&orgStr.charAt(len-2)=='b'
					&&orgStr.charAt(len-3)=='c');
		}
		long endtime=System.currentTimeMillis();
		
		System.out.println("testCharAt 花费:"+(endtime-begintime)+" ms");
	}
	
	@Test
	public void testStartwith() {
		String orgStr=null;
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<1000;i++){
			sb.append(i);
			sb.append(";");
		}
		orgStr=sb.toString();
		int len=orgStr.length();
		
		long begintime=System.currentTimeMillis();
		for(int i=0;i<1000000;i++){
			orgStr.startsWith("abc");
			orgStr.endsWith("abc");
		}
		long endtime=System.currentTimeMillis();
		
		System.out.println("testStartwith 花费:"+(endtime-begintime)+" ms");
	}
}
