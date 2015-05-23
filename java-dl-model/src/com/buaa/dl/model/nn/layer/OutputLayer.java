package com.buaa.dl.model.nn.layer;

import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;

/**
 * 
 * <p>神经网络输出层</p>
 * @author towan
 * @Email  tongwenzide@163.com
 * 2015年5月21日
 * @version V1.0
 */
public class OutputLayer {
	private int numNeurons; // 输出层神经元个数
	private DoubleMatrix weight; // 输出层对输入的权重
	private DoubleMatrix bias; // 输出层对输入的偏置
	private DoubleMatrix error; // 输出层的误差
	private DoubleMatrix weightDiff; // 权重的偏倒数
	private DoubleMatrix biasDiff; // 偏置的偏倒数
	private DoubleMatrix weightMomentum; // 权重的动量
	private DoubleMatrix biasMomentum; // 偏置的动量
	private DoubleMatrix output; // 输出层输出
	
	/*
	 * 构造函数
	 * 
	 * @param int numNeurons 输出层神经元个数
	 * @param int numInputs 输出层输入维度
	 */
	public OutputLayer(int numNeurons, int numInputs) {
		this.numNeurons = numNeurons;
		weight = DoubleMatrix.randn(numNeurons, numInputs).mul(0.007);
		bias = DoubleMatrix.randn(numNeurons).mul(0.007);
		error = DoubleMatrix.zeros(numNeurons);
		weightDiff = DoubleMatrix.zeros(numNeurons, numInputs);
		biasDiff = DoubleMatrix.zeros(numNeurons);
		weightMomentum = DoubleMatrix.zeros(numNeurons, numInputs);
		biasMomentum = DoubleMatrix.zeros(numNeurons);
		output = DoubleMatrix.zeros(numNeurons);
	}
	
	/*
	 * 构造函数
	 * 
	 * @param int numNeurons 隐含层神经元个数
	 * @param int numInputs 隐含层输入维度
	 * @param DoubleMatrix weight 权重
	 * @param DoubleMatrix bias 偏置
	 */
	public OutputLayer(int numNeurons, int numInputs, DoubleMatrix weight, DoubleMatrix bias) {
		this.numNeurons = numNeurons;
		this.weight = weight.dup();
		this.bias = bias.dup();
		error = DoubleMatrix.zeros(numNeurons);
		weightDiff = DoubleMatrix.zeros(numNeurons, numInputs);
		biasDiff = DoubleMatrix.zeros(numNeurons);
		weightMomentum = DoubleMatrix.zeros(numNeurons, numInputs);
		biasMomentum = DoubleMatrix.zeros(numNeurons);
		output = DoubleMatrix.zeros(numNeurons);
	}
	
	/*
	 * 拷贝构造函数
	 * 
	 * @param OutputLayer outputLayer 待拷贝的输出层
	 */
	public OutputLayer(OutputLayer outputLayer) {
		numNeurons = outputLayer.getNumNeurons();
		weight = outputLayer.getWeight().dup();
		bias = outputLayer.getBias().dup();
		error = outputLayer.getError().dup();
		weightDiff = outputLayer.getWeightDiff().dup();
		biasDiff = outputLayer.getBiasDiff().dup();
		weightMomentum = outputLayer.getWeightMomentum().dup();
		biasMomentum = outputLayer.getBiasMomentum().dup();
		output = outputLayer.getOutput().dup();
	}
	
	/*
	 * 获取输出层神经元个数
	 * 
	 * @return int 输出层神经元个数
	 */
	public int getNumNeurons() {
		return numNeurons;
	}
	
	/*
	 * 获取输出层对输入的权重
	 * 
	 * @return DoubleMatrix 输出层对输入的权重
	 */
	public DoubleMatrix getWeight() {
		return weight.dup();
	}

	/*
	 * 获取输出层偏置
	 * 
	 * @return DoubleMatrix 输出层对输入的偏置
	 */
	public DoubleMatrix getBias() {
		return bias.dup();
	}
	
