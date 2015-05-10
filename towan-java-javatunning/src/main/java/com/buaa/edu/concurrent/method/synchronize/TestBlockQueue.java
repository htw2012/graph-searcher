package com.buaa.edu.concurrent.method.synchronize;

import java.util.Random;

public class TestBlockQueue {
    
	static BlockQueue queue = new BlockQueue();

	public static class PutThread extends Thread {
		@Override
		public void run() {
			System.out.println(System.currentTimeMillis() + ":"
					+ Thread.currentThread().getName() + " START");
			//一直放入元素
			while (true) {
				Object obj = new Object();
				queue.put(obj);
				System.out.println(System.currentTimeMillis() + ":"
						+ Thread.currentThread().getName() + " PUT OBJECT");
				try {
					Thread.sleep(new Random().nextInt(2000));//放入比较慢
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static class PopThread extends Thread {
		@Override
		public void run() {
			System.out.println(System.currentTimeMillis() + ":"
					+ Thread.currentThread().getName() + " START");
			while (true) {
				try {
					Object obj =   queue.pop();
					if (obj != null)
						System.out.println(System.currentTimeMillis() + ":"
								+ Thread.currentThread().getName()
								+ " Get Object");
					else
						System.out.println(System.currentTimeMillis() + ":"
								+ Thread.currentThread().getName()
								+ " Get NULL");
					Thread.sleep(new Random().nextInt(1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String args[]) throws InterruptedException {
		PopThread pop1 = new PopThread();
		pop1.setName("POP_Thread_1");
		pop1.start();
		PopThread pop2 = new PopThread();
		pop2.setName("POP_Thread_2");
		pop2.start();
		Thread.sleep(1000);
		PutThread put = new PutThread();
		put.setName("Put_Thread");
		put.start();
	}

}
