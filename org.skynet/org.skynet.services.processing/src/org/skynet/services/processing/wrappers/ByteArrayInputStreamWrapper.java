/**
 * 
 */
package org.skynet.services.processing.wrappers;

import java.io.ByteArrayInputStream;

/**
 * @author Gabriel
 *
 */
public class ByteArrayInputStreamWrapper extends ByteArrayInputStream {

	/**
	 * @param buf
	 */
	public ByteArrayInputStreamWrapper(byte[] buf) {
		super(buf);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param buf
	 * @param offset
	 * @param length
	 */
	public ByteArrayInputStreamWrapper(byte[] buf, int offset, int length) {
		super(buf, offset, length);
		// TODO Auto-generated constructor stub
	}

	/**
	 * get the buffer without copying it.
	 * @return the buffer.
	 */
	public synchronized byte[] getBuffer() {
		return this.buf;
	}
}
