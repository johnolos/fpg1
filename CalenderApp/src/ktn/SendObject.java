package ktn;

import java.io.Serializable;

public class SendObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4059218807183200253L;
	private RequestEnum sendType;
	private Object object;
	
	public SendObject(RequestEnum request,Object obj){
		this.sendType = request;
		this.object = obj;
	}

	public RequestEnum getSendType() {
		return sendType;
	}

	public void setSendType(RequestEnum sendType) {
		this.sendType = sendType;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
}
