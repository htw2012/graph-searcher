package com.buaa.edu.concurrent.dp.masterslaver;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;




public class Master {
	//任务队列
	protected Queue<Object> workQueue = new ConcurrentLinkedQueue<Object>();
	//slaver线程队列
	protected Map<String,Thread> threadMap=new HashMap<String,Thread>();
	//子任务处理结果集
	protected Map<String,Object> resultMap = new ConcurrentHashMap<String,Object>();
	
	//是否所有的子任务都结束了
	public boolean isComplete(){
		for(Map.Entry<String,Thread> entry:threadMap.entrySet()){
		    //判断线程状态
			if(entry.getValue().getState()!=Thread.State.TERMINATED){
				return false;
			}
		}
		return true;
	}
	
	//Master的构造，需要一个Slaver进程逻辑，和需要的Slaver进程数量
	public Master(Slaver slaver,int countSlaver){
		slaver.setWorkQueue(workQueue);
		slaver.setResultMap(resultMap);
		for(int i=0;i<countSlaver ;i++)
			threadMap.put(Integer.toString(i), new Thread(slaver,Integer.toString(i)));
	}
	
	//提交一个任务
	public void submit(Object job){
		workQueue.add(job);
	}
	
	//返回子任务结果集
	public Map<String,Object>  getResultMap(){
		return resultMap;
	}
	
	//开始运行所有的slaver进程，进行处理
	public void execute(){
		for(Map.Entry<String, Thread> entry:threadMap.entrySet()){
			entry.getValue().start();
		}
	}
}
