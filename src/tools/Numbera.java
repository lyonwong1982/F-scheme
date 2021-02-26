/**
 * Tools related to operate numbers.
 */
package tools;

import java.util.ArrayList;

/**
 * @author Liang Wang
 *
 */
public class Numbera {
	/**
	 * Get divisors of a given integer.
	 * @param n the given integer.
	 * @return An array of integer contains all divisors of the given integer.
	 */
	public static ArrayList<Integer> getDivisors(int n) {
		ArrayList<Integer> divisors = new ArrayList<Integer>();
		try {
			//Validation.
			if(n < 1) {
				throw new Exception("Wrong parameter!");
			}
			for(int i = 1; i <= n; i ++) {
				if(n % i == 0) {
					divisors.add(i);
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return divisors;
	}
}
