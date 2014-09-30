package com.familiaborges.danilo.apm.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WebUIConfig {

	private static WebUIConfig instance;

	private Properties props = null;

	private WebUIConfig() throws IOException {
		
		props = new Properties();
		String propFileName = "config.properties";

		InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(propFileName);

		if (inputStream == null) {
			throw new FileNotFoundException("property file '" + propFileName
					+ "' not found in the classpath");
		}
		props.load(inputStream);
		String current = new java.io.File( "." ).getCanonicalPath();
	    //System.out.println("Current dir:"  +current);
		//System.out.println(getClass().getResource(name));
	}

	public static synchronized WebUIConfig getInstance() {
		if (instance == null)

			try {
				instance = new WebUIConfig();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return instance;
	}

	public String getValue(String propKey) {
		return this.props.getProperty(propKey);
	}

}
