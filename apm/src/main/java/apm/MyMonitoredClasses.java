package apm;

import java.util.ArrayList;
import java.util.List;

public class MyMonitoredClasses {
	
	private static List<String> monitoredClasses;
	
	public static synchronized List<String> getMonitoredClasses() {
	    if (monitoredClasses == null) {
	    	monitoredClasses = new ArrayList<String>();
	    }
	    return monitoredClasses;
	}
	
	private void populateIgnoredClasses() {
		getMonitoredClasses().add("");
	}

}
