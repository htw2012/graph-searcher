package com.buaa.edu.concurrent.method.volatitle;

import org.junit.Test;
//使用-server参数运行 FIXME
public class VolatileModel {
	 public class MyThread extends Thread{
	     //使用volatile确保stop变量在多线程中可见
		 private volatile  boolean stop = false;  
		 public void stopMe(){
			 stop=true;
		 }
		 @Override
         public void run() {  
             int i = 0;  
             while (!stop) { 
                 i++;  
             }
             System.out.println("Stop Thread:"+i);  
         }  
	 }
	 
	 @Test
	 public void test() throws InterruptedException{
		 MyThread t = new MyThread();  
		 t.start(); 
		 Thread.sleep(1000);
		 t.stopMe();
		 Thread.sleep(1000);
		 
	 }
}
