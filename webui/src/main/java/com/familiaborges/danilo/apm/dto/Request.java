package com.familiaborges.danilo.apm.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.SerializationUtils;

import asmapm.model.CallStackTrace;

@Entity
@Table(name="requests")
public class Request implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private long startTimeInMillis;
	
	private String uri;
	
	private byte[] data;

	@Id
	@Column(name="id_request")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public long getStartTimeInMillis() {
		return startTimeInMillis;
	}

	public void setStartTimeInMillis(long startTimeInMillis) {
		this.startTimeInMillis = startTimeInMillis;
	}

	@Column(name="uri")
	public String getURI() {
		return uri;
	}

	public void setURI(String uri) {
		this.uri = uri;
	}

	@Column(name="data", columnDefinition = "LONGBLOB")
	@Lob
	public byte[] getCallStackTraceAsByteArray() {
		return data;
	}

	public void setCallStackTraceAsByteArray(byte[] callStackTrace) {
		this.data = callStackTrace;
	}
	
	@Transient
    public CallStackTrace getData() {
        return (CallStackTrace) SerializationUtils.deserialize(data);
    }

    public void setData(CallStackTrace callStackTrace) {
        this.data = SerializationUtils.serialize((Serializable) callStackTrace);
    }    
	
	
}
