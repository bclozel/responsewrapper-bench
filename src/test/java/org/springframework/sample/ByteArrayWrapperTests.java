package org.springframework.sample;

import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import org.springframework.sample.support.FastByteArrayOutputStream;
import org.springframework.util.ResizableByteArrayOutputStream;

public class ByteArrayWrapperTests {

	private static final int iterations = 500000;

	private byte[] bytes;

	private void generateRandomBytes() {
		Random random = new Random();
		this.bytes = new byte[20 * 1024];
		random.nextBytes(this.bytes);
	}

	@Test
	public void resizableByteArrayFixed() throws Exception {
		for(int i=0; i<iterations; i++) {
			generateRandomBytes();
			ResizableByteArrayOutputStream baos = new ResizableByteArrayOutputStream(1024);
			baos.resize(this.bytes.length);
			baos.write(this.bytes);
			Assert.assertTrue(baos.size() > 0);
		}
	}

	@Test
	public void resizableByteArrayUnknown() throws Exception {
		for(int i=0; i<iterations; i++) {
			generateRandomBytes();
			ResizableByteArrayOutputStream baos = new ResizableByteArrayOutputStream(1024);
			baos.write(this.bytes);
			Assert.assertTrue(baos.size() > 0);
		}
	}

	@Test
	public void fastByteArrayFixed() throws Exception {
		for(int i=0; i<iterations; i++) {
			generateRandomBytes();
			FastByteArrayOutputStream baos = new FastByteArrayOutputStream(1024);
			baos.resize(this.bytes.length);
			baos.write(this.bytes);
			Assert.assertTrue(baos.size() > 0);
		}
	}

	@Test
	public void fastByteArrayUnknown() throws Exception {
		for(int i=0; i<iterations; i++) {
			generateRandomBytes();
			FastByteArrayOutputStream baos = new FastByteArrayOutputStream(1024);
			baos.write(this.bytes);
			Assert.assertTrue(baos.size() > 0);
		}
	}

}
