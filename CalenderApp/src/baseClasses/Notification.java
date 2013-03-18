package baseClasses;

import java.io.Serializable;

public class Notification implements Serializable {

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
	
	public Notification(NotificationEnum type, Appointment appointment){
		this.type = type;
		setup(this.type);
		this.appointment = appointment;
	}
	
	void setup(NotificationEnum type) {
		
		switch(type) {
		case OKBOX:
			listMessage = "okbox";
			break;
		case DECLINED:
			listMessage = "Avlyst";
			meetingName = appointment.getTitle();
			timeOfMeeting = appointment.getStart().toString();
			break;
		case INVITATION:
			listMessage = "Invitasjon";
			location = appointment.getLocation();
			description = appointment.getDescription();
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
	
}
