import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

import entities.Code;
import entities.KeyPair;
import entities.SymbolMap;
import functions.DataEncoding;
import functions.DataProcessor;
import functions.Fingerprinting;
import functions.SymbolMapping;
import tools.Configurator;
import tools.Converter;
import tools.ECDSA;
import tools.Printer;
import tools.Randomizer;

/**
 * The main testbed.
 */

/**
 * @author Liang Wang
 *
 */
public class Testbed {
	public static void main(String[] args) {
		//Set timer
		long time = 0;
		//Progress settings
		Runnable rt = ()->{
			while(true) {
				try {
					Thread.sleep(10000);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Configurator.progress[0] + "/" + Configurator.progress[1]);
			}
		};
		//Options
		int option = 0;
		try(Scanner in = new Scanner(System.in)) {
			System.out.println("1. Experiment: Correctness: Test 1");
			System.out.println("2. Experiment: Correctness: Test 2");
			System.out.println("3. Experiment: Robustness");
			System.out.println("4. Experiment: Efficiency");
			System.out.println("==========");
			option = Integer.parseInt(in.nextLine());
		}
		switch (option) {
		case 1:
			System.out.println("Start Experiment: Correctness: Test 1...");
			//Load configuration
			Configurator.loadConf("conf_exp_correctness_test1.txt");
			//Start progress monitor
			new Thread(rt).start();
			//Run test
			time = System.currentTimeMillis();
			Testbed.expCorrectnessTest1();
			System.out.println("Run time: " + (System.currentTimeMillis() - time));
			System.exit(0);
			break;
		case 2:
			System.out.println("Start Experiment: Correctness: Test 2...");
			//Load configuration
			Configurator.loadConf("conf_exp_correctness_test2.txt");
			//Start progress monitor
			new Thread(rt).start();
			//Run test
			time = System.currentTimeMillis();
			Testbed.expCorrectnessTest2();
			System.out.println("Run time: " + (System.currentTimeMillis() - time));
			System.exit(0);
			break;
		case 3:
			System.out.println("Start Experiment: Robustness: ...");
			//Load configuration
			Configurator.loadConf("conf_exp_robustness.txt");
			//Start progress monitor
			new Thread(rt).start();
			//Run test
			time = System.currentTimeMillis();
			Testbed.expRobustness();
			System.out.println("Run time: " + (System.currentTimeMillis() - time));
			System.exit(0);
			break;
		case 4:
			System.out.println("Start Experiment: Efficiency: ...");
			//Load configuration
			Configurator.loadConf("conf_exp_efficiency.txt");
			//Start progress monitor
			new Thread(rt).start();
			//Run test
			time = System.currentTimeMillis();
			Testbed.expEfficiency();
			System.out.println("Run time: " + (System.currentTimeMillis() - time));
			System.exit(0);
			break;
		default:
			System.out.println("Bye bye!");
			return;
		}
	}
	
	//////////////////////////////////////////
	//The experiment of correctness: Test 1.//
	//////////////////////////////////////////
	
