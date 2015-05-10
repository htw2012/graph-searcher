package com.buaa.edu.concurrent.lockfree.amino;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.amino.ds.lockfree.LockFreeSet;
import org.junit.Test;

public class TestLockFreeSet {
	private static final int MAX_THREADS = 2000;
	private static final int TASK_COUNT = 4000;
	java.util.Random rand=new java.util.Random();
	
	Set<Integer> set;
	
	public class AccessSetThread implements Runnable{
		protected String name;
		
		public AccessSetThread(){
		}
		public AccessSetThread(String name){
			this.name=name;
		}
		@Override
		public void run() {
			try {
				for(int i=0;i<500;i++)
					handleSet(rand.nextInt(1000));
				Thread.sleep(rand.nextInt(100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public class CounterPoolExecutor extends ThreadPoolExecutor{
		private AtomicInteger count =new AtomicInteger(0);
		public long startTime=0;
		public String funcname="";
		public CounterPoolExecutor(int corePoolSize, int maximumPoolSize,
				long keepAliveTime, TimeUnit unit,
				BlockingQueue<Runnable> workQueue) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		}
		
		protected void afterExecute(Runnable r, Throwable t) { 
			int l=count.addAndGet(1);
			if(l==TASK_COUNT){
				System.out.println(funcname+" spend time:"+(System.currentTimeMillis()-startTime));
			}
		}
	}
	
	public Object handleSet(int index){
		set.add(rand.nextInt(2000));
		if(set.size()>10000)set.clear();
		return null;
	}
	
	public void initSet(){
		set=Collections.synchronizedSet(new HashSet<Integer>());

	}
	
	public void initFreeLockSet(){
		set=new LockFreeSet<Integer>();

	}
	
	//@Test
	public void testSet() throws InterruptedException {
		initSet();
		CounterPoolExecutor exe=new CounterPoolExecutor(MAX_THREADS, MAX_THREADS,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

		long starttime=System.currentTimeMillis();
		exe.startTime=starttime;
		exe.funcname="testSet";
		for(int i=0;i<TASK_COUNT;i++)
			exe.submit(new AccessSetThread());
		
		Thread.sleep(10000);
	}
	
	@Test
	public void testLockFreeSet() throws InterruptedException {
		initFreeLockSet();
		CounterPoolExecutor exe=new CounterPoolExecutor(MAX_THREADS, MAX_THREADS,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

		long starttime=System.currentTimeMillis();
		exe.startTime=starttime;
		exe.funcname="testLockFreeSet";
		for(int i=0;i<TASK_COUNT;i++)
			exe.submit(new AccessSetThread());
		
		Thread.sleep(10000);
	}
}
