package com.buaa.edu.dp.valueobject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class OrderManager extends UnicastRemoteObject implements IOrderManager {
	protected OrderManager() throws RemoteException {
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
	public String getClientName(int id) throws RemoteException {
		return "billy";
	}

	@Override
	public String getProdName(int id) throws RemoteException {
		return "desk";
	}

	@Override
	public int getNumber(int id) throws RemoteException {
		return 20;
	}
}
