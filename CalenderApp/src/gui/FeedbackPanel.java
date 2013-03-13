package gui;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;

public class FeedbackPanel extends JPanel {
	
	public FeedbackPanel() {
		
		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setFont(new Font("Tahoma", Font.PLAIN, 17));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(52)
					.addComponent(lblMessage)
					.addContainerGap(318, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(95)
					.addComponent(lblMessage)
					.addContainerGap(183, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}
}
