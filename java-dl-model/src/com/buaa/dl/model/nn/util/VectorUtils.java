/*
 * Copyright 畅捷通股份有限公司  @ 2015 版权所有
 */
package com.buaa.dl.model.nn.util;

import java.util.Arrays;

import org.junit.Test;

/**
 * <p>向量的使用类</p>
 * @author huangtw3
 * @Email  huangtw3@chanjet.com
 * 2015年1月16日下午3:51:17
 *
 * @version V1.0  
 */
public class VectorUtils {

    public static double[] getSum(double []num1,double[] num2){
        int length = num1.length;
        double []ret = new double[length];
        for(int i=0;i<length;i++){
            ret[i] = num1[i]+num2[i];
        }
        return ret;
    }
    
    public static double[] getSub(double []num1,double[] num2){
        int length = num1.length;
        for(int i=0;i<length;i++){
            num1[i] = num1[i]-num2[i];
        }
        return num1;
    }
    /**
     * 将一个数分别乘到这个数组中
     * @param num 对应数组
     * @param k 
     * @return
     * @author huangtw3	
     * @Email  huangtw3@chanjet.com
     * 2015年1月22日上午11:52:32
     */
    public static double[] scale(double []num, double k){
        for(int i=0;i<num.length;i++){
            num[i] *= k;
        }
        return num;
    }
    
    @Test
    public void testProduct(){
        double []num = {1d,2d,4.5d,3.3d};
        System.out.println("Before:"+Arrays.toString(num));
        double[] product = scale(num, 3);
        System.out.println("After:"+Arrays.toString(product));
        System.out.println("Before:"+Arrays.toString(num));
    }

    /**
     *	 获得 最大的元素
     * @param arr
     * @return
     * @author huangtw3
     * @Email  huangtw3@chanjet.com
     * 2015年1月30日下午3:37:55
     */
    public static int getMaxElement(int[] arr) {
        int max = arr[0];
        for(int i=1;i<arr.length;i++){
            if(arr[i]>max){
                max = arr[i];
            }
        }
        return max;
    }
}
