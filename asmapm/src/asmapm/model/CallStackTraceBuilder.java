package asmapm.model;

import asmapm.queue.Send;

public class CallStackTraceBuilder {
	private static CallStackTraceThreadLocal traceBuilder = new CallStackTraceThreadLocal();

	public void print() {
		CallStackTrace state = traceBuilder.get();
		state.printOnTextFormat();

	}

	public void startprofile(String cName, String mName) {
		CallStackTrace state = traceBuilder.get();
		state.setBuildingTrace(true);
		state.resetCallStack();
		// System.out.println("=========COMECOU O PROFILE===========");

	}

	public void endprofile(String cName, String mName, long threshold,
			long executionTime) {
		CallStackTrace state = traceBuilder.get();
		state.setBuildingTrace(false);
		if (executionTime > threshold) {
			// System.out.println("LEVEL: "+ state.getLevel() + " - " +
			// cName+"::"+mName+" Tempo: " + executionTime);
		}

		MethodCall method = new MethodCall();

		method.setClassName(cName);
		method.setMethodName(mName);
		method.setExecutionTime(executionTime);
		method.setLevel(state.getLevel());

		state.getMethodCalls().add(method);

		state.setStopTimestamp(System.currentTimeMillis());

		Send.getInstance().testSend(state);
		// System.out.println("=========TERMINOU O PROFILE===========");

	}

	public void enter(String methodName, String methodSignature,
			String fullyQualifiedClassname, String classType, String path,
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

		MethodCall method = new MethodCall();
		method.setClassName(cName);
		method.setMethodName(mName);
		method.setExecutionTime(executionTime);
		method.setLevel(state.getLevel());

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

}

class CallStackTraceThreadLocal extends ThreadLocal<CallStackTrace> {

	@Override
	protected CallStackTrace initialValue() {

		return new CallStackTrace();
	}
}
