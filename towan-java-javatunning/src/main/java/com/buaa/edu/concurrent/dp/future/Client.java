package com.buaa.edu.concurrent.dp.future;

public class Client {
    public Data request(final String queryStr) {
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
