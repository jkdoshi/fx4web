package com.doshiland.fx4web.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.doshiland.fx4web.hibernate.HibernateUtil;

public class Util {
	/**
	 * Converts a given collection into a map assuming each element of the
	 * collection is an Object array with at least 2 elements. The 0'th element
	 * of the Object[] becomes the key in the resulting map and the 1'th element
	 * becomes the value. One of the many uses of this would be in JSF to create
	 * f:selectItems from lists returned from database queries.
	 * 
	 * @param collection
	 *            collection of Object[]
	 * @return
	 */
	public static Map collection2map(Collection collection) {
		Map map = new LinkedHashMap();
		for (Iterator itr = collection.iterator(); itr.hasNext();) {
			Object[] tuple = (Object[]) itr.next();
			assert tuple.length >= 2;
			map.put(tuple[0], tuple[1]);
		}
		return map;
	}

	public static Map query2map(String queryName) {
		Session session = HibernateUtil.getCurrentSession();
		List rows = session.getNamedQuery(queryName).list();
		return collection2map(rows);
	}
}
