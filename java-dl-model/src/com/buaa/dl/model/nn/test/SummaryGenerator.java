/*
 * Copyright 北京航空航天大学  @ 2015 版权所有
 */
package com.buaa.dl.model.nn.test;

import java.io.IOException;
import java.util.List;

import org.jblas.DoubleMatrix;

import com.buaa.dl.model.nn.NeuralNetwork;
import com.buaa.dl.model.nn.conf.Configuration;
import com.buaa.dl.model.nn.sample.Sample;
import com.buaa.dl.model.nn.util.FileUtil;

/**
 * <p>摘要生成器</p>
 * @author towan
 * @time 2015年5月28日 
 */
public class SummaryGenerator {

    public static void genSummary(String filename, String genFile) throws IOException{
        
        NeuralNetwork net = new NeuralNetwork(Configuration.outputModelFileName);
        List<Sample> samples = Sample.loadFileSample(filename);
        for(Sample sample :samples){
            DoubleMatrix output = net.activate(sample.getInput());
            int argmax = output.argmax();
            FileUtil.append(genFile, argmax+"", false);
        }
    }
    
    public static void main(String[] args) throws IOException {
        String filename = "data/test/testset.txt";
        String genFile = "data/test/resultset.txt";
        genSummary(filename, genFile);
    }
    
}
