package com.buaa.edu.dp.singleton;

public class SingletonFactory {
	private SingletonFactory() {
		//创建单例的过程可能会比较慢
		System.out.println("Singleton is create");
	}

	private static SingletonFactory instance = new SingletonFactory();
	public static SingletonFactory getInstance() {
		return instance;
	} 
}