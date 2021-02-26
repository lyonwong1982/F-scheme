import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;

import javax.sound.sampled.ReverbType;

import java.util.Queue;

import entities.ByteQueue;
import entities.SymbolMap;
import functions.DataDecoding;
import functions.DataEncoding;
import functions.DataProcessor;
import functions.Fingerprinting;
import functions.ParameterMaker;
import functions.SymbolMapping;
import tools.Calculator;
import tools.Configurator;
import tools.Converter;
import tools.ECDSA;
import tools.Numbera;
import tools.Randomizer;

/**
 * This is a tester for the functionalities in this project.
 */

/**
 * @author Liang Wang
 *
 */
public class Tester {
	public static void main(String[] args) {
//		HashMap<String, String> kps = ECDSA.getECDSAKeyPairsHash(5);
//		for(Entry<String, String> entry : kps.entrySet()) {
//			System.out.println(entry.getKey() + ":" + entry.getValue());
//		}
//		String str = "abcde987";
//		char[] cs = new char[str.length()];
//		str.getChars(0, str.length(), cs, 0);
////		System.out.println(bs[0]);
//		System.out.println(Integer.parseInt(String.valueOf(cs[6]), 16));
//		int k = DataProcessor.sumSubString(str, 3, 6);
//		System.out.println(k);
//		int[] scale = new int[2];
//		String hSK = ECDSA.getECDSAPublicKeyHash();
//		scale = DataProcessor.getDataMatrixScale(133, hSK);
//		System.out.println(scale[0] + ":" + scale[1]);
		
//		ArrayList<Integer> al = Numbera.getDivisors(7381546);
//		for(int i = 0; i < al.size(); i ++) {
//			System.out.println(al.get(i));
//		}
		
//		for(int i = 1; i <= 4095; i ++) {
//			System.out.println(4095 % i);
//		}
		
//		System.out.println(Integer.parseInt("fff", 16));
		
//		int decimal = DataProcessor.decimalSubString("abcdefffabcde", 2, 6);
//		System.out.println(decimal);
		
//		String hSK = ECDSA.getECDSAPublicKeyHash();
//		int p = ParameterMaker.getSymbolLength(hSK, 57);
//		System.out.println(p);
		
//		int phi = ParameterMaker.getFingerprintRedundancy(hSK, 34944, 64);
//		int eta = ParameterMaker.getHiddenCodeLength(phi, 34944, 64);
//		System.out.println(phi + ":" + eta);
		
//		for(int i = 1; i < 10; i ++) {
////			System.out.println((long)Math.pow(2, 8*i));
//			if(Math.pow(2, 8*i) >= (1073741824 * 64d)) {
//				System.out.println(i);
//			}
//		}
		
//		int k = ParameterMaker.getPlainCodeLength(hSK, 12345, 7);
//		System.out.println(k);
		
//		byte[] b = {97,98,99};
//		String str = Converter.byte2Hex(b);
//		System.out.println(str);
//		System.out.println(Integer.parseInt(str.substring(0,2), 16));
		
//		long t = System.currentTimeMillis();
//		String str = "";
//		byte[] b = new byte[104857600];
//		for(int i = 0; i < 104857600; i ++) {
////			str += "a";
//			b[i] = (byte)97;
////			if(i == 104857509) {
////				System.out.println(String.valueOf(b[i]));
////			}
//		}
//		System.out.println("b has been done!");
//		SymbolMapping.createSymbolMap(b, hSK, "");
////		System.out.println(Converter.byte2Hex(b));
//		t = System.currentTimeMillis() - t;
//		System.out.println(t/1000 + "s");
		
//		String str = "abcÍõ";
//		try {
//			byte[] b = str.getBytes("UTF-8");		
//			for(int i = 0; i < b.length; i ++) {
//				System.out.println(b[i]);
//			}
//		}
//		catch(Exception ex) {
//			ex.printStackTrace();
//		}
		
//		String str = "abe908";
////		int l = 2882398472;
//		byte[] b = str.getBytes();
//		for(int i = 0 ; i < b.length; i ++) {
//			System.out.println(b[i]);
//		}
		
//		String str = "abcÍõ";
//		System.out.println("Original: " + str);
//		try {
//			byte[] b = str.getBytes("UTF-8");
//			String hex = Converter.byte2Hex(b);//61 62 63 e7 8e 8b
//			System.out.println("b2h: " + hex);
//			byte[] bty = Converter.Hex2byte(hex);//49 50 51 231 142 139
//			String hex2 = Converter.byte2Hex(bty);
//			System.out.println("b2h2: " + hex2);
//			System.out.println(Arrays.equals(b, bty));
//			str = new String(bty,"UTF-8");
//			System.out.println(str);
//		}
//		catch(Exception ex) {
//			ex.printStackTrace();
//		}
		
//		byte[][] b = new byte[2][3];
//		System.out.println(b.length);
//		System.out.println(b[0].length);
		
//		ArrayList<String> astr = new ArrayList<String>();
//		for(int i = 0; i < 10; i ++) {
//			astr.add("s" + i);
//		}
//		for(String s : astr) {
//			System.out.println(s);
//		}
		
//		System.out.println(Randomizer.randomHex(10));
//		System.out.println(Integer.parseInt("0c21e228", 16));
		
//		byte[][] a = {{1,2},{3,4,},{5,6}};
//		byte[][] b = {{1,2,3},{4,5,6},{7,8,9}};
//		for(int i = 0; i < a.length; i ++) {
//			a[i] = b[i];
//		}
//		for(int i = 0; i < a.length; i ++) {
//			for(int j = 0; j < a[i].length; j ++) {
//				System.out.print(a[i][j] + " ");
//			}
//			System.out.println("");
//		}
//		for(int i = 0; i < b.length; i ++) {
//			for(int j = 0; j < b[i].length; j ++) {
//				System.out.print(b[i][j] + " ");
//			}
//			System.out.println("");
//		}
		
//		byte[] a = {1,2,3,4};
//		byte[] b = {1,2,3,4};
//		System.out.println(a.hashCode() +":"+ b.hashCode());
		
//		Queue<String> k = new LinkedList<String>();
//		for(int i = 0; i < 10; i ++) {
//			k.offer(String.valueOf(i));
//		}
//		Iterator<String> itr = k.iterator();
//		for(int i = 0; i < 3; i ++) {
//			itr.next();
//		}
//		itr.remove();
//		System.out.println(itr.next());
//		int j = k.size();
//		for(int i = 0; i < j; i ++) {
//			System.out.println(k.poll());
//		}
		
//		byte[] b = Converter.Hex2byte("0000000f");
//		for(int i = 0; i < b.length; i ++) {
//			System.out.println(b[i]);
//		}
//		System.out.println(Integer.toHexString(4095));
		
//		byte[][] b = new byte[2][3];
//		for(int i = 0; i < b.length; i ++) {
//			for(int j = 0; j < b[i].length; j ++) {
//				b[i][j] = (byte)(i + j);
//			}
//		}
//		for(byte[] s : b) {
//			for(int i = 0; i < s.length; i ++) {
//				System.out.println(s[i]);
//			}
//		}
		
//		for(int i = 104857600; i <= 104857600; i ++) {
//			ArrayList<Integer> divisors = Numbera.getDivisors(i);
//			System.out.println(i + ":" +divisors.size());
//		}
		
//		Queue<Byte> bq = new LinkedList<Byte>();
//		for(int i = 0; i < 10485760; i ++) {
//			bq.offer((byte)(i % 250));
//		}
//		long t = System.currentTimeMillis();
//		Byte[] B = bq.toArray(new Byte[0]);
//		System.out.println(B[244].byteValue());
//		System.out.println(System.currentTimeMillis() - t);
//		
//		t = System.currentTimeMillis();
//		Iterator<Byte> itr = bq.iterator();
//		byte b = 0;
//		for(int i = 0; i < 245; i ++) {
//			b = itr.next().byteValue();
//		}
//		System.out.println(b);
//		System.out.println(System.currentTimeMillis() - t);
		
//		int m = 2;
//		double n = 2;
//		System.out.println(m == n);

//		byte[] b = new byte[6];
//		byte[] a = {49, 50, 51, 52, 53, 54, 55, 56, 57, 58};
//		byte[] c = {61, 62, 63, 64};
//		Calculator.fillArray(b, a, 1, 8);
//		System.out.println("b:");
//		for(int i = 0; i < 6; i ++) {
//			System.out.print(b[i] + "|");
//		}
//		System.out.println("\na:");
//		for(int i = 0; i < 10; i ++) {
//			System.out.print(a[i] + "|");
//		}
//		System.out.println("\nc:");
//		for(int i = 0; i < 4; i ++) {
//			System.out.print(c[i] + "|");
//		}
		
//		System.out.println(123 % 130);
		
//		byte[] a = {1,2,3};
//		byte[] b = {3,7,9};
//		byte[] c = Calculator.concatArrays(a, b);
//		byte[] d = Calculator.concatArrays(b, a);
//		for(byte k : c) {
//			System.out.println(k);
//		}
//		System.out.println("");
//		for(byte k : d) {
//			System.out.println(k);
//		}
		
//		byte[] a = new byte[20];
//		for(byte k : a) {
//			System.out.print(k);
//		}
//		System.out.println();
//		Arrays.fill(a, (byte)47);
//		for(byte k : a) {
//			System.out.print(k);
//		}
		
//		byte[][] b0 = {{1,2},{3,4},{5,6}};
//		byte[][] b1 = {{7,8},{9,10}};
//		byte[][] b2 = {{11,12},{13,14},{15,16},{17,18}};
//		ArrayList<ByteQueue> Ph = new ArrayList<ByteQueue>();
//		Ph.add(ByteQueue.getByteQueue(b0));
//		Ph.add(ByteQueue.getByteQueue(b1));
//		Ph.add(ByteQueue.getByteQueue(b2));
//		ArrayList<ByteQueue> PF = new ArrayList<ByteQueue>();
//		int size = 1;
//		for(int i = 0; i < Ph.size(); i ++) {
//			size = Ph.get(i).size() * size;
//		}
//		for(int i = 0; i < size; i ++) {
//			PF.add(ByteQueue.getByteQueue(2));
//		}
//		Fingerprinting.getCombination(Ph, PF, 0, 0);
//		for(int i = 0; i < PF.size(); i ++) {
//			ByteQueue bq = PF.get(i);
//			Queue<Byte> q = bq.getAllData();
//			while(!q.isEmpty()) {
//				System.out.print(q.poll().byteValue() + " ");
//			}
//			System.out.println();
//		}
		
//		double v = 456.123456789;
//		v = Math.round(v * 10000) / 10000d;
//		System.out.println(v);
		
//		int k =1;
//		k = 2147483647 + 1;
//		int w = k;
//		System.out.println(w);
		
//		byte[] d = DataProcessor.readData("data/ccs-2021_Manuscript.pdf");
//		boolean r = DataProcessor.writeData(d, "data/new.pdf");
		
//		String str = "0.3,0.9";
//		String[] str2 = str.split(",");
//		for(int i = 0; i < str2.length; i ++) {
//			System.out.println(Double.parseDouble(str2[i]));
//		}
		
//		HashMap<String, String> hm = new HashMap<String, String>();
//		hm.put("a","A");
//		hm.put("b","B");
//		hm.put("c","C");
//		hm.put("b","BB");
//		for(String key : hm.keySet()) {
//			System.out.println(hm.get(key));
//		}
//		for(Entry<String, String> entry : hm.entrySet()) {
//			System.out.println(entry.getKey());
//		}
		
//		byte[] a = Randomizer.randomBytes(100000);
//		byte[] b = Arrays.copyOf(a, a.length);
//		
//		long ttt = System.currentTimeMillis();
//		System.out.println(Arrays.equals(a, b));
//		ttt = System.currentTimeMillis() - ttt;
//		System.out.println(ttt);
//		
//		ArrayList<Byte> A = new ArrayList<Byte>();
//		for(int i = 0; i < 1000000; i ++) {
//			byte k = (byte)Randomizer.randomInt(0, 256);
//			A.add(k);
//		}
//		ArrayList<Byte> B = new ArrayList<Byte>();
//		for(int i = 0; i < A.size(); i ++) {
//			B.add(A.get(i).byteValue());
//		}
//		ttt = System.currentTimeMillis();
//		System.out.println(A.equals(B));
//		ttt = System.currentTimeMillis() - ttt;
//		System.out.println(ttt);
//		
//		ttt = System.currentTimeMillis();
//		byte[] aa = new byte[A.size()];
//		for(int i = 0; i < A.size(); i ++) {
//			aa[i] = A.get(i).byteValue();
//		}
//		byte[] bb = new byte[B.size()];
//		for(int i = 0; i < B.size(); i ++) {
//			aa[i] = B.get(i).byteValue();
//		}
//		System.out.println(Arrays.equals(a, b));
////		System.out.println(A.equals(B));
//		ttt = System.currentTimeMillis() - ttt;
//		System.out.println(ttt);
		
//		Byte b1 = new Byte((byte)1);
//		Byte b2 = new Byte((byte)2);
//		System.out.println(b1.equals(b2));
		
//		class Code{
//			private byte[] b;
//			public Code(int length) {
//				b = new byte[length];
//			}
//			public Code(byte[] data) {
//				b = data;
//			}
//			public Code() {}
//			public void setData(byte[] data) {
//				this.b = Arrays.copyOf(data, data.length);
//			}
//			public byte[] getData() {
//				return b;
//			}
//			@Override
//			public boolean equals(Object obj) {
//				if(this == obj) return true;
//				if(obj == null) return false;
//				if(getClass() != obj.getClass()) return false;
//				return Arrays.equals(b, ((Code)obj).b);
//			}
//			@Override
//			public int hashCode() {
//				int result = 17;
//				result = 31 * result + byteArrayHashCode(b);
//				return result;
//			}
//			
//			private int byteArrayHashCode(byte[] b) {
//				int result = 17;
//				for(int i = 0; i < b.length; i ++) {
//					result = 31 * result + (int)b[i];
//				}
//				return result;
//			}
//		}
////		long ttt = System.currentTimeMillis();
////		Code code1 = new Code();
////		Code code2 = new Code();
////		code1.b = a;
////		code2.b = b;
////		System.out.println(code1.equals(code2));
////		ttt = System.currentTimeMillis() - ttt;
////		System.out.println(ttt);
//
//		System.out.println("====================");
//		byte[] aa = {1,2,3};
//		byte[] bb = {4,5,6,7,8};
//		
//		HashMap<Code, Code> hm = new HashMap<Code, Code>();
//		for(int i = 0; i < 100; i ++) {
//			Code c1 = new Code();
//			aa[2] = (byte) (Randomizer.randomInt(0, 256));
////			byte[] cc = {1,2,3};
////			byte[] uu = Arrays.copyOf(aa, aa.length);
////			uu[2] = (byte) (Randomizer.randomInt(0, 256));
//			c1.setData(aa);
//			Code c2 = new Code();
//			c2.setData(Randomizer.randomBytes(1));
//			hm.put(c1, c2);
//		}
//		byte[] cc = {9};
//		byte[] dd = {5};
//		Code c3 = new Code(cc);
//		Code c4 = new Code(dd);
//		System.out.println(hm.get(c3));
//		System.out.println(hm.containsKey(c3));
//		System.out.println(hm.containsValue(c4));
//		
//		System.out.println(hm.size());
//		for(Entry<Code, Code> entry : hm.entrySet()) {
//			String key = Converter.byte2Hex(entry.getKey().b);
//			String value = Converter.byte2Hex(entry.getValue().b);
//			System.out.println(key + ":" + value);
//		}
//
//		System.out.println("====================");
//		ArrayList<Code> C = new ArrayList<Code>(hm.keySet());
//		for(Code k : C) {
//			System.out.println(Converter.byte2Hex(k.b));
//		}
		
//		ArrayList<String> a1 = new ArrayList<String>();
//		for(int i = 0; i < 10; i ++) {
//			a1.add(String.valueOf(i));
//		}
//		ArrayList<String> a2 = new ArrayList<String>();
//		for(int i = 0; i < 1000000; i ++) {
//			a2.add(String.valueOf(i+20));
//		}
//		long ttt = System.currentTimeMillis();
//		a1.retainAll(a2);
//		System.out.println(System.currentTimeMillis() - ttt);
//		ttt = System.currentTimeMillis();
//		a2.retainAll(a1);
//		System.out.println(System.currentTimeMillis() - ttt);
//		System.out.println(a1.size() + "<-a1/a2->" + a2.size());
		
//		HashMap<String, String> ss = new HashMap<String, String>();
//		String s = "abc";
//		ss.put(s, "123");
//		s = "abd";
//		ss.put(s, "456");
//		System.out.println(ss.size());
//		System.out.println(ss.get(s));
//		ArrayList<Code> ac = new ArrayList<Code>(hm.values());
//		for(int i = 0; i < ac.size(); i ++) {
//			System.out.println(ac.get(i).b.length);
//		}
//		ttt = System.currentTimeMillis() - ttt;
//		System.out.println(ttt);
//		ttt = System.currentTimeMillis();
//		ttt = System.currentTimeMillis() - ttt;
//		System.out.println(ttt);
//		ttt = System.currentTimeMillis();
//		ttt = System.currentTimeMillis() - ttt;
//		System.out.println(ttt);
//		ttt = System.currentTimeMillis();
//		System.out.println(hm.containsKey(c3));
//		System.out.println(hm.containsValue(c3));
//		System.out.println(hm.remove(c3));
//		ttt = System.currentTimeMillis() - ttt;
//		System.out.println(ttt);
		
//		byte[] s1 = {2,1,-128,3};
//		byte[] s2 = {-2,-1,-128,-3};
////		s1 = Randomizer.randomBytes(10);
////		s2 = Randomizer.randomBytes(10);
//		System.out.println(correlation(s1, s2, 4));
		
//		------------------------------------------------------------
//		/**Begin: Symbol mapping**/
//		System.out.println("-----Symbol mapping-----");
//		HashMap<String, String> keyPairs = ECDSA.getECDSAKeyPairsHash(1);
//		String hSK = "";
//		String hPK = "";
//		for(Entry<String, String> entry : keyPairs.entrySet()) {
//			hPK = entry.getKey();
//			hSK = entry.getValue();
//		}
//		byte[] F = Converter.Hex2byte(hPK);
////		String hSK = ECDSA.getECDSAPublicKeyHash();
////		String hSK = "b2ba6375d8c456dfec7de8a818308829be17009ba08013af538f8d215ce0924d";
//		System.out.println("hPK-" + hPK);
//		System.out.println("hSK-" + hSK);
////		byte[] d = new byte[Randomizer.randomInt(1000, 104857601)];
//		byte[] d = new byte[1048576]; // 52428800 (50MB)
//		for(int i = 0; i < d.length; i ++) {
//			d[i] = (byte)Randomizer.randomInt(0, 256);
////			d[i] = (byte)(i % 128);
////			d[i] = 49;
//		}
//		long time = System.currentTimeMillis();
//		SymbolMap map = SymbolMapping.createSymbolMap(d, hSK, F, 0.3, 1048576, 0, 0, 1024);
//		System.out.println("Run time: " + (System.currentTimeMillis() - time));
//		System.out.println("Theta: " + Configurator.theta);
//		System.out.println("Rho: " + Configurator.rho);
//		System.out.println("Eta: " + Configurator.eta);
//		System.out.println("Phi: " + Configurator.phi);
//		System.out.println("chi: " + Configurator.chi);
//		System.out.println("Distinct symbols: " + map.getSsDistinct().size());
//		System.out.println("Plain codes: " + map.getPs().size());
//		System.out.println("Distinct hidden codes: " + map.getHsDistinct().size());
//		System.out.println("Full size: " + map.getSizeTotal() + "(" + (map.getSizeTotal() / 1024) + "KB)");
//		System.out.println("Public part size: " + map.getSizePublicPart() + "(" + (map.getSizePublicPart() / 1024) + "KB)");
//		System.out.println("Secret part size: " + map.getSizeSecretPart() + "(" + (map.getSizeSecretPart() / 1024) + "KB)");
//		/**End: Symbol mapping**/
//		
//		/**Begin: Data encoding**/
//		System.out.println("-----Data encoding-----");
//		time = System.currentTimeMillis();
//		byte[] dF = DataEncoding.encodeData(d, map, F);
//		System.out.println("Run time: " + (System.currentTimeMillis() - time));
//		System.out.println("|dF|: " + dF.length);
////		for(int i = 0; i < dF.length; i ++) {
////			System.out.print(dF[i]);
////		}
////		System.out.println("");
////		try {
////			System.out.println(new String(dF,"UTF-8"));
////		}
////		catch(Exception ex) {
////			ex.printStackTrace();
////		}
//		/**End: Data encoding**/
//
//		/**Break dF by deleting several bytes.**/
////		byte[] brokenDF = new byte[2 * dF.length / 3 + 1];
////		for(int i = 0; 3 * i < dF.length - 1; i ++) {
////			brokenDF[2 * i] = dF[3 * i];
////			brokenDF[2 * i + 1] = dF[3 * i + 1]; 
////		}
//		/**Break dF by crossing every two bytes.**/
////		byte[] brokenDF = new byte[dF.length];
////		for(int i = 0; i < brokenDF.length; i ++) {
////			brokenDF[i] = dF[i + (int)Math.pow(-1, i)];
////		}
//		/**Fake dF by creating a different dF.**/
//		byte[] brokenDF = new byte[dF.length];
//		for(int i = 0; i < brokenDF.length; i ++) {
//			brokenDF[i] = (byte)Randomizer.randomInt(0, 256);
//		}
////		for(int i = brokenDF.length / 2; i < brokenDF.length; i ++) {
////			brokenDF[i] = (byte)Randomizer.randomInt(0, 128);
////		}
//		/**Break dF by extending much more bytes.**/
////		byte[] ext = new byte[dF.length];
////		for(int i = 0; i < ext.length; i ++) {
////			ext[i] = (byte)Randomizer.randomInt(0, 256);
////		}
////		byte[] brokenDF = Calculator.concatArrays(dF, ext);
//		/**Break dF by insertion extending much more bytes.**/
////		byte[] brokenDF = new byte[dF.length * 2];
////		for(int i = 0; i < dF.length; i ++) {
//////			brokenDF[2 * i] = (byte)Randomizer.randomInt(0, 256);
////			brokenDF[2 * i] = dF[i]; //Duplicate insertion.
////			brokenDF[2 * i + 1] = dF[i];
////		}
//		/**Break dF by cross extending much more bytes.**/
////		byte[] brokenDF = new byte[dF.length];
////		for(int i = 0; i < brokenDF.length; i ++) {
////			brokenDF[i] = dF[i + (int)Math.pow(-1, i)];
////		}
////		byte[] ext = new byte[dF.length];
////		for(int i = 0; i < ext.length; i ++) {
////			ext[i] = (byte)Randomizer.randomInt(0, 256);
////		}
////		brokenDF = Calculator.concatArrays(brokenDF, ext);
//		/**Fake dF and extend much more bytes.**/
////		byte[] brokenDF = new byte[dF.length];
////		for(int i = 0; i < brokenDF.length / 2; i ++) {
////			brokenDF[i] = (byte)Randomizer.randomInt(0, 256);
////		}
////		for(int i = brokenDF.length / 2; i < brokenDF.length; i ++) {
////			brokenDF[i] = (byte)Randomizer.randomInt(0, 128);
////		}
////		byte[] ext = new byte[dF.length];
////		for(int i = 0; i < ext.length; i ++) {
////			ext[i] = (byte)Randomizer.randomInt(0, 256);
////		}
////		brokenDF = Calculator.concatArrays(brokenDF, ext);
//		/**Break dF by negating it.**/
////		byte[] brokenDF = new byte[dF.length];
////		for(int i = 0; i < brokenDF.length; i ++) {
////			brokenDF[i] = (byte)(((int)dF[i] * (int)Math.pow(-1, i)));
////		}
//		/**Break dF by reverse it.**/
////		byte[] brokenDF = new byte[dF.length];
////		for(int i = 0; i < dF.length; i ++) {
////			brokenDF[i] =dF[dF.length - 1 - i];
////		}
//		
//		/**Begin: Fingerprinting**/
//		System.out.println("-----Fingerprinting-----");
//		time = System.currentTimeMillis();
////		boolean fpResult = Fingerprinting.fingerprinting(dF, map, F, 0.5, 20, 1048576, 0.6);
//		boolean fpResult = Fingerprinting.fingerprinting(dF, map, F, 0.6, 12, 1048576, 0.6);
//		System.out.println("Run time: " + (System.currentTimeMillis() - time));
//		System.out.println("PhiStar: " + Configurator.phiStar);
//		System.out.println("Phi x Epsilon: " + (Configurator.phi * 0.6));
//		System.out.println("Mean: " + Configurator.mean);
//		System.out.println("Sigma: 12");
//		System.out.println("Fingerprinting result: " + fpResult);
//		/**End: Fingerprinting**/
//		
//		/**Begin: Data decoding**/
////		System.out.println("-----Data decoding-----");
////		time = System.currentTimeMillis();
////		byte[] dx = DataDecoding.decodeData(dF, map);
////		System.out.println("Run time: " + (System.currentTimeMillis() - time));
////		System.out.println("|d|: " + dx.length);
////		boolean check = true;
////		for(int i = 0; i < d.length; i ++) {
////			if(d[i] != dx[i]) {
////				check = false;
////				break;
////			}
////		}
////		System.out.println("decoding result: " + check);
//		/**End: Data decoding**/
	}
	
