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
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import baseClasses.Appointment;
import baseClasses.Person;
import baseClasses.Room;

import database.Database;

public class Server {
	private final static String SERVERIP = "192.168.1.9";
	private final static int SERVERPORT = 4004;

	public Server() {
	}

	public void startServer() {
		try {
			// Creating a ServerSocket: Is not used further
			ServerSocket serverSocket = new ServerSocket(this.SERVERPORT, 50, InetAddress.getByName(this.SERVERIP));
			// Printing IP:Port for the server
			System.out.println("Waiting for connections on " + this.SERVERIP + " : " + this.SERVERPORT);
			
			// A never-ending while-loop that constantly listens to the Socket
			Socket newConnectionSocket;
			while (true) {
				// Accepts a in-coming connection
				newConnectionSocket = serverSocket.accept();
				// System message
				System.out.println("New connection.");
				// Delegating the further connection with the client to a thread class
				new ClientConnection(newConnectionSocket).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	// Thread class for handling further connection with server when connection is established
	class ClientConnection extends Thread {
		private Socket connection;
		private ObjectOutputStream objectOut;
		private ObjectInputStream objectIn;
		ClientConnection(Socket connection) {
			this.connection = connection;
		}
		private void send(SendObject obj) {
			try {
				this.objectOut.writeObject(obj);
				System.out.println("sendt");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void run() {
			System.out.println("Connected to client on " + this.connection.getRemoteSocketAddress());
			InputStream clientInputStream;
			try {
				// Fetches InputStream from connection
				clientInputStream = this.connection.getInputStream();
				// Fetches OutputStream from connect
				OutputStream clientOutputStream = this.connection.getOutputStream();
				// Create InputStreamReader for InputStream
				InputStreamReader inFromClient = new InputStreamReader(clientInputStream);
				
				// Create ObjectOutputStream
				objectOut = new ObjectOutputStream(clientOutputStream);
				//Create InputObjectStream
				objectIn = new ObjectInputStream(clientInputStream);
				
				System.out.println("Waiting for message from client");
				
				// While-loop to ensure continuation of reading in-coming messages
				int i = 10;
				while (i>0) {
					i--;
					try {
						//Receive object from client
						SendObject obj =(SendObject) this.objectIn.readObject();
						//Sends object to databsefunction and return result-object
						SendObject dbObj = databaseQuery(obj); 
						//Sends object back to client
						send(dbObj);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					catch (SocketException e) {
					}
					

				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}
	
	SendObject databaseQuery(SendObject obj) {
		Database database = null;
		try {
			database = new Database();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR while connecting to database - ktnSTYLE");
			
		}
		String[] keyword;
		RequestEnum reType;
		keyword = obj.getKeyword();
		reType = obj.getSendType();
		// This is the object that the server sends.
		SendObject sObject;
		if(obj.isRequest()){
			switch(reType) {
			case LOGIN:
				Person person = database.login(keyword);
				sObject = new SendObject(RequestEnum.PERSON, person);
				return sObject;
			case PERSON:
				ArrayList<Person> persons = database.getPerson(keyword);
				sObject = new SendObject(RequestEnum.PERSON, persons);
				return sObject;
			case APPOINTMENT:
				ArrayList<Appointment> appointments;
				
				/*
				 * If string is empty, then all available appointments shall be delivered.
				 * Used inside change Appointments panel in gui.
				 */
				if(keyword[1] == "") {
					appointments = database.getAppointmentsOnPerson(keyword);
					sObject = new SendObject(RequestEnum.APPOINTMENT, appointments);
				}
				/*
				 * If string is not empty, appointments a specific date is received.
				 * This one is used to get appointments for each row in a day view
				 * in calender.
				 */
				 else {
					 appointments = database.getAppointmentsOnPerson(keyword);
					 sObject = new SendObject(RequestEnum.APPOINTMENT, appointments);
				}
				return sObject;
			case S_PERSON://Returns person after completed login
				Boolean bool2 = database.registerUser(keyword);
				sObject = new SendObject(RequestEnum.BOOLEAN,bool2);
				return sObject;
			case ROOM:
				ArrayList<Room> rooms= database.fetchRooms(keyword);
				sObject = new SendObject(RequestEnum.ROOM,rooms);
				return sObject;
			default:
				break;
			}
		}
		else{
			switch (reType) {
			case S_APPOINTMENT:
				Boolean bool3 = database.createAppointment((Appointment)obj.getObject());
				sObject = new SendObject(RequestEnum.BOOLEAN,bool3);
				return sObject;
			default:
				break;
			}
		}
		return null;
		
		
	}


	// Main-function to start server
	public static void main(String[] args) {
		new Server().startServer();
	}
}
