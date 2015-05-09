package com.buaa.edu.concurrent.dp.gs.simple;

public class ServerThread extends Thread {
    //请求队列
    private RequestQueue requestQueue;
    public ServerThread(RequestQueue requestQueue, String name) {
        super(name);
        this.requestQueue = requestQueue;
    }
    
    @Override
    public void run() {
        //一直在这里轮询，获得处理的请求
        while (true) {
            //得到请求
            final Request request = requestQueue.getRequest();
            try {
                //模拟请求处理耗时
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            System.out.println(Thread.currentThread().getName() + " handles  " + request);
            
        }
    }
}
