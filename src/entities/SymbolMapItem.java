/**
 * Symbol map item.
 * 
 */
package entities;

/**
 * @deprecated
 * @author Liang Wang
 * All elements of an item are presented in Hex string.
 */
public class SymbolMapItem {
	//Symbol
	private byte[] s;
	//Plain code
	private byte[] p;
	//Hidden code
	private byte[] h;
	/**
	 * Make an item of a symbol map.
	 * @param s symbol
	 * @param p plain code
	 * @param h hidden code
	 */
	public SymbolMapItem(byte[] s, byte[] p, byte[] h) {
		this.s = s;
		this.p = p;
		this.h = h;
	}
	/**
	 * Set the symbol of the item.
	 * @param s the symbol to be set.
	 */
	public void setS(byte[] s) {this.s = s;}
	/**
	 * Set the plain code of the item.
	 * @param p the plain code to be set.
	 */
	public void setP(byte[] p) {this.p = p;}
	/**
	 * Set the hidden code of the item.
	 * @param h the hidden code to be set.
	 */
	public void setH(byte[] h) {this.h = h;}
	/**
	 * Get the symbol of the item.
	 * @return the symbol.
	 */
	public byte[] getS() {return this.s;}
	/**
	 * Get the plain code of the item.
	 * @return the plain code.
	 */
	public byte[] getP() {return this.p;}
	/**
	 * Get the hidden code of the item.
	 * @return the hidden code.
	 */
	public byte[] getH() {return this.h;}
	/**
	 * Get the secret part of the item.
	 * @return the secret part of the item
	 */
	public SymbolMapItemSecret getSecretPart() {
		return new SymbolMapItemSecret(s, p);
	}
	/**
	 * Get the public part of the item.
	 * @return the public part of the item.
	 */
	public SymbolMapItemPublic getPublicPart() {
		return new SymbolMapItemPublic(p, h);
	}
}
