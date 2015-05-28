/*
 * Copyright 北京航空航天大学 @ 2015 版权所有
 */
package com.buaa.dl.model.nn.data;

import org.jblas.DoubleMatrix;

/**
 * <p>
 * 行数据处理类
 * </p>
 * @author towan
 * @Email tongwenzide@163.com 2015年5月27日
 * @version V1.0
 */
public class LineDataProvider implements DataProvider {
    String[] strs;
    public LineDataProvider(String line) {
        super();
        strs = line.split(",");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.buaa.dl.model.nn.data.util.DataProvider#getInput()
     */
    @Override
    public DoubleMatrix getInput() {
        int len = strs.length;
        DoubleMatrix input = new DoubleMatrix(len - 1, 1 );
        for (int i = 0; i < len - 1; i++) {
            input.put(i, Double.valueOf(strs[i]));
        }
        return input;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.buaa.dl.model.nn.data.util.DataProvider#getTarget()
     */
    @Override
    public DoubleMatrix getTarget() {
        DoubleMatrix target = new DoubleMatrix(2, 1);
        String s = strs[strs.length - 1];
        if(s.equals("1")){
            target.put(1, 1.0);
        }else{
            target.put(0, 1.0);
        }
//        target.put(0, Double.valueOf(s));
        return target;
    }

}
