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
	
	private int port;
	private String serverAddress;
	
	private ObjectOutputStream objectOutput;
	private ObjectInputStream objectInput;
	
	
	public Client(int port, String serverAddress) {
		this.port = port;
		this.serverAddress = serverAddress;
	}
	
	public void connect() {
		this.startClient();
	}
	
	public void close() {
		
	}
	
	// Login function
	public void login(String username, String password) {
		String[] keyword = {username,password};
		RequestObjects reqObj = new RequestObjects(RequestEnum.LOGIN, keyword);
	}
	
	// Send function
	private void send(RequestObjects reqObj) {
		try {
			this.objectOutput.writeObject(reqObj);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Client: Object couldn't be sent");
		}
	}
	
	// receive function
	private Object receive() {
		Object receivedObject = null;
		try {
			receivedObject = this.objectInput.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return receivedObject;
	}
	
	public void startClient() {
		try {
			// Create a TCP connection to Server/Client
			connection = new Socket(InetAddress.getByName(this.serverAddress), this.port);
			// Connection established
			System.out.println("Connected to server");
			//Acquire the InputStream from Socket
			InputStream serverInputStream = connection.getInputStream();
			// Creating InputSteamReader from the InputStream
			InputStreamReader inFromServer = new InputStreamReader(serverInputStream);
			// Creating Buffer for serverInputStream
			BufferedReader stringFromServer = new BufferedReader(inFromServer);
			// Acquiring the OutputStream from the Socket
			OutputStream serverOutputStream = connection.getOutputStream();
			// Creating a PrintWriter class to use as output channel into the socket
			PrintWriter outToServer = new PrintWriter(serverOutputStream, true);
			
			System.out.println("Say something:");
			// Creating input buffer for user input
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			// Thread for sending messages; Passing over BufferedReader(Input from user) and PrintWriter
			new OutputClass(inFromUser, outToServer).start();
			// Thread for receiving messages; Passing over BufferedReader(Input from server)
			new InputClass(serverInputStream).start();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Class for "printing" input from user to server with PrintWriter
	class OutputClass extends Thread {
		private BufferedReader inFromUser;
		private PrintWriter outToServer;
		private ObjectOutputStream objectOut;
		
		
		OutputClass(BufferedReader inFromUser, PrintWriter outToServer) {
			this.inFromUser = inFromUser;
			this.outToServer = outToServer;
		}
		
		@Override
		public void run() {
			while(true) {
				try {
					// Fetches input from user and printing them to server using PrintWriter
					String userInput = this.inFromUser.readLine();
					outToServer.println(userInput);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
			
		}
	
	// Class for showing received messages from server through BufferedReader
	class InputClass extends Thread{
		private ObjectInputStream objectIn;
		
		
		InputClass(InputStream serverInputStream) {
			try {
				this.objectIn = new ObjectInputStream(serverInputStream);
			} catch (IOException e) {e.printStackTrace();
			}
		}
		public void run() {
			while(true) {
				SendObject object;
				try {
					object = (SendObject) this.objectIn.readObject();
					switch(object.getSendType()) {
					case LOGIN:
						Boolean bol = (Boolean) object.getObject();
						break;
					case APPOINTMENT:
						Appointment app = (Appointment) object.getObject();
						break;
					case PERSON:
						Person person = (Person) object.getObject();
						break;
					case ALARM:
						Alarm alarm = (Alarm) object.getObject();
					case ROOM:
						Room room = (Room) object.getObject();
					default:
						break;
					}
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	// Function to initiate a client
	public static void main(String[] args) {
		new Client(4004,"78.91.62.42").startClient();
		//new Client(7899,"127.0.0.1").startClient();
	}
}
