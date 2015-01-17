package com.pic.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageTest3 {
	
	//根据图片路径，读取图片信息，存储到map中并返回
	public static Map<String,Object> readImg(String path)
	{	
		Map<String, Object> map = new HashMap<String,Object>();
		File file = new File(path);
		if(!file.exists())
		{
			System.out.println("目标图片不存在");
			return null;
		}
		String fileName = file.getName();
		map.put("fileName", fileName);
		String formatType = fileName.substring(fileName.lastIndexOf(".")+1);
		map.put("formatType", formatType);
		BufferedImage image = null;
		try {
			 image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(image == null)
		{
			System.out.println("图片读取失败，请检查图片");
			return null;
		}
		
		int imageType = image.getType();
		map.put("imageType", imageType);
		
		
		int width=image.getWidth();   
		int height=image.getHeight();
		map.put("width", width);
		map.put("height", height);
		
		int minx=image.getMinX();   
		int miny=image.getMinY(); 
		map.put("minx", minx);
		map.put("miny", miny);
		
		int offset = 0;  
        int scansize = width; 
        
        int[] pixelArray = new int[offset + (height - miny) * scansize]; 
        image.getRGB(minx, miny, width, height, pixelArray, offset, scansize); 
        
        map.put("pixelArray", pixelArray);
		
				
		return map;
	}
	
	public static boolean createImage(Map<String,Object> map,String outPath)
	{    
		if(map == null)
		{
			System.out.println("map 输入为 null");
			return false;
		}
		 OutputStream output = null;
		 int imageType = (int)map.get("imageType"); 
		 int width = (int)map.get("width");
		 int height = (int)map.get("height");
		 int scansize = width;
		 int minx = (int)map.get("minx");
		 int miny = (int)map.get("miny");
		 int[] pixelArray = (int[])map.get("pixelArray");
		 String formatType = (String)map.get("formatType");
		 try {
			File file = new File(outPath);  
			 if (!file.exists())  
			 {
				 File dir = file.getParentFile();
				 if(!dir.exists())
					 dir.mkdirs();
				 file.createNewFile();  
			 }else
			 {
				 System.out.println("图片已存在");
				 return false;
			 }
			     
			 output = new FileOutputStream(file);  
			 BufferedImage imgOut = new BufferedImage(width, height, imageType); 
			 //offset 暂时设定为0
//           imgOut.setRGB(minx, miny, width, height, pixelArray, offset, scansize);  
			 imgOut.setRGB(minx, miny, width, height, pixelArray, 0, scansize); 
			 ImageIO.write(imgOut, formatType, output);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {  
            if (output != null)  
                try {  
                    output.close();  
                } catch (IOException e) {  
                }  
        }  
		return true;
	}
	
	
	public static void main(String[] args) {
		String inPath = "D:/11.jpg";
		String outPath = "D:/1copy.jpg";
		Map<String,Object> map = readImg(inPath);
		boolean b = createImage(map,outPath);
		System.out.println(b);
	}

}
