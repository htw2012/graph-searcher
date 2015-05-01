package com.buaa.edu.basic.map;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class TestSet {
	Set set;
	public void initSet(){
		set.add(100);
		set.add(10);
		set.add(4);
		set.add(33);
		set.add(9);
	}
	
	@Test
	public void testHashSet() {
		set=new HashSet();
		initSet();
		System.out.println("testHashSet");
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Integer key = (Integer) iterator.next();
			System.out.println(key);
		}
	}
	@Test
	public void testLinkedHashSet() {
		set=new LinkedHashSet();
		initSet();
		System.out.println("testLinkedHashSet");
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Integer key = (Integer) iterator.next();
			System.out.println(key);
		}
	}
	@Test
	public void testTreeSet() {
		set=new TreeSet();
		initSet();
		System.out.println("testTreeSet");
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			Integer key = (Integer) iterator.next();
			System.out.println(key);
		}
	}

}
