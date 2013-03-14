package ktn;

public enum RequestEnum {
	
	
	/** USED TO REQUEST AND SEND INFORMATION **/
	
	LOGIN, 			// Used to query the server for LOGINrequest.
	APPOINTMENT, 	// Used to query the server to get APPOINTMENTs.
	PERSON, 		// Used to query the server to query PERSONs.
	ALARM, 			// Used to query the server for ALARMs.
	ROOM, 			// Used to query the server for ROOMs.
	NOTIFICATION, 	// Used to query the server for NOTIFICATIONs.
	
	
	/** USED TO STORE OR REPLY ON ACTIONS **/
	
	
	BOOLEAN, 		// Used to reply TRUE/FALSE from the server
	S_APPOINTMENT, 	// Used to store APPOINTMENT to the server.
	S_ALARM, 		// Used to store ALARM to the server.
	S_PERSON; 		// Used to store PERSON to the server.
	
	
}