	/**
	 * Experiment: Correctness: Test 1.
	 */
	public static void expCorrectnessTest1() {
		//Set progress
		Configurator.progress[1] = Configurator.CdF + Configurator.CdF * 2 * Configurator.epsilon.length * Configurator.sigma.length;
		
		//Create key pairs
		HashMap<String, String> keyPairs = ECDSA.getECDSAKeyPairsHash(Configurator.users);
		
		//Prepare result set
		/*Header: Test, result, Host, F (hPK), Volume (MB), epsilon, 
		sigma, theta, rho, eta, chi, phi, tau, lambda, gamma*/
		ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String,String>>();
		/*Header: Test, Accuracy, Precision, Sensitivity, epsilon, sigma*/
		ArrayList<HashMap<String, String>> finalResults = new ArrayList<HashMap<String,String>>();
		
		//Data preparation
		byte[] d = new byte[Integer.parseInt(Configurator.volume[0])];
		for(int i = 0; i < d.length; i ++) {
			d[i] = (byte)Randomizer.randomInt(0, 256);
		}
		
		//System Parameters
		double gamma = Double.parseDouble(Configurator.gamma[0]);
		int tau = Integer.parseInt(Configurator.tau[0]);
		int lambda = Integer.parseInt(Configurator.lambda[0]);
		int presTheta = Integer.parseInt(Configurator.presTheta[0]);
		int presEta = Integer.parseInt(Configurator.presEta[0]);
		double r = Configurator.r;
		
		//Mappings and Embedding
		HashMap<String, SymbolMap> maps = new HashMap<String, SymbolMap>(); //<F, map>
		HashMap<String, Code> dFs = new HashMap<String, Code>(); //<F, dF>
		for(Entry<String, String> entry : keyPairs.entrySet()) {
			//Prepare hSK
			String hPK = entry.getKey();
			String hSK = entry.getValue();
			//Prepare F
			byte[] F = Converter.Hex2byte(hPK);
			//Mapping
			SymbolMap map = SymbolMapping.createSymbolMap(d, hSK, F, gamma, tau, presTheta, presEta, lambda);
			map.phi = Configurator.phi;
			map.chi = Configurator.chi;
			maps.put(hPK, map);
			//Embedding
			byte[] dF = DataEncoding.encodeData(d, map, F);
			Code cdF = new Code(dF);
			dFs.put(hPK, cdF);
			//Update progress
			Configurator.progress[0] ++;
		}
		
		//Test 1
		for(int j = 0; j < Configurator.epsilon.length; j ++) {
			double epsilon = Double.parseDouble(Configurator.epsilon[j]);
			for(int k = 0; k < Configurator.sigma.length; k ++) {
				int sigma = Integer.parseInt(Configurator.sigma[k]);
				//Terms of assessment
				int TP = 0, FP = 0, FN = 0, TN = 0;
				//Fingerprinting
				for(Entry<String, Code> entry : dFs.entrySet()) {
					//Get encoded data
					byte[] dF = entry.getValue().getData();
					
					//Get two pairs of map and F, one for positive test the other for negative test.
					HashMap<String, SymbolMap> f_maps = new HashMap<String, SymbolMap>();
					//Positive map and F
					String hPK = entry.getKey();
					f_maps.put(hPK, maps.get(hPK));
					//Negative map and F
					KeyPair<String, String> kp = Randomizer.randomEntry(keyPairs, hPK);
					f_maps.put(kp.getKey(), maps.get(kp.getKey()));
					
					//Testing
					for(Entry<String, SymbolMap> f_map : f_maps.entrySet()) {
						//Result set preparation
						HashMap<String, String> result = new HashMap<String, String>();
						//Authentication
						byte[] Fx = Converter.Hex2byte(f_map.getKey());
						if(Fingerprinting.fingerprinting(dF, f_map.getValue(), Fx, epsilon, sigma, tau, r)) {
							if(f_map.getKey().equals(hPK)) {
								TP ++;
								result.put("Result", "TP");
							}
							else {
								FP ++;
								result.put("Result", "FP");
							}
						}
						else {
							if(f_map.getKey().equals(hPK)) {
								FN ++;
								result.put("Result", "FN");
							}
							else {
								TN ++;
								result.put("Result", "TN");
							}
						}

						//Add items to the result
						result.put("Test", "1");
						result.put("Host", "simulated");
						result.put("F", hPK);
						result.put("Volume", Configurator.volume[0]);
						result.put("epsilon", String.valueOf(epsilon));
						result.put("sigma", String.valueOf(sigma));
						result.put("theta", String.valueOf(f_map.getValue().sLength));
						result.put("rho", String.valueOf(f_map.getValue().pLength));
						result.put("eta", String.valueOf(f_map.getValue().hLength));
						result.put("phi", String.valueOf(f_map.getValue().phi));
						result.put("chi", String.valueOf(f_map.getValue().chi));
						result.put("phiStar", String.valueOf(Configurator.phiStar));
						result.put("mean", String.valueOf(Configurator.mean));
						result.put("tau", Configurator.tau[0]);
						result.put("lambda", Configurator.lambda[0]);
						result.put("gamma", Configurator.gamma[0]);
						result.put("r", String.valueOf(Configurator.r));
						//Add result to results
						results.add(result);

						//Update progress
						Configurator.progress[0] ++;
					}
				}
				

				//Prepare metrics
				double accuracy = 0, precision = 0, sensitivity = 0;
				//Calculate the result
				accuracy = (TP + TN) * 1d / (TP + FN + TN + FP);
				accuracy = Math.round(accuracy * 10000d) / 10000d;
				precision = TP * 1d / (TP + FP);
				precision = Math.round(precision * 10000d) / 10000d;
				sensitivity = TP * 1d / (TP + FN);
				sensitivity = Math.round(sensitivity * 10000d) / 10000d;
				
				//Print result
				HashMap<String, String> finalResult = new HashMap<String, String>();
				/*Header: Test, Accuracy, Precision, Sensitivity, epsilon, sigma*/
				finalResult.put("Test", "1");
				finalResult.put("Accuracy", String.valueOf(accuracy));
				finalResult.put("Precision", String.valueOf(precision));
				finalResult.put("Sensitivity", String.valueOf(sensitivity));
				finalResult.put("epsilon", String.valueOf(epsilon));
				finalResult.put("sigma", String.valueOf(sigma));
				//Add finalResult to finalResults
				finalResults.add(finalResult);
			}
		}

		//Print results
		Printer.printHashMap("data/ExperimentCorrectnessTest1_details", results);
		Printer.printHashMap("data/ExperimentCorrectnessTest1_final", finalResults);
		System.out.println("Done!");
	}
	
