package com.buaa.edu.dp.proxy;

public class Main {
	public static void main(String args[]){
	    //�ӳټ��أ���������ʱ
		IDBQuery q=new DBQueryProxy();	//ʹ�ô���
		q.request();					//������ʹ��ʱ�Ŵ�����ʵ����
	}
}
