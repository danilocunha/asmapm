package asmapm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CallStackTrace implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private List<MethodCall> methodCalls = null;
	private boolean buildingTrace = false;
	private int count = 0;
	private int level = 0;
	private long theadId;
	private long startTimestamp;
	private long stopTimestamp;
	private String classType;
	private String path;
	private String className;
	private String methodName;

	public CallStackTrace() {
		theadId = Thread.currentThread().getId();
	}

	public List<MethodCall> getMethodCalls() {
		if (methodCalls == null) {
			methodCalls = new ArrayList<MethodCall>();
		}
		return methodCalls;
	}

	public void resetCallStack() {
		this.startTimestamp = System.currentTimeMillis();		
		
		this.methodCalls = new ArrayList<>();
		this.level = 0;
		this.count = 0;
	}

	public boolean isBuildingTrace() {
		return buildingTrace;
	}

	public void setBuildingTrace(boolean buildingTrace) {
		this.buildingTrace = buildingTrace;
	}

	public int getCount() {
		return count;
	}

	public void incCount() {
		count++;
	}

	public void decCount() {
		count--;
	}

	public int getLevel() {
		return level;
	}

	public void incLevel() {
		level++;
	}

	public void decLevel() {
		level--;
	}

	public long getStartTimestamp() {
		return startTimestamp;
	}

	public void setStartTimestamp(long startTimestamp) {
		this.startTimestamp = startTimestamp;
	}

	public long getStopTimestamp() {
		return stopTimestamp;
	}

	public void setStopTimestamp(long stopTimestamp) {
		this.stopTimestamp = stopTimestamp;
	}

	public long getTheadId() {
		return theadId;
	}

	public void setTheadId(long theadId) {
		this.theadId = theadId;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getOnStringFormat() {

		StringBuffer sb = new StringBuffer();

		Iterator<MethodCall> ite = this.getMethodCalls().iterator();
		MethodCall m;
		boolean showAll = false;
		int lastLevel = 0;
		while (ite.hasNext()) {
			m = ite.next();
			if ((m.getExecutionTime() > 1000)) {
				sb.append(m.toString());
				sb.append("\n");
				lastLevel = m.getLevel();
			} else {
				if (showAll && (m.getExecutionTime() > 10)) {
					sb.append(m.toString());
					sb.append("\n");
					if (lastLevel == m.getLevel()) {
						showAll = false;
					}
				} else {
					if (m.getSql() != null) {
						sb.append(m.getSql());
						sb.append("\n");
						// System.out.println(c.toString() + c.getSql());
						showAll = true;
					}
				}

			}

		}
		return sb.toString();
	}

	public String getOnStringFormatMethod() {

		StringBuffer sb = new StringBuffer();

		Iterator<MethodCall> ite = this.methodCalls.iterator();
		MethodCall m;
		boolean showAll = false;
		int lastLevel = 0;
		while (ite.hasNext()) {
			m = ite.next();
			if ((m.getExecutionTime() > 1000)) {
				sb.append(m.toString());
				sb.append("\n");
				lastLevel = m.getLevel();
			} else {
				if (showAll && (m.getExecutionTime() > 10)) {
					sb.append(m.toString());
					sb.append("\n");
					if (lastLevel == m.getLevel()) {
						showAll = false;
					}
				} else {
					if (m.getSql() != null) {
						sb.append(m.getSql());
						sb.append("\n");
						// System.out.println(c.toString() + c.getSql());
						showAll = true;
					}
				}

			}

		}
		return sb.toString();
	}

	public void printOnTextFormat() {
		// System.out.println(this.callStack.getThreadid());
		/*
		 * for(CallStack c : this.callStack.getMethodCalls()) { for(int i
		 * =0;i<c.getLevel();i++) { System.out.print(""); }
		 * System.out.println(c.getMethodName()); }
		 */
		Iterator<MethodCall> ite = this.getMethodCalls().iterator();
		MethodCall m;
		boolean showAll = false;
		int lastLevel = 0;
		while (ite.hasNext()) {
			m = ite.next();
			if ((m.getExecutionTime() > 1000)) {
				System.out.println(m.toString());
				lastLevel = m.getLevel();
			} else {
				if (showAll && (m.getExecutionTime() > 10)) {
					System.out.println(m.toString());
					if (lastLevel == m.getLevel()) {
						showAll = false;
					}
				} else {
					if (m.getSql() != null) {
						System.out.println(m.toString() + m.getSql());
						showAll = true;
					}
				}
			}
		}
	}
}
