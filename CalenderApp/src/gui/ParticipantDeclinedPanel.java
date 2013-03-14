package src.gui;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;


public class ParticipantDeclinedPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Create the panel.
	 */
	public ParticipantDeclinedPanel() {
		
		JLabel lblDeltagerAvsltt = new JLabel("Deltager avsl\u00E5tt");
		lblDeltagerAvsltt.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		JButton changeButton = new JButton("Endre m\u00F8tedetaljer");
		
		JButton removeParticipantButton = new JButton("Fjern deltager");
		
		JButton cancelButton = new JButton("Avlys m\u00F8te");
		
		JLabel TitleLabel = new JLabel("Avsl\u00E5tt m\u00F8te:");
		
		JLabel timeLabel = new JLabel("M\u00F8tetidspunkt:");
		
		JLabel participantLabel = new JLabel("Deltager:");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
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
										.addComponent(TitleLabel))
									.addGap(18)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
										.addComponent(textField_2, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
										.addComponent(textField_1)
										.addComponent(textField))))))
					.addContainerGap(20, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(30)
					.addComponent(lblDeltagerAvsltt)
					.addGap(35)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(TitleLabel)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(timeLabel)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(participantLabel)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(changeButton)
						.addComponent(removeParticipantButton)
						.addComponent(cancelButton))
					.addGap(24))
		);
		setLayout(groupLayout);

	}

}
