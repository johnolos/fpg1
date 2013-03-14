package ktn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import ktn.RequestEnum;
import ktn.RequestObjects;
import ktn.SendObject;

import baseClasses.Alarm;
import baseClasses.Appointment;
import baseClasses.Person;
import baseClasses.Room;

//TCP client
public class Client {
	
	private Socket connection;
	private final static String SERVERIP = "78.91.62.42";
	private final static int SERVERPORT = 4004;
	
	private ObjectOutputStream objectOutput;
	private ObjectInputStream objectInput;
	
	
	public Client() {

	}
	
	// Opens a connction
	public void connect() {
		this.startClient();
	}
	
	// Closes the connection
	public void close() {
		
	}
	
	// Login function
	public boolean login(String username, String password) {
		String[] keyword = {username,password};
		RequestObjects reqObj = new RequestObjects(RequestEnum.LOGIN, keyword);
		this.send(reqObj);
		SendObject receivedObject = receive();
		if(!checkObject(RequestEnum.BOOLEAN,receivedObject))
			try {
				throw new IOException("Login failed. Wrong object received from server");
			} catch (IOException e) {
				e.printStackTrace();
			}
		Boolean bol = (Boolean)receivedObject.getObject();
		return bol.booleanValue();	
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
	
	public void startClient() {
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
	
	
	// --- SHALL BE DELETED. PRESERVED FOR REUSE OF CODE ---
//	class InputClass extends Thread{
//		private ObjectInputStream objectIn;
//		
//		
//		InputClass(InputStream serverInputStream) {
//			try {
//				this.objectIn = new ObjectInputStream(serverInputStream);
//			} catch (IOException e) {e.printStackTrace();
//			}
//		}
//		public void run() {
//			while(true) {
//				SendObject object;
//				try {
//					object = (SendObject) this.objectIn.readObject();
//					switch(object.getSendType()) {
//					case LOGIN:
//						Boolean bol = (Boolean) object.getObject();
//						break;
//					case APPOINTMENT:
//						Appointment app = (Appointment) object.getObject();
//						break;
//					case PERSON:
//						Person person = (Person) object.getObject();
//						break;
//					case ALARM:
//						Alarm alarm = (Alarm) object.getObject();
//					case ROOM:
//						Room room = (Room) object.getObject();
//					default:
//						break;
//					}
//				} catch (IOException | ClassNotFoundException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//	}
//	
}
