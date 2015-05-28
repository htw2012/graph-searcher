package com.buaa.dl.model.nn.data;

import java.io.File;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.buaa.dl.model.nn.util.FileUtil;
import com.buaa.dl.model.nn.util.SegUtil;


public class PrepareData {

    public static void main(String[] args) throws IOException {

    }

    /**
     * 给句子分词。NLP分词模式
     * 
     * <p>
     * 存储为：fileName + ".word"。每行原句子的分词结果，空格分隔
     * </p>
     * 
     * @param fileName 。文件名称，文件的每行是一句话
     * @throws IOException
     */
    public static void splitSentenctToWordByAnsj(String fileName) throws IOException {
        File file = new File(fileName + ".word");
        if (file.exists()) {
//            return;
        }
        List<String> lines = FileUtil.readByLine(fileName + ".sent");// 原始文件
        List<String> list = new ArrayList<String>();// 句子的分词结果
        for (String line : lines) {
//            String r = SegUtil.segByAnsj(line, " ");
//            list.add(r);
            //修改为to模型分词
            line = SegUtil.replaceBlank(line);
    /*        List<String> segByAnsjTo = SegUtil.segByAnsjTo(line);
            list.addAll(segByAnsjTo);*/
          String r = SegUtil.segByAnsjTo(line, " ");
          list.add(r);
        }
        //行数增多了
        FileUtil.saveFile(fileName + ".word", list);
    }

    /**
     * 给句子分词。WORD分词模式
     * 
     * <p>
     * 存储为：model/data.txt。每行原句子的分词结果，空格分隔
     * </p>
     * 
     * @param folderName 文件所在目录，文件的每行是一句话
     * @param fileName 文件名称
     * @throws IOException
     */
    public static void splitSentenctToWordByWord(String folderName, String fileName)
            throws IOException {
        folderName = folderName + "model/";
        File file = new File(folderName);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(folderName + "data.txt");
        if (file.exists()) {
            return;
        }
        List<String> lines = FileUtil.readByLine(fileName + ".sent");// 原始文件
        List<String> list = new ArrayList<String>();// 句子的分词结果
        for (String line : lines) {
            line = SegUtil.replaceBlank(line);
            String r = SegUtil.segByWord2(line, " ");
            list.add(r);
        }
        FileUtil.saveFile(folderName + "data.txt", list);
    }

    /**
     * 将文章拆分为句子。
     * <p>
     * 存储为：fileName + ".sent"。每行是一句话
     * </p>
     * 
     * @param fileName 。 一个文件是一篇文档
     * @return 
     * @throws IOException
     */
    public static List<String> splitDocumentToSentence(String fileName) throws IOException {
        String text = FileUtil.readToString(fileName);// 原始文件
        List<String> list = new ArrayList<String>();
		Locale currentLocale = new Locale("en", "US");
		BreakIterator sentenceIter = BreakIterator
				.getSentenceInstance(currentLocale);
		sentenceIter.setText(text);
		int end = sentenceIter.last();
		for (int start = sentenceIter.previous(); start != BreakIterator.DONE; end = start, start = sentenceIter
				.previous()) {
			list.add(text.substring(start, end));
		}
        FileUtil.saveFile(fileName + ".sent", list);
		return list;
    }

}
