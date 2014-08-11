package com.familiaborges.danilo.apm.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.familiaborges.danilo.apm.util.PersistenceManager;

public class GenericDAO {
	
	private EntityManager em;
	
	protected EntityManager getEntityManager() {
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		
		if (em == null) {			
			em = emf.createEntityManager();
		}
		return em;
	}

}
