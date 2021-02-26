/**
 * This tool collects all constants and the methods for collecting them.
 */
package tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Liang Wang
 *
 */
public class Configurator {
	//System parameters
	public static String[] gamma = {"0.3"}; // Redundancy factor. (double)
	public static String[] tau = {"1048576"}; // Upper limit size of the public part of a symbol map. (int)
	public static String[] lambda = {"1024"}; // Symbol length factor. (int)
	public static double r = 0.6d; // Pearson correlation coefficient. (double)
	
	//Fingerprinting parameters
	public static String[] epsilon = {"0.3"}; // Lower limit ratio of fingerprint redundancy. (double)
	public static String[] sigma = {"90"}; // Upper limit of average byte usage differences. (int)
	
	//Application parameters
	public static String[] volume = {"1048576"}; // Data volumes. (int)
	public static String[] fileName = null; // Data file names. (String)
	public static int CdF = 0; // The number of d with fingerprints embedded.
	public static int users = 0; // The number of users involved in tests.
	public static int[] progress = {0, 0}; // Progress bar.
	public static String[] presTheta = {"0"}; // Preset symbol length.
	public static String[] presEta = {"0"}; // Preset hidden code length.
	
	//User-dependent factors
	public static int theta = 0; //Symbol length
	public static int rho = 0; //Plain code length
	public static int eta = 0; //Hidden code length
	public static int phi = 0; //Fingerprint redundancy
	public static int chi = 0; //Coding redundancy
	public static int phiStar = 0;
	public static double mean = 0d;
	/**
	 * Returns a HashMap that contains all pairs of parameters.
	 */
	public static HashMap<String, String> readParameters(String fileName){
		BufferedReader reader = null;
		HashMap<String, String> params = new HashMap<String, String>();
		try {
			reader = new BufferedReader(new FileReader(fileName));
			String str = null;
			while((str = reader.readLine()) != null) {
				params.put(str.substring(0, str.indexOf("=")).trim(),
						str.substring(str.indexOf("=") + 1).trim());
			}
			reader.close();
			return params;
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if(reader != null) {
				try {
					reader.close();
				}
				catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		return params;
	}
	/**
	 * This is a function that automatically load configuration from conf.txt.
	 */
	public static void loadConf(String fileName) {
		HashMap<String, String> paras = Configurator.readParameters(fileName);
		Configurator.gamma = paras.get("gamma").split(",");
		Configurator.tau = paras.get("tau").split(",");
		Configurator.presTheta = paras.get("presTheta").split(",");
		Configurator.presEta = paras.get("presEta").split(",");
		Configurator.lambda = paras.get("lambda").split(",");
		Configurator.epsilon = paras.get("epsilon").split(",");
		Configurator.sigma = paras.get("sigma").split(",");
		Configurator.volume = paras.get("volume").split(",");
		Configurator.fileName = paras.get("fileName").split(",");
		Configurator.CdF = Integer.parseInt(paras.get("CdF"));
		Configurator.users = Integer.parseInt(paras.get("users"));
		Configurator.r = Double.parseDouble(paras.get("r"));
	}
}
