package com.buaa.dl.model.nn.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;

public class SegUtil {

    public static void main(String[] args) {
        String str = "我是分词123测试字符串SUNDY说他饿了30%";
        List<String> list = SegUtil.segByWord(str);
        System.out.println(list);
        String content = SegUtil.segByWord(str, ",");
        System.out.println(content);
        str = "国际 奥委会 主席 罗格 ， 中共中央 政治局 委员 、 北京 市委 书记 、 北京 奥 组 委 主席 刘淇 在 启动 仪式 上 手持 中国 古钱币 形状 的 青铜 钥匙 ， 联手 打开 金锁 ， 向 全世界 开启 了 北京 2008年 奥运会 市场 开发 大门 。";
        System.out.println("Before Replace Blank:"+str);
        str = SegUtil.replaceBlank(str);
        System.out.println("After Replace Blank:"+str);
        String segByAnsj = SegUtil.segByAnsj(str, ",");
        System.out.println("Ansj NlpAnalysis:"+segByAnsj);
        str="北京2008年奥运会市场开发计划由赞助计划和特许计划组成。";
        String segByAnsjTo = SegUtil.segByAnsjTo(str, " ");
        System.out.println("Ansj ToAnalysis:"+segByAnsjTo);
    }

    /**
     * 一元（单字符）分词
     * 
     * @param str
     * @param split
     * @return
     */
    public static String segByChar(String str, String split) {
        String result = "";
        for (int i = 0; i < str.length(); i++) {
            String term = String.valueOf(str.charAt(i));
            result += term + split;
        }
        return result;
    }

    /**
     * 一元（单字符）分词
     * 
     * @param str
     * @return
     */
    public static List<String> segByChar(String str) {
        List<String> listResult = new ArrayList<String>();
        for (int i = 0; i < str.length(); i++) {
            String term = String.valueOf(str.charAt(i));
            listResult.add(term);
        }
        return listResult;
    }

    /**
     * ansj分词，NLP模式
     * 
     * @param str
     * @return
     */
    public static List<String> segByAnsj(String str) {
        List<String> listResult = new ArrayList<String>();
        List<Term> listTerm = NlpAnalysis.parse(str);
        for (Term term : listTerm) {
            listResult.add(term.getName());
        }
        return listResult;
    }

    /**
     * ansj分词，NLP模式
     * 
     * @param str
     * @param split 结果的分隔符
     * @return
     */
    public static String segByAnsj(String str, String split) {
        String result = "";
        List<Term> listTerm = NlpAnalysis.parse(str);
        for (Term term : listTerm) {
            result += term.getName() + split;
        }
        return result;
    }
    /**
     * ansj分词，To模式
     * 
     * @param str
     * @param split 结果的分隔符
     */
    public static String segByAnsjTo(String str, String split) {
        String result = "";
        List<Term> listTerm = ToAnalysis.parse(str);
        for (Term term : listTerm) {
            result += term.getName() + split;
        }
        return result;
    }
    /**
     * ansj分词，To模式
     * 
     * @param str
     * @param split 结果的分隔符
     */
    public static List<String> segByAnsjTo(String str) {
        List<String> listResult = new ArrayList<String>();
        List<Term> listTerm = ToAnalysis.parse(str);
        for (Term term : listTerm) {
            listResult.add(term.getName());
        }
        return listResult;
    }

    /**
     * 一元+数字+英文单词
     * 
     * @param str
     * @param split
     * @return
     */
    public static String segByWord(String str, String split) {
        String result = "";
        List<Term> listTerm = BaseAnalysis.parse(str);
        for (Term term : listTerm) {
            String r = term.getName();
            result += r + split;
        }
        return result;
    }

    /**
     * 一元+数字+英文单词
     * 
     * @param str
     * @param split
     * @return
     */
    public static String segByWord2(String str, String split) {
        String result = "";
        List<Term> listTerm = BaseAnalysis.parse(str);
        for (Term term : listTerm) {
            String r = term.getName();
            if (isAllNumber(r) || isAllLetter(r)) {
                result += r + split;
            } else {
                result += segByChar(r, split);
            }
        }
        return result;
    }

