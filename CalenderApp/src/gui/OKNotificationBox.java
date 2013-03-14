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
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class OKNotificationBox extends JPanel implements ActionListener{

	private JLabel titleLabel;
	JButton okButton;
	private JTextArea textArea;
	private JButton btnX;
	/**
	 * Create the panel.
	 */
	public OKNotificationBox() {
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		
		titleLabel = new JLabel(">>Title<<");
		titleLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		textArea = new JTextArea();
		textArea.setBackground(UIManager.getColor("Panel.background"));
		
		btnX = new JButton("X");
		btnX.addActionListener(this);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(145, Short.MAX_VALUE)
					.addComponent(okButton)
					.addGap(142))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(49)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(48, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(127)
					.addComponent(titleLabel)
					.addContainerGap(126, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(245, Short.MAX_VALUE)
					.addComponent(btnX))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(24)
							.addComponent(titleLabel))
						.addComponent(btnX))
					.addGap(34)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(okButton)
					.addGap(28))
		);
		setLayout(groupLayout);

	}
	
	public void setTitle(String title){
		this.titleLabel.setText(title);
	}
	
	public void setDescription(String description){
		this.textArea.setText(description);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnX)){
			HomeGUI.list.clearSelection();
			HomeGUI.layeredPane.moveToBack(this);
		}
		if(e.getSource().equals(okButton)){
			HomeGUI.list.clearSelection();
			HomeGUI.layeredPane.moveToBack(this);
		}
	}

}
