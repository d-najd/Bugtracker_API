package com.bugtracker;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class EmptyObj {
	private String error;
	
	public EmptyObj() {
		super();
	}
	
	public EmptyObj(String error) {
		super();
		this.error = error;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
}
