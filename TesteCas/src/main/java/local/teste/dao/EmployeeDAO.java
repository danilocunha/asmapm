package local.teste.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.hibernate.Criteria;

import local.teste.entity.Employee;
import local.teste.monitor.TaskMonitor;
import local.teste.util.ObjectSizeFetcher;

public class EmployeeDAO {
	protected EntityManager entityManager;

	public EmployeeDAO() {
		entityManager = getEntityManager();
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory("EmployeePU");
		
		if (entityManager == null) {
			entityManager = factory.createEntityManager();
		}

		return entityManager;
	}

	public List<Employee> listaTeste() {
		TaskMonitor.getInstance().addCall(Thread.currentThread().getId(), Thread.currentThread().getStackTrace()[1].getMethodName());
		//timerA();
		//timerB();
		List<Employee> result = findAll();
		TaskMonitor.getInstance().removeCall(Thread.currentThread().getId());
		return result;
		
	}
	

	public void timerA() {
		try {
			Thread.sleep(2500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
public void timerB() {
	try {
		Thread.sleep(500);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	@SuppressWarnings("unchecked")
	public List<Employee> findAll() {
		
		Query query = entityManager.createQuery("FROM "
				+ Employee.class.getName());
		query.setMaxResults(100);
		
		List<Employee> result = query.getResultList();
		
		/*
		 * try { System.out.println(ObjectSizeFetcher.sizeOf(result)/1024/1024);
		 * } catch (IllegalAccessException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */
		
		return result;
	}

}
