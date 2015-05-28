/*
 * Copyright 北京航空航天大学  @ 2015 版权所有
 */
package com.buaa.dl.model.nn.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.buaa.dl.model.nn.util.FileUtil;
import com.buaa.dl.model.nn.util.VectorUtils;
import com.buaa.dl.model.nn.util.WordVector;

/**
 * <p>
 * 文件的特征生成
 * </p>
 * @author towan
 * @time 2015年5月28日 
 */
public class FileVecProvider {
    /**
     * <p>
     * 将文件转化为特征文件
     * </p>
     * @param filename
     * @param featureFilename
     * @author towan
     * 2015年5月28日
     * @throws IOException 
     */
    public static void  genFileFeature(String filename, String featureFilename) throws IOException {
        
        PrepareData.splitDocumentToSentence(filename);
        PrepareData.splitSentenctToWordByAnsj(filename);
        
        List<String> lines = FileUtil.readByLine(filename+".word");
        List<double[]> emddings = new  ArrayList<>();
        double[] sumVec = new double[100];
        for(int i=0;i<lines.size();i++){
            String line  = lines.get(i);
            double[] sentVec = WordVector.getSentVec(line.split(" "));
            sumVec = VectorUtils.getSum(sumVec, sentVec);
            emddings.add(sentVec);
        }
        String sumLine = "";
        for(double d:sumVec){
            sumLine +=d+",";
        }
        
        for(double[] db:emddings){
            String line = getLineData(db,sumLine);
        }
        FileUtil.append(featureFilename, sumLine, false);
        
    }
    /**
     * <p>
     * 获得每行，逗号分割
     * </p>
     * @param db
     * @param sumLine
     * @return
     * @author towan
     * 2015年5月28日
     */
    private static String getLineData(double[] db, String sumLine) {
        String line = "";
        
        return null;
    }
    public static void main(String[] args) throws IOException {
        double []vec1 = {1.2,33.44,544.5};
        double []vec2 = {1.25,33.44,644.5};
        String line = vec1[0]+"";
        for(int i=1;i<vec1.length;i++){
            line = String.format("%s, %s", line,vec1[i]);
        }
        for(int i=0;i<vec2.length;i++){
            line = String.format("%s, %s", line,vec2[i]);
        }
        line = line.substring(0, line.length());
        System.out.println(line);
        
        String []strs = line.split(",");
        System.out.println("Size:"+strs.length+"\t"+strs[4]);
    }
    public static void main2(String[] args) throws IOException {
        String filename = "data/test/document";
        String featureFilename = "data/test/featureFile";
        genFileFeature(filename, featureFilename);
    }
}
