package com.buaa.edu.jvmtune.memory;

import java.util.Vector;

/**
 * -Xmx11M
 * <p>最大内存</p>
 * @author towan
 * @email tongwenzide@163.com
 * 2015年5月16日
 */
 
public class TestXmx {
	public static void main(String args[]){
		Vector<byte[]> v=new Vector<byte[]>();
		for(int i=1;i<=10;i++){
			byte[] b=new byte[1024*1024]; //1M内存
			v.add(b);
			System.out.println(i+"M is allocated");
		}
		System.out.println("Max memory:"+Runtime.getRuntime().maxMemory()/1024/1024+"M");
	}
}
