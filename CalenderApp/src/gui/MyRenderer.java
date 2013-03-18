package gui;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

class MyRenderer implements TableCellRenderer  
{  
    JTextArea textArea;  
   
    public MyRenderer()  
    {  
        textArea = new JTextArea();
    }  
   
    public Component getTableCellRendererComponent(JTable table,  
                                                   Object value,  
                                                   boolean isSelected,  
                                                   boolean hasFocus,  
                                                   int row, int column)  
    {  
        textArea.setText((String)value);  
        return textArea;
    }
}