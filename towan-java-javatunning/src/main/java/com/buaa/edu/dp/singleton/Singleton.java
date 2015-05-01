package com.buaa.edu.dp.singleton;
/**
 * 
 * <p>���ص��͵ĵ���</p>
 * @author huangtw3
 * @Email  huangtw3@chanjet.com
 * 2015��5��1������10:27:18
 *
 * @version V1.0
 */
public class Singleton {
	private Singleton() {
		//���������Ĺ��̿��ܻ�Ƚ���
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