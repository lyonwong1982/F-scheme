/**
 * The public part of a symbol map item.
 */
package entities;

/**
 * @deprecated
 * @author Liang Wang
 *
 */
public class SymbolMapItemPublic {
	//Plain code
	private byte[] p;
	//Hidden code
	private byte[] h;
	/**
	 * Make the public part of a symbol map item.
	 * @param p the plain code
	 * @param h the hidden code
	 */
	public SymbolMapItemPublic(byte[] p, byte[] h) {
		this.p = p;
		this.h = h;
	}
	/**
	 * Set the plain code
	 * @param p the plain code
	 */
	public void setP(byte[] p) {this.p = p;}
	/**
	 * Set the hidden code
	 * @param h the hidden code
	 */
	public void setH(byte[] h) {this.h = h;}
	/**
	 * Get the plain code.
	 * @return the plain code
	 */
	public byte[] getP() {return p;}
	/**
	 * Get the hidden code.
	 * @return the hidden code.
	 */
	public byte[] getH() {return h;}
}
