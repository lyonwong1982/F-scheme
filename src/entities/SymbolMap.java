/**
 * Symbol map
 */
package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


/**
 * @author Liang Wang
 *
 */
public class SymbolMap {
	private HashMap<Code, Code> mapP; // <p, h> Public part of the symbol map.
	private HashMap<Code, Code> mapS; // <p, s> Secret part of the symbol map.
	public int pLength = 0; // rho
	public int sLength = 0; // theta
	public int hLength = 0; // eta
	public int phi = 0; // phi just for ease of statistic.
	public int chi = 0; // chi just for ease of statistic.
	/**
	 * Initiates an empty object of SymbolMap.
	 */
	public SymbolMap() {
		mapP = new HashMap<Code, Code>();
		mapS = new HashMap<Code, Code>();
	}
//********************
//	/**
//	 * Initiates an object of SymbolMap with given mapP and mapS.<br>
//	 * Warning: Be careful of the parameters, or you may make the items of this symbol map inconsistent.
//	 * @param mapP public part of the symbol map.
//	 * @param mapS secret part of the symbol map.
//	 */
//	public SymbolMap(HashMap<Code, Code> mapP, HashMap<Code, Code> mapS) {
//		this.mapP = mapP;
//		this.mapS = mapS;
//	}
//	/**
//	 * Sets the mapP.<br>
//	 * Warning: This method should be used in conjunction with setMapS, or may cause inconsistent items in this symbol map.
//	 * @param mapP new mapP
//	 */
//	public void setMapP(HashMap<Code, Code> mapP) {
//		this.mapP = mapP;
//	}
//	/**
//	 * Sets the mapS.<br>
//	 *  Warning: This method should be used in conjunction with setMapP, or may cause inconsistent items in this symbol map.
//	 * @param mapS new mapS
//	 */
//	public void setMapS(HashMap<Code, Code> mapS) {
//		this.mapS = mapS;
//	}
//********************
	/**
	 * Gets the public part of this symbol map.
	 * @return Returns the mapP
	 */
	public HashMap<Code, Code> getMapP(){
		return mapP;
	}
	/**
	 * Gets the secret part of this symbol map.
	 * @return Returns the mapS
	 */
	public HashMap<Code, Code> getMapS(){
		return mapS;
	}
	/**
	 * Returns the set of symbols of this symbol map without distinction.
	 */
	public ArrayList<Code> getSs() {
		return new ArrayList<Code>(mapS.values());
	}
	/**
	 * Returns the distinct set of symbols of this symbol map.
	 */
	public ArrayList<Code> getSsDistinct(){
		ArrayList<Code> DS = new ArrayList<Code>();
		ArrayList<Code> S= new ArrayList<Code>(mapS.values());
		for(int i = 0; i < S.size(); i ++) {
			Code c = S.get(i);
			if(!DS.contains(c)) {
				DS.add(c);
			}
		}
		return DS;
	}
	/**
	 * Returns the set of hidden codes of this symbol map without distinction.
	 */
	public ArrayList<Code> getHs(){
		return new ArrayList<Code>(mapP.values());
	}
	/**
	 * Returns the distinct set of hidden codes of this symbol map.
	 */
	public ArrayList<Code> getHsDistinct(){
		ArrayList<Code> DH = new ArrayList<Code>();
		ArrayList<Code> H= new ArrayList<Code>(mapP.values());
		for(int i = 0; i < H.size(); i ++) {
			Code c = H.get(i);
			if(!DH.contains(c)) {
				DH.add(c);
			}
		}
		return DH;
	}
	/**
	 * Returns the set of plain codes of this symbol map.
	 */
	public ArrayList<Code> getPs(){
		return new ArrayList<Code>(mapP.keySet());
	}
	/**
	 * Returns the symbol related to a given plain code.
	 * @param p the given plain code.
	 */
	public Code getsByp(Code p) {
		if(p == null) {
			return null;
		}
		return mapS.get(p);
	}
	/**
	 * Returns the hidden code related to a given plain code.
	 * @param p the given plain code.
	 */
	public Code gethByp(Code p) {
		if(p == null) {
			return null;
		}
		return mapP.get(p);
	}
	/**
	 * Returns the set of plain codes related to a same symbol.
	 * @param s the given symbol
	 */
	public ArrayList<Code> getPBys(Code s){
		if(s == null) {
			return null;
		}
		ArrayList<Code> P = new ArrayList<Code>();
		for(Entry<Code, Code> entry : mapS.entrySet()) {
			if(entry.getValue().equals(s)) {
				P.add(entry.getKey());
			}
		}
		return P;
	}
	/**
	 * Returns the set of plain codes related to a same hidden code.
	 * @param h the given hidden code.
	 */
	public ArrayList<Code> getPByh(Code h){
		if(h == null) {
			return null;
		}
		ArrayList<Code> P = new ArrayList<Code>();
		for(Entry<Code, Code> entry : mapP.entrySet()) {
			if(entry.getValue().equals(h)) {
				P.add(entry.getKey());
			}
		}
		return P;
	}
	/**
	 * Returns the set of plain codes related to a given symbol and a given hidden code.
	 * @param s the given symbol
	 * @param h the given hidden code
	 */
	public ArrayList<Code> getPBysh(Code s, Code h){
		if(s == null || h == null) {
			return null;
		}
		ArrayList<Code> Ps = getPBys(s);
		ArrayList<Code> Ph = getPByh(h);
		if(Ps == null || Ph == null) {
			return null;
		}
		Ps.retainAll(Ph);
		return Ps;
	}
	/**
	 * Examines whether the symbol map contains an item which has s and h contained.
	 * @param s the given symbols.
	 * @param h the given hidden code.
	 * @return Returns true if the symbol map contains such item that has s and h at the same time.
	 */
	public boolean contains(Code s, Code h) {
		ArrayList<Code> P = getPBysh(s, h);
		if(P != null) {
			if(!P.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	/**
	 * Adds a new item into this symbol map. <br>
	 * @param s the symbol
	 * @param p the plain code
	 * @param h the hidden code
	 * @param distinct true indicates checking repetition before insert the new item
	 * @return True if succeeds.
	 */
	public boolean addItem(Code s, Code p, Code h, boolean distinct) {
		if(s == null || p == null || h == null) {
			System.out.println(this.toString() + ": addItem(), parameters null!");
			return false;
		}
		if(!s.isValid() || !p.isValid() || !h.isValid()) {
			System.out.println(this.toString() + ": addItem(), parameters invalid!");
			return false;
		}
		if(mapS.containsKey(p) || mapP.containsKey(p)) {
			System.out.println(this.toString() + ": addItem(), p is exist!");
			return false;
		}
		if(distinct) {
			if(contains(s, h)) {
				return false;
			}
		}
		mapP.put(p, h);
		mapS.put(p, s);
		sLength = s.length();
		pLength = p.length();
		hLength = h.length();
		return true;
	}
	/**
	 * Returns the size of the public part of this symbol map.
	 */
	public long getSizePublicPart() {
		if(mapP.isEmpty()) {
			return 0;
		}
		return 1L * pLength * mapP.size() + hLength * getHsDistinct().size();
	}
	/**
	 * Returns the size of the secret part of this symbol map.
	 */
	public long getSizeSecretPart() {
		if(mapS.isEmpty()) {
			return 0;
		}
		return 1L * pLength * mapP.size() + sLength * getSsDistinct().size();
	}
	/**
	 * Returns the total size of the symbol map.
	 */
	public long getSizeTotal() {
		if(mapP.isEmpty() || mapS.isEmpty()) {
			return 0;
		}
		return 1L * pLength * mapP.size() + sLength * getSsDistinct().size() + hLength * getHsDistinct().size();
	}
}
