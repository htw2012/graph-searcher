package com.buaa.edu.concurrent.dp.future.jdk;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
    	//构造FutureTask
        RealData realData = new RealData("a");
        FutureTask<String> future = new FutureTask<String>(realData);

        //        future.run(); 单线程执行
        //使用线程池执行
        ExecutorService executor = Executors.newFixedThreadPool(1);
        //执行FutureTask，相当于上例中的 client.request("a") 发送请求
        //在这里开启线程进行RealData的call()执行
        executor.submit(future);
        System.out.println("请求完毕");
        
        
        try {
        //这里依然可以做额外的数据操作，这里使用sleep代替其他业务逻辑的处理
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        
        
        //相当于上例中得data.getContent()，取得call()方法的返回值
        //如果此时call()方法没有执行完成，则依然会等待
        String data = future.get();
        System.out.println("数据 = " + data);
    }
}