	public static double correlation(byte[] s1, byte[] s2, int n) {
		double sum_s12 = 0.0;
	    double sum_s1  = 0.0;
	    double sum_s2  = 0.0;
	    double sum_s1s1 = 0.0; //s1^2
	    double sum_s2s2 = 0.0; //s2^2
	    double pxy = 0.0;
	    double temp1 = 0.0;
	    double temp2 = 0.0;
	    
	    if(s1 == null || s2 == null || n <= 0) {
	    	return -10;
	    }
	    
	    for(int i = 0;i < n; i++)
	    {
	    	sum_s12 += (int)s1[i] * (int)s2[i];
	        sum_s1 += (int)s1[i];
	        sum_s2 += (int)s2[i];
	        sum_s1s1 += (int)s1[i] * (int)s1[i]; 
	        sum_s2s2 += (int)s2[i] * (int)s2[i]; 
	    }
	    
	    temp1 = n * sum_s1s1 - sum_s1 * sum_s1;
	    temp2 = n * sum_s2s2 - sum_s2 * sum_s2;
	    
	    if((temp1 > -0.0001d && temp1 < 0.0001d) || (temp2 > -0.0001d && temp2 < 0.0001d) || (temp1 * temp2 <= 0)){
	    	return -10;
        }
	    
	    pxy = (n * sum_s12 - sum_s1 * sum_s2) / Math.sqrt(temp1 * temp2);
	    
	    return pxy;
	}
}
