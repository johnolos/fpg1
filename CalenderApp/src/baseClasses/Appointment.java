package baseClasses;
 
import java.io.Serializable;

import org.joda.time.DateTime;
 
public class Appointment implements Serializable {
       
        // version 2.234
       
        private DateTime start = new DateTime();
        private DateTime end = new DateTime();
       
        private Room room;
        private String location;
       
        private String title;
        private String description;
       
        private Person admin;
               
 
        Appointment(DateTime start, DateTime end, String location, Room room, String description, Person admin) {
                this.start=start;
                this.end=end;
                this.location=location;
                this.room=room;
                this.description=description;
                this.admin=admin;
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
 
 
        public Person getAdmin() {
                return admin;
        }
       
 
}