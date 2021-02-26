/**
 * The secret part of a symbol map.
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
public class SymbolMapSecret {
	private ArrayList<SymbolMapItemSecret> items;
	/**
	 * Create a new empty secret part of a symbol map.
	 */
	public SymbolMapSecret() {
		items = new ArrayList<SymbolMapItemSecret>();
	}
	/**
	 * Create a new secret part of a symbol map with given items.
	 * @param items the given items.
	 */
	public SymbolMapSecret(ArrayList<SymbolMapItemSecret> items) {
		this.items = items;
	}
	/**
	 * Set the items
	 * @param items the given items.
	 */
	public void setItems(ArrayList<SymbolMapItemSecret> items) {this.items = items;}
	/**
	 * Get the items.
	 * @return the items.
	 */
	public ArrayList<SymbolMapItemSecret> getItems(){return items;}
	/**
	 * Add a new item to this secret part of symbol map.
	 * @param item the new item
	 */
	public void addItem(SymbolMapItemSecret item) {
		items.add(item);
	}
	/**
	 * Get the full set S.<br>
	 * The symbols in this set are presented in Hex string, so you should convert them into bytes before you use them.
	 * @return The set S.
	 */
	public ArrayList<String> getSetS(){
		ArrayList<String> S = new ArrayList<String>();
		for(int i = 0; i < items.size(); i ++) {
			String s = Converter.byte2Hex(items.get(i).getS());
			if(!S.contains(s)) {
				S.add(s);
			}
		}
		return S;
	}
	/**
	 * Get s by p
	 * @param p the given plain code.
	 * @return The s in connection with the given p.
	 */
	public byte[] getsByp(byte[] p) {
		for(int i = 0; i < items.size(); i ++) {
			if(Arrays.equals(p, items.get(i).getP())) {
				return items.get(i).getS();
			}
		}
		return null;
	}
	/**
	 * Get set P [by s].<br>
	 * The plain codes in the result set are presented in Hex string, remember to convert them into bytes before use them.
	 * @param s the given symbol. If you want to get the full set of P, then you can leave s as null.
	 * @return The set P (in Hex string).
	 */
	public ArrayList<String> getSetP(byte[] s){
		ArrayList<String> P = new ArrayList<String>();
		if(s == null) {//Get the full set of P
			for(int i = 0; i < items.size(); i ++) {
				String p = Converter.byte2Hex(items.get(i).getP());
				if(!P.contains(p)) {
					P.add(p);
				}
			}
		}
		else {//Get set P by the given s
			for(int i = 0; i < items.size(); i ++) {
				if(Arrays.equals(s, items.get(i).getS())) {
					String p = Converter.byte2Hex(items.get(i).getP());
					if(!P.contains(p)) {
						P.add(p);
					}
				}
			}
		}
		return P;
	}
}
