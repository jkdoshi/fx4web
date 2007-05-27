package com.doshiland.fx4web.collections;

import java.util.Iterator;
import java.util.Map;

/**
 * An extension to the Map interface that adds List semantics so that the same
 * object can be used as a List as well a Map. This is very useful for systems
 * that require List semantics for some parts of the system while the rest of
 * the code finds it much easier to use the Map semantics.
 * 
 * @author <a href='mailto:jitesh@doshiland.com'>Jitesh Doshi</a>
 */
public interface ListMap extends Map {
	/**
	 * Implementations should determine the Map key for the given object and
	 * return it. This is used while adding elements to the List, but internally
	 * using the Map for storage.
	 * 
	 * @param obj
	 *            object to be added to ListMap
	 * 
	 * @return value of the key at which the object should be added
	 */
	public abstract Object getKey(Object obj);

	/**
	 * Adds an object to the ListMap using the key determined by the {@link
	 * #getKey(Object)} method.
	 * 
	 * @param obj
	 *            object to be added to the ListMap
	 */
	public void add(Object obj);

	/**
	 * Returns the element (value) present at this given index in this ordered
	 * ListMap.
	 * 
	 * @param i
	 *            position index
	 * 
	 * @return element (value) at that position
	 */
	public Object get(int i);

	/**
	 * Returns an itertor over the values in this ListMap.
	 * 
	 * @return value iterator
	 */
	public Iterator iterator();
}
