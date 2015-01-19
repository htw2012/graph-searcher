/*
 * Copyright Lenovo  @ 2015 版权所有
 */
package com.pic.test;

import com.pic.dao.ImageDao;
import com.pic.model.ImageInfo;
import com.pic.util.ImageUtil;

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
		String imgName = "clothes_145080.jpg";
		long start = System.currentTimeMillis();
		ImageInfo image = dao.load(imgName);
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		
		boolean b = ImageUtil.createImage(image, "E:/temp/"+imgName);
		System.out.println(b);
		
		dao.shutDown();
	}
	

}
