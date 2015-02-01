package com.pic.test;

import com.pic.dao.ImageBatchInsert;

public class Test5 {
	
	public static void main(String[] args) {
	       // GraphDatavaseService gds=new RestGraphDatabase("http://10.108.xxx.xx:7474/db/data")
	        ImageBatchInsert daoBatch = new ImageBatchInsert();
	        daoBatch.saveAll("images");
	        daoBatch.shutDown();

	    }

}
