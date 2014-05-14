package asmapm;

import asmapm.model.CallStackTraceBuilderFactory;

public class ShutdownHook extends Thread {

	@Override
	public void run() {
		super.run();
		System.out.println("exit");
		//CallStackTraceBuilderFactory.getCallStackTraceBuilder().print();
	}
}
