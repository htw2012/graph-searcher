package com.buaa.edu.concurrent.dp.future;

public class Client {
    public Data request(final String queryStr) {
        final FutureData future = new FutureData();
        // //构造比较慢，在单独的线程进行执行
        new Thread() {                                      
            public void run() {         
              //构造比较慢，在单独的线程进行执行
                RealData realdata = new RealData(queryStr);
                future.setRealData(realdata);
            }                                               
        }.start();
        return future;
    }
}
