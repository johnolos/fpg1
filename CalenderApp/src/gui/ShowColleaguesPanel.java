package gui;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.Collator;
import java.util.List;
import javax.swing.UIManager;

public class ShowColleaguesPanel extends JPanel {
	
	
	private JTextField searchField;
	
	private JFrame frame;
	
	JList searchResultColleaguesList;
	JList shownColleaguesList;
	
	DefaultListModel<Object> searchResultListModel = new DefaultListModel<Object>();
	DefaultListModel<Object> shownListModel = new DefaultListModel<Object>();
	

	/**
	 * Create the panel.
	 */
	public ShowColleaguesPanel() {
		
		frame = new JFrame();
		frame.setTitle("Vis kollegaer");
		frame.setSize(610, 448);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setContentPane(this);
		
		JLabel showColleaguesLabel = new JLabel("Vis kollegaer");
		showColleaguesLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		searchField = new JTextField();
		searchField.setColumns(10);
		
		JLabel searchLabel = new JLabel("S\u00F8k:");
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton showButton = new JButton("Vis -->");
		showButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(searchResultColleaguesList.getSelectedIndex() != -1 && searchResultColleaguesList.getSelectedValue() != null){
					List<Object> selectedList = searchResultColleaguesList.getSelectedValuesList();
					int[] selectedIndeces = searchResultColleaguesList.getSelectedIndices();
					for(int i=0; i<selectedList.size(); i++){
						shownListModel.addElement(selectedList.get(i));
						
					}
					for(int i=0; i<selectedIndeces.length; i++){
						searchResultListModel.removeElementAt(selectedIndeces[i]);
						for(int k=0; k<selectedIndeces.length; k++){
							selectedIndeces[k] = selectedIndeces[k]-1;
						}
					}
					
				}
				
			}
		});
		
		JButton removeButton = new JButton("<-- Fjern");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				if(shownColleaguesList.getSelectedIndex() != -1 && shownColleaguesList.getSelectedValue() != null){
					
					List<Object> selectedList = shownColleaguesList.getSelectedValuesList();
					int[] selectedIndeces = shownColleaguesList.getSelectedIndices();
					for(int i=0; i<selectedList.size(); i++){
						searchResultListModel.addElement(selectedList.get(i));
						
					}
					
					for(int i=0; i<selectedIndeces.length; i++){
						shownListModel.removeElementAt(selectedIndeces[i]);
						for(int k=0; k<selectedIndeces.length; k++){
							selectedIndeces[k] = selectedIndeces[k]-1;
						}
					}
					
					// sorterer den
				     int numItems = searchResultListModel.getSize();
				     String[] a = new String[numItems];
				     for (int i=0;i<numItems;i++){
				       a[i] = (String)searchResultListModel.getElementAt(i);
				       }
				     sortArray(Collator.getInstance(),a);
				     for (int i=0;i<numItems;i++) {
				       searchResultListModel.setElementAt(a[i], i);
				     }
				     //------------
				}
				
			}
		});
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JButton saveButton = new JButton("Lagre");
		
		JButton cancelButton = new JButton("Avbryt");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				frame.dispose();
				
			}
		});
		
		JLabel descriptionLabel = new JLabel("Vis valgte kollegaer sine avtaler i din kalender");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(248)
							.addComponent(showColleaguesLabel))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(13)
							.addComponent(searchLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(searchField)
								.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(showButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(removeButton, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)))
					.addGap(64))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(226)
					.addComponent(saveButton)
					.addGap(38)
					.addComponent(cancelButton)
					.addContainerGap(237, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(193)
					.addComponent(descriptionLabel)
					.addContainerGap(201, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(25)
					.addComponent(showColleaguesLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(descriptionLabel)
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(searchLabel))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(scrollPane_1, Alignment.LEADING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGap(11)
							.addComponent(showButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(removeButton))
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(cancelButton)
						.addComponent(saveButton))
					.addGap(42))
		);
		
		shownColleaguesList = new JList();
		shownColleaguesList.setModel(shownListModel);
		scrollPane_1.setViewportView(shownColleaguesList);
		
		searchResultColleaguesList = new JList();
		searchResultColleaguesList.setModel(searchResultListModel);
		searchResultListModel.addElement("Bjørn Christian Torp Olsen");
		searchResultListModel.addElement("b");
		searchResultListModel.addElement("c");
		searchResultListModel.addElement("d");
		searchResultListModel.addElement("e");
		searchResultListModel.addElement("f");
		scrollPane.setViewportView(searchResultColleaguesList);
		setLayout(groupLayout);

	}
	
	public static void sortArray(Collator collator, String[] strArray) {
		   String tmp;
		   if (strArray.length == 1) return;
		   for (int i = 0; i < strArray.length; i++) {
		    for (int j = i + 1; j < strArray.length; j++) {
		      if( collator.compare(strArray[i], strArray[j] ) > 0 ) {
		        tmp = strArray[i];
		        strArray[i] = strArray[j];
		        strArray[j] = tmp;
		        }
		      }
		    } 
		   }
}
