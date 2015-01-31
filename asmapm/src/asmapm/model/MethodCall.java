package asmapm.model;

import java.io.Serializable;

import asmapm.ApmType;

public class MethodCall implements Serializable {

	private static final long serialVersionUID = 1L;

	private String className;
	private String methodName;
	private String methodSignature;
	private String fullyQualifiedClassname;
	// private CallStack callerMethod;
	private int callCount;
	private int level;
	private int callerMethodIndex;
	private Long timestamp;
	private Long executionTime;
	private long threadid;
	private String threadName;
	private String sql;
	
	private Integer indexOfDadMethodCall;
	private boolean isVisibleOnCallTree;
	private String typeData;//Some data according to the apmType
	private ApmType type;

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

	public String getMethodSignature() {
		return methodSignature;
	}

	public void setMethodSignature(String methodSignature) {
		this.methodSignature = methodSignature;
	}

	public String getFullyQualifiedClassname() {
		return fullyQualifiedClassname;
	}

	public void setFullyQualifiedClassname(String fullyQualifiedClassname) {
		this.fullyQualifiedClassname = fullyQualifiedClassname;
	}

	/*
	 * public CallStack getCallerMethod() { return callerMethod; } public void
	 * setCallerMethod(CallStack callerMethod) { this.callerMethod =
	 * callerMethod; }
	 */
	public int getCallCount() {
		return callCount;
	}

	public void setCallCount(int callCount) {
		this.callCount = callCount;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getCallerMethodIndex() {
		return callerMethodIndex;
	}

	public void setCallerMethodIndex(int callerMethodIndex) {
		this.callerMethodIndex = callerMethodIndex;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Long getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Long executionTime) {
		this.executionTime = executionTime;
	}

	public long getThreadid() {
		return threadid;
	}

	public void setThreadid(long threadid) {
		this.threadid = threadid;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}
	
	public String getTypeData() {
		return typeData;
	}

	public void setTypeData(String typeData) {
		this.typeData = typeData;
	}

	public ApmType getType() {
		return type;
	}

	public void setType(ApmType type) {
		this.type = type;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public Integer getIndexOfDadMethodCall() {
		return indexOfDadMethodCall;
	}

	public void setIndexOfDadMethodCall(Integer indexOfDadMethodCall) {
		this.indexOfDadMethodCall = indexOfDadMethodCall;
	}	

	public boolean isVisibleOnCallTree() {
		return isVisibleOnCallTree;
	}

	public void setVisibleOnCallTree(boolean isVisibleOnCallTree) {
		this.isVisibleOnCallTree = isVisibleOnCallTree;
	}

	public String toString() {
		return this.getClassName() + "::" + this.getMethodName() + " Tempo: "
				+ this.getExecutionTime() + " Level: " + this.getLevel()
				+ " Index dof Dad:" + this.getIndexOfDadMethodCall() + " SQL:"
				+ this.sql;
	}

}
