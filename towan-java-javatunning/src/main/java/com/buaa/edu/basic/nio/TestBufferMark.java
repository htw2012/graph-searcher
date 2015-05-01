package com.buaa.edu.basic.nio;

import static org.junit.Assert.*;

import java.nio.ByteBuffer;

import org.junit.Test;

public class TestBufferMark {

	@Test
	public void test() {
		//System.out.println("++++++++++++test begin++++++++++++");
		ByteBuffer b=ByteBuffer.allocate(15);
		for(int i=0;i<10;i++){
			b.put((byte)i);
		}
		b.flip();
		for(int i=0;i<b.limit();i++){
			System.out.print(b.get());
			if(i==4){
				b.mark();
				System.out.print("(mark at "+i+")");
			}
		}
		b.reset();
		System.out.println("\nreset to mark");
		while(b.hasRemaining())
			System.out.print(b.get());
		System.out.println();
	}

}
