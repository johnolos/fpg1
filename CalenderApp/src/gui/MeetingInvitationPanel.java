package gui;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

import baseClasses.Notification;


public class MeetingInvitationPanel extends JPanel implements ActionListener {
	private JLabel lblMteinnkallingFranavn;
	
	JTextArea descriptionArea;
	
	private JTextField titleField;
	private JTextField startField;
	private JTextField endField;
	private JTextField locationField;
	
	private Notification notification;
	
	private JButton acceptButton;
	private JButton declineButton;
	private JButton btnX;

	/**
	 * Create the panel.
	 */
	public MeetingInvitationPanel() {
		
		lblMteinnkallingFranavn = new JLabel("M\u00F8teinnkalling fra >>NAVN<<");
		lblMteinnkallingFranavn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		JLabel titleLabel = new JLabel("Tittel:");
		
		JLabel lblNewLabel = new JLabel("Start:");
		
		JLabel lblBeskrivelse = new JLabel("Beskrivelse:");
		
		JLabel lblNewLabel_1 = new JLabel("Sted:");
		
		JLabel lblSlutt = new JLabel("Slutt:");
		
		titleField = new JTextField();
		titleField.setColumns(10);
		
		startField = new JTextField();
		startField.setColumns(10);
		
		endField = new JTextField();
		endField.setColumns(10);
		
		locationField = new JTextField();
		locationField.setColumns(10);
		
		descriptionArea = new JTextArea();
		
		acceptButton = new JButton("Godta");
		acceptButton.addActionListener(this);
		
		declineButton = new JButton("Avsl\u00E5");
		declineButton.addActionListener(this);
		
		btnX = new JButton("X");
		btnX.addActionListener(this);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(35)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBeskrivelse, Alignment.TRAILING)
						.addComponent(lblNewLabel, Alignment.TRAILING)
						.addComponent(titleLabel, Alignment.TRAILING)
						.addComponent(lblNewLabel_1, Alignment.TRAILING)
						.addComponent(lblSlutt, Alignment.TRAILING))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(titleField)
						.addComponent(locationField)
						.addComponent(descriptionArea, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
						.addComponent(endField)
						.addComponent(startField))
					.addContainerGap(67, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(73, Short.MAX_VALUE)
					.addComponent(lblMteinnkallingFranavn)
					.addGap(48))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(89)
					.addComponent(acceptButton)
					.addGap(39)
					.addComponent(declineButton)
					.addContainerGap(100, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(259, Short.MAX_VALUE)
					.addComponent(btnX))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(btnX)
					.addGap(3)
					.addComponent(lblMteinnkallingFranavn)
					.addGap(36)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(titleLabel)
						.addComponent(titleField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(startField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(17)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSlutt)
						.addComponent(endField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(locationField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblBeskrivelse)
						.addComponent(descriptionArea, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(declineButton)
						.addComponent(acceptButton))
					.addContainerGap())
		);
		setLayout(groupLayout);

	}	
	
	public void setTitle(String title){
		lblMteinnkallingFranavn.setText(title);
	}
	
	public void setTitleOfMeeting(String title){
		titleField.setText(title);
	}
	
	public void setStart(String start){
		startField.setText(start);
	}
	
	public void setEnd(String end){
		endField.setText(end);
	}
	
	public void setLocation(String location){
		locationField.setText(location);
	}
	
	public void setDescription(String desc){
		descriptionArea.setText(desc);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource().equals(acceptButton)){
			HomeGUI.getClient().sendAccept(HomeGUI.getCurrentUser().getUsername(),notification.getAppointment());
			HomeGUI.getNotificationUpdate().update();
		}
		else if(e.getSource().equals(declineButton)){
			HomeGUI.getClient().sendDecline(HomeGUI.getCurrentUser().getUsername(), notification.getAppointment());
			HomeGUI.getNotificationUpdate().update();
		}
		
		HomeGUI.list.clearSelection();
		HomeGUI.layeredPane.moveToBack(this);
		
	}

	public void setNotification(Notification n) {
		this.notification = n;
	}
}
