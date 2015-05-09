package com.buaa.edu.concurrent.threadpool.simple;

public class PThread extends Thread {
    // 线程池
    private ThreadPool pool;
    // 任务
    private Runnable target;
    private boolean isShutDown = false;
    private boolean isIdle = false;

    public PThread(Runnable target, String name, ThreadPool pool) {
        super(name);
        this.pool = pool;
        this.target = target;
    }

    public Runnable getTarget() {
        return target;
    }

    public boolean isIdle() {
        return isIdle;
    }
    @Override
    public void run() {
        //只要没有关闭就一直运行
        while (!isShutDown) {
            isIdle = false;
            if (target != null) {
                //运行任务
                target.run();
            }
            // 任务结束，设置为闲置状态
            isIdle = true;
            try {
                // 放入空闲队列
                pool.repool(this);
                
                synchronized (this) {
                    // 线程空闲，等待新的任务到来
                    wait();
                }
            } catch (InterruptedException ie) {}
            isIdle = false;
        }
    }


    public synchronized void setTarget(Runnable newTarget) {
        target = newTarget;
        //设置任务之后，通知run方法，开始执行任务
        notifyAll();
    }
    //关闭线程
    public synchronized void shutDown() {
        isShutDown = true;
        notifyAll();
    }
}
