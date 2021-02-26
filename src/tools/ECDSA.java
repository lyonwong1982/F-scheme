/**
 * ECDSA public key generator.
 */
package tools;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.HashMap;

/**
 * @author Liang Wang
 *
 */
public class ECDSA {
	/**
	 * Get the hash of a public key of ECDSA.
	 * @return Hex string of the hash of the generated public key.
	 */
	public static String getECDSAPublicKeyHash() {
		String h = "";
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
			keyPairGenerator.initialize(256);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
			h = SHA256.getSHA256FromBytes(ecPublicKey.getEncoded());
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return h;
	}
	/**
	 * Get the hash of a public key of ECDSA.
	 * @return byte array of the hash of the generated public key.
	 */
	public static byte[] getECDSAPublicKeyHashBytes() {
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
			keyPairGenerator.initialize(256);
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
			return SHA256.getSHA256(ecPublicKey.getEncoded());
		} 
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	/**
	 * Generate a group of ECDSA key pairs on demand.
	 * @param num the number of key pairs.
	 * @return Generated key pairs (hPK, hSK).
	 */
	public static HashMap<String, String> getECDSAKeyPairsHash(int num){
		HashMap<String, String> kps = new HashMap<String, String>();
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
			keyPairGenerator.initialize(256);
			for(int i = 0; i < num; i++) {
				String hPK = "";
				String hSK = "";
				while(true) {
					KeyPair keyPair = keyPairGenerator.generateKeyPair();
					ECPublicKey ecPublicKey = (ECPublicKey) keyPair.getPublic();
					ECPrivateKey ecPrivateKey = (ECPrivateKey) keyPair.getPrivate();
					hPK = SHA256.getSHA256FromBytes(ecPublicKey.getEncoded());
					hSK = SHA256.getSHA256FromBytes(ecPrivateKey.getEncoded());
					if(!kps.containsKey(hPK)) {
						kps.put(hPK, hSK);
						break;
					}
				}
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		return kps;
	}
}
