package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLayeredPane;

public class LayeredPaneTest extends JPanel {
	
	private JPanel headlinePanel;
	private JPanel weekPanel;
	private JPanel buttonPanel;
	private JPanel calendarPanel;
	private JPanel notificationPanel;
	
	public static void main(String[] args){
		LayeredPaneTest lpt = new LayeredPaneTest();
	}

	public LayeredPaneTest() {
//		
//		
//		JLayeredPane layeredPane = new JLayeredPane();
//		headlinePanel = new JPanel();
//		buttonPanel = new JPanel();
//		calendarPanel = new JPanel();
//		weekPanel = new JPanel();
//		notificationPanel = new JPanel();
//		layeredPane.add(headlinePanel, new Integer(2), 0);
//		layeredPane.add(buttonPanel, new Integer(2), 0);
//		layeredPane.add(calendarPanel, new Integer(2), 1);
//		layeredPane.add(weekPanel, new Integer(2), 0);
//		layeredPane.add(notificationPanel, new Integer(2), 0);
//		
//		
//		
//		GroupLayout groupLayout = new GroupLayout(this);
//		groupLayout.setHorizontalGroup(
//			groupLayout.createParallelGroup(Alignment.LEADING)
//				.addComponent(layeredPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
//		);
//		groupLayout.setVerticalGroup(
//			groupLayout.createParallelGroup(Alignment.LEADING)
//				.addComponent(layeredPane, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
//		);
//		setLayout(groupLayout);
//		

        // This Works as expected
        JFrame usingPanel = new JFrame();
        JPanel p = new JPanel();
        p.add(new BluePanel());
        usingPanel.setContentPane(p);
        usingPanel.pack();
        usingPanel.setVisible(true);

        // This makes the frame but does not paint the BluePanel
        JFrame usingLayer = new JFrame();
        JLayeredPane l = new JLayeredPane();
        l.setPreferredSize(new Dimension(200,200));
        l.add(new BluePanel(), JLayeredPane.DEFAULT_LAYER);
        JPanel p2 = new JPanel();
        p2.add(l);
        usingLayer.setContentPane(p2);
        usingLayer.pack();
        usingLayer.setVisible(true);
    }
	static class BluePanel extends JPanel{

        public BluePanel(){
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(Color.BLUE);
            g.fillRect(0, 0, getWidth(), getHeight());
        }


    }
	
	public Dimension getPreferredSize() {
	    return new Dimension(200, 200);
	} 

}
