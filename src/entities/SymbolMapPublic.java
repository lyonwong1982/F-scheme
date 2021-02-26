/**
 * The public part of a symbol map.
 */
package entities;

import java.util.ArrayList;
import java.util.Arrays;

import tools.Converter;

/**
 * @deprecated
 * @author Liang Wang
 *
 */
public class SymbolMapPublic {
	private ArrayList<SymbolMapItemPublic> items;
	/**
	 * Create a new empty public part of a symbol map.
	 */
	public SymbolMapPublic() {
		items = new ArrayList<SymbolMapItemPublic>();
	}
	/**
	 * Create a new public part of a symbol map with given items.
	 * @param items the given items.
	 */
	public SymbolMapPublic(ArrayList<SymbolMapItemPublic> items) {
		this.items = items;
	}
	/**
	 * Set the items
	 * @param items the given items.
	 */
	public void setItems(ArrayList<SymbolMapItemPublic> items) {this.items = items;}
	/**
	 * Get the items.
	 * @return the items.
	 */
	public ArrayList<SymbolMapItemPublic> getItems(){return items;}
	/**
	 * Add a new item to this secret part of symbol map.
	 * @param item the new item
	 */
	public void addItem(SymbolMapItemPublic item) {
		items.add(item);
	}
	/**
	 * Get the full set P.<br>
	 * The plain codes in this set are presented in Hex string, so you should convert them into bytes before you use them.
	 * @return The set P.
	 * @deprecated
	 */
	public ArrayList<String> getSetP(){
		ArrayList<String> P = new ArrayList<String>();
		for(int i = 0; i < items.size(); i ++) {
			String p = Converter.byte2Hex(items.get(i).getP());
			if(!P.contains(p)) {
				P.add(p);
			}
		}
		return P;
	}
	/**
	 * Get h by p
	 * @param p the given plain code.
	 * @return The h in connection with the given p.
	 */
	public byte[] gethByp(byte[] p) {
		for(int i = 0; i < items.size(); i ++) {
			if(Arrays.equals(p, items.get(i).getP())) {
				return items.get(i).getH();
			}
		}
		return null;
	}
	/**
	 * Gets the set of P which is correspinding to the given h.
	 * @param h the hidden code.
	 * @return A ByteQueue which contains all qualified p.
	 */
	public ByteQueue getSetPByh(byte[] h) {
		if(h == null) { // validation
			return null;
		}
		ByteQueue P = ByteQueue.getByteQueue(items.get(0).getP().length);
		for(int i = 0; i < items.size(); i ++) {
			if(Arrays.equals(h, items.get(i).getH())) {
				P.offer(items.get(i).getP());
			}
		}
		return P;
	}
}
