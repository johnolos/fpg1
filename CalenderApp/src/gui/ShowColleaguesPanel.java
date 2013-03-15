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

import baseClasses.Person;

public class ShowColleaguesPanel extends JPanel {

	private JTextField searchField;

	private JFrame frame;

	JList<Person> searchResultColleaguesList;
	JList<Person> shownColleaguesList;

	DefaultListModel<Person> searchResultListModel = new DefaultListModel<Person>();
	DefaultListModel<Person> shownListModel = new DefaultListModel<Person>();

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
				searchResultColleaguesList.setSelectionInterval(0, 0);

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

				shownColleaguesList.setSelectionInterval(0, 0);

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
		searchResultListModel.addElement(new Person("user1", "email1",
				"Torkjel", "Skjetne"));
		searchResultListModel.addElement(new Person("user2", "email2",
				"John-Olav", "Storvold"));
		searchResultListModel.addElement(new Person("user3", "email3",
				"Bj�rn Christan", "Olsen"));
		searchResultListModel.addElement(new Person("user4", "email4",
				"Hans-Olav", "Hessen"));
		searchResultColleaguesList.setSelectionInterval(0, 0);
		searchResultColleaguesList.setCellRenderer(new PersonListRenderer());
		scrollPane.setViewportView(searchResultColleaguesList);
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
}
