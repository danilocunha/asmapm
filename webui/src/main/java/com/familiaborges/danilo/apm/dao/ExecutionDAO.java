package com.familiaborges.danilo.apm.dao;

import com.familiaborges.danilo.apm.dto.Execution;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;

public class ExecutionDAO extends GenericDAO {

	public void save(Execution execution) {
		try {
		getEntityManager().getTransaction().begin();
		getEntityManager().persist(execution);
		getEntityManager().getTransaction().commit();
		getEntityManager().close();
		} catch(Exception e) {
			System.out.println(execution.getDuration());
			e.printStackTrace();
		}
	}
	
	public JPAContainer<Execution> getJPAContainer() {
		JPAContainer<Execution> executions = JPAContainerFactory.make(Execution.class, getEntityManager());
		//executions.addContainerProperty("idExecution", Long.class, null);
		return executions;
	}
	
	public Execution findById(Object idExecution) {
		return getEntityManager().find(Execution.class, idExecution);
	}
}
