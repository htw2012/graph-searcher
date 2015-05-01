package com.buaa.edu.dp.decorator;

public class PacketBodyCreator implements IPacketCreator{
	@Override
	public String handleContent() {
		return "Content of Packet";
	}
}
