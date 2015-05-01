package com.buaa.edu.dp.decorator;

public class PacketHTTPHeaderCreator extends PacketDecorator{
	public PacketHTTPHeaderCreator(IPacketCreator c) {
		super(c);
	}

	@Override
	public String handleContent() {
		StringBuffer sb=new StringBuffer();
		sb.append("Cache-Control:no-cache\n");
		sb.append("Date:Mon,31Dec201204:25:57GMT\n");
		sb.append(componet.handleContent());
		return sb.toString();
	}
}
