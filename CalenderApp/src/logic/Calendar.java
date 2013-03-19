package logic;

import gui.LoginPanel;
import ktn.Client;
import ktn.Server;

public class Calendar {
	private Client client;
	
	
	public Calendar() {
		client = new Client();
	}
	
	private void run() {
		LoginPanel loginPanel = new LoginPanel(client);
	}

public static void main(String args[]) {
	new Server().startServer();
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	new Calendar().run();
}

}
