package MainMenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import GameCommunicationServer.Communication;
import Util.FileFinder;


public class RestoreGameGUI extends JPanel implements MouseListener {
	
	private Image background;
	private Image arrow;
	private Image hostGame;
	private Image joinGame;
	
	private boolean downArrowSelected;
	private boolean upArrowSelected;
	private int saveGameIndex = 0;
	private final int LISTSIZE = 5;
	
	private String[] games;
	private int hoverOver;
	
	private int listItemX;
	private JFrame frame;
	
	public RestoreGameGUI(JFrame frame){
		 
		this.frame = frame;
		this.setLayout(new GridLayout(3,1));
		
		this.addMouseListener(this);


		JPanel container = new JPanel();
		container.setBackground(new Color(Color.TRANSLUCENT));
	
		games = FileFinder.RemoveExtension(FileFinder.CutDirectoryOut(
					FileFinder.FindFiles(FileFinder.SAVEDIRECTORY),FileFinder.SAVEDIRECTORY));
		
//		String[] labels = new String[files.length];
//		
//		for(int f = 0; f < files.length; f++){
//			
//			labels[f] = files[f].getPath();
//		}

		this.repaint();
		
	    background = new ImageIcon("ImageBank/MainMenu/restoregamebackground.png").getImage();
	    arrow = new ImageIcon("ImageBank/MainMenu/arrow.png").getImage();
	    hostGame = new ImageIcon("ImageBank/MainMenu/hostgame.png").getImage();
	    joinGame = new ImageIcon("ImageBank/MainMenu/joingame.png").getImage();
		
	}

	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		
		
		char[] title = "Restore game".toCharArray();
		g.setFont(new Font("Arial",Font.PLAIN,100));
		g.setColor(Color.white);
		g.drawImage(background, 0,0,this.getWidth(),this.getHeight(), null);
		g.drawChars(title, 0, title.length, (this.getWidth()/3), this.getHeight()/6);
		
		g.drawImage(joinGame, 3*this.getWidth()/4, 3*this.getHeight()/4,null);
		g.drawImage(hostGame, this.getWidth() - ((3*this.getWidth()/4) + joinGame.getWidth(null)),
				3*this.getHeight()/4,null);
		
		
		drawList(g);
		
