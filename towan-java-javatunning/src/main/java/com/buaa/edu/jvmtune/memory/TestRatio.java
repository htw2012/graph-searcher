package com.buaa.edu.jvmtune.memory;

import java.util.Vector;

/**
-XX:+PrintGCDetails  -Xmn10M -XX:SurvivorRatio=8
 -XX:+PrintGCDetails -XX:NewRatio=2 -Xmx20M -Xms20M
 *
 */
public class TestRatio {
	public static void main(String args[]){
		Vector<byte[]> v=new Vector<>();
		for(int i=1;i<=10;i++){
			byte[] b=new byte[1024*1024];
			v.add(b);
			if(v.size()==3)
				v.clear();
		}
	}
}
