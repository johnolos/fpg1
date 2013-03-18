package baseClasses;

public class Notification {

	private Appointment appointment;
	
	private NotificationEnum type;
	private String listMessage;
	
	//Fields for all 3 boxes (and the only fields for OKBOX)
	private String description;
	private String title;
	private String admin;
	
	//Fields for DECLINED
	private String meetingName;
	private String timeOfMeeting;
	private String declinedParticipant;
	
	//Fields for INVITATION
	private String location;
	private String start;
	private String end;
	private String date;
	
	public Notification(NotificationEnum type){
		this.type = type;
		setup(type);
	}
	
	public Notification(String type, String title, String admin, String start, String end, String date){
		setEnum(type);
		this.title = title;
		this.admin = admin;
		this.start = start;
		this.end = end;
		this.date = date;
		//setup(type);
	}
	
	void setup(NotificationEnum type) {
		
		switch(type) {
		case OKBOX:
			
			break;
		case DECLINED:
			
			break;
		case INVITATION:
			
			break;
		default:
			listMessage = "Ny melding";
			break;
		}
	}
	
	public NotificationEnum getType(){
		return this.type;
	}
	
	public String getListMessage(){
		return this.listMessage;
	}

	public String getDescription() {
		return description;
	}

	public String getTitle() {
		return title;
	}

	public String getMeetingName() {
		return meetingName;
	}

	public String getTimeOfMeeting() {
		return timeOfMeeting;
	}

	public String getDeclinedParticipant() {
		return declinedParticipant;
	}

	public String getLocation() {
		return location;
	}

	public String getStart() {
		return start;
	}

	public String getEnd() {
		return end;
	}
	
	public Appointment getAppointment(){
		return appointment;
	}
	
	public void setEnum(String type){
		switch(type){
		case "DECLINED": this.type = NotificationEnum.DECLINED;break;
		case "INVITATION": this.type = NotificationEnum.INVITATION;break;
		case "OKBOX": this.type = NotificationEnum.OKBOX;break;
		}
	}
	
}