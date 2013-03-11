package baseClasses;

import org.joda.time.DateTime;

public class Appointment {
	
	// version 2.234
	
	private DateTime start = new DateTime();
	private DateTime end = new DateTime();
	
	private Room room;
	private String location;
	
	private String title;
	private String description;
	
	private Person admin;
		

	Appointment() {
		
	}
	

}
