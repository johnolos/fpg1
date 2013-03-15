package database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import baseClasses.Alarm;
import baseClasses.Appointment;
import baseClasses.Person;
import baseClasses.Room;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;


public class Database {
	
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	private final static String CONNECTION = "jdbc:mysql://localhost:3306/mydb";
	private final static String USER = "root";
	private final static String PASSWORD = "123";
	
	private java.sql.Connection con;
	private java.sql.Statement statement;
	
	
	public Database() throws Exception{
		try{
			Class.forName(DRIVER);
			con = DriverManager.getConnection(CONNECTION, USER, PASSWORD);
			statement = con.createStatement();
		}
		catch(Exception e){
			System.out.println("Connection error " + e.getMessage());	
		}
	}
	
	//Register a user in the database
	public boolean registerUser(String[] keyword){
		try {
			String query = "" + 
					"INSERT INTO person(username, password, firstName, lastName, email, telefon) " +
					"VALUES ('"+ 		keyword[0] +
							"',SHA1('"+ keyword[1] +
							"'),'"+ 	keyword[2] +
							"','"+ 		keyword[3] +
							"','"+ 		keyword[4] +
							"','"+ 		keyword[5] +"')";
				
			con.createStatement().executeUpdate(query);
		} catch (SQLException e) { 
			return false;
		}
		return true;
	}
	
