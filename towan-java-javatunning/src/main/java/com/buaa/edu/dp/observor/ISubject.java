package com.buaa.edu.dp.observor;

public interface ISubject{  
    void attach(IObserver observer);	  
    void detach(IObserver observer);	
    void inform();					
}  
