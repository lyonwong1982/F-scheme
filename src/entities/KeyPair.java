/**
 * This entity is used to store a key-pair of two objects.
 */
package entities;

/**
 * @author Liang Wang
 *
 */
public class KeyPair <K, V> {
	public K key;
	public V value;
	public KeyPair() {
		key = null;
		value = null;
	}
	public KeyPair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	public K getKey() {return key;}
	public V getValue() {return value;}
	public void put(K key, V value) {
		this.key = key;
		this.value = value;
	}
}
