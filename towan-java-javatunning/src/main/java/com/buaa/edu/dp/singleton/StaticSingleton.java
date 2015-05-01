package com.buaa.edu.dp.singleton;

public class StaticSingleton {
    
	private StaticSingleton(){
	    //创建过程可能比较缓慢，可以考虑延迟加载
		System.out.println("StaticSingleton is create");
	}
	//使用内部类维护实例
	private static class SingletonHolder {
		private static StaticSingleton instance = new StaticSingleton();
	}

	public static StaticSingleton getInstance() {
		return SingletonHolder.instance;
	}
	//模拟单例类扮演其他角色
	public static void createString(){
		System.out.println("createString in Singleton");
	}
}