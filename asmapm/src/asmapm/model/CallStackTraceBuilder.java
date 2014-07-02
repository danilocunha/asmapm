package asmapm.model;

public class CallStackTraceBuilder {
	private static CallStackTraceThreadLocal traceBuilder = new CallStackTraceThreadLocal();

	public void enter(String methodName, String methodSignature,
			String fullyQualifiedClassname, String classType, String path,
			long timestamp) {
		// System.out.println("\t\tCallStackTraceBuilder enter from: " +
		// fullyQualifiedClassname + "." + methodSignature);
		
		CallStackTrace state = traceBuilder.get();
		
		if (!state.isBuildingTrace()) {
			
			state.setBuildingTrace(true);
			state.setFirstClassName(classType);
			state.setFirstMethodName(methodName);
			System.out.println("Comecou a execucao chamada para classe: " + classType
					+ " metodo: " + methodName + " com thread id "
					+ Thread.currentThread().getId());

		}
		state.incCount();
		//System.out.println("Level Enter: " + state.getLevel());
		state.incLevel();

		if ((state.getCount() > 10000) && (state.isBuildingTrace())) {
			System.out.println("********************");
			System.out
					.println("enter: Call Stack of over 10000 method calls are truncated");
			System.out.println("********************");
			//state.setBuildingTrace(false);
			return;
		}
		
	}

	public void print() {
		CallStackTrace state = traceBuilder.get();
		state.printOnTextFormat();

	}

	public void leave(String cName, String mName, long threshold,
			long executionTime) {
		CallStackTrace state = traceBuilder.get();
		
		state.decLevel();
		//System.out.println("Level Leave: " + state.getLevel());
		//Caso seja o nivel 0 e seja a saida do metodo que iniciou a construção é o fim do processo de coleta dos dados da chamada
		if ((state.getLevel() == 0) && (state.getFirstClassName().equals(cName)) && (state.getFirstMethodName().equals(mName)) && (state.isBuildingTrace())) {
			state.setBuildingTrace(false);
			System.out.println("Acabou execucao chamada para classe: " + cName
					+ " metodo: " + mName + " tempo de execucao: "
					+ executionTime);
			
			System.out.println("Level Leave: " + state.getLevel());
			System.out.println("Level Leave: " + state.getFirstClassName());
			System.out.println("Level Leave: " + state.getFirstMethodName());
		}
		if (!state.isBuildingTrace()) {
			return; // If we are buildning a trace already, do not start a new
					// trace
		}

		//state.setBuildingTrace(true);
		if (executionTime < threshold) {

			state.decCount();

		} else {
			System.out.println("Classe:" + cName + " - Metodo:" + mName
					+ " - Count:" + state.getCount() + " - Level: "
					+ state.getLevel() + "ThreadID"
					+ Thread.currentThread().getId());
			/*
			 * for (StackTraceElement ste :
			 * Thread.currentThread().getStackTrace()) {
			 * System.out.println(ste); }
			 */
		}
		// state.decLevel();

		/*
		 * CallStack contextStack = state.getCallStack(); if (contextStack !=
		 * null) { contextStack.setExecutionTime(executionTime);
		 * 
		 * if (contextStack.getCallingMethod() == null) {
		 * state.setStopTimestamp(contextStack.getTimestamp() + executionTime);
		 * // System.out.println("No calling method for: " + //
		 * contextStack.toString()); //
		 * System.out.println("Printing stack trace"); //
		 * EurekaJStringLogger.logCallStack(state); traceBuilder.remove(); }
		 * else { if (state.getCount() > 10000) {
		 * System.out.println("********************"); System.out
		 * .println("leave: Call Stack of over 10000 method calls are truncated"
		 * ); System.out.println("********************"); return; }
		 * 
		 * CallStack callingMethod = contextStack.getCallingMethod();
		 * state.setCallStack(callingMethod); } }
		 */
		// state.setBuildingTrace(false);
	}

	public CallStackTraceThreadLocal getCallStackTrace() {
		return traceBuilder;
	}

	public void leave(String cName, String mName, long threshold,
			long executionTime, String sql) {
		CallStackTrace state = traceBuilder.get();
		state.decLevel();
		if (!state.isBuildingTrace()) {
			return; // If we are buildning a trace already, do not start a new
					// trace
		}

		state.setBuildingTrace(true);
		if (executionTime < threshold) {

			state.decCount();

		} else {
			System.out.println("Classe:" + cName + " \n Metodo:" + mName
					+ " \n Count:" + state.getCount() + " \n Level: "
					+ state.getLevel() + "ThreadID"
					+ Thread.currentThread().getId() + "SQL: " + sql);
			/*
			 * for (StackTraceElement ste :
			 * Thread.currentThread().getStackTrace()) {
			 * System.out.println(ste); }
			 */
		}

	}
}

class CallStackTraceThreadLocal extends ThreadLocal<CallStackTrace> {

	@Override
	protected CallStackTrace initialValue() {
		
		return new CallStackTrace();
	}
}
