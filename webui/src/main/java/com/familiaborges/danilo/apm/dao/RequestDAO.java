package com.familiaborges.danilo.apm.dao;

import com.familiaborges.danilo.apm.dto.Execution;
import com.familiaborges.danilo.apm.dto.Request;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;

public class RequestDAO extends GenericDAO {

	public void save(Request request) {
		try {
		getEntityManager().getTransaction().begin();
		getEntityManager().persist(request);
		getEntityManager().getTransaction().commit();
		getEntityManager().close();
		} catch(Exception e) {			
			e.printStackTrace();
		}
	}
	
	public void remove(Request request) {
		try {
		getEntityManager().getTransaction().begin();
		request = getEntityManager().getReference(Request.class, request.getId());
		getEntityManager().remove(request);
		getEntityManager().getTransaction().commit();
		getEntityManager().close();
		} catch(Exception e) {			
			e.printStackTrace();
		}
	}
	
	public JPAContainer<Request> getJPAContainer() {
		JPAContainer<Request> requests = JPAContainerFactory.make(Request.class, getEntityManager());
		//executions.addContainerProperty("idExecution", Long.class, null);
		return requests;
	}
	
	public Request findById(Object id) {
		return getEntityManager().find(Request.class, id);
	}
}
