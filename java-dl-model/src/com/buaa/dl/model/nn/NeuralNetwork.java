package com.buaa.dl.model.nn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.jblas.DoubleMatrix;
import org.jblas.MatrixFunctions;
import com.buaa.dl.model.nn.layer.HiddenLayer;
import com.buaa.dl.model.nn.layer.InputLayer;
import com.buaa.dl.model.nn.layer.OutputLayer;
import com.buaa.dl.model.nn.parallel.UpdateDiffThread;
import com.buaa.dl.model.nn.sample.Sample;
import com.buaa.dl.model.nn.util.FileUtil;

/**
 * 神经网络
 * 
 */
public class NeuralNetwork {
	private InputLayer inputLayer; // 输入层
	private HiddenLayer hiddenLayer; // 隐含层
	private OutputLayer outputLayer; // 输出层
	
	/*
	 * 构造函数
	 * 
	 * @param int[] numNeurons 三维数组，存放输入层、隐含层、输出层的神经元个数
	 */
	public NeuralNetwork(int[] numNeurons) {
		if(numNeurons.length != 3)
			throw new IllegalArgumentException();
		inputLayer = new InputLayer(numNeurons[0]);
		hiddenLayer = new HiddenLayer(numNeurons[1], numNeurons[0]);
		outputLayer = new OutputLayer(numNeurons[2], numNeurons[1]);
	}
	
