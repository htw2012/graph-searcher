package com.buaa.dl.model.nn.layer;

import org.jblas.DoubleMatrix;

/**
 * <p>输入层</p>
 * @author towan
 * @Email  tongwenzide@163.com
 * 2015年5月21日
 * @version V1.0
 */

public class InputLayer {
	private int numNeurons; // 神经元个数
	private DoubleMatrix error; // 输入层误差 
	private DoubleMatrix output; // 输入层输出
	
	/*
	 * 构造函数
	 * 
	 * @param int numNeurons 输入层神经元个数
	 */
	public InputLayer(int numNeurons) {
		this.numNeurons = numNeurons;
		error = DoubleMatrix.zeros(numNeurons);
	}
	
	/*
	 * 拷贝构造函数
	 * 
	 * @param InputLayer inputLayer 待拷贝的输入层
	 */
	public InputLayer(InputLayer inputLayer) {
		numNeurons = inputLayer.getNumNeurons();
		error = inputLayer.getError().dup();
	}
	
	/*
	 * 获取输入层神经元个数
	 * 
	 * @return int 输入层神经元个数
	 */
	public int getNumNeurons() {
		return numNeurons;
	}
	
	/*
	 * 获取输入层误差
	 * 
	 * @return DoubleMatrix 输入层误差
	 */
	public DoubleMatrix getError() {
		return error.dup();
	}
	
	/*
	 * 获取输入层输入
	 * 
	 * @return DoubleMatrix 输入层输出
	 */
	public DoubleMatrix getOutput() {
		return output.dup();
	}
	
	/*
	 * 激活输入层，返回输入层的输出
	 * 
	 * @param DoubleMatrix input 输入层输入
	 * @return DoubleMatrix 输入层输出
	 */
	public DoubleMatrix activate(DoubleMatrix input) {
		output = input.dup();
		return output.dup();
	}
	
	/*
	 * 更新输入层误差
	 * 
	 * @param DoubleMatrix nextLayerWeight 输入层与下一层之间的权重
	 * @param DoubleMatrix nextLayerError 下一层的误差
	 */
	public void updateError(DoubleMatrix nextLayerWeight, DoubleMatrix nextLayerError) {
		error = nextLayerWeight.transpose().mmul(nextLayerError);
	}
}
