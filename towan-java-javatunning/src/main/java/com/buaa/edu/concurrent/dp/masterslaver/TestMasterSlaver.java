package com.buaa.edu.concurrent.dp.masterslaver;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class TestMasterSlaver {
    //立方和
	public class PlusWorker extends Slaver{
		public Object handle(Object input){
			Integer i=(Integer)input;
			return i*i*i;
		}
	}
	
	@Test
	public void testMasterWorker() {
	    long start = System.nanoTime();
		Master master = new Master(new PlusWorker(),5);
		for(int i=0;i<100;i++)
			master.submit(i);
		master.execute();
		int re=0;
		Map<String,Object> resultMap = master.getResultMap();
		while(resultMap.size()>0 || !master.isComplete()){
			Set<String> keys=resultMap.keySet();
			String key=null;
			for(String k:keys){
				key=k;
				break;
			}
			Integer result=null;
			if(key!=null)
				result =(Integer)resultMap.get(key);
			if(result!=null)
				re += result;
			if(key!=null)
				resultMap.remove(key);
		}
		
		System.out.println("testMasterSlaver:"+re);
		long end =System.nanoTime();
		System.out.println("Use time:"+(end-start));
	}
	
	@Test
	public void testPlus(){
	    long start = System.nanoTime();
		int re=0;
		for(int i=0;i<100;i++){
			re+=i*i*i;
		}
		System.out.println("testPlus:"+re);
		long end = System.nanoTime();
        System.out.println("Use time:"+(end-start));
	}

}
