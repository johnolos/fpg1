package ktn;

import java.io.Serializable;

import baseClasses.Appointment;

public class RequestObjects implements Serializable{
	
	requestType reType;
	String search;
	
	public RequestObjects(requestType reType, String search) {
		this.reType = reType;
		this.search = search;
	}
	
	
	public enum requestType{
		APPOINTMENT,PERSON,ALARM,ROOM;
	}

}
