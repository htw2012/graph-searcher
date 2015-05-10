package com.buaa.edu.concurrent.lockfree.amino;

import java.util.List;
import java.util.Vector;

import org.amino.pattern.internal.Doable;
import org.amino.pattern.internal.DynamicWorker;
import org.amino.pattern.internal.MasterWorker;
import org.amino.pattern.internal.MasterWorker.ResultKey;
import org.amino.pattern.internal.MasterWorkerFactory;
import org.amino.pattern.internal.WorkQueue;
import org.junit.Test;

public class TestMasterWorker {

	public class Pow3 implements Doable<Integer,Integer>{
		@Override
		public Integer run(Integer input) {

			return input*input*input;
		}
	}
	
	public class Pow3Dyn implements DynamicWorker<Integer,Integer>{
		@Override
		public Integer run(Integer w, WorkQueue<Integer> wq) {
			return w*w*w;
		}
	}
	
	@Test
	public void testStatic() {
		MasterWorker<Integer,Integer> mw=MasterWorkerFactory.newStatic(new Pow3());
		List<MasterWorker.ResultKey> keyList=new Vector<MasterWorker.ResultKey>();
		for(int i=0;i<100;i++){
		  //调度任务，key用于取得结果
		    keyList.add(mw.submit(i));
		}
		mw.execute();
		int re=0;
		while(keyList.size()>0){					
		    //不等待全部执行完，就开始求和
			ResultKey k=keyList.get(0);
			//由Key取得一个任务计算结果
			Integer i=mw.result(k);
			if(i!=null){
				re+=i;
				keyList.remove(0);
			}
		}
		System.out.println(re);
	}
	
	@Test
	public void testDynamic() {
		MasterWorker<Integer,Integer> mw=MasterWorkerFactory.newDynamic(new Pow3Dyn());
		List<MasterWorker.ResultKey> keyList=new Vector<MasterWorker.ResultKey>();
		for(int i=0;i<50;i++)
			keyList.add(mw.submit(i));
		mw.execute();								
		//可以持续添加任务，而静态不能
		for(int i=50;i<100;i++)
			keyList.add(mw.submit(i));
		int re=0;
		while(keyList.size()>0){					
			MasterWorker.ResultKey k=keyList.get(0);
			Integer i=mw.result(k);
			if(i!=null){
				re+=i;
				keyList.remove(0);
			}
		}
		System.out.println(re);
	}

	
}
