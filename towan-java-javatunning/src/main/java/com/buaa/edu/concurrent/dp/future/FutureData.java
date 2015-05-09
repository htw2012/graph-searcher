package com.buaa.edu.concurrent.dp.future;
//关键，相当于一个虚拟数据，异步处理，封装RealData的等待过程
public class FutureData implements Data {
    //FutureData是RealData封装
    protected RealData realdata = null; //组合关系
    protected boolean isReady = false;
    
    public synchronized void setRealData(RealData realdata) {
        if (isReady) {                        
            return;     
        }
        this.realdata = realdata;
        isReady = true;
        //RealData已经被注入，通知getResult
        notifyAll();
    }
    //会等待RealData的构造
    public synchronized String getResult() {
        while (!isReady) {
            try {
                wait();//会等待RealData的构造
            } catch (InterruptedException e) {
            }
        }
        return realdata.result;//RealData的实现
    }
}
