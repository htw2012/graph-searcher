package com.buaa.dl.model.nn.conf;

/**
 * 配置
 * 
 */
public class Configuration {

	public static final int[] numNeurons = { 200, 100, 2}; // {输入层神经元数，隐层神经元数，输出层神经元数}
	public static final double alpha = 0.05; // 学习率
	public static final double lambda = 0.00025; // 规则化参数
	public static final double eta = 0.35; // 动量系数
	public static final int numIterations = 3; // 迭代次数
	public static final int numThreads = 2; // 线程数
	private static final String folder = System.getProperty("user.dir"); // 当前目录 
	public static final String trainFileName = folder + "/data/trainset.txt"; // 训练数据文件
	public static final String testFileName = folder + "/data/testset.txt"; // 测试数据文件
	public static final String outputModelFileName = folder + "/data/modelFile_new.txt"; // 模型参数文件
}
