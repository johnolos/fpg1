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

public class Server {
	private final static String SERVERIP = "78.91.10.38";
	private final static int SERVERPORT = 4058;

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
						SendObject obj =(SendObject) this.objectIn.readObject();
						System.out.println(obj.getKeyword() + "Dsfd");
						SendObject obj = databaseQuery(obj);
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
		if(obj.isRequest()){
			System.out.println("hadsdfdsf");
			String[] keyword;
			RequestEnum reType;
			keyword = obj.getKeyword();
			reType = obj.getSendType();
			
			switch(reType) {
			case LOGIN:
				Boolean bol = database.login(keyword);
				System.out.println(bol);
				return bol;
				break;
			case APPOINTMENT:
				
				break;
			case PERSON:
				break;
			case ALARM:
				break;
			case ROOM:
				break;
			default:
				break;
			}
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

	// Main-function to start server
	public static void main(String[] args) {
		new Server().startServer();
	}
}
