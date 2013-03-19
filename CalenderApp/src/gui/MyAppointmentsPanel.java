package gui;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTextArea;
import javax.swing.JSpinner;

import org.joda.time.DateTime;

import database.Database;

import baseClasses.Appointment;
import baseClasses.Person;
import baseClasses.Room;

import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;

import ktn.Client;


public class MyAppointmentsPanel extends JPanel {
	
	Database db;
	
	DateTime jodaTime = new DateTime();
	
	private JTextField titleField;
	private JTextField locationField;
	
	private JComboBox<Room> roomComboBox;
	DefaultComboBoxModel<Room> boxModel;
	
	private JTextArea descriptionArea;
	private JList<Person> participantsList;
	
	private JFrame frame;
	
	JButton editParticipantsButton;
	JButton removeAlarmButton;
	JButton saveChangesButton;
	JButton cancelChangesButton;
	JButton declineMeetingButton;
	JButton cancelMeetingButton;
	private JScrollPane scrollPane;
	private JList<Appointment> myAppointmentsList;
	DefaultListModel<Appointment> appointmentsModel;
	static DefaultListModel<Person> model = new DefaultListModel<Person>();
	private JLabel alarmLabel;

	JSpinner startHourSpinner;
	JSpinner startMinuteSpinner;	
	JSpinner endHourSpinner;
	JSpinner endMinuteSpinner;
	JSpinner dateDaySpinner;
	JSpinner dateMonthSpinner;
	JSpinner dateYearSpinner;
	JSpinner alarmDaySpinner;;
	JSpinner alarmMonthSpinner;
	JSpinner alarmYearSpinner;
	JSpinner alarmHourSpinner;
	JSpinner alarmMinuteSpinner;
	
	Appointment ap;
	
	private Client client;
	private Person user;
	
	/**
	 * Create the panel.
	 */
	public MyAppointmentsPanel(final Client client, Person person, Appointment selectApp) {
		this.client = client;
		
		try{
			db = new Database();
		} catch(Exception e){
			//lulz
		}
		
		// ----------------------------------------------------------------//
		// Frame
		// ----------------------------------------------------------------//

		frame = new JFrame();
		frame.setTitle("Mine avtaler");
		frame.setSize(870, 730);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setContentPane(this);
		
		JLabel lblMineAvtaler = new JLabel("Mine Avtaler");
		lblMineAvtaler.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		JLabel titleLabel = new JLabel("Tittel:");
		
		JLabel dateLabel = new JLabel("Dato:");
		
		JLabel startLabel = new JLabel("Start:");
		
		JLabel endLabel = new JLabel("Slutt:");
		
		JLabel locationLabel = new JLabel("Sted:");
		
		JLabel roomLabel = new JLabel("Rom:");
		
		JLabel descriptionLabel = new JLabel("Beskrivelse:");
		
		JLabel participantsLabel = new JLabel("Deltagere:");
		
		titleField = new JTextField();
		titleField.setEnabled(false);
		titleField.setColumns(10);
		
		locationField = new JTextField();
		locationField.setEnabled(false);
		locationField.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		roomComboBox = new JComboBox<Room>();
		roomComboBox.setEnabled(false);
		boxModel = new DefaultComboBoxModel<Room>();
		roomComboBox.setModel(boxModel);
		roomComboBox.setRenderer(new ComboBoxRenderer());
		
		editParticipantsButton = new JButton("Edit");
		editParticipantsButton.setEnabled(false);
		editParticipantsButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				EditParticipantsPanel editParticipanstPanel = new EditParticipantsPanel(frame, model, HomeGUI.getClient());
			}
			
		});
		
		saveChangesButton = new JButton("Lagre endringer");
		saveChangesButton.setEnabled(true);
		saveChangesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Appointment newAppointment = new Appointment(new DateTime((int)dateYearSpinner.getValue(), (int)dateMonthSpinner.getValue(), (int)dateDaySpinner.getValue(),(int)startHourSpinner.getValue(),(int)startMinuteSpinner.getValue()),
						new DateTime((int)dateYearSpinner.getValue(), (int)dateMonthSpinner.getValue(), (int)dateDaySpinner.getValue(),(int)endHourSpinner.getValue(),(int)endMinuteSpinner.getValue()), 
						locationField.getText(), titleField.getText(), (Room)roomComboBox.getSelectedItem(), descriptionArea.getText(),myAppointmentsList.getSelectedValue().getAdmin());
				boolean change = client.changeAppointment(myAppointmentsList.getSelectedValue(), newAppointment);
				ArrayList<Appointment> appointmentsListFromDb;
				if(change) {
					appointmentsModel.removeAllElements();
					appointmentsListFromDb = client.fetchAllAppointments(HomeGUI.getCurrentUser().getUsername());
					for(int i=0; i<appointmentsListFromDb.size(); i++){
						appointmentsModel.addElement(appointmentsListFromDb.get(i));
						if(appointmentsListFromDb.get(i).getRoom().getName().equals(newAppointment.getRoom().getName()) && appointmentsListFromDb.get(i).getStart().equals(newAppointment.getStart())){
							myAppointmentsList.setSelectedIndex(i);
						}
							
					}
					
				}
			}
		});
		
		cancelChangesButton = new JButton("Avbryt");
		cancelChangesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.dispose();
				
			}
		});
		
		declineMeetingButton = new JButton("Meld avbud");
		declineMeetingButton.setEnabled(false);
		declineMeetingButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(client.sendDecline(HomeGUI.getCurrentUser().getUsername(), myAppointmentsList.getSelectedValue()));
					appointmentsModel.removeAllElements();
					updateAppointmentList();
			}
			
		});
		
		cancelMeetingButton = new JButton("Avlys m\u00F8te");
		cancelMeetingButton.setEnabled(false);
		cancelMeetingButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(client.sendDecline(HomeGUI.getCurrentUser().getUsername(), myAppointmentsList.getSelectedValue())){
					appointmentsModel.removeAllElements();
					updateAppointmentList();
				}
					
			}
			
		});
		
		descriptionArea = new JTextArea();
		descriptionArea.setEnabled(false);
		
		scrollPane = new JScrollPane();
		myAppointmentsList = new JList<Appointment>();
		
		alarmLabel = new JLabel("Alarm:");
		
		startHourSpinner = new JSpinner();
		startHourSpinner.setEnabled(false);
		
		startMinuteSpinner = new JSpinner();
		startMinuteSpinner.setEnabled(false);
		
		JLabel startMinuteLabel = new JLabel(":");
		
		endHourSpinner = new JSpinner();
		endHourSpinner.setEnabled(false);
		
		endMinuteSpinner = new JSpinner();
		endMinuteSpinner.setEnabled(false);
		
		JLabel endMinuteLabel = new JLabel(":");
		
		JLabel dateMonthLabel = new JLabel("mnd.:");
		
		JLabel dateDayLabel = new JLabel("dag:");
		
		JLabel dateYearLabel = new JLabel("\u00E5r:");
		
		dateDaySpinner = new JSpinner();
		dateDaySpinner.setEnabled(false);
		
		dateMonthSpinner = new JSpinner();
		dateMonthSpinner.setEnabled(false);
		
		dateYearSpinner = new JSpinner();
		dateYearSpinner.setEnabled(false);
		
		JLabel alarmMonthLabel = new JLabel("mnd.:");
		
		alarmDaySpinner = new JSpinner();
		alarmDaySpinner.setEnabled(false);
		
		JLabel alarmDayLabel = new JLabel("dag:");
		
		alarmMonthSpinner = new JSpinner();
		alarmMonthSpinner.setEnabled(false);
		
		JLabel alarmYearLabel = new JLabel("\u00E5r:");
		
		alarmYearSpinner = new JSpinner();
		alarmYearSpinner.setEnabled(false);
		
		alarmHourSpinner = new JSpinner();
		alarmHourSpinner.setEnabled(false);
		
		JLabel alarmMinuteLabel = new JLabel(":");
		
		alarmMinuteSpinner = new JSpinner();
		alarmMinuteSpinner.setEnabled(false);
		
		removeAlarmButton = new JButton("Fjern alarm");
		removeAlarmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		removeAlarmButton.setEnabled(false);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(23)
									.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
									.addGap(27)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(descriptionLabel)
										.addComponent(roomLabel)
										.addComponent(participantsLabel)
										.addComponent(locationLabel)
										.addComponent(endLabel)
										.addComponent(startLabel)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(titleLabel)
											.addComponent(dateLabel))
										.addComponent(alarmLabel)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(60)
									.addComponent(declineMeetingButton)
									.addGap(28)
									.addComponent(cancelMeetingButton)))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(24)
											.addComponent(saveChangesButton)
											.addGap(26)
											.addComponent(cancelChangesButton))
										.addGroup(groupLayout.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
												.addComponent(titleField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
												.addComponent(locationField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
												.addComponent(roomComboBox, Alignment.LEADING, 0, 291, Short.MAX_VALUE)
												.addComponent(descriptionArea, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
												.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(editParticipantsButton)
											.addPreferredGap(ComponentPlacement.RELATED)))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(18)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
											.addComponent(dateDayLabel)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(endHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(startHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(dateDaySpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(dateMonthLabel))
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(startMinuteLabel)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(startMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(endMinuteLabel)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(endMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addGap(2)
										.addComponent(dateMonthSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(7)
										.addComponent(dateYearLabel)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(dateYearSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGap(44)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(34)
											.addComponent(alarmHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(alarmMinuteLabel)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(alarmMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(32)
											.addComponent(removeAlarmButton))
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(alarmDayLabel)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(alarmDaySpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(alarmMonthLabel)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(alarmMonthSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(14)
											.addComponent(alarmYearLabel)
											.addGap(4)
											.addComponent(alarmYearSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addGap(13))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(130)
							.addComponent(lblMineAvtaler)))
					.addGap(58))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(11, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblMineAvtaler)
							.addGap(15)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(24)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(titleLabel)
								.addComponent(titleField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(dateLabel)
								.addComponent(dateYearLabel)
								.addComponent(dateDaySpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(dateMonthSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(dateYearSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(dateMonthLabel)
								.addComponent(dateDayLabel))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(startLabel)
								.addComponent(startHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(startMinuteLabel)
								.addComponent(startMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(endLabel)
								.addComponent(endHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(endMinuteLabel)
								.addComponent(endMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(locationLabel)
								.addComponent(locationField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(roomLabel)
								.addComponent(roomComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(descriptionLabel)
								.addComponent(descriptionArea, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
							.addGap(19)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
								.addComponent(participantsLabel)
								.addComponent(editParticipantsButton))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(alarmLabel)
								.addComponent(alarmDaySpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(alarmYearLabel)
								.addComponent(alarmMonthSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(alarmYearSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(alarmMonthLabel)
								.addComponent(alarmDayLabel))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(alarmHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(removeAlarmButton)
								.addComponent(alarmMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(alarmMinuteLabel))))
					.addGap(44)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(cancelMeetingButton)
						.addComponent(declineMeetingButton)
						.addComponent(saveChangesButton)
						.addComponent(cancelChangesButton))
					.addGap(407))
		);
		
		myAppointmentsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
				if(e.getValueIsAdjusting()){
					if(myAppointmentsList.getSelectedIndex() != -1){
						ap = myAppointmentsList.getSelectedValue();
						
						titleField.setText(ap.getTitle());
						dateDaySpinner.setValue(ap.getStart().getDayOfMonth());
						dateMonthSpinner.setValue(ap.getStart().getMonthOfYear());
						dateYearSpinner.setValue(ap.getStart().getYear());
						startHourSpinner.setValue(ap.getStart().getHourOfDay());
						startMinuteSpinner.setValue(ap.getStart().getMinuteOfHour());
						endHourSpinner.setValue(ap.getEnd().getHourOfDay());
						endMinuteSpinner.setValue(ap.getEnd().getMinuteOfHour());
						locationField.setText(ap.getLocation());
						boxModel.removeAllElements();
						Room room = ap.getRoom();
						boxModel.addElement(room);
						roomComboBox.setSelectedItem(room);
						descriptionArea.setText(ap.getDescription());
						model.removeAllElements();
						for(int i=0; i<ap.getParticipants().size(); i++){
							model.addElement(ap.getParticipants().get(i));
						}
						
						
						if(HomeGUI.getCurrentUser().getUsername().equals(ap.getAdmin()))
							setAllFieldsEnabled(true);
						else
							setAlarmFieldsEnabled(true);
						setButtonsEnabled(true);
					}
				}
				
			}
		});
		scrollPane.setViewportView(myAppointmentsList);
		appointmentsModel = new DefaultListModel<Appointment>();
		myAppointmentsList.setModel(appointmentsModel);
		myAppointmentsList.setCellRenderer(new appointmentListRenderer());
//		appointmentsModel.addElement(new Appointment(new DateTime(2014, 01, 02, 03, 00) , new DateTime(2014, 01, 02, 04, 04), "DunnoWhatThisFieldIs :(", "Videokonferanse med Steria", new Room(20, "testrom"), "WutIsDisField?", "WahtBistDeineFelt?"));
		
		String user = HomeGUI.getCurrentUser().getUsername();
		updateAppointmentList();
		
		
		participantsList = new JList<Person>();
		participantsList.setBackground(UIManager.getColor("Panel.background"));
		participantsList.setEnabled(false);
		scrollPane_1.setViewportView(participantsList);
		participantsList.setCellRenderer(new PersonListRenderer());
		model = new DefaultListModel<Person>();
		participantsList.setModel(model);
		setLayout(groupLayout);
		
		restrictSpinners();
		
		int appIsInList = -1;
		Appointment compareApp;
		if(selectApp != null){
			for(int i=0; i<appointmentsModel.getSize(); i++){
				compareApp = appointmentsModel.get(i);
				//Two appointments cannot have the same room if they start at the same time
				if( compareApp.getRoom().getName().equals(selectApp.getRoom().getName()) &&
					compareApp.getStart().equals(selectApp.getStart())){
					appIsInList = i;
					break;
				}
			}
			if(appIsInList != -1)
				myAppointmentsList.setSelectedIndex(appIsInList);
		}
		
	}
	
	private void setAllFieldsEnabled(boolean b){
		setAlarmFieldsEnabled(b);
		
		titleField.setEnabled(b);
		dateDaySpinner.setEnabled(b);
		dateMonthSpinner.setEnabled(b);
		dateYearSpinner.setEnabled(b);
		startHourSpinner.setEnabled(b);
		startMinuteSpinner.setEnabled(b);
		endHourSpinner.setEnabled(b);
		endMinuteSpinner.setEnabled(b);
		
		locationField.setEnabled(b);
		roomComboBox.setEnabled(b);
		descriptionArea.setEnabled(b);
		participantsList.setEnabled(b);
		
		setButtonsEnabled(b);
	}
	
	private void setAlarmFieldsEnabled(boolean b){
		alarmDaySpinner.setEnabled(b);
		alarmMonthSpinner.setEnabled(b);
		alarmYearSpinner.setEnabled(b);
		
		alarmHourSpinner.setEnabled(b);
		alarmMinuteSpinner.setEnabled(b);
	}
	
	
	private void setButtonsEnabled(boolean b){
		saveChangesButton.setEnabled(b);
		cancelChangesButton.setEnabled(b);
		
		if(HomeGUI.getCurrentUser().getUsername().equals(ap.getAdmin())){
			declineMeetingButton.setEnabled(false);
			cancelMeetingButton.setEnabled(true);
			editParticipantsButton.setEnabled(true);
		}
		else{
			declineMeetingButton.setEnabled(true);
			cancelMeetingButton.setEnabled(false);
		}
		
//		if(AVTALEN HAS ALARM)
			removeAlarmButton.setEnabled(b);
	}
	private void restrictSpinners(){
		startHourSpinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		endHourSpinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		alarmHourSpinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		
		startMinuteSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		endMinuteSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		alarmMinuteSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		
		dateDaySpinner.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		alarmDaySpinner.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		
		dateMonthSpinner.setModel(new SpinnerNumberModel(1, 1, 12, 1));
		alarmMonthSpinner.setModel(new SpinnerNumberModel(1, 1, 12, 1));
		
		dateYearSpinner.setModel(new SpinnerNumberModel(jodaTime.getYear(), jodaTime.getYear(), jodaTime.getYear()+20, 1));
		alarmYearSpinner.setModel(new SpinnerNumberModel(jodaTime.getYear(), jodaTime.getYear(), jodaTime.getYear()+20, 1));
	}
	
	public void updateAppointments() {
		appointmentsModel.removeAllElements();
		ArrayList<Appointment> appointments = client.fetchAllAppointments(user.getUsername());
		for(int i = 0; i < appointments.size(); i++) {
			appointmentsModel.addElement(appointments.get(i));
			
		}	
	}
	
	public JList getMyAppointmentsList(){
		return this.myAppointmentsList;
	}
	
	private void updateAppointmentList(){
		ArrayList<Appointment> list = client.fetchAllAppointments(HomeGUI.getCurrentUser().getUsername());
		for(int i=0; i<list.size(); i++){
			appointmentsModel.addElement(list.get(i));
		}
	}
}
