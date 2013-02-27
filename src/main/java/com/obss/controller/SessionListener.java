package com.obss.controller;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.obss.util.HibernateUtil;

@Configuration
@EnableWebMvc
public class SessionListener implements HttpSessionListener {

	protected static Logger logger = Logger.getLogger("sessionListener");

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		
		HibernateUtil.turnOn();

		logger.debug("sessionCreated");

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {

		HibernateUtil.shutdown();

		logger.debug("sessionDestroyed");

	}

}
