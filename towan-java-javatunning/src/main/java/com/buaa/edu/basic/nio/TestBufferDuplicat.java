package com.buaa.edu.basic.nio;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.Test;

public class TestBufferDuplicat {

	@Test
	public void test() {
		//System.out.println("++++++++++++test begin++++++++++++");
		ByteBuffer b=ByteBuffer.allocate(15);
		for(int i=0;i<10;i++){
			b.put((byte)i);
		}
		ByteBuffer c=b.duplicate();
		System.out.println("After b.duplicate()");
		System.out.println(b);
		System.out.println(c);
		c.flip();
		System.out.println("After c.flip()");
		System.out.println(b);
		System.out.println(c);
		c.put((byte)(100));
		System.out.println("After c.put((byte)(100))");
		System.out.println("b.get(0)="+b.get(0));
		System.out.println("c.get(0)="+c.get(0));

	}

}
