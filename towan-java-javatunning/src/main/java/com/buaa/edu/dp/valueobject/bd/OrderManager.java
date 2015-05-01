package com.buaa.edu.dp.valueobject.bd;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.buaa.edu.dp.valueobject.Order;

public class OrderManager extends UnicastRemoteObject implements IOrderManager {
	public OrderManager() throws RemoteException {
		super();
	}
	private static final long serialVersionUID = -1717013007581295639L;

	@Override
	public Order getOrder(int id) throws RemoteException {
		Order o=new Order();
		o.setClientName("billy");
		o.setNumber(20);
		o.setProdunctName("desk");
		return o;
	}

	@Override
	public boolean checkUser(int userid) throws RemoteException {
		return true;
	}

	@Override
	public boolean updateOrder(Order order) throws RemoteException {
		return true;
	}
}
