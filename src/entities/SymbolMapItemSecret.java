/**
 * The secret part of a symbol map item.
 */
package entities;

/**
 * @deprecated
 * @author Liang Wang
 *
 */
public class SymbolMapItemSecret {
	//Symbol
	private byte[] s;
	//Plain code
	private byte[] p;
	/**
	 * Make the secret part of a symbol map item.
	 * @param s the symbol
	 * @param p the plain code
	 */
	public SymbolMapItemSecret(byte[] s, byte[] p) {
		this.s = s;
		this.p = p;
	}
	/**
	 * Set the symbol
	 * @param s the symbol
	 */
	public void setS(byte[] s) {this.s = s;}
	/**
	 * Set the plain code
	 * @param p the plain code
	 */
	public void setP(byte[] p) {this.p = p;}
	/**
	 * Get the symbol
	 * @return the symbol
	 */
	public byte[] getS() {return s;}
	/**
	 * Get the plain code.
	 * @return the plain code.
	 */
	public byte[] getP() {return p;}
}
