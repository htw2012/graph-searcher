package com.buaa.edu.dp.valueobject;

import java.rmi.Naming;

public class Client {
	public static void main(String[] argv) {
		try {
			IOrderManager usermanager = (IOrderManager) Naming
					.lookup("OrderManager");

			long begin = System.currentTimeMillis();
			for (int i = 0; i < 1000; i++) {
				usermanager.getOrder(i);
			}
			System.out.println("getOrder spend:"
					+ (System.currentTimeMillis() - begin));

			begin = System.currentTimeMillis();
			for (int i = 0; i < 1000; i++) {
				usermanager.getClientName(i);
				usermanager.getNumber(i);
				usermanager.getProdName(i);
			}
			System.out.println("3 Method call spend:"
					+ (System.currentTimeMillis() - begin));
			System.out.println(usermanager.getOrder(0).getClientName());
		} catch (Exception e) {
			System.out.println("OrderManager exception: " + e);
		}
	}
}
