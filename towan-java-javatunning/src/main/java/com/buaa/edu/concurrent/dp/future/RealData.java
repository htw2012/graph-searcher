package com.buaa.edu.concurrent.dp.future;

public class RealData implements Data {
    
    protected final String result;
    //构造比较慢，使用sleep进行模拟
    public RealData(String para) {//传入实际请求参数
    	
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i < 10; i++) {
        	sb.append(para);
            try {//构造比较慢，使用sleep进行模拟
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        result = sb.toString();
    }
    public String getResult() {
        return result;
    }
}
