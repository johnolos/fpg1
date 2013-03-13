package database;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

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
	private final static String PASSWORD = "bitnami";
	
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
	public void getPersonAppointments(String username){
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
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in getAppontment query");
		}
	}
	
	//Delete given appointment from database
	public void deleteAppointment(Appointment a){
		try {
			String query = "" +
					"DELETE FROM appointment " +
					"WHERE title='"+ a.getTitle() + "' " +
						"AND sTime='"+ new Time(a.getStart().toLocalTime().getMillisOfDay()) +"' " +
						"AND eTime='"+ new Time(a.getEnd().toLocalTime().getMillisOfDay()) +"' " +
						"AND date='"+ new Date(a.getStart().toLocalDate().toDate().getTime()) +"' " +
						"AND description='"+ a.getDescription() +"'";
				
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
	
	public void createAppointment(String title,String start, String end,String date,String description,String location, String admin,int roomId){
		try {
			String query = "INSERT INTO appointment (title,sTime,eTime,date,description,location,admin,room_idRoom)" +
					" VALUES ('"+title+"', '"+start+"', '"+end+"','"+date+"','"+description+"'" +
							",'"+location+"','"+admin+"','"+roomId+"')";
			
			con.createStatement().executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in query for creating appointment in database");
		}
		
	}
	
	public void changeAppointment(String idAppointment,String title,String start, String end,String date,String description,String location, String admin,int roomId){
		try {
			String query =  "UPDATE appointment " +
							"SET title ='"+title+"', sTime='"+start+"',eTime='"+end+"', " +
							"date='"+date+"',description='"+description+"',location='"+location+"'," +
							"admin='"+admin+"',room_idRoom='"+roomId+"' " +
							"WHERE idAppointment='"+idAppointment+"'";
			
			con.createStatement().executeUpdate(query);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR in query for changing appointment in database");
		}
	}
	
	public void createPersonAppointment(String appointmentID,String personID){
		try{
			String query = "INSERT INTO person_appointment (appointment_idAppointment,person_idPerson) " +
						   "VALUES ('"+appointmentID+"','"+personID+"')";
			con.createStatement().executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in query for updating person_appointment in database");
		}
	}
	
	public void addAlarm(String time,String appointmentID, String personID){
		try{
			String query = "INSERT INTO alarm (time,person_appointment_appointment_idAppointment,person_appointment_person_idPerson) " +
						   "VALUES ('"+time+"','"+appointmentID+"','"+personID+"')";
			con.createStatement().executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in query for adding alarm in database");
		}
	}
	
	public void changeAlarm(String time,String appointmentID, String personID){
		try{
			String query = "UPDATE alarm " +
						   "SET time = '"+time+"' " +
						   "WHERE person_appointment_appointment_idAppointment = '"+appointmentID+"' AND " +
						   	"person_appointment_person_idPerson = '"+personID+"'";
			
			con.createStatement().executeUpdate(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("ERROR in query for updating alarm in database");
		}
	}
	
	public void deleteAlarm(String idAlarm){
		try{
			String query = "DELETE FROM alarm " +
						   "WHERE idAlarm = '"+idAlarm+"'";
			
			con.createStatement().executeUpdate(query);
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR in query for deleting alarm in database");
		}
	}
	
	
	//For testing
	public static void main(String [] args) throws Exception{
		Database db = new Database();
		db.getPersonAppointments("test");
	}
}
	


