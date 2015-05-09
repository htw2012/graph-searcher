package com.buaa.edu.concurrent.dp.gs.simple;

import java.util.LinkedList;
//中间缓存作用
public class RequestQueue {
    private LinkedList<Request> queue = new LinkedList<Request>();
    /**
     * 获得处理请求
     * @return
     * @author towan
     * 2015年5月9日
     */
    public synchronized Request getRequest() {
        //请求队列处理完就等待，没有移除
        while (queue.size() == 0) {
            try {                                   
               wait();
            } catch (InterruptedException e) {      
            }                                       
        } 
        return queue.remove();
    }
    /**
     * 增加处理请求
     * @param request
     * @author towan
     * 2015年5月9日
     */
    public synchronized void addRequest(Request request) {
        queue.add(request);
        notifyAll(); //有可能队列请求再等待
    }
}
