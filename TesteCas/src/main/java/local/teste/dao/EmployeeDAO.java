package local.teste.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import local.teste.entity.Employee;
import local.teste.monitor.TaskMonitor;
import local.teste.util.PersistenceManager;

public class EmployeeDAO {
	
	
	protected EntityManager em;

	public EmployeeDAO() {
		/*entityManager = getEntityManager();*/
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		
		if (em == null) {
			//System.out.println("Criando Entity Manager");
			em = emf.createEntityManager();
		}

		return em;
	}

	public List<Employee> listaTeste() {
		TaskMonitor.getInstance().addCall(Thread.currentThread().getId(),
				Thread.currentThread().getStackTrace()[1].getMethodName());
		// timerA();
		// timerB();
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
		// timerA();
		
		//Stored procedure with delay for tests
		//Query query = getEntityManager().createNativeQuery("call employees.sp_employees()", Employee.class);
		
		Query query = getEntityManager().createQuery(
				"FROM " + Employee.class.getName());
		query.setMaxResults(10);

		List<Employee> result = query.getResultList();
		getEntityManager().close();
		/*
		 * try { System.out.println(ObjectSizeFetcher.sizeOf(result)/1024/1024);
		 * } catch (IllegalAccessException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */

		return result;
	}

	public void testefunc(String s1, String s2) {
		System.out.println("String 1:  " + s1 + " String s2:" + s2);
	}

}
