package com.buaa.dl.model.nn.sample;

import org.jblas.DoubleMatrix;

import com.buaa.dl.model.nn.conf.Configuration;

/**
 * 字
 * 
 */
public class Word {
    private String word; // 字
    private double[] vector; // 向量

    // public static Map<String, double[]> map = new HashMap<String, double[]>();
    // static { init(); }

    /*
     * 初始化数据
     */
    /*
     * private static void init() { try { List<String> lines1 =
     * FileUtil.readByLine(Configuration.trainFileName); List<String> lines2 =
     * FileUtil.readByLine(Configuration.testFileName); lines1.addAll(lines2); String term = "";
     * String line; for(int i = 0; i < lines1.size(); i += 2) { line = lines1.get(i); line =
     * line.trim(); System.out.println(i + "\t" + line); for(int j = 0; j < line.length(); ++j) {
     * //System.out.println(line.charAt(j)); term = String.valueOf(line.charAt(j)); if(!
     * map.containsKey(term)) map.put(term, WordVectorRedis.readFromRedis(term,
     * Configuration.VECTOR_SIZE)); } } } catch (Exception e) { e.printStackTrace(); } }
     */

    /*
     * 构造函数
     * 
     * @param String word 字
     */
    public Word(String word) {
        this.word = word;
        initWordEmbedding(word);
    }

    public void initWordEmbedding(String word) {
        if (word.equals(" ")) {
            vector = new double[Configuration.VECTOR_SIZE]; // 初始化为0向量
        } else { // 非空格，使用word2vec训练出的字向量
            vector = new double[Configuration.VECTOR_SIZE]; // 找不到该字的向量，以0向量代替
        }
    }

    /*
     * 获取字
     * 
     * @return String 字
     */
    public String getWord() {
        return word;
    }

    /*
     * 获取字向量
     * 
     * @return double[] 字向量
     */
    public double[] getVector() {
        return vector;
    }

    /*
     * 更新字向量的偏倒数
     * 
     * @param DoubleMatrix diff 字向量的偏倒数
     */
    public void updateDiff(DoubleMatrix inputDiff) {}

    /*
     * 更新字向量
     * 
     * @param double alpha 学习率
     * @param double lambda 规则化参数
     * @param double eta 动量系数
     */
    public void updateFeature(double alpha, double lambda, double eta) {}
}
