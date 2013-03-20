package gui;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;

import ktn.Client;

import baseClasses.Appointment;
import baseClasses.Notification;


public class ParticipantDeclinedPanel extends JPanel implements ActionListener {
	JLabel lblDeltagerAvsltt;
	
	private JTextField declinedMeetingField;
	private JTextField timeOfMeetingField;
	private JTextField participantField;

	Client client;
	Notification notification;
	
	JButton btnX;
	JButton changeButton;
	JButton removeParticipantButton;
	JButton cancelButton;
	/**
	 * Create the panel.
	 */
	public ParticipantDeclinedPanel(Client clientz) {
		
		this.client = clientz;
		lblDeltagerAvsltt = new JLabel("Deltager avsl\u00E5tt");
		lblDeltagerAvsltt.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		changeButton = new JButton("Endre m\u00F8tedetaljer");
		changeButton.addActionListener(this);
		
		removeParticipantButton = new JButton("Fjern deltager");
		removeParticipantButton.addActionListener(this);
		
		cancelButton = new JButton("Avlys m\u00F8te");
		cancelButton.addActionListener(this);
		
		JLabel titleLabel = new JLabel("Avsl\u00E5tt m\u00F8te:");
		
		JLabel timeLabel = new JLabel("M\u00F8tetidspunkt:");
		
		JLabel participantLabel = new JLabel("Deltager:");
		
		declinedMeetingField = new JTextField();
		declinedMeetingField.setColumns(10);
		declinedMeetingField.setEnabled(false);
		
		timeOfMeetingField = new JTextField();
		timeOfMeetingField.setColumns(10);
		timeOfMeetingField.setEnabled(false);
		
		participantField = new JTextField();
		participantField.setColumns(10);
		participantField.setEnabled(false);
		
		btnX = new JButton("X");
		btnX.addActionListener(this);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(127)
							.addComponent(lblDeltagerAvsltt))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(32)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(changeButton)
									.addGap(18)
									.addComponent(removeParticipantButton)
									.addGap(18)
									.addComponent(cancelButton))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(participantLabel)
										.addComponent(timeLabel)
										.addComponent(titleLabel))
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(participantField, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
										.addComponent(timeOfMeetingField)
										.addComponent(declinedMeetingField))))))
					.addContainerGap(20, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(310, Short.MAX_VALUE)
					.addComponent(btnX))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(btnX)
					.addGap(7)
					.addComponent(lblDeltagerAvsltt)
					.addGap(35)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(titleLabel)
						.addComponent(declinedMeetingField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(timeLabel)
						.addComponent(timeOfMeetingField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(participantLabel)
						.addComponent(participantField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(changeButton)
						.addComponent(removeParticipantButton)
						.addComponent(cancelButton))
					.addGap(24))
		);
		setLayout(groupLayout);

	}
	
	public void setTitle(String title){
		lblDeltagerAvsltt.setText(title);
	}
	
	public void setDeclinedMeeting(String meeting){
		declinedMeetingField.setText(meeting);
	}
	
	public void setTimeOfMeeting(String time){
		timeOfMeetingField.setText(time);
	}
	
	public void setParticipant(String partic){
		participantField.setText(partic);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(changeButton)){
			Appointment app = notification.getAppointment();
        	//Skal åpne Mine Avtaler med denne avtalen valgt
        	MyAppointmentsPanel myAppointmentPanel = new MyAppointmentsPanel(HomeGUI.getClient(), HomeGUI.getCurrentUser(), app);
			client.deleteNotification(notification);
		}
		else if(e.getSource().equals(removeParticipantButton)){
			HomeGUI.getClient().sendDeletePerson(participantField.getText(), notification.getAppointment());
			client.deleteNotification(notification);
		}
		else if(e.getSource().equals(cancelButton)){
			HomeGUI.getClient().deleteAppointment(notification.getAppointment());
			client.deleteNotification(notification);
		}
		HomeGUI.list.clearSelection();
		HomeGUI.layeredPane.moveToBack(this);
		HomeGUI.getNotificationUpdate().update();
	}
	
	public void setNotification(Notification n){
		this.notification = n;
	}
}
