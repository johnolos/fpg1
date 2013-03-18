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
import java.awt.event.KeyListener;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
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

import baseClasses.Person;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import ktn.Client;

public class EditParticipantsPanel extends JPanel {
	private JTextField searchField;

	JList<Person> searchResultParticipantsList;
	JList<Person> chosenParticipantsList;
	
	DefaultListModel<Person> searchResultListModel = new DefaultListModel<Person>();
	DefaultListModel<Person> chosenListModel = new DefaultListModel<Person>();
	
	private Client client;
	
	private JFrame frame;
	
	/**
	 * Create the panel.
	 */
	public EditParticipantsPanel(JFrame utgangspunkt, DefaultListModel<Person> utgangspunktModel, final Client client) {
		
		this.client = client;
		
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
		searchField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyChar() == (KeyEvent.VK_ENTER)) {
					updatePersons();
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				/* THIS CODE FITS BETTER UNDER ENTER IS PRESSED
				 * BIT BUGGY ELSE. PROGRAM FREEZES.
				searchResultListModel.removeAllElements();
				ArrayList<Person> persons = client.fetchPersons(searchField.getText());
				for(int i = 0; i < persons.size(); i++) {
					searchResultListModel.addElement(persons.get(i));
				}
				*/
			}
			
		});
		
		JScrollPane searchResultParticipantsScrollPane = new JScrollPane();
		
		JButton saveButton = new JButton("Lagre");
		saveButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				CreateAppointmentPanel.model.removeAllElements();
				MyAppointmentsPanel.model.removeAllElements();
				for(int i=0; i<chosenListModel.getSize(); i++){
					CreateAppointmentPanel.model.addElement(chosenListModel.get(i));
					MyAppointmentsPanel.model.addElement(chosenListModel.get(i));
				}
				frame.dispose();
			}
			
		});
		
		JScrollPane chosenParticipantsScrollPane = new JScrollPane();
		
		JButton addParticipantButton = new JButton("Legg til -->");
		addParticipantButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(searchResultParticipantsList.getSelectedIndex() != -1 && searchResultParticipantsList.getSelectedValue() != null){
					List<Person> selectedList = searchResultParticipantsList.getSelectedValuesList();
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
					
					List<Person> selectedList = chosenParticipantsList.getSelectedValuesList();
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
					//Collections.sort(searchResultParticipantsList);
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
		
		chosenParticipantsList = new JList<Person>();
		chosenParticipantsList.setModel(chosenListModel);
		chosenParticipantsScrollPane.setViewportView(chosenParticipantsList);
		chosenParticipantsList.setCellRenderer(new PersonListRenderer());
		for(int i=0; i<utgangspunktModel.size(); i++){
			chosenListModel.addElement(utgangspunktModel.get(i));
		}
		
		searchResultParticipantsList = new JList<Person>();
		searchResultParticipantsList.setModel(searchResultListModel);
		updatePersons();
		searchResultParticipantsScrollPane.setViewportView(searchResultParticipantsList);
		searchResultParticipantsList.setCellRenderer(new PersonListRenderer());
		setLayout(groupLayout);
	}
	/**
	 * Update searchListPersons when initialized or pressed ENTER
	 */
	private void updatePersons() {
		searchResultListModel.removeAllElements();
		ArrayList<Person> persons = client.fetchPersons(searchField.getText());
		boolean add;
		for(int i = 0; i < persons.size(); i++) {
			add = true;
			for(int j=0; j<chosenListModel.getSize(); j++){
				if(persons.get(i).getUsername().equals(chosenListModel.get(j).getUsername()))
					add = false;
			}
			if(add)
				searchResultListModel.addElement(persons.get(i));
		}
	}
}
