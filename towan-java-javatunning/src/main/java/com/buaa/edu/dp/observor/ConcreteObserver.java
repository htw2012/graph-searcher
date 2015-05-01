package com.buaa.edu.dp.observor;

public class ConcreteObserver implements IObserver{  
    public void update(Event evt){  
    	System.out.println("obserer receives information");
    }  
}  
