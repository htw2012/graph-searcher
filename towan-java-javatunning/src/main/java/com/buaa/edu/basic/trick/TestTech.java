package com.buaa.edu.basic.trick;

import java.util.ArrayList;

import org.junit.Test;

public class TestTech {
	public static int ta=0; 
	@Test
	public void testInForTryCatch() {
		long starttime=System.currentTimeMillis();
		int a=0;
		for(int i=0;i<100000000;i++){
			try{
				a++;
			}catch(Exception e){
			}
		}
		long endtime=System.currentTimeMillis();
		System.out.println("testInForTryCatch:"+(endtime-starttime));
	}

	
	@Test
	public void testOutForTryCatch() {
		long starttime=System.currentTimeMillis();
		int a=0;
		try{
			for(int i=0;i<100000000;i++){
				a++;
			}
		}catch(Exception e){
		}
		long endtime=System.currentTimeMillis();
		System.out.println("testOutForTryCatch:"+(endtime-starttime));
	}
	
	@Test
	public void testLocalVar() {
		long starttime=System.currentTimeMillis();
		int a=0;
		for(int i=0;i<100000000;i++){
			a++;
		}
		long endtime=System.currentTimeMillis();
		System.out.println("testLocalVar:"+(endtime-starttime));
	}
	
	@Test
	public void testMemberVar() {
		long starttime=System.currentTimeMillis();
		for(int i=0;i<100000000;i++){
			ta++;
		}
		long endtime=System.currentTimeMillis();
		System.out.println("testMemberVar:"+(endtime-starttime));
	}
	
	@Test
	public void testMultiply() {
		long starttime=System.currentTimeMillis();
		long a=100;
		for(int i=0;i<10000000;i++){
			a*=2;
			a/=2;
		}
		long endtime=System.currentTimeMillis();
		System.out.println("testMultiply:"+(endtime-starttime));
	}
	
	@Test
	public void testMultiplyBit() {
		long starttime=System.currentTimeMillis();
		long a=100;
		for(int i=0;i<10000000;i++){
			a<<=1;
			a>>=1;
		}
		long endtime=System.currentTimeMillis();
		System.out.println("testMemberVar:"+(endtime-starttime));
	}
	
	protected int switchInt(int z){
		int i=z%10+1;
		switch(i){
			case 1:return 3;
			case 2:return 6;
			case 3:return 7;
			case 4:return 8;
			case 5:return 10;
			case 6:return 16;
			case 7:return 18;
			case 8:return 44;
			default:return -1;
		}
	}
	
	protected int ifelseInt(int z){
		int i=z%10+1;
		if(i==1)return 3;
		else if(i==2)return 6;
		else if(i==3)return 7;
		else if(i==4)return 8;
		else if(i==5)return 10;
		else if(i==6)return 16;
		else if(i==7)return 18;
		else if(i==8)return 44;
		else return -1;
	}
	
	protected int arrayInt(int[] sw,int z){
		int i=z%10+1;
		if(i>7 || i<1)
			return -1;
		else
			return sw[i];
	}
	
	@Test
	public void testIfelseInt(){
		long starttime=System.currentTimeMillis();
		int re=0;
		for(int i=0;i<10000000;i++){
			re=ifelseInt(i);
		}
		long endtime=System.currentTimeMillis();
		System.out.println("testIfelseInt:"+(endtime-starttime));
	}
	
	@Test
	public void testSwitchInt(){
		long starttime=System.currentTimeMillis();
		int re=0;
		for(int i=0;i<10000000;i++){
			re=switchInt(i);
		}
		long endtime=System.currentTimeMillis();
		System.out.println("testSwitchInt:"+(endtime-starttime));
	}
	
	@Test
	public void testArrayInt(){
		long starttime=System.currentTimeMillis();
		int re=0;
		int[] sw=new int[]{0,3,6,7,8,10,16,18,44};
		for(int i=0;i<10000000;i++){
			re=arrayInt(sw,i);
		}
		long endtime=System.currentTimeMillis();
		System.out.println("testArrayInt:"+(endtime-starttime));
	}

	//@Test
	public void testMethodCall(){
		int re=0;
		ArrayList<Integer> al=new ArrayList<Integer>();
		for(int i=0;i<1000000;i++)
			al.add(i);
		
		long starttime=System.currentTimeMillis();
		for(int k=0;k<1000;k++)
		for(int i=0;i<al.size();i++){
			re=al.get(i);
		}
		long endtime=System.currentTimeMillis();
		System.out.println("testMethodCall:"+(endtime-starttime));
	}
	
