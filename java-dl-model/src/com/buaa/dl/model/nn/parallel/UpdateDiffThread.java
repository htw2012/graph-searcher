package com.buaa.dl.model.nn.parallel;

import java.util.List;

import com.buaa.dl.model.nn.NeuralNetwork;
import com.buaa.dl.model.nn.sample.Sample;

/**
 * 更新参数偏倒数线程，使用Runnable接口
 * 
 */
public class UpdateDiffThread implements Runnable {
	private NeuralNetwork net; // 待训练的神经网络
	private List<Sample> samples; // 训练样例
	
	/*
	 * 构造函数
	 * 
	 * @param NeuralNetwork net 待训练的神经网络
	 * @param ArrayList<Sample> samples 训练样例
	 */
	public UpdateDiffThread(NeuralNetwork net, List<Sample> samples) {
		this.net = new NeuralNetwork(net);
		this.samples = samples;
	}
	
	/*
	 * run方法
	 */
	public void run() {
		for(Sample sample : samples) {
			net.activate(sample.getInput());	// 激活神经网络
			net.updateError(sample.getTarget()); // 更新误差
			sample.updateDiff(net.getInputLayer().getError()); // 更新输入向量的偏倒数
			net.updateDiff(); // 更新神经网络参数的偏倒数
		}
	}
	
	/*
	 * 获取神经网络
	 * 
	 * @return NeuralNetwork 神经网络
	 */
	public NeuralNetwork getNeuralNetwork() {
		return net;
	}
}
