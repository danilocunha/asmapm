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
		
	}
	
	public void endprofile(String cName, String mName, long threshold,
			long executionTime) {
		CallStackTrace state = traceBuilder.get();
		state.setBuildingTrace(false);
		if (executionTime > threshold) {
			//System.out.println("LEVEL: "+ state.getLevel() + " - " + cName+"::"+mName+" Tempo: " + executionTime);	
		}
		CallStack stack = new CallStack();
		stack.setClassName(cName);
		stack.setMethodName(mName);
		stack.setExecutionTime(executionTime);
		stack.setLevel(state.getLevel());
		state.getCallStack().addMethodCall(stack);
		//state.printOnTextFormat();
		Send.getInstance().testSend(state);
		//System.out.println("=========TERMINOU O PROFILE===========");
		
	}
	
	public void enter(String methodName, String methodSignature,
			String fullyQualifiedClassname, String classType, String path,
			long timestamp) {
		// System.out.println("\t\tCallStackTraceBuilder enter from: " +
		// fullyQualifiedClassname + "." + methodSignature);
		
		CallStackTrace state = traceBuilder.get();
		
		state.incCount();
		//System.out.println("Level Enter: " + state.getLevel());
		//System.out.println("LEVEL: "+ state.getLevel() + " - " + classType+"::"+methodName);
		state.incLevel();
		
		/*if ((state.getCount() > 10000) && (state.isBuildingTrace())) {
			System.out.println("********************");
			System.out
					.println("enter: Call Stack of over 10000 method calls are truncated");
			System.out.println("********************");
			//state.setBuildingTrace(false);
			return;
		}*/
		
	}
	
	public void leave(String cName, String mName, long threshold,
			long executionTime) {
		CallStackTrace state = traceBuilder.get();
		
		if(!state.isBuildingTrace()) {
			return;
		}
		
		if (executionTime > threshold) {
			
			state.decCount();
			//System.out.println("LEVEL: "+ state.getLevel() + " - " + cName+"::"+mName+" Tempo: " + executionTime);
		} else {
			/*System.out.println("Classe:" + cName + " - Metodo:" + mName
					+ " - Count:" + state.getCount() + " - Level: "
					+ state.getLevel() + "ThreadID"
					+ Thread.currentThread().getId());*/
			
		}
		
		state.decLevel();
		

		CallStack stack = new CallStack();
		stack.setClassName(cName);
		stack.setMethodName(mName);
		stack.setExecutionTime(executionTime);
		stack.setLevel(state.getLevel());
		state.getCallStack().addMethodCall(stack);
		
	}
	
	public void leave(String cName, String mName, long threshold,
			long executionTime, String sql) {
		
		CallStackTrace state = traceBuilder.get();
		if(!state.isBuildingTrace()) {
			return;
		}
		
				
		if (executionTime > threshold) {
			
			//state.decCount();
			

		} else {
			/*System.out.println("Classe:" + cName + " \n Metodo:" + mName
					+ " \n Count:" + state.getCount() + " \n Level: "
					+ state.getLevel() + "ThreadID"
					+ Thread.currentThread().getId() + "SQL: " + sql);*/
			
		}
		CallStack stack = new CallStack();
		stack.setClassName(cName);
		stack.setMethodName(mName);
		stack.setExecutionTime(executionTime);
		stack.setLevel(state.getLevel());
		stack.setSql(sql);
		state.getCallStack().addMethodCall(stack);
		//System.out.println("LEVEL: "+ state.getLevel() + " - " + cName+"::"+mName+" Tempo: " + executionTime + " SQL: " + sql);
		state.decLevel();

	}

	public CallStackTraceThreadLocal getCallStackTrace() {
		return traceBuilder;
	}

	
}

class CallStackTraceThreadLocal extends ThreadLocal<CallStackTrace> {

	@Override
	protected CallStackTrace initialValue() {
		
		return new CallStackTrace();
	}
}
