package com.buaa.edu.dp.proxy;

public class DBQuery implements IDBQuery{
	public DBQuery(){
		//���ܰ������ݿ����ӵȺ�ʱ����
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	@Override
	public String request() {
		return "request string";
	}
}
