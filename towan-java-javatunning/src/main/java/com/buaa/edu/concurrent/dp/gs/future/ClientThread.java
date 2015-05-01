package com.buaa.edu.concurrent.dp.gs.future;

import java.util.ArrayList;
import java.util.List;

import com.buaa.edu.concurrent.dp.future.FutureData;

public class ClientThread extends Thread {
    private RequestQueue requestQueue;
    private List<Request> myRequest=new ArrayList<Request>();
    public ClientThread(RequestQueue requestQueue, String name) {
        super(name);
        this.requestQueue = requestQueue;
    }
    public void run() {
    	//先提出请求
        for (int i = 0; i < 10; i++) {
            Request request = new Request("RequestID:" + i+" Thread_Name:"+Thread.currentThread().getName());
            System.out.println(Thread.currentThread().getName() + " requests " + request);
            //设置一个futuredata的返回值
            request.setResponse(new FutureData());
            requestQueue.addRequest(request);
            //发出请求
            myRequest.add(request);
            try {//做一些额外的业务处理，等待服务端装配数据
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
        
        //取得服务端返回值
        for(Request r:myRequest){
            System.out.println("ClientThread Name is:"+
            		//如果服务器还没有处理完就会等待
                    Thread.currentThread().getName()+
            		" Reponse is:"+r.getResponse().getResult());
        }
    }
}
