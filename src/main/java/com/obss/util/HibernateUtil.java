package com.obss.util;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.obss.config.HibernateConfiguration;

public class HibernateUtil {

	protected static Logger logger = Logger.getLogger("hibernateUtil");

	private static final SessionFactory sessionFactory = buildSessionFactory();

	private static SessionFactory buildSessionFactory() {
		try {
			return new Configuration().configure().buildSessionFactory(HibernateConfiguration.hibernateConfig());			
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			logger.error("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static void turnOn() {

		logger.debug("turning on Hibernate session");

		// Create Database connection
		getSessionFactory().openSession();

	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void shutdown() {

		logger.debug("shuting down Hibernate session");

		// Close caches and connection pools
		getSessionFactory().close();

	}
}
