/**
 * Code
 */
package entities;

import java.util.Arrays;

/**
 * @author Liang Wang
 *
 */
public class Code {
	private byte[] data = null; // original
	/**
	 * Initiate an empty code with a given length
	 * @param length the given length
	 */
	public Code(int length) {
		data = new byte[length];
	}
	/**
	 * Initiate a code object using a given byte array.
	 * @param data the given byte array.
	 */
	public Code(byte[] data) {
		this.data = Arrays.copyOf(data, data.length);
	}
	/**
	 * Initiate an empty code. The attribute "data" is not initiated.
	 */
	public Code() {}
	/**
	 * Sets new code.<br>
	 * The parameter "data" will be clone for a new one, so as to keep the distinction of the keys of this object of Code.
	 */
	public void setData(byte[] data) {
		this.data = Arrays.copyOf(data, data.length);
	}
	/**
	 * Gets the byte array of this code.
	 */
	public byte[] getData() {
		return data;
	}
	/**
	 * Only when the code is not empty, true will be returned.
	 */
	public boolean isValid() {
		if(data == null) {
			return false;
		}
		if(data.length == 0) {
			return false;
		}
		return true;
	}
	/**
	 * Returns the length of this code.
	 */
	public int length() {
		if(data == null) {
			return 0;
		}
		return data.length;
	}
	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		return Arrays.equals(data, ((Code)obj).data);
	}
	@Override
	public int hashCode() {
		int h = 17;
		h = 31 * h + byteArrayHashCode(data);
		return h;
	}
	
	private int byteArrayHashCode(byte[] b) {
		int h = 17;
		for(int i = 0; i < b.length; i ++) {
			h = 31 * h + (int)b[i];
		}
		return h;
	}
}
