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
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import baseClasses.Notification;
import baseClasses.NotificationEnum;

public class HomeGUI extends JPanel {

	// ----------------------------------------------------------------//
	// Fields
	// ----------------------------------------------------------------//

	String[] columnNames;
	Object[][] data;
	private int ukeNr = 1;

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
	private JLabel weekLabel = new JLabel("Uke" + ukeNr);

	private JTextField weekTextField;

	static JList<Notification> list;
	
	private ImageIcon starFighterIcon;
	private ImageIcon forrigeUke;
	private ImageIcon nesteUke;
	private JPanel panel_1;
	private JButton btnDsadsa;
	private JPanel calendarPanel;
	
	MeetingInvitationPanel meetingInvitationPanel;
	OKNotificationBox okNotificationBox;
	ParticipantDeclinedPanel participantDeclinedPanel;
	private JTable table;

	static JLayeredPane layeredPane;

	public HomeGUI() {

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
		notificationLabel = new JLabel("Notifikasjoner");
		notificationLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
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
				
				ShowColleaguesPanel showColleagues = new ShowColleaguesPanel();
				
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
		
		this.setBackground(UIManager.getColor("Panel.background"));
		headlinePanel.setBackground(UIManager.getColor("Panel.background"));
		buttonPanel.setBackground(UIManager.getColor("Panel.background"));
		weekPanel.setBackground(UIManager.getColor("Panel.background"));
		notificationPanel.setBackground(UIManager.getColor("Panel.background"));
	}

	public void initTable() {
//		table.setRowHeight(100);
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
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"sfsdf", "dfsdf", "dwfsdf", "dsfsdf", "dsfsdf"},
				{"df", "h", "h", "h", null},
				{"hh", "h", "h", "fyh", "fyh"},
				{"hfy", "hf", "yh", "uh", null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column"
			}
		));
		GroupLayout gl_calendarPanel = new GroupLayout(calendarPanel);
		gl_calendarPanel.setHorizontalGroup(
			gl_calendarPanel.createParallelGroup(Alignment.TRAILING)
				.addComponent(table, GroupLayout.DEFAULT_SIZE, 1106, Short.MAX_VALUE)
		);
		gl_calendarPanel.setVerticalGroup(
			gl_calendarPanel.createParallelGroup(Alignment.TRAILING)
				.addComponent(table, GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
		);
		calendarPanel.setLayout(gl_calendarPanel);
		
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
		gl_panel1.setHorizontalGroup(gl_panel1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel1.createSequentialGroup()
						.addContainerGap()
						.addComponent(headlineLabel, GroupLayout.DEFAULT_SIZE,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGap(384)));
		gl_panel1.setVerticalGroup(gl_panel1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel1.createSequentialGroup()
						.addComponent(headlineLabel)
						.addContainerGap(GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		headlinePanel.setLayout(gl_panel1);
		buttonPanel.add(showColleaguesButton);
		setLayout(groupLayout);

	}
}
