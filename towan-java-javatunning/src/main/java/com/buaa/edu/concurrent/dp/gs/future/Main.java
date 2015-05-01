package com.buaa.edu.concurrent.dp.gs.future;



public class Main {
    public static void main(String[] args) {
        RequestQueue requestQueue = new RequestQueue();
        for(int i=0;i<5;i++)
        	new ServerThread(requestQueue, "ServerThread"+i).start();
        for(int i=0;i<10;i++)
        	new ClientThread(requestQueue, "ClientThread"+i).start();
    }
}
