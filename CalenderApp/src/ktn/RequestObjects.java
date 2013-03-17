package ktn;

import java.io.Serializable;

import baseClasses.Appointment;

public class RequestObjects implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5129966378464084609L;
	RequestEnum reType;
	String[] keyword;
	
	public RequestObjects(RequestEnum reType, String[] keyword) {
		this.reType = reType;
		this.keyword = keyword;
	}
	
	
	public RequestEnum getReType() {
		return this.reType;
	}
	
	public String[] getSearch() {
		return this.keyword;
	}
}