package com.buaa.edu.concurrent.dp.gs.future;

import com.buaa.edu.concurrent.dp.future.FutureData;
import com.buaa.edu.concurrent.dp.future.RealData;


public class ServerThread extends Thread {
    private RequestQueue requestQueue;
    public ServerThread(RequestQueue requestQueue, String name) {
        super(name);
        this.requestQueue = requestQueue;
    }
    public void run() {
        while (true) {
            final Request request = requestQueue.getRequest();
            final FutureData future =   (FutureData)request.getResponse();
            //RealData创建比较耗时
            RealData realdata = new RealData(request.getName());
            //处理完成，通知客户进程
            future.setRealData(realdata);
            System.out.println(Thread.currentThread().getName() + " handles  " + request);
        }
    }
}
