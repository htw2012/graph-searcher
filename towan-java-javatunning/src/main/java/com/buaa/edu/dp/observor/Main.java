package com.buaa.edu.dp.observor;

public class Main {
	public static void main(String[] args) {
		ConcreteSubject sub=new ConcreteSubject();
		sub.attach(new ConcreteObserver());
		sub.inform();
	}
}
