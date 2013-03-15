package ktn;

import java.util.ArrayList;

import org.joda.time.DateTime;

import baseClasses.Appointment;
import baseClasses.Person;
import baseClasses.Room;

import com.sun.org.apache.bcel.internal.generic.InstructionConstants.Clinit;

public class TestFile {
	
	public static void main(String[] args) throws InterruptedException {
		run();
	}
	public static void run() throws InterruptedException{
		//Appointment app = new Appointment(new DateTime(2013,05,05,12,0), new DateTime(2013,05,05,13,0), "Jkefd", "PORN", null, "Ingen", "Hans");
		//System.out.println(createAppointment(app));
		Client client = new Client();
		client.connect();
		
		ArrayList<Room> rooms = client.fetchRooms("2013","14:00:00","15:00:00");
		/*String [] hans = {"Hans","sdf"};
		Person person = client.login(hans);
		System.out.println("Final : "+ person.getUsername());
		*/
	}

}
