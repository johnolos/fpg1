package logic;

import gui.LoginPanel;
import ktn.Client;

public class Calendar {
	private Client client;

	public Calendar() {
		client = new Client();
	}

	private void run() {
		LoginPanel loginPanel = new LoginPanel(client);
	}

	public static void main(String args[]) {

		new Calendar().run();
	}

}
