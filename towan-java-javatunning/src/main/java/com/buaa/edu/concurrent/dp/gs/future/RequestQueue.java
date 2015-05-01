package com.buaa.edu.concurrent.dp.gs.future;

import java.util.LinkedList;

public class RequestQueue {
    
    private LinkedList<Request> queue = new LinkedList<Request>();
    
    public synchronized Request getRequest() {
        while (queue.size() == 0) {
            try { 
                //等待 直到有新的请求加入
                wait();
            } catch (InterruptedException e) {      
            }                                       
        } 
        return (Request)queue.remove();
    }
    //加入新的请求
    public synchronized void addRequest(Request request) {
        queue.add(request);
        //通知 getRequest方法
        notifyAll();
    }
}
