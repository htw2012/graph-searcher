package com.pic.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageTest1 {
	
	public static Map<String,Object> readImg(String path)
	{	
		Map<String, Object> map = new HashMap<String,Object>();
		File file = new File(path);
		BufferedImage image = null;
		try {
			 image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int type = image.getType();
		map.put("type", type);
		
		
		int width=image.getWidth();   
		int height=image.getHeight();
		map.put("width", width);
		map.put("height", height);
		
		int minx=image.getMinX();   
		int miny=image.getMinY(); 
		map.put("minx", minx);
		map.put("miny", miny);
		
		int[][] redPixel = new int[width][height];
		int[][] greenPixel = new int[width][height];
		int[][] bluePixel = new int[width][height];
		
		int[][] pixelArr = new int[width][height];
		
		int grayArr[] = new int[256];
		
		for(int i=minx;i<width;i++)   
		{   
			for(int j=miny;j<height;j++)  
			{    
				int pixel=image.getRGB(i, j);  
				pixelArr[i][j]= pixel;
				redPixel[i][j] = (pixel & 0xff0000 ) >> 16   ;      
				greenPixel[i][j] = (pixel & 0xff00 ) >> 8       ;   
				bluePixel[i][j] = (pixel & 0xff );      
				//System.out.println("i="+i+",j="+j+":("+rgb[0]+","+rgb[1]+","+rgb[2]+")"); 
				int gray = (int)(redPixel[i][j]*0.3 + greenPixel[i][j]*0.59 +bluePixel[i][j]*0.11);
				grayArr[gray]++;
			}  
			
		} 
		
		map.put("pixelArr", pixelArr);
		map.put("redPixel", redPixel);
		map.put("greenPixel", greenPixel);
		map.put("bluePixel", bluePixel);
		map.put("grayArr", grayArr);
				
		return map;
	}
	
	public static boolean creatImg(Map map,String outPath)
	{
		File file = new File(outPath);
		int type = (int)map.get("type");
		int width = (int)map.get("width");
		int height = (int)map.get("height");
		int minx = (int)map.get("minx");
		int miny = (int)map.get("miny");
		int[][] pixelArr = (int[][])map.get("pixelArr");
		
		BufferedImage image = new BufferedImage(width,height,type);
		for(int i=minx;i<width;i++)   
		{   
			for(int j=miny;j<height;j++)  
			{    
				image.setRGB(i, j, pixelArr[i][j]);
			}  
			
		} 
		
		try {
			ImageIO.write(image, "", file);
		} catch (IOException e) {
			
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		String path = "D:\\robotdog.jpg";
		Map<String,Object> map = readImg(path);
		for(String key : map.keySet())
		{
			Object o = map.get(key);
			System.out.println("key= "+ key + " and value= " + map.get(key));
		}
		System.out.println();

	}

}
