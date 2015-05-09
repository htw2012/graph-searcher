package com.buaa.edu.concurrent.dp.producerconsumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Producer implements Runnable {
	private volatile boolean isRunning = true;
	//�ڴ滺����
	private BlockingQueue<TaskData> queue;
	//������ԭ�Ӳ���
	private static AtomicInteger count = new AtomicInteger();
	private static final int SLEEPTIME = 1000;

	public Producer(BlockingQueue<TaskData> queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		TaskData data = null;
		Random r = new Random();

		System.out.println("start producer id="+Thread.currentThread().getId());
		try {
			while (isRunning) {
				Thread.sleep(r.nextInt(SLEEPTIME));
				//������������ ��ԭ�Ӳ�����1
				data = new TaskData(count.incrementAndGet());
				System.out.println(data+" is put into queue");
				//�����ض������ݵ�������
				if (!queue.offer(data, 2, TimeUnit.SECONDS)) {
					System.err.println("failed to put data ��" + data);
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
	}
	public void stop() {
		isRunning = false;
	}
}