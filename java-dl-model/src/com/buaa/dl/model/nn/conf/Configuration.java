package com.buaa.dl.model.nn.conf;

/**
 * 配置
 * 
 */
public class Configuration {
	public static final int WINDOW_SIZE = 5; // 窗口大小
	public static final int HALF_WINDOW_SIZE = WINDOW_SIZE / 2; // 窗口大小的一半
	public static final int VECTOR_SIZE = 50; // 字向量维数
	public static final int TAGS_SIZE = 4; // 标签数量
	public static final int[] numNeurons = {WINDOW_SIZE * VECTOR_SIZE, 100, TAGS_SIZE}; // {输入层神经元数，隐层神经元数，输出层神经元数}
	public static final double alpha = 0.05; // 学习率
	public static final double lambda = 0.00025; // 规则化参数
	public static final double eta = 0.35; // 动量系数
	public static final int numIterations = 200; // 迭代次数
	public static final int numThreads = 4; // 线程数
	private static final String folder = System.getProperty("user.dir"); // 当前目录 
	public static final String trainFileName = folder + "/data/trainset.txt"; // 训练数据文件
	public static final String testFileName = folder + "/data/测试样例54_1.txt"; // 测试数据文件
	public static final String testFileName2 = folder + "/data/test_close.txt"; // 测试数据文件2
	public static final String word2VecFileName = folder + "/data/data0327.single.size50.bin"; // word2vec文件 
	public static final String modelFileName= folder + "/data/modelFile_new.txt"; // 模型参数文件
	public static final String outputModelFileName = folder + "/data/modelFile_new.txt"; // 模型参数文件
}
