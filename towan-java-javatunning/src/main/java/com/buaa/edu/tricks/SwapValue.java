package com.buaa.edu.tricks;

public class SwapValue {
	public static void main(String args[]){
		int a=55;
		int b=66;
		a=a+b;
		b=a-b;
		a=a-b;
		System.out.println("a="+a+" b="+b);
	}
}
