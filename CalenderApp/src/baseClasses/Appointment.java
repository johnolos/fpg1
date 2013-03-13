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
       
        private String admin;
               
 
        public Appointment(DateTime start, DateTime end, String location, String title, Room room, String description, String admin) {
                this.start = start;
                this.end = end;
                this.location = location;
                this.room = room;
                this.description = description;
                this.admin = admin;
                this.title = title;
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