	//////////////////////////////////////////
	//The experiment of correctness: Test 2.//
	//////////////////////////////////////////
	
	/**
	 * Experiment: Correctness: Test 2.
	 */
	public static void expCorrectnessTest2() {
		//Set progress
		Configurator.progress[1] = Configurator.CdF * Configurator.fileName.length + Configurator.CdF * 2 * Configurator.fileName.length;
		
		//Create key pairs
		HashMap<String, String> keyPairs = ECDSA.getECDSAKeyPairsHash(Configurator.users);
		
		//System Parameters
		double gamma = Double.parseDouble(Configurator.gamma[0]);
		int tau = Integer.parseInt(Configurator.tau[0]);
		int lambda = Integer.parseInt(Configurator.lambda[0]);
		int presTheta = Integer.parseInt(Configurator.presTheta[0]);
		int presEta = Integer.parseInt(Configurator.presEta[0]);
		double r = Configurator.r;
		//Application parameters
		double epsilon = Double.parseDouble(Configurator.epsilon[0]);
		int sigma = Integer.parseInt(Configurator.sigma[0]);
		
		//Prepare result set
		/*Header: Test, result, Host, F (hPK), Volume (MB), epsilon, 
		sigma, theta, rho, eta, chi, phi, tau, lambda, gamma*/
		ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String,String>>();
		/*Header: Test, Accuracy, Precision, Sensitivity, epsilon, sigma*/
		ArrayList<HashMap<String, String>> finalResults = new ArrayList<HashMap<String,String>>();
		
		//Test2
		for(int i = 0; i < Configurator.fileName.length; i ++) {
			//Data preparation
			byte[] d = DataProcessor.readData(Configurator.fileName[i]);
			
			//Mappings and Embedding
			HashMap<String, SymbolMap> maps = new HashMap<String, SymbolMap>(); //<F, map>
			HashMap<String, Code> dFs = new HashMap<String, Code>(); //<F, dF>
			for(Entry<String, String> entry : keyPairs.entrySet()) {
				//Prepare hSK
				String hPK = entry.getKey();
				String hSK = entry.getValue();
				//Prepare F
				byte[] F = Converter.Hex2byte(hPK);
				//Mapping
				SymbolMap map = SymbolMapping.createSymbolMap(d, hSK, F, gamma, tau, presTheta, presEta, lambda);
				map.phi = Configurator.phi;
				map.chi = Configurator.chi;
				maps.put(hPK, map);
				//Embedding
				byte[] dF = DataEncoding.encodeData(d, map, F);
				Code cdF = new Code(dF);
				dFs.put(hPK, cdF);
				//Update progress
				Configurator.progress[0] ++;
			}
			
			//Terms of assessment
			int TP = 0, FP = 0, FN = 0, TN = 0;
			//Fingerprinting
			for(Entry<String, Code> entry : dFs.entrySet()) {
				//Get encoded data
				byte[] dF = entry.getValue().getData();
				
				//Get two pairs of map and F, one for positive test the other for negative test.
				HashMap<String, SymbolMap> f_maps = new HashMap<String, SymbolMap>();
				//Positive map and F
				String hPK = entry.getKey();
				f_maps.put(hPK, maps.get(hPK));
				//Negative map and F
				KeyPair<String, String> kp = Randomizer.randomEntry(keyPairs, hPK);
				f_maps.put(kp.getKey(), maps.get(kp.getKey()));
				
				//Testing
				for(Entry<String, SymbolMap> f_map : f_maps.entrySet()) {
					//Result set preparation
					HashMap<String, String> result = new HashMap<String, String>();
					//Authentication
					byte[] Fx = Converter.Hex2byte(f_map.getKey());
					if(Fingerprinting.fingerprinting(dF, f_map.getValue(), Fx, epsilon, sigma, tau, r)) {
						if(f_map.getKey().equals(hPK)) {
							TP ++;
							result.put("Result", "TP");
						}
						else {
							FP ++;
							result.put("Result", "FP");
						}
					}
					else {
						if(f_map.getKey().equals(hPK)) {
							FN ++;
							result.put("Result", "FN");
						}
						else {
							TN ++;
							result.put("Result", "TN");
						}
					}

					//Add items to the result
					result.put("Test", "2");
					result.put("Host", Configurator.fileName[i]);
					result.put("F", hPK);
					result.put("Volume", String.valueOf(d.length));
					result.put("epsilon", Configurator.epsilon[0]);
					result.put("sigma", Configurator.sigma[0]);
					result.put("theta", String.valueOf(f_map.getValue().sLength));
					result.put("rho", String.valueOf(f_map.getValue().pLength));
					result.put("eta", String.valueOf(f_map.getValue().hLength));
					result.put("phi", String.valueOf(f_map.getValue().phi));
					result.put("chi", String.valueOf(f_map.getValue().chi));
					result.put("phiStar", String.valueOf(Configurator.phiStar));
					result.put("mean", String.valueOf(Configurator.mean));
					result.put("tau", Configurator.tau[0]);
					result.put("lambda", Configurator.lambda[0]);
					result.put("gamma", Configurator.gamma[0]);
					result.put("r", String.valueOf(Configurator.r));
					//Add result to results
					results.add(result);

					//Update progress
					Configurator.progress[0] ++;
				}
			}
			
			//Prepare metrics
			double accuracy = 0, precision = 0, sensitivity = 0;
			//Calculate the result
			accuracy = (TP + TN) * 1d / (TP + FN + TN + FP);
			accuracy = Math.round(accuracy * 10000d) / 10000d;
			precision = TP * 1d / (TP + FP);
			precision = Math.round(precision * 10000d) / 10000d;
			sensitivity = TP * 1d / (TP + FN);
			sensitivity = Math.round(sensitivity * 10000d) / 10000d;
			
			//Print result
			HashMap<String, String> finalResult = new HashMap<String, String>();
			/*Header: Test, Accuracy, Precision, Sensitivity, epsilon, sigma*/
			finalResult.put("Test", "2");
			finalResult.put("Host", Configurator.fileName[i]);
			finalResult.put("Accuracy", String.valueOf(accuracy));
			finalResult.put("Precision", String.valueOf(precision));
			finalResult.put("Sensitivity", String.valueOf(sensitivity));
			finalResult.put("Volume", String.valueOf(d.length));
			finalResult.put("epsilon", Configurator.epsilon[0]);
			finalResult.put("sigma", Configurator.sigma[0]);
			//Add finalResult to finalResults
			finalResults.add(finalResult);
		}
		
		//Print results
		Printer.printHashMap("data/ExperimentCorrectnessTest2_details", results);
		Printer.printHashMap("data/ExperimentCorrectnessTest2_final", finalResults);
		System.out.println("Done!");
	}
	