	//Confirms that a person with username and password exists
	public Person login(String[] keyword){
		try {
			String query = 	"SELECT * " +
							"FROM person " +
							"WHERE username='"+ keyword[0] +"' " +
							"AND password=SHA1('"+ keyword[1] +"')";
				
			ResultSet res = con.createStatement().executeQuery(query);
			Person person;
			if(res.next()){
				return new Person(res.getString(2),res.getString(4),res.getString(5),res.getString(6));
			}
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	//Find persons with search word
	public ArrayList<Person> getPerson(String[] keyword){
		ArrayList<Person> personList = new ArrayList<Person>();
		try {
			String query = "" +
					"SELECT username, email, firstName, lastName " +
					"FROM person " +
					"WHERE username LIKE '%" + keyword[0] + "%' " +
					"OR username LIKE '%" + keyword[0] + "%' " +
					"OR username LIKE '%" + keyword[0] + "%' " +
					"ORDER BY lastName ASC";
				
			ResultSet res = con.createStatement().executeQuery(query);
			
			while(res.next()){
				personList.add( new Person(res.getString(1), res.getString(2), res.getString(3), res.getString(4)) );
			}
			return personList;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	//Get  a list with all appointments from user
	public ArrayList<Appointment> getAppointmentsOnPerson(String[] keyword){
		ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();
		
		try {
			String query = 	"SELECT a.* " +
							"FROM appointment a, person_appointment pha, person p " +
							"WHERE a.idAppointment = pha.appointment_idAppointment " +
							"AND pha.person_idPerson=p.idPerson AND p.username='"+ keyword[0] +"' " +
							"ORDER BY a.date ASC";
				
			ResultSet res = con.createStatement().executeQuery(query);
			
			//Add all appointments
			while(res.next()){
				
				//Get all values from ResultSet
				String 	title = res.getString(2);
				int[] 	localTimeStart = toInt(res.getString(3).split(":"));
				int[] 	localTimeEnd = toInt(res.getString(4).split(":"));
				int[] 	localDate = toInt(res.getString(5).split("-"));
				String 	description = res.getString(6);
				String 	location = res.getString(7);
				String 	admin = res.getString(8);
				int 	idRoom = Integer.parseInt(res.getString(9));
				
				
				//Create time and date objects and combine them to DateTime
				LocalTime start = new LocalTime(localTimeStart[0], localTimeStart[1]);
				LocalTime end = new LocalTime(localTimeEnd[0], localTimeEnd[1]);
				LocalDate date = new LocalDate(localDate[0], localDate[1], localDate[2]);
				
				DateTime startTime = date.toDateTime(start);
				DateTime endTime = date.toDateTime(end);
				
				//Get Room object from idRoom
				Room room = null;
				//There may be no room
				if(idRoom != 0){
					String[] r = getRoom(idRoom);
					room = new Room(Integer.parseInt(r[0]), r[1]);
				}
				
				//Add appointment
				appointmentList.add(new Appointment(startTime, endTime, location, title, room, description, admin));
			}
			return appointmentList;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	//Get a list of Appointment from user with date
	public ArrayList<Appointment> getAppointmentsOnDate(String[] keyword){
		ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();
		
		try {
			String query = 	"SELECT a.* " +
							"FROM appointment a, person_appointment pa, person p " +
							"WHERE a.idAppointment = pa.appointment_idAppointment " +
							"AND pa.person_idPerson = p.idPerson " +
							"AND p.username = '"+ keyword[0] +"' " +
							"AND date = '"+ keyword[1] +"' " +
							"ORDER BY a.date ASC";
					
			ResultSet res = con.createStatement().executeQuery(query);
			
			//Add all appointments
			while(res.next()){
					
				//Get all values from ResultSet
				String 	title = res.getString(2);
				int[] 	localTimeStart = toInt(res.getString(3).split(":"));
				int[] 	localTimeEnd = toInt(res.getString(4).split(":"));
				int[] 	localDate = toInt(res.getString(5).split("-"));
				String 	description = res.getString(6);
				String 	location = res.getString(7);
				String 	admin = res.getString(8);
				int 	idRoom = Integer.parseInt(res.getString(9));
				
				
				//Create time and date objects and combine them to DateTime
				LocalTime start = new LocalTime(localTimeStart[0], localTimeStart[1]);
				LocalTime end = new LocalTime(localTimeEnd[0], localTimeEnd[1]);
				LocalDate date = new LocalDate(localDate[0], localDate[1], localDate[2]);
				
				DateTime startTime = date.toDateTime(start);
				DateTime endTime = date.toDateTime(end);
				
				//Get Room object from idRoom
				Room room = new Room(0,null);
				//There may be no room
				if(idRoom != 0){
					String[] r = getRoom(idRoom);
					room = new Room(Integer.parseInt(r[0]), r[1]);
				}
				
				//Add appointment to list
				appointmentList.add(new Appointment(startTime, endTime, location, title, room, description, admin));
			}
			return appointmentList;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	//Delete given appointment from database
	public void deleteAppointment(String username, Appointment app){
		try {
			String query = 	"DELETE FROM appointment " +
							"WHERE idAppointment = '"+ getAppointmentId(app) +"' " +
							"AND admin = '"+ username +"'";
				
			con.createStatement().executeUpdate(query);
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public Boolean createAppointment(Appointment app){
		if(app.getRoom() == null){
			app.setRoom(new Room(0, "Ingen rom"));
		}
		try {
			String query = 	"INSERT INTO appointment (title,sTime,eTime,date,description,location,admin,room_idRoom) " +
							"VALUES ('"+ app.getTitle() + 
									"', '"+ app.getStart().toLocalTime().toString() +
									"', '"+ app.getEnd().toLocalTime().toString() +
									"', '"+ app.getStart().toLocalDate().toString() +
									"', '"+ app.getDescription() +
									"', '"+ app.getLocation() + 
									"', '"+ app.getAdmin()+
									"', '"+ getRoomId( app.getRoom() )+ "')";
			
			con.createStatement().executeUpdate(query);
			//Connect person to appointment
			createPersonAppointment(app.getAdmin(), app);
			//agreedAppointment(app.getAdmin(), app);
			return true;
		} catch (SQLException e) {
			e.printStackTrace(); }
		return false;
		
	}

	public void changeAppointment(Appointment app){
		try {
			String query =  "UPDATE appointment " +
							"SET title ='"+ app.getTitle() +
							"', sTime ='"+ app.getStart().toLocalTime().toString() +
							"', eTime ='"+ app.getEnd().toLocalTime().toString() +
							"', date ='"+ app.getStart().toLocalDate().toString() +
							"', description ='"+ app.getDescription() +
							"', location ='"+ app.getLocation() +
							"', admin ='"+ app.getAdmin() +
							"', room_idRoom ='"+ getRoomId(app.getRoom()) + "' " +
							"WHERE idAppointment='"+ getAppointmentId(app) +"'";
			
			con.createStatement().executeUpdate(query);
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	//Set hasAgreed to 1 for user on appointment
	public void agreedAppointment(String user, Appointment app){
		try{
			String query = 	"UPDATE person_appointment "+
							"SET hasAgreed = '1' "+
							"WHERE person_appointment_person_idPerson ='"+ getPersonId(user) +"' "+
							"AND person_appointment_appointment_idAppointment ='"+ getAppointmentId(app)+"'";
			con.createStatement().executeUpdate(query);
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	//Add a user to appointment 
	public void createPersonAppointment(String user, Appointment app){
		try{
			String query = "INSERT INTO person_appointment (appointment_idAppointment,person_idPerson) " +
						   "VALUES ('"+ getAppointmentId(app) +"','"+ getPersonId(user) +"')";
			con.createStatement().executeUpdate(query);
			
		} catch (SQLException e) { e.printStackTrace(); }
	}

	//Delete a user from appointment
	public void deletePersonAppointment(String user, Appointment app){
		try{
			String query = 	"DELETE FROM person_appointment "+
							"WHERE appointment_idAppointment='"+ getAppointmentId(app) +"' "+
							"AND person_idPerson = '"+ getPersonId(user) +"'";
			
			con.createStatement().executeUpdate(query);
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	//Add alarm with user and appointment
	public void addAlarm(String user, Appointment app, Alarm alarm){
		try{
			String time = alarm.getAlarm().toLocalDate().toString() +" "+ alarm.getAlarm().toLocalTime().toString();
			String query = 	"INSERT INTO alarm (time,person_appointment_appointment_idAppointment,person_appointment_person_idPerson) " +
							"VALUES ('"+ time +
						   			"','"+ getAppointmentId(app) +
						   			"','"+ getPersonId(user) +"')";
			
			con.createStatement().executeUpdate(query);
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	//Changes alarm with user and appointment
	public void changeAlarm(String user, Appointment app, Alarm alarm){
		try{
			String time = alarm.getAlarm().toLocalDate().toString() +" "+ alarm.getAlarm().toLocalTime().toString();
			String query = "UPDATE alarm " +
						   "SET time = '"+ time +"' " +
						   "WHERE person_appointment_appointment_idAppointment = '"+getAppointmentId(app)+"' " +
						   "AND person_appointment_person_idPerson = '"+getPersonId(user)+"'";
			
			con.createStatement().executeUpdate(query);
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	//Delete alarm from user with time
	public void deleteAlarm(String user, Alarm alarm){
		try{
			String time = alarm.getAlarm().toLocalDate().toString() +" "+ alarm.getAlarm().toLocalTime().toString();
			String query = "DELETE FROM alarm " +
						   "WHERE time = '"+ time + "' " +
						   "AND person_appointment_person_idPerson = '"+ getPersonId(user) +"'";
			
			con.createStatement().executeUpdate(query);
			
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	//Get alarm on an appointment
	public Alarm getAlarmOnAppointment(String user,Appointment app){
		try{
			String query = 	"SELECT time " +
							"FROM alarm " +
							"WHERE person_appointment_appointment_idAppointment = '"+ getAppointmentId(app) +"' " +
							"AND person_appointment_person_idPerson = '"+ getPersonId(user) +"'";
			
			ResultSet res = con.createStatement().executeQuery(query);
			
			
			if(res.next()){
				String [] temp = res.getString(1).split(" ");
				int [] temp2 = toInt(temp[0].split("-"));
				int [] temp3 = toInt(temp[1].split(":"));
				
				return new Alarm(new DateTime(temp2[0],temp2[1],temp2[2],temp3[0],temp3[1],temp3[2]));
			}				
		}
		catch (Exception e) { e.printStackTrace(); }
		return null;
	}
	
	//Get list of alarm with date
	public ArrayList<Alarm> getAlarmOnDate(String[] keyword){
		ArrayList<Alarm> alarmList = new ArrayList<Alarm>();
		
		try{
			String query = 	"SELECT time " +
							"FROM alarm " +
							"WHERE person_appointment_person_idPerson = '"+ getPersonId( keyword[0] ) +"' " +
							"AND time LIKE '"+ keyword[1] +"%' " +
							"ORDER BY time ASC";
			
			ResultSet res = con.createStatement().executeQuery(query);
			
			while(res.next()){
				String [] temp = res.getString(1).split(" ");
				int [] date = toInt(temp[0].split("-"));
				int [] time = toInt(temp[1].substring(0, 7).split(":"));
				
				alarmList.add( new Alarm( new DateTime(date[0],date[1],date[2],time[0],time[1],time[2]) ) );
			}	
	
			return alarmList;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//Parse String[] to int[]
	private int[] toInt(String[] s){
		int[] newInt = new int[s.length];
		for(int i=0; i<s.length;i++)
			newInt[i] = Integer.parseInt(s[i]);
		
		return newInt;	
	}
	
	//Return idAppointment from appointment
	private String getAppointmentId(Appointment app){
		try{
			String query = 	"SELECT idAppointment " +
							"FROM appointment " +
							"WHERE title='" + app.getTitle() + "' " +
							"AND sTime='" + app.getStart().toLocalTime().toString() + "' " +
							"AND eTime='" + app.getEnd().toLocalTime().toString() + "' " +
							"AND date='" + app.getStart().toLocalDate().toString() + "' " +
							"AND description='" + app.getDescription() + "' " +
							"AND admin = '" + app.getAdmin() + "'";
			ResultSet res = con.createStatement().executeQuery(query);
			
			if(res.next()) return res.getString(1);
		}
		catch (Exception e) { e.printStackTrace(); }
		return null;
	}
	
	//Return idPerson from a user
	private String getPersonId(String user){
		try{
			String query = 	"SELECT idPerson " +
							"FROM person " +
							"WHERE username = '"+ user +"'";
			ResultSet res = con.createStatement().executeQuery(query);
			
			if(res.next()) return res.getString(1);
		}
		catch (Exception e) { e.printStackTrace(); }
		return null;
	}
	
	//Return idRoom from a Room
	private String getRoomId(Room room) {
		if(room == null){
			return "1";
		}
		try{
			String query = 	"Select idRoom " +
							"FROM room " +
							"WHERE name = '"+ room.getName() +"'";
			ResultSet res = con.createStatement().executeQuery(query);
			
			if(res.next()) return res.getString(1);
		}
		catch (Exception e) { e.printStackTrace(); }
		return null;
	}
	
	//Return String[] with information from given room
	private String[] getRoom(int idRoom){
		try {
			String query = 	"SELECT capacity, name " +
							"FROM room " +
							"WHERE idRoom = "+ idRoom;
				
			ResultSet res = con.createStatement().executeQuery(query);
			
			if(res.next()){
				String s = res.getString(1)+":"+res.getString(2);
				return s.split(":");
			}
		
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}

	//For testing
	public static void main(String [] args) throws Exception{

		Database db = new Database();
		
		String[] keyword = new String[2];
		keyword[0] = "test";
		keyword[1] = "2013-03-14";
		
	}

	public ArrayList<Room> fetchRooms(String[] keyword) {
		//Henter ut alle ledige rom mellom et gitt tidspunkt. 
		String date = keyword[0];
		String start = keyword[1];
		String end = keyword[2];
		ArrayList<Room> rooms = new ArrayList<Room>();
		try{
			String query = "SELECT r.capacity,r.name " +
					"FROM room as r, appointment as a " +
					"WHERE a.sTime  not between '"+start+"' AND '"+end+"' " +
					"AND a.eTime not between '"+start+"' AND '"+end+"' " +
					"AND a.date != '"+date+"' " +
					"AND a.room_idRoom = r.idRoom " +
					"GROUP BY r.name";
			ResultSet res = con.createStatement().executeQuery(query);
			while(res.next()){
				rooms.add(new Room(Integer.parseInt(res.getString(1)), res.getString(2)));
			}
			return rooms;
		}
		catch (Exception e) {
			System.out.println("Can't fetch rooms from database in that period");
		}
		return null;
	}
}
