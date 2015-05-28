package com.buaa.dl.model.nn.sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jblas.DoubleMatrix;
import com.buaa.dl.model.nn.data.util.LineDataProvider;
import com.buaa.dl.model.nn.util.FileUtil;

/**
 * 样例
 * 
 */
public class Sample {
    private DoubleMatrix input;
    private DoubleMatrix target;
	
	/**
     * @param input
     * @param target
     */
    public Sample(DoubleMatrix input, DoubleMatrix target) {
        super();
        this.input = input;
        this.target = target;
    }

    /*
	 * 获取样例的输入
	 * 
	 * @return DoubleMatrix 样例的输入
	 */
	public DoubleMatrix getInput() {
		return input;
	}

	/*
	 * 获取样例的目标输出
	 * 
	 * @return DoubleMatrix 样例的目标输出
	 */
	public DoubleMatrix getTarget() {
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
	public static List<Sample> loadFileSample(String fileName) throws IOException {
		List<Sample> samples = new ArrayList<Sample>();
		List<String> lines = FileUtil.readByLine(fileName);
		for(String line:lines){
		    LineDataProvider provider = new LineDataProvider(line);
		    samples.add(new Sample(provider.getInput(), provider.getTarget()));
/*		    if(samples.size() == 300){
		        break; //测试使用
		    }*/
		}
		return samples;
	}
	
}
