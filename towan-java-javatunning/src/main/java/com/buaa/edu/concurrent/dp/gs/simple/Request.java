package com.buaa.edu.concurrent.dp.gs.simple;

//POJO对象
public class Request {
    private String name;
	public Request(String name) {//请求内容
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String toString() {
        return "[ Request " + name + " ]";
    }
}
