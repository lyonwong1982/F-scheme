/**
 * Random number generator.
 */
package tools;

import java.util.Map.Entry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import entities.KeyPair;
import entities.SymbolMap;

/**
 * @author Liang Wang
 *
 */
public class Randomizer {
	/**
	 * Get a random integer that begins with start and end with (end - 1).
	 * @param start The beginning of the range.
	 * @param end The ending of the range.
	 * @return The generated random integer.
	 */
	public static int randomInt(int start, int end) {
		try {
			if(start >= end) {
				throw new Exception("Wrong argument(s)!");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Random r = new Random();
		int rand = r.nextInt(end - start);
		return rand + start;
	}
	/**
	 * Get a random Hex string which is of the length codelength.
	 * @param codelength the code length is measured by bytes.
	 * @return The Hex string whose length is 2 * codelength.
	 */
	public static String randomHex(int codelength) {
		try {
			//Validation.
			if(codelength <= 0) {
				throw new Exception("Codelength must be greater than 0!");
			}
			
			//Generation.
			String hex = "";
			for(int i = 0; i < codelength; i ++) {
				String s = Integer.toHexString(Randomizer.randomInt(0, 256));
				if(s.length() == 1) {
					s = "0" + s;
				}
				hex += s;
			}
			return hex;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	/**
	 * Get a random byte array which is of the given code length.
	 * @param codelength the given code length.
	 * @return The byte array.
	 */
	public static byte[] randomBytes(int codelength) {
		try {
			//validation
			if(codelength <= 0) {
				throw new Exception("codelength must be greater than 0!");
			}
			
			//Generation
			byte[] b = new byte[codelength];
			for(int i = 0; i < codelength; i ++) {
				b[i] = (byte)Randomizer.randomInt(0, 256);
			}
			return b;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	/**
	 * Randomly returns an item from a hash map
	 * @param exc the key to be excluded.
	 */
	public static KeyPair<String, String> randomEntry(HashMap<String, String> hm, String exc){
		if(hm == null) {
			return null;
		}
		if(hm.size() == 0) {
			return null;
		}
		while(true) {
			int index = randomInt(0, hm.size());
			Iterator<Entry<String, String>> itr = hm.entrySet().iterator();
			for(int i = 0; i < index; i ++) {
				itr.next();
			}
			Entry<String, String> entry = itr.next();
			if(entry.getKey().equals(exc)) {
				if(hm.size() == 1) {
					return null;
				}
				else {
					continue;
				}
			}
			KeyPair<String, String> kp = new KeyPair<String, String>(entry.getKey(), entry.getValue());
			return kp;
		}
	}
}