	//@Test
	public void testNoMethodCall(){
		int re=0;
		ArrayList<Integer> al=new ArrayList<Integer>();
		for(int i=0;i<1000000;)
			al.add(i++);
		
		long starttime=System.currentTimeMillis();
		int size=al.size();
		for(int k=0;k<1000;k++)
		for(int i=0;i< size;){
			re=al.get(i++);
		}
		long endtime=System.currentTimeMillis();
		System.out.println("testNoMethodCall:"+(endtime-starttime));
	}
	
	@Test
	public void testWeitiqu(){
		double d=Math.random();
		double a=Math.random();
		double b=Math.random();
		double e=Math.random();
		double x,y;
		long starttime=System.currentTimeMillis();
		for(int i=0;i<10000000;i++){
			x=d*a*b/3*4*a;
			y=e*a*b/3*4*a;
		}
		long endtime=System.currentTimeMillis();
		System.out.println("testWeitiqu:"+(endtime-starttime));
	}
	
	@Test
	public void testTiqu(){
		double d=Math.random();
		double a=Math.random();
		double b=Math.random();
		double e=Math.random();
		double t,x,y;
		long starttime=System.currentTimeMillis();
		for(int i=0;i<10000000;i++){
			 t=a*b/3*4*a;
			 x=d*t;
			 y=e*t;
		}
		long endtime=System.currentTimeMillis();
		System.out.println("testTiqu:"+(endtime-starttime));
	}
	
	@Test
	public void testNormaCircle(){
		long starttime=System.currentTimeMillis();
		int[] array=new int[9999999];
		for(int i=0;i<9999999;i++){
			array[i]=i;
		}
		long endtime=System.currentTimeMillis();
		System.out.println("testNormaCircle:"+(endtime-starttime)+" "+array[9999998]);
	}
	
	@Test
	public void testExpandCircle(){
		long starttime=System.currentTimeMillis();
		int[] array=new int[9999999];
		for(int i=0;i<9999999;i+=3){
			array[i]=i;
			array[i+1]=i+1;
			array[i+2]=i+2;
		}
		long endtime=System.currentTimeMillis();
		System.out.println("testExpandCircle:"+(endtime-starttime)+" "+array[9999998]);
	}
	
	@Test
	public void testBitLogic(){
		long starttime=System.currentTimeMillis();
		boolean a=false;
		boolean b=true;
		int d=0;
		for(int i=0;i<10000000;i++)
			if(a&b&"Java_Perform".contains("Java"))
				d=0;
		long endtime=System.currentTimeMillis();
		System.out.println("testBitLogic:"+(endtime-starttime));
	}
	
	@Test
	public void testBooleanLogic(){
		long starttime=System.currentTimeMillis();
		boolean a=false;
		boolean b=true;
		int d=0;
		for(int i=0;i<10000000;i++)
			if(a&&b&&"Java_Perform".contains("Java"))
				d=0;
		long endtime=System.currentTimeMillis();
		System.out.println("testBooleanLogic:"+(endtime-starttime));
	}
	
	@Test
	public void testArrayCopyNormal(){
		int size=100000;
		int[] array=new int[size];
		int[] arraydst=new int[size];
		for(int i=0;i<array.length;i++){
			array[i]=i;
		}
		long starttime=System.currentTimeMillis();
		for(int k=0;k<1000;k++)
			for(int i=0;i<size;i++)
				arraydst[i]=array[i];
		long endtime=System.currentTimeMillis();
		System.out.println("testArrayCopyNormal:"+(endtime-starttime)+" last:"+arraydst[size-1]);
	}
	
	@Test
	public void testArrayCopySystem(){
		int size=100000;
		int[] array=new int[size];
		int[] arraydst=new int[size];
		for(int i=0;i<array.length;i++){
			array[i]=i;
		}
		long starttime=System.currentTimeMillis();
		for(int k=0;k<1000;k++)
			System.arraycopy(array, 0, arraydst, 0, size);
		long endtime=System.currentTimeMillis();
		System.out.println("testArrayCopySystem:"+(endtime-starttime)+" last:"+arraydst[size-1]);
	}
	
}