	/////////////////////////////////
	//The experiment of robustness.//
	/////////////////////////////////
	
	/**
	 * Experiment: Robustness.
	 */
	public static void expRobustness() {
		//Set progress
		Configurator.progress[1] = Configurator.CdF + Configurator.CdF * 2 * 5 * Configurator.epsilon.length * Configurator.sigma.length;
		
		//Create key pairs
		HashMap<String, String> keyPairs = ECDSA.getECDSAKeyPairsHash(Configurator.users);

		//Data preparation
		byte[] d = new byte[Integer.parseInt(Configurator.volume[0])];
		for(int i = 0; i < d.length; i ++) {
			d[i] = (byte)Randomizer.randomInt(0, 256);
		}
		
		//System Parameters
		double gamma = Double.parseDouble(Configurator.gamma[0]);
		int tau = Integer.parseInt(Configurator.tau[0]);
		int lambda = Integer.parseInt(Configurator.lambda[0]);
		int presTheta = Integer.parseInt(Configurator.presTheta[0]);
		int presEta = Integer.parseInt(Configurator.presEta[0]);
		double r = Configurator.r;
		
		//Mappings and Embedding
		HashMap<String, SymbolMap> maps = new HashMap<String, SymbolMap>(); //<F, map>
		HashMap<String, Code> dFs = new HashMap<String, Code>(); //<F, dF>
		for(Entry<String, String> entry : keyPairs.entrySet()) {
			//Prepare hSK
			String hPK = entry.getKey();
			String hSK = entry.getValue();
			//Prepare F
			byte[] F = Converter.Hex2byte(hPK);
			//Mapping
			SymbolMap map = SymbolMapping.createSymbolMap(d, hSK, F, gamma, tau, presTheta, presEta, lambda);
			map.phi = Configurator.phi;
			map.chi = Configurator.chi;
			maps.put(hPK, map);
			//Embedding
			byte[] dF = DataEncoding.encodeData(d, map, F);
			Code cdF = new Code(dF);
			dFs.put(hPK, cdF);
			//Update progress
			Configurator.progress[0] ++;
		}
		
		//Prepare result set
		/*Header: Test, result, Host, F (hPK), Volume (MB), epsilon, 
		sigma, theta, rho, eta, chi, phi, tau, lambda, gamma*/
		ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String,String>>();
		/*Header: Test, Accuracy, Precision, Sensitivity, epsilon, sigma*/
		ArrayList<HashMap<String, String>> finalResults = new ArrayList<HashMap<String,String>>();
		
		//Attack models
		String[] attacks = {"Deletion","Swap","Padding","Negation","Reversion"};
		
		//Test2
		for(int i = 0; i < attacks.length; i ++) {
			for(int j = 0; j < Configurator.epsilon.length; j ++) {
				double epsilon = Double.parseDouble(Configurator.epsilon[j]);
				for(int k = 0; k < Configurator.sigma.length; k ++) {
					int sigma = Integer.parseInt(Configurator.sigma[k]);
					//Terms of assessment
					int TP = 0, FP = 0, FN = 0, TN = 0;
					
					//Fingerprinting
					for(Entry<String, Code> entry : dFs.entrySet()) {
						//Get encoded data
						byte[] dF = entry.getValue().getData();
						
						//Broken data preparation
						dF = DataProcessor.dataBreaking(dF, attacks[i]);
					
						//Get two pairs of map and F, one for positive test the other for negative test.
						HashMap<String, SymbolMap> f_maps = new HashMap<String, SymbolMap>();
						//Positive map and F
						String hPK = entry.getKey();
						f_maps.put(hPK, maps.get(hPK));
						//Negative map and F
						KeyPair<String, String> kp = Randomizer.randomEntry(keyPairs, hPK);
						f_maps.put(kp.getKey(), maps.get(kp.getKey()));
						
						//Testing
						for(Entry<String, SymbolMap> f_map : f_maps.entrySet()) {
							//Result set preparation
							HashMap<String, String> result = new HashMap<String, String>();
							//Authentication
							byte[] Fx = Converter.Hex2byte(f_map.getKey());
							if(Fingerprinting.fingerprinting(dF, f_map.getValue(), Fx, epsilon, sigma, tau, r)) {
								if(f_map.getKey().equals(hPK)) {
									TP ++;
									result.put("Result", "TP");
								}
								else {
									FP ++;
									result.put("Result", "FP");
								}
							}
							else {
								if(f_map.getKey().equals(hPK)) {
									FN ++;
									result.put("Result", "FN");
								}
								else {
									TN ++;
									result.put("Result", "TN");
								}
							}

							//Add items to the result
							result.put("Attack", attacks[i]);
							result.put("F", hPK);
							result.put("Volume", String.valueOf(d.length));
							result.put("epsilon", String.valueOf(epsilon));
							result.put("sigma", String.valueOf(sigma));
							result.put("theta", String.valueOf(f_map.getValue().sLength));
							result.put("rho", String.valueOf(f_map.getValue().pLength));
							result.put("eta", String.valueOf(f_map.getValue().hLength));
							result.put("phi", String.valueOf(f_map.getValue().phi));
							result.put("chi", String.valueOf(f_map.getValue().chi));
							result.put("phiStar", String.valueOf(Configurator.phiStar));
							result.put("mean", String.valueOf(Configurator.mean));
							result.put("tau", Configurator.tau[0]);
							result.put("lambda", Configurator.lambda[0]);
							result.put("gamma", Configurator.gamma[0]);
							result.put("r", String.valueOf(Configurator.r));
							//Add result to results
							results.add(result);

							//Update progress
							Configurator.progress[0] ++;
						}
					}
					
					//Prepare metrics
					double accuracy = 0, precision = 0, sensitivity = 0;
					//Calculate the result
					accuracy = (TP + TN) * 1d / (TP + FN + TN + FP);
					accuracy = Math.round(accuracy * 10000d) / 10000d;
					precision = TP * 1d / (TP + FP);
					precision = Math.round(precision * 10000d) / 10000d;
					sensitivity = TP * 1d / (TP + FN);
					sensitivity = Math.round(sensitivity * 10000d) / 10000d;
					
					//Print result
					HashMap<String, String> finalResult = new HashMap<String, String>();
					/*Header: Test, Accuracy, Precision, Sensitivity, epsilon, sigma*/
					finalResult.put("Attack", attacks[i]);
					finalResult.put("Accuracy", String.valueOf(accuracy));
					finalResult.put("Precision", String.valueOf(precision));
					finalResult.put("Sensitivity", String.valueOf(sensitivity));
					finalResult.put("Volume", String.valueOf(d.length));
					finalResult.put("epsilon", String.valueOf(epsilon));
					finalResult.put("sigma", String.valueOf(sigma));
					//Add finalResult to finalResults
					finalResults.add(finalResult);
				}
			}
		}
		//Print results
		Printer.printHashMap("data/ExperimentRobustness_details", results);
		Printer.printHashMap("data/ExperimentRobustness_final", finalResults);
		System.out.println("Done!");
	}
	
