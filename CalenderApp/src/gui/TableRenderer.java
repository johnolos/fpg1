package gui;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import baseClasses.Appointment;

public class TableRenderer extends DefaultTableCellRenderer implements TableCellRenderer {

	JList<Object> list;
	
	public TableRenderer(){
		list = new JList<>();
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable arg0, Object arg1,
			boolean arg2, boolean arg3, int arg4, int arg5) {
		DefaultListModel<Object> model = new DefaultListModel<Object>();
		list.setModel(model);
		list.setCellRenderer(new CalendarListRenderer());
		list.setBorder(BorderFactory.createRaisedBevelBorder());
		if(arg1 != ""){
		ArrayList<Appointment> arrayList = (ArrayList)arg1;
			for(int i=0; i<arrayList.size(); i++){
				model.addElement(arrayList.get(i));
			}
		}
		return list;
	}
}
