package asmapm.model;

import java.io.Serializable;

public class MethodCall  implements Serializable {

private static final long serialVersionUID = 1L;
	
	private String className;
	private String methodName;
	private String methodSignature;
	private String fullyQualifiedClassname;
	private CallStack callerMethod;
	private int callCount;
	private int level;
	private Long timestamp;
	private Long executionTime;
	private long threadid;
	private String threadName;
	private String sql;
	
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
	public CallStack getCallerMethod() {
		return callerMethod;
	}
	public void setCallerMethod(CallStack callerMethod) {
		this.callerMethod = callerMethod;
	}
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
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	public String toString() {
		return this.getClassName() + "::" + this.getMethodName()  + " Tempo: " + this.getExecutionTime() + " Level: " + this.getLevel();
	}
	
	
}
