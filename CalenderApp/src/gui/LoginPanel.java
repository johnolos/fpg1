
package gui;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPasswordField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import baseClasses.Person;

import ktn.Client;

import database.Database;

public class LoginPanel extends JPanel {

	
	// ----------------------------------------------------------------//
	// Fields
	// ----------------------------------------------------------------//

	private JFrame frame;
	private JPanel topPanel;
	private JPanel middlePanel;
	
	private JLabel nameLabel;
	private JLabel imageLabel;
	private JLabel passwordLabel;
	private JLabel wrongInputLabel;
	
	private JTextField nameTextField;
	private JPasswordField passwordField;
	
	private JButton loginButton;
	private JButton registerButton;
	
	private ImageIcon image;
	
	private Database db;


	public LoginPanel() {
		
		try {
			db = new Database();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Could not connect to the database");
		}
		
		// ----------------------------------------------------------------//
		// NimbusLook
		// ----------------------------------------------------------------//
		
		getNimbusLookAndFeel();

		// ----------------------------------------------------------------//
		// Panels
		// ----------------------------------------------------------------//

		topPanel = new JPanel(new MigLayout());
		middlePanel = new JPanel(new MigLayout("", "[grow][]", "[][][][][][]"));

		this.add(topPanel);
		this.add(middlePanel);

		// ----------------------------------------------------------------//
		// Frame
		// ----------------------------------------------------------------//

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setContentPane(this);
		frame.setSize(640, 400);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);

		// ----------------------------------------------------------------//
		// Images
		// ----------------------------------------------------------------//

		image = new ImageIcon("images/img.png");
		imageLabel = new JLabel(image);

		// ----------------------------------------------------------------//
		// Name
		// ----------------------------------------------------------------//

		nameLabel = new JLabel("Brukernavn:  ");
		nameTextField = new JTextField();
		nameTextField.setColumns(20);


		// ----------------------------------------------------------------//
		// Password
		// ----------------------------------------------------------------//

		passwordLabel = new JLabel("Passord:  ");
		passwordField = new JPasswordField();


		// ----------------------------------------------------------------//
		// Buttons
		// ----------------------------------------------------------------//

		loginButton = new JButton("Logg inn");
		registerButton = new JButton("Registrer bruker");

		// ----------------------------------------------------------------//
		// Label(s)
		// ----------------------------------------------------------------//
		
		wrongInputLabel = new JLabel("Wrong username or password");
		wrongInputLabel.setVisible(false);
		wrongInputLabel.setForeground(Color.RED);

		// ----------------------------------------------------------------//
		// MethodCalls
		// ----------------------------------------------------------------//

		createLayout();
		setColors();
		addListeners();
		addStuffToPanels();
	}

	// ----------------------------------------------------------------//
	// Methods
	// ----------------------------------------------------------------//

	public void addStuffToPanels(){
		
		middlePanel.add(wrongInputLabel, "cell 1 0");
		middlePanel.add(registerButton, "cell 0 4 2 1,grow");
		middlePanel.add(loginButton, "cell 0 3 2 1,grow");
		middlePanel.add(passwordField, "cell 1 2,growx");
		middlePanel.add(passwordLabel, "cell 0 2");
		middlePanel.add(nameTextField, "cell 1 1");
		middlePanel.add(nameLabel, "cell 0 1");
		topPanel.add(imageLabel, "push, align center");
		
		
	}
	
	public void addListeners(){
		
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI gotoRegister = new RegisterGUI();

			}
		});
		
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pass = "";
				for(int i=0; i<passwordField.getPassword().length; i++){
					pass+=passwordField.getPassword()[i];
				}
				String[] keyword = {nameTextField.getText(), pass};
				Client client = new Client();
				try {
					client.connect();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				Person person = client.login(keyword);
				if(person != null){
					frame.dispose();
					HomeGUI gotoHome = new HomeGUI();
					HomeGUI.currentUser = person;
				}
				else{
					wrongInputLabel.setVisible(true);
					
				}

			}
		});
		
		passwordField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginButton.doClick();
				}
			}

		});
		
	}
	
	public void getNimbusLookAndFeel() {

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
		}

	}

	public void setColors() {
		this.setBackground(UIManager.getColor("Panel.background"));
		nameLabel.setForeground(Color.BLACK);
		passwordLabel.setForeground(Color.BLACK);
		topPanel.setBackground(UIManager.getColor("Panel.background"));
		middlePanel.setBackground(UIManager.getColor("Panel.background"));


	}

	public void createLayout() {



		// ----------------------------------------------------------------//
		// Layout
		// ----------------------------------------------------------------//

		GroupLayout gl_mainPanel = new GroupLayout(this);
		gl_mainPanel
				.setHorizontalGroup(gl_mainPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_mainPanel
										.createSequentialGroup()
										.addGroup(
												gl_mainPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_mainPanel
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				topPanel,
																				GroupLayout.DEFAULT_SIZE,
																				1256,
																				Short.MAX_VALUE))
														.addGroup(
																gl_mainPanel
																		.createSequentialGroup()
																		.addGap(514)
																		.addComponent(
																				middlePanel,
																				GroupLayout.PREFERRED_SIZE,
																				300,
																				GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));
		gl_mainPanel.setVerticalGroup(gl_mainPanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_mainPanel
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(topPanel, GroupLayout.PREFERRED_SIZE,
								155, GroupLayout.PREFERRED_SIZE)
						.addGap(147)
						.addComponent(middlePanel, GroupLayout.PREFERRED_SIZE,
								169, GroupLayout.PREFERRED_SIZE).addGap(266)));
		this.setLayout(gl_mainPanel);

	}
}
