package com.buaa.edu.concurrent.lockfree.amino;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.amino.ds.lockfree.LockFreeList;
import org.amino.ds.lockfree.LockFreeVector;
import org.junit.Test;

public class TestLockFreeList {
	private static final int MAX_THREADS = 2000;
	private static final int TASK_COUNT = 4000;
	List<Integer> list;
	
	public class AccessListThread implements Runnable{
		protected String name;
		Random rand=new Random();
		public AccessListThread(){
		}
		public AccessListThread(String name){
			this.name=name;
		}
		@Override
		public void run() {
			try {
				for(int i=0;i<1000;i++)
					handleList(rand.nextInt(1000));
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
	
	public Object handleList(int index){
	    //增加和删除操作
		list.add(index);
		list.remove(index%list.size());
		return null;
	}
	
	public void initLinkedList(){
		List<Integer> l=new ArrayList<Integer>();
		for(int i=0;i<1000;i++)
			l.add(i);
		list=Collections.synchronizedList(new LinkedList<Integer>(l));
	}

	public void initVector(){
		List<Integer> l=new ArrayList<Integer>();
		for(int i=0;i<1000;i++)
			l.add(i);
		list=new Vector<Integer>(l);
	}
	
	public void initFreeLockList(){
		List<Integer> l=new ArrayList<Integer>();
		for(int i=0;i<1000;i++)
			l.add(i);
		list= new LockFreeList<Integer>();
//		list.addAll(l);
	}
	
	public void initFreeLockVector(){
		list=new LockFreeVector<Integer>();
		for(int i=0;i<1000;i++)
			list.add(i);
	}
	
	@Test
	public void testFreeLockList() throws InterruptedException {
		initFreeLockList();
		CounterPoolExecutor exe=new CounterPoolExecutor(MAX_THREADS, MAX_THREADS,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

		long starttime=System.currentTimeMillis();
		exe.startTime=starttime;
		exe.funcname="testFreeLockList";
		//对每个List做1000次增加和删除操作
		Runnable t=new AccessListThread();
		for(int i=0;i<TASK_COUNT;i++)
			exe.submit(t);
		
		Thread.sleep(10000);
	}

	@Test
	public void testLinkedList() throws InterruptedException {
		initLinkedList();
		CounterPoolExecutor exe=new CounterPoolExecutor(MAX_THREADS, MAX_THREADS,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

		long starttime=System.currentTimeMillis();
		exe.startTime=starttime;
		exe.funcname="testLinkedList";
		Runnable t=new AccessListThread();
		for(int i=0;i<TASK_COUNT;i++)
			exe.submit(t);
		
		Thread.sleep(10000);
	}
	
	@Test
	public void testVector() throws InterruptedException {
		initVector();
		CounterPoolExecutor exe=new CounterPoolExecutor(MAX_THREADS, MAX_THREADS,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

		long starttime=System.currentTimeMillis();
		exe.startTime=starttime;
		exe.funcname="testVector";
		Runnable t=new AccessListThread();
		for(int i=0;i<TASK_COUNT;i++)
			exe.submit(t);
		
		Thread.sleep(10000);
	}
	
	@Test
	public void testFreeLockVector() throws InterruptedException {
		initFreeLockVector();
		CounterPoolExecutor exe=new CounterPoolExecutor(MAX_THREADS, MAX_THREADS,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

		long starttime=System.currentTimeMillis();
		exe.startTime=starttime;
		exe.funcname="testFreeLockVector";
		Runnable t=new AccessListThread();
		for(int i=0;i<TASK_COUNT;i++)
			exe.submit(t);
		
		Thread.sleep(10000);
	}
}
