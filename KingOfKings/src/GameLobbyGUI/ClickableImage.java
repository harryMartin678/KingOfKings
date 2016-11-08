package GameLobbyGUI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ClickableImage extends JPanel {

	private ImageIcon icon;
	
	public ClickableImage(String filePath){
		
		icon = new ImageIcon(filePath);
	}

	
	public void setXY(int x, int y){
		
		this.setLocation(x, y);
	}
	
	public void setWidthHeight(int width, int height){
		
		this.setSize(width, height);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		System.out.println("DRAW IMAGE CLICKABLEIMAGE");
		g.drawImage(icon.getImage(),0,0, this.getWidth(),this.getHeight(),null);
	}
	
}
