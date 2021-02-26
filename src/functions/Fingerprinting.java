/**
 * Functions of fingerprinting
 */
package functions;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import entities.ByteQueue;
import entities.Code;
import entities.SymbolMap;
import tools.Calculator;
import tools.Configurator;
import tools.Randomizer;

/**
 * @author Liang Wang
 *
 */
public class Fingerprinting {
	/**
	 * Fingerprinting F from dF
	 * @param dF a data matrix may contain fingerprints.
	 * @param map the symbol map.
	 * @param F the fingerprint
	 * @param epsilon the lower limit ratio of tested fingerprint redundancy (usually set to 0.3).
	 * @param sigma the upper limit mean of byte usage differentialsz(usually set to 90).
	 * @param tau upper limit size of the public part of a map (usually set to 1048576).
	 * @param r the lower limit of correlation coefficient (usually set to 0.6).
	 * @return Returns true if determining that dF does contain F, otherwise returns false.
	 */
	public static boolean fingerprinting(byte[] dF, SymbolMap map, byte[] F, double epsilon, int sigma, int tau, double r) {
		//validation
		try {
			if(dF.length < 1) {
				throw new Exception("Too short of dF!");
			}
			if(map == null) {
				throw new Exception("MapPublic cannot be null!");
			}
			if(F.length < 32) {
				throw new Exception("Too short of F!");
			}
			if(epsilon <= 0 || epsilon > 1) {
				throw new Exception("The epsilon must be in (0,1]!");
			}
			if(sigma < 1) {
				throw new Exception("The sigma must not be less than 1!");
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		boolean phi_test = true;
		boolean sigma_test = true;
		
		//Solve for rho and eta from map
		int rho = map.pLength;
		int eta = map.hLength;
		
		//Verify dF
		if(dF.length > (tau * rho) / (F.length + rho)) {
			epsilon = epsilon * tau * rho / ((F.length + rho) * dF.length);
			sigma = sigma * ((F.length + rho) * dF.length) / (tau * rho);
//			System.out.println("The data you are fingerprinting is fake!\n"
//					+ "Please turn EPSILON down appropriately!\n" //EPSILON: The lower limit ratio of fingerprint redundancy.
//					+ "Please turn SIGMA up appropriately!\n" //SIGMA: The upper limit mean of byte usage differentials.
//					+ "You might be still under an EXTENSION ATTACK!");
		}
		
		//Calculate phi
		int phiFromDF = (int)Math.floor(eta * dF.length * 1d / (rho * F.length));
		int phiFromMap = (int)Math.floor(eta * map.getPs().size() * 1d / F.length);
		int phi = Math.min(phiFromDF, phiFromMap);
//		System.out.println("    phi: " + phi);
//		/****/long ttt = System.currentTimeMillis();
		/*****Forward strict verification*****/
		//1. Get possible plain codes from dF. Assume that dF wasn't broken too much.
		//2. Get the hidden code for each plain code.
		//3. Get a |F|-partition of T.
		//4. Calculate correlation coefficients and phiStar.
		int phiStar = 0;
		for(int i = 0; i < rho; i ++) {
			phiStar = 0;
			int tail = (dF.length - i) % rho;
			byte[] dFnoTail = Calculator.subArray(dF, i, dF.length - tail);
			ByteQueue P = ByteQueue.getByteQueue(dFnoTail, dFnoTail.length / rho, rho);
			ByteQueue T = ByteQueue.getByteQueue(eta);
			if(P != null) {
				while(P.size() > 0) {
					Code h = map.gethByp(new Code(P.poll()));
					if(h != null) {
						T.offer(h.getData());
					}
				}
			}
			byte [] Th = T.getAllData();
			if(Th != null) {
				tail = (Th.length % F.length);
				if(tail > 0) {
					Th = Calculator.subArray(Th, 0, Th.length - tail);
				}
				ByteQueue Tf = ByteQueue.getByteQueue(Th, Th.length / F.length, F.length);
				if(Tf != null) {
					while(Tf.size() > 0) {
						byte[] FStar = Tf.poll();
						double rff = Fingerprinting.correlation(FStar, F, F.length);
						if(Math.abs(rff) >= r) {
							phiStar ++;
						}
					}
				}
			}
			
			Configurator.phiStar = phiStar;
			
			if(phiStar >= epsilon * phi) {
				phi_test = true;
				Configurator.mean = 0;
//				break; //This option should be used when in test.
				return true; //This option should be used when in practice.
			}
			else{
				phi_test = false;
			}
		}
//		/****/System.out.println("  forward-" + (System.currentTimeMillis() - ttt));
		
//		/****/ttt = System.currentTimeMillis();
		/*****Backward probabilistic verification*****/
		//Byte usage difference strategy
		//Byte usage of dF
		HashMap<String, Integer> U = new HashMap<String, Integer>();
		for(int i = 0; i < dF.length; i ++) {
			String key = String.valueOf(dF[i]);
			if(U.containsKey(key)) {
				U.put(key, U.get(key).intValue() + 1);
			}
			else {
				U.put(key, 1);
			}
		}
		//Get a eta-partition of Fphi with the tail trimmed.
		byte[] Fphi = new byte[phi * F.length - ((phi * F.length) % eta)];
		for(int i = 0; i < phi; i ++) {
			Calculator.fillArray(Fphi, F, i * F.length, (i + 1) * F.length);
		}
//		byte[] Fphi = F;
//		for(int i = 1; i < phi; i ++) {
//			Fphi = Calculator.concatArrays(Fphi, F);
//		}
//		int tail = Fphi.length % eta;
//		if(tail > 0) {
//			Fphi = Calculator.subArray(Fphi, 0, Fphi.length - tail);
//		}
		ByteQueue Feta = ByteQueue.getByteQueue(Fphi, Fphi.length / eta, eta);
		//Get possible
		byte[] dFStar = null;
		while(Feta.size() > 0) {
			ArrayList<Code> Ph = map.getPByh(new Code(Feta.poll()));
			int rand = Randomizer.randomInt(0, Ph.size());
			byte[] p = Ph.get(rand).getData();
			dFStar = Calculator.concatArrays(dFStar, p);
		}
		//Byte usage of dFStar
		HashMap<String, Integer> Up = new HashMap<String, Integer>();
		for(int i = 0; i < dFStar.length; i ++) {
			String key = String.valueOf(dFStar[i]);
			if(Up.containsKey(key)) {
				Up.put(key, Up.get(key).intValue() + 1);
			}
			else {
				Up.put(key, 1);
			}
		}
		//Compare the usage between the elements from U and Up, respectively.
		double mean = 0d;
		for(Entry<String, Integer> entry : Up.entrySet()) {
			mean = mean + Math.abs(entry.getValue() - (U.get(entry.getKey()) == null ? 0 : U.get(entry.getKey())));
		}
		mean = mean / Up.size();
		
		Configurator.mean = Math.round(mean * 10000d) / 10000d;
		
		//Mean judgment
		if(mean <= sigma) { // Mean should not be greater than an threshold.
			sigma_test = true;
		}
		else {
			sigma_test = false;
		}
//		/****/System.out.println("  backward-" + (System.currentTimeMillis() - ttt));
		//Final judgment
		return phi_test || sigma_test;
	}
	/**
	 * Calculates a Pearson correlation coefficient for two signals
	 * @param a signal one
	 * @param b signal two
	 * @param n length of a signal
	 * @return [-1,1]. Returns -10 if there are some thing wrong in parameters.
	 */
	private static double correlation(byte[] s1, byte[] s2, int n) {
	    if(s1 == null || s2 == null || n <= 0) {
	    	return -10;
	    }
	    
		double sum_s12 = 0.0;
	    double sum_s1  = 0.0;
	    double sum_s2  = 0.0;
	    double sum_s1s1 = 0.0; //s1^2
	    double sum_s2s2 = 0.0; //s2^2
	    double pxy = 0.0;
	    double temp1 = 0.0;
	    double temp2 = 0.0;
	    
	    for(int i = 0; i < n; i++)
	    {
	    	sum_s12 += (int)s1[i] * (int)s2[i];
	        sum_s1 += (int)s1[i];
	        sum_s2 += (int)s2[i];
	        sum_s1s1 += (int)s1[i] * (int)s1[i]; 
	        sum_s2s2 += (int)s2[i] * (int)s2[i]; 
	    }
	    
	    temp1 = n * sum_s1s1 - sum_s1 * sum_s1;
	    temp2 = n * sum_s2s2 - sum_s2 * sum_s2;
	    
	    if((temp1 > -0.0001d && temp1 < 0.0001d) || (temp2 > -0.0001d && temp2 < 0.0001d) || (temp1 * temp2 <= 0)){
	    	return -10;
        }
	    
	    pxy = (n * sum_s12 - sum_s1 * sum_s2) / Math.sqrt(temp1 * temp2);
	    
	    return pxy;
	}
}
