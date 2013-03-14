package ktn;

public class SendObject {
	
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
