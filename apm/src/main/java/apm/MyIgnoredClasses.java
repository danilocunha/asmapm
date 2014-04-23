package apm;

import java.util.ArrayList;
import java.util.List;

public class MyIgnoredClasses {
	
	private static List<String> ignoredClasses;
	
	public static synchronized List<String> getIgnoredClasses() {
	    if (ignoredClasses == null) {
	    	ignoredClasses = new ArrayList<String>();
	    }
	    return ignoredClasses;
	}
	
	private void populateIgnoredClasses() {
		getIgnoredClasses().add("");
	}

}
