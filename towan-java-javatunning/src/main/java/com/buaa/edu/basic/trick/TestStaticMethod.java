package com.buaa.edu.basic.trick;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * 静态方法和实例方法的差别
 */
public class TestStaticMethod {

	public static void staticMethod(){
	}
	public  void instanceMethod(){
	}
	
	@Test
	public void test() {
		int CIRCLE=100000000;
		long start=System.currentTimeMillis();
		for(int i=0;i<CIRCLE;i++){
			staticMethod();
		}
		System.out.println("staticMethod:"+(System.currentTimeMillis()-start));
		start=System.currentTimeMillis();
		for(int i=0;i<CIRCLE;i++){
			instanceMethod();
		}
		System.out.println("instanceMethod:"+(System.currentTimeMillis()-start));
	}
}
