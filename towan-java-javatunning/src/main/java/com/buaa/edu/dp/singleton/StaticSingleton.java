package com.buaa.edu.dp.singleton;

public class StaticSingleton {
    
	private StaticSingleton(){
	    //�������̿��ܱȽϻ��������Կ����ӳټ���
		System.out.println("StaticSingleton is create");
	}
	//ʹ���ڲ���ά��ʵ��
	private static class SingletonHolder {
		private static StaticSingleton instance = new StaticSingleton();
	}

	public static StaticSingleton getInstance() {
		return SingletonHolder.instance;
	}
	//ģ�ⵥ�������������ɫ
	public static void createString(){
		System.out.println("createString in Singleton");
	}
}