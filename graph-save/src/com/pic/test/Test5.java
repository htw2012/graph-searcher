package com.pic.test;

import java.awt.Image;

import com.pic.dao.ImageBatchInsert;

public class Test5 {
	
	public static void main(String[] args) {
		ImageBatchInsert dao = new ImageBatchInsert();
		dao.saveAll("contest_data");
		dao.shutDown();
	}

}
