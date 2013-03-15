package ktn;

import java.io.Serializable;

public class SendObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4059218807183200253L;
	private RequestEnum sendType;
	private Object object;
	private String [] keyword;
	private boolean value;//If true, then it contains an object. 
	
	//Request object does not contain an object.
	public SendObject(RequestEnum reType, String[] keyword) {
		this.sendType = reType;
		this.keyword = keyword;
		this.object = null;
		this.value = false;
	}

	public SendObject(RequestEnum request,Object obj){
		this.sendType = request;
		this.object = obj;
		this.keyword = null;
		this.value = true;
	}

	public RequestEnum getSendType() {
		return sendType;
	}

	public Object getObject() {
		return object;
	}
	public boolean isRequest(){
		return this.object == null ? true : false;
	}
	public String [] getKeyword(){
		return this.keyword;
	}
}
