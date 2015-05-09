/*
 * Copyright 北京航空航天大学  @ 2015 版权所有
 */
package com.buaa.edu.concurrent.threadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.buaa.edu.concurrent.threadpool.TestCustomThreadPool.MyThread;

/**
 * <p>
 * 定制的线程池扩展器，便于系统纠错
 * 
 * 扩展其执行前后方法
 * </p>
 * @author towan
 * @email tongwenzide@163.com
 * 2015年5月9日
 */
public class CustomThreadPoolExecutor extends ThreadPoolExecutor{
    public CustomThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
            long keepAliveTime, TimeUnit unit,
            BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }
   
    protected void beforeExecute(Thread t, Runnable r) {
        System.out.println("beforeExecute MyThread Name:"+((MyThread)r).getName()+" TID:"+t.getId());
    }
    
    protected void afterExecute(Runnable r, Throwable t) { 
         System.out.println("afterExecute TID:"+Thread.currentThread().getId());
         System.out.println("afterExecute PoolSize:"+this.getPoolSize());
    }
}

