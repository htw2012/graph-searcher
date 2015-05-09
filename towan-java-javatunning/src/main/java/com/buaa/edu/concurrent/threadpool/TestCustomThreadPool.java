package com.buaa.edu.concurrent.threadpool;

//import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicLong;

import org.junit.Test;
/**
 * 
 * <p>定值线程池，扩展其方法，便于调错</p>
 * @author towan
 * @email tongwenzide@163.com
 * 2015年5月9日
 */
public class TestCustomThreadPool {
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
				System.out.println(name+" in run"+" "+Thread.currentThread().getId());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public String getName(){
			return name;
		}
	}
	
	
	@Test
	public void testThreadPoolExecutor1() throws InterruptedException {

		long starttime=System.currentTimeMillis();
		//ExecutorService exe=new MyThreadPoolExecutor(100,600,0L,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(200));
		ExecutorService exe=new CustomThreadPoolExecutor(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
		for(int i=0;i<1000;i++){
			exe.execute(new MyThread("testThreadPoolExecutor1_"+Integer.toString(i)));
		}
		System.out.println();
		long endtime=System.currentTimeMillis();
		System.out.println("testThreadPoolExecutor1"+": "+(endtime-starttime));
		System.out.println("testThreadPoolExecutor1 exe size"+": "+((ThreadPoolExecutor)exe).getPoolSize());
		Thread.sleep(1000*101);
	}
	
}
