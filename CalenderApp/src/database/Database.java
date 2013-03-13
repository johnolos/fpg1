package database;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
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
	public void registerUser(String username, String password, String firstName, String lastName, String email, String tlf){
		try {
			String query = "" + 
					"INSERT INTO person(username, password, firstName, lastName, email, telefon) " +
					"VALUES ('"+ username +"',SHA1('"+ password +"'),'"+ firstName +"','"+ lastName +"','"+ email +"','"+ tlf +"')";
				
			con.createStatement().executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in query for updating database");
		}
	}
	
	//Confirms that a person with this username and password exists
	public boolean login(String username, String password){
		try {
			String query = "" +
					"SELECT username " +
					"FROM person " +
					"WHERE username='"+ username +"' AND password=SHA1('"+ password +"')";
				
			ResultSet res = con.createStatement().executeQuery(query);
			
			if(res.next()) return true;
			else return false;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in login query");
		}
		return false;
	}
	
	public Person getPerson(String username){
		try {
			String query = "" +
					"SELECT username, email, firstName, lastName " +
					"FROM person " +
					"WHERE username='"+ username +"'";
				
			ResultSet res = con.createStatement().executeQuery(query);
			
			while(res.next()){
				return new Person(res.getString(1), res.getString(2), res.getString(3), res.getString(4));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in getPerson query");
		}
		return null;
	}
	
	//Get all appointments from a person
	public ArrayList<Appointment> getPersonAppointments(String username){
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		
		try {
			String query = "" +
					"SELECT a.* " +
					"FROM appointment a, person_appointment pha, person p " +
					"WHERE a.idAppointment = pha.appointment_idAppointment " +
					"AND pha.person_idPerson=p.idPerson AND p.username='"+ username +"'";
				
			ResultSet res = con.createStatement().executeQuery(query);
			
			//Add all appointments
			while(res.next()){
				/*
				 * 1 = idAppointment
				 * 2 = title
				 * 3 = sTime (Start time)
				 * 4 = eTime (End time)
				 * 5 = date
				 * 6 = description
				 * 7 = location
				 * 8 = admin
				 * 9 = room_idRoom
				 */
				
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
				
				//Add appointment
				appointments.add(new Appointment(startTime, endTime, location, title, room, description, admin));
			}
			return appointments;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in getAppontment query");
		}
		return null;
	}
	
	//Delete given appointment from database
	public void deleteAppointment(Appointment app,Person person){
		try {
			String query = "" +
					"DELETE FROM appointment " +
					"WHERE idAppointment = '"+getAppointmentId(app)+"' AND admin = '"+person.getUsername()+"'";
				
			con.createStatement().executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in delete appointment query");
		}
	}
	
	/*
	 * Return String[] med length 5
	 * 0 = username
	 * 1 = email
	 * 2 = firstName
	 * 3 = lastName
	 * 4 = telefon
	 */
	private String[] getPerson(int idPerson){
		String[] person = new String[5];
		
		try {
			String query = "" +
					"SELECT username, email, firstName, lastName, telefon " +
					"FROM person " +
					"WHERE idPerson='"+ idPerson +"'";
				
			ResultSet res = con.createStatement().executeQuery(query);
			
			res.next();
			person[0] = res.getString(1);
			person[1] = res.getString(2);
			person[2] = res.getString(3);
			person[3] = res.getString(4);
			person[4] = res.getString(5);
			
			return person;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in getPerson query");
		}
		return person;
	}
	
	/*
	 * Return String[] med length 2
	 * 0 = capacity
	 * 1 = name
	 */
	private String[] getRoom(int idRoom){
		try {
			String query = "" +
					"SELECT capacity, name " +
					"FROM room " +
					"WHERE idRoom = "+ idRoom;
				
			ResultSet res = con.createStatement().executeQuery(query);
			
			res.next();
			String s = res.getString(1)+":"+res.getString(2);
			return s.split(":");
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in room selection query");
		}
		return new String[0];
	}
	
	//Parse String[] to int[]
	private int[] toInt(String[] s){
		int[] newInt = new int[s.length];
		for(int i=0; i<s.length;i++)
			newInt[i] = Integer.parseInt(s[i]);
		
		return newInt;	
	}
	
	public void print(){
		try {
			String query = "SELECT * FROM person";
				
			ResultSet res = con.createStatement().executeQuery(query);
			
			while(res.next()){
				System.out.println(
						res.getString(1) +" "+ 
						res.getString(2) +" "+ 
						res.getString(3)
				);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in query");
		}
	}
	
	public void createAppointment(Appointment app){
		try {
			String query = "INSERT INTO appointment (title,sTime,eTime,date,description,location,admin,room_idRoom)" +
					" VALUES ('"+app.getTitle()+"', '"+new Time(app.getStart().toLocalTime().getMillisOfDay())+"', " +
							"'"+new Time(app.getEnd().toLocalTime().getMillisOfDay())+"'," +
							"'"+new Date(app.getStart().toLocalDate().toDate().getTime())+"','"+app.getDescription()+"'" +
							",'"+app.getLocation()+"','"+app.getAdmin()+"','"+getRoomId(app.getRoom())+"')";
			
			con.createStatement().executeUpdate(query);
			
			createPersonAppointment(app,app.getAdmin());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in query for creating appointment in database");
		}
		
	}
	
	private String getRoomId(Room room) {
		if(room == null){
			return "1";
		}
		try{
			String query = "Select idRoom " +
					"FROM room " +
					"WHERE name = '"+room.getName()+"'";
			ResultSet res = con.createStatement().executeQuery(query);
			
			while(res.next()){
				return res.getString(1);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERRORR in getting roomID");
		}
		return null;
	}

	public void changeAppointment(Appointment app){
		try {
			String query =  "UPDATE appointment " +
							"SET title ='"+app.getTitle()+"', sTime='"+new Time(app.getStart().toLocalTime().getMillisOfDay())+"'," +
							"eTime='"+new Time(app.getEnd().toLocalTime().getMillisOfDay())+"', " +
							"date='"+new Date(app.getStart().toLocalDate().toDate().getTime())+"'," +
							"description='"+app.getDescription()+"',location='"+app.getLocation()+"'," +
							"admin='"+app.getAdmin()+"',room_idRoom='"+getRoomId(app.getRoom())+"' " +
							"WHERE title='"+ app.getTitle() + "' " +
							"AND sTime='"+ new Time(app.getStart().toLocalTime().getMillisOfDay()) +"' " +
							"AND eTime='"+ new Time(app.getEnd().toLocalTime().getMillisOfDay()) +"' " +
							"AND date='"+ new Date(app.getStart().toLocalDate().toDate().getTime()) +"' " +
							"AND description='"+ app.getDescription() +"' " +
							"AND admin = '"+app.getAdmin()+"'";
			
			con.createStatement().executeUpdate(query);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR in query for changing appointment in database");
		}
	}
	
	private String getAppointmentId(Appointment app){
		//System.out.println(app.getStart());
		try{
			String query = "SELECT idAppointment " +
							"FROM appointment " +
							"WHERE title='"+ app.getTitle() + "' " +
							"AND sTime='"+ new Time(app.getStart().toLocalTime().minusHours(0).getMillisOfDay()) +"' " +
							"AND eTime='"+ new Time(app.getEnd().toLocalTime().minusHours(0).getMillisOfDay()) +"' " +
							"AND date='"+ new Date(app.getStart().toLocalDate().toDate().getTime()) +"' " +
							"AND description='"+ app.getDescription() +"' " +
							"AND admin = '"+app.getAdmin()+"'";
			ResultSet res = con.createStatement().executeQuery(query);
			
			res.next();
			return res.getString(1);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR while getting appID");
		}
		return null;
	}

	public void agreedAppointment(Appointment app, Person person){
		try{
			String query = "UPDATE person_appointment  " +
							"SET hasAgreed = 1";
			con.createStatement().executeUpdate(query);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR while changed agreed in database");
		}
	}

	private String getPersonId(String person){

		try{
			String query = "SELECT idPerson " +
							"FROM person " +
							"WHERE username = '"+person+"'";
			ResultSet res = con.createStatement().executeQuery(query);
			res.next();
			return res.getString(1);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR while getting idPerson");
		}
		return null;
	}
	
	public void createPersonAppointment(Appointment app,String person){
		try{
			String query = "INSERT INTO person_appointment (appointment_idAppointment,person_idPerson) " +
						   "VALUES ('"+getAppointmentId(app)+"','"+getPersonId(person)+"')";
			con.createStatement().executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in query for updating person_appointment in database");
		}
	}

	public void deletePersonAppointment(Appointment app,Person person){
		try{
			String query = "DELETE FROM person_appointment " +
							"WHERE appointment_idAppointment='"+getAppointmentId(app)+"' " +
							"AND person_idPerson = '"+getPersonId(person.getUsername())+"'";
			
			con.createStatement().executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in query for updating person_appointment in database");
		}
	}
	public void addAlarm(DateTime time,Appointment app, Person person){
		try{
			String query = "INSERT INTO alarm (time,person_appointment_appointment_idAppointment,person_appointment_person_idPerson) " +
						   "VALUES ('"+new Date(time.toLocalDate().toDate().getTime())+"'" +
						   	",'"+getAppointmentId(app)+"','"+getPersonId(person.getUsername())+"')";
			
			con.createStatement().executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in query for adding alarm in database");
		}
	}
	
	public void changeAlarm(DateTime time,Appointment app, Person person){
		try{
			String query = "UPDATE alarm " +
						   "SET time = '"+new Date(time.toLocalDate().toDate().getTime())+"' " +
						   "WHERE person_appointment_appointment_idAppointment = '"+getAppointmentId(app)+"' AND " +
						   	"person_appointment_person_idPerson = '"+getPersonId(person.getUsername())+"'";
			
			con.createStatement().executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in query for updating alarm in database");
		}
	}
	
	public void deleteAlarm(Alarm alarm,Person person){
		try{
			String query = "DELETE FROM alarm " +
						   "WHERE time = '"+alarm.getAlarm()+"' " +
						   "AND person_appointment_person_idPerson = '"+getPersonId(person.getUsername())+"'";
			
			con.createStatement().executeUpdate(query);
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR in query for deleting alarm in database");
		}
	}
	
	public Alarm getAlarm(Person person,Appointment app){
		try{
			String query = "SELECT time FROM alarm WHERE person_appointment_appointment_idAppointment = '"+getAppointmentId(app)+"' " +
							"AND person_appointment_person_idPerson = '"+getPersonId(person.getUsername())+"'";
			ResultSet res = con.createStatement().executeQuery(query);
			res.next();
			String [] temp = res.getString(1).split(" ");
			int [] temp2 = toInt(temp[0].split("-"));
			int [] temp3 = toInt(temp[1].split(":"));
			
			
			return new Alarm(new DateTime(temp2[0],temp2[1],temp2[2],temp3[0],temp3[1],temp3[2]));
								
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR while getting alarm");
		}
		return null;
	}
	public ArrayList<Appointment> getDateAppointments(Person person,DateTime date2){
		ArrayList<Appointment> appointments = new ArrayList<Appointment>();
		
		try {
			String query = "" +
					"SELECT a.* " +
					"FROM appointment a, person_appointment pha, person p " +
					"WHERE a.idAppointment = pha.appointment_idAppointment " +
					"AND pha.person_idPerson=p.idPerson AND p.username='"+ person.getUsername() +" " +
					"AND date = '"+new Date(date2.toLocalDate().toDate().getTime())+"'";
				
			ResultSet res = con.createStatement().executeQuery(query);
			
			//Add all appointments
			while(res.next()){
				/*
				 * 1 = idAppointment
				 * 2 = title
				 * 3 = sTime (Start time)
				 * 4 = eTime (End time)
				 * 5 = date
				 * 6 = description
				 * 7 = location
				 * 8 = admin
				 * 9 = room_idRoom
				 */
				
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
				
				//Add appointment
				appointments.add(new Appointment(startTime, endTime, location, title, room, description, admin));
			}
			return appointments;
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in getAppontment query");
		}
		return null;
	}
	
	//For testing
	public static void main(String [] args) throws Exception{

		Database db = new Database();
		//db.registerUser("Hans", "test", "Hansf", "Olav", "hans@", "41638760");
		/*if(db.login("Hans", "test")){
			System.out.println("Jess");;
		}*/
		//System.out.println(db.getPerson("Hans").getEmail());
		db.createAppointment(new Appointment(new DateTime(2013,05,05,12,0), new DateTime(2013,05,05,13,0), "Jkefd", "Test", null, "Ingen", "Hans"));
		//db.agreedAppointment(db.getPersonAppointments("Hans").get(0),db.getPerson("Hans"));
		//System.out.println(db.getPersonAppointments("Hans").get(0).getTitle());
		//db.deleteAppointment(db.getPersonAppointments("Hans").get(0),db.getPerson("Hans"));
		
	}
}
	


