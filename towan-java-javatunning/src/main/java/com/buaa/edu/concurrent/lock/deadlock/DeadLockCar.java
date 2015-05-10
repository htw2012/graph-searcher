package com.buaa.edu.concurrent.lock.deadlock;

import java.util.concurrent.locks.ReentrantLock;

public class DeadLockCar extends Thread{
    //资源
	protected Object myDirect;
	
	static ReentrantLock south = new ReentrantLock();
	static ReentrantLock north = new ReentrantLock();
	static ReentrantLock west = new ReentrantLock();
	static ReentrantLock east = new ReentrantLock();

	public DeadLockCar(Object obj){
		this.myDirect=obj;
		//设置各线程名称
		if(myDirect==south){
			this.setName("south");
		}
		if(myDirect==north){
			this.setName("north");
		}
		if(myDirect==west){
			this.setName("west");
		}
		if(myDirect==east){
			this.setName("east");
		}
	}
	@Override
	public void run() {
		if (myDirect == south) {//向南走的小车
			try {
				west.lockInterruptibly();//占用到向西走的路
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
				south.lockInterruptibly();//等待向南的路
				System.out.println("car to south has passed");
			} catch (InterruptedException e1) {
				System.out.println("car to south is killed");
			}finally{
				if(west.isHeldByCurrentThread())
					west.unlock();
				if(south.isHeldByCurrentThread())
					south.unlock();
			}

		}
		if (myDirect == north) {
			try {
				east.lockInterruptibly();
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
				north.lockInterruptibly();
				System.out.println("car to north has passed");
			} catch (InterruptedException e1) {
				System.out.println("car to north is killed");
			}finally{
				if(north.isHeldByCurrentThread())
					north.unlock();
				if(east.isHeldByCurrentThread())
					east.unlock();
			}

		}
		if (myDirect == west) {
			try {
				north.lockInterruptibly();
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
				west.lockInterruptibly();
				System.out.println("car to west has passed");
			} catch (InterruptedException e1) {
				System.out.println("car to west is killed");
			}finally{
				if(north.isHeldByCurrentThread())
					north.unlock();
				if(west.isHeldByCurrentThread())
					west.unlock();
			}

		}
		if (myDirect == east) {
			try {
				south.lockInterruptibly();
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
				east.lockInterruptibly();
				System.out.println("car to east has passed");
			} catch (InterruptedException e1) {
				System.out.println("car to east is killed");
			}finally{
				if(south.isHeldByCurrentThread())
					south.unlock();
				if(east.isHeldByCurrentThread())
					east.unlock();
			}

		}
	}

	public static void main(String[] args) throws InterruptedException {
		DeadLockCar car2south = new DeadLockCar(south);
		DeadLockCar car2north = new DeadLockCar(north);
		DeadLockCar car2west = new DeadLockCar(west);
		DeadLockCar car2east = new DeadLockCar(east);
		car2south.start();
		car2north.start();
		car2west.start();
		car2east.start();
		Thread.sleep(1000);
		car2north.interrupt();//强行杀死任意一个小车资源，可以通过
	}
}