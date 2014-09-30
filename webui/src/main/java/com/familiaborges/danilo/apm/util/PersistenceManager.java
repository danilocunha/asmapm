package com.familiaborges.danilo.apm.util;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {

	public static final boolean DEBUG = true;

	private static final PersistenceManager singleton = new PersistenceManager();

	protected EntityManagerFactory emf;

	public static PersistenceManager getInstance() {

		return singleton;
	}

	private PersistenceManager() {
	}

	public EntityManagerFactory getEntityManagerFactory() {

		if (emf == null)
			createEntityManagerFactory();
		return emf;
	}

	public void closeEntityManagerFactory() {

		if (emf != null) {
			emf.close();
			emf = null;
			if (DEBUG)
				System.out.println("n*** Persistence finished at "
						+ new java.util.Date());
		}
	}

	protected void createEntityManagerFactory() {
		
		Map<String, Object> props = new HashMap<String, Object>();
        props.put("db.host",WebUIConfig.getInstance().getValue("db.host"));
        props.put("db.port",WebUIConfig.getInstance().getValue("db.port"));
        props.put("db.name",WebUIConfig.getInstance().getValue("db.name"));
        props.put("db.user",WebUIConfig.getInstance().getValue("db.user"));
        props.put("db.password",WebUIConfig.getInstance().getValue("db.password"));
        
		this.emf = Persistence.createEntityManagerFactory("ApmPU", props);
		if (DEBUG)
			System.out.println("n*** Persistence started at "
					+ new java.util.Date());
	}
}
