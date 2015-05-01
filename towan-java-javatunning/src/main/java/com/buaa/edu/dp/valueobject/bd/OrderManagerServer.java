package com.buaa.edu.dp.valueobject.bd;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class OrderManagerServer {
	  public static void main(String[] argv)   
	   {   
	      try  
	      {   
	         LocateRegistry.createRegistry(1099);   
	         IOrderManager usermananger = new OrderManager();   
	         Naming.rebind("OrderManager", usermananger);   
	         System.out.println("OrderManager is ready.");   
	      }   
	      catch (Exception e)   
	      {   
	         System.out.println("OrderManager Server failed: " + e);   
	      }   
	   }   
}
