/*
 * Copyright 北京航空航天大学  @ 2015 版权所有
 */
package com.buaa.edu.concurrent.dp.finals;

/**
 * <p>
 * 不可变的产品对象
 * 
 * 不可变模式应用：比如模型训练参数的设置、基本数据类型、服务的初始配置加载
 * </p>
 * @author towan
 * @email tongwenzide@163.com
 * 2015年5月9日
 */
public final class Product {

    private final String no;
    private final String name;
    private final double price;
    /**
     * @param no
     * @param name
     * @param price
     */
    public Product(String no, String name, double price) {
        super();
        this.no = no;
        this.name = name;
        this.price = price;
    }
    /**
     * @return the no
     */
    public String getNo() {
        return no;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }
    
    
    
}
