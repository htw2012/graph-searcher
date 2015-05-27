package com.buaa.dl.model.nn.train;

import java.io.IOException;
import java.util.List;

import com.buaa.dl.model.nn.NeuralNetwork;
import com.buaa.dl.model.nn.conf.Configuration;
import com.buaa.dl.model.nn.sample.Sample;

/**
 * 训练
 * 
 */
public class Train {
    
	private NeuralNetwork net; // 要训练的神经网络
	private double alpha; // 学习率
	private double lambda; // 规则化参数
	private double eta; // 动量系数
	private int numIterations; // 迭代次数
	private int numThreads; // 线程数
	private List<Sample> trainSet; // 训练集
	private List<Sample> testSet; // 测试集
	
	/*
	 * 构造函数
	 */
	public Train() throws IOException {
//		net = new NeuralNetwork(Configuration.modelFileName);
		net = new NeuralNetwork(Configuration.numNeurons);
		alpha = Configuration.alpha;
		lambda = Configuration.lambda;
		eta = Configuration.eta;
		numIterations = Configuration.numIterations;
		numThreads = Configuration.numThreads;
		trainSet = Sample.loadFileSample(Configuration.trainFileName);
		testSet = Sample.loadFileSample(Configuration.testFileName);
	}
	/*
	 * 训练
	 */
	public void work() throws InterruptedException, IOException {
		net.train(trainSet, testSet, alpha, lambda, eta, numIterations, numThreads); // 训练模型
		net.save(Configuration.outputModelFileName); // 模型参数保存到文件中
	}
}

