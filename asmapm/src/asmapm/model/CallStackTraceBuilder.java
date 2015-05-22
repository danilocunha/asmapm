package asmapm.model;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.SerializationUtils;

import com.google.gson.Gson;

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
			state.setStartTimestamp(System.currentTimeMillis());
			state.setAsmapmTraceId(UUID.randomUUID().toString());
			state.setStopTimestamp(0);
			state.setEventType(EventType.START_REQUEST);
			state.incCount();
			// Send init trace
			log.log(Level.INFO, "START PROFILE: " + cName + "::" + mName
					+ " Thread ID: " + Thread.currentThread().getId()
					+ " Thread Name: " + Thread.currentThread().getName()
					+ " ID: " + state.getAsmapmTraceId());
			CallStackTrace cst = new CallStackTrace(state);
			Agent.getInstance().getQueue().add(cst);

			return state;
		} else {
			state.incCount();
			state.incLevel();
			return state;
		}
		// System.out.println("=========COMECOU O PROFILE===========");

	}

	public void endprofile(String cName, String mName, long threshold, boolean didStartMonitor,
			long executionTime) {
		CallStackTrace state = traceBuilder.get();

		if (didStartMonitor) {// Is the end of
															// profile

			MethodCall method = new MethodCall();

			method.setClassName(cName);
			method.setMethodName(mName);
			method.setExecutionTime(executionTime);
			method.setLevel(state.getLevel());

			state.getMethodCalls().add(method);

			log.log(Level.INFO,
					"END PROFILE " + cName + "::" + mName + "Spent Time: "
							+ executionTime + " Thread ID"
							+ Thread.currentThread().getId() + "ID:"
							+ state.getAsmapmTraceId());

			state.setBuildingTrace(false);
			state.setStopTimestamp(System.currentTimeMillis());

			/*
			 * log.info("Start timestamp: " + state.getStartTimestamp());
			 * log.info("Stop timestamp: " + state.getStopTimestamp());
			 * log.info("Execution time do state: " + state.getExecutionTime());
			 * log.info("Execution time calulado: " + executionTime);
			 */
			if (state.getExecutionTime() > Agent.lowThreshold) {

				log.info("Evento acicionado a fila de envio com execution time de "
						+ state.getExecutionTime());
				state.setEventType(EventType.END_REQUEST_WITH_EVENT);
				CallStackTrace cst = new CallStackTrace(state);
				log.info("Count do state: " + state.getCount());
				log.info("Count do arraylist de metodos: "
						+ state.getMethodCalls().size());
				try {
					log.info("Tamanho do objeto CallStackTrace em bytes: "
							+ Agent.sizeof(state));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Agent.getInstance().getQueue().add(cst);
			} else {
				CallStackTrace cst = new CallStackTrace();
				cst.setAsmapmTraceId(state.getAsmapmTraceId());
				cst.setEventType(EventType.END_REQUEST_NO_EVENT);
				Agent.getInstance().getQueue().add(cst);
			}
		} else {
			leave(cName, mName, Agent.lowThreshold, executionTime);
		}
		// System.out.println("=========TERMINOU O PROFILE===========");

	}

	public void enter(String mName, String methodSignature,
			String fullyQualifiedClassname, String cName, String path,
			long timestamp) {
		// System.out.println("\t\tCallStackTraceBuilder enter from: " +
		// fullyQualifiedClassname + "." + methodSignature);

		CallStackTrace state = traceBuilder.get();

		state.incCount();

		state.incLevel();

		/*if (state.isBuildingTrace()) {
			log.log(Level.INFO,
					"ENTER METHOD: " + cName + "::" + mName + " Thread ID"
							+ Thread.currentThread().getId() + " Thread Name"
							+ Thread.currentThread().getName() + " ID:"
							+ state.getAsmapmTraceId() + " Level: "
							+ state.getLevel());
		}*/
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

		/*if (state.isBuildingTrace()) {
			log.log(Level.INFO,
					"LEAVE METHOD: " + cName + "::" + mName + " Thread ID"
							+ Thread.currentThread().getId() + " Thread Name"
							+ Thread.currentThread().getName() + " ID:"
							+ state.getAsmapmTraceId() + " Level: "
							+ state.getLevel());
		}*/

		if (executionTime > 10) {
			MethodCall method = new MethodCall();
			method.setClassName(cName);
			method.setMethodName(mName);
			method.setExecutionTime(executionTime);
			method.setLevel(state.getLevel());

			state.getMethodCalls().add(method);
			/*
			 * log.info("Added to call tree: " + cName + "::" + mName +
			 * " + level:" + state.getLevel());
			 */
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

			/*
			 * log.info("Added to call tree: " + cName + "::" + mName +
			 * " + level:" + state.getLevel());
			 */
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

	public void leaveSql(String cName, String mName, long threshold,
			long executionTime, String sql, ApmType apmType) {

		CallStackTrace state = traceBuilder.get();
		if (!state.isBuildingTrace()) {
			return;
		}

		MethodCall method = new MethodCall();
		method.setClassName(cName);
		method.setMethodName(mName);
		method.setExecutionTime(executionTime);
		method.setLevel(state.getLevel());
		method.setTypeData(sql);
		method.setSql(sql);
		method.setType(apmType);
		state.getMethodCalls().add(method);

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

	public String getAsmapmTraceId() {
		CallStackTrace state = traceBuilder.get();
		return state.getAsmapmTraceId();
	}

}

class CallStackTraceThreadLocal extends ThreadLocal<CallStackTrace> {

	@Override
	protected CallStackTrace initialValue() {

		return new CallStackTrace();
	}
}
