package com.buaa.edu.concurrent.dp.producerconsumer;

/**
 * 
 * <p>生成任务或者任务数据</p>
 * @author towan
 * @email tongwenzide@163.com
 * 2015年5月9日
 */
public final class TaskData {
	private  final int intData;
	public TaskData(int d){
		intData=d;
	}
	public TaskData(String d){
		intData=Integer.valueOf(d);
	}
	public int getData(){
		return intData;
	}
	@Override
	public String toString(){
		return "data:"+intData;
	}
}
