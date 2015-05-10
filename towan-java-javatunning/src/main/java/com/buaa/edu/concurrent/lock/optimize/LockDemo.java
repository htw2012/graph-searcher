package com.buaa.edu.concurrent.lock.optimize;

public class LockDemo {
	
	protected void mutextMethod(){
		
	}
	
	protected void othercode1(){
		
	}
	
	protected void othercode2(){
		
	}
	//法1
	public synchronized void syncMethod(){
		othercode1();
		mutextMethod();
		othercode2();
	}
	//优化 为 减少锁持有时间
	public void syncMethod2(){
		othercode1();
		synchronized(this){//此对象
			mutextMethod();
		}
		othercode2();
	}
	
}
