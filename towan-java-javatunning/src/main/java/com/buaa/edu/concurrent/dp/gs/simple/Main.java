package com.buaa.edu.concurrent.dp.gs.simple;

//10个服务进程和10个客户端进程同时开启，但是客户端快，保证不丢请求
public class Main {
    public static void main(String[] args) {
        RequestQueue requestQueue = new RequestQueue();
        for(int i=0;i<10;i++)
        	new ServerThread(requestQueue, "ServerThread"+i).start();
        for(int i=0;i<10;i++)
        	new ClientThread(requestQueue, "ClientThread"+i).start();
    }
}
