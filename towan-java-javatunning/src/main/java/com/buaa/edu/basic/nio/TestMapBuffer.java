package com.buaa.edu.basic.nio;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

/**
 * �ڴ����
 * 
 * @author Administrator
 * 
 */
public class TestMapBuffer {
	protected static int numOfInts = 4000000;


	
	@Test
	public void testStreamWrite() throws IOException {
		long starttime = System.currentTimeMillis();
		 //DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File("d:\\temp.tmp")),16*1024*1024));  
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File("d:\\temp_stream.tmp"))));
         for(int i = 0; i < numOfInts; i++){  
             dos.writeInt(i);  
         }  
         if(dos != null){  
             dos.close();  
         }  
		long endtime = System.currentTimeMillis();
		System.out.println("testStreamWrite:"+(endtime-starttime)+"ms");
	}
	
	@Test
	public void testStreamRead() throws IOException {
		long starttime = System.currentTimeMillis();
		//DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File("d:\\temp.tmp")),16*1024*1024));  
		DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File("d:\\temp_stream.tmp"))));
        for(int i = 0; i < numOfInts; i++){  
            dis.readInt();  
        }  
        if(dis != null){  
            dis.close();  
        }  
		long endtime = System.currentTimeMillis();
		System.out.println("testStreamRead:"+(endtime-starttime)+"ms");
	}
	 
	
	@Test
	public void testMappedWrite() throws IOException {
		long starttime = System.currentTimeMillis();
		FileChannel fc = new RandomAccessFile("d:\\temp_mapped.tmp", "rw")
				.getChannel();
		IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, numOfInts*4)
				.asIntBuffer();
		for (int i = 0; i < numOfInts; i++) {
			ib.put(i);
		}
		if (fc != null) {
			fc.close();
		}
		long endtime = System.currentTimeMillis();
		System.out.println("testMappedWrite:"+(endtime-starttime)+"ms");
	}
	
	@Test
	public void testMappedRead() throws IOException {
		long starttime = System.currentTimeMillis();
		FileChannel fc = new FileInputStream("d:\\temp_mapped.tmp").getChannel();  
        IntBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).asIntBuffer();  
        while(ib.hasRemaining()){  
            ib.get();  
        }  
        if(fc != null){  
            fc.close();  
        }  
		long endtime = System.currentTimeMillis();
		System.out.println("testMappedRead:"+(endtime-starttime)+"ms");
	}
}
