package mouse.movement;

import java.util.AbstractMap;

/*
 * This class represent a Object with two elements.
 */
@SuppressWarnings("serial")
public class SortedMapSimpleEntry<K, V extends Comparable<V>> extends AbstractMap.SimpleEntry<K, V> implements Comparable<SortedMapSimpleEntry<K, V>> {
	
	public SortedMapSimpleEntry(K arg0, V arg1) {
		super(arg0, arg1);
	}

	public int compareTo(SortedMapSimpleEntry<K, V> other) {
		return super.getValue().compareTo(other.getValue());
	}
	
	public String toString() {
		return "Key: " + super.getKey() + "\t Value: " + super.getValue();
	}

}
