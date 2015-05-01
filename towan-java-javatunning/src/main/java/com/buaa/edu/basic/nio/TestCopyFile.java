package com.buaa.edu.basic.nio;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

public class TestCopyFile {
	
	
	public static void nioCopyFile(String resource, String destination)
			throws IOException {
		FileInputStream fis = new FileInputStream(resource);
		FileOutputStream fos = new FileOutputStream(destination);
		FileChannel readChannel = fis.getChannel();
		FileChannel writeChannel = fos.getChannel();   
		ByteBuffer buffer = ByteBuffer.allocate(1024*8);
		while (true) {
			buffer.clear();
			int len = readChannel.read(buffer);
			if (len == -1) {
				break;
			}
			buffer.flip();
			writeChannel.write(buffer);
		}
		readChannel.close();
		writeChannel.close();
	}
	
	public static void ioCopyFile(String resource, String destination) throws IOException{
		FileInputStream fis = new FileInputStream(resource);
		//BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(destination));
		FileOutputStream fos = new FileOutputStream(destination);
		byte[] buffer=new byte[1024*8];
		int count=0;
		while((count=fis.read(buffer))!=-1){
			fos.write(buffer, 0, count);
		}
		fos.close();
		fis.close();
	}
	
	@Test
	public void testIoCopyFile() throws IOException{
		long start=System.currentTimeMillis();
		for(int i=0;i<10;i++)
			ioCopyFile("filecopy.txt","filecopy1.txt");
		System.out.println("ioCopy spend:"+(System.currentTimeMillis()-start));
		
		start=System.currentTimeMillis();
		for(int i=0;i<10;i++)
			nioCopyFile("filecopy.txt","filecopy2.txt");
		System.out.println("nioCopy spend:"+(System.currentTimeMillis()-start));
	}
}
