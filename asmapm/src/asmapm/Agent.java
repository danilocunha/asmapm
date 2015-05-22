package asmapm;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;

import asmapm.config.APMConfig;
import asmapm.model.CallStackTrace;
import asmapm.model.CallStackTraceBuilderFactory;
import asmapm.model.MethodCall;
import asmapm.queue.AssyncRabbitMQSender;

public class Agent {

	private static Logger log = Logger.getLogger("asmapm.Agent");
	
	private static Agent agent;

	public static long lowThreshold = 2000;
	
	private static final AtomicLong LAST_TIME_MS = new AtomicLong(0);
	
	
	private LinkedBlockingQueue<CallStackTrace> queue;
	
	private AssyncRabbitMQSender sender;

	public static void premain(String agentArgs, Instrumentation inst) {
		//System.out.println("===== TESTEEEEEEE =====");
		//dumpVars(System.getenv());		
				
		
		if (!getInstance().initTransport())
			return;
		
		inst.addTransformer(new MyAsmTransformer());

	}
	
	public static Agent getInstance() {
		if(agent == null) {
			agent = new Agent();	
		}
		return agent;		
	}
	
	public LinkedBlockingQueue<CallStackTrace> getQueue() {		
		return this.queue;		
	}

	private boolean initTransport() {
		try {

			this.queue = new LinkedBlockingQueue<CallStackTrace>(1000);			
			Thread t = new Thread(new AssyncRabbitMQSender());
			t.setName("ASMAPM Sender Thread");
	        t.start();

		} catch (Exception e) {

			return false;
		}
		return true;
	}	
	
	private static void dumpVars(Map<String, ?> m) {
		List<String> keys = new ArrayList<String>(m.keySet());
		Collections.sort(keys);
		for (String k : keys) {
			System.out.println(k + " : " + m.get(k));
		}
	}
	
	public static void addExtraData(String key, Object value) {
		//log.log(Level.INFO, "Add extra data with key: " + key + " an value " + value);
		CallStackTraceBuilderFactory.getCallStackTraceBuilder().addExtraData(key, value);
	}

	public static boolean startprofile(String cName, String mName) {
		CallStackTrace state = CallStackTraceBuilderFactory.getCallStackTraceBuilder().getState();
		if(!state.isBuildingTrace()) {			
			CallStackTraceBuilderFactory.getCallStackTraceBuilder().startprofile(
					mName, cName);
			return true;
		} else {
			enter(cName, mName);
			//log.log(Level.INFO, "PROFILE JA EXISTENTE PARA CHAMADA: " + cName + "::" + mName);
			//log.log(Level.INFO, "PROFILE JA INICIOU EM: " + state.getClassName() + "::" + state.getMethodName());
		}
		return false;		

	}
	
	public static int sizeof(Object obj) throws IOException {

		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteOutputStream);

		objectOutputStream.writeObject(obj);
		objectOutputStream.flush();
		objectOutputStream.close();

		return byteOutputStream.toByteArray().length;
	}
	
	public static void startprofile(String cName, String mName, Object object) {
		//log.log(Level.INFO, "START PROFILE " + cName + "::" + mName);
		CallStackTraceBuilderFactory.getCallStackTraceBuilder().startprofile(
				mName, cName);

	}
	
	public static void endprofile(String cName, String mName,
			long executionTime) {
		
		CallStackTraceBuilderFactory.getCallStackTraceBuilder().endprofile(
				mName, cName, lowThreshold, false, executionTime);

	}
	
	public static void endprofile(String cName, String mName, boolean didStartMonitor,
			long executionTime) {
		
		CallStackTraceBuilderFactory.getCallStackTraceBuilder().endprofile(
				mName, cName, lowThreshold, didStartMonitor, executionTime);

	}

	public static void endprofile(String cName, String mName, boolean didStartMonitor,
			long executionTime, RuntimeException e) {
		log.log(Level.INFO, "END PROFILE - RUNTIMEEXCEPTION" + cName + "::" + mName);
		//log.log(Level.INFO, "Context Path: " + CallStackTraceBuilderFactory.getCallStackTraceBuilder().g);
		e.printStackTrace();
		CallStackTraceBuilderFactory.getCallStackTraceBuilder().endprofile(
				mName, cName, lowThreshold, didStartMonitor, executionTime);

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
	
	public static void leaveHttpClient(String cName, String mName, long time, Object url) {
		//log.log(Level.INFO, "LEAVE METHOD WITH SQL CALL:" + cName + "::" + mName+ " - " + sql);
		CallStackTraceBuilderFactory.getCallStackTraceBuilder().leaveHttpClient(cName,
				mName, lowThreshold, time, url.toString(), ApmType.HTTP_CLIENT);

	}
	
	public static void leaveSql(String cName, String mName, long time, Object sql) {
		//log.info("LEAVE METHOD WITH SQL CALL:" + cName + "::" + mName+ " - " + sql);
		CallStackTraceBuilderFactory.getCallStackTraceBuilder().leaveSql(cName,
				mName, lowThreshold, time, sql.toString(), ApmType.JDBC);

	}
	
	public static void leave(String cName, String mName, long time, ApmType type) {
		//log.log(Level.INFO, "LEAVE METHOD WITH SQL CALL:" + cName + "::" + mName+ " - " + sql);
		CallStackTraceBuilderFactory.getCallStackTraceBuilder().leave(cName,
				mName, lowThreshold, time, type);

	}
	
	public static String getAsmapmTraceId() {
		return CallStackTraceBuilderFactory.getCallStackTraceBuilder().getAsmapmTraceId();
	}

}
