package database;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import baseClasses.Alarm;
import baseClasses.Appointment;
import baseClasses.Notification;
import baseClasses.NotificationEnum;
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
	
	/**
	 * Register a user in the database
	 * @param keyword username, password, firstName, lastName, email, telephone
	 * @return Boolean
	 */
	public Boolean registerUser(String[] keyword){
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
	
	/**
	 * Confirms that a person with username and password exists
	 * @param keyword username, password
	 * @return Person
	 */
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
	
	/**
	 * Find persons with search word
	 * @param keyword search word
	 * @return ArrayList<Person>
	 */
	public ArrayList<Person> getPerson(String[] keyword){
		ArrayList<Person> personList = new ArrayList<Person>();
		try {
			String query = 	"SELECT username, email, firstName, lastName " +
							"FROM person " +
							"WHERE username LIKE '%" + keyword[0] + "%' " +
							"OR firstName LIKE '%" + keyword[0] + "%' " +
							"OR lastName LIKE '%" + keyword[0] + "%' " +
							"ORDER BY lastName ASC";
				
			ResultSet res = con.createStatement().executeQuery(query);
			
			while(res.next()){
				personList.add( new Person(res.getString(1), res.getString(2), res.getString(3), res.getString(4)) );
			}
			return personList;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	/**
	 * Get a list for what room is free on date at time
	 * @param keyword date,startTime,endTime
	 * @return ArrayList<Room>
	 */
	public ArrayList<Room> getRoomOnTime(String[] keyword){
		System.out.println(keyword[0] + "  "+ keyword[1] + " " + keyword[2]);
		ArrayList<Room> roomList = new ArrayList<Room>();
		
		try {
			String query = 	"SELECT capacity, name " +
							"FROM room " +
							"WHERE idRoom NOT IN( " +
								"SELECT idRoom FROM room, appointment " +
								"WHERE idRoom=room_idRoom " +
								"AND date='" + keyword[0] + "' " +
								"AND (sTime BETWEEN CAST('" + keyword[1] +"' AS TIME) AND CAST('"+ keyword[2] +"' AS TIME) " +
								"OR eTime BETWEEN CAST('" + keyword[1] +"' AS TIME) AND CAST('"+ keyword[2] +"' AS TIME))) " +
							"ORDER BY capacity ASC";
				
			ResultSet res = con.createStatement().executeQuery(query);
			
			while(res.next()){
				roomList.add( new Room(Integer.parseInt(res.getString(1)), res.getString(2)) );
			}
			
			if(roomList.isEmpty())
				roomList.add(0, new Room(0, "Ingen"));
			else if(!roomList.get(0).getName().equals("Ingen rom"))
				roomList.add(0, new Room(0, "Ingen rom"));
			
			return roomList;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	/**
	 * Get  a list with all appointments from user
	 * @param keyword username
	 * @return ArrayList<Appointment>
	 */
	public ArrayList<Appointment> getAppointmentsOnPerson(String[] keyword){
		String query = 	"SELECT a.* " +
						"FROM appointment a, person_appointment pha, person p " +
						"WHERE a.idAppointment = pha.appointment_idAppointment " +
						"AND pha.person_idPerson=p.idPerson AND p.username='"+ keyword[0] +"' " +
						"ORDER BY a.date ASC";
		
		return getAppointmentQuery(query);
	}
	
	/**
	 * Get a list of Appointment from user with date
	 * @param keyword user, date
	 * @return ArrayList<Appointment>
	 */
	public ArrayList<Appointment> getAppointmentsOnDate(String[] keyword){
		String query = 	"SELECT a.* " +
						"FROM appointment a, person_appointment pa, person p " +
						"WHERE a.idAppointment = pa.appointment_idAppointment " +
						"AND pa.person_idPerson = p.idPerson " +
						"AND p.username = '"+ keyword[0] +"' " +
						"AND date = '"+ keyword[1] +"' " +
						"ORDER BY a.date ASC";
				
		return getAppointmentQuery(query);			
	}
	
	/**
	 * Get a list of appointment from query
	 * @param query
	 * @return ArrayList<Appointment>
	 */
	private ArrayList<Appointment> getAppointmentQuery(String query){
		ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();
		
		try {		
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
				Appointment newApp = new Appointment(startTime, endTime, location, title, room, description, admin); 
				newApp.setParticipants(getMembersOnAppointment(newApp));
				appointmentList.add(newApp);
			}
			return appointmentList;
			
		} catch (SQLException e) { 
			e.printStackTrace();
			System.out.println("ERROR in query for appointments");
		}
		return null;
	}
	
	/**
	 * Delete appointment from database
	 * @param user
	 * @param app
	 */
	public void deleteAppointment(String user, Appointment app){
		try {
			String query = 	"DELETE FROM appointment " +
							"WHERE idAppointment = '"+ getAppointmentId(app) +"' " +
							"AND admin = '"+ user +"'";
				
			con.createStatement().executeUpdate(query);
			
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	/**
	 * Create an appointment
	 * @param app
	 * @return Boolean
	 */
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
			agreedAppointment(app.getAdmin(), app);
			return true;
		} catch (SQLException e) {
			e.printStackTrace(); }
		return false;
		
	}

	/**
	 * Change appointment to new values
	 * @param newApp
	 * @param oldApp
	 * @return Boolean
	 */
	public Boolean changeAppointment(Appointment newApp, Appointment oldApp){
		try {
			String query =  "UPDATE appointment " +
							"SET title ='"+ newApp.getTitle() +
							"', sTime ='"+ newApp.getStart().toLocalTime().toString() +
							"', eTime ='"+ newApp.getEnd().toLocalTime().toString() +
							"', date ='"+ newApp.getStart().toLocalDate().toString() +
							"', description ='"+ newApp.getDescription() +
							"', location ='"+ newApp.getLocation() +
							"', admin ='"+ newApp.getAdmin() +
							"', room_idRoom ='"+ getRoomId(newApp.getRoom()) + "' " +
							"WHERE idAppointment='"+ getAppointmentId(oldApp) +"'";
			
			con.createStatement().executeUpdate(query);
			return true;
		}
		catch (Exception e) { e.printStackTrace(); }
		return false;
	}

	/**
	 * Set user has agreed to an appointment
	 * @param user
	 * @param app
	 * @return Boolean
	 */
	public Boolean agreedAppointment(String user, Appointment app){
		try{
			String query = 	"UPDATE person_appointment "+
							"SET hasAgreed = '1' "+
							"WHERE person_idPerson ='"+ getPersonId(user) +"' "+
							"AND appointment_idAppointment ='"+ getAppointmentId(app)+"'";
			con.createStatement().executeUpdate(query);
			deleteNotification(new String[] {user,app.getAdmin()}, app);
			return true;
		}
		catch (Exception e) { e.printStackTrace(); }
		return false;
	}

	/**
	 * Add a user to appointment
	 * @param user
	 * @param app
	 * @return Boolean
	 */
	public Boolean createPersonAppointment(String user, Appointment app){
		try{
			String query = "INSERT INTO person_appointment (appointment_idAppointment,person_idPerson) " +
						   "VALUES ('"+ getAppointmentId(app) +"','"+ getPersonId(user) +"')";
			con.createStatement().executeUpdate(query);
			
			String [] keyword = {"INVITATION",user, app.getAdmin()};
			createNotification(keyword, app);
			
			return true;
			
		} catch (SQLException e) { e.printStackTrace(); }
		return false;
	}
	
	/**
	 * Get a list of persons with an appointment
	 * @param app
	 * @return ArrayList<Person>
	 */
	public ArrayList<Person> getMembersOnAppointment(Appointment app){
		ArrayList<Person> personList = new ArrayList<Person>();
		try {
			String query = 	"SELECT username, email, firstName, lastName " +
							"FROM person " +
							"WHERE idPerson IN (" +
								"SELECT idPerson FROM person, person_appointment " +
								"WHERE idPerson = person_idPerson " +
								"AND appointment_idAppointment = '"+ getAppointmentId(app) +"') "+
							"ORDER BY lastName ASC";
				
			ResultSet res = con.createStatement().executeQuery(query);
			
			while(res.next()){
				personList.add( new Person(res.getString(1), res.getString(2), res.getString(3), res.getString(4)) );
			}
			return personList;
			
		} catch (SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	/**
	 * Delete a user from an appointment
	 * @param user
	 * @param app
	 * @return Boolean
	 */
	public Boolean deletePersonAppointment(String user, Appointment app){
		try{
			String query = 	"DELETE FROM person_appointment "+
							"WHERE appointment_idAppointment='"+ getAppointmentId(app) +"' "+
							"AND person_idPerson = '"+ getPersonId(user) +"'";
			
			con.createStatement().executeUpdate(query);
			
			if(user != app.getAdmin()){
				deleteNotification(new String[] {user,app.getAdmin()}, app);
				createNotification(new String[] {"DECLINED",user,app.getAdmin()}, app);
			}
			return true;
			
		} catch (SQLException e) { e.printStackTrace(); }
		return false;
	}
	
	/**
	 * Add alarm with user and appointment
	 * @param user
	 * @param appointment
	 * @param alarm
	 */
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
	
	/**
	 * Changes alarm with user and appointment
	 * @param user
	 * @param appointment
	 * @param alarm
	 */
	public Boolean changeAlarm(String user, Appointment app, Alarm alarm){
		String time = alarm.getAlarm().toLocalDate().toString() +" "+ alarm.getAlarm().toLocalTime().toString();
		String query = "UPDATE alarm " +
						   "SET time = '"+ time +"' " +
						   "WHERE person_appointment_appointment_idAppointment = '"+getAppointmentId(app)+"' " +
						   "AND person_appointment_person_idPerson = '"+getPersonId(user)+"'";
			
		return executeUpdate(query);
	}
	
	/**
	 * Delete alarm from user with time
	 * @param user
	 * @param alarm
	 */
	public void deleteAlarm(String user, Alarm alarm){
		try{
			String time = alarm.getAlarm().toLocalDate().toString() +" "+ alarm.getAlarm().toLocalTime().toString();
			String query = "DELETE FROM alarm " +
						   "WHERE time = '"+ time + "' " +
						   "AND person_appointment_person_idPerson = '"+ getPersonId(user) +"'";
			
			con.createStatement().executeUpdate(query);
			
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	/**
	 * Get alarm on an appointment
	 * @param user
	 * @param appointment
	 * @return Alarm
	 */
	public Alarm getAlarmOnAppointment(String user,Appointment app){
		String query = 	"SELECT time " +
						"FROM alarm " +
						"WHERE person_appointment_appointment_idAppointment = '"+ getAppointmentId(app) +"' " +
						"AND person_appointment_person_idPerson = '"+ getPersonId(user) +"'";
			
		ResultSet res = executeQuery(query);
		
		try{
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
	
	/**
	 * Get list of alarm with date
	 * @param keyword username, time
	 * @return ArrayList<Alarm>
	 */
	public ArrayList<Alarm> getAlarmOnDate(String[] keyword){
		ArrayList<Alarm> alarmList = new ArrayList<Alarm>();
		
		String query = 	"SELECT time " +
						"FROM alarm " +
						"WHERE person_appointment_person_idPerson = '"+ getPersonId( keyword[0] ) +"' " +
						"AND time LIKE '"+ keyword[1] +"%' " +
						"ORDER BY time ASC";
			
		ResultSet res = executeQuery(query);
		
		try{
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
	
	/**
	 * Create a Notification to a person with appointment and type
	 * @param keyword type, toUser, fromUser
	 * @param appointment
	 * @return Boolean
	 */
	public Boolean createNotification(String[] keyword, Appointment app){
		String query = 	"INSERT INTO notification(type,idPerson,idAppointment, fromPerson) " +
						"VALUES('"+keyword[0]+"','"+ getPersonId(keyword[1]) +"','"+ getAppointmentId(app) +"','"+ keyword[2]+ "')";
			
		return executeUpdate(query);
	}

	
	public ArrayList<Notification> getNotification(String user){
		ArrayList<Notification> noteList = new ArrayList<Notification>();
		
		String query = "Select type, a.idAppointment, fromPerson " +
						"FROM notification as n, appointment as a " +
						"WHERE n.idAppointment = a.idAppointment  AND n.idPerson = '"+getPersonId(user)+"'";
		ResultSet res = executeQuery(query);
		
		try{	
			while(res.next()){
				if(res.getString(1) == "DECLINED"){
					Appointment app = getAppointment(Integer.parseInt(res.getString(2)));
					app.setAdmin(res.getString(3));
					noteList.add(new Notification(getEnum(res.getString(1)), app));
				}
				else{
					Appointment app = getAppointment(Integer.parseInt(res.getString(2)));
					noteList.add(new Notification(getEnum(res.getString(1)), app));
				}
			}
			return noteList;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR when getting notifications from user");
		}
		return null;
	}
	
	/**
	 * Delete notification 
	 * @param keyword toUser, fromUser
	 * @param app
	 * @return Boolean
	 */
	public Boolean deleteNotification(String [] keyword,Appointment app){
		String query = 	"DELETE FROM notification " +
						"WHERE idPerson = '"+getPersonId(keyword[0])+"' " +
						"AND idAppointment = '"+getAppointmentId(app)+"' " +
						"AND fromPerson = '"+keyword[1]+"'";
		
		return executeUpdate(query);
	}
	
	/**
	 * Get integer array from string array
	 * @param s
	 * @return int[]
	 */
	private int[] toInt(String[] s){
		int[] newInt = new int[s.length];
		for(int i=0; i<s.length;i++)
			newInt[i] = Integer.parseInt(s[i]);
		
		return newInt;	
	}
		
	/**
	 * Get appointment from id
	 * @param idAppointment
	 * @return Appointment
	 */
	private Appointment getAppointment(int idAppointment){
		String query = 	"SELECT * " +
						"FROM appointment " +
						"WHERE idAppointment ='"+ idAppointment +"'";
							
		ResultSet res = executeQuery(query);
				
		return toAppointment(res).get(0);
	}
	
	/**
	 * Get id from appointment
	 * @param app
	 * @return String
	 */
	private String getAppointmentId(Appointment app){
		String query = 	"SELECT idAppointment " +
						"FROM appointment " +
						"WHERE title='" + app.getTitle() + "' " +
						"AND sTime='" + app.getStart().toLocalTime().toString() + "' " +
						"AND eTime='" + app.getEnd().toLocalTime().toString() + "' " +
						"AND date='" + app.getStart().toLocalDate().toString() + "' " +
						"AND description='" + app.getDescription() + "' " +
						"AND admin = '" + app.getAdmin() + "'";
		ResultSet res = executeQuery(query);
		
		try{
			if(res.next()) return res.getString(1);
		} catch (Exception e) { e.printStackTrace(); }
		return null;
	}
	
	/**
	 * Get id from user
	 * @param user
	 * @return String
	 */
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
	
	/**
	 * Get id from room
	 * @param room
	 * @return String
	 */
	private String getRoomId(Room room) {
		if(room == null){
			return "1";
		}
		String query = 	"Select idRoom " +
						"FROM room " +
						"WHERE name = '"+ room.getName() +"'";
		ResultSet res = executeQuery(query);
		
		try{
			if(res.next()) return res.getString(1);
		} catch (Exception e) { 
			e.printStackTrace(); 
			System.out.println("ERROR in reading ResultSet for idRoom");
		}
		return null;
	}
	
	/**
	 * Get room from id
	 * @param idRoom
	 * @return String[] capacity, name
	 */
	private String[] getRoom(int idRoom){
		String query = 	"SELECT capacity, name " +
						"FROM room " +
						"WHERE idRoom = "+ idRoom;
		ResultSet res = executeQuery(query);
		
		try {
			if(res.next()){
				String s = res.getString(1)+":"+res.getString(2);
				return s.split(":");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in reading ResultSet");
		}
		return null;
	}
	
	/**
	 * Get enum from string
	 * @param type
	 * @return NotificationEnum
	 */
	private NotificationEnum getEnum(String type){
		switch(type){
		case "DECLINED": return NotificationEnum.DECLINED;
		case "INVITATION": return NotificationEnum.INVITATION;
		case "OKBOX": return NotificationEnum.OKBOX;
		default: return null;
		}
	}

	/**
	 * Get a list of appointment from ResultSet
	 * @param res
	 * @return ArrayList<Appointment>
	 */
	private ArrayList<Appointment> toAppointment(ResultSet res){
		ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();
		
		try {
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
				
				appointmentList.add(new Appointment(startTime, endTime, location, title, room, description, admin)); 
			}
			return appointmentList;
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Execute query on database
	 * @param query
	 * @return ResultSet
	 */
	private ResultSet executeQuery(String query){
		try {
			return con.createStatement().executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in executeQuery");
		}
		return null;
	}
	
	/**
	 * Execute update in database 
	 * @param query
	 */
	private Boolean executeUpdate(String query){
		try {
			con.createStatement().executeUpdate(query);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in executeUpdate");
		}
		return false;
	}
	
	public ArrayList<Room> fetchRooms(String[] keyword) {
		//Henter ut alle ledige rom mellom et gitt tidspunkt. 
		// Time hh:mm Date: YYYY/MM/DD
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
			System.out.println(rooms.get(0).getName());
			return rooms;
		}
		catch (Exception e) {
			System.out.println("Can't fetch rooms from database in that period");
		}
		return null;
	}
	
}
