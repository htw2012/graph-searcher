package com.buaa.edu.concurrent.dp.future;

public class Client {
    public Data request(final String queryStr) {
        //�첽ִ�У����������ڶ�����future,futureʵ�������ݻ�ȡ��Data�ӿ�
        final FutureData future = new FutureData();
        // //����Ƚ������ڵ������߳̽���ִ��
        new Thread() {                                      
            public void run() {         
              //����Ƚ������ڵ������߳̽���ִ��
                RealData realdata = new RealData(queryStr);
                future.setRealData(realdata);
            }                                               
        }.start();
        return future;
    }
}
