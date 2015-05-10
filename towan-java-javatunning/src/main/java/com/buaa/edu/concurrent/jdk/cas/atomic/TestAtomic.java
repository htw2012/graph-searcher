package com.buaa.edu.concurrent.jdk.cas.atomic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class TestAtomic {
	private static final int MAX_THREADS = 3;
	private static final int TASK_COUNT = 3;
	private static final int TARGET_COUNT = 1000000;
	private AtomicInteger acount =new AtomicInteger(0);
	private int count=0;
	//有锁的加法
	protected synchronized int inc(){
		return ++count;
	}
	//有锁的获得总数
	protected synchronized int getCount(){
		return count;
	}
	
	public class SyncThread implements Runnable{
		protected String name;
		protected long starttime;
		TestAtomic out;
		public SyncThread(TestAtomic o,long starttime){
			out=o;
			this.starttime=starttime;
		}
		@Override
		public void run() {
			int v=out.inc();
			while(v<TARGET_COUNT){
				v=out.inc();
			}
			long endtime=System.currentTimeMillis();
			System.out.println("SyncThread spend:"+(endtime-starttime)+"ms"+" v="+v);
		}
	}
	
	public class AtomicThread implements Runnable{
		protected String name;
		protected long starttime;
		public AtomicThread(long starttime){
			this.starttime=starttime;
		}
		@Override
		public void run() {
		    //在到达目标之前不断的循环
			int v=acount.incrementAndGet();
			while(v<TARGET_COUNT){
			    //无锁的加法
			    v=acount.incrementAndGet();
			}
			long endtime=System.currentTimeMillis();
			System.out.println("AtomicThread spend:"+(endtime-starttime)+"ms"+" v="+v);
		}
	}
	
	@Test //整数的累加操作
	public void testAtomic() throws InterruptedException{
		ExecutorService exe=Executors.newFixedThreadPool(MAX_THREADS);
		long starttime=System.currentTimeMillis();
		AtomicThread atomic=new AtomicThread(starttime);
		for(int i=0;i<TASK_COUNT;i++){
			exe.submit(atomic);
		}
		Thread.sleep(10000);
	}

	@Test
	public void testSync() throws InterruptedException{
		ExecutorService exe=Executors.newFixedThreadPool(MAX_THREADS);
		long starttime=System.currentTimeMillis();
		SyncThread sync=new SyncThread(this,starttime);
		for(int i=0;i<TASK_COUNT;i++){
			exe.submit(sync);
		}
		Thread.sleep(10000);
	}
}
