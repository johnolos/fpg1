package gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import baseClasses.Notification;

public class NotificationListRenderer implements ListCellRenderer {

	@Override
	public Component getListCellRendererComponent(JList arg0, Object arg1,
			int arg2, boolean arg3, boolean arg4) {
		
		Notification n = (Notification) arg1;
        JLabel label;

        label = new JLabel(n.getListMessage());
        System.out.println(n.getListMessage());
        if (arg3) {
            label.setForeground(Color.BLUE);
        }
        
        return label;
	}

}
