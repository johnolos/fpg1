package gui;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;


public class OKNotificationBox extends JPanel {

	/**
	 * Create the panel.
	 */
	public OKNotificationBox() {
		
		JButton btnOk = new JButton("OK");
		
		JLabel lblTitleHere = new JLabel(">>TITLE HERE<<");
		lblTitleHere.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		JLabel lblSdfdf = new JLabel((String) null);
		
		JTextPane textPane = new JTextPane();
		textPane.setBackground(SystemColor.control);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(79)
							.addComponent(lblSdfdf))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(143)
							.addComponent(btnOk))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(87)
							.addComponent(lblTitleHere)))
					.addContainerGap(124, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(23, Short.MAX_VALUE)
					.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)
					.addGap(19))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(24)
					.addComponent(lblTitleHere)
					.addGap(28)
					.addComponent(lblSdfdf)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
					.addGap(17)
					.addComponent(btnOk)
					.addGap(88))
		);
		setLayout(groupLayout);

	}
}
