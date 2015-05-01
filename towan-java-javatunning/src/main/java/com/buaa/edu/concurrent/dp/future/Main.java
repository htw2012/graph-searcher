package com.buaa.edu.concurrent.dp.future;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();
        
        Data data = client.request("a");
        System.out.println("请求完毕");
        try {
        	//这里可以用一个sleep代替了对其它业务逻辑的处理
            System.out.println("Doing another start");
            Thread.sleep(2000);
            System.out.println("Doing another done");
        } catch (InterruptedException e) {
        }
        	//使用真实的数据
        System.out.println("数据 = " + data.getResult());
    }
}
