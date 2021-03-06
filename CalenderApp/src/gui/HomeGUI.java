package gui;


import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.JTable;
import javax.swing.Timer;
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
	private String monthStr;
	private DateTime date;
	private int day;
	private String startTime;
	private String endTime;
	private static DateTime endOfWeek, startOfWeek;

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
	private JLabel skipToDateLabel;

	private JTextField skipToWeekTextField;

	static JList<Notification> list;
	
	private ImageIcon starFighterIcon;
	private ImageIcon forrigeUke;
	private ImageIcon nesteUke;
	private JPanel panel_1;
	private JPanel calendarPanel;
	
	private MeetingInvitationPanel meetingInvitationPanel;
	private OKNotificationBox okNotificationBox;
	private ParticipantDeclinedPanel participantDeclinedPanel;
	static MyJTable table;
	
	private Appointment appointment;

	static JLayeredPane layeredPane;
	static ArrayList<Object> listOfFrames;
	
	private static Person currentUser;
	private static Client client;
	private JLabel skipToYearLabel;
	private JTextField skipToYearTextField;
	private int dayOfWeek;
	private int startDay;
	private int endDay;
	private DefaultListModel<Notification> notificationModel;
	private String endMonth;
	private String monthString;
	private DateTime endMonthDate;
	private String endMonthString;
	private int dayStartOfWeek;
	private int dayEndOfWeek;
	private String startMonth;
	
	private String[] columnNames;

	private static ArrayList<Appointment> allMyAppointments;
	
	private DefaultTableModel tableModel;
	private Object[][] initialTableContents;
	private JLabel mondayLabel;
	private JLabel tuesdayLabel;
	private JLabel wednesdayLabel;
	private JLabel thursdayLabel;
	private JLabel fridayLabel;
	private JLabel saturdayLabel;
	private JLabel sundayLabel;
	
	static ArrayList<Person> showAllThese;
	
	boolean firstTimeUpdate = true;
	
	private static NotificationUpdate notUpdate;
	private JTextField skipToDateTextField;

	public HomeGUI(Person user, Client client) {
	
		
		
		showAllThese = new ArrayList<Person>();
		this.currentUser = user;
		this.client = client;
		allMyAppointments = client.fetchAllAppointments(currentUser.getUsername());
		showAllThese.add(getCurrentUser());
		
		yearAndDateLabel = new JLabel("");
		createDateTime();
		
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

		forrigeUke = new ImageIcon("images/arrow_previous_new.png");
		nesteUke = new ImageIcon("images/arrow_next_new.png");
		starFighterIcon = new ImageIcon("images/starfighter.png");
		starfighterLabel = new JLabel(starFighterIcon);
		starfighterLabel.setText("starfighter");

		// ----------------------------------------------------------------//
		// Labels
		// ----------------------------------------------------------------//

		skipToWeekLabel = new JLabel("Hopp til uke:");
		skipToYearLabel = new JLabel("Hopp til �r:");
		
		notificationLabel = new JLabel("Notifikasjoner");
		notificationLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		headlineLabel = new JLabel("Angelei Kalendersystem");
		headlineLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 25));
		
		weekLabel = new JLabel("Uke " + week);
		
		yearAndDateLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 20));
		
		skipToDateLabel = new JLabel("Hopp til dato:");

		// ----------------------------------------------------------------//
		// Buttons
		// ----------------------------------------------------------------//

		lastWeekButton = new JButton();
		lastWeekButton.setIcon(forrigeUke);

		nextWeekButton = new JButton();
		nextWeekButton.setIcon(nesteUke);

		logoutButton = new JButton("        Logg ut       ");
		showColleaguesButton = new JButton("    Vis Kollegaer   ");
		myCalendarButton = new JButton("   Min Kalender    ");
		myAppointmentsButton = new JButton("    Mine Avtaler     ");
		createAppointmentButton = new JButton("  Opprett Avtale   ");

		// ----------------------------------------------------------------//
		// TextFields
		// ----------------------------------------------------------------//

		skipToWeekTextField = new JTextField();
		skipToWeekTextField.setColumns(3);			
		skipToWeekTextField.setDocument(new JTextFieldLimit(2));
		
		skipToYearTextField = new JTextField();
		skipToYearTextField.setColumns(3);
		skipToYearTextField.setDocument(new JTextFieldLimit(4));
		
		skipToDateTextField = new JTextField();
		skipToDateTextField.setDocument(new JTextFieldLimit(5));
		
		


		// ----------------------------------------------------------------//
		// MethodCalls
		// ----------------------------------------------------------------//

		addStuffToPanels();
		createLayout();
		setColors();
		addListeners();
		
		insertAppointmentsIntoTable();

	}
	
	
	// ----------------------------------------------------------------//
	// Methods
	// ----------------------------------------------------------------//
	
	
	// ----------------------------------------------------------------//
	// DateTime
	// ----------------------------------------------------------------//
	
	public void createDateTime() {

		date = new DateTime();

		year = date.getYear();
		month = date.getMonthOfYear();
		week = date.getWeekOfWeekyear();
		dayOfMonth = date.getDayOfMonth();
		dayOfWeek = date.getDayOfWeek();
		
		startOfWeek = new DateTime(year,month,(dayOfMonth - (dayOfWeek - 1)),0,0,0);
		endOfWeek = startOfWeek.plusDays(6);
		
		
		updateMonthString();


	}
	
	private void updateMonthString() {
		
		//f� tak i startdag og sluttdag
		
		dayStartOfWeek = startOfWeek.getDayOfMonth();
		dayEndOfWeek = endOfWeek.getDayOfMonth();
		startMonth = startOfWeek.monthOfYear().getAsText();
		endMonth = endOfWeek.monthOfYear().getAsText();
		year = endOfWeek.getYear();
		yearAndDateLabel.setText("" + dayStartOfWeek + ". " + startMonth + " - " + dayEndOfWeek + ". " + endMonth + " "  + year);
		
		if(firstTimeUpdate)
			firstTimeUpdate = false;
		else
			insertAppointmentsIntoTable();
	}
	
	
	
	public void addListeners(){
		
		skipToDateTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(skipToDateTextField.getText() == "") {
					return;
				}
				
				String[] splitString = skipToDateTextField.getText().split("\\.");
				int dayEntered = Integer.parseInt(splitString[0]);
				int monthEntered = Integer.parseInt(splitString[1]);
				DateTime toDate = new DateTime(startOfWeek.getYear(),monthEntered, dayEntered, 0, 0, 0);
				int daysBetween = toDate.getDayOfYear() - startOfWeek.getDayOfYear();
				startOfWeek = startOfWeek.plusDays(daysBetween);
				int toStartOfWeek = 1 - startOfWeek.getDayOfWeek();
				startOfWeek = startOfWeek.plusDays(toStartOfWeek);
				endOfWeek = startOfWeek.plusDays(6);
				updateMonthString();
				updateDateLabels();
//				updateColumnIdentifiers();
			}
		});
		
		skipToWeekTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if(skipToWeekTextField.getText() == "") {
					return;
				}
				int weekTextFieldNumber = Integer.parseInt(skipToWeekTextField.getText());
				if(weekTextFieldNumber >= 1 && weekTextFieldNumber <= 52) {
					int differenceWeeks = weekTextFieldNumber-startOfWeek.getWeekOfWeekyear();
					startOfWeek = startOfWeek.plusWeeks(differenceWeeks);
					endOfWeek = endOfWeek.plusWeeks(differenceWeeks);
					week = weekTextFieldNumber;
					weekLabel.setText("Uke " + weekTextFieldNumber);
				}
				
				updateMonthString();
				updateDateLabels();