		if(games.length > 5){
			drawArrows(g);
		}
		
	}
	
	private void drawArrows(Graphics g) {
		// TODO Auto-generated method stub
		
		if(saveGameIndex > 0){
			if(upArrowSelected){
				
				g.drawImage(getSelectedArrow(), 3*this.getWidth()/4, this.getHeight()/4, null);
				
			}else{
				
				g.drawImage(arrow, 3*this.getWidth()/4, this.getHeight()/4, null);
			}
		}
		
		if(games.length - saveGameIndex >= LISTSIZE){
			if(downArrowSelected){
				
				g.drawImage(getSelectedArrow(), 3*this.getWidth()/4, this.getHeight()/2 + arrow.getHeight(null),
						arrow.getWidth(null), -arrow.getHeight(null), null);
				
			}else{
				
				g.drawImage(arrow, 3*this.getWidth()/4, this.getHeight()/2 + arrow.getHeight(null),
						arrow.getWidth(null), -arrow.getHeight(null), null);
			}
		}
	}
	
	private Image getSelectedArrow() {
		// TODO Auto-generated method stub
		BufferedImage image = toBufferedImage(arrow);
		
		for(int x = 0; x < image.getWidth(); x++){
			for(int y = 0; y < image.getHeight(); y++){
				
				Color c = new Color(image.getRGB(x, y));

				if(!(c.getRed() == 0 && c.getBlue() == 0 && c.getGreen() == 0)){
					
					image.setRGB(x, y, new Color(0,0,255).getRGB());
				}
			}
		}
		return image;
	}


	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}


	private void drawList(Graphics g){
		
		if(games != null){
			
			listItemX = this.getWidth()/4;
			g.setFont(new Font("Arial",Font.PLAIN,76));
			
			for(int f = saveGameIndex; f < saveGameIndex + 5 && f < games.length; f++){
				
				if(hoverOver == (f-saveGameIndex)){
					g.setColor(Color.BLUE);
					g.fillRect(listItemX, (this.getHeight()/3) + ((f-saveGameIndex)
							* this.getHeight()/10) - (this.getHeight()/12),
							this.getWidth()/2, (this.getHeight()/10));
				}
				g.setColor(Color.white);
				g.drawString(games[f], listItemX, (this.getHeight()/3) + 
						((f-saveGameIndex) * this.getHeight()/10));
			}
		}
	}


	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		// TODO Auto-generated method stub
		if(games != null){
			
			int xHostGame = this.getWidth() - ((3*this.getWidth()/4) + joinGame.getWidth(null));
			
			if(inRect(3*this.getWidth()/4,(3*this.getWidth()/4) + this.joinGame.getWidth(null),
					3*this.getHeight()/4, (3*this.getHeight()/4) + this.joinGame.getHeight(null),
					e.getX(), e.getY())){
				
				String[] gameInfo = null;
				try {
					gameInfo = getGameInfo();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frame.removeAll();
				frame.getContentPane().removeAll();
				new GameGraphics.Graphics(frame,false,gameInfo[0],new Integer(gameInfo[1]));
				frame.revalidate();

			}else if(inRect(xHostGame,xHostGame + hostGame.getWidth(null),3*this.getHeight()/4,
					(3*this.getHeight()/4) + hostGame.getHeight(null),e.getX(),e.getY())){
				
				String[] gameInfo = null;
				try {
					gameInfo = getGameInfo();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				final String[] commInfo = new String[]{gameInfo[0],gameInfo[2]};
				
				new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							new Communication(commInfo[0],new Integer(commInfo[1]).intValue());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				}).start();
				
				//frame.removeAll();
				frame.getContentPane().removeAll();
				new GameGraphics.Graphics(frame,true,gameInfo[0],new Integer(gameInfo[1]));
				frame.revalidate();
				
			}else{
			
				for(int f = 0; f < 5; f++){
					
					if(inRect(listItemX,listItemX + (this.getWidth()/3),
							(this.getHeight()/3) + (f * this.getHeight()/10)- (this.getHeight()/12)
							,(this.getHeight()/3) + ((f+1) * this.getHeight()/10)
							- (this.getHeight()/12),e.getX(),e.getY())){
					
								hoverOver = f;
								repaint();
								break;
						}
				}
				
				if(games.length >= LISTSIZE){
					
					//up arrow
					if(inRect((3*this.getWidth()/4),(3*this.getWidth()/4) + arrow.getWidth(null), 
							(this.getHeight()/4),(this.getHeight()/4) + arrow.getHeight(null),e.getX(),e.getY())
							&& saveGameIndex > 0){
						
						upArrowSelected = true;
										
						new Timer(250,new ActionListener(){
		
							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								upArrowSelected = false;
								repaint();
							}
							
							
						}).start();
						
						saveGameIndex --;
						repaint();
						
					}
					//down arrow
					else if(inRect((3*this.getWidth()/4),(3*this.getWidth()/4) + arrow.getWidth(null), 
							(this.getHeight()/2),(this.getHeight()/2) + arrow.getHeight(null),e.getX(),e.getY())
							&& games.length - saveGameIndex >= LISTSIZE){
						
						downArrowSelected = true;
						
						new Timer(250,new ActionListener(){
		
							@Override
							public void actionPerformed(ActionEvent arg0) {
								// TODO Auto-generated method stub
								downArrowSelected = false;
								repaint();
							}
							
							
						}).start();
						
						saveGameIndex++;
						repaint();
					}
					
				}
				
			}
			
		}
	}
	
	private String[] getGameInfo() throws IOException {
		// TODO Auto-generated method stub
		File file = new File("SavedGames/" + games[hoverOver] + ".sav");
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		return new String[]{reader.readLine(),reader.readLine(),reader.readLine()};
	}


	private boolean inRect(int lx,int hx,int ly,int hy,int mx, int my){
		
		return mx > lx && mx < hx && my > ly && my < hy;
	}


	@Override
	public void mouseEntered(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(java.awt.event.MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
