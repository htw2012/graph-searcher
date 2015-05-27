/*
 * Copyright 北京航空航天大学  @ 2015 版权所有
 */
package com.buaa.dl.model.nn.data.util;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.buaa.dl.model.nn.util.FileUtil;

/**
 * <p>文件测试</p>
 * @author towan
 * @Email  tongwenzide@163.com
 * 2015年5月27日
 * @version V1.0  
 */
public class LineDataProviderTest {

    @Test
    public void test() throws IOException {
      List<String> lines = FileUtil.readByLine("data/testfile.txt");
      for(String line:lines){
          LineDataProvider data = new LineDataProvider(line);
          System.out.println(data.getInput());
          System.out.println(data.getTarget());
      }
    }

}
