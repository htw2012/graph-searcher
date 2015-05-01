package com.buaa.edu.dp.valueobject.bd;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.buaa.edu.dp.valueobject.Order;

public interface IOrderManager  extends Remote {
	 public Order getOrder(int id) throws RemoteException;
	 public boolean checkUser(int userid) throws RemoteException;
	 public boolean updateOrder(Order order) throws RemoteException;
}
