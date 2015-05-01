package com.buaa.edu.dp.singleton;
/**
 * 
 * <p>朴素典型的单例</p>
 * @author huangtw3
 * @Email  huangtw3@chanjet.com
 * 2015年5月1日上午10:27:18
 *
 * @version V1.0
 */
public class Singleton {
	private Singleton() {
		//创建单例的过程可能会比较慢
		System.out.println("Singleton is create");
	}

	private static Singleton instance = new Singleton();
	public static Singleton getInstance() {
		return instance;
	}

	public static void createString(){
		System.out.println("createString in Singleton");
	}
}