	/*
	 * 获取输出层误差
	 * 
	 * @return DoubleMatrix 输出层误差
	 */
	public DoubleMatrix getError() {
		return error.dup();
	}
	
	/*
	 * 获取权重的偏倒数
	 * 
	 * @return DoubleMatrix 权重偏倒数
	 */
	public DoubleMatrix getWeightDiff() {
		return weightDiff.dup();
	}
	
	/*
	 * 获取偏置的偏倒数
	 * 
	 * @return DoubleMatrix 偏置的偏倒数
	 */
	public DoubleMatrix getBiasDiff() {
		return biasDiff.dup();
	}

	/*
	 * 获取权重的动量
	 * 
	 * @return DoubleMatrix 权重的动量
	 */
	public DoubleMatrix getWeightMomentum() {
		return weightMomentum.dup();
	}

	/*
	 * 获取偏置的动量
	 * @return DoubleMatrix 偏置的动量
	 */
	public DoubleMatrix getBiasMomentum() {
		return biasMomentum.dup();
	}
	
	/*
	 * 获取输出层的输出
	 * 
	 * @return DoubleMatrix 输出层的输出
	 */
	public DoubleMatrix getOutput() {
		return output.dup();
	}
	
	/*
	 * 把另一个输出层各参数的偏倒数加到此输出层
	 * 
	 * @param OutputLayer outputLayer 另一个输出层，它的各参数的偏倒数将被加到此输出层
	 */
	public void addDiff(OutputLayer outputLayer) {
		weightDiff.addi(outputLayer.getWeightDiff());
		biasDiff.addi(outputLayer.getBiasDiff());
	}
	
	/*
	 * 激活输出层，返回输出层输出
	 * 
	 * @param DoubleMatrix input 输出层输入
	 * @return DoubleMatrix 输出层输出
	 */
	public DoubleMatrix activate(DoubleMatrix input) {
		// 线性部分
		output = weight.mmul(input).add(bias);
		
		// softmax
		for(int i = 0; i < output.length; ++i)
			output.put(i, Math.exp(output.get(i)));
		double sum = output.sum();
		output.divi(sum);
		
		return output.dup();
	}
	
	/*
	 * 求输出层参数的平方和，该值用于规则化
	 * 
	 * @return double 输出层参数的平方和
	 */
	public double square() {
		double square = 0.0;
		square += MatrixFunctions.pow(weight, 2.0).sum();
		square += MatrixFunctions.pow(bias, 2.0).sum();
		return square;
	}
	
	/*
	 * 更新输出层误差
	 * 
	 * @param DoubleMatrix target 目标输出
	 */
	public void updateError(DoubleMatrix target) {
		error = output.sub(target);
	}
	
	/*
	 * 更新输出层参数的偏倒数
	 * 
	 * @param DoubleMatrix input 输出层输出
	 */
	public void updateDiff(DoubleMatrix input) {
		weightDiff.addi(error.mmul(input.transpose()));
		biasDiff.addi(error);
	}
	
	/*
	 * 更新输出层参数
	 * 
	 * @param int m 批大小
	 * @param double alpha 学习率
	 * @param double lambda 规则化参数
	 * @param double eta 动量系数
	 */
	public void updateParameter(int m, double alpha, double lambda, double eta) {
		// 更新权重
		DoubleMatrix weightLearning = (weightDiff.div(m).add(weight.mul(lambda))).mul(- alpha).add(weightMomentum.mul(eta));
		weight.addi(weightLearning);
		weightMomentum.copy(weightLearning) ;
		
		// 更新偏置
		DoubleMatrix biasLearning = (biasDiff.div(m).add(bias.mul(lambda))).mul(- alpha).add(biasMomentum.mul(eta));
		bias.addi(biasLearning);
		biasMomentum.copy(biasLearning);
		
		// 偏倒数重置为0
		weightDiff.fill(0.0);
		biasDiff.fill(0.0);
	}
}
