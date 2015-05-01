package com.buaa.edu.basic.list;

import java.util.LinkedList;

import org.junit.Test;

public class TestLinkedList {

	@Test
	public void test() {
		LinkedList<Integer> l=new LinkedList<Integer>();
		l.add(1);
		l.add(2);
		l.add(3);
		System.out.println(l);
	}

}
