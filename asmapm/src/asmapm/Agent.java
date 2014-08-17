package asmapm;

import java.lang.instrument.Instrumentation;
import java.util.logging.Level;
import java.util.logging.Logger;

import asmapm.model.CallStackTraceBuilderFactory;

public class Agent {
	
	//String s = this.getClass().
	private final static Logger log = Logger.getLogger("Agent");
	
	private static long lowThreshold = 200;

	public static void premain(String agentArgs, Instrumentation inst) {
		inst.addTransformer(new MyAsmTransformer());

	}

	public static void startprofile(String cName, String mName) {
		log.log(Level.INFO, "START PROFILE");
		CallStackTraceBuilderFactory.getCallStackTraceBuilder().startprofile(mName, cName);

	}
	
	public static void endprofile(String cName, String mName, long executionTime, String caller) {
		log.log(Level.INFO, "END PROFILE");
		CallStackTraceBuilderFactory.getCallStackTraceBuilder().endprofile(mName, cName, lowThreshold, executionTime);

	}
	
	
	public static void enter(String cName, String mName) {

		CallStackTraceBuilderFactory.getCallStackTraceBuilder().enter(mName, "",
				"", cName, "", System.currentTimeMillis());

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
