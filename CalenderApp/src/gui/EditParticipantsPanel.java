package gui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Point;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JButton;
import javax.tools.JavaFileManager.Location;

public class EditParticipantsPanel extends JPanel {
	private JTextField searchField;

	private JFrame frame;
	
	/**
	 * Create the panel.
	 */
	public EditParticipantsPanel(JFrame utgangspunkt) {
		
		frame = new JFrame();
		frame.setTitle("Endre deltagere");
		frame.setSize(360, 380);
		frame.setLocationRelativeTo(utgangspunkt);
		frame.setVisible(true);
		frame.setContentPane(this);
		
		
		JLabel editParticipantsLabel = new JLabel("Endre deltagere");
		editParticipantsLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		JLabel searchLabel = new JLabel("S\u00F8k:");
		
		searchField = new JTextField();
		searchField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton saveButton = new JButton("Lagre");
		
		JButton cancelButton = new JButton("Avbryt");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(searchLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(searchField, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(102)
							.addComponent(editParticipantsLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(82)
							.addComponent(saveButton)
							.addGap(36)
							.addComponent(cancelButton)))
					.addContainerGap(91, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(24)
					.addComponent(editParticipantsLabel)
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(searchLabel)
						.addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
					.addGap(34)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(saveButton)
						.addComponent(cancelButton))
					.addContainerGap(249, Short.MAX_VALUE))
		);
		
		JList participantsList = new JList();
		scrollPane.setViewportView(participantsList);
		setLayout(groupLayout);

		
		
	}

}
