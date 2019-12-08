/**
 * 
 */
package org.skynet.services.processing.wrappers;

import java.io.ByteArrayOutputStream;

/**
 * @author Gabriel
 *
 */
public class ByteArrayOutputStreamWrapper extends ByteArrayOutputStream {

	/**
	 * 
	 */
	public ByteArrayOutputStreamWrapper() {
		super();
	}
	
	/**
	 * @param size
	 */
	public ByteArrayOutputStreamWrapper(int size) {
		super(size);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * get the buffer without copying it.
	 * @return the buffer
	 */
	public synchronized byte[] getBuffer() {
		return this.buf;
	}
}
