package com.buaa.edu.concurrent.method.volatitle;

import org.junit.Test;

public class VolatileTest {
	
    volatile boolean isExit;

	public void tryExit() {
	  //成立则退出 FIXME 思考这样的情况
		if (isExit == !isExit)
			System.exit(0);
	}

	public void swapValue() {
		isExit = !isExit;
	}

	@Test
	public void test() throws InterruptedException {
		final VolatileTest volObj = new VolatileTest();
		Thread mainThread = new Thread() {
			public void run() {
				System.out.println("mainThread start");
				while(true) {
				    //不停尝试是否可以退出
					volObj.tryExit();
				}
			}
		};
		mainThread.start();
		
		Thread swapThread = new Thread() {
			public void run() {
				System.out.println("swapThread start");
				while(true) {
					volObj.swapValue(); //不停修改isExit
				}
			}
		};
		swapThread.start();
		Thread.sleep(10000);
	}
}