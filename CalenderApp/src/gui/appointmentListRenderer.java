package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import baseClasses.Appointment;
import baseClasses.Person;

public class appointmentListRenderer implements ListCellRenderer {
	
	@Override
	public Component getListCellRendererComponent(JList arg0, Object arg1,
			int arg2, boolean arg3, boolean arg4) {
		
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
        
        label = new JLabel(formatNumber(ap.getStart().getDayOfMonth())+"/"+formatNumber(ap.getStart().getMonthOfYear())+"/"+formatNumber(ap.getStart().getYear()) +" "+ formatNumber(ap.getStart().getHourOfDay())+":"+formatNumber(ap.getStart().getMinuteOfHour()) + " - " + formatNumber(ap.getEnd().getHourOfDay())+":"+formatNumber(ap.getEnd().getMinuteOfHour()) + ": " + ap.getTitle());
        if (arg3) {
            label.setForeground(Color.BLUE);
        }
        
        return label;
	}

	private String formatNumber(int number){
		if(number < 9){
        	return "0"+number;
        }
        else
        	return ""+number;
        
	}
	
	
}
