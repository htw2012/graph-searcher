package com.buaa.edu.concurrent.dp.gs.simple;

import java.util.LinkedList;
//中间缓存作用
public class RequestQueue {
    private LinkedList<Request> queue = new LinkedList<Request>();
    public synchronized Request getRequest() {
        while (queue.size() == 0) {
            try {                                   
                wait();
            } catch (InterruptedException e) {      
            }                                       
        } 
        return queue.remove();
    }
    public synchronized void addRequest(Request request) {
        queue.add(request);
        notifyAll();
    }
}
