/**
 * In this function, parameters are made and processed.
 */
package functions;

import java.util.ArrayList;

import tools.Calculator;

/**
 * @author Liang Wang
 *
 */
public class ParameterMaker {
	/**
	 * Get the symbol length (theta) according to given parameters.
	 * @param hSK hash of private key.
	 * @param lF length of fingerprint.
	 * @param ld length of data matrix.
	 * @param tau upper limit of the size of the public part of the symbol map of the data matrix (usually 1048576).
	 * @param lambda symbol length factor (usually 1024).
	 * @return The symbol length (theta).
	 */
	public static int getSymbolLength(String hSK, int lF, int ld, int tau, int lambda) {
		int theta = 0;
		try {
			//Validation.
			if(hSK.length() < 64) {
				throw new Exception("Too short hSK!");
			}
			if(lF < 32) {
				throw new Exception("Too short fingerprint!");
			}
			if(ld < 1) {
				throw new Exception("Wrong size of data matrix!");
			}
			if(tau < (lF + ((Math.log(ld)/Math.log(2)) + 1) / 8d)) {
				throw new Exception("Too small tau!");
			}
			
			//Upper limit
			int upperLimit = (int)Math.floor((2d * ld) / (lambda * lF)); //The lambda implies that the encoded data should be greater than lambda|F|.
			int lowerLimit = (int)Math.ceil(ld * (lF + (((Math.log(ld)/Math.log(2)) + 1) / 8d)) / tau);
			
			//Get the decimal number of the first 5 characters.
			int basis = Calculator.decimalSubString(hSK, 0, 5); //up to 1048575+1000
			
			//Customize the symbol length
			theta = (basis % (upperLimit - lowerLimit + 1)) + lowerLimit;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return theta;
	}
	/**
	 * Get fingerprint redundancy, namely phi.
	 * @param hSK the hash of private key. The length of hSH should not less than 64.
	 * @param nSymbols the number of symbols of the original data matrix (not distinct).
	 * @param lFingerprint the length of the fingerprint (in bytes).
	 * @return The number of redundant fingerprints.
	 * @deprecated
	 */
	public static int getFingerprintRedundancy(String hSK, int nSymbols, int lFingerprint) {
		int phi = 0;
		try {
			//validation.
			if(hSK.length() < 64 || nSymbols < 1 || lFingerprint < 32) {
				throw new Exception("Wrong parameters!");
			}
			//Get the decimal number of the third 5 characters.
			int basis = Calculator.decimalSubString(hSK, 10, 15); //up to 1048575+1000
			//Get all possible parameters of fingerprint redundancy, namely phi.
			ArrayList<Integer> phis = new ArrayList<Integer>();
			for(int i = 1; i <= nSymbols; i ++) {//avoid over perfect phi.
				if((lFingerprint * i) % nSymbols == 0) {
					phis.add(i);
				}
			}
			if(basis >= phis.size()) {
				basis = basis % phis.size();
			}
			phi = phis.get(basis);
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return phi;
	}
	/**
	 * Get hidden code length, namely eta, with given parameters.
	 * @param hSK hash of private key.
	 * @param nSymbols the number of symbols in the original data matrix (not distinct).
	 * @param lFingerprint the length of the fingerprint (in bytes).
	 * @param gamma redundancy factor (usually set to 0.3)
	 * @return The hidden code length, namely eta.
	 */
	public static int getHiddenCodeLength(String hSK, int nSymbols, int lFingerprint, double gamma) {
		int eta = 0;
		try {
			//Validation.
			if(hSK.length() < 64) {
				throw new Exception("hSK must not be shorter than 64!");
			}
			if(lFingerprint < 32) {
				throw new Exception("lFingerprint must not be shorter than 32 bytes!");
			}
			if(nSymbols < 1) {
				throw new Exception("nSymbols must not be less than 1!");
			}
			if(gamma <= 0 || gamma > 1) {
				throw new Exception("gamme must be in the range of (0,1]!");
			}
			
			//Calculate the lower limit of eta.
			int ll = (int)Math.ceil(lFingerprint * Math.ceil(gamma * nSymbols) / nSymbols);
			
			//Get the range of eta.
			int range = lFingerprint - ll + 1;
			
			//Get the decimal number of the second 5 characters.
			int basis = Calculator.decimalSubString(hSK, 5, 10); //up to 1048575+1000
			
			//Get eta
			int r = basis % range;
			eta = ll + r;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return eta;
	}
	/**
	 * Get plain code length, namely rho.
	 * @param hSK the hash of private key (in Hex string).
	 * @param nSymbols the number of symbols in a data matrix (not distinct).
	 * @param lSymbol the length of a symbol, namely theta.
	 * @return The plain code length, namely rho.
	 * @deprecated
	 */
	public static int getPlainCodeLength(String hSK, int nSymbols, int lSymbol) {
		//Validation.
		try {
			if(hSK.length() < 64 || nSymbols < 1 || lSymbol <1) {
				throw new Exception("Wrong parameters!");
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		//Get the sum of the 4th 5 characters.
		int basis = Calculator.sumSubString(hSK, 15, 20);// up to 75+1
		//Determine whether to use the compressed encoding.
		if(basis % 2 == 0) {// Use compressed encoding.
			for(int i = 1; i < lSymbol; i ++) {
				if(Math.pow(2, 8 * i) >= (nSymbols * 32d)) {
					//In case one of the symbols has enough codes to cover a full fingerprint, i.e. 32 bytes.
					return i;
				}
			}
		}
		//If don't use compressed encoding or fail to find a proper rho, use isometric encoding.
		if(Math.pow(2, 8 * lSymbol) >= (nSymbols * 33d)) {
			// nSymbols symbols have to be subtracted.
			return lSymbol;
		}
		//If cannot use isometric encoding, rise the coding space.
		int i = lSymbol + 1;
		while(Math.pow(2, 8 * i) < (nSymbols * 32d)) {
			i ++;
		}
		return i;
	}
	/**
	 * Get coding redundancy, namely chi.
	 * @param hSK the Hex string of private key.
	 * @param lMapPublic the number of items in the public part of a symbol map.
	 * @param rho length of plain code.
	 * @return
	 */
	public static int getCodingRedundancy(String hSK, int lMapPublic, int rho) {
		int chi = 0;
		try {
			//validation
			if(hSK.length() < 64) {
				throw new Exception("hSK must not be shorter than 64!");
			}
			if(rho < 1) {
				throw new Exception("rho must not be less than 1 byte!");
			}
			
			//Get the 900 calculation of the third 5 characters.
			int c900 = Calculator.c900SubString(hSK, 10, 15); // up to 900+1
			
			//Get the decimal number of the fourth 5 characters.
			int basis = Calculator.decimalSubString(hSK, 15, 20); //up to 1048575+1000
			
			//Calculate coding redundancy.
			int leftCodingSpace = (int)Math.floor(Math.pow(2, 8 * rho) - lMapPublic);
			if(leftCodingSpace < 900) {
				chi = (basis % leftCodingSpace) + 1;
			}
			else {
				chi = c900;
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return chi;
	}
}
