package com.buaa.edu.dp.proxy;

public class DBQueryProxy implements IDBQuery {

    private DBQuery real=null; 
	@Override
    public String request() {
		//��������Ҫ��ʱ�� ���Ŵ�����ʵ���󣬴������̿��ܺ���
		if(real==null)
			real=new DBQuery();
		//�ڶ��̻߳����£����ﷵ��һ������࣬������Futureģʽ //TODO NEED know 
		return real.request();
	}
}
