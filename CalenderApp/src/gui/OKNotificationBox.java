package src.gui;
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
import javax.swing.UIManager;


public class OKNotificationBox extends JPanel {

	/**
	 * Create the panel.
	 */
	public OKNotificationBox() {
		
		JButton btnOk = new JButton("OK");
		
		JLabel lblTitleHere = new JLabel(">>TITLE HERE<<");
		lblTitleHere.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		JLabel lblSdfdf = new JLabel((String) null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBackground(UIManager.getColor("Panel.background"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(79)
							.addComponent(lblSdfdf))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(87)
							.addComponent(lblTitleHere)))
					.addContainerGap(108, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(145, Short.MAX_VALUE)
					.addComponent(btnOk)
					.addGap(142))
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(49)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(48, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(24)
					.addComponent(lblTitleHere)
					.addGap(28)
					.addComponent(lblSdfdf)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnOk)
					.addGap(28))
		);
		setLayout(groupLayout);

	}
}
