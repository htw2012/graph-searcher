package com.pic.cluster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.google.common.collect.Maps;
/**
 * 
 * <p>图片的根据像素进行聚类操作</p>
 * @author towan
 * @Email  tongwenzide@163.com
 * 2015年1月29日下午8:10:21
 *
 * @version V1.0
 */
public class ImageKmeans {
    public static void main(String[] args) throws IOException {
        //图
        Map<String,int[]> imageNamePixel =  null;//构建数据 //TODO
        int clusterNums = 30;
        int iterNums = 50;
        ImageKmeans wordKmeans = new ImageKmeans(imageNamePixel, clusterNums, iterNums);
        Cluster[] labelClass = wordKmeans.train();
        for (int i = 0; i < labelClass.length; i++) {
            System.out.println("--------Cluster" + i + "---------");
            System.out.println(labelClass[i].getTop(10));
        }
    }

    private Map<String, int[]> paramMap = null;
    private int iter;
    private Cluster[] cArray = null;

    public ImageKmeans(Map<String, int[]> paramMap, int clcn, int iter) {
        this.paramMap = paramMap;
        this.iter = iter;
        cArray = new Cluster[clcn];
    }

    public Cluster[] train() {
        //first 取前clcn个点
        Iterator<Entry<String, int[]>> iterator = paramMap.entrySet().iterator();
        for (int i = 0; i < cArray.length; i++) {
            Entry<String, int[]> next = iterator.next();
            cArray[i] = new Cluster(i, next.getValue());
        }

        for (int i = 0; i < iter; i++) {
            for (Cluster classes : cArray) {
                classes.clean();
            }

            iterator = paramMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<String, int[]> next = iterator.next();
                int miniScore = Integer.MAX_VALUE;
                int tempScore;
                int classesId = 0;
                for (Cluster classes : cArray) {
                    tempScore = classes.distance(next.getValue());
                    if (miniScore > tempScore) {
                        miniScore = tempScore;
                        classesId = classes.id;
                    }
                }
                cArray[classesId].putValue(next.getKey(), miniScore);
            }

            for (Cluster classes : cArray) {
                classes.updateCenter(paramMap);
            }
            System.out.println("iter " + i + " ok!");
        }
        return cArray;
    }

    public static class Cluster {
        //类标签编号 
        private int id;
        //类中心点
        private int[] center;

        public Cluster(int id, int[] center) {
            this.id = id;
            this.center = center.clone();
        }
        //存放中心点        
        Map<String, Integer> values = Maps.newHashMap();

        public int distance(int[] value) {
            int sum = 0;
            for (int i = 0; i < value.length; i++) {
                sum += (center[i] - value[i])*(center[i] - value[i]) ;
            }
            return sum ;
        }

        public void putValue(String word, int score) {
            values.put(word, score);
        }

        /**
         * 重新计算中心
         * @param wordMap
         */
        public void updateCenter(Map<String, int[]> wordMap) {
            for (int i = 0; i < center.length; i++) {
                center[i] = 0;
            }
            int[] value = null;
            for (String keyWord : values.keySet()) {
                value = wordMap.get(keyWord);
                for (int i = 0; i < value.length; i++) {
                    center[i] += value[i];
                }
            }
            for (int i = 0; i < center.length; i++) {
                center[i] = center[i] / values.size();
            }
        }

        /**
         * 清空历史结果
         */
        public void clean() {
            values.clear();
        }

        /**
         * 取得每个类别的前n个结�?         * @param n
         * @return 
         */
        public List<Entry<String, Integer>> getTop(int n) {
            List<Map.Entry<String, Integer>> arrayList = new ArrayList<Map.Entry<String, Integer>>(
                values.entrySet());
            System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
            Collections.sort(arrayList, new Comparator<Map.Entry<String, Integer>>() {
                public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
                    return o1.getValue() > o2.getValue() ? 1 : -1;
                }
            });
            int min = Math.min(n, arrayList.size() - 1);
            if(min<=1)return Collections.emptyList() ;
            return arrayList.subList(0, min);
        }

    }
}
