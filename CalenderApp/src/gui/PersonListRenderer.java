package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import baseClasses.Person;


public class PersonListRenderer implements ListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList arg0, Object arg1,
			int arg2, boolean arg3, boolean arg4) {
		
		Person p = (Person) arg1;
        JLabel label;
        label = new JLabel(p.getLastname() + " " + p.getFirstName());
        
        if(p.getAgreed() == 1){
        	label.setForeground(Color.GREEN);
        }
        else if(p.getAgreed() == -1){
        	label.setForeground(Color.RED);
        }
        
        if (arg3) {
            label.setForeground(Color.BLUE);
        }
        
        return label;
	}

}
