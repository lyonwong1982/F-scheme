/**
 * Format converter.
 */
package tools;


/**
 * @author Liang Wang
 *
 */
public class Converter {
	/**
	 * Convert byte to Hex.
	 * @param bytes the array of bytes.
	 * @return string in Hex form.
	 */
	public static String byte2Hex(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < bytes.length; i++) {
			String temp = Integer.toHexString(bytes[i] & 0xFF);
			if (temp.length() == 1) {
				stringBuffer.append("0");
			}
			stringBuffer.append(temp);
		}
		return stringBuffer.toString();
	}
	/**
	 * Convert Hex to byte.
	 * @param str the Hex string to be converted.
	 * @return The byte array.
	 */
	public static byte[] Hex2byte(String str) {
		try {
			//Validation
			if(str == null) {
				throw new Exception("Input parameter should not be null!");
			}
			if(str.length() % 2 != 0) {
				System.out.println(str);
				throw new Exception("Input string should be of even length!");
			}
			//Conversion
			byte[] b= new byte[str.length() / 2];
			for(int i = 0, j = 0; i < str.length(); i = i + 2, j ++) {
				b[j] = (byte)Integer.parseInt(str.substring(i, i + 2), 16);
			}
			return b;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
