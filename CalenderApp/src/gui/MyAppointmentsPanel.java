package gui;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
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
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;


public class MyAppointmentsPanel extends JPanel {
	
	DateTime jodaTime = new DateTime();
	
	private JTextField titleField;
	private JTextField locationField;
	
	private JComboBox roomComboBox;
	private JTextArea descriptionArea;
	private JList participantsList;
	
	private JFrame frame;
	
	JButton editParticipantsButton;
	JButton removeAlarmButton;
	JButton saveChangesButton;
	JButton cancelChangesButton;
	JButton declineMeetingButton;
	JButton cancelMeetingButton;
	private JScrollPane scrollPane;
	private JList myAppointmentsList;
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
	
	
	/**
	 * Create the panel.
	 */
	public MyAppointmentsPanel() {
		
		// ----------------------------------------------------------------//
		// Frame
		// ----------------------------------------------------------------//

		frame = new JFrame();
		frame.setTitle("Mine avtaler");
		frame.setSize(787, 730);
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
		
		roomComboBox = new JComboBox();
		roomComboBox.setEnabled(false);
		
		editParticipantsButton = new JButton("Edit");
		editParticipantsButton.setEnabled(false);
		
		saveChangesButton = new JButton("Lagre endringer");
		saveChangesButton.setEnabled(false);
		
		cancelChangesButton = new JButton("Avbryt");
		cancelChangesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.dispose();
				
			}
		});
		
		declineMeetingButton = new JButton("Meld avbud");
		declineMeetingButton.setEnabled(false);
		
		cancelMeetingButton = new JButton("Avlys m\u00F8te");
		cancelMeetingButton.setEnabled(false);
		
		descriptionArea = new JTextArea();
		descriptionArea.setEnabled(false);
		
		scrollPane = new JScrollPane();
		myAppointmentsList = new JList();
		
		alarmLabel = new JLabel("Alarm:");
		
		startHourSpinner = new JSpinner();
		startHourSpinner.setEnabled(false);
		
		startMinuteSpinner = new JSpinner();
		startMinuteSpinner.setEnabled(false);
		
		JLabel startHourLabel = new JLabel("time:");
		
		JLabel startMinuteLabel = new JLabel("min.:");
		
		JLabel endHourLabel = new JLabel("time:");
		
		endHourSpinner = new JSpinner();
		endHourSpinner.setEnabled(false);
		
		endMinuteSpinner = new JSpinner();
		endMinuteSpinner.setEnabled(false);
		
		JLabel endMinuteLabel = new JLabel("min.:");
		
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
		
		JLabel alarmHourLabel = new JLabel("time:");
		
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
		
		JLabel alarmMinuteLabel = new JLabel("min.:");
		
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
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addGap(24)
											.addComponent(saveChangesButton)
											.addGap(26)
											.addComponent(cancelChangesButton))
										.addGroup(groupLayout.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(titleField, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
												.addGroup(groupLayout.createSequentialGroup()
													.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
														.addComponent(locationField, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
														.addComponent(roomComboBox, Alignment.LEADING, 0, 227, Short.MAX_VALUE)
														.addComponent(descriptionArea, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
														.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(editParticipantsButton)
													.addPreferredGap(ComponentPlacement.RELATED)))))
									.addGroup(groupLayout.createSequentialGroup()
										.addGap(18)
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
												.addComponent(endHourLabel)
												.addComponent(startHourLabel))
											.addComponent(dateDayLabel))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(endHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(endMinuteLabel))
											.addGroup(groupLayout.createSequentialGroup()
												.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
													.addComponent(dateDaySpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
													.addComponent(startHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.RELATED)
												.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
													.addComponent(dateMonthLabel)
													.addComponent(startMinuteLabel))))
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(endMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(startMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGroup(groupLayout.createSequentialGroup()
												.addGap(2)
												.addComponent(dateMonthSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(7)
												.addComponent(dateYearLabel)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(dateYearSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
										.addGap(44)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(alarmHourLabel)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(alarmHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(alarmMinuteLabel)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(alarmMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
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
								.addComponent(startMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(startMinuteLabel)
								.addComponent(startHourLabel))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(endLabel)
								.addComponent(endHourLabel)
								.addComponent(endHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(endMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(endMinuteLabel))
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
								.addComponent(alarmHourLabel)
								.addComponent(alarmHourSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(alarmMinuteLabel)
								.addComponent(alarmMinuteSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(removeAlarmButton))))
					.addGap(30)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(cancelMeetingButton)
						.addComponent(declineMeetingButton)
						.addComponent(saveChangesButton)
						.addComponent(cancelChangesButton))
					.addGap(407))
		);
		
		myAppointmentsList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				
//				if(JEG ER LEDER AV AVTALEN)
//					setAllFieldsEnabled(true);
//				else
//					setAlarmFieldsEnabled(true);
				setButtonsEnabled(true);
				
			}
		});
		scrollPane.setViewportView(myAppointmentsList);
		
		participantsList = new JList();
		participantsList.setBackground(UIManager.getColor("Panel.background"));
		participantsList.setEnabled(false);
		scrollPane_1.setViewportView(participantsList);
		setLayout(groupLayout);
		
		restrictSpinners();
		
		
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
		
//		//Enable "Meld avbud" eller "Avlys avtale" avhengig av om man er leder eller ikke
//		if(JEG IKKE ER LEDER AV M�TET){
//			declineMeetingButton.setEnabled(true);
//			cancelMeetingButton.setEnabled(false);
//		}
//		else{
//			declineMeetingButton.setEnabled(false);
//			cancelMeetingButton.setEnabled(true);
//		}
		
//		if(AVTALEN HAS ALARM)
			removeAlarmButton.setEnabled(b);
	}
	private void restrictSpinners(){
		startHourSpinner.setModel(new SpinnerNumberModel(0, 0, 24, 1));
		endHourSpinner.setModel(new SpinnerNumberModel(0, 0, 24, 1));
		alarmHourSpinner.setModel(new SpinnerNumberModel(0, 0, 24, 1));
		
		startMinuteSpinner.setModel(new SpinnerNumberModel(0, 0, 60, 1));
		endMinuteSpinner.setModel(new SpinnerNumberModel(0, 0, 60, 1));
		alarmMinuteSpinner.setModel(new SpinnerNumberModel(0, 0, 60, 1));
		
		dateDaySpinner.setModel(new SpinnerNumberModel(0, 0, 31, 1));
		alarmDaySpinner.setModel(new SpinnerNumberModel(0, 0, 31, 1));
		
		dateMonthSpinner.setModel(new SpinnerNumberModel(0, 0, 12, 1));
		alarmMonthSpinner.setModel(new SpinnerNumberModel(0, 0, 12, 1));
		
		dateYearSpinner.setModel(new SpinnerNumberModel(jodaTime.getYear(), jodaTime.getYear(), jodaTime.getYear()+20, 1));
		alarmYearSpinner.setModel(new SpinnerNumberModel(jodaTime.getYear(), jodaTime.getYear(), jodaTime.getYear()+20, 1));
		
	}
}
