package com.doshiland.fx4web.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory;

	private static final Log log = LogFactory.getLog(HibernateUtil.class);

	public static Session getCurrentSession() {
		return getSessionFactory().getCurrentSession();
	}

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			buildSessionFactory();
		}
		return sessionFactory;
	}

	private static void buildSessionFactory() {
		log.debug("Building and configuring SessionFactory: BEGIN");
		Configuration cfg = new Configuration();
		cfg.configure();
		sessionFactory = cfg.buildSessionFactory();
		log.debug("Building and configuring SessionFactory: END");
	}
}
