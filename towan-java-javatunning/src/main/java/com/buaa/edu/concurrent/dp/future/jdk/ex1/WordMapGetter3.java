/*
 * Copyright 北京航空航天大学  @ 2015 版权所有
 */
package com.buaa.edu.concurrent.dp.future.jdk.ex1;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * <p>(用一句话描述该文件做什么)</p>
 * @author towan
 * @email tongwenzide@163.com
 * 2015年5月9日
 */
public class WordMapGetter3 {

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        FutureTask<Map<String, double[]>> task = new FutureTask<Map<String, double[]>>(new RealData());
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(task);
        service.shutdown(); //不允许再添加新的线程
//        task.run();
        long end = System.currentTimeMillis();
        System.out.println("Constructor use time:"+(end-start));
//        Thread.sleep(80);
        Map<String, double[]> wordVecs =  task.get();
        long end2 = System.currentTimeMillis();
        System.out.println("Get wordmapper time:"+(end2-start));
        System.out.println("Get 1:"+Arrays.toString(wordVecs.get("1")));
    }
}
