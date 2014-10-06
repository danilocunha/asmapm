package local.teste.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EnquetePM {
	
	public static final boolean DEBUG = true;
	
	private static final EnquetePM singleton = new EnquetePM();

	protected EntityManagerFactory emf;

	public static EnquetePM getInstance() {

		return singleton;
	}

	private EnquetePM() {
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
				System.out.println("*** Enquete Persistence Manager finished at "
						+ new java.util.Date());
		}
	}

	protected void createEntityManagerFactory() {

		this.emf = Persistence.createEntityManagerFactory("EmployeePU");
		if (DEBUG)
			System.out.println("*** Enquete Persistence Manager started at "
					+ new java.util.Date());
	}
}
