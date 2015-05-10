package com.buaa.edu.concurrent.method.threadlocal;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

public class TestThreadLocal {


	@Test
	public void testThreadLocal() throws InterruptedException {
		ExecutorService exe=Executors.newCachedThreadPool();
		exe.submit(new MyThread(1));
		exe.submit(new MyThread(2));
		Thread.sleep(10000);
	}

}
