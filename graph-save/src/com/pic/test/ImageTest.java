package com.pic.test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageTest {
	
	public static void main(String[] args) {
		try {
			File file = new File("D:\\robotdog.jpg");  
			BufferedImage image = ImageIO.read(file);
			System.out.println(image);
			int tran = image.getTransparency();
			int grayArr[] = new int[256];
			int rgb[] = new int[3]; 
			int width=image.getWidth();   
			int height=image.getHeight();
			int minx=image.getMinX();   
			int miny=image.getMinY();     
			System.out.println("width="+width+",height="+height+".");   
			System.out.println("minx="+minx+",miniy="+miny+".");  
			for(int i=minx;i<width;i++)   
			{   
				for(int j=miny;j<height;j++)  
				{    
					int pixel=image.getRGB(i, j);  
					int transparency = (pixel & 0xff000000) >> 24;
					rgb[0] = (pixel & 0xff0000 ) >> 16   ;      
					rgb[1] = (pixel & 0xff00 ) >> 8       ;   
					rgb[2] = (pixel & 0xff );      
					//System.out.println("i="+i+",j="+j+":("+rgb[0]+","+rgb[1]+","+rgb[2]+")"); 
					int gray = (int)(rgb[0]*0.3 + rgb[1]*0.59 +rgb[2]*0.11);
					grayArr[gray]++;
				}  
				
			} 
			double[] pArr = new double[256];
			double sum = 0;
			for(int i=0;i<256;i++)
			{
				  pArr[i] = grayArr[i] * 1.0 / (width * height);   //每一灰度值出现的概率  
				  System.out.println(pArr[i]);
				  sum += pArr[i] * (Math.log(1/pArr[i]) / Math.log(2));       //熵 
			}
			System.out.println(sum);
			
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	}

}
