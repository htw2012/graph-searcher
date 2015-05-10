package com.buaa.edu.concurrent.method.volatitle;

import org.junit.Test;

public class VolatileTest {
	//确保该变量在多线程可见
    volatile boolean isExit;

	public void tryExit() {
	  //swap线程对isExit修改很快的被tryExit发现，因为它设法向主内存区中获取数据，当它获取等式左边、右边的数据前swap线程可能已经把数据修改了
		if (isExit == !isExit){
		    System.out.println("Swap thread has changed value");
		    System.exit(0);
		}
		
	}

	public void swapValue() {
		isExit = !isExit;
	}

	@Test
	public void test() throws InterruptedException {
		final VolatileTest volObj = new VolatileTest();
		//线程1
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
		
		//线程2
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