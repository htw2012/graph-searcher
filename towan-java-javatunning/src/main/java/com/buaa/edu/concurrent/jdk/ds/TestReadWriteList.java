package com.buaa.edu.concurrent.jdk.ds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class TestReadWriteList {
    //最大线程数
	private static final int MAX_THREADS = 2000;
	private static final int TASK_COUNT = 4000;
	
	List<Integer> list;
	
	public class AccessListThread implements Runnable{
		protected String name;
		Random rand =new Random();
		public AccessListThread(){
		}
		public AccessListThread(String name){
			this.name=name;
		}
		@Override
		public void run() {
			try {
			    //
				handleList(rand.nextInt(1000));
				Thread.sleep(rand.nextInt(100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * <p>
	 * 实现一个线程池用于实现性能数据统计
	 * 测试数据：最大线程数 2000
	 * </p>
	 * @author towan
	 * @email tongwenzide@163.com
	 * 2015年5月9日
	 */
	public class CounterPoolExecutor extends ThreadPoolExecutor{
	    //统计执行次数
		private AtomicInteger count =new AtomicInteger(0);
		public long startTime=0;
		//任务名称
		public String funcname="";
		
		public CounterPoolExecutor(int corePoolSize, int maximumPoolSize,
				long keepAliveTime, TimeUnit unit,
				BlockingQueue<Runnable> workQueue) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		}
		
		protected void afterExecute(Runnable r, Throwable t) { 
			//每次任务执行完成就加1
		    int l=count.addAndGet(1);
			//完成任务达到预期值
		    if(l==TASK_COUNT){
				System.out.println(funcname+" spend time:"+(System.currentTimeMillis()-startTime));
			}
		}
	}
	
	public Object handleList(int index){
		list.add(index);
		return null;
	}
	
	public void initListCopyOnWriteContent(){
		List<Integer> l=new ArrayList<Integer>();
		for(int i=0;i<1000;i++){
		    l.add(i);
		}
		list=new CopyOnWriteArrayList<Integer>(l);
	}
	
	public void initVector(){
		List<Integer> l=new ArrayList<Integer>();
		for(int i=0;i<1000;i++)
			l.add(i);
		list=new Vector<Integer>(l);
	}
	
	@Test
	public void testWriteCopyOnWriteList() throws InterruptedException {
		initListCopyOnWriteContent();
		CounterPoolExecutor exe=new CounterPoolExecutor(MAX_THREADS, MAX_THREADS,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

		long starttime = System.currentTimeMillis();
		exe.startTime=starttime;
		exe.funcname="testGetCopyOnWriteList";
		for(int i=0;i<TASK_COUNT;i++){
		    exe.submit(new AccessListThread());
		}
		
		Thread.sleep(10000);
	}

	@Test
	public void testWriteVector() throws InterruptedException {
		initVector();
		CounterPoolExecutor exe=new CounterPoolExecutor(MAX_THREADS, MAX_THREADS,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

		long starttime=System.currentTimeMillis();
		exe.startTime=starttime;
		exe.funcname="testGetVector";
		for(int i=0;i<TASK_COUNT;i++)
			exe.submit(new AccessListThread());
		
		Thread.sleep(1000);
	}
}
