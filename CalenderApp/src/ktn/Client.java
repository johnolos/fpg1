package ktn;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.joda.time.DateTime;


import baseClasses.Alarm;
import baseClasses.Appointment;
import baseClasses.Person;
import baseClasses.Room;
import baseClasses.Notification;

//TCP client
public class Client {
	
	private Socket connection;
	private final static String SERVERIP = "25.229.17.168";
	private final static int SERVERPORT = 4004;
	
	private ObjectOutputStream objectOutput;
	private ObjectInputStream objectInput;
	
	
	
	public Client() {
	}
	
	// Opens a connction
	public void connect() throws InterruptedException {
		this.startClient();
	}
	
	// Closes the connection
	public void close() {
		this.objectInput = null;
		this.objectOutput = null;
		try {
			this.connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.connection = null;
	}
	
	// Login function
	public Person login(String [] keyword) {
		//Creates requestObject
		SendObject reqObj = new SendObject(RequestEnum.LOGIN, keyword);
		//Sends object to server
		this.send(reqObj);
		//Returns object from server
		SendObject receivedObject = receive();
		if(!checkObject(RequestEnum.PERSON,receivedObject))
			try {
				throw new IOException("Login failed. Wrong object received from server");
			} catch (IOException e) {
				e.printStackTrace();
			}
		return (Person)receivedObject.getObject();	
	}
	
	// Check function to check the type of in-comming object
	private boolean checkObject(RequestEnum value, SendObject object) {
		if(object.getSendType() == value) {
			return true;
		}
		return false;
	}
	
	// Send function to send object over ObjectOutputStream
	private void send(RequestObjects reqObj) {
		try {
			this.objectOutput.writeObject(reqObj);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Client: Object couldn't be sent");
		}
	}
	
	private void send(SendObject obj) {
		try {
			this.objectOutput.writeObject(obj);
			System.out.println("sendt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Receive function to read object from ObjectInputStream
	private SendObject receive() {
		Object receivedObject = null;
		try {
			receivedObject = this.objectInput.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (SendObject)receivedObject;
	}
	
	// Fetches all notifications available to the specified user.
	public ArrayList<Notification> fetchNotifications(String username) {
		String[] keyword = {username};
		SendObject reqObj = new SendObject(RequestEnum.NOTIFICATION, keyword);
		this.send(reqObj);
		SendObject obj = this.receive();
		if(!checkObject(RequestEnum.NOTIFICATION,obj)) {
			try {
				throw new IOException("Request failed. Wrong object received from server");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ArrayList<Notification> notifications = (ArrayList<Notification>)obj.getObject();
		return notifications;
	}
	
	// Fetches all appointments at the specified date.
	public ArrayList<Appointment> fetchAppointments(String user, String date) {
		String[] keyword = {user,date};
		SendObject reqObj = new SendObject(RequestEnum.APPOINTMENT, keyword);
		this.send(reqObj);
		SendObject obj = this.receive();
		if(!checkObject(RequestEnum.APPOINTMENT,obj)) {
			try {
				throw new IOException("Request failed. Wrong object received from server");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ArrayList<Appointment> appointments = (ArrayList<Appointment>)obj.getObject();
		return appointments;
	}
	
	// Fetches all rooms available 
	public ArrayList<Room> fetchRooms(String date,String start, String end) { 
		// hh:mm - YYYY-MM-DD
		String[] keyword = {date, start, end};
		SendObject reqObj = new SendObject(RequestEnum.ROOM, keyword);
		this.send(reqObj);
		SendObject obj = this.receive();
		if(!checkObject(RequestEnum.ROOM, obj)) {
			try {
				throw new IOException("Request failed. Wrong object received from server");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ArrayList<Room> rooms = (ArrayList<Room>)obj.getObject();
		return rooms;
	}
	
	// Fetches Person with searchWord, if "" then everybody
	public ArrayList<Person> fetchPersons(String searchWord) {
		String[] keyword = {searchWord};
		SendObject reqObj = new SendObject(RequestEnum.PERSON, keyword);
		this.send(reqObj);
		SendObject obj = this.receive();
		if(!checkObject(RequestEnum.PERSON, obj)) {
			try {
				throw new IOException("Request failed. Retry query from server");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ArrayList<Person> persons = (ArrayList<Person>)obj.getObject();
		return persons;
	}
	
	// Fetches Alarms from a specific date
	public ArrayList<Alarm> fetchAlarms(String date, String user) {
		String[] keyword = {user, date};
		SendObject reqObj = new SendObject(RequestEnum.ALARM, keyword);
		this.send(reqObj);
		SendObject obj = this.receive();
		if(!checkObject(RequestEnum.ALARM, obj)) {
			try {
				throw new IOException("Login failed. Wrong object received from server");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ArrayList<Alarm> alarms = (ArrayList<Alarm>)obj.getObject();
		return alarms;
	}

	// Admin is stored in Appointment. Server gets the username from this field.
	public boolean createAppointment(Appointment app) {
		SendObject sendObj = new SendObject(RequestEnum.S_APPOINTMENT, app);
		this.send(sendObj);
		SendObject receivedObj = this.receive();
		if(!checkObject(RequestEnum.BOOLEAN,receivedObj))
			try {
				throw new IOException("Creating Appointment failed. Wrong object received from server");
			} catch (IOException e) {
				e.printStackTrace();
			}
		Boolean bol = (Boolean)receivedObj.getObject();
		return bol.booleanValue();	
	}
	
	// Create person from login-menu
	public boolean createPerson(Person person) {
		SendObject sendObject = new SendObject(RequestEnum.S_PERSON, person);
		this.send(sendObject);
		SendObject receivedObj = this.receive();
		if(!checkObject(RequestEnum.BOOLEAN,receivedObj))
			try {
				throw new IOException("Register Failed. Try again!");
			} catch (IOException e) {
				e.printStackTrace();
			}
		Boolean bol = (Boolean)receivedObj.getObject();
		return bol.booleanValue();
	}
	
	
	private void startClient() {
		try {
			// Create a TCP connection to Server/Client
			connection = new Socket(InetAddress.getByName(Client.SERVERIP), Client.SERVERPORT);
			// Connection established
			System.out.println("Connected to server");
			//Acquire the InputStream from Socket
			InputStream serverInputStream = connection.getInputStream();
			// Creating InputSteamReader from the InputStream
			InputStreamReader inFromServer = new InputStreamReader(serverInputStream);
			// Creating ObjectInputStream from InputStream
			this.objectInput = new ObjectInputStream(serverInputStream);
			// Acquiring OutputStream from connection
			OutputStream serverOutputStream = connection.getOutputStream();
			// Creating ObjectOutputStream from OutputStream
			this.objectOutput = new ObjectOutputStream(serverOutputStream);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws InterruptedException {
		new Client().connect();
	}
}
