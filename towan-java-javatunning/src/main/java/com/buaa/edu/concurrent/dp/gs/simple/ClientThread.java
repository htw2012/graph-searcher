package com.buaa.edu.concurrent.dp.gs.simple;


public class ClientThread extends Thread {
      //请求队列
    private RequestQueue requestQueue;
    public ClientThread(RequestQueue requestQueue, String name) {
        super(name);
        this.requestQueue = requestQueue;
    }
    
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            //构造请求
            Request request = new Request("RequestID:" + i+" Thread_Name:"+Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName() + " requests " + request);
            //提交请求
            requestQueue.addRequest(request);
            try {
                Thread.sleep(10);//客户端请求速度，快于服务端处理速度
            } catch (InterruptedException e) {
            }
            System.out.println("ClientThread Name is:"+Thread.currentThread().getName());
        }
        System.out.println(Thread.currentThread().getName()+" request end");
    }
}
