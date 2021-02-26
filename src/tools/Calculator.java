/**
 * Tools for calulation.
 */
package tools;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Liang Wang
 *
 */
public class Calculator {
	/**
	 * This function sum up the symbols of a sub string of str in Hex, and return the decimal sum.
	 * @param str the original string.
	 * @param start the start position of the sub string.
	 * @param end the end position of the sub string (pointing at end - 1).
	 * @return the decimal sum.
	 */
	public static int sumSubString(String str, int start, int end) {
		int sum = 0;
		try {
			//Validation
			if(start < 0 || start >= end || str.length() < end) {
				throw new Exception("Wrong parameters!");
			}
			//Get a substring from str.
			String subStr = "";
			subStr = str.substring(start, end);
			char[] cs = new char[subStr.length()];
			subStr.getChars(0, subStr.length(), cs, 0);
			for(int i = 0; i < cs.length; i ++) {
				sum += Integer.parseInt(String.valueOf(cs[i]), 16);
			}
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return sum + 1;//in case getting zero.
	}
	/**
	 * Get a decimal number from a sub string of a given string (in Hex).
	 * @param str the original string.
	 * @param start the start position of the sub string.
	 * @param end the end position of the sub string (pointing at end - 1).
	 * @return the decimal number representing the substring.
	 */
	public static int decimalSubString(String str, int start, int end) {
		int decimal = 0;
		try {
			//Validation.
			if(start < 0 || start >= end || str.length() < end) {
				throw new Exception("Wrong parameters!");
			}
			//Get a substring from str.
			String subStr = "";
			subStr = str.substring(start, end);
			decimal = Integer.parseInt(subStr, 16);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return decimal + 1000;//in case getting zero.
	}
	/**
	 * c900 calculation follows such a rule that c900=(c1+c2+c3+c4)*c5
	 * @param str original string.
	 * @param start start position in str.
	 * @param end end position in str.
	 * @return Returns a c900.
	 */
	public static int c900SubString(String str, int start, int end) {
		int c900 = 0;
		try {
			//Validation
			if(start < 0 || start >= end || str.length() < end) {
				throw new Exception("Wrong parameters!");
			}
			if((end - start) != 5) {
				throw new Exception("In c900, (end - start) must be 5!");
			}
			
			//Calculate c900
			int c1 = Integer.parseInt(str.substring(start, start + 1), 16);
			int c2 = Integer.parseInt(str.substring(start + 1, start + 2), 16);
			int c3 = Integer.parseInt(str.substring(start + 2, start + 3), 16);
			int c4 = Integer.parseInt(str.substring(start + 3, start + 4), 16);
			int c5 = Integer.parseInt(str.substring(start + 4, start + 5), 16);
			c900 = (c1 + c2 + c3 + c4) * c5;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return c900 + 1;
	}
	/**
	 * This method fills container with content from start to end-1. If end exceeds the range of the container, then it will be adjusted to the length of the container.
	 * @param container the container byte array.
	 * @param content the content byte array.
	 * @param start the point of container start to fill.
	 * @param end the point of container end to fill.
	 * @return True if succeed, or otherwise false.
	 */
	public static boolean fillArray(byte[] container, byte[] content, int start, int end) {
		try {
			//Validation.
			if(start >= container.length) {
				throw new Exception("Start point should be within the container!");
			}
			if(start >= end) {
				throw new Exception("Start point should be less than the end point!");
			}
			if((end - start) > content.length) {
				throw new Exception("The length of content should not be less than (end - start)!");
			}
			
			//Adjustment
			if(end >= container.length) {
				end = container.length;
			}
			
			//Filling
			for(int i = start; i < end; i ++) {
				container[i] = content[i - start];
			}
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	/**
	 * Returns a new string array with no same elements.
	 * @param strs the original string array.
	 * @return The new string array with no same elements.
	 * @deprecated
	 */
	public static ArrayList<String> distinctStringArray(ArrayList<String> strs){
		ArrayList<String> distinctStrs = new ArrayList<String>();
		for(int i = 0; i < strs.size(); i ++) {
			if(!distinctStrs.contains(strs.get(i))) {
				distinctStrs.add(strs.get(i));
			}
		}
		return distinctStrs;
	}
	/**
	 * Returns a new 2d byte array with no same elements.
	 * @param b the byte array to be delt with.
	 * @return Returns a new 2d byte array.
	 */
	public static byte[][] distinctByteArray(byte[][] b) {
		if(b == null) {
			return null;
		}
		byte[][] tb = new byte[b.length][b[0].length];
		int j = 0;
		for(int i = 0; i < b.length; i ++) {
			if(!Calculator.containsArray(tb, b[i])) {
				tb[j] = b[i];
				j ++;
			}
		}
		byte[][] nb = new byte[j][b[0].length];
		for(int i = 0; i < j; i ++) {
			nb[i] = tb[i];
		}
		return nb;
	}
	/**
	 * Checks a set to find out whether the given element is contained.
	 * @param set the set is a 2d byte array where the first dimension present a byte array.
	 * @param element the byte array to be checked.
	 * @return Returns true if 'set' contains 'element'.
	 */
	public static boolean containsArray(byte[][] set, byte[] element) {
		try {
			//Validation.
			if(set == null || element == null) {
				throw new Exception("Wrong parameters!");
			}
			for(int i = 0; i < set.length; i ++) {
				if(Arrays.equals(set[i], element)) {
					return true;
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	/**
	 * Returns a nearest integer to core from the divisors of base.
	 * @param base an integer larger than 0.
	 * @param core an integer larger than 0.
	 * @return The nearest integer. Returns 0, if there is something wrong.
	 */
	public static int findNearestInt(int base, int core) {
		try {
			if(base < 1 || core < 1) { // Validation
				throw new Exception("The base and core must be greater than 0!");
			}
			
			//Searching
			ArrayList<Integer> divisors = Numbera.getDivisors(base);
			int distance = Integer.MAX_VALUE;
			for(int i = 0; i < divisors.size(); i ++) {
				int n = Math.abs(divisors.get(i) - core);
				if(n < distance) {
					distance = n;
				}
				else {
					return divisors.get(i - 1);
				}
			}
			return divisors.get(divisors.size() - 1);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}
	/**
	 * Splices byte arrays a and byte b.
	 * @param a the first byte array
	 * @param b the second byte array
	 * @return Returns the concat of a and b.
	 */
	public static byte[] concatArrays(byte[] a, byte[] b) {
		if(a == null) {
			return b;
		}
		if(b == null) {
			return a;
		}
		byte[] c = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}
	/**
	 * Returns a new byte array that starts from start and ends at (end - 1) of a given byte array.
	 * @param b the given byte array.
	 * @param start start point
	 * @param end end point + 1
	 */
	public static byte[] subArray(byte[] b, int start, int end) {
		try {
			if(b == null) {
				throw new Exception("The data should not be null!");
			}
			if(end > b.length) {
				throw new Exception("End should not be more than b.length!");
			}
			if(start >= end) {
				throw new Exception("Start should be less than end!");
			}
			if(start < 0) {
				throw new Exception("Start should not be less than 0!");
			}
			
			byte[] nb = new byte[end - start];
			for(int i = start; i < end; i ++) {
				nb[i - start] = b[i];
			}
			return nb;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
