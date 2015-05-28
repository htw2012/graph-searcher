package com.buaa.dl.model.nn.util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.buaa.dl.model.nn.conf.Configuration;

public class WordVector {
    private static Logger log = Logger.getLogger(WordVector.class);
	static Map<String, double[]> map = new HashMap<String, double[]>();
	static {
		init();
	}

	/**
	 * 初始化数据，按行读取
	 */
	private static void init() {
	    String fileName = Configuration.vecFileName;
	    List<String> list = new ArrayList<>();
		try {
		    System.out.println("FileName:"+fileName);
			list = FileUtil.readByLine(fileName);
		} catch (IOException e) {
			log.error("read " + fileName + " failed.");
		}

		if (null == list) {
			log.error("no data");
			return;
		}
		for (String line : list) {
			String[] sets = line.split(" ");
			if (null == sets) {
//				log.warn(line + " skiped.");
				continue;
			}
			int n = sets.length;
			if (n <= 2) {
				continue;
			}
			//词语
			String str = sets[0];
			//词向量
			double[] value = new double[n - 1];
			for (int i = 1; i < n; i++) {
				value[i - 1] = Double.valueOf(sets[i]);
			}
			map.put(str, value);
		}
	}

	/**
	 * 获取字/词语向量
	 * 
	 * @param word 字/词语
	 * @return 字/词语向量
	 */
	public static double[] getVector(String word) {
	    if(null==map.get(word)){
	        log.warn("Can't Get word: ["+word+"]");
	        return null;
	    }
		return map.get(word);
	}
	/**
	 * <p>获得句子向量的表示</p>
	 * @param words
	 * @return
	 * @author towan
	 * 2015年5月28日
	 */
	public static double[] getSentVec(String[] words) {
	    double []ret = new double[100];
	    for(String word:words){
	        ret = VectorUtils.getSum(ret, getVector(word));  
	    }
	    return ret;
	    
    }

}
