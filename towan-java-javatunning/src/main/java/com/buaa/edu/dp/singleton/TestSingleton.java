package com.buaa.edu.dp.singleton;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.Assert;

import org.junit.Test;

public class TestSingleton {

	@Test
	public void test() {
		Singleton s=Singleton.getInstance();
		Singleton s1=null;
		Class<Singleton> singletonClass = Singleton.class;
		Constructor<Singleton> cons;
		try {
			cons = singletonClass.getDeclaredConstructor(null);
			cons.setAccessible(true);
			s1 = cons.newInstance(null);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {}
		Assert.assertNotSame(s, s1);
	}
	
	public static class AccessSingletonThread extends Thread{
		long begintime;
		public AccessSingletonThread(long begintime){
			this.begintime=begintime;
		}
		@Override
		public void run(){
			System.out.println("try to get instance");
			for(int i=0;i<100000;i++)
//				Singleton.getInstance();
//				LazySingleton.getInstance();
//			LazySingleton.getInstance2();
			    StaticSingleton.getInstance();
			System.out.println("spend:"+(System.currentTimeMillis()-begintime));	
		}
	}
	
	@Test
	public void testPerformance() throws InterruptedException{
		ExecutorService exe=Executors.newFixedThreadPool(5);
		long begintime=System.currentTimeMillis();
		exe.submit(new AccessSingletonThread(begintime));
		exe.submit(new AccessSingletonThread(begintime));
		exe.submit(new AccessSingletonThread(begintime));
		exe.submit(new AccessSingletonThread(begintime));
		exe.submit(new AccessSingletonThread(begintime));
		
		Thread.sleep(10000);
	}
	
	@Test
	public void testSingleton(){
		StaticSingleton.createString();
		StaticSingleton.getInstance();
	}

}
