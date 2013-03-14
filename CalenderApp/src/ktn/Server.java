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

import database.Database;

public class Server {
	private final static String SERVERIP = "78.91.62.42";
	private final static int SERVERPORT = 4004;

	public Server() {
		startServer();
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
				while (true) {
					
					try {
						Object obj = this.objectIn.readObject();
						
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					

				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}
	private void isRequestObject(Object obj){
		obj
	}
	
	void databaseQuery(RequestObjects obj) {
		Database database = null;
		try {
			database = new Database();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR while connecting to database - ktnSTYLE");
		}
		
		String[] keyword;
		RequestEnum reType;
		keyword = obj.getSearch();
		reType = obj.getReType();
		
		
		switch(reType) {
		case LOGIN:
			Boolean bol = database.login(keyword[0], keyword[1]);
			System.out.println(bol);
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

	// Main-function to start server
	public static void main(String[] args) {
		new Server(4295, "78.91.62.42").startServer();
	}
}
