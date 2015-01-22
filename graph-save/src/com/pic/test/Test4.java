/*
 * Copyright Lenovo  @ 2015 版权所有
 */
package com.pic.test;

import com.pic.dao.ImageDao;

/**
 * <p>(ss</p>
 * @author jiahc1
 * @Email  jiahc1@lenovo.com
 * 2015-1-17下午5:23:50
 *
 * @version V1.0  
 */
public class Test4 {
	
	
	public static void main(String[] args) {
		ImageDao dao = ImageDao.getInstance();
//		dao.saveAll("contest_data");
		
//		String imgName = "shoes_100003.jpg";
//		long start = System.currentTimeMillis();
//		ImageInfo image = dao.findOne(imgName);
//		long end = System.currentTimeMillis();
//		System.out.println(end - start);
//		
//		boolean b = ImageUtil.createImage(image, "D:/temp/"+imgName);
//		System.out.println(b);
		
//		dao.getAllNodes();
		
//		dao.saveOne("1.jpg");
		
//		dao.checkIndex();
		
//		String[] fileNameArray = {"clothes_235101.jpg",
//		                          "clothes_235102.jpg",
//		                          "clothes_235103.jpg",
//		                          "clothes_235104.jpg",
//		                          "clothes_235105.jpg",
//		                          "clothes_235106.jpg",
//		                          "clothes_235107.jpg",
//		                          "clothes_235108.jpg",
//		                          "clothes_235109.jpg",
//		                          "clothes_235110.jpg"};
//		List<ImageInfo> list = dao.findByArray(fileNameArray);
//		ImageUtil.createImageBatch(list, "copytest");
		
		dao.shutDown();
	}
	

}
