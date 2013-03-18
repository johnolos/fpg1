package gui;


import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.SpinnerNumberModel;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.AbstractListModel;
import javax.swing.ScrollPaneConstants;

import baseClasses.Appointment;
import baseClasses.Person;
import baseClasses.Room;
import javax.swing.JSpinner;

import ktn.Client;

import org.joda.time.DateTime;

import database.Database;


public class CreateAppointmentPanel extends JPanel {
	
	Database db;
	
	DateTime jodaTime = new DateTime();
	
	JFrame frmOpprettAvtale;
	private JTextField titleField;
	private JTextField locationField;

	
	JComboBox<Room> roomBox;
	DefaultComboBoxModel<Room> boxModel;
	
	JList<Person> list;
	static DefaultListModel<Person> model;
	JTextPane descriptionArea;
	
	JSpinner dateDaySpinner;
	JSpinner dateMonthSpinner;
	JSpinner dateYearSpinner;
	JSpinner startHourSpinner;
	JSpinner startMinuteSpinner;
	JSpinner endHourSpinner;
	JSpinner endMinuteSpinner;

	private Client client;
	
	CreateAppointmentPanel(final Client client){
		
		this.client = client;
		
		// ----------------------------------------------------------------//
		// Frame
		// ----------------------------------------------------------------//

		
		frmOpprettAvtale = new JFrame();
		frmOpprettAvtale.setTitle("Opprett Avtale");
		frmOpprettAvtale.setSize(526, 600);
		frmOpprettAvtale.setLocationRelativeTo(null);
		frmOpprettAvtale.setVisible(true);
		frmOpprettAvtale.setContentPane(this);
		
		JLabel createAppointmentLabel = new JLabel("Opprett avtale");
		createAppointmentLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		JLabel titleLabel = new JLabel("Tittel:");
		
		JLabel dateLabel = new JLabel("Dato:");
		
		JLabel startLabel = new JLabel("Start:");
		
		JLabel endLabel = new JLabel("Slutt:");
		
		JLabel locationLabel = new JLabel("Sted:");
		
		JLabel roomLabel = new JLabel("Rom:");
		
		JLabel descriptionLabel = new JLabel("Beskrivelse:");
		
		JLabel peopleLabel = new JLabel("Deltagere:");
		
		titleField = new JTextField();
		titleField.setColumns(10);
		
		locationField = new JTextField();
		locationField.setColumns(10);
		
		roomBox = new JComboBox<Room>();
		roomBox.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				updateRooms();
			}

			@Override
			public void focusLost(FocusEvent arg0) {
			}
			
		});
		
		boxModel = new DefaultComboBoxModel<Room>();
