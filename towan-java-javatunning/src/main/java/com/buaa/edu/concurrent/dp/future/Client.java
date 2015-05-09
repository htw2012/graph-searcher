package com.buaa.edu.concurrent.dp.future;

public class Client {
    public Data request(final String queryStr) {
        //异步执行，返回类似于订单的future,future实现了数据获取的Data接口
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
