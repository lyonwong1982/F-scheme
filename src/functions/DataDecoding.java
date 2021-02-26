/**
 * Functions related to data decoding.
 */
package functions;

import java.util.Arrays;
import java.util.Queue;

import entities.ByteQueue;
import entities.Code;
import entities.SymbolMap;
import tools.Calculator;

/**
 * @author Liang Wang
 *
 */
public class DataDecoding {
	/**
	 * Decode a data.
	 * @param dF the data to be decoded
	 * @param map the symbol map.
	 * @return Returns byte array which contains the decoded data.
	 */
	public static byte[] decodeData(byte[] dF, SymbolMap map) {
		//validation
		try {
			if(dF.length < 1) {
				throw new Exception("Too short dF!");
			}
			if(map == null) {
				throw new Exception("mapSecret should not be null!");
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		
		//Solve for rho and theta from mapSecret
		int rho = map.pLength;
		int theta = map.sLength;
		
		//Right zero padding of d.
		int x = dF.length % rho;
		if(x > 0) {
			byte[] filler = new byte[rho - x];
			Arrays.fill(filler, (byte)0);
			dF = Calculator.concatArrays(dF, filler);
		}
		
		//Divide d by theta all in bytes.
		byte[][] P = new byte[dF.length/rho][rho];
		for(int i = 0; i < dF.length/rho; i ++) {
			for(int j = i * rho; j < (i + 1) * rho; j ++) {
				P[i][j - i * rho] = dF[j];
			}
		}
		
		ByteQueue D = ByteQueue.getByteQueue(theta);
		for(byte[] p : P) {
			D.offer(map.getsByp(new Code(p)).getData());
		}
		
		return D.getAllData();
	}
}
