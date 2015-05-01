package com.buaa.edu.dp.observor;

import java.util.Vector;

public class ConcreteSubject implements ISubject{
	Vector<IObserver> observers=new Vector<IObserver>();
    public void attach(IObserver observer){  
    	observers.addElement(observer);  
    }  
    public void detach(IObserver observer){  
    	observers.removeElement(observer);  
    }  
    public void inform(){
    	Event evt=new Event();
    	for(IObserver ob:observers){
    		ob.update(evt);
    	}
    }  
}  