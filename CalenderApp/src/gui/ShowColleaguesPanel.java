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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.Collator;
import java.util.ArrayList;
import java.util.List;
import javax.swing.UIManager;

import ktn.Client;

import baseClasses.Appointment;
import baseClasses.Person;

public class ShowColleaguesPanel extends JPanel {

	private JTextField searchField;

	private JFrame frame;

	JList<Person> searchResultColleaguesList;
	JList<Person> shownColleaguesList;

	DefaultListModel<Person> searchResultListModel = new DefaultListModel<Person>();
	DefaultListModel<Person> shownListModel = new DefaultListModel<Person>();

	private Client client;
	/**
	 * Create the panel.
	 */
	public ShowColleaguesPanel(Client clientz) {
		this.client = clientz;

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
				// TODO Auto-generated method stub
				
			}
		});

		JLabel searchLabel = new JLabel("S\u00F8k:");

		JScrollPane scrollPane = new JScrollPane();

		JButton showButton = new JButton("Vis -->");
		showButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (searchResultColleaguesList.getSelectedIndex() != -1
						&& searchResultColleaguesList.getSelectedValue() != null) {
					List<Person> selectedList = searchResultColleaguesList
							.getSelectedValuesList();
					int[] selectedIndeces = searchResultColleaguesList
							.getSelectedIndices();
					for (int i = 0; i < selectedList.size(); i++) {
						shownListModel.addElement(selectedList.get(i));

					}
					for (int i = 0; i < selectedIndeces.length; i++) {
						searchResultListModel
								.removeElementAt(selectedIndeces[i]);
						for (int k = 0; k < selectedIndeces.length; k++) {
							selectedIndeces[k] = selectedIndeces[k] - 1;
						}
					}

				}

			}
		});

		JButton removeButton = new JButton("<-- Fjern");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (shownColleaguesList.getSelectedIndex() != -1
						&& shownColleaguesList.getSelectedValue() != null) {

					List<Person> selectedList = shownColleaguesList
							.getSelectedValuesList();
					int[] selectedIndeces = shownColleaguesList
							.getSelectedIndices();
					for (int i = 0; i < selectedList.size(); i++) {
						searchResultListModel.addElement(selectedList.get(i));

					}

					for (int i = 0; i < selectedIndeces.length; i++) {
						shownListModel.removeElementAt(selectedIndeces[i]);
						for (int k = 0; k < selectedIndeces.length; k++) {
							selectedIndeces[k] = selectedIndeces[k] - 1;
						}
					}

				}

			}

		});

		JScrollPane scrollPane_1 = new JScrollPane();

		JButton saveButton = new JButton("Lagre");
		saveButton.addActionListener(new ActionListener(){
			
				@Override
				public void actionPerformed(ActionEvent arg0) {
					for(int l=0; l<shownListModel.getSize(); l++){
						HomeGUI.showAllThese.add(shownListModel.get(l));
						System.out.println("ADDED TO SHOWALLTHESE");
//						ArrayList<Appointment> allMyAppointments = client.fetchAllAppointments(shownListModel.get(l).getUsername());
//						int row;
//						int column;
//						Appointment app;
//						if(allMyAppointments != null){
//							for(int i=0; i<allMyAppointments.size(); i++){
//								app = allMyAppointments.get(i);
//								if(app.getStart().isBefore(HomeGUI.getEndOfWeek()) && app.getStart().isAfter(HomeGUI.getStartOfWeek())){
//									column = app.getStart().getDayOfWeek();
//									row = app.getStart().getHourOfDay();
//									/*
//									 * Sjekk gjennom lista med avtaler (allMyAppointments)
//									 * Hvis avtalens start-dato er mellom startDay og endDay:
//									 * Break hvis start-dato er forbi endDay
//									 * column = appointment.getDayOfWeek()
//									 * row = start-time
//									 * table.setValueAt(row, column);
//									 * */
//									ArrayList<Appointment> appList = new ArrayList<Appointment>();
//									ArrayList<Appointment> currentlyInWantedCell = null;
//									Object listBeforeConverted = HomeGUI.table.getValueAt(row, column);
//									if(listBeforeConverted != null) {
//										if(listBeforeConverted instanceof ArrayList){
//											currentlyInWantedCell = (ArrayList)listBeforeConverted;
//									
//										for(int j=0; j<currentlyInWantedCell.size(); j++){
//											if(app.getStart().getWeekOfWeekyear() == currentlyInWantedCell.get(j).getStart().getWeekOfWeekyear())
//												appList.add(currentlyInWantedCell.get(j));
//											else
//												currentlyInWantedCell.clear();
//										}
//										}
//									}
//									appList.add(app); //Denne legger den til i lista...
//									HomeGUI.table.setValueAt(appList, row, column);
//								}
//								else if(app.getStart().isAfter(HomeGUI.getEndOfWeek()))
//									break;
//							}
//						}
//					
						HomeGUI.insertAppointmentsIntoTable();
						frame.dispose();
				}
			}
		});
		
		JButton cancelButton = new JButton("Avbryt");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				frame.dispose();

			}
		});

		JLabel descriptionLabel = new JLabel(
				"Vis valgte kollegaer sine avtaler i din kalender");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(248)
																		.addComponent(
																				showColleaguesLabel))
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGap(13)
																		.addComponent(
																				searchLabel)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(
																								searchField)
																						.addComponent(
																								scrollPane,
																								GroupLayout.DEFAULT_SIZE,
																								202,
																								Short.MAX_VALUE))
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.TRAILING)
																						.addComponent(
																								showButton,
																								GroupLayout.DEFAULT_SIZE,
																								GroupLayout.DEFAULT_SIZE,
																								Short.MAX_VALUE)
																						.addComponent(
																								removeButton,
																								GroupLayout.DEFAULT_SIZE,
																								79,
																								Short.MAX_VALUE))
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				scrollPane_1,
																				GroupLayout.PREFERRED_SIZE,
																				196,
																				GroupLayout.PREFERRED_SIZE)))
										.addGap(64))
						.addGroup(
								groupLayout.createSequentialGroup().addGap(226)
										.addComponent(saveButton).addGap(38)
										.addComponent(cancelButton)
										.addContainerGap(237, Short.MAX_VALUE))
						.addGroup(
								groupLayout.createSequentialGroup().addGap(193)
										.addComponent(descriptionLabel)
										.addContainerGap(201, Short.MAX_VALUE)));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGap(25)
										.addComponent(showColleaguesLabel)
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(descriptionLabel)
										.addGap(26)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																searchField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																searchLabel))
										.addGap(18)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING,
																false)
														.addComponent(
																scrollPane_1,
																Alignment.LEADING)
														.addGroup(
																Alignment.LEADING,
																groupLayout
																		.createSequentialGroup()
																		.addGap(11)
																		.addComponent(
																				showButton)
																		.addPreferredGap(
																				ComponentPlacement.UNRELATED)
																		.addComponent(
																				removeButton))
														.addComponent(
																scrollPane,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																210,
																Short.MAX_VALUE))
										.addPreferredGap(
												ComponentPlacement.RELATED, 39,
												Short.MAX_VALUE)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																cancelButton)
														.addComponent(
																saveButton))
										.addGap(42)));

		shownColleaguesList = new JList();
		shownColleaguesList.setModel(shownListModel);
		shownColleaguesList.setCellRenderer(new PersonListRenderer());
		scrollPane_1.setViewportView(shownColleaguesList);


		searchResultColleaguesList = new JList();
		searchResultColleaguesList.setModel(searchResultListModel);
		searchResultColleaguesList.setCellRenderer(new PersonListRenderer());
		scrollPane.setViewportView(searchResultColleaguesList);
		
		updatePersons();
		setLayout(groupLayout);

	}

	public static void sortArray(Collator collator, String[] strArray) {
		String tmp;
		if (strArray.length == 1)
			return;
		for (int i = 0; i < strArray.length; i++) {
			for (int j = i + 1; j < strArray.length; j++) {
				if (collator.compare(strArray[i], strArray[j]) > 0) {
					tmp = strArray[i];
					strArray[i] = strArray[j];
					strArray[j] = tmp;
				}
			}
		}
	}
	
	private void updatePersons() {
		searchResultListModel.removeAllElements();
		ArrayList<Person> persons = client.fetchPersons(searchField.getText());
		for(int i = 0; i < persons.size(); i++) {
			if(!persons.get(i).getUsername().equals(HomeGUI.getCurrentUser().getUsername()))
				searchResultListModel.addElement(persons.get(i));
		}
	}
}
