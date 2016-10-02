package MainMenu;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * Creates the main menu GUI
 */
public class MainMenuGUI extends JPanel {
	
	private ImageIcon exitImage;
	private ImageIcon optionsImage;
	private ImageIcon restoreGameImage;
	private ImageIcon startGameImage;
	private ImageIcon joinGameImage;
	//private Image[] backgroundImages;
	private Image backgroundImage;
	private final String filepath = "ImageBank/MainMenu/";
	private Timer timer;
	
	public static String OPTIONS = "options";
	public static String STARTGAME = "startgame";
	public static String RESTOREGAME = "restoregame";
	public static String JOINGAME = "joingame";
	//private final int changeBGTime = 3000;
	
	//private int backgroundCount = 0;
	
	private String pressedButton = "None";

	/*
	 * Initialises the main menu 
	 */
	public MainMenuGUI(){
		
		//timer = new Timer(changeBGTime,this);
		
		this.setLayout(new GridLayout(5,1));
		
		exitImage = new ImageIcon(filepath + "exit.png");
		JLabel exit = new JLabel(exitImage);
		exit.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		optionsImage = new ImageIcon(filepath + "options.png");
		JLabel oLabel = new JLabel(optionsImage);
		oLabel.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				pressedButton = OPTIONS;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		restoreGameImage = new ImageIcon(filepath + "restoregame.png");
		JLabel rgLabel = new JLabel(restoreGameImage);
		rgLabel.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				pressedButton = RESTOREGAME;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		startGameImage = new ImageIcon(filepath + "hostgame.png");
		JLabel sgLabel = new JLabel(startGameImage);
		sgLabel.setPreferredSize(new Dimension(400, 100));
		sgLabel.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				pressedButton = STARTGAME;
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
			
		});
		
		joinGameImage = new ImageIcon(filepath + "joingame.png");
		JLabel jgLabel = new JLabel(joinGameImage);
		jgLabel.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				pressedButton = JOINGAME;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		this.add(sgLabel);
		this.add(jgLabel);
		this.add(rgLabel);
		this.add(oLabel);
		this.add(exit);
		
		backgroundImage = new ImageIcon(filepath + "MainMenuBackground.png").getImage();
		
	//	backgroundImages = new Image[5];
		
		//load background images 
//		for(int i = 0; i < 5; i++){
//			
//			backgroundImages[i] = new ImageIcon(filepath + "backgroundImage"+Integer.toString(i+1)
//					+".png").getImage();
//			
//		}
		
		//timer.start();
		
	}
	
	public String getButtonPressed(){
		
		return pressedButton;
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		
		//draws the background image indicated by the backgroundCount variable
		g.drawImage(backgroundImage, 0, 0, this.getWidth(),
				this.getHeight(), null);
				
//		//exit button 
//		g.drawImage(exitImage.getImage(), this.getWidth()/2 - (this.getWidth()/8), 5*this.getHeight()/6,
//				this.getWidth()/4,this.getHeight()/12, null);
//		
//		//options
//		g.drawImage(optionsImage.getImage(), this.getWidth()/2 - (this.getWidth()/8), 4*this.getHeight()/6,
//				this.getWidth()/4,this.getHeight()/12, null);
//		
//		//join game button
//		g.drawImage(joinGameImage.getImage(), this.getWidth()/2 - (this.getWidth()/8), 3*this.getHeight()/6,
//				this.getWidth()/4,this.getHeight()/12, null);
//		
//		//restore game button
//		g.drawImage(restoreGameImage.getImage(), this.getWidth()/2 - (this.getWidth()/8), 2*this.getHeight()/6,
//				this.getWidth()/4,this.getHeight()/12, null);
//		
//		//new game button
//		g.drawImage(startGameImage.getImage(), this.getWidth()/2 - (this.getWidth()/8), this.getHeight()/6,
//				this.getWidth()/4,this.getHeight()/12, null);
		
		
	}

//	/*
//	 * (non-Javadoc)
//	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
//	 * 
//	 * Checks whether the mouse is has been clicked inside the button.
//	 */
//	@Override
//	public void mouseClicked(MouseEvent e) {
//		// TODO Auto-generated method stub
//		int x = e.getX();
//		int y = e.getY();
//		
//		
//		//exit button
//		if(x > this.getWidth()/2 - (this.getWidth()/8)   && x < this.getWidth()/2 + (this.getWidth()/8)
//				&& y > 5*this.getHeight()/6 && 
//				y < 5*this.getHeight()/6 + (this.getHeight()/12)){
//			
//			System.exit(0);
//		}
//		
//		//options button
//		if(x > this.getWidth()/2 - (this.getWidth()/8)   && x < this.getWidth()/2 + (this.getWidth()/8)
//					&& y > 4*this.getHeight()/6 && 
//					y < 4*this.getHeight()/6 + (this.getHeight()/12)){
//					
//				pressedButton = OPTIONS;
//		}
//		
//		//join game button
//		if(x > this.getWidth()/2 - (this.getWidth()/8)   && x < this.getWidth()/2 + (this.getWidth()/8)
//				&& y > 3*this.getHeight()/6 && 
//				y < 3*this.getHeight()/6 + (this.getHeight()/12)){
//							
//				pressedButton = JOINGAME;
//		}
//		
//		//restore game button
//		if(x > this.getWidth()/2 - (this.getWidth()/8)   && x < this.getWidth()/2 + (this.getWidth()/8)
//					&& y > 2*this.getHeight()/6 && 
//					y < 2*this.getHeight()/6 + (this.getHeight()/12)){
//							
//				pressedButton = RESTOREGAME;
//		}
//		
//		//start game button
//		if(x > this.getWidth()/2 - (this.getWidth()/8)   && x < this.getWidth()/2 + (this.getWidth()/8)
//					&& y > this.getHeight()/6 && 
//					y < this.getHeight()/6 + (this.getHeight()/12)){
//									
//				pressedButton = STARTGAME;
//		}
//	}
//
//	@Override
//	public void mouseEntered(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseExited(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mousePressed(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void mouseReleased(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void actionPerformed(ActionEvent e) {
//		// TODO Auto-generated method stub
//		
//		//changes background image in cycle 
////		if(backgroundCount == 4){
////			
////			backgroundCount = 0;
////		
////		}else{
////			
////			backgroundCount ++;
////		}
//		
//		repaint();
//	}

	
}
