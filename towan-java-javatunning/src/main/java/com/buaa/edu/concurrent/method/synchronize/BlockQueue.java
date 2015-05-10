package com.buaa.edu.concurrent.method.synchronize;

import java.util.ArrayList;
import java.util.List;

public class BlockQueue {
	private List<Object> list = new ArrayList<Object>();
	/**
	 * 从队列中取出一个元素
	 * @return
	 * @throws InterruptedException
	 * @author towan
	 * 2015年5月10日
	 */
	public synchronized Object pop() throws InterruptedException {
	    //如果队列中元素为空，需要等待元素进入
	    while (list.size() == 0){
		    this.wait();
		}
	    //取队头
		if (list.size() > 0) {
			return list.remove(0);
		} else
			return null;
	}
	//放入元素，唤醒等待取元素的操作
	public synchronized void put(Object o) {
		list.add(o);
		this.notify();
	}
}
