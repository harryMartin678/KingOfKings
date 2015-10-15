package GameGraphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MouseButtons extends JPanel {
	
	public MouseButtons(){
		
		//super(new BorderLayout(2,5));

		
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();

		JButton button = new JButton("test");
/*		button.setOpaque(false);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);*/
		
		int i = 150;
		int j = 150;
		
		JPanel[][] panels = new JPanel[i][j];
		
		for(int y = 0; y < i; y++){
			for(int x = 0; x < j; x++){
				
				panels[y][x] = new JPanel();
				add(panels[y][x]);
			}
		}

		panels[50][50].add(button);
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