	/*
	 * 构造函数（利用参数文件）
	 * 
	 * @param String fileName 存放参数的文件
	 */
	public NeuralNetwork(String fileName) {
		try {
//		    List<String> lines = FileUtil.readByResource(fileName);
		    List<String> lines = FileUtil.readByLine(fileName);
			int numNeurons0 = Integer.valueOf(lines.get(0).trim());
			int numNeurons1 = Integer.valueOf(lines.get(1).trim());
			int numNeurons2 = Integer.valueOf(lines.get(2).trim());
			
			DoubleMatrix weight10 = new DoubleMatrix(numNeurons1, numNeurons0);
			DoubleMatrix bias1 = new DoubleMatrix(numNeurons1);
			DoubleMatrix weight21 = new DoubleMatrix(numNeurons2, numNeurons1);
			DoubleMatrix bias2 = new DoubleMatrix(numNeurons2);
			
			int i = 3;
			for(int j = 0; j < weight10.length && i < lines.size(); ++j)
				weight10.put(j, Double.valueOf(lines.get(i++).trim()));
			for(int j = 0; j < bias1.length && i < lines.size(); ++j)
				bias1.put(j, Double.valueOf(lines.get(i++).trim()));
			for(int j = 0; j < weight21.length && i < lines.size(); ++j)
				weight21.put(j, Double.valueOf(lines.get(i++).trim()));
			for(int j = 0; j < bias2.length && i < lines.size(); ++j)
				bias2.put(j, Double.valueOf(lines.get(i++).trim()));
			
			inputLayer = new InputLayer(numNeurons0);
			hiddenLayer = new HiddenLayer(numNeurons1, numNeurons0, weight10, bias1);
			outputLayer = new OutputLayer(numNeurons2, numNeurons1, weight21, bias2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 拷贝构造函数
	 * 
	 * @param NeuralNetwork net 待拷贝的神经网络
	 */
	public NeuralNetwork(NeuralNetwork net) {
		inputLayer = new InputLayer(net.getInputLayer());
		hiddenLayer = new HiddenLayer(net.getHiddenLayer());
		outputLayer = new OutputLayer(net.getOutputLayer());
	}

	/*
	 * 获取神经网络的输入层
	 * 
	 * @return InputLayer 输入层
	 */
	public InputLayer getInputLayer() {
		return inputLayer;
	}
	
	/*
	 * 获取神经网络的隐含层
	 * 
	 * @return HiddenLayer 隐含层
	 */
	public HiddenLayer getHiddenLayer() {
		return hiddenLayer;
	}

	/*
	 * 获取神经网络的输出层
	 * 
	 * @return OutputLayer 输出层
	 */
	public OutputLayer getOutputLayer() {
		return outputLayer;
	}
	
	/*
	 * 把另一个神经网络各参数的偏倒数加到此神经网络
	 * 
	 * @param NeuralNetwork net 另一个神经网络，它的各参数的偏倒数将被加到此神经网络
	 */
	public void addDiff(NeuralNetwork net) {
		hiddenLayer.addDiff(net.getHiddenLayer());
		outputLayer.addDiff(net.getOutputLayer());
	}
	
	/*
	 * 反向传播，依次求输出层、隐含层、输入层误差
	 * 
	 * @param target 神经网络的目标输出
	 */
	public void updateError(DoubleMatrix target) {
		outputLayer.updateError(target);
		hiddenLayer.updateError(outputLayer.getWeight(), outputLayer.getError());
		inputLayer.updateError(hiddenLayer.getWeight(), hiddenLayer.getError());
	}
	
	/*
	 * 更新各层参数的偏倒数
	 */
	public void updateDiff() {
		hiddenLayer.updateDiff(inputLayer.getOutput());
		outputLayer.updateDiff(hiddenLayer.getOutput());
	}
	
	/*
	 * 更新各层参数
	 * 
	 * @param int m 批大小
	 * @param double alpha 学习率
	 * @param double lambda 规则化参数
	 * @param double eta 动量系数
	 */
	public void updateParameter(int m, double alpha, double lambda, double eta) {
		hiddenLayer.updateParameter(m, alpha, lambda, eta);
		outputLayer.updateParameter(m, alpha, lambda, eta);
	}
	
	/*
	 * 激活神经网络，返回神经网络的输出
	 * 
	 * @param DoubleMatrix input 神经网络的输入
	 * @return DoubleMatrix 神经网络的输出
	 */
	public DoubleMatrix activate(DoubleMatrix input) {
		DoubleMatrix h = inputLayer.activate(input);
        DoubleMatrix activate = hiddenLayer.activate(h);
        DoubleMatrix activate2 = outputLayer.activate(activate);
        return activate2;
	}

	/*
	 * 求神经网络参数平方和，该值用于规则化
	 * 
	 * @param double 神经网络所有参数的平方和
	 */
	public double square() {
		double square = 0.0;
		square += hiddenLayer.square();
		square += outputLayer.square();
		return square;
	}
	
	/*
	 * 训练神经网络
	 * 
	 * @param List<Sample> trainSet 训练集
	 * @param List<Sample> testSet 测试集
	 * @param double alpha 学习率
	 * @param double lambda 规则化参数
	 * @param double eta 动量系数
	 * @param int numIterations 迭代次数
	 * @param int numThreads 线程数
	 */
	public void train(List<Sample> trainSet, List<Sample> testSet, double alpha, double lambda, double eta,
			int numIterations, int numThreads) throws InterruptedException {
		double lastCloseCost = Double.MAX_VALUE; // 上一轮迭代后的封闭测试代价函数值
		double defaultAlpha = alpha; // 初始学习率作为默认学习率
		
		// 把训练集拆分为numThreads个小的训练集
		List<List<Sample>> minitrainSets = new ArrayList<>();
		for(int k = 0; k < numThreads; ++k){
		    int fromIndex = k * trainSet.size() / numThreads;
            int toIndex = (k + 1) * trainSet.size() / numThreads;
            List<Sample> subList = trainSet.subList(fromIndex, toIndex);
            minitrainSets.add(new ArrayList<Sample>(subList));
		}
		// 迭代
		for(int i = 1; i <= numIterations; ++i) {
			long startTime = System.currentTimeMillis();
			// 利用多线层进行参数偏倒数的计算，每个线程处理一个小训练集
			List<UpdateDiffThread> updateDiffThreads = new ArrayList<UpdateDiffThread>(); // 多线程
			for(List<Sample> minitrainSet : minitrainSets){
			    // 初始化每个线程并分配任务
			    updateDiffThreads.add(new UpdateDiffThread(this, minitrainSet));
			}
			ExecutorService executor = Executors.newCachedThreadPool(); // 线程池
			for(UpdateDiffThread updateDiffThread : updateDiffThreads){
			    // 线程执行
			    executor.execute(updateDiffThread);
			} 
			executor.shutdown(); // 禁止向executor中添加新的任务
			while(! executor.awaitTermination(200, TimeUnit.MILLISECONDS)); // 确保所有线程执行完
			
			// 汇总各线程所计算出的参数偏倒数
			for(UpdateDiffThread updateDiffThread : updateDiffThreads){
			    // 累加 
			    addDiff(updateDiffThread.getNeuralNetwork());
			} 
			
			// 更新输入向量和神经网络参数
			//for(Sample sample : trainSet) // 更新输入向量 
			//	sample.updateFeature(alpha, lambda, eta);
			updateParameter(trainSet.size(), alpha, lambda, eta); // 更新神经网络参数
			
			// 计算此轮迭代后的开放测试代价函数值
			double openCost = 0.0;
			int openMatch = 0;
			for(Sample sample : testSet){
			    DoubleMatrix target = sample.getTarget();
			    DoubleMatrix output = activate(sample.getInput());
			    if(target.argmax()==output.argmax()){
			        openMatch++;
                }
                openCost += (target.mul(MatrixFunctions.log(output))).sum();
			}
			openCost = - openCost / testSet.size() + square() * lambda / 2.0;
					
			// 计算此轮迭代后的封闭测试代价函数值
			double closeCost = 0.0;
			int match = 0;
			for(Sample sample : trainSet) {
                DoubleMatrix input = sample.getInput();
                //实际输出
                DoubleMatrix output = activate(input);
                
                DoubleMatrix target = sample.getTarget();
                if(target.argmax()==output.argmax()){
                    match++;
                }
                DoubleMatrix doubleMatrix = target.mul(MatrixFunctions.log(output));
                closeCost += doubleMatrix.sum();
            }
			closeCost = - closeCost / trainSet.size() + square() * lambda / 2.0;
			
			// 调整学习率
			if(closeCost < lastCloseCost){
			    // 封闭测试代价函数值减小，提高学习率
			    alpha  *= 1.05;
			}else if(closeCost < 1.05 * lastCloseCost) {
			    // 封闭测试代价函数值增加，降低学习率
			    alpha *= 0.7;
			}else {
			    // 封闭测试代价函数值增加的较多，恢复默认学习率
			    alpha = defaultAlpha;
			}
			
			lastCloseCost = closeCost; // 更新lastCloseCost
			long endTime = System.currentTimeMillis();
			double time = (endTime - startTime) / 1000.0;
			System.out.println(i + "," + openCost + "," + closeCost + "," + time + "s,train->"+match+":"+trainSet.size()+""
			        + ",test->"+openMatch+":"+testSet.size());
		}
	}
	
	/*
	 * 将模型参数（权重、偏置）保存到文件中
	 * 
	 * 保存格式：
	 * 		输入层神经元个数
	 * 		隐含层神经元个数
	 * 		输出层神经元个数
	 * 		输入层与隐含层之间的权重（每行只保存一个权重）
	 * 		输入层与隐含层之间的偏置（每行只保存一个偏置）
	 * 		隐含层与输出层之间的权重（每行只保存一个权重）
	 * 		隐含层与输出层之间的偏置（每行只保存一个偏置）
	 * 
	 * @param String fileName 保存的文件名
	 */
	public void save(String fileName) throws IOException {
	    
		List<String> lines = new ArrayList<String>();
		
		lines.add(Integer.toString(inputLayer.getNumNeurons())); // 输入层神经元个数
		lines.add(Integer.toString(hiddenLayer.getNumNeurons())); // 隐含层神经元个数
		lines.add(Integer.toString(outputLayer.getNumNeurons())); // 输出层神经元个数
		
		DoubleMatrix weight10 = hiddenLayer.getWeight(); // 输入层-隐含层之间的权重
		for(int i = 0; i < weight10.length; ++i)
			lines.add(Double.toString(weight10.get(i)));
		
		DoubleMatrix bias1 = hiddenLayer.getBias(); // 输入层-隐含层之间的偏置
		for(int i = 0; i < bias1.length; ++i)
			lines.add(Double.toString(bias1.get(i)));
		
		DoubleMatrix Weight21 = outputLayer.getWeight(); // 隐含层-输出层之间的权重
		for(int i = 0; i < Weight21.length; ++i)
			lines.add(Double.toString(Weight21.get(i)));
		
		DoubleMatrix bias2 = outputLayer.getBias(); // 隐含层-输出层之间的偏置
		for(int i = 0; i < bias2.length; ++i)
			lines.add(Double.toString(bias2.get(i)));
		
		FileUtil.saveFile2(fileName, lines);
	}
}