    /**
     * 一元+数字+英文单词
     * 
     * @param str
     * @param split
     * @return
     */
    public static List<String> segByWord(String str) {
        List<String> listResult = new ArrayList<String>();
        List<Term> listTerm = BaseAnalysis.parse(str);
        for (Term term : listTerm) {
            String r = term.getName();
            if (isAllNumber(r) || isAllLetter(r)) {
                listResult.add(r);
            } else {
                listResult.addAll(segByChar(r));
            }
        }
        return listResult;
    }

    /**
     * 是否全部是字母
     * 
     * @param r
     * @return
     */
    public static boolean isAllLetter(String r) {
        for (int i = 0; i < r.length(); i++) {
            char c = r.charAt(i);
            if (!(isLetter(c))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否字母
     * 
     * @param c
     * @return
     */
    public static boolean isLetter(char c) {
        if (c >= 'a' && c <= 'z') {
            return true;
        } else if (c >= 'A' && c <= 'Z') {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否全部是数字
     * 
     * @param r
     * @return
     */
    public static boolean isAllNumber(String r) {
        for (int i = 0; i < r.length(); i++) {
            char c = r.charAt(i);
            if (!(isNumber(c))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否是数字
     * 
     * @param c
     * @return
     */
    public static boolean isNumber(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否是汉字（严格意义的汉字，不包括标点符号）
     * 
     * @param ch
     * @return
     */
    public static boolean isChinese(char ch) {
        Pattern pattern = Pattern.compile("[\\u4E00-\\u9FBF]+");
        String str = String.valueOf(ch);
        return pattern.matcher(str.trim()).find();
    }

    /**
     * 是否是汉字（严格意义的汉字，不包括标点符号）
     * 
     * @param ch
     * @return
     */
    public static boolean isChinese(String word) {
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!isChinese(ch)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 预处理，抽取汉字
     * 
     * @param line
     * @return
     */
    public static String exactChinese(String line) {
        String result = "";
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);
            if (SegUtil.isChinese(ch)) {
                result += String.valueOf(ch);
            }
        }
        return result;
    }

    public static String replaceBlank(String text) {
//        System.out.println(text);
        text = text.trim();
        String result = "";
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            int type = Character.getType(ch);
            if (12 == type) {// blank
                if (i > 0 && i < text.length() - 1) {
                    char ch_previous = text.charAt(i - 1);
                    int type_previous = Character.getType(ch_previous);
                    char ch_next = text.charAt(i + 1);
                    int type_next = Character.getType(ch_next);
                    while (12 == type_next && i < text.length() - 2) {
                        i++;
                        ch_next = text.charAt(i + 1);
                        type_next = Character.getType(ch_next);
                    }
                    if ((9 == type_previous || 2 == type_previous || 1 == type_previous)
                            && (9 == type_next || 2 == type_next || 1 == type_next)) {
                        result += ch;
                    }
                }
            } else if (18 == type) {// illegal char
            } else {
                result += ch;
            }
        }
//        System.out.println(result);
//        System.out.println();
        return result;
    }

	/**
	 * 分词，只返回汉字
	 * 
	 * @param str
	 * @return
	 */
	public static String segByChinese(String line, String split) {
		String result = "";
		for (int i = 0; i < line.length(); i++) {
			char ch = line.charAt(i);
			if (SegUtil.isChinese(ch)) {
				result += String.valueOf(ch) + split;
			}
		}
		result = result.substring(0, result.length() - split.length());
		return result;
	}

	/**
	 * 分词，只返回汉字
	 * 
	 * @param str
	 * @return
	 */
	public static List<String> segByChinese(String line) {
		List<String> result = new ArrayList<String>();
		for (int i = 0; i < line.length(); i++) {
			char ch = line.charAt(i);
			if (SegUtil.isChinese(ch)) {
				result.add(String.valueOf(ch));
			}
		}
		return result;
	}
}
