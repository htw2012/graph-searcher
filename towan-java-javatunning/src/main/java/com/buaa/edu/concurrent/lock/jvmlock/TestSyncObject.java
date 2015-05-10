package com.buaa.edu.concurrent.lock.jvmlock;

import org.junit.Test;

public class TestSyncObject {
	public static Object lock=new Object();
	
	public static final int CIRCLE=10000000;
	@Test
	public void test() {
		long begintime=System.currentTimeMillis();
		for(int i=0;i<CIRCLE;i++){
			synchronized(lock){
				
			}
		}
		long endtime=System.currentTimeMillis();
		System.out.println("sync in loop:"+(endtime-begintime));
	}
	
	@Test//锁粗化
	public void test1() {
		long begintime=System.currentTimeMillis();
		synchronized(lock){
		for(int i=0;i<CIRCLE;i++){
				
			}
		}
		long endtime=System.currentTimeMillis();
		System.out.println("sync out loop:"+(endtime-begintime));
	}
	
	public void demoMethod(){
		synchronized(lock){
			//do sth.
		}
		//做其他不需同步工作，但是完成很快
		synchronized(lock){
			//do sth.
		}
	}

}
