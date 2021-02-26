/**
 * Data processor includes tools of processing varying data.
 */
package functions;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import tools.Calculator;
import tools.Randomizer;

/**
 * @author Liang Wang
 *
 */
public class DataProcessor {
	/**
	 * Get the scale of a data matrix to be formatted.<br>
	 * Rules of hSK: <br>
	 * 1. The length of hSK must be equal or grater than 64.<br>
	 * 2. The first 5 characters of hSK will be used to add up as a basis of the scale.<br>
	 * 3. The characters in hSK must be within the range of 0-15 (in Hex).<br>
	 * @param dlength the length of a data matrix (in byte). This parameter must not be less than 75.
	 * @param hSK the hash of private key.
	 * @return the scale with form of m x n.
	 * @deprecated
	 */
	public static int[] getDataMatrixScale(int dlength, String hSK) {
		int[] scale = new int[2];
		try {
			//Validation
			if(dlength < 76 || hSK.length() < 64) {
				throw new Exception("Wrong parameters!");
			}
			//Get the sum of the first 5 characters.
			int basis = Calculator.sumSubString(hSK, 0, 5);//up to 75+1
			//Find a scale.
			while(dlength % basis != 0) {
				basis ++;
			}
			scale[0] = dlength / basis; //Row count
			scale[1] = basis; //Column count
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return scale;
	}
	/**
	 * Reads a data matrix from a hard disk.
	 */
	public static byte[] readData(String name) {
		FileInputStream fis = null;
		DataInputStream dis = null;
		try {
			if(name == null || "".equals(name)) { // validation
				throw new Exception("The file name must be specified!");
			}
			
			File f = new File(name);
			if(!f.exists()) {
				return null;
			}
			fis = new FileInputStream(f);
			dis = new DataInputStream(fis);
			byte[] d = new byte[(int)f.length()];
			for(int i = 0; i < d.length; i++) {
				d[i] = (byte)dis.read();
			}
			return d;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				dis.close();
				fis.close();
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * Writes the given data into a file.
	 * @param d the given data.
	 * @param name the file's name.
	 * @return Returns true if succeeds, or otherwise false.
	 */
	public static boolean writeData(byte[] d, String name) {
		FileOutputStream fos = null;
		DataOutputStream dos = null;
		try {
			if(name == null || "".equals(name)) { // validation
				throw new Exception("The file name must be specified!");
			}
			if(d == null || d.length < 1) {
				throw new Exception("Low quality of d!");
			}
			
			File f = new File(name);
			if(!f.exists()) {
				f.createNewFile();
			}
			fos = new FileOutputStream(f);
			dos = new DataOutputStream(fos);
			for(int i = 0; i < d.length; i ++) {
				dos.write(d[i]);
			}
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try {
				dos.close();
				fos.close();
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}
	/**
	 * Breaks a data array with specific manner of attack.
	 * @param d the data array to be broken.
	 * @param attack the name of the attack model.
	 * @return Returns the broken data.
	 */
	public static byte[] dataBreaking(byte[] d, String attack) {
		if(d == null) return null;
		byte[] brokenDF = null;
		switch (attack) {
		case "Deletion":
			brokenDF = new byte[2 * d.length / 3 + 1];
			for(int i = 0; 3 * i < d.length - 1; i ++) {
				brokenDF[2 * i] = d[3 * i];
				brokenDF[2 * i + 1] = d[3 * i + 1]; 
			}
			break;
		case "Swap":
			brokenDF = new byte[d.length];
			for(int i = 0; i < brokenDF.length; i ++) {
				brokenDF[i] = d[i + (int)Math.pow(-1, i)];
			}
			break;
		case "Padding":
			byte[] ext = new byte[d.length];
			for(int i = 0; i < ext.length; i ++) {
				ext[i] = (byte)Randomizer.randomInt(0, 256);
			}
			brokenDF = Calculator.concatArrays(d, ext);
			break;
		case "Negation":
			brokenDF = new byte[d.length];
			for(int i = 0; i < brokenDF.length; i ++) {
				brokenDF[i] = (byte)(((int)d[i] * (int)Math.pow(-1, i)));
			}
			break;
		case "Reversion":
			brokenDF = new byte[d.length];
			for(int i = 0; i < d.length; i ++) {
				brokenDF[i] =d[d.length - 1 - i];
			}
			break;
		default:
			return d;
		}
		return brokenDF;
	}
}
