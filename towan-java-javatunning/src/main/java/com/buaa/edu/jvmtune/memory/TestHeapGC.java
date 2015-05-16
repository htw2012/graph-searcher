package com.buaa.edu.jvmtune.memory;

/**
 * 参数 ：-XX:+PrintGCDetails  -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -Xms40M -Xmx40M -Xmn20M
 * <p>了解对象在堆的存放</p>
 * @author towan
 * @email tongwenzide@163.com
 * 2015年5月16日
 */
public class TestHeapGC {
	@SuppressWarnings("unused")
    public static void main(String args[]){
		byte[] b1=new byte[1024*1024/2];
		byte[] b2=new byte[1024*1024*8];
		b2=null;
		b2=new byte[1024*1024*8];
		System.gc();
		//b1会进入to space还是from space
	}
}
