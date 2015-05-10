package com.buaa.edu.concurrent.method.threadlocal;

import java.util.Date;

public class MyThread implements Runnable{
	public static final ThreadLocal<Date> localvar= new ThreadLocal<Date>();
	private long time;
	public MyThread(long time){
		this.time=time;
	}
	@Override
	public void run() {
	    //必须在每个线程中新建
		Date d=new Date(time);
		for(int i=0;i<50000;i++){
		    //设置一个线程的local值不影响其他线程
			localvar.set(d);
			if(localvar.get().getTime()!=time){
			    System.out.println("id="+time+" localvar="+localvar.get().getTime());
			}
		}
	}
}
