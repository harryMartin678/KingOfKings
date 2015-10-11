package GameGraphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MouseButtons extends JPanel {
	
	public MouseButtons(){
		
		//super(new BorderLayout(2,5));

		JButton button = new JButton("test");
		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		this.add(button);
		button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("PRESSED TEST!!!");
			}
			
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
	}

}
