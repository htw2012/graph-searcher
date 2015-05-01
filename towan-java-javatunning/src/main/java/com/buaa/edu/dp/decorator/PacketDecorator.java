package com.buaa.edu.dp.decorator;

public abstract class PacketDecorator implements IPacketCreator{
	IPacketCreator componet;
	public PacketDecorator(IPacketCreator c){
		componet=c;
	}
}
