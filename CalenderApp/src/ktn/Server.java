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

public class Server {
	private int port;
	private String serverAddress;

	public Server(int port, String serverAddress) {
		this.port = port;
		this.serverAddress = serverAddress;
	}

	public void startServer() {
		try {
			// Creating a ServerSocket: Is not used further
			ServerSocket serverSocket = new ServerSocket(this.port, 50, InetAddress.getByName(serverAddress));
			// Printing IP:Port for the server
			System.out.println("Waiting for connections on " + this.serverAddress + " : " + this.port);
			
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
				ObjectOutputStream objectOut = new ObjectOutputStream(clientOutputStream);
				//Create InputObjectStream
				ObjectInputStream objectIn = new ObjectInputStream(clientInputStream);
				// Create Buffer InputStreamReader
				BufferedReader stringFromClient = new BufferedReader(inFromClient);
				// Create PrintWriter for OutputStream
				PrintWriter outToClient = new PrintWriter(clientOutputStream, true);
				System.out.println("Waiting for message from client");
				
				// While-loop to ensure continuation of reading in-coming messages
				String fromClient;
				while (true) {
					
					
					
					// Reads message from buffer
					fromClient = stringFromClient.readLine();
					System.out.println("Message from client: " + fromClient);
					// Prints "OK:" + "messageFromClient" using PrintWriter to OutputStream
					outToClient.println("OK: " + fromClient);
					// System.out.println(clientSocket.getLocalSocketAddress());
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}

	// Main-function to start server
	public static void main(String[] args) {
		new Server(7899, "127.0.0.1").startServer();
	}
}
