/**
 * Functions related to data encoding.
 */
package functions;

import java.util.ArrayList;
import java.util.Arrays;

import entities.ByteQueue;
import entities.Code;
import entities.SymbolMap;
import tools.Calculator;
import tools.Randomizer;

/**
 * @author Liang Wang
 *
 */
public class DataEncoding {
	/**
	 * 
	 * @param d
	 * @param map
	 * @param F
	 * @return
	 */
	public static byte[] encodeData(byte[] d, SymbolMap map, byte[] F) {
		//validation
		try {
			if(d.length < 1) {
				throw new Exception("Too short of d!");
			}
			if(map == null) {
				throw new Exception("Map cannot be null!");
			}
			if(F.length < 32) {
				throw new Exception("Too short of F!");
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		//Solve for theta and eta from map
		int theta = map.sLength;
		int eta = map.hLength;
		
		//Right zero padding of d.
		int x = d.length % theta;
		if(x > 0) {
			byte[] filler = new byte[theta - x];
			Arrays.fill(filler, (byte)0);
			d = Calculator.concatArrays(d, filler);
		}
		
		//Divide d by theta.
		byte[][] S = new byte[d.length/theta][theta];
		for(int i = 0; i < d.length/theta; i ++) {
			for(int j = i * theta; j < (i + 1) * theta; j ++) {
				S[i][j - i * theta] = d[j];
			}
		}
		
		//Calculate fingerprint redundancy: phi
		int phi = (int)Math.ceil(eta * S.length * 1d / F.length);
		
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
		
		//Get divided redundant fingerprint all in bytes.
		ByteQueue Feta = ByteQueue.getByteQueue(Fphi, S.length, eta);
		
		//Encoding
		byte[] dF = null;
		for(int i = 0; i < S.length; i ++) {
			ArrayList<Code> P = map.getPBysh(new Code(S[i]), new Code(Feta.poll()));
			int r = Randomizer.randomInt(0, P.size());
			byte[] p = P.get(r).getData();
			dF = Calculator.concatArrays(dF, p);
		}
		
		return dF;
	}
}
