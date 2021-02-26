/**
 * Functions related to symbol mapping, i.e., phi 1.
 */
package functions;


import java.util.Arrays;

import entities.ByteQueue;
import entities.Code;
import entities.SymbolMap;
import tools.Calculator;
import tools.Configurator;
import tools.Converter;
import tools.Randomizer;

/**
 * @author Liang Wang
 *
 */
public class SymbolMapping {
	/**
	 * Create a symbol map of a data matrix with a given private key and a fingerprint.
	 * @param d data matrix.
	 * @param hSK hash of private key (in Hex string).
	 * @param F fingerprint (in bytes).
	 * @param gamma redundancy factor (usually set to 0.3).
	 * @param tau upper limit size of the public part of a map (usually set to 1048576).
	 * @param presTheta a preset theta (a nearest theta will be set). Leaves it <1, if a hSK-specified one is needed.
	 * @param presEta a preset eta. Leaves it <1, if a hSK-specified one is needed.<br> Note: presEta <= |F| must hold.
	 * @param lambda symbol length factor (usually set to 1024).
	 * @return A symbol map in connection with d.
	 */
	public static SymbolMap createSymbolMap(byte[] d, String hSK, byte[] F, double gamma, int tau, int presTheta, int presEta, int lambda) {
		//Validation
		try {
			if(d.length < 1) {
				throw new Exception("Too short of d!");
			}
			if(hSK.length() < 64) {
				throw new Exception("Too short of hSK!");
			}
			if(F.length < 32) {
				throw new Exception("Too short of F!");
			}
			if(gamma <= 0 || gamma > 1) {
				throw new Exception("The gamma must be within the range of (0,1]!");
			}
			if(tau < (F.length + ((Math.log(d.length)/Math.log(2)) + 1) / 8d)) {
				throw new Exception("Too small of tau!");
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
//		/****/System.out.println("    |d|-" + d.length);
//		/****/long ttt = System.currentTimeMillis();
		//Get symbol length.
		int theta = 0;
		if(presTheta > 0) {
			theta = Calculator.findNearestInt(d.length, presTheta);
		}
		else {
			theta = ParameterMaker.getSymbolLength(hSK, F.length, d.length, tau, lambda);
		}
//		/****/System.out.println("p1-" + (System.currentTimeMillis() - ttt));
//		/****/System.out.println("    theta-" + theta);
		Configurator.theta = theta;
		
//		/****/ttt = System.currentTimeMillis();
		//Right zero padding of d.
		int x = d.length % theta;
		if(x > 0) {
			byte[] filler = new byte[theta - x];
			Arrays.fill(filler, (byte)0);
			d = Calculator.concatArrays(d, filler);
		}
		
		//Divide d by theta all in bytes.
		byte[][] S = new byte[d.length/theta][theta];
		for(int i = 0; i < d.length/theta; i ++) {
			for(int j = i * theta; j < (i + 1) * theta; j ++) {
				S[i][j - i * theta] = d[j];
			}
		}
//		//Divide d by theta all in Hex string.
//		ArrayList<String> S = new ArrayList<String>();
//		for(int i = 0; i < d.length/theta; i++) {
//			byte[] b = new byte[theta];
//			for(int j = i * theta; j < (i + 1) * theta; j ++) {
//				b[j - i * theta] = d[j];
//			}
//			String s = Converter.byte2Hex(b);
//			S.add(s);
//		}
//		/****/System.out.println("p2-" + (System.currentTimeMillis() - ttt));
//		/****/System.out.println("    |S|-" + S.length);
		
		//Calculates plain code length: rho
		int rho = (int)Math.ceil(((Math.log(S.length) / Math.log(2)) + 1) / 8d);
//		/****/System.out.println("    rho-" + rho);
		Configurator.rho = rho;

		//Get hidden code length: eta
		int eta = 0;
		if(presEta > 0 && presEta < F.length + 1) {
			eta = presEta;
		}
		else {
			eta = ParameterMaker.getHiddenCodeLength(hSK, S.length, F.length, gamma);
		}
//		/****/System.out.println("    eta-" + eta);
		Configurator.eta = eta;

		//Calculate fingerprint redundancy: phi
		int phi = (int)Math.ceil(eta * S.length * 1d / F.length);
//		/****/System.out.println("    phi-" + phi);
		Configurator.phi = phi;

//		/****/long ttt = System.currentTimeMillis();
		//Get redundant fingerprint and cut the tail.
		byte[] Fphi = new byte[eta * S.length];
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
//		/****/System.out.println("  p3-" + (System.currentTimeMillis() - ttt));
		
//		/****/ttt = System.currentTimeMillis();
		//Get divided redundant fingerprint.
//		Queue<String> Feta = new LinkedList<String>();
//		for(int i = 0; i < Fphi.length/eta; i++) {
//			byte[] b = new byte[eta];
//			for(int j = i * eta; j < (i + 1) * eta; j ++) {
//				b[j - i * eta] = Fphi[j];
//			}
//			String s = Converter.byte2Hex(b);
//			Feta.offer(s);
//		}
		//Get divided redundant fingerprint all in bytes.
//		ByteQueue Feta = ByteQueue.getByteQueue(eta);
//		for(int i = 0; i < Fphi.length/eta; i ++) {
//			byte[] b = new byte[eta];
//			for(int j = i * eta; j < (i + 1) * eta; j ++) {
//				b[j - i * eta] = Fphi[j];
//			}
//			Feta.offer(b);
//		}
		//Get divided redundant fingerprint all in bytes.
		ByteQueue Feta = ByteQueue.getByteQueue(Fphi, S.length, eta);
//		/****/System.out.println("p4-" + (System.currentTimeMillis() - ttt));
		
//		/****/long ttt = System.currentTimeMillis();
		//Create a coding space.
		ByteQueue C = ByteQueue.getByteQueue(rho); //rho cannot be greater than 3!!!
		for(int i = 0; i < (int)Math.pow(2, 8 * rho); i ++) {
			String hex = Integer.toHexString(i);
			int comp = 2 * rho - hex.length();
			for(int j = 0; j < comp; j ++) {
				hex = "0" + hex;
			}
			byte[] b = Converter.Hex2byte(hex);
			C.offer(b);
		}
//		/****/System.out.println("p5-" + (System.currentTimeMillis() - ttt));
		
//		/****/ttt = System.currentTimeMillis();
		//Generate a basic symbol map
		SymbolMap map = new SymbolMap();
		for(int i = 0; i < S.length; i ++) {
			int rand =Randomizer.randomInt(0, C.size());
			byte[] p_rho = C.remove(rand);
			map.addItem(new Code(S[i]), new Code(p_rho), new Code(Feta.element(i)), true);
		}
//		/****/System.out.println("  p6-" + (System.currentTimeMillis() - ttt));
		
//		/****/ttt = System.currentTimeMillis();
		//Obfuscation.
		int chi = ParameterMaker.getCodingRedundancy(hSK, map.getMapP().size(), rho);
//		/****/System.out.println("    chi-" + chi);
		Configurator.chi = chi;
		for(int i = 0; i < chi; i ++) {
			byte[] s = S[Randomizer.randomInt(0, S.length)];
			byte[] h = Feta.element(Randomizer.randomInt(0, Feta.size()));
			int rand =Randomizer.randomInt(0, C.size());
			byte[] p_rho = C.remove(rand);
			map.addItem(new Code(s), new Code(p_rho), new Code(h), false);
		}
//		/****/System.out.println("p7-" + (System.currentTimeMillis() - ttt));
		
		return map;
	}
}
