package gui;


import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JPasswordField;

import database.Database;

public class RegisterGUI extends JPanel {

	//Når username deselekteres, skal brukeren få vite om brukernavnet er ledig eller ei.
	
	// ----------------------------------------------------------------//
	// Fields
	// ----------------------------------------------------------------//

	private JLabel usernameLabel;
	private JLabel passwordLabel;
	private JLabel confirmPasswordLabel;
	private JLabel firstNameLabel;
	private JLabel lastNameLabel;
	private JLabel emailLabel;
	private JLabel telephoneLabel;
	private JLabel registerUserHeadlineLabel;

	private JTextField usernameTextField;
	private JPasswordField passwordTextField;
	private JPasswordField confirmPasswordTextField;
	private JTextField firstNameTextField;
	private JTextField lastNameTextField;
	private JTextField telephoneTextField;
	private JTextField emailTextField;

	private JFrame frame;
	private JButton registerButton;
	private JButton abortButton;
	
	Database db;

	public RegisterGUI() {
		
		
		
		try {
			db = new Database();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Could not connect to database");
		}

		// ----------------------------------------------------------------//
		// Frame
		// ----------------------------------------------------------------//

		frame = new JFrame();
		frame.setTitle("Registrer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setContentPane(this);

		// ----------------------------------------------------------------//
		// Labels
		// ----------------------------------------------------------------//

		usernameLabel = new JLabel("Brukernavn*:");
		passwordLabel = new JLabel("Passord*:");
		confirmPasswordLabel = new JLabel("Bekreft passord*:");
		firstNameLabel = new JLabel("Fornavn: ");
		lastNameLabel = new JLabel("Etternavn: ");
		emailLabel = new JLabel("E-post: ");
		telephoneLabel = new JLabel("Tlf.:");

		registerUserHeadlineLabel = new JLabel("Registrer bruker");
		registerUserHeadlineLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));

		// ----------------------------------------------------------------//
		// TextFields
		// ----------------------------------------------------------------//

		usernameTextField = new JTextField();
		usernameTextField.setColumns(10);

		passwordTextField = new JPasswordField();
		
		confirmPasswordTextField = new JPasswordField();

		firstNameTextField = new JTextField();
		firstNameTextField.setColumns(10);

		lastNameTextField = new JTextField();
		lastNameTextField.setColumns(10);

		telephoneTextField = new JTextField();
		telephoneTextField.setColumns(10);

		emailTextField = new JTextField();
		emailTextField.setColumns(10);

		// ----------------------------------------------------------------//
		// Buttons
		// ----------------------------------------------------------------//

		registerButton = new JButton("Registrer");
		abortButton = new JButton("Avbryt");

		// ----------------------------------------------------------------//
		// MethodCalls && Misc
		// ----------------------------------------------------------------//

		createLayout();
		setBackground(Color.DARK_GRAY);
		addListeners();

	}

	// ----------------------------------------------------------------//
	// Methods
	// ----------------------------------------------------------------//

	public void addListeners() {
		
		abortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!passwordTextField.getText().equals(confirmPasswordTextField.getText())){
					passwordLabel.setBackground(Color.RED);
					confirmPasswordLabel.setBackground(Color.RED);
					frame.repaint();
				}
				else{
					registerUser();
					frame.dispose();
				}
			}
		});
		
		confirmPasswordTextField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_ENTER)){
					if (confirmPasswordTextField.getPassword().length > 0) {
					registerButton.doClick();
					//LoginPanel gotoLogin = new LoginPanel();
					} else
					System.out.println("You didn't type nothing, dawg");
				}
			}

		});
		
		telephoneTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (confirmPasswordTextField.getText().length() > 0) {
					registerButton.doClick();
				} else
					System.out.println("ERROR");
			}
		});
		
		
	}
	
	public void createLayout() {
		

		// ----------------------------------------------------------------//
		// Layout
		// ----------------------------------------------------------------//

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(passwordLabel)
								.addComponent(emailLabel)
								.addComponent(telephoneLabel)
								.addComponent(lastNameLabel)
								.addComponent(usernameLabel)
								.addComponent(firstNameLabel)
								.addComponent(confirmPasswordLabel))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(telephoneTextField, 243, 243, Short.MAX_VALUE)
								.addComponent(emailTextField, 243, 243, Short.MAX_VALUE)
								.addComponent(lastNameTextField, 243, 243, 243)
								.addComponent(usernameTextField, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
								.addComponent(confirmPasswordTextField, 243, 243, 243)
								.addComponent(firstNameTextField, 243, 243, 243)
								.addComponent(passwordTextField, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(registerButton)
							.addGap(27)
							.addComponent(abortButton)
							.addGap(39)))
					.addGap(56))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(134)
					.addComponent(registerUserHeadlineLabel, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
					.addGap(130))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(29)
					.addComponent(registerUserHeadlineLabel)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(usernameLabel)
						.addComponent(usernameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(passwordTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(confirmPasswordTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(confirmPasswordLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(firstNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(firstNameLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lastNameLabel)
						.addComponent(lastNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(emailLabel, Alignment.TRAILING)
						.addComponent(emailTextField, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(telephoneLabel)
						.addComponent(telephoneTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(abortButton)
						.addComponent(registerButton))
					.addGap(63))
		);

		setLayout(groupLayout);

	}
	
	public void registerUser(){
		String pass = "";
		for(int i=0; i<passwordTextField.getPassword().length;i++){
			pass += passwordTextField.getPassword()[i];
		}
		String[] keyword = {usernameTextField.getText(), pass, firstNameTextField.getText(), lastNameTextField.getText(), emailTextField.getText(), telephoneTextField.getText()};
		db.registerUser(keyword);
	}
}
