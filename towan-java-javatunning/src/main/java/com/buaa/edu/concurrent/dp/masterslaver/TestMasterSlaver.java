package com.buaa.edu.concurrent.dp.masterslaver;

import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class TestMasterSlaver {
    /**
     * 
     * <p>创建一个立方处理器</p>
     * @author towan
     * @email tongwenzide@163.com
     * 2015年5月9日
     */
	public class PlusWorker extends Slaver{
		public Object handle(Object input){
			Integer i=(Integer)input;
			return i*i*i;
		}
	}
	
	@Test
	public void testMasterWorker() {
	    long start = System.nanoTime();
		//master创建
	    Master master = new Master(new PlusWorker(),5);
		
		//任务提交
		for(int i=0;i<1000;i++){
		    master.submit(i);
		}
		//任务执行
		master.execute();
		
		//子任务结果获得
		int re=0;
		Map<String,Object> resultMap = master.getResultMap();
		//这里没有等待所有执行完就进行统计
		while(resultMap.size()>0 || !master.isComplete()){
			Set<String> keys = resultMap.keySet();
			String key=null;
			for(String k:keys){//得到一个就跳出
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
		for(int i=0;i<1000;i++){
			re+=i*i*i;
		}
		System.out.println("testPlus:"+re);
		long end = System.nanoTime();
        System.out.println("Use time:"+(end-start));
	}

}
