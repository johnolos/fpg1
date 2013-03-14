package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import baseClasses.Person;
import baseClasses.Room;

class ComboBoxRenderer extends JLabel
                       implements ListCellRenderer {
    
	JLabel label;
	
    public ComboBoxRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }
    @Override
	public Component getListCellRendererComponent(JList arg0, Object arg1,
			int arg2, boolean arg3, boolean arg4) {
		if(!(arg1 instanceof String) && arg1 != null){
			Room r = (Room) arg1;
	
//	        if(r != null){
		        label = new JLabel(r.getName() + " ("+r.getCapacity()+")");
		        if (arg3)
		            label.setForeground(Color.BLUE);
//	        }
        }
		else
			label = new JLabel("Ingen ledige rom");
        return label;
	}
    

	
}