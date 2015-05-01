package com.buaa.edu.basic.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class TestIterator {
	List<String> list = null;
	private static final int CIRCLE = 1000000;

	public void initList(List<String> list) {
		list.clear();
		for (int i = 0; i < CIRCLE; i++) {
			list.add(String.valueOf(0));
		}
	}

	public void internalTest() {
		String tmp;
		long start = System.currentTimeMillis();
		for (String s : list) {
			tmp = s;
		}
		System.out.println("foreach spend:"
				+ (System.currentTimeMillis() - start));

		start = System.currentTimeMillis();
		for (Iterator<String> it = list.iterator(); it.hasNext();) {
			tmp = it.next();
		}
		System.out.println("Iterator spend;"
				+ (System.currentTimeMillis() - start));

		if(list instanceof LinkedList)return;
		start = System.currentTimeMillis();
		int size = list.size();
		for (int i = 0; i < size; i++) {
			tmp = list.get(i);
		}
		System.out.println("for spend;" + (System.currentTimeMillis() - start));
	}

	@Test
	public void testArrayList() {
		list = new ArrayList<String>();
		initList(list);
		internalTest();
	}

	@Test
	public void testLinkedList() {
		list = new LinkedList<String>();
		initList(list);
		internalTest();
	}
}
