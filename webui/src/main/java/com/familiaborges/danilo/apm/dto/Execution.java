package com.familiaborges.danilo.apm.dto;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.SerializationUtils;

import asmapm.model.CallStackTrace;

@Entity
@Table(name="executions")
public class Execution implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String idExecution;
	
	private long startTimeMillis;
	
	private long duration;
	
	private byte[] callStackTraceAsByteArray;

	@Id
	@Column(name="id_execution")
	public String getIdExecution() {
		return idExecution;
	}

	public void setIdExecution(String idExecution) {
		this.idExecution = idExecution;
	}
	
	public long getStartTimeMillis() {
		return startTimeMillis;
	}

	public void setStartTimeMillis(long startTimeMillis) {
		this.startTimeMillis = startTimeMillis;
	}
	
	@Transient
    public String getDataHora() {
		Calendar calendar = Calendar.getInstance();
	    calendar.setTimeInMillis(startTimeMillis);
	    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		return df.format(calendar.getTime());
    }

	@Column(name="duration")
	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	@Column(name="callStackTrace", columnDefinition = "LONGBLOB")
	@Lob
	public byte[] getCallStackTraceAsByteArray() {
		return callStackTraceAsByteArray;
	}

	public void setCallStackTraceAsByteArray(byte[] callStackTrace) {
		this.callStackTraceAsByteArray = callStackTrace;
	}
	
	@Transient
    public CallStackTrace getCallStackTrace() {
        return (CallStackTrace) SerializationUtils.deserialize(callStackTraceAsByteArray);
    }

    public void setCallStackTrace(CallStackTrace callStackTrace) {
        this.callStackTraceAsByteArray = SerializationUtils.serialize((Serializable) callStackTrace);
    }
    
    @Transient
    public String getServerUrl() {
        return getCallStackTrace().getExtraData().get("serverUrl").toString();
    }
	
	
}
