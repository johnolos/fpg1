package gui;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.JTable;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class HomeGUI extends JPanel {

	// ----------------------------------------------------------------//
	// Fields
	// ----------------------------------------------------------------//

	String[] columnNames;
	Object[][] data;
	private JTable table;
	private int ukeNr = 1;

	private JFrame frame;

	private JPanel headlinePanel;
	private JPanel weekPanel;
	private JPanel buttonPanel;
	private JPanel calendarPanel;
	private JPanel notificationPanel;

	private JButton lastWeekButton;
	private JButton nextWeekButton;
	private JButton logoutButton;
	private JButton myCalendarButton;
	private JButton myAppointmentsButton;
	private JButton createAppointmentButton;
	private JButton showColleaguesButton;

	private JLabel notificationLabel;
	private JLabel headlineLabel;
	private JLabel starfighterLabel;
	private JLabel skipToWeekLabel;
	private JLabel weekLabel = new JLabel("Uke" + ukeNr);

	private JTextField weekTextField;
	private JTextArea notificationTextField;

	private ImageIcon starFighterIcon;
	private ImageIcon forrigeUke;
	private ImageIcon nesteUke;

	public HomeGUI() {

		// ----------------------------------------------------------------//
		// Panels
		// ----------------------------------------------------------------//

		headlinePanel = new JPanel();
		buttonPanel = new JPanel();
		calendarPanel = new JPanel();
		weekPanel = new JPanel();
		notificationPanel = new JPanel();

		// ----------------------------------------------------------------//
		// Frame
		// ----------------------------------------------------------------//

		frame = new JFrame();
		frame.setTitle("Min Kalender");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1280, 1000);
		frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		frame.setContentPane(this);

		// ----------------------------------------------------------------//
		// Images and Icons
		// ----------------------------------------------------------------//

		forrigeUke = new ImageIcon("images/arrow_previous.png");
		nesteUke = new ImageIcon("images/arrow_next.png");
		starFighterIcon = new ImageIcon("images/starfighter.png");
		starfighterLabel = new JLabel(starFighterIcon);
		starfighterLabel.setText("starfighter");

		// ----------------------------------------------------------------//
		// Labels
		// ----------------------------------------------------------------//

		skipToWeekLabel = new JLabel("Hopp til uke:");
		notificationLabel = new JLabel("Notifikasjoner");
		headlineLabel = new JLabel("GRUPPE 1s KALENDERSYSTEM!!!");
		headlineLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));

		// ----------------------------------------------------------------//
		// Buttons
		// ----------------------------------------------------------------//

		lastWeekButton = new JButton();
		lastWeekButton.setIcon(forrigeUke);

		nextWeekButton = new JButton();
		nextWeekButton.setIcon(nesteUke);

		logoutButton = new JButton("Logg ut");
		showColleaguesButton = new JButton("  Vis Kollegaer  ");
		myCalendarButton = new JButton("  Min Kalender  ");
		myAppointmentsButton = new JButton("  Mine Avtaler  ");
		createAppointmentButton = new JButton("Opprett Avtale");

		// ----------------------------------------------------------------//
		// TextFields
		// ----------------------------------------------------------------//

		weekTextField = new JTextField();
		weekTextField.setColumns(10);
		notificationTextField = new JTextArea();
		notificationTextField.setText("halloooo");

		// ----------------------------------------------------------------//
		// MethodCalls
		// ----------------------------------------------------------------//

		addStuffToPanels();
		initTable();
		createLayout();
		setColors();
		addListeners();


	}
	
	public void addListeners(){
		
		weekTextField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					int textFieldNumber = Integer.parseInt(weekTextField
							.getText());
					if (textFieldNumber > 0 && textFieldNumber < 52) {
						ukeNr = textFieldNumber;
						weekLabel.setText("Uke " + textFieldNumber);
					}
				}

			}

		});
		
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginPanel gotoLogin = new LoginPanel();
				frame.dispose();
			}
		});
		
		showColleaguesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		nextWeekButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ukeNr += 1;
				ukeNr = ukeNr % 52;
				weekLabel.setText("Uke " + ukeNr);
			}
		});
		
		lastWeekButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ukeNr -= 1;
				if (ukeNr < 0) {
					ukeNr = ukeNr + 52;
				}
				weekLabel.setText("Uke " + ukeNr);

			}
		});
		
	}
	
	public void addStuffToPanels(){
		
		// ----------------------------------------------------------------//
		// AddStuffToPanels
		// ----------------------------------------------------------------//

		weekPanel.add(skipToWeekLabel);
		weekPanel.add(weekTextField);
		weekPanel.add(lastWeekButton);
		weekPanel.add(weekLabel);
		weekPanel.add(nextWeekButton);

		notificationPanel.add(logoutButton);

		buttonPanel.add(myCalendarButton);
		buttonPanel.add(myAppointmentsButton);
		buttonPanel.add(createAppointmentButton);
		
		myAppointmentsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MyAppointmentsPanel myAppointmentsPanel = new MyAppointmentsPanel();
			}
		});
		createAppointmentButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				CreateAppointmentPanel createAppointmentPanel = new CreateAppointmentPanel();
			}
		});
	}
	
	public void setColors(){
		
		this.setBackground(Color.DARK_GRAY);
		headlinePanel.setBackground(Color.DARK_GRAY);
		buttonPanel.setBackground(Color.DARK_GRAY);
		calendarPanel.setBackground(Color.DARK_GRAY);
		weekPanel.setBackground(Color.DARK_GRAY);
		notificationPanel.setBackground(Color.DARK_GRAY);
	}

	public void initTable() {
		columnNames = new String[] { "First Name", "Last Name", "Sport",
				"# of Years", "Vegetarian" };

		data = new Object[][] {
				{ "Kathy", "Smith", "Snowboarding", new Integer(5),
						new Boolean(false) },
				{ "John", "Doe", "Rowing", new Integer(3), new Boolean(true) },
				{ "Sue", "Black", "Knitting", new Integer(2),
						new Boolean(false) },
				{ "Jane", "White", "Speed reading", new Integer(20),
						new Boolean(true) },
				{ "Joe", "Brown", "Pool", new Integer(10), new Boolean(false) } };

		table = new JTable(data, columnNames);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(100);
		table.setVisible(true);
		calendarPanel.add(table, "align left, wrap");

	}

	public void createLayout() {

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																headlinePanel,
																GroupLayout.DEFAULT_SIZE,
																1256,
																Short.MAX_VALUE)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								notificationPanel,
																								GroupLayout.PREFERRED_SIZE,
																								120,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								buttonPanel,
																								GroupLayout.PREFERRED_SIZE,
																								120,
																								GroupLayout.PREFERRED_SIZE))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								calendarPanel,
																								GroupLayout.DEFAULT_SIZE,
																								1130,
																								Short.MAX_VALUE)
																						.addComponent(
																								weekPanel,
																								GroupLayout.DEFAULT_SIZE,
																								1130,
																								Short.MAX_VALUE))))
										.addContainerGap()));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(headlinePanel,
												GroupLayout.PREFERRED_SIZE, 35,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING,
																false)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				buttonPanel,
																				GroupLayout.PREFERRED_SIZE,
																				138,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				notificationPanel,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				weekPanel,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				calendarPanel,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)))
										.addContainerGap(107, Short.MAX_VALUE)));

		weekPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		GroupLayout gl_notificationPanel = new GroupLayout(notificationPanel);
		gl_notificationPanel
				.setHorizontalGroup(gl_notificationPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_notificationPanel
										.createSequentialGroup()
										.addGroup(
												gl_notificationPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_notificationPanel
																		.createSequentialGroup()
																		.addGap(10)
																		.addComponent(
																				notificationLabel))
														.addGroup(
																gl_notificationPanel
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				notificationTextField,
																				GroupLayout.PREFERRED_SIZE,
																				100,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_notificationPanel
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				starfighterLabel,
																				GroupLayout.DEFAULT_SIZE,
																				100,
																				Short.MAX_VALUE)))
										.addContainerGap())
						.addGroup(
								Alignment.TRAILING,
								gl_notificationPanel.createSequentialGroup()
										.addGap(29).addComponent(logoutButton)
										.addContainerGap(22, Short.MAX_VALUE)));
		gl_notificationPanel.setVerticalGroup(gl_notificationPanel
				.createParallelGroup(Alignment.LEADING).addGroup(
						gl_notificationPanel
								.createSequentialGroup()
								.addGap(9)
								.addComponent(notificationLabel)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(notificationTextField,
										GroupLayout.PREFERRED_SIZE, 245,
										GroupLayout.PREFERRED_SIZE)
								.addGap(11)
								.addComponent(starfighterLabel,
										GroupLayout.PREFERRED_SIZE, 124,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(logoutButton).addContainerGap()));
		notificationPanel.setLayout(gl_notificationPanel);

		GroupLayout gl_panel = new GroupLayout(headlinePanel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(headlineLabel, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGap(384)));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel.createSequentialGroup()
						.addComponent(headlineLabel)
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		headlinePanel.setLayout(gl_panel);
		GroupLayout gl_calendarPanel = new GroupLayout(calendarPanel);
		gl_calendarPanel
				.setHorizontalGroup(gl_calendarPanel.createParallelGroup(
						Alignment.LEADING)
						.addGroup(
								gl_calendarPanel
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(table,
												GroupLayout.DEFAULT_SIZE, 750,
												Short.MAX_VALUE)
										.addContainerGap()));
		gl_calendarPanel.setVerticalGroup(gl_calendarPanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_calendarPanel
						.createSequentialGroup()
						.addGap(5)
						.addComponent(table, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(44, Short.MAX_VALUE)));
		calendarPanel.setLayout(gl_calendarPanel);
		buttonPanel.add(showColleaguesButton);
		setLayout(groupLayout);

	}
}
