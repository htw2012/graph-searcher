package com.buaa.edu.dp.proxy;

public class Main {
	public static void main(String args[]){
	    //延迟加载，创建不耗时
		IDBQuery q=new DBQueryProxy();	//使用代理
		q.request();					//在真正使用时才创建真实对象
	}
}
