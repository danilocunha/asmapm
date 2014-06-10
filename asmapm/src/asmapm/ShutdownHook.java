package asmapm;

public class ShutdownHook extends Thread {
	private Thread dadThread;

	public ShutdownHook(Thread t) {
		// this.threadId = threadId;
		this.dadThread = t;
	}

	@Override
	public void run() {
		super.run();
		

		// CallStackTraceBuilderFactory.getCallStackTraceBuilder().print();
	}
}
