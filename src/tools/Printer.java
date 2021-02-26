/**
 * Functions related to printing reports of experimental results.
 */
package tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * @author Liang Wang
 *
 */
public class Printer {
	/**
	 * Prints the content of an array of HashMaps onto the hard disk with the suffix of ".csv".
	 * @param fileName relative path and file name (without any suffix) of the result file.
	 * @param hm the content to be printed.
	 */
	public static void printHashMap(String fileName, ArrayList<HashMap<String, String>> ahm) {
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			File f = new File(fileName + ".csv");
			boolean isNew = false;
			if (!f.exists()) {
				f.createNewFile();
				isNew = true;
			}
			fw = new FileWriter(f, true);
			pw = new PrintWriter(fw);
			if(isNew) {
				//Print the header
				String header = "";
				for(Entry<String, String> entry : ahm.get(0).entrySet()) {
					header = header + entry.getKey() + ",";
				}
				header = header.substring(0, header.length() - 1);
				pw.println(header);	
			}
			//Print items
			for(int i = 0; i < ahm.size(); i ++) {
				String item = "";
				for(Entry<String, String> entry : ahm.get(i).entrySet()) {
					item = item + entry.getValue() + ",";
				}
				item = item.substring(0, item.length() - 1);
				pw.println(item);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				pw.flush();
				fw.flush();
				pw.close();
				fw.close();
			}catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
