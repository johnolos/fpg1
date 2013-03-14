package ktn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

//TCP client
public class Client {
	
	private int port;
	private String serverAddress;
	
	
	public Client(int port, String serverAddress) {
		this.port = port;
		this.serverAddress = serverAddress;
	}
	
	public void startClient() {
		Socket serverConnection = null;
		try {
			// Create a TCP connection to Server/Client
			serverConnection = new Socket(InetAddress.getByName(this.serverAddress), this.port);
			// Connection established
			System.out.println("Connected to server");
			//Acquire the InputStream from Socket
			InputStream serverInputStream = serverConnection.getInputStream();
			// Creating InputSteamReader from the InputStream
			InputStreamReader inFromServer = new InputStreamReader(serverInputStream);
			// Creating Buffer for serverInputStream
			BufferedReader stringFromServer = new BufferedReader(inFromServer);
			// Acquiring the OutputStream from the Socket
			OutputStream serverOutputStream = serverConnection.getOutputStream();
			// Creating a PrintWriter class to use as output channel into the socket
			PrintWriter outToServer = new PrintWriter(serverOutputStream, true);
			
			System.out.println("Say something:");
			// Creating input buffer for user input
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			// Thread for sending messages; Passing over BufferedReader(Input from user) and PrintWriter
			new OutputClass(inFromUser, outToServer).start();
			// Thread for receiving messages; Passing over BufferedReader(Input from server)
			new InputClass(stringFromServer).start();
			
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
		BufferedReader stringFromServer;
		
		
		InputClass(BufferedReader stringFromServer) {
			this.stringFromServer = stringFromServer;
		}
		@Override
		public void run() {
			while(true) {
				try {
					// Fetches lines from BufferedReader and printing them to console
					String response = stringFromServer.readLine();
					System.out.println(response);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	// Function to initiate a client
	public static void main(String[] args) {
		new Client(7899,"127.0.0.1").startClient();
		//new Client(7899,"127.0.0.1").startClient();
	}
}
