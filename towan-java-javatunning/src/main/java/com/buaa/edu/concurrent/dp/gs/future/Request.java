package com.buaa.edu.concurrent.dp.gs.future;

import com.buaa.edu.concurrent.dp.future.Data;

public class Request {
    private String name;
    private Data response;//请求的返回值
    
    public synchronized Data getResponse() {
		return response;
	}
	public synchronized void setResponse(Data response) {
		this.response = response;
	}
	
	public Request(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String toString() {
        return "[ Request " + name + " ]";
    }
}
