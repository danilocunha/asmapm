package asmapm.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class APMConfig {
	private static APMConfig instance;

	private Properties props = null;

	public APMConfig() throws Exception {
		super();

		props = new Properties();
		String propFileName = "asmapm.properties";

		InputStream inputStream;
		try {
			System.out.println(getJarDirectory() + "/" + propFileName);
			File f = new java.io.File(
					getJarDirectory() + "/" + propFileName);
			inputStream = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException("property file '" + propFileName
					+ "' not found in the classpath");
		}

		props.load(inputStream);
	}

	public static synchronized APMConfig getInstance() {
		if (instance == null)

			try {
				instance = new APMConfig();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return instance;
	}

	public String getValue(String propKey) {
		return this.props.getProperty(propKey);
	}
	
	private String getJarDirectory() {

		String urlString = "";

		URL url;
		try {
			urlString = ClassLoader.getSystemClassLoader()
					.getResource("asmapm/Agent.class").toString();

			urlString = urlString.substring(urlString.indexOf("file:"),
					urlString.indexOf('!'));
			url = new URL(urlString);
			File file = new File(url.toURI());
			urlString = file.getParent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return urlString;

	}

}