//		boxModel.addElement(new Room(20, "TESTROM"));
//		boxModel.addElement(new Room(30, "TESTROM2"));
		roomBox.setModel(boxModel);
		roomBox.setRenderer(new ComboBoxRenderer());
		
		descriptionArea = new JTextPane();
		
		JButton cancelButton = new JButton("Avbryt");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmOpprettAvtale.dispose();
			}
		});
		
		JButton editParticipantsButton = new JButton("Edit");
		editParticipantsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				EditParticipantsPanel editParticipantsPanel = new EditParticipantsPanel(frmOpprettAvtale, model, client);
			
			}
		});
		
		model = new DefaultListModel<Person>();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		try{
			db = new Database();
		} catch(Exception e){
			
		}
		
		/*
		 * Denne fungerer med unntak av Room, som er satt til roomID = 1,
		 * da den ikke kan være satt til null.
		 */
		JButton saveButton = new JButton("Lagre");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Appointment app = new Appointment(new DateTime((Integer) dateYearSpinner.getValue(), (Integer) dateMonthSpinner.getValue(), (Integer)dateDaySpinner.getValue(), (Integer)startHourSpinner.getValue(), (Integer)startMinuteSpinner.getValue()), new DateTime((Integer)dateYearSpinner.getValue(), (Integer)dateMonthSpinner.getValue(), (Integer)dateDaySpinner.getValue(), (Integer)endHourSpinner.getValue(), (Integer)endMinuteSpinner.getValue()), locationField.getText(), titleField.getText(), (Room) roomBox.getSelectedItem(), descriptionArea.getText(), HomeGUI.getCurrentUser().getUsername());
				if(client.createAppointment(app)){
					while(model.size()>0){
						client.createPersonAppointment(model.remove(0),app);
					}
					//---------------------------------
					ArrayList<Appointment> appList = new ArrayList<Appointment>();
					ArrayList<Appointment> currentlyInWantedCell = null;
					//ENDRE LOKASJONEN HER
					Object listBeforeConverted = HomeGUI.table.getValueAt(1, 1);
					if(listBeforeConverted != null) {
						if(listBeforeConverted instanceof ArrayList){
							currentlyInWantedCell = (ArrayList)listBeforeConverted;
					
						for(int i=0; i<currentlyInWantedCell.size(); i++){
							appList.add(currentlyInWantedCell.get(i));
						}
						}
					}
					appList.add(app);
					//ENDRE LOKASJONEN HER
					HomeGUI.table.setValueAt(appList, 1, 1);
//					HomeGUI.updateRowHeights();
					frmOpprettAvtale.dispose();
				}
			}
		});
		
		JLabel lblDag = new JLabel("dag:");
		
		dateDaySpinner = new JSpinner();
		
		JLabel lblMnd = new JLabel("mnd.:");
		
		dateMonthSpinner = new JSpinner();
		
		JLabel lblr = new JLabel("\u00E5r:");
		
		dateYearSpinner = new JSpinner();
		dateDaySpinner.setModel(new SpinnerNumberModel(1, 1, 31, 1));
		dateMonthSpinner.setModel(new SpinnerNumberModel(1, 1, 12, 1));
		dateYearSpinner.setModel(new SpinnerNumberModel(jodaTime.getYear(), jodaTime.getYear(), jodaTime.getYear()+20, 1));
		
		
		startHourSpinner = new JSpinner();
		
		JLabel label = new JLabel(":");
		
		startMinuteSpinner = new JSpinner();
		
		endHourSpinner = new JSpinner();
		
		JLabel label_1 = new JLabel(":");
		
		endMinuteSpinner = new JSpinner();
		
		startHourSpinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		startMinuteSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		endHourSpinner.setModel(new SpinnerNumberModel(0, 0, 23, 1));
		endMinuteSpinner.setModel(new SpinnerNumberModel(0, 0, 59, 1));
			
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(59)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(descriptionLabel)
						.addComponent(locationLabel)
						.addComponent(roomLabel)
						.addComponent(peopleLabel)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addComponent(startLabel)
							.addComponent(endLabel))
						.addComponent(dateLabel)
						.addComponent(titleLabel))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addGroup(groupLayout.createSequentialGroup()
												.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
													.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
													.addComponent(roomBox, 0, 239, Short.MAX_VALUE)
													.addComponent(locationField, 174, 239, Short.MAX_VALUE)
													.addGroup(groupLayout.createSequentialGroup()
														.addComponent(saveButton)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(cancelButton)
														.addGap(29))
													.addComponent(descriptionArea, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
													.addComponent(titleField, GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(editParticipantsButton, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(lblDag)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(dateDaySpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(lblMnd)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(dateMonthSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(lblr)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(dateYearSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addComponent(createAppointmentLabel))
									.addGap(26))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(startHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(label)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(startMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(254))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addComponent(endHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(endMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(254))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(createAppointmentLabel, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(titleField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(titleLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(dateLabel)
						.addComponent(lblDag)
						.addComponent(dateDaySpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMnd)
						.addComponent(dateMonthSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblr)
						.addComponent(dateYearSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(startLabel)
						.addComponent(startHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label)
						.addComponent(startMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(endLabel)
						.addComponent(endHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_1)
						.addComponent(endMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(locationField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(locationLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(roomBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(roomLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(descriptionLabel)
						.addComponent(descriptionArea, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(editParticipantsButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(peopleLabel))
							.addGap(68))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(saveButton)
						.addComponent(cancelButton))
					.addGap(43))
		);
		
	
		list = new JList<Person>();
		list.setModel(model);
		list.setCellRenderer(new PersonListRenderer());
		scrollPane.setViewportView(list);
		setLayout(groupLayout);
		
	}
	private void setModelContent(){
		ArrayList<Person> persons = client.fetchPersons("H");//Må gjøre noe her!
		// HER OGSÅ! DETTE FORKLARER IKKE HVA SOM MÅ GJØRES.
		// I tilfellet noen andre må gjøre arbeidet.
		while(persons.size()>0){
			this.model.addElement(persons.remove(0));
		}
	}
	/**
	 * Used to update room model
	 */
	private void updateRooms() {
		// hh:mm - YYYY-MM-DD
		this.boxModel.removeAllElements();
		ArrayList<Room> rooms = client.fetchRooms(String.valueOf((Integer)dateYearSpinner.getValue())+ "-" + String.valueOf((Integer)dateMonthSpinner.getValue()) + "-" +String.valueOf((Integer)dateDaySpinner.getValue()),
				String.valueOf((Integer)startHourSpinner.getValue()) + ":" + String.valueOf((Integer)startMinuteSpinner.getValue()),
				String.valueOf((Integer)endHourSpinner.getValue()) + ":" + String.valueOf((Integer)endMinuteSpinner.getValue()));

		for(int i = 0; i < rooms.size(); i++) {
			boxModel.addElement(rooms.get(i));
		}
	}
	
	private String formatNumber(int number){
		if(number <=9)
			return "0"+number;
		return number+"";
	}
	
	
}
