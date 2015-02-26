package asmapm.model;

import java.util.logging.Level;
import java.util.logging.Logger;

import asmapm.Agent;
import asmapm.ApmType;
import asmapm.queue.AssyncRabbitMQSender;
import asmapm.queue.Send;

public class CallStackTraceBuilder {
	private static CallStackTraceThreadLocal traceBuilder = new CallStackTraceThreadLocal();

	private static Logger log = Logger.getLogger("asmapm.Agent");

	public void print() {
		CallStackTrace state = traceBuilder.get();
		state.printOnTextFormat();

	}

	public CallStackTrace startprofile(String cName, String mName) {
		CallStackTrace state = traceBuilder.get();
		if (!state.isBuildingTrace()) {
			state.setBuildingTrace(true);
			state.resetCallStack();
			state.setClassName(cName);
			state.setMethodName(mName);
			return state;
		} else {
			return state;
		}
		// System.out.println("=========COMECOU O PROFILE===========");

	}

	public void endprofile(String cName, String mName, long threshold,
			long executionTime) {
		CallStackTrace state = traceBuilder.get();

		if ((state.getLevel() == 0) && (state.getClassName().equals(cName))
				&& (state.getMethodName().equals(mName))) {// Is the end of profile
															
			
			MethodCall method = new MethodCall();

			method.setClassName(cName);
			method.setMethodName(mName);
			method.setExecutionTime(executionTime);
			method.setLevel(state.getLevel());

			state.getMethodCalls().add(method);
			
			log.log(Level.INFO, "END PROFILE " + cName + "::" + mName);
			System.out.println();
			System.out.println();
			System.out.println();
			state.setStopTimestamp(System.currentTimeMillis());
			state.setBuildingTrace(false);
			// Send.getInstance().testSend(state);
			Agent.getInstance().getQueue().add(state);
		} else {
			leave(cName, mName, Agent.lowThreshold, executionTime);
		}
		// System.out.println("=========TERMINOU O PROFILE===========");

	}

	public void enter(String methodName, String methodSignature,
			String fullyQualifiedClassname, String cName, String path,
			long timestamp) {
		// System.out.println("\t\tCallStackTraceBuilder enter from: " +
		// fullyQualifiedClassname + "." + methodSignature);

		CallStackTrace state = traceBuilder.get();

		state.incCount();
		// System.out.println("Level Enter: " + state.getLevel());
		// System.out.println("LEVEL: "+ state.getLevel() + " - " +
		// classType+"::"+methodName);
		state.incLevel();

		/*
		 * if ((state.getCount() > 10000) && (state.isBuildingTrace())) {
		 * System.out.println("********************"); System.out
		 * .println("enter: Call Stack of over 10000 method calls are truncated"
		 * ); System.out.println("********************");
		 * //state.setBuildingTrace(false); return; }
		 */

	}

	public void leave(String cName, String mName, long threshold,
			long executionTime) {
		CallStackTrace state = traceBuilder.get();

		if (!state.isBuildingTrace()) {
			return;
		}

		if (executionTime > threshold) {

			state.decCount();
			// System.out.println("LEVEL: "+ state.getLevel() + " - " +
			// cName+"::"+mName+" Tempo: " + executionTime);
		} else {
			/*
			 * System.out.println("Classe:" + cName + " - Metodo:" + mName +
			 * " - Count:" + state.getCount() + " - Level: " + state.getLevel()
			 * + "ThreadID" + Thread.currentThread().getId());
			 */

		}

		if (executionTime > 10) {
			MethodCall method = new MethodCall();
			method.setClassName(cName);
			method.setMethodName(mName);
			method.setExecutionTime(executionTime);
			method.setLevel(state.getLevel());

			state.getMethodCalls().add(method);
			log.info("Added to call tree: " + cName + "::" + mName
					+ " + level:" + state.getLevel());
		}
		state.decLevel();

	}

	public void leave(String cName, String mName, long lowThreshold,
			long executionTime, ApmType type) {
		CallStackTrace state = traceBuilder.get();
		if (!state.isBuildingTrace()) {
			return;
		}
		if (executionTime > 10) {
			MethodCall method = new MethodCall();
			method.setClassName(cName);
			method.setMethodName(mName);
			method.setExecutionTime(executionTime);
			method.setLevel(state.getLevel());

			state.getMethodCalls().add(method);

			log.info("Added to call tree: " + cName + "::" + mName
					+ " + level:" + state.getLevel());
		}
		// System.out.println("LEVEL: "+ state.getLevel() + " - " +
		// cName+"::"+mName+" Tempo: " + executionTime + " SQL: " + sql);
		state.decLevel();
	}

	public void leaveHttpClient(String cName, String mName, long lowThreshold,
			long executionTime, String url, ApmType apmType) {
		CallStackTrace state = traceBuilder.get();
		if (!state.isBuildingTrace()) {
			return;
		}

		MethodCall method = new MethodCall();
		method.setClassName(cName);
		method.setMethodName(mName);
		method.setExecutionTime(executionTime);
		method.setLevel(state.getLevel());
		method.setTypeData(url);
		method.setType(apmType);
		state.getMethodCalls().add(method);

		state.decLevel();

	}

	public void leave(String cName, String mName, long threshold,
			long executionTime, String sql) {

		CallStackTrace state = traceBuilder.get();
		if (!state.isBuildingTrace()) {
			return;
		}

		if (executionTime > threshold) {

			// state.decCount();

		} else {
			/*
			 * System.out.println("Classe:" + cName + " \n Metodo:" + mName +
			 * " \n Count:" + state.getCount() + " \n Level: " +
			 * state.getLevel() + "ThreadID" + Thread.currentThread().getId() +
			 * "SQL: " + sql);
			 */

		}

		MethodCall method = new MethodCall();
		method.setClassName(cName);
		method.setMethodName(mName);
		method.setExecutionTime(executionTime);
		method.setLevel(state.getLevel());
		method.setSql(sql);

		state.getMethodCalls().add(method);

		// System.out.println("LEVEL: "+ state.getLevel() + " - " +
		// cName+"::"+mName+" Tempo: " + executionTime + " SQL: " + sql);
		state.decLevel();

	}

	public CallStackTraceThreadLocal getCallStackTrace() {
		return traceBuilder;
	}

	public void addExtraData(String key, Object value) {
		CallStackTrace state = traceBuilder.get();
		state.getExtraData().put(key, value);

	}

	public boolean isBuildingTrace() {
		CallStackTrace state = traceBuilder.get();
		return state.isBuildingTrace();
	}

	public CallStackTrace getState() {
		CallStackTrace state = traceBuilder.get();
		return state;
	}

}

class CallStackTraceThreadLocal extends ThreadLocal<CallStackTrace> {

	@Override
	protected CallStackTrace initialValue() {

		return new CallStackTrace();
	}
}
