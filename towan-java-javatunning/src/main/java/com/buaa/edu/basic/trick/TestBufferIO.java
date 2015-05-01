package com.buaa.edu.basic.trick;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

public class TestBufferIO {
	public static int count=10000;
	@Test
	public void testStream() throws IOException{
		DataOutputStream dos=new DataOutputStream(new FileOutputStream("testfile.txt"));
		long start=System.currentTimeMillis();
		for(int i=0;i<count;i++)
			dos.writeBytes(String.valueOf(i)+"\r\n");
		dos.close();
		System.out.println("testStream write file cost:"+(System.currentTimeMillis()-start));
		start=System.currentTimeMillis();
		DataInputStream dis=new DataInputStream(new FileInputStream("testfile.txt"));
		while(dis.readLine() != null);
		dis.close();
		System.out.println("testStream read file cost:"+(System.currentTimeMillis()-start));
	}
	
	@Test
	public void testBufferedStream() throws IOException{
		DataOutputStream dos=new DataOutputStream(new BufferedOutputStream(new FileOutputStream("testfile.txt")));
		long start=System.currentTimeMillis();
		for(int i=0;i<count;i++)
			dos.writeBytes(String.valueOf(i)+"\r\n");
		dos.close();
		System.out.println("testBufferedStream write file cost:"+(System.currentTimeMillis()-start));
		start=System.currentTimeMillis();
		DataInputStream dis=new DataInputStream(new BufferedInputStream( new FileInputStream("testfile.txt")));
		while(dis.readLine() != null);
		dis.close();
		System.out.println("testBufferedStream read file cost:"+(System.currentTimeMillis()-start));
	}
	
	@Test
	public void testReaderWriter()throws IOException{
		FileWriter fw=new FileWriter("testfile.txt");
		long start=System.currentTimeMillis();
		for(int i=0;i<count;i++)
			fw.write(String.valueOf(i)+"\r\n");
		fw.close();
		System.out.println("testReaderWriter write file cost:"+(System.currentTimeMillis()-start));
		start=System.currentTimeMillis();
		FileReader fr=new FileReader("testfile.txt");
		while(fr.read() != -1);
		fr.close();
		System.out.println("testReaderWriter read file cost:"+(System.currentTimeMillis()-start));
	}
	
	@Test
	public void testBufferedReaderWriter()throws IOException{
		BufferedWriter fw=new BufferedWriter(new FileWriter("testfile.txt"));
		long start=System.currentTimeMillis();
		for(int i=0;i<count;i++)
			fw.write(String.valueOf(i)+"\r\n");
		fw.close();
		System.out.println("testBufferedReaderWriter write file cost:"+(System.currentTimeMillis()-start));
		start=System.currentTimeMillis();
		BufferedReader fr=new BufferedReader(new FileReader("testfile.txt"));
		while(fr.read() != -1);
		fr.close();
		System.out.println("testBufferedReaderWriter read file cost:"+(System.currentTimeMillis()-start));
	}
}
