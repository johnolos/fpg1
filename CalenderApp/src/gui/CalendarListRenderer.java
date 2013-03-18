package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import baseClasses.Appointment;
import baseClasses.Person;

public class CalendarListRenderer implements ListCellRenderer {
	
	@Override
	public Component getListCellRendererComponent(JList arg0, Object arg1,
			int arg2, boolean arg3, boolean arg4) {
		if(arg1 instanceof Appointment){
				Appointment ap = (Appointment) arg1;
		        JLabel label;
		
		        String startMinuteText = "";
		        int startMinute = ap.getStart().getMinuteOfHour();
		        if(startMinute < 9){
		        	startMinuteText = "0"+startMinute;
		        }
		        else
		        	startMinuteText = ""+startMinute;
		        
		        String startHourText = "";
		        int startHour = ap.getStart().getHourOfDay();
		        if(startHour < 9){
		        	startHourText = "0"+startHour;
		        }
		        else
		        	startHourText = ""+startHour;
		        String user = "";
		        if(!(ap.getAdmin().equals(HomeGUI.getCurrentUser().getUsername())))
		        	user = ap.getAdmin();
		        label = new JLabel(user + ap.getTitle());
		        if (arg3) {
		            label.setForeground(Color.BLUE);
		        }
		        return label;
			} else
				return new JLabel();
		
	}

	private String formatNumber(int number){
		if(number < 9){
        	return "0"+number;
        }
        else
        	return ""+number;
        
	}
	
	
}
