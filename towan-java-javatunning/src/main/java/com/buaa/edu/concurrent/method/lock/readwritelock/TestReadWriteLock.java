package com.buaa.edu.concurrent.method.lock.readwritelock;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.junit.Test;

public class TestReadWriteLock {
	private static final int MAX_THREADS = 2000;
	private static final int TASK_COUNT = 4000;
	private static Lock lock=new ReentrantLock();
	private static ReentrantReadWriteLock readWriteLock=new ReentrantReadWriteLock();
	private static Lock readLock = readWriteLock.readLock();
	private static Lock writeLock = readWriteLock.writeLock();
	
	Random rand=new Random();
	
	private int value;
	/**
	 * 
	 * <p>重入锁的读写</p>
	 * @author towan
	 * @email tongwenzide@163.com
	 * 2015年5月10日
	 */
	public class ReadWriteThread implements Runnable{
		protected String name;
		
		public ReadWriteThread(){
		}
		public ReadWriteThread(String name){
			this.name=name;
		}
		@Override
		public void run() {
			try {
				handleRead();
				int v=rand.nextInt(100);
				if(v<10)
					handleWrite(v);
				Thread.sleep(rand.nextInt(100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * <p>读写锁的读写</p>
	 * @author towan
	 * @email tongwenzide@163.com
	 * 2015年5月10日
	 */
	public class ReadWriteThread2 implements Runnable{
		protected String name;
		
		public ReadWriteThread2(){
		}
		public ReadWriteThread2(String name){
			this.name=name;
		}
		@Override
		public void run() {
			try {
				handleRead2();//读
				int v = rand.nextInt(100);
				if(v<10)
					handleWrite2(v);//写
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
	//重入锁读
	public Object handleRead() throws InterruptedException{
		try{
			lock.lock();
			Thread.sleep(1);
			return value;//模拟读
		}finally{
		lock.unlock();
		}
	}
	//重入锁写
	public void handleWrite(int index) throws InterruptedException{
		try{
			lock.lock();
			Thread.sleep(1);
			value=index;//模拟写
		}finally{
		lock.unlock();
		}
	}
	//读写锁读方式
	public Object handleRead2() throws InterruptedException{
		try{
			readLock.lock();
			Thread.sleep(1);
			return value;
		}finally{
			readLock.unlock();
		}
	}
	//读写锁写操作
	public void handleWrite2(int index) throws InterruptedException{
		try{
			writeLock.lock();
			Thread.sleep(1);
			value=index;
		}finally{
			writeLock.unlock();
		}
	}
	
	@Test//重入锁
	public void testLock() throws InterruptedException {
	
		CounterPoolExecutor exe=new CounterPoolExecutor(MAX_THREADS, MAX_THREADS,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

		long starttime=System.currentTimeMillis();
		exe.startTime=starttime;
		exe.funcname="testLock";
		for(int i=0;i<TASK_COUNT;i++)
			exe.submit(new ReadWriteThread());
		
		Thread.sleep(100000);
	}
	
	@Test//读写锁
	public void testLock2() throws InterruptedException {
	
		CounterPoolExecutor exe=new CounterPoolExecutor(MAX_THREADS, MAX_THREADS,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

		long starttime=System.currentTimeMillis();
		exe.startTime=starttime;
		exe.funcname="testLock2";
		for(int i=0;i<TASK_COUNT;i++)
			exe.submit(new ReadWriteThread2());
		
		Thread.sleep(100000);
	}

}