	/////////////////////////////////
	//The experiment of efficiency.//
	/////////////////////////////////
	
	/**
	 * Experiment: Efficiency.
	 */
	public static void expEfficiency() {
		//Set progress
		Configurator.progress[1] = Configurator.volume.length * Configurator.presTheta.length * Configurator.presEta.length;
		
		//Prepare a key-pair
		String hSK = ECDSA.getECDSAPublicKeyHash();
		byte[] F = ECDSA.getECDSAPublicKeyHashBytes();
		
		//System Parameters
		double gamma = Double.parseDouble(Configurator.gamma[0]);
		int tau = Integer.parseInt(Configurator.tau[0]);
		int lambda = Integer.parseInt(Configurator.lambda[0]);
		double epsilon = Double.parseDouble(Configurator.epsilon[0]);
		int sigma = Integer.parseInt(Configurator.sigma[0]);
		double r = Configurator.r;
		
		ArrayList<HashMap<String, String>> results = new ArrayList<HashMap<String,String>>();
		
		//Vary volumes
		for(int i = 0; i < Configurator.volume.length; i ++) {
			//Data preparation
			byte[] d = new byte[Integer.parseInt(Configurator.volume[i])];
			for(int j = 0; j < d.length; j ++) {
				d[j] = (byte)Randomizer.randomInt(0, 256);
			}
			
			//Vary presTheta
			for(int m = 0; m < Configurator.presTheta.length; m ++) {
				int presTheta = Integer.parseInt(Configurator.presTheta[m]);
				//Vary presEta
				for(int n = 0; n < Configurator.presEta.length; n ++) {
					int presEta = Integer.parseInt(Configurator.presEta[n]);
					//Mapping
					long SMRT = System.currentTimeMillis();
					SymbolMap map = SymbolMapping.createSymbolMap(d, hSK, F, gamma, tau, presTheta, presEta, lambda);
					SMRT = System.currentTimeMillis() - SMRT;
					map.phi = Configurator.phi;
					map.chi = Configurator.chi;
					//Embedding
					byte[] dF = DataEncoding.encodeData(d, map, F);
					//Fingerprinting
					long FIRT = System.currentTimeMillis();
					Fingerprinting.fingerprinting(dF, map, F, epsilon, sigma, tau, r);
					FIRT = System.currentTimeMillis() - FIRT;
					
					//Result set preparation
					HashMap<String, String> result = new HashMap<String, String>();
					//Add items to the result
					result.put("SMRT", String.valueOf(SMRT));
					result.put("FIRT", String.valueOf(FIRT));
					result.put("F", Converter.byte2Hex(F));
					result.put("Volume", String.valueOf(d.length));
					result.put("epsilon", String.valueOf(epsilon));
					result.put("sigma", String.valueOf(sigma));
					result.put("theta", String.valueOf(presTheta));
					result.put("rho", String.valueOf(map.pLength));
					result.put("eta", String.valueOf(presEta));
					result.put("phi", String.valueOf(map.phi));
					result.put("chi", String.valueOf(map.chi));
					result.put("tau", Configurator.tau[0]);
					result.put("lambda", Configurator.lambda[0]);
					result.put("gamma", Configurator.gamma[0]);
					//Add result to results
					results.add(result);

					//Update progress
					Configurator.progress[0] ++;
				}
			}
		}
		//Print results
		Printer.printHashMap("data/ExperimentEfficiency_details", results);
		System.out.println("Done!");
	}
}