//				updateColumnIdentifiers();
				
				}

			});
		
		skipToYearTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(skipToYearTextField.getText() == "") {
					return;
				}
				int yearTextFieldNumber = Integer.parseInt(skipToYearTextField.getText());
				int differenceYears = Integer.parseInt(skipToYearTextField.getText()) - (int)startOfWeek.getYear();
				startOfWeek = startOfWeek.plusWeeks(52 * differenceYears);
				endOfWeek = startOfWeek.plusDays(6);
				updateMonthString();
				updateDateLabels();
//				updateColumnIdentifiers();
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
				startOfWeek = startOfWeek.plusDays(7);
				endOfWeek = startOfWeek.plusDays(6);
				
				updateMonthString();
				updateDateLabels();
//				updateColumnIdentifiers();
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
				startOfWeek = startOfWeek.minusDays(7);
				endOfWeek = startOfWeek.plusDays(6);
				
				updateMonthString();
				updateDateLabels();
//				updateColumnIdentifiers();
				
			}
			
		});
		

		
		myAppointmentsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				MyAppointmentsPanel myAppointmentsPanel = new MyAppointmentsPanel(client, currentUser, null);
			}
		});
		createAppointmentButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				CreateAppointmentPanel createAppointmentPanel = new CreateAppointmentPanel(client);
			}
		});
		
	}
	
	public void updateDateLabels(){

		mondayLabel.setText("" + startOfWeek.getDayOfMonth() + "." + startOfWeek.monthOfYear().getAsText());
		
		tuesdayLabel.setText("" + (startOfWeek.plusDays(1).getDayOfMonth()) + "." + startOfWeek.plusDays(1).monthOfYear().getAsText());
		
		wednesdayLabel.setText("" + (startOfWeek.plusDays(2).getDayOfMonth()) + "." + startOfWeek.plusDays(2).monthOfYear().getAsText());
		
		thursdayLabel.setText("" + (startOfWeek.plusDays(3).getDayOfMonth()) + "." + startOfWeek.plusDays(3).monthOfYear().getAsText());
		
		fridayLabel.setText("" + (startOfWeek.plusDays(4).getDayOfMonth()) + "." + startOfWeek.plusDays(4).monthOfYear().getAsText());

		saturdayLabel.setText("" + (startOfWeek.plusDays(5).getDayOfMonth()) + "." + startOfWeek.plusDays(5).monthOfYear().getAsText());

		sundayLabel.setText("" + (startOfWeek.plusDays(6).getDayOfMonth()) + "." + startOfWeek.plusDays(6).monthOfYear().getAsText());
	}
	
	public void addStuffToPanels(){
		
		mondayLabel = new JLabel("mondayLabel");
		mondayLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		
		tuesdayLabel = new JLabel("tuesdayLabel");
		tuesdayLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		
		wednesdayLabel = new JLabel("wednesdayLabel");
		wednesdayLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		
		thursdayLabel = new JLabel("thursdayLabel");
		thursdayLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		
		fridayLabel = new JLabel("fridayLabel");
		fridayLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));

		saturdayLabel = new JLabel("saturdayLabel");
		saturdayLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));

		sundayLabel = new JLabel("sundayLabel");
		sundayLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		
		updateDateLabels();

		GroupLayout gl_weekPanel = new GroupLayout(weekPanel);
		gl_weekPanel.setHorizontalGroup(
			gl_weekPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_weekPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(skipToDateLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(skipToDateTextField, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(skipToYearLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(skipToYearTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(skipToWeekLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(skipToWeekTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(76)
					.addComponent(lastWeekButton)
					.addGap(18)
					.addComponent(weekLabel)
					.addGap(18)
					.addComponent(nextWeekButton)
					.addContainerGap(583, Short.MAX_VALUE))
		);
		gl_weekPanel.setVerticalGroup(
			gl_weekPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_weekPanel.createSequentialGroup()
					.addGroup(gl_weekPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_weekPanel.createSequentialGroup()
							.addGap(5)
							.addGroup(gl_weekPanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(skipToDateLabel)
								.addComponent(skipToYearLabel)
								.addComponent(skipToDateTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(skipToYearTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(skipToWeekLabel)
								.addComponent(skipToWeekTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(weekLabel)))
						.addComponent(nextWeekButton)
						.addComponent(lastWeekButton))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		weekPanel.setLayout(gl_weekPanel);

		notificationPanel.add(logoutButton);

		buttonPanel.add(myCalendarButton);
		buttonPanel.add(myAppointmentsButton);
		buttonPanel.add(createAppointmentButton);
		
	}
	
	public void setColors(){
		
		this.setBackground(UIManager.getColor("Panel.background"));
		headlinePanel.setBackground(Color.LIGHT_GRAY);
		buttonPanel.setBackground(UIManager.getColor("Panel.background"));
		weekPanel.setBackground(Color.LIGHT_GRAY);
		notificationPanel.setBackground(UIManager.getColor("Panel.background"));
	}


	public void createLayout() {
		
		layeredPane = new JLayeredPane();
	

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(headlinePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
								.addComponent(notificationPanel, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 1126, Short.MAX_VALUE)
								.addComponent(weekPanel, GroupLayout.DEFAULT_SIZE, 1126, Short.MAX_VALUE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(171)
									.addComponent(mondayLabel, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
									.addGap(28)
									.addComponent(tuesdayLabel, GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(wednesdayLabel, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
									.addGap(30)
									.addComponent(thursdayLabel, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(fridayLabel, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
									.addGap(14)
									.addComponent(saturdayLabel, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
									.addGap(37)
									.addComponent(sundayLabel, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
									.addGap(50)))))
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
							.addGap(3)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(sundayLabel)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(thursdayLabel)
									.addComponent(saturdayLabel)
									.addComponent(fridayLabel)
									.addComponent(tuesdayLabel)
									.addComponent(mondayLabel)
									.addComponent(wednesdayLabel)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)))
					.addGap(11))
		);
		
		calendarPanel = new JPanel();
		calendarPanel.setBounds(0, 0, 1126, 599);
		layeredPane.add(calendarPanel);
		
		JScrollPane calendarScrollPane = new JScrollPane();
		
		GroupLayout gl_calendarPanel = new GroupLayout(calendarPanel);
		gl_calendarPanel.setHorizontalGroup(
			gl_calendarPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(calendarScrollPane, GroupLayout.DEFAULT_SIZE, 1126, Short.MAX_VALUE)
		);
		gl_calendarPanel.setVerticalGroup(
			gl_calendarPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_calendarPanel.createSequentialGroup()
					.addComponent(calendarScrollPane, GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
					.addContainerGap())
		);
		

		
		table = new MyJTable();
		table.setRowHeight(80);
		
		
		calendarScrollPane.setViewportView(table);
		initialTableContents = new Object [][] {
				{"00:00", "", "", "", "", "", "", ""},
				{"01:00", "", "", "", "", "", "", ""},
				{"02:00", "", "", "", "", "", "", ""},
				{"03:00", "", "", "", "", "", "", ""},
				{"04:00", "", "", "", "", "", "", ""},
				{"05:00", "", "", "", "", "", "", ""},
				{"06:00", "", "", "", "", "", "", ""},
				{"07:00", "", "", "", "", "", "", ""},
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
				{"19:00", "", "", "", "", "", "", ""},
				{"20:00", "", "", "", "", "", "", ""},
				{"21:00", "", "", "", "", "", "", ""},
				{"22:00", "", "", "", "", "", "", ""},
				{"23:00", "", "", "", "", "", "", ""}
		};
		tableModel = new DefaultTableModel(
				initialTableContents,
					columnNames = new String[]  {
						"Tid","Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "L�rdag", "S�ndag"});
		table.setModel(tableModel);
		calendarPanel.setLayout(gl_calendarPanel);
		
		
		for(int i=1; i<table.getColumnCount();i++){
			table.getColumnModel().getColumn(i).setCellRenderer(new TableRenderer());
		}
		table.setBackground(Color.LIGHT_GRAY);
		table.addMouseListener(new MouseListener(){
	
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				
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
		
		participantDeclinedPanel = new ParticipantDeclinedPanel(client);
		participantDeclinedPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		participantDeclinedPanel.setBounds(0, 111, 399, 262);
		layeredPane.add(participantDeclinedPanel);
		
		okNotificationBox = new OKNotificationBox();
		okNotificationBox.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		okNotificationBox.setBounds(0, 111, 334, 234);
		layeredPane.add(okNotificationBox);
		
		panel_1 = new JPanel();
		panel_1.setBounds(31, 99, 106, 64);
		
		JScrollPane scrollPane = new JScrollPane();

		GroupLayout gl_notificationPanel = new GroupLayout(notificationPanel);
		gl_notificationPanel.setHorizontalGroup(
			gl_notificationPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_notificationPanel.createSequentialGroup()
					.addGroup(gl_notificationPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_notificationPanel.createSequentialGroup()
							.addGap(10)
							.addComponent(notificationLabel))
						.addGroup(gl_notificationPanel.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)))
					.addContainerGap())
				.addGroup(gl_notificationPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(logoutButton)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_notificationPanel.createSequentialGroup()
					.addComponent(starfighterLabel, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)
					.addGap(8))
		);
		gl_notificationPanel.setVerticalGroup(
			gl_notificationPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_notificationPanel.createSequentialGroup()
					.addGap(9)
					.addComponent(notificationLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 235, GroupLayout.PREFERRED_SIZE)
					.addGap(40)
					.addComponent(starfighterLabel, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
					.addGap(30)
					.addComponent(logoutButton)
					.addContainerGap(48, Short.MAX_VALUE))
		);
		
		this.notificationModel = new DefaultListModel<Notification>();
		list = new JList<Notification>();
		list.setModel(this.notificationModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e){
				Notification n;
				//Without this if statement both mouse click, then mouse move to another element and then mouse released will be recognized in valueChanged()
				//With this if statement only the element(s) selected(highlighted) when mouse is released will recognized in valueChanged()
				if(list.getValueIsAdjusting() == false && list.getSelectedIndex() != -1){
					n =  list.getSelectedValue();
					if(n.getType().equals(NotificationEnum.INVITATION)){
						meetingInvitationPanel.setTitle("M�teinnkalling fra " + n.getAdmin());
						meetingInvitationPanel.setTitleOfMeeting(n.getTitle());
						meetingInvitationPanel.setDescription(n.getDescription());
						meetingInvitationPanel.setStart(n.getStart());
						meetingInvitationPanel.setEnd(n.getEnd());
						meetingInvitationPanel.setLocation(n.getLocation());
						meetingInvitationPanel.setNotification(n);
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
						participantDeclinedPanel.setNotification(n);
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
			gl_panel1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel1.createSequentialGroup()
					.addContainerGap()
					.addComponent(headlineLabel, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE)
					.addGap(67)
					.addComponent(yearAndDateLabel, GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
					.addGap(42))
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
		
		
		// Notication update
		notUpdate = new NotificationUpdate(this);
		notUpdate.run();
	}
	
	private void updateNotification() {
		System.out.println("fetchNotificaions");
		this.notificationModel.removeAllElements();
		ArrayList<Notification> notifications = client.fetchNotifications(currentUser.getUsername());
		if(notifications == null){
			this.list.setModel(notificationModel); // Leste p� stackoverflow at dette skulle gj�res
			this.list.repaint(); // Vet ikke om denne gj�r noe.
			return;
		}
		if(notifications.isEmpty()){
			this.list.setModel(notificationModel); // Leste p� stackoverflow at dette skulle gj�res
			this.list.repaint(); // Vet ikke om denne gj�r noe.
			return;
		}
		this.notificationModel.removeAllElements();
		for(int i = 0; i < notifications.size(); i++) {
			this.notificationModel.addElement(notifications.get(i));
		}
		this.list.setModel(notificationModel); // Leste p� stackoverflow at dette skulle gj�res
		this.list.repaint(); // Vet ikke om denne gj�r noe.
	}

	public static Person getCurrentUser() {
		return currentUser;
	}
	
	public static void updateRowHeights() {
	    try
	    {
	        for (int row = 0; row < table.getRowCount(); row++)
	        {
	            int rowHeight = table.getRowHeight();

	            for (int column = 0; column < table.getColumnCount(); column++)
	            {
	                Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
	                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
	            }

	            table.setRowHeight(row, rowHeight);
	        }
	    }
	    catch(ClassCastException e) {}
	}
	
	class MyJTable extends JTable {
		public TableCellEditor getCellEditor(int row, int column) {
		     return new CalendarCellEditor();
		}
	}
	
	class NotificationUpdate extends Thread implements ActionListener {
		private HomeGUI gui;
		Timer timer = new Timer(30000, this);
		public NotificationUpdate(HomeGUI gui) {
			this.gui = gui;
			this.timer.setRepeats(true);
			this.timer.setInitialDelay(0);
		}
		@Override
		public void run() {
			timer.start();
		}
		@Override
		public void actionPerformed(ActionEvent arg0) {
			gui.updateNotification();
		}
		
		public void update() {
			gui.updateNotification();
		}
		
	}
	
	public static Client getClient(){

		return client;
	}
	
	public static void insertAppointmentsIntoTable(){
		if(table != null) {
			for(int i=1; i<table.getColumnCount(); i++){
				for(int j=0; j<table.getRowCount(); j++){
					table.setValueAt("", j, i);
				}
			}
		}
		int row;
		int column;
		Appointment app;
		for(int a=0; a<showAllThese.size(); a++){
			allMyAppointments = client.fetchAllAppointments(showAllThese.get(a).getUsername());
			if(allMyAppointments != null){
				for(int i=0; i<allMyAppointments.size(); i++){
					app = allMyAppointments.get(i);
					if(app.getStart().isBefore(endOfWeek) && app.getStart().isAfter(startOfWeek)){
						column = app.getStart().getDayOfWeek();
						row = app.getStart().getHourOfDay();
						/*
						 * Sjekk gjennom lista med avtaler (allMyAppointments)
						 * Hvis avtalens start-dato er mellom startDay og endDay:
						 * Break hvis start-dato er forbi endDay
						 * column = appointment.getDayOfWeek()
						 * row = start-time
						 * table.setValueAt(row, column);
						 * */
						ArrayList<Appointment> appList = new ArrayList<Appointment>();
						ArrayList<Appointment> currentlyInWantedCell = null;
						Object listBeforeConverted = table.getValueAt(row, column);
						if(listBeforeConverted != null) {
							if(listBeforeConverted instanceof ArrayList){
								currentlyInWantedCell = (ArrayList)listBeforeConverted;
						
							for(int j=0; j<currentlyInWantedCell.size(); j++){
								if(app.getStart().getWeekOfWeekyear() == currentlyInWantedCell.get(j).getStart().getWeekOfWeekyear())
									appList.add(currentlyInWantedCell.get(j));
								else
									currentlyInWantedCell.clear();
							}
							}
						}
						appList.add(app); //Denne legger den til i lista...
						table.setValueAt(appList, row, column);
					}
					else if(app.getStart().isAfter(endOfWeek))
						break;
				}
			}
		}
	}
	
	public static DateTime getStartOfWeek(){
		return startOfWeek;
	}
	
	public static DateTime getEndOfWeek(){
		return endOfWeek;
	}
	
	public static NotificationUpdate getNotificationUpdate() {
		return notUpdate;
	}
}
