/*
 * Copyright 北京航空航天大学  @ 2015 版权所有
 */
package com.buaa.edu.concurrent.dp.future.jdk.ex1;

import java.util.Arrays;
import java.util.Map;

/**
 * <p>词向量的获得器</p>
 * @author towan
 * @email tongwenzide@163.com
 * 2015年5月9日
 */
public class WordMapGetter {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        RealData data = new RealData();
        long end = System.currentTimeMillis();
        System.out.println("Constructor use time:"+(end-start));
//        FutureTask task = new FutureTask(data);
//        task.run();
        Map<String, double[]> wordVecs = data.call();
        long end2 = System.currentTimeMillis();
        System.out.println("Get wordmapper time:"+(end2-start));
        System.out.println("Get 1:"+Arrays.toString(wordVecs.get("1")));
    }
}
