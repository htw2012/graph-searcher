package com.buaa.edu.jvmtune.memory;

import org.junit.Test;
/**
 * -Xss1M
 * <p>
 * 递归调用求得可用栈的深度
 * 证实递归函数调用次数由栈的大小决定
 * </p>
 * @author towan
 * @email tongwenzide@163.com
 * 2015年5月16日
 */
public class TestStack {
	private int count=0;
	public void recursion() throws InterruptedException{
		count++;
		recursion();
	}
	@Test
	public void testStack(){
		try{
			recursion();
		}catch(Throwable e){//11379  //56151
			System.out.println("deep of stack is "+count);
			e.printStackTrace();
		}
	}
	public  void t(){
		
	}
}
