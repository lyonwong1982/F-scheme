/**
 * SHA256 digest generator.
 */
package tools;

import java.security.MessageDigest;

/**
 * @author Liang Wang
 *
 */
public class SHA256 {
	/**
	 * Return a digest of a string.
	 * @param str the original.
	 * @return cipher of the original.
	 */
	public static String getSHA256FromString(String str) {
		MessageDigest messageDigest;
		String encodeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(str.getBytes("UTF-8"));
			encodeStr = Converter.byte2Hex(messageDigest.digest());
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return encodeStr;
	}
	/**
	 * Return a digest of a byte array.
	 * @param b the byte array.
	 * @return cipher of the original.
	 */
	public static String getSHA256FromBytes(byte[] b) {
		MessageDigest messageDigest;
		String encodeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(b);
			encodeStr = Converter.byte2Hex(messageDigest.digest());
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return encodeStr;
	}
	/**
	 * Get a digest in bytes from a byte array.
	 * @param b the byte array.
	 * @return The digest in bytes.
	 */
	public static byte[] getSHA256(byte[] b) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(b);
			return messageDigest.digest();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
