package com.buaa.dl.model.nn.sample;

import java.util.ArrayList;
import java.util.Hashtable;
import org.jblas.DoubleMatrix;
import com.buaa.dl.model.nn.conf.Configuration;

/**
 * 样例
 * 
 */
public class Sample {
	private String[] words; // 字窗口
	private char tag; // 标签（窗口中间那个字的）
	public static Hashtable<String, Word> wordTable = new Hashtable<String, Word>(); // 存放字对象的哈希表
	
	/*
	 * 构造函数
	 * 
	 * @param String[] words // 待处理的字
	 * @param char tag; // w3的标签（‘B’，‘M’，‘E’，‘N’）
	 */
	public Sample(String[] words, char tag) {
		this.words = words;
	    this.tag = tag;
	}
	
	/*
	 * 获取窗口字
	 * 
	 * @return String[] 窗口字
	 */
	public String[] getWords() {
		return words;
	}
	
	/*
	 * 获取标签
	 */
	public char getTag() {
		return tag;
	}
	
	/*
	 * 获取样例的输入
	 * 
	 * @return DoubleMatrix 样例的输入
	 */
	public DoubleMatrix getInput() {
	    // 获取字向量，并将各字的字向量拼接作为样例的输入
	    DoubleMatrix input = new DoubleMatrix(Configuration.WINDOW_SIZE * Configuration.VECTOR_SIZE);
	    double[] vector;
	    for(int i = 0; i < Configuration.WINDOW_SIZE ; ++i) {
	    	if(! wordTable.containsKey(words[i])) { // 若该字不在字表中，将其加入
	    		wordTable.put(words[i], new Word(words[i]));
	    	}
	    	vector = wordTable.get(words[i]).getVector();
	    	if(vector == null)
	    		System.out.println(words[i]);
	    	for(int j = 0; j < Configuration.VECTOR_SIZE; ++j) {
	    		input.put(i * Configuration.VECTOR_SIZE + j, vector[j]);
	    	}
	    }
		return input;
	}
	
	public static DoubleMatrix getInput(String[] words) {
	    // 获取字向量，并将各字的字向量拼接作为样例的输入
	    DoubleMatrix input = new DoubleMatrix(Configuration.WINDOW_SIZE * Configuration.VECTOR_SIZE);
	    for(int i = 0; i < Configuration.WINDOW_SIZE ; ++i) {
	    	if(! wordTable.containsKey(words[i])) { // 若该字不在字表中，将其加入
	    		wordTable.put(words[i], new Word(words[i]));
	    	}
	    	double[] vector = wordTable.get(words[i]).getVector();
	    	for(int j = 0; j < Configuration.VECTOR_SIZE; ++j) {
	    		input.put(i * Configuration.VECTOR_SIZE + j, vector[j]);
	    	}
	    }
		return input;
	}

	/*
	 * 获取样例的目标输出
	 * 
	 * @return DoubleMatrix 样例的目标输出
	 */
	public DoubleMatrix getTarget() {
		// 根据标签初始化样例的目标输出，采用one-hot表示法
		DoubleMatrix target = DoubleMatrix.zeros(Configuration.TAGS_SIZE);
		switch (tag) {
			case 'O':
				target.put(0, 1.0);
				break;
			case 'N':
				target.put(1, 1.0);
				break;
			case 'B':
				target.put(1, 1.0);
				break;
			case 'M':
				target.put(2, 1.0);
				break;
			case 'E':
				target.put(3, 1.0);
				break;
			default:
				throw new IllegalArgumentException();
		}
		return target;
	}
	
	/*
	 * 更新样例输入向量的偏倒数
	 * 
	 * @param DoubleMatrix inputDiff 输入向量的偏倒数 
	 */
	public void updateDiff(DoubleMatrix inputDiff) {
	}
	
	/*
	 * 更新输入向量
	 * 
	 * @param double alpha 学习率
	 * @param double lambda 规则化参数
	 * @param double eta 动量系数
	 */
	public void updateFeature(double alpha, double lambda, double eta) {
	}
	
	/*
	 * 读文件，加载样例
	 * 
	 * @param String fileName 样例文件
	 * @return ArrayList<Sample> 文件中包含的样例
	 */
	public static ArrayList<Sample> loadFileSample(String fileName) {
		ArrayList<Sample> samples = new ArrayList<Sample>();
		//TODO
		return samples;
	}
	
}
