package com.doshiland.fx4web.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A class that adds list semantics (<code>add</code>, <code>get</code>
 * and <code>iterator</code> methods) to a HashMap. The way it achieves that
 * is to make derived classes implement the {@link #getKey(Object)} method that
 * determines the Map key for a given element object and then uses it to
 * determine the slot to map this element to.
 * 
 * @author <a href='mailto:jitesh@doshiland.com'>Jitesh Doshi</a>
 */
public abstract class ListMapBase extends HashMap implements ListMap {
	/** List of keys, maintained in order to preserve insertion order. */
	private List keyList = new ArrayList();

	public ListMapBase() {
		super();
	}

	public ListMapBase(Map map) {
		super(map);
		keyList.addAll(map.keySet());
	}

	public ListMapBase(List list) {
		for (Object elem : list) {
			add(elem);
		}
	}

	/**
	 * Derived classes should implement this method such that it returns the key
	 * from an element object being added to the Map using the List semantics.
	 * 
	 * @param obj
	 *            element object being added to Map.
	 * 
	 * @return key that should be used to put this element into the Map
	 */
	public abstract Object getKey(Object obj);

	/**
	 * Adds an object to the Map using the key determined by the {@link
	 * #getKey(Object)} method.
	 * 
	 * @param obj
	 *            object to be added to the Map
	 */
	public void add(Object obj) {
		Object key = getKey(obj);
		put(key, obj);
	}

	/**
	 * Clears the objects from the HashMap and the ArrayList
	 */
	public void clear() {
		keyList.clear();
		super.clear();
	}

	public Object clone() {
		ListMapBase result = (ListMapBase) super.clone();
		result.keyList = (ArrayList) ((ArrayList) this.keyList).clone();

		return result;
	}

	/**
	 * Returns the element (value) present at this given index in this ordered
	 * Map.
	 * 
	 * @param i
	 *            position index
	 * 
	 * @return element (value) at that position
	 */
	public Object get(int i) {
		Object key = keyList.get(i);

		if (containsKey(key)) {
			return get(key);
		} else {
			keyList.remove(key);

			return null;
		}
	}

	/**
	 * Returns an itertor over the (ordered) keys in this Map.
	 * 
	 * @return value iterator
	 */
	public Iterator iterator() {
		return keyList.iterator();
	}

	public Object put(Object key, Object value) {
		if (!containsKey(key)) {
			keyList.add(key);
		}

		return super.put(key, value);
	}

	public Object remove(Object key) {
		keyList.remove(key);

		return super.remove(key);
	}
}
