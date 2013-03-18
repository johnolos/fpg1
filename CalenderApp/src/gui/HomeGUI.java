package gui;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import org.joda.time.DateTime;

import sun.beans.editors.StringEditor;

import baseClasses.Appointment;
import baseClasses.Notification;
import baseClasses.NotificationEnum;
import baseClasses.Person;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ktn.Client;

public class HomeGUI extends JPanel {

	// ----------------------------------------------------------------//
	// Fields
	// ----------------------------------------------------------------//

	private int week;
	private int year;
	private int month;
	private int dayOfMonth;
	String monthStr;
	private DateTime date;
	private int day;
	private String startTime;
	private String endTime;

	private JFrame frame;

	private JPanel headlinePanel;
	private JPanel weekPanel;
	private JPanel buttonPanel;
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
	private JLabel weekLabel;
	private JLabel yearAndDateLabel;

	private JTextField weekTextField;

	static JList<Notification> list;
	
	private ImageIcon starFighterIcon;
	private ImageIcon forrigeUke;
	private ImageIcon nesteUke;
	private JPanel panel_1;
	private JPanel calendarPanel;
	
	private MeetingInvitationPanel meetingInvitationPanel;
	private OKNotificationBox okNotificationBox;
	private ParticipantDeclinedPanel participantDeclinedPanel;
	static JTable table;
	
	private Appointment appointment;

	static JLayeredPane layeredPane;
	static ArrayList<Object> listOfFrames;
	
	private static Person currentUser;
	private Client client;
	private JLabel skipToYearLabel;
	private JTextField skipToYearTextField;
	private int dayOfWeek;
	private int startDay;
	private int endDay;


