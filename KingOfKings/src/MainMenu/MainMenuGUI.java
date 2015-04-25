package MainMenu;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * Creates the main menu GUI
 */
public class MainMenuGUI extends JPanel implements MouseListener, ActionListener {
	
	private Image exitImage;
	private Image optionsImage;
	private Image restoreGameImage;
	private Image newGameImage;
	private Image joinGameImage;
	private Image[] backgroundImages;
	private final String filepath = "ImageBank/MainMenu/";
	private Timer timer;
	private Timer refresh;
	private final int changeBGTime = 3000;
	
	private int backgroundCount = 0;
	
	private String pressedButton = "None";

	/*
	 * Initialises the main menu 
	 */
	public MainMenuGUI(){
		
		this.addMouseListener(this);
		
		timer = new Timer(changeBGTime,this);
		
		
		exitImage = new ImageIcon(filepath + "ExitButton.png").getImage();
		optionsImage = new ImageIcon(filepath + "OptionsButton.png").getImage();
		restoreGameImage = new ImageIcon(filepath + "RestoreGameButton.png").getImage();
		newGameImage = new ImageIcon(filepath + "NewGameButton.png").getImage();
		joinGameImage = new ImageIcon(filepath + "JoinGameButton.png").getImage();
		
		backgroundImages = new Image[5];
		
		//load background images 
		for(int i = 0; i < 5; i++){
			
			backgroundImages[i] = new ImageIcon(filepath + "backgroundImage"+Integer.toString(i+1)
					+".png").getImage();
			
		}
		
		timer.start();
		
	}
	
	public String getButtonPressed(){
		
		return pressedButton;
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		
		//draws the background image indicated by the backgroundCount variable
		g.drawImage(backgroundImages[backgroundCount], 0, 0, this.getWidth(),
				this.getHeight(), null);
				
		//exit button 
		g.drawImage(exitImage, this.getWidth()/2 - (this.getWidth()/8), 5*this.getHeight()/6,
				this.getWidth()/4,this.getHeight()/12, null);
		
		//options
		g.drawImage(optionsImage, this.getWidth()/2 - (this.getWidth()/8), 4*this.getHeight()/6,
				this.getWidth()/4,this.getHeight()/12, null);
		
		//join game button
		g.drawImage(joinGameImage, this.getWidth()/2 - (this.getWidth()/8), 3*this.getHeight()/6,
				this.getWidth()/4,this.getHeight()/12, null);
		
		//restore game button
		g.drawImage(restoreGameImage, this.getWidth()/2 - (this.getWidth()/8), 2*this.getHeight()/6,
				this.getWidth()/4,this.getHeight()/12, null);
		
		//new game button
		g.drawImage(newGameImage, this.getWidth()/2 - (this.getWidth()/8), this.getHeight()/6,
				this.getWidth()/4,this.getHeight()/12, null);
		
		
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 * 
	 * Checks whether the mouse is has been clicked inside the button.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		
		
		//exit button
		if(x > this.getWidth()/2 - (this.getWidth()/8)   && x < this.getWidth()/2 + (this.getWidth()/8)
				&& y > 5*this.getHeight()/6 && 
				y < 5*this.getHeight()/6 + (this.getHeight()/12)){
			
			System.exit(0);
		}
		
		//options button
		if(x > this.getWidth()/2 - (this.getWidth()/8)   && x < this.getWidth()/2 + (this.getWidth()/8)
					&& y > 4*this.getHeight()/6 && 
					y < 4*this.getHeight()/6 + (this.getHeight()/12)){
					
				pressedButton = "options";
		}
		
		//join game button
		if(x > this.getWidth()/2 - (this.getWidth()/8)   && x < this.getWidth()/2 + (this.getWidth()/8)
				&& y > 3*this.getHeight()/6 && 
				y < 3*this.getHeight()/6 + (this.getHeight()/12)){
							
				pressedButton = "joinGame";
		}
		
		//restore game button
		if(x > this.getWidth()/2 - (this.getWidth()/8)   && x < this.getWidth()/2 + (this.getWidth()/8)
					&& y > 2*this.getHeight()/6 && 
					y < 2*this.getHeight()/6 + (this.getHeight()/12)){
							
				pressedButton = "restoreGame";
		}
		
		//new game button
		if(x > this.getWidth()/2 - (this.getWidth()/8)   && x < this.getWidth()/2 + (this.getWidth()/8)
					&& y > this.getHeight()/6 && 
					y < this.getHeight()/6 + (this.getHeight()/12)){
									
				pressedButton = "newGame";
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//changes background image in cycle 
		if(backgroundCount == 4){
			
			backgroundCount = 0;
		
		}else{
			
			backgroundCount ++;
		}
		
		repaint();
	}

	
}
