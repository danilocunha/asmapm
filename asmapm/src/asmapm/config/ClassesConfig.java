package asmapm.config;

import java.util.ArrayList;
import java.util.List;

public class ClassesConfig {
	
	private static ClassesConfig instance = null;
	
	protected List<String> classesToSkip = new ArrayList<String>();
	
	protected List<String> classesToInclude = new ArrayList<String>();
	
	protected List<String> jdbcClasses = new ArrayList<String>();

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
	}
	
	private void loadJdbcClasses() {
		jdbcClasses.add("com.mysql");
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
		
		classesToSkip.add("org.xml.");
		classesToSkip.add("org.w3c.");
		classesToSkip.add("antlr.");
		classesToSkip.add("sessionmon.");
		classesToSkip.add("apple.");
		classesToSkip.add("com.arjuna");
		classesToSkip.add("com.mysql");
	}
	
	public List<String> getClassesToSkip() {
		return this.classesToSkip;
	}
	
	private void loadClassesToInclude() {
		classesToInclude.add("org.apache.wicket.protocol.http.WicketFilter");
		classesToInclude.add("com.mysql.jdbc.PreparedStatement");
		
	}
	
	public List<String> getClassesToInclude() {
		return this.classesToInclude;
	}
	
}
