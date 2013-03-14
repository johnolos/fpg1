package ktn;

import java.io.Serializable;

import baseClasses.Appointment;

public class RequestObjects implements Serializable{
	
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