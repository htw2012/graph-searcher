package com.buaa.edu.concurrent.dp.gs.threadpool.singlepool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class TestSingleThreadPool {
	public class MyThread implements Runnable{
		protected String name;
		public MyThread(){
		}
		public MyThread(String name){
			this.name=name;
		}
		@Override
		public void run() {
			try {
				Thread.sleep(100);
				System.out.println(name+" ");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//@Test
	public void testSingle() throws InterruptedException {
		long starttime=System.currentTimeMillis();
		ExecutorService exe=Executors.newSingleThreadExecutor();
		for(int i=0;i<1000;i++){
			exe.execute(new MyThread("testSingle"+Integer.toString(i)));
		}
		System.out.println();
		long endtime=System.currentTimeMillis();
		System.out.println("testSingle"+": "+(endtime-starttime));
		Thread.sleep(1000*101);
	}
	
	@Test
	public void test(){
		System.out.println(Runtime.getRuntime().availableProcessors());
	}

}
