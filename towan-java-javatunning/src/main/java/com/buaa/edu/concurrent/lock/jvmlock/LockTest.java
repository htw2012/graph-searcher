package com.buaa.edu.concurrent.lock.jvmlock;


/**锁消除，逃逸分析技术
 * -server -XX:-DoEscapeAnalysis -XX:-EliminateLocks 
 */
public class LockTest {
	private static final int CIRCLE = 20000000; 

	public static void main(String args[]) throws InterruptedException {
		long start = System.currentTimeMillis();
		for (int i = 0; i < CIRCLE; i++) {
			craeteStringBuffer("Java", "Performance");
		}
		long bufferCost = System.currentTimeMillis() - start;
		System.out.println("craeteStringBuffer: " + bufferCost + " ms");
	}

	public static String craeteStringBuffer(String s1, String s2) {
		StringBuffer sb = new StringBuffer();//局部变量没有逃逸出这个方法
		sb.append(s1);//append方法中有锁的申请
		sb.append(s2);
		return sb.toString();
	}
}