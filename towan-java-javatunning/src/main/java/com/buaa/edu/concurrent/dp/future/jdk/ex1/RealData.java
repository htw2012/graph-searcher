/*
 * Copyright 北京航空航天大学  @ 2015 版权所有
 */
package com.buaa.edu.concurrent.dp.future.jdk.ex1;

import java.util.Map;
import java.util.concurrent.Callable;

import com.google.common.collect.Maps;

/**
 * <p>完成实际数据的处理并返回结果</p>
 * @author towan
 * @email tongwenzide@163.com
 * 2015年5月9日
 */
public class RealData implements Callable<Map<String,double[]>>{
    
    private Map<String, double[]> wordVecs;
    public RealData() {//这里可以进行相应的操作，比如计算文件的大小,确保返回值一样....
        wordVecs  = Maps.newHashMap();
        for(int i=0;i<10000;i++){
            double[] value = new double[100];
            for(int j=0;j<value.length;j++){
                value[j] = Math.random();
            }
            wordVecs.put(i+"", value);
        }
    }
    
    //返回为词向量的列表
    /* (non-Javadoc)
     * @see java.util.concurrent.Callable#call()
     */
    @Override
    public Map<String, double[]> call() throws Exception {
        return wordVecs;
    }

    
}
