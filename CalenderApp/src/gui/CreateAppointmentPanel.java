package gui;


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
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractListModel;
import javax.swing.ScrollPaneConstants;


public class CreateAppointmentPanel extends JPanel {
	
	JFrame frmOpprettAvtale;
	private JTextField titleField;
	private JTextField dateField;
	private JTextField startField;
	private JTextField endField;
	private JTextField locationField;
	
	
	CreateAppointmentPanel(){
		
		// ----------------------------------------------------------------//
		// Frame
		// ----------------------------------------------------------------//

		
		frmOpprettAvtale = new JFrame();
		frmOpprettAvtale.setTitle("Opprett Avtale");
		frmOpprettAvtale.setSize(402, 600);
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
		
		dateField = new JTextField();
		dateField.setColumns(10);
		
		startField = new JTextField();
		startField.setColumns(10);
		
		endField = new JTextField();
		endField.setColumns(10);
		
		locationField = new JTextField();
		locationField.setColumns(10);
		
		JComboBox roomBox = new JComboBox();
		
		JTextPane descreptionArea = new JTextPane();
		
		JButton cancelButton = new JButton("Avbryt");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmOpprettAvtale.dispose();
			}
		});
		
		JButton editParticipantsButton = new JButton("Edit");
		editParticipantsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				EditParticipantsPanel editParticipantsPanel = new EditParticipantsPanel(frmOpprettAvtale);
			
			}
		});
		
		DefaultListModel<Object> model = new DefaultListModel<>();
		model.addElement("asdads");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JButton saveButton = new JButton("Lagre");

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(59)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(dateLabel)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(dateField, 174, 174, 174))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(startLabel)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(startField, 174, 174, 174))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(endLabel)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(endField, 174, 174, 174))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(titleLabel)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(titleField, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(descriptionLabel)
										.addComponent(locationLabel)
										.addComponent(roomLabel)
										.addComponent(peopleLabel))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
										.addComponent(roomBox, 0, 175, Short.MAX_VALUE)
										.addComponent(locationField, 174, 175, Short.MAX_VALUE)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(saveButton)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(cancelButton)
											.addGap(29))
										.addComponent(descreptionArea, GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE))))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(editParticipantsButton, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(138)
							.addComponent(createAppointmentLabel)))
					.addGap(26))
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
						.addComponent(dateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(dateLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(startField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(startLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(endField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(endLabel))
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
						.addComponent(descreptionArea, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
		
		JList<Object> list = new JList();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(list);
		setLayout(groupLayout);
		
	}
}