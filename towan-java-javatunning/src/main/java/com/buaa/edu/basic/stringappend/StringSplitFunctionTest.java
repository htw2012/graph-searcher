package com.buaa.edu.basic.stringappend;

import java.util.StringTokenizer;

import org.junit.Test;

public class StringSplitFunctionTest {

	@Test
	public void testSplit() {
		String str="a;b,c:d";
		String re[]= str.split("[;|,|:]");
		for(String s:re){
			System.out.println(s);
		}
		
		//ÐÞ¸ÄÎªcharAt
	}

	
}
