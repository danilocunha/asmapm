package com.familiaborges.danilo.apm.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.familiaborges.danilo.apm.collector.RabbitMQCollector;

@WebListener
public class WebUIServletContextListener implements ServletContextListener {

	private RabbitMQCollector collector = new RabbitMQCollector();
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener destroyed");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Thread threadCollector = new Thread(collector);
		threadCollector.start();
	}

}
