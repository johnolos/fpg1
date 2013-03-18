package gui;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;

import baseClasses.Appointment;

class CalendarCellEditor extends AbstractCellEditor implements TableCellEditor {
	
	private ListSelectionListener listener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent evt) {
        	if(evt.getValueIsAdjusting()){
	        	if(!first){
		        	Appointment app = (Appointment)list.getSelectedValue();
		        	//Skal åpne Mine Avtaler med denne avtalen valgt
		        	MyAppointmentsPanel myAppointmentPanel = new MyAppointmentsPanel(HomeGUI.getClient(), HomeGUI.getCurrentUser(), app);
		        	System.out.println("Tittelen er: " + app.getTitle());
	        	} else {
	        		list.clearSelection();
	        		first = false;
	        	}
        	}
        }
    };
    JList<Object> list;
	boolean first = true;
	Object value;
    public Component getTableCellEditorComponent(JTable table,
            Object value, boolean isSelected, int row, int column) {
    	
    	this.value = value;
    	DefaultListModel<Object> model = new DefaultListModel<>();
    	
    	ArrayList<Appointment> arrayList;
    	
        if (value != "") {
        	arrayList = (ArrayList<Appointment>)value;
            list = new JList<Object>();
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            for(int i=0; i<arrayList.size(); i++){
            	model.addElement(arrayList.get(i));
            }
            list.setModel(model);
            list.setVisibleRowCount(4);
            list.setCellRenderer(new CalendarListRenderer());
            list.addListSelectionListener(listener);
            JScrollPane pane = new JScrollPane(list);
            return pane;
        } else {
            // TODO return whatever you need
            return null;
        }
    }

    public Object getCellEditorValue() {
        // TODO return whatever you need
        return value;
    }

}