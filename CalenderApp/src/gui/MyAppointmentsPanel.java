package gui;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JComboBox;


public class MyAppointmentsPanel extends JPanel {
	private JTextField titleField;
	private JTextField dateField;
	private JTextField startField;
	private JTextField endField;
	private JTextField locationField;
	

	private JFrame frame;
	
	/**
	 * Create the panel.
	 */
	public MyAppointmentsPanel() {
		
		// ----------------------------------------------------------------//
		// Frame
		// ----------------------------------------------------------------//

		frame = new JFrame();
		frame.setTitle("Mine avtaler");
		frame.setSize(750, 630);
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
		
		dateField = new JTextField();
		dateField.setEnabled(false);
		dateField.setColumns(10);
		
		startField = new JTextField();
		startField.setEnabled(false);
		startField.setColumns(10);
		
		endField = new JTextField();
		endField.setEnabled(false);
		endField.setColumns(10);
		
		locationField = new JTextField();
		locationField.setEnabled(false);
		locationField.setColumns(10);
		
		JTextPane descriptionArea = new JTextPane();
		descriptionArea.setEditable(false);
		
		JList meetingsList = new JList();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JComboBox roomComboBox = new JComboBox();
		roomComboBox.setEnabled(false);
		
		JButton editParticipantsButton = new JButton("Edit");
		editParticipantsButton.setEnabled(false);
		
		JButton saveChangesButton = new JButton("Lagre endringer");
		saveChangesButton.setEnabled(false);
		
		JButton cancelChangesButton = new JButton("Avbryt");
		cancelChangesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				frame.dispose();
				
			}
		});
		
		JButton sauNoToMeetingButton = new JButton("Meld avbud");
		sauNoToMeetingButton.setEnabled(false);
		
		JButton cancelMeetingButton = new JButton("Avlys m\u00F8te");
		cancelMeetingButton.setEnabled(false);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addContainerGap()
									.addComponent(meetingsList, GroupLayout.PREFERRED_SIZE, 323, GroupLayout.PREFERRED_SIZE)
									.addGap(48)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(descriptionLabel)
										.addComponent(roomLabel)
										.addComponent(locationLabel)
										.addComponent(endLabel)
										.addComponent(startLabel)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(titleLabel)
											.addComponent(dateLabel))
										.addComponent(participantsLabel)))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(59)
									.addComponent(sauNoToMeetingButton)
									.addGap(28)
									.addComponent(cancelMeetingButton)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(roomComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(locationField)
										.addComponent(titleField)
										.addComponent(dateField)
										.addComponent(startField)
										.addComponent(endField, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
										.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
										.addComponent(descriptionArea))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(editParticipantsButton))
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(10)
									.addComponent(saveChangesButton)
									.addGap(18)
									.addComponent(cancelChangesButton))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(130)
							.addComponent(lblMineAvtaler)))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(11, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(24)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(titleLabel)
								.addComponent(titleField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(dateLabel)
								.addComponent(dateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(startLabel)
								.addComponent(startField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(endLabel)
								.addComponent(endField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(locationLabel)
								.addComponent(locationField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(roomLabel)
								.addComponent(roomComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(descriptionLabel)
								.addComponent(descriptionArea, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
									.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
									.addComponent(editParticipantsButton))
								.addComponent(participantsLabel)))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblMineAvtaler)
							.addGap(18)
							.addComponent(meetingsList, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGap(29)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(saveChangesButton)
						.addComponent(cancelChangesButton)
						.addComponent(sauNoToMeetingButton)
						.addComponent(cancelMeetingButton))
					.addGap(459))
		);
		
		JList participantsList = new JList();
		participantsList.setEnabled(false);
		scrollPane_1.setViewportView(participantsList);
		setLayout(groupLayout);
		
	}
}
