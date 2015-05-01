package com.buaa.edu.dp.decorator;

public class PacketHTMLHeaderCreator extends PacketDecorator{

	public PacketHTMLHeaderCreator(IPacketCreator c) {
		super(c);
	}

	@Override
	public String handleContent() {
		StringBuffer sb=new StringBuffer();
		sb.append("<html>");
		sb.append("<body>");
		sb.append(componet.handleContent());
		sb.append("</body>");
		sb.append("</html>\n");
		return sb.toString();
	}
}
