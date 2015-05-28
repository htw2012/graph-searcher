package com.buaa.dl.model.nn.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class FileUtil {
	static Logger log = Logger.getLogger(FileUtil.class);
	
	/*
	 * 将List<Sting>中的内容保存到文件（在文件的末尾添加，不覆盖原文件的内容）
	 * 
	 * @param String fileName 要保存到的文件的名称
	 * @param List<String>  list 待保存的list
	 */
	public static void saveFile(String fileName, List<String> list)
			throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(fileName, true);
		for (String line : list) {
			writer.write(line + "\r\n");
		}
		writer.close();
	}
	
	/*
	 * 将List<Sting>中的内容保存到文件（覆盖原文件的内容）
	 * 
	 * @param String fileName 要保存到的文件的名称
	 * @param List<String>  list 待保存的list
	 */
	public static void saveFile2(String fileName, List<String> list)
			throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter writer = new FileWriter(fileName);
		for (String line : list) {
			writer.write(line + "\r\n");
		}
		writer.close();
	}
	
	/*
	 * 按行读文件
	 * 
	 * @param String fileName 待读取的文件
	 * @return List<String> 读取的内容，按行存放在List中
	 */
	public static List<String> readByLine(String fileName) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			log.error("file '" + fileName + "' not exist.");
			return null;
		}
		InputStream input= new FileInputStream(file);
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		List<String> list = new ArrayList<String>();
		String line = null;
		while ((line = reader.readLine()) != null) {
			if (line.trim().length() > 0) {
				list.add(line);
			}
		}
		reader.close();
		input.close();
		return list;
	}
	

	/**
	 * 加载资源文件
	 * @param fileName 资源文件名称
	 * @return
	 * @throws IOException
	 */
	public static List<String> readByResource(String fileName) throws IOException {
		InputStream in = FileUtil.class.getClassLoader().getResourceAsStream(fileName);		
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		List<String> list = new ArrayList<String>();
		String line = null;
		while ((line = reader.readLine()) != null) {
			if (line.trim().length() > 0) {
				list.add(line);
			}
		}
		reader.close();
		in.close();
		return list;
	}
	
	public static List<String> readAsResource(String fileName)
            throws IOException {
        List<String> list = new ArrayList<String>();
        InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(
                fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        is.close();
        return list;
    }
    public static Set<String> readAsResource2(String fileName)
            throws IOException {
        Set<String> list = new HashSet<String>();
        InputStream is = FileUtil.class.getClassLoader().getResourceAsStream(
                fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line = "";
        while ((line = reader.readLine()) != null) {
            list.add(line);
        }
        reader.close();
        is.close();
        return list;
    }
    
    public static void append(String fileName, String line,boolean flag) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            if(!flag){
                file.createNewFile();
                log.warn("file " + file + " has created");
            }else{
                log.error("file " + file + " not exist");
                return;
            }
        }
        //如果为true的话，就需要进行追加
        FileWriter writer = new FileWriter(fileName, true);
        writer.append(line+"\r\n" );
        writer.close();
    }
    public static String readToString(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            log.error("file '" + fileName + "' not exist.");
            return null;
        }
        InputStream in = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String result = "";
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                result += line + " ";
            }
        }
        reader.close();
        in.close();
        return result;
    }
}
