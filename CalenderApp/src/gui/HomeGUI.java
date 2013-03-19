package gui;


import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
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
	private String monthStr;
	private DateTime date;
	private int day;
	private String startTime;
	private String endTime;
	private DateTime endOfWeek, startOfWeek;

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

	private ArrayList<Appointment> allMyAppointments;

	public HomeGUI(Person user, Client client) {
		
		
		yearAndDateLabel = new JLabel("");
		createDateTime();
		
		this.currentUser = user;
		this.client = client;
		allMyAppointments = client.fetchAllAppointments(currentUser.getUsername());
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
		skipToYearLabel = new JLabel("Hopp til �r:");
		
		notificationLabel = new JLabel("Notifikasjoner");
		notificationLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		headlineLabel = new JLabel("GRUPPE 1s KALENDERSYSTEM!!!");
		headlineLabel.setFont(new Font("Tahoma", Font.PLAIN, 26));
		
		weekLabel = new JLabel("Uke " + week);
		
		yearAndDateLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));

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
	
	
	// ----------------------------------------------------------------//
	// Methods
	// ----------------------------------------------------------------//
	
	
	// ----------------------------------------------------------------//
	// DateTime
	// ----------------------------------------------------------------//
	
	/*
	public void adjustMonth(){
		boolean adjustMonth = false;
		 //Husk leap year

		if (month < 8) {
			if (month == 2) {
				if (endDay > 28) {
					endDay = endDay - 28;
					adjustMonth = true;
				}
			} else if (month % 2 == 0) {
				if (endDay > 30) {
					endDay = endDay - 30;
					adjustMonth = true;
				}
			} else if (month % 2 == 1) {
				if (endDay > 31) {
					endDay = endDay - 31;
					adjustMonth = true;
				}
			}
			
			if (adjustMonth) {
				endMonth += 1;

			}
	
		} 
		
		else {
			if (month % 2 == 0) {
				if (endDay > 31) {
					endDay = endDay - 31;
					adjustMonth = true;
				}
			}
			else if (month % 2 == 1) {
				if (endDay > 30) {
					endDay = endDay - 30;
					adjustMonth = true;
				}
			}
		}
			
			if (adjustMonth) {
				endMonth += 1;
			}
			if (endMonth == 13){
				endMonth = 1;
				year += 1;
		}
	}
	*/
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
		
		
		
	}
	
	
	
	public void addListeners(){
		
		weekTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if(weekTextField.getText() == "") {
					return;
				}
				int textFieldNumber = Integer.parseInt(weekTextField.getText());
				if(textFieldNumber < 1 && textFieldNumber > 52) {
					return;
				}
				
				//
				//
				//
				//HER SKAL VI GJ�RE OM SLIK AT VI HELLER S�KER OPP EN DATO - DET ER LETTERE OG FINERE.
				//
				//
				//
				updateMonthString();
				
					
				
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
				
			}
			
		});
		
		skipToYearTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(skipToYearTextField.getText() == "") {
					return;
				}
				int differenceYears = Integer.parseInt(skipToYearTextField.getText()) - (int)startOfWeek.getYear();
				System.out.println(differenceYears);
				startOfWeek = startOfWeek.plusYears(differenceYears);
				endOfWeek = endOfWeek.plusYears(differenceYears);
				updateMonthString();
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
		

		
		table = new MyJTable();
		table.setRowHeight(80);
		
		
		calendarScrollPane.setViewportView(table);
		DefaultTableModel tableModel = new DefaultTableModel(
				new Object [][] {
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
					},
					new String[]  {
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
		
		updateNotification();

	}
	
	private void updateNotification() {
		ArrayList<Notification> notifications = client.fetchNotifications(currentUser.getUsername());
		if(notifications == null){
			return;
		}
		if(notifications.isEmpty()){
			return;
		}
		for(int i = 0; i < notifications.size(); i++) {
			this.notificationModel.addElement(notifications.get(i));
			System.out.println(notifications.get(i).getListMessage());
		}
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
	
	public static Client getClient(){
		return client;
	}
	
}
