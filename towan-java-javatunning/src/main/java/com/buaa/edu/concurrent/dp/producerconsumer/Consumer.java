package com.buaa.edu.concurrent.dp.producerconsumer;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

// import java.util.concurrent.TimeUnit;
/**
 * 
 * <p>计算平方的消费者</p>
 * @author towan
 * @email tongwenzide@163.com
 * 2015年5月9日
 */
public class Consumer implements Runnable {
    // 缓冲区
    private BlockingQueue<TaskData> queue;
    private static final int SLEEPTIME = 1000;

    public Consumer(BlockingQueue<TaskData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("start Consumer id=" + Thread.currentThread().getId());
        Random r = new Random();

        try {
            while (true) {
                //提取任务
                TaskData data = queue.take();
                if (null != data) {
                    //真实计算任务
                    int re = data.getData() * data.getData();
                    System.out.println(MessageFormat.format("{0}*{1}={2}", data.getData(),
                            data.getData(), re));
                    //随机等待时间
                    Thread.sleep(r.nextInt(SLEEPTIME));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
