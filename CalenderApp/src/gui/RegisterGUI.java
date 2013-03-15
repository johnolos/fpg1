package gui;


import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.UIManager;

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
	private JLabel takenUsernameLabel;

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
		frame.setBackground(UIManager.getColor("Panel.background"));

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
		
		
		takenUsernameLabel = new JLabel("Utilgjengelig brukernavn");
		takenUsernameLabel.setForeground(Color.RED);
		takenUsernameLabel.setVisible(false);
		

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
				}
			}
		});
		
		confirmPasswordTextField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_ENTER)){
					if (confirmPasswordTextField.getPassword().length > 0){
					registerButton.doClick();
					}
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
						.addComponent(passwordLabel)
						.addComponent(emailLabel)
						.addComponent(telephoneLabel)
						.addComponent(lastNameLabel)
						.addComponent(usernameLabel)
						.addComponent(firstNameLabel)
						.addComponent(confirmPasswordLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(telephoneTextField, Alignment.LEADING, 243, 243, Short.MAX_VALUE)
						.addComponent(emailTextField, Alignment.LEADING, 243, 243, Short.MAX_VALUE)
						.addComponent(lastNameTextField, Alignment.LEADING, 243, 243, 243)
						.addComponent(usernameTextField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
						.addComponent(confirmPasswordTextField, Alignment.LEADING, 243, 243, 243)
						.addComponent(firstNameTextField, Alignment.LEADING, 243, 243, 243)
						.addComponent(passwordTextField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 243, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(registerButton)
							.addGap(32)
							.addComponent(abortButton)
							.addGap(46)))
					.addGap(56))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(145)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(takenUsernameLabel)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(registerUserHeadlineLabel, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
							.addGap(77))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(registerUserHeadlineLabel)
					.addGap(16)
					.addComponent(takenUsernameLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
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
						.addComponent(registerButton)
						.addComponent(abortButton))
					.addGap(84))
		);

		setLayout(groupLayout);

	}
	
	public void registerUser(){
		String pass = "";
		for(int i=0; i<passwordTextField.getPassword().length;i++){
			pass += passwordTextField.getPassword()[i];
		}
		String[] keyword = {usernameTextField.getText(), pass, firstNameTextField.getText(), lastNameTextField.getText(), emailTextField.getText(), telephoneTextField.getText()};
		try{
			db.registerUser(keyword);
			frame.dispose();
		} catch (Exception e){
			takenUsernameLabel.setVisible(true);
			
		}
	}
}
