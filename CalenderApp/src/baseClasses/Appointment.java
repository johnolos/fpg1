package baseClasses;
 
import java.io.Serializable;
import java.util.ArrayList;

import org.joda.time.DateTime;
 
public class Appointment implements Serializable {
       
        // version 2.234
       
        private DateTime start = new DateTime();
        private DateTime end = new DateTime();
       
        private Room room;
        private String location;
       
        private String title;
        private String description;
       
        private String admin;          
        
        private ArrayList<Person> participants;
 
        public Appointment(DateTime start, DateTime end, String location, String title, Room room, String description, String admin) {
                this.start = start;
                this.end = end;
                this.location = location;
                this.room = room;
                this.description = description;
                this.admin = admin;
                this.title = title;
                this.participants = new ArrayList<Person>();
        }
 
 
        public DateTime getStart() {
                return start;
        }
 
 
        public DateTime getEnd() {
                return end;
        }
 
 
        public Room getRoom() {
                return room;
        }
 
 
        public String getLocation() {
                return location;
        }
 
 
        public String getTitle() {
                return title;
        }
 
 
        public String getDescription() {
                return description;
        }
 
 
        public String getAdmin() {
                return admin;
        }
        
        public void setParticipants(ArrayList<Person> list){
        	this.participants = list;
        }
        
        public ArrayList<Person> getParticipants(){
        	return participants;
        }


		public void setRoom(Room room2) {
			this.room = room2;
			
		}


		public void setAdmin(String admin) {
			this.admin = admin;
		}
       
 
}