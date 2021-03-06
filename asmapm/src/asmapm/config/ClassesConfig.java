package asmapm.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

public class ClassesConfig {
	
	private Logger log = Logger.getLogger(this.getClass().getName());
	
	private static ClassesConfig instance = null;
	
	protected List<String> classesToSkip = new ArrayList<String>();
	
	protected List<String> classesToInclude = new ArrayList<String>();
	
	protected List<String> jdbcClasses = new ArrayList<String>();
	
	protected List<String> preparedStatementClasses = new ArrayList<String>();
	
	protected List<String> filterClasses = new ArrayList<String>();

	public static ClassesConfig getInstance() {
	      if(instance == null) {
	         instance = new ClassesConfig();
	      }
	      return instance;
	   }
	
	public ClassesConfig() {
		super();
		loadJdbcClasses();
		loadClassesToSkip();
		loadClassesToInclude();
		loadPreparedStatementClasses();
	}
	
	private void loadJdbcClasses() {
		jdbcClasses.add("com.mysql");
		jdbcClasses.add("net.sourceforge.jtds");
		jdbcClasses.add("com.microsoft.sqlserver");
	}
	
	private void loadPreparedStatementClasses() {
		preparedStatementClasses.add("com.mysql.jdbc.PreparedStatement");
		preparedStatementClasses.add("net.sourceforge.jtds.jdbc.JtdsPreparedStatement");
		preparedStatementClasses.add("com.microsoft.sqlserver.jdbc.SQLServerPreparedStatement");
	}
	
	public List<String> getPreparedStatementClasses() {
		return this.preparedStatementClasses;
	}
	
	public boolean isJdbcClass(String dotClassName) {
		Iterator<String> ite = jdbcClasses.iterator();
		while(ite.hasNext()) {
			if(dotClassName.startsWith(ite.next())){
				return true;
			}
		}
		return false;
	}
	
	public List<String> getJdbcClasses() {
		return this.jdbcClasses;
	}
	
	private void loadClassesToSkip() {
		classesToSkip.add("apm.");
		classesToSkip.add("asmapm.");
		classesToSkip.add("javassist.");
		classesToSkip.add("javax.");
		classesToSkip.add("java.");
		classesToSkip.add("sun.");
		classesToSkip.add("com.sun.");
		classesToSkip.add("org.");
		classesToSkip.add("com.ctc.");
		classesToSkip.add("io.undertow.");
		classesToSkip.add("__redirected.");
		classesToSkip.add("com.rabbitmq.");
		classesToSkip.add("net.sf.");
		
		classesToSkip.add("org.xml.");
		classesToSkip.add("org.w3c.");
		classesToSkip.add("antlr.");
		classesToSkip.add("sessionmon.");
		classesToSkip.add("apple.");
		classesToSkip.add("com.arjuna");
		classesToSkip.add("com.mysql");
		classesToSkip.add("net.sourceforge.jtds");
		classesToSkip.add("com.fastsearch");
		classesToSkip.add("gnu");
		classesToSkip.add("EDU");
		classesToSkip.add("bsh");
		classesToSkip.add("com.microsoft");
	}
	
	public List<String> getClassesToSkip() {
		return this.classesToSkip;
	}
	
	private void loadClassesToInclude() {
		classesToInclude.add("org.apache.wicket.protocol.http.WicketFilter");
		classesToInclude.add("com.mysql.jdbc.PreparedStatement");
		classesToInclude.add("net.sourceforge.jtds.jdbc.JtdsPreparedStatement");
		classesToInclude.add("org.apache.commons.httpclient.HttpClient");
		
	}
	
	public List<String> getClassesToInclude() {
		return this.classesToInclude;
	}
	
	public void addFilterClass(String className) {
		
		this.filterClasses.add(className);
	}
	
	public boolean isFilterClass(String className) {
		return this.filterClasses.contains(className);
	}
	
}
