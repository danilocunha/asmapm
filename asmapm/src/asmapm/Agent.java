package asmapm;

import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

import asmapm.model.CallStackTraceBuilderFactory;
import asmapm.model.MethodCall;

public class Agent {

	private final static Logger log = Logger.getLogger("Agent");

	private static long lowThreshold = 200;

	public static void premain(String agentArgs, Instrumentation inst) {
		// System.out.println("===== ENV VARIABLES =====");
		// dumpVars(System.getenv());

		inst.addTransformer(new MyAsmTransformer());

	}

	private static void dumpVars(Map<String, ?> m) {
		List<String> keys = new ArrayList<String>(m.keySet());
		Collections.sort(keys);
		for (String k : keys) {
			System.out.println(k + " : " + m.get(k));
		}
	}
	
	public static void addExtraData(String key, Object value) {
		log.log(Level.INFO, "Add extra data with key: " + key + " an value " + value);
		CallStackTraceBuilderFactory.getCallStackTraceBuilder().addExtraData(key, value);
	}

	public static void startprofile(String cName, String mName) {
		log.log(Level.INFO, "START PROFILE");
		CallStackTraceBuilderFactory.getCallStackTraceBuilder().startprofile(
				mName, cName);

	}

	public static void startprofile(String cName, String mName, Object object) {
		log.log(Level.INFO, "START PROFILE");
		CallStackTraceBuilderFactory.getCallStackTraceBuilder().startprofile(
				mName, cName);

	}
	
	public static void endprofile(String cName, String mName,
			long executionTime, String caller) {
		log.log(Level.INFO, "END PROFILE");
		log.log(Level.INFO, "Context Path: " + caller);
		CallStackTraceBuilderFactory.getCallStackTraceBuilder().endprofile(
				mName, cName, lowThreshold, executionTime);

	}

	public static void endprofile(String cName, String mName,
			long executionTime, RuntimeException e) {
		log.log(Level.INFO, "END PROFILE");
		//log.log(Level.INFO, "Context Path: " + CallStackTraceBuilderFactory.getCallStackTraceBuilder().g);
		e.printStackTrace();
		CallStackTraceBuilderFactory.getCallStackTraceBuilder().endprofile(
				mName, cName, lowThreshold, executionTime);

	}

	/*
	 * public static void endprofile(String cName, String mName, long
	 * executionTime, RuntimeException e, HashMap<String, String> hashMap) {
	 * log.log(Level.INFO, "END PROFILE"); log.log(Level.INFO, "Context Path: "
	 * + hashMap.get("contextPath")); e.printStackTrace();
	 * CallStackTraceBuilderFactory.getCallStackTraceBuilder().endprofile(
	 * mName, cName, lowThreshold, executionTime);
	 * 
	 * }
	 */
	/*
	 * public static void endprofile(String cName, String mName, long
	 * executionTime, RuntimeException e, HttpServlet servlet) {
	 * log.log(Level.INFO, "END PROFILE"); log.log(Level.INFO, "Context Path: "
	 * + servlet.getServletContext().getContextPath()); e.printStackTrace();
	 * CallStackTraceBuilderFactory.getCallStackTraceBuilder().endprofile(
	 * mName, cName, lowThreshold, executionTime);
	 * 
	 * }
	 */

	public static void enter(String cName, String mName) {

		CallStackTraceBuilderFactory.getCallStackTraceBuilder().enter(mName,
				"", "", cName, "", System.currentTimeMillis());

	}

	public static void leave(String cName, String mName, long time) {

		CallStackTraceBuilderFactory.getCallStackTraceBuilder().leave(cName,
				mName, lowThreshold, time);

	}

	public static void leave(String cName, String mName, long time, String sql) {

		CallStackTraceBuilderFactory.getCallStackTraceBuilder().leave(cName,
				mName, lowThreshold, time, sql);

	}

}
