package com.buaa.edu.basic.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

public class TestNioBuffer extends TestMapBuffer {

	public static byte[] int2byte(int res) {
		byte[] targets = new byte[4];
		targets[3] = (byte) (res & 0xff);// ���λ 
		targets[2] = (byte) ((res >> 8) & 0xff);// �ε�λ 
		targets[1] = (byte) ((res >> 16) & 0xff);// �θ�λ 
		targets[0] = (byte) (res >>> 24);// ���λ,�޷�����ơ� 
		return targets; 
	}
	
	public static int byte2int(byte b1,byte b2,byte b3,byte b4) {
		return ((b1& 0xff)<<24)|((b2& 0xff)<<16)|((b3& 0xff)<<8)|(b4& 0xff);
	}
	
	/**
	 * ʹ��bufferֱ��д�ļ�
	 * @throws IOException
	 */
	@Test
	public void testBufferWrite() throws IOException {
		long starttime = System.currentTimeMillis();
		// DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File("d:\\temp.tmp")),16*1024*1024));  
		FileOutputStream fout = new FileOutputStream(new File("d:\\temp_buffer.tmp"));
		FileChannel fc=fout.getChannel();
		ByteBuffer byteBuffer=ByteBuffer.allocate(numOfInts*4);
        for(int i = 0; i < numOfInts; i++){  
        	byteBuffer.put(int2byte(i));
        }  
        byteBuffer.flip();
        fc.write(byteBuffer);
		long endtime = System.currentTimeMillis();
		System.out.println("testBufferWrite:"+(endtime-starttime)+"ms");
	}
	

	/**
	 * ͨ��buffer��ȡ�ļ�
	 * @throws IOException
	 */
	@Test
	public void testBufferRead() throws IOException {
		long starttime = System.currentTimeMillis();
		// DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File("d:\\temp.tmp")),16*1024*1024));  
		FileInputStream fin = new FileInputStream(new File("d:\\temp_buffer.tmp"));
		FileChannel fc=fin.getChannel();
		ByteBuffer byteBuffer=ByteBuffer.allocate(numOfInts*4);
		fc.read(byteBuffer);
		fc.close();
		byteBuffer.flip();
        while(byteBuffer.hasRemaining()){  
        	byte2int(byteBuffer.get(),  byteBuffer.get(),byteBuffer.get(),byteBuffer.get());
        }  
		long endtime = System.currentTimeMillis();
		System.out.println("testBufferRead:"+(endtime-starttime)+"ms");
	}
	
	//@Test
	public void testBufferWriteInt() throws IOException {
		long starttime = System.currentTimeMillis();
		// DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File("d:\\temp.tmp")),16*1024*1024));  
		FileOutputStream fout = new FileOutputStream(new File("d:\\temp_buffer_int.tmp"));
		FileChannel fc=fout.getChannel();
		ByteBuffer byteBuffer=ByteBuffer.allocate(numOfInts*4);
        for(int i = 0; i < numOfInts; i++){  
        	byteBuffer.put(int2byte(i));
        }  
        byteBuffer.flip();
        fc.write(byteBuffer);
		long endtime = System.currentTimeMillis();
		System.out.println("testBufferWriteInt:"+(endtime-starttime)+"ms");
	}
	
	//@Test
	public void testBufferReadInt() throws IOException {
		long starttime = System.currentTimeMillis();
		// DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File("d:\\temp.tmp")),16*1024*1024));  
		FileInputStream fin = new FileInputStream(new File("d:\\temp_buffer_int.tmp"));
		FileChannel fc=fin.getChannel();
		ByteBuffer byteBuffer=ByteBuffer.allocate(numOfInts*4);
		fc.read(byteBuffer);
		fc.close();
		byteBuffer.flip();
		IntBuffer ib=byteBuffer.asIntBuffer();
        while(ib.hasRemaining()){  
        	ib.get();
        }  
		long endtime = System.currentTimeMillis();
		System.out.println("testBufferReadInt:"+(endtime-starttime)+"ms");
	}
	
	
}
