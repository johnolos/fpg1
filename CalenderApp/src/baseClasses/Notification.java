package baseClasses;

public class Notification {

	private Appointment appointment;
	
	private NotificationEnum type;
	private String listMessage;
	
	
	//Fields for all 3 boxes (and the only fields for OKBOX)
	public String description;
	public String title;
	
	//Fields for DECLINED
	public String meetingName;
	public String timeOfMeeting;
	public String declinedParticipant;
	
	//Fields for INVITATION
	public String location;
	public String start;
	public String end;
	
	public Notification(NotificationEnum type){
		this.type = type;
		setup(type);
	}
	
	void setup(NotificationEnum type) {
		
		switch(type) {
		case OKBOX:
			listMessage = "Ny melding";
			title = "OKTITTEEEL";
			description = "OKDESCRIPTIIOOOONNNN";
			break;
		case DECLINED:
			listMessage = "Deltager avslått";
			title = "DECLINEDTITTEEELLL";
			description = "DECLINEDDESCRIPTIIOOONNN";
			meetingName = "DECINEDMEETINGNAME";
			timeOfMeeting = "DECLIENDTIMEOFMEETING";
			declinedParticipant = "DECLINEDDECPARTICIANT";
			break;
		case INVITATION:
			listMessage = "Møteinvitasjon";
			title = "invtitle";
			description = "invdesc";
			location = "invloc";
			start = "invstart";
			end = "invend";
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