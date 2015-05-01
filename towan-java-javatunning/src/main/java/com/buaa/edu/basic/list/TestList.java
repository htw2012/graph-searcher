package com.buaa.edu.basic.list;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.junit.Test;

public class TestList {
	private static final int CIRCLE1 = 100000;
	protected List<Object> list;
	
	protected void testAddTail(String funcname){
		Object obj=new Object();
		long starttime=System.currentTimeMillis();
		
		for(int i=0;i<500000;i++){
			list.add(obj);
		}
		long endtime=System.currentTimeMillis();
		System.out.println(funcname+": "+(endtime-starttime));
	}
	
	protected void testDelTail(String funcname){
		Object obj=new Object();
		for(int i=0;i<CIRCLE1;i++){
			list.add(obj);
		}
		
		long starttime=System.currentTimeMillis();
		while(list.size()>0){
			list.remove(list.size()-1);
		}
		long endtime=System.currentTimeMillis();
		System.out.println(funcname+": "+(endtime-starttime));
	}
	
	protected void testDelFirst(String funcname){
		Object obj=new Object();
		for(int i=0;i<CIRCLE1;i++){
			list.add(obj);
		}
		
		long starttime=System.currentTimeMillis();
		while(list.size()>0){
			list.remove(0);
		}
		long endtime=System.currentTimeMillis();
		System.out.println(funcname+": "+(endtime-starttime));
	}
	
	protected void testDelMiddle(String funcname){
		Object obj=new Object();
		for(int i=0;i<CIRCLE1;i++){
			list.add(obj);
		}
		
		long starttime=System.currentTimeMillis();
		while(list.size()>0){
			list.remove(list.size()>>1);
		}
		long endtime=System.currentTimeMillis();
		System.out.println(funcname+": "+(endtime-starttime));
	}
	
	protected void testAddFirst(String funcname){
		Object obj=new Object();
		long starttime=System.currentTimeMillis();
		
		for(int i=0;i<50000;i++){
			list.add(0, obj);
		}
		long endtime=System.currentTimeMillis();
		System.out.println(funcname+": "+(endtime-starttime));
	}
	
	
	//====add tail
	@Test
	public void testAddTailArrayList() {
		list=new ArrayList();
		testAddTail("testAddTailArrayList");
	}
	
	//@Test
	public void testAddTailVector() {
		list=new Vector();
		testAddTail("testAddTailVector");
	}
	
	@Test
	public void testAddTailLinkedList() {
		list=new LinkedList();
		testAddTail("testAddTailLinkedList");
	}
	
	//====add first
	//@Test
	public void testAddFirstArrayList() {
		list=new ArrayList();
		testAddFirst("testAddFirstArrayList");
	}
	
	//@Test
	public void testAddFirstVector() {
		list=new Vector();
		testAddFirst("testAddFirstVector");
	}
	
	//@Test
	public void testAddFirstLinkedList() {
		list=new LinkedList();
		testAddFirst("testAddFirstLinkedList");
	}
	
	//====delete tail
	//@Test
	public void testDeleteTailArrayList() {
		list=new ArrayList();
		
		testDelTail("testDeleteTailArrayList");
	}
	
	//@Test
	public void testDeleteTailVector() {
		list=new Vector();
		testDelTail("testDeleteTailVector");
	}
	
	//@Test
	public void testDeleteTailLinkedList() {
		list=new LinkedList();
		testDelTail("testDeleteTailLinkedList");
	}
	
	//====delete first
	//@Test
	public void testDeleteFirstArrayList() {
		list=new ArrayList();
		testDelFirst("testDeleteFirstArrayList");
	}
	
	//@Test
	public void testDeleteFirstVector() {
		list=new Vector();
		testDelFirst("testDeleteFirstVector");
	}
	
	//@Test
	public void testDeleteFirstLinkedList() {
		list=new LinkedList();
		testDelFirst("testDeleteFirstLinkedList");
	}
	
	//@Test
	public void testDeleteMiddleLinkedList() {
		list=new LinkedList();
		testDelMiddle("testDeleteMiddleLinkedList");
	}
	
	//@Test
	public void testDeleteMiddleArrayList() {
		list=new ArrayList();
		testDelMiddle("testDeleteMiddleArrayList");
	}
}