	public HomeGUI(Person user, Client client) {
		this.currentUser = user;
		this.client = client;
		
		// ----------------------------------------------------------------//
		// DateTime
		// ----------------------------------------------------------------//
		
		date = new DateTime();
		
		monthStr = date.monthOfYear().getAsText();
		year = date.getYear();
		month = date.getMonthOfYear();
		
		dayOfMonth = date.getDayOfMonth();
		dayOfWeek = date.getDayOfWeek();
		
		startDay = dayOfMonth - (dayOfWeek - 1);
		endDay = dayOfMonth + (7 - dayOfWeek);
		
		week = date.getWeekOfWeekyear();
		
		yearAndDateLabel = new JLabel("" + startDay + ". " + monthStr + " - " + endDay + ". " + monthStr + " "  + year);
		yearAndDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		
		
		appointment = new Appointment(new DateTime(2014, 2, 2, 13, 37), new DateTime(2014, 2, 2, 14, 38), "hei", "hallo", null, "jøde", "kristen");

		// ----------------------------------------------------------------//
		// Panels
		// ----------------------------------------------------------------//

		headlinePanel = new JPanel();
		buttonPanel = new JPanel();
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
		skipToYearLabel = new JLabel("Hopp til år:");
		notificationLabel = new JLabel("Notifikasjoner");
		notificationLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		headlineLabel = new JLabel("GRUPPE 1s KALENDERSYSTEM!!!");
		headlineLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		weekLabel = new JLabel("Uke " + week);

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
		weekTextField.setColumns(3);			
		weekTextField.setDocument(new JTextFieldLimit(2));
		
		skipToYearTextField = new JTextField();
		skipToYearTextField.setColumns(3);
		skipToYearTextField.setDocument(new JTextFieldLimit(4));
		


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
					if (textFieldNumber > 0 && textFieldNumber <= 52) {
						week = textFieldNumber;
						weekLabel.setText("Uke " + textFieldNumber);
					}
					else if(textFieldNumber < 1){
						week = 1;
						weekLabel.setText("Uke " + 1);
					}
					else if(textFieldNumber > 52){
						week = 52;
						weekLabel.setText("Uke " + 52);
					}
				}

			}

		});
		
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				client.close();
				LoginPanel gotoLogin = new LoginPanel(new Client());
				frame.dispose();
			}
		});
		
		showColleaguesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ShowColleaguesPanel showColleagues = new ShowColleaguesPanel(client);
				
			}
		});
		
		nextWeekButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				week += 1;
				if (week == 53)
					week = 1;
				weekLabel.setText("Uke " + week);
			}
		});
		
		lastWeekButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				week -= 1;
				if (week < 0) {
					week = week + 52;
				}
				
				if (week == 0)
					week = 52;
				weekLabel.setText("Uke " + week);
			}
		});
		
		skipToYearTextField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					
					year =  Integer.parseInt(skipToYearTextField.getText());
					date = new DateTime(year + "-" + month + "-" + dayOfMonth);
					monthStr = date.monthOfYear().getAsText();
					month = date.getMonthOfYear();
					
					
					
					dayOfMonth = date.getDayOfMonth();
					dayOfWeek = date.getDayOfWeek();
					
					startDay = dayOfMonth - (dayOfWeek - 1);
					endDay = dayOfMonth + (7 - dayOfWeek);
					
					week = date.getWeekOfWeekyear();
					
					yearAndDateLabel.setText("" + startDay + ". " + monthStr + " - " + endDay + ". " + monthStr + " "  + year);
				
			}
		});
		
		myAppointmentsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MyAppointmentsPanel myAppointmentsPanel = new MyAppointmentsPanel(client, currentUser);
			}
		});
		createAppointmentButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				CreateAppointmentPanel createAppointmentPanel = new CreateAppointmentPanel(client);
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
		weekPanel.add(skipToYearLabel);
		weekPanel.add(skipToYearTextField);

		notificationPanel.add(logoutButton);

		buttonPanel.add(myCalendarButton);
		buttonPanel.add(myAppointmentsButton);
		buttonPanel.add(createAppointmentButton);
		
	}
	
	public void setColors(){
		
		this.setBackground(UIManager.getColor("Panel.background"));
		headlinePanel.setBackground(UIManager.getColor("Panel.background"));
		buttonPanel.setBackground(UIManager.getColor("Panel.background"));
		weekPanel.setBackground(UIManager.getColor("Panel.background"));
		notificationPanel.setBackground(UIManager.getColor("Panel.background"));
	}

	public void initTable() {
	}

	public void createLayout() {
		
		layeredPane = new JLayeredPane();

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(headlinePanel, GroupLayout.DEFAULT_SIZE, 1244, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(notificationPanel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
								.addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addComponent(weekPanel, GroupLayout.DEFAULT_SIZE, 1106, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 1118, Short.MAX_VALUE)))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(headlinePanel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(notificationPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(weekPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)))
					.addGap(96))
		);
		
		calendarPanel = new JPanel();
		calendarPanel.setBounds(0, 0, 1106, 554);
		layeredPane.add(calendarPanel);
		
		JScrollPane calendarScrollPane = new JScrollPane();
		
		GroupLayout gl_calendarPanel = new GroupLayout(calendarPanel);
		gl_calendarPanel.setHorizontalGroup(
			gl_calendarPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_calendarPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(calendarScrollPane, GroupLayout.DEFAULT_SIZE, 1086, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_calendarPanel.setVerticalGroup(
			gl_calendarPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_calendarPanel.createSequentialGroup()
					.addComponent(calendarScrollPane, GroupLayout.DEFAULT_SIZE, 543, Short.MAX_VALUE)
					.addContainerGap())
		);
		

		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent arg0) {
				//table.getSelectedRow(arg0.getComponent());
			}
		});
		table.setRowHeight(80);
		
		
		calendarScrollPane.setViewportView(table);
		DefaultTableModel tableModel = new DefaultTableModel(
				new Object [][] {
						{"08:00", "", "", "", "", "", "", ""},
						{"09:00", "", "", "", "", "", "", ""},
						{"10:00", "", "", "", "", "", "", ""},
						{"11:00", "", "", "", "", "", "", ""},
						{"12:00", "", "", "", "", "", "", ""},
						{"13:00", "", "", "", "", "", "", ""},
						{"14:00", "", "", "", "", "", "", ""},
						{"15:00", "", "", "", "", "", "", ""},
						{"16:00", "", "", "", "", "", "", ""},
						{"17:00", "", "", "", "", "", "", ""},
						{"18:00", "", "", "", "", "", "", ""},
					},
					new String[]  {
						"Tid","Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag", "Søndag"});
		table.setModel(tableModel);
		calendarPanel.setLayout(gl_calendarPanel);
		
		day = appointment.getStart().getDayOfWeek();
		startTime = appointment.getStart().getHourOfDay() + ":" + appointment.getStart().getMinuteOfHour() + "";
		endTime = appointment.getEnd().getHourOfDay()+ ":" + appointment.getEnd().getMinuteOfHour() + "";
		
		table.setValueAt("Start: "+startTime+ " End: " + endTime + "\nllllllllllllllllllllllllllllllll", 1, 1);
		for(int i=1; i<table.getColumnCount();i++){
			table.getColumnModel().getColumn(i).setCellRenderer(new MyRenderer());
		}
		table.setBackground(Color.LIGHT_GRAY);
		table.addMouseListener(new MouseListener(){
	
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
					System.out.println("Mouse clicked at Column: " + table.getSelectedColumn() + " and Row: " + table.getSelectedRow());
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		meetingInvitationPanel = new MeetingInvitationPanel();
		meetingInvitationPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		meetingInvitationPanel.setBounds(0, 111, 348, 379);
		layeredPane.add(meetingInvitationPanel);
		
		participantDeclinedPanel = new ParticipantDeclinedPanel();
		participantDeclinedPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		participantDeclinedPanel.setBounds(0, 111, 399, 262);
		layeredPane.add(participantDeclinedPanel);
		
		okNotificationBox = new OKNotificationBox();
		okNotificationBox.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		okNotificationBox.setBounds(0, 111, 334, 234);
		layeredPane.add(okNotificationBox);
		
		panel_1 = new JPanel();
		panel_1.setBounds(31, 99, 106, 64);
		

		weekPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JScrollPane scrollPane = new JScrollPane();

		GroupLayout gl_notificationPanel = new GroupLayout(notificationPanel);
		gl_notificationPanel.setHorizontalGroup(
			gl_notificationPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_notificationPanel.createSequentialGroup()
					.addGroup(gl_notificationPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_notificationPanel.createSequentialGroup()
							.addGap(10)
							.addComponent(notificationLabel))
						.addGroup(gl_notificationPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(starfighterLabel, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
						.addGroup(gl_notificationPanel.createSequentialGroup()
							.addGap(29)
							.addComponent(logoutButton))
						.addGroup(gl_notificationPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_notificationPanel.setVerticalGroup(
			gl_notificationPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_notificationPanel.createSequentialGroup()
					.addGap(9)
					.addComponent(notificationLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(starfighterLabel, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(logoutButton)
					.addContainerGap())
		);
		
		list = new JList();
		list.setModel(new AbstractListModel<Notification>() {
			Notification[] values = new Notification[] {new Notification(NotificationEnum.INVITATION), new Notification(NotificationEnum.DECLINED), new Notification(NotificationEnum.DECLINED), new Notification(NotificationEnum.OKBOX), new Notification(NotificationEnum.INVITATION)};
			
			public int getSize() {
				return values.length;
			}
			public Notification getElementAt(int index) {
				return values[index];
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e){
				Notification n;
				//Without this if statement both mouse click, then mouse move to another element and then mouse released will be recognized in valueChanged()
				//With this if statement only the element(s) selected(highlighted) when mouse is released will recognized in valueChanged()
				if(list.getValueIsAdjusting() == false && list.getSelectedIndex() != -1){
					n =  list.getSelectedValue();
					if(n.getType().equals(NotificationEnum.INVITATION)){
						meetingInvitationPanel.setTitle("Møteinnkalling fra " + n.getTitle());
						meetingInvitationPanel.setTitleOfMeeting(n.getTitle());
						meetingInvitationPanel.setDescription(n.getDescription());
						meetingInvitationPanel.setStart(n.getStart());
						meetingInvitationPanel.setEnd(n.getEnd());
						meetingInvitationPanel.setLocation(n.getLocation());
						layeredPane.moveToFront(meetingInvitationPanel);
						layeredPane.moveToBack(okNotificationBox);
						layeredPane.moveToBack(participantDeclinedPanel);
					}
					else if(n.getType().equals(NotificationEnum.OKBOX)){
						okNotificationBox.setTitle(n.getTitle());
						okNotificationBox.setDescription(n.getDescription());
						layeredPane.moveToFront(okNotificationBox);
						layeredPane.moveToBack(meetingInvitationPanel);
						layeredPane.moveToBack(participantDeclinedPanel);
					}
					else if(n.getType().equals(NotificationEnum.DECLINED)){
						participantDeclinedPanel.setTitle(n.getTitle());
						participantDeclinedPanel.setDeclinedMeeting(n.getMeetingName());
						participantDeclinedPanel.setTimeOfMeeting(n.getTimeOfMeeting());
						participantDeclinedPanel.setParticipant(n.getDeclinedParticipant());
						layeredPane.moveToFront(participantDeclinedPanel);
						layeredPane.moveToBack(meetingInvitationPanel);
						layeredPane.moveToBack(okNotificationBox);
					}
				}
					
			}
		});
		list.setCellRenderer(new NotificationListRenderer());
		
		scrollPane.setViewportView(list);
		notificationPanel.setLayout(gl_notificationPanel);

		GroupLayout gl_panel1 = new GroupLayout(headlinePanel);
		gl_panel1.setHorizontalGroup(
			gl_panel1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel1.createSequentialGroup()
					.addComponent(headlineLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(336)
					.addComponent(yearAndDateLabel)
					.addGap(479))
		);
		gl_panel1.setVerticalGroup(
			gl_panel1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel1.createSequentialGroup()
					.addGroup(gl_panel1.createParallelGroup(Alignment.BASELINE)
						.addComponent(headlineLabel)
						.addComponent(yearAndDateLabel))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		headlinePanel.setLayout(gl_panel1);
		buttonPanel.add(showColleaguesButton);
		setLayout(groupLayout);

	}

	public static Person getCurrentUser() {
		return currentUser;
	}
}
