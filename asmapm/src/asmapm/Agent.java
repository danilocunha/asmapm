package asmapm;

import java.lang.instrument.Instrumentation;

import asmapm.model.CallStackTrace;
import asmapm.model.CallStackTraceBuilderFactory;

public class Agent {
	private static Agent agent;

	private static Thread shutdownHook;

	public static void premain(String agentArgs, Instrumentation inst) {
		agent = new Agent();
		// registers the transformer

		inst.addTransformer(new MyAsmTransformer());

		// shutdownHook.j
	}

	public static void methodEntered(String className, String methodName,
			String desc) {

		JavaLogEvent event = new JavaLogEvent("method_entered", "javaagent");
		// event.addPair("appName", agent.appName);
		// event.addPair("appID", agent.appID);
		event.addPair("className", className);
		event.addPair("methodName", methodName);
		event.addPair("methodDesc", desc);
		event.addPair("threadID", Thread.currentThread().getId());
		event.addPair("threadName", Thread.currentThread().getName());

		try {
			StackTraceElement ste = Thread.currentThread().getStackTrace()[3];
			if (ste != null)
				event.addPair("lineNumber", ste.getLineNumber());
			event.addPair("sourceFileName", ste.getFileName());
		} catch (Exception e1) {
		}

		System.out.println(event.toString());

	}

	public static void methodExited(String className, String methodName,
			String desc) {

		JavaLogEvent event = new JavaLogEvent("method_exited", "javaagent");

		event.addPair("className", className);
		event.addPair("methodName", methodName);
		event.addPair("methodDesc", desc);
		event.addPair("threadID", Thread.currentThread().getId());
		event.addPair("threadName", Thread.currentThread().getName());
		// addUserTags(event);
		/*
		 * try { agent.eventQueue.put(event); //
		 * agent.eventQueue.offer(event,1000,TimeUnit.MILLISECONDS); } catch
		 * (InterruptedException e) {
		 * 
		 * }
		 */
		System.out.println(event.toString());

	}

	public static void enter() {

		CallStackTraceBuilderFactory.getCallStackTraceBuilder().enter("", "",
				"", "", "", System.currentTimeMillis());

	}

	public static void leave(String cName, String mName, long time) {
		
		CallStackTraceBuilderFactory.getCallStackTraceBuilder().leave(cName,
				mName, 1000, time);

	}

	public static void leave(String cName, String mName, long time, String sql) {

		CallStackTraceBuilderFactory.getCallStackTraceBuilder().leave(cName,
				mName, 1000, time, sql);

	}

	public static void testefunc(String cName, String mName) {
		System.out.println("oi " + cName + " " + mName);
	}

	public static void testefunc() {
		System.out.println("oi euuuuu ");
	}

	public static void log(String className, String methodName, String desc,
			long elapsedTime) {

		JavaLogEvent event = new JavaLogEvent("method_exited", "javaagent");

		event.addPair("className", className);
		event.addPair("methodName", methodName);
		event.addPair("methodDesc", desc);
		event.addPair("elapsedTime", elapsedTime);
		event.addPair("threadID", Thread.currentThread().getId());
		event.addPair("threadName", Thread.currentThread().getName());
		// addUserTags(event);
		/*
		 * try { agent.eventQueue.put(event); //
		 * agent.eventQueue.offer(event,1000,TimeUnit.MILLISECONDS); } catch
		 * (InterruptedException e) {
		 * 
		 * }
		 */
		System.out.println(event.toString());

	}

	public static void log(String className, String methodName, String desc) {

		JavaLogEvent event = new JavaLogEvent("method_exited", "javaagent");

		event.addPair("className", className);
		event.addPair("methodName", methodName);
		event.addPair("methodDesc", desc);

		event.addPair("threadID", Thread.currentThread().getId());
		event.addPair("threadName", Thread.currentThread().getName());
		// addUserTags(event);
		/*
		 * try { agent.eventQueue.put(event); //
		 * agent.eventQueue.offer(event,1000,TimeUnit.MILLISECONDS); } catch
		 * (InterruptedException e) {
		 * 
		 * }
		 */
		System.out.println(event.toString());

	}

}
