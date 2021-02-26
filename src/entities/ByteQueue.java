/**
 * ByteQueue is an implementation of Queue on 2D byte arrays.
 */
package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Liang Wang
 * <br><br>The instances of ByteQueue can only be get from the method getByteQueue(byte[][]).
 */
public class ByteQueue {
	private Queue<Byte> bq;
	private int queueLength; // The length of the queue.
	private int elementLength; // the length of one element.
	/**
	 * The instances of ByteQueue can only be get from the method getByteQueue(byte[][]).
	 */
	public ByteQueue() {
		bq = new LinkedList<Byte>();
		queueLength = 0;
		elementLength = 0;
	}
	
	public ByteQueue(int queueLength, int elementLength) {
		bq = new LinkedList<Byte>();
		this.queueLength = queueLength;
		this.elementLength = elementLength;
	}
	/**
	 * Fills the queue with a 2D byte array b. This process will set the queueLenth and elementLength of this queue.
	 * @param b the 2D byte array to be put into the queue.
	 * @return Returns true if the filling succeeds, or otherwise false.
	 */
	public boolean fill(byte[][] b) {
		if(b == null) { //Validation.
			return false;
		}
		for(int i = 0; i < b.length; i ++) {
			for(int j = 0; j < b[i].length; j ++) {
				bq.offer(b[i][j]);
			}
		}
		queueLength = b.length;
		elementLength = b[0].length;
		return true;
	}
	/**
	 * Fills the queue with a byte array b. This process will not set any length of this queue.
	 * @param b the given byte array.
	 * @return Returns true if the filling succeeds, or otherwise false.
	 */
	public boolean fill(byte[] b) {
		if(b == null) {//validation
			return false;
		}
		for(int i = 0; i < b.length; i ++) {
			bq.offer(b[i]);
		}
		return true;
	}
	/**
	 * Puts a new byte array into this queue.
	 * @param b the byte array to be put into the queue.
	 * @return Returns true if the offering succeeds, or otherwise false.
	 */
	public boolean offer(byte[] b) {
		if(b == null) { //Validation.
			return false;
		}
		if(b.length != elementLength) { //Validation
			return false;
		}
		for(int i = 0; i < b.length; i ++) {
			bq.offer(b[i]);
		}
		queueLength ++;
		return true;
	}
	/**
	 * Takes an element from the head of this queue.
	 * @return The head element of this queue. Returns null if the queue is empty.
	 */
	public byte[] poll() {
		if(bq.isEmpty()) { //validation
			return null;
		}
		byte[] b = new byte[elementLength];
		for(int i = 0; i < elementLength; i ++) {
			b[i] = bq.poll();
		}
		queueLength --;
		return b;
	}
	/**
	 * Reads an element by pointing a precise position.
	 * @param position the position to read the element.
	 * @return Returns the required element with a byte array.
	 */
	public byte[] element(int position) {
		if(position >= queueLength) { // validation
			return null;
		}
		Iterator<Byte> itr = bq.iterator();
		for(int i = 0; i < position * elementLength; i ++) {
			itr.next();
		}
		byte[] b = new byte[elementLength];
		for(int i = 0; i < elementLength; i ++) {
			b[i] = itr.next().byteValue();
		}
		return b;
	}
	/**
	 * Reads and removes an element at a given position.
	 * @param position the position to read and remove an element.
	 * @return Returns the removed element with a byte array.
	 */
	public byte[] remove(int position) {
		if(position >= queueLength) { // validation
			return null;
		}
		Iterator<Byte> itr = bq.iterator();
		for(int i = 0; i < position * elementLength; i ++) {
			itr.next();
		}
		byte[] b = new byte[elementLength];
		for(int i = 0; i < elementLength; i ++) {
			b[i] = itr.next().byteValue();
			itr.remove();
		}
		queueLength --;
		return b;
	}
	/**
	 * Examines whether the queue contains a given element (a byte array).
	 * @param e the given byte array.
	 * @return Returns true if the queue does contain e.
	 */
	public boolean contains(byte[] e) {
		if(e == null) { //validation
			return false;
		}
		if(e.length != elementLength) {
			return false;
		}
		Iterator<Byte> itr = bq.iterator();
		for(int i = 0; i < queueLength; i ++) {
			byte[] b = new byte[elementLength];
			for(int j = 0; j < elementLength; j ++) {
				b[j] = itr.next().byteValue();
			}
			if(Arrays.equals(b, e)) {
				return true;
			}
		}
		return false;
	}
	
//	public boolean mayContains(byte[] e) {
//		
//	}
	/**
	 * @return Returns the number of elements of this queue.
	 */
	public int size() {
		return queueLength;
	}
	/**
	 * @return Returns the length of the elements of this queue.
	 */
	public int elementLength() {
		return elementLength;
	}
	/**
	 * Returns a new ByteQueue with a given 2d byte array.
	 * @param b the given 2d byte array.
	 * @return Returns a new ByteQueue. Returns null if b is null.
	 */
	public static ByteQueue getByteQueue(byte[][] b) {
		if(b == null) { //validation
			return null;
		}
		ByteQueue bq = new ByteQueue();
		bq.fill(b);
		return bq;
	}
	/**
	 * Returns a new ByteQueue with a given byte array, the length of the queue, and the length of an element.
	 * @param b the given byte array.
	 * @param queueLength
	 * @param elementLength
	 * @return Returns the new ByteQueue and null if fails.
	 */
	public static ByteQueue getByteQueue(byte[] b, int queueLength, int elementLength) {
		if(b == null) { //validation
			return null;
		}
		if(queueLength < 1 || elementLength < 1) { // validation
			return null;
		}
		ByteQueue bq = new ByteQueue(queueLength, elementLength);
		bq.fill(b);
		return bq;
	}
	/**
	 * Returns an empty ByteQueue with the given length of element.
	 * @param elementLength
	 * @return Returns the empty ByteQueue with the given length of element.
	 */
	public static ByteQueue getByteQueue(int elementLength) {
		if(elementLength < 1) {//validation
			return null;
		}
		ByteQueue bq = new ByteQueue(0, elementLength);
		return bq;
	}
	/**
	 * Returns a new ByteQueue from an ArrayList<Code>
	 * @param C the ArrayList<code>
	 */
	public static ByteQueue getByteQueue(ArrayList<Code> C) {
		if(C == null) {
			return null;
		}
		if(C.isEmpty()) {
			return null;
		}
		ByteQueue bq = new ByteQueue(0, C.get(0).length());
		for(int i = 0; i < C.size(); i ++) {
			bq.offer(C.get(i).getData());
		}
		return bq;
	}
	/**
	 * Gets all data in this queue in sequence.<br>This will break the ByteQueue.
	 * @return Returns a byte array containing all the data in this ByteQueue.
	 */
	public byte[] getAllData()	{
		if(bq.isEmpty()) {
			return null;
		}
		byte[] b = new byte[bq.size()];
		int index = 0;
		while(!bq.isEmpty()) {
			b[index] = bq.poll().byteValue();
			index ++;
		}
		return b;
	}
}
