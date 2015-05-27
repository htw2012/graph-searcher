/*
 * Copyright 北京航空航天大学  @ 2015 版权所有
 */
package com.buaa.dl.model.nn.train;

import java.io.IOException;

import org.junit.Test;

/**
 * <p>(用一句话描述该文件做什么)</p>
 * @author towan
 * @Email  tongwenzide@163.com
 * 2015年5月27日
 * @version V1.0  
 */
public class TrainTest {

    @Test
    public void test() throws InterruptedException, IOException {
        Train train = new Train();
        train.work();
    }

}
