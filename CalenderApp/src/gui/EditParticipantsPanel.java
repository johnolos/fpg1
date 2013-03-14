package gui;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.ListModel;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.JButton;
import javax.tools.JavaFileManager.Location;
import javax.swing.AbstractListModel;
import javax.swing.ScrollPaneConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EditParticipantsPanel extends JPanel {
	private JTextField searchField;

	JList searchResultParticipantsList;
	JList chosenParticipantsList;
	
	DefaultListModel<Object> searchResultListModel = new DefaultListModel<Object>();
	DefaultListModel<Object> chosenListModel = new DefaultListModel<Object>();
	
	private JFrame frame;
	
	/**
	 * Create the panel.
	 */
	public EditParticipantsPanel(JFrame utgangspunkt) {
		
		frame = new JFrame();
		frame.setTitle("Endre deltagere");
		frame.setSize(611, 449);
		frame.setLocationRelativeTo(utgangspunkt);
		frame.setVisible(true);
		frame.setContentPane(this);
		
		
		JLabel editParticipantsLabel = new JLabel("Endre deltagere");
		editParticipantsLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		JLabel searchLabel = new JLabel("S\u00F8k:");
		
		searchField = new JTextField();
		searchField.setColumns(10);
		
		JScrollPane searchResultParticipantsScrollPane = new JScrollPane();
		
		JButton saveButton = new JButton("Lagre");
		
		JScrollPane chosenParticipantsScrollPane = new JScrollPane();
		
		JButton addParticipantButton = new JButton("Legg til -->");
		addParticipantButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(searchResultParticipantsList.getSelectedIndex() != -1 && searchResultParticipantsList.getSelectedValue() != null){
					List<Object> selectedList = searchResultParticipantsList.getSelectedValuesList();
					int[] selectedIndeces = searchResultParticipantsList.getSelectedIndices();
					for(int i=0; i<selectedList.size(); i++){
						chosenListModel.addElement(selectedList.get(i));
						
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
		
		JButton removeParticipantButton = new JButton("<-- Fjern");
		removeParticipantButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				if(chosenParticipantsList.getSelectedIndex() != -1 && chosenParticipantsList.getSelectedValue() != null){
					
					List<Object> selectedList = chosenParticipantsList.getSelectedValuesList();
					int[] selectedIndeces = chosenParticipantsList.getSelectedIndices();
					for(int i=0; i<selectedList.size(); i++){
						searchResultListModel.addElement(selectedList.get(i));
						
					}
					
					for(int i=0; i<selectedIndeces.length; i++){
						chosenListModel.removeElementAt(selectedIndeces[i]);
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
		
		JButton cancelButton = new JButton("Avbryt");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(searchLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(searchField, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
						.addComponent(searchResultParticipantsScrollPane, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addComponent(addParticipantButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(removeParticipantButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(18)
					.addComponent(chosenParticipantsScrollPane, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
					.addGap(38))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(241, Short.MAX_VALUE)
					.addComponent(editParticipantsLabel)
					.addGap(236))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(221)
					.addComponent(saveButton)
					.addGap(28)
					.addComponent(cancelButton)
					.addContainerGap(222, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(22)
					.addComponent(editParticipantsLabel)
					.addGap(34)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(searchLabel)
						.addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(30)
							.addComponent(addParticipantButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(removeParticipantButton))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(chosenParticipantsScrollPane, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
								.addComponent(searchResultParticipantsScrollPane, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE))))
					.addPreferredGap(ComponentPlacement.RELATED, 18, GroupLayout.PREFERRED_SIZE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(saveButton)
						.addComponent(cancelButton))
					.addGap(62))
		);
		
		chosenParticipantsList = new JList();
		chosenParticipantsList.setModel(chosenListModel);
		chosenParticipantsScrollPane.setViewportView(chosenParticipantsList);
		
		searchResultParticipantsList = new JList();
		searchResultParticipantsList.setModel(searchResultListModel);
		searchResultListModel.addElement("Bjørn Christian Torp Olsen");
		searchResultListModel.addElement("b");
		searchResultListModel.addElement("c");
		searchResultListModel.addElement("d");
		searchResultListModel.addElement("e");
		searchResultListModel.addElement("f");		
		searchResultParticipantsScrollPane.setViewportView(searchResultParticipantsList);
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
