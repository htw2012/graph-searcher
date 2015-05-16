package com.buaa.edu.jvmtune.memory;

import org.junit.Test;

/**
 * -XX:PermSize=2M -XX:MaxPermSize=4M -XX:+PrintGCDetails
 * <p>永久区的常量饱和</p>
 * @author towan
 * @email tongwenzide@163.com
 * 2015年5月16日
 */
public class TestPermConstGC {
	@Test
	public void permGenGC() {
	    for (int i = 0; i < Integer.MAX_VALUE; i++) {
	        //intern目的如果池中不存在就加入常量池，存在就不加入
	        @SuppressWarnings("unused")
            String t = String.valueOf(i).intern();//不断加入常量池
	    }
	}
}
