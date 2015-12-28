package GameLobbyGUI;

import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.TextField;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import GameClient.ClientMessages;
import GameClient.ParseText;

public class LobbyGUI extends JPanel implements MouseListener {

	private ClientMessages cmsg;
	private ImageIcon readyBtn;
	private ImageIcon background;
	private ImageIcon go;
	private final String filepath = "ImageBank/GameLobby/";
	private ArrayList<String> players;
	private boolean killLookForPlayers;
	private boolean writePlayerName; 
	private TextField text;
	
	public LobbyGUI(ClientMessages cmsg){
		
		this.cmsg = cmsg;
		readyBtn = new ImageIcon(filepath + "ReadyButton.png");
		background = new ImageIcon(filepath + "Background.png");
		go = new ImageIcon(filepath + "Go.png");
		players = new ArrayList<String>();
		killLookForPlayers = false;
		writePlayerName = true;
		this.addMouseListener(this);
		
		startGetPlayers();
		
		this.setLayout(null);
		
		text = new TextField();
		this.add(text);
				
	}
	
	public void setClMsg(ClientMessages cmsg){
		
		this.cmsg = cmsg;
		startGetPlayers();
	}
	
	public void startGetPlayers(){
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					
					if(killLookForPlayers){
						
						break;
					}
					
					String message = cmsg.getMessage();
					
					if(message != "null"){
						
						players.clear();
						String[] playersStr = message.split(" ");
						
						for(int p = 0; p < playersStr.length; p++){
							
							players.add(playersStr[p]);
						}
						
					}
					
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			
			
		}).start();
		
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		g.drawImage(background.getImage(), 0, 0,this.getWidth(),this.getHeight(), null);
		
		if(writePlayerName){
			
			drawPlayerName(g);
			
		}else{
			
			drawLobby(g);
		}
		
		repaint();
	}
	
	private void drawPlayerName(Graphics g){
		
		text.setSize(this.getWidth()/3,this.getHeight()/25);
		text.setLocation((this.getWidth()/2) - text.getWidth()/2, this.getHeight()/2);
		
		double playerListWidth = (20*this.getWidth())/100;
		double playerListHeight = (20 * this.getHeight())/100;
		
		g.drawImage(go.getImage(), (int)(this.getWidth()/2 - playerListWidth/2), 
				(int)(((3*this.getHeight())/4) - playerListHeight/2) ,
				(int)playerListWidth,(int)playerListHeight, null);
		
	}
	
	private void drawLobby(Graphics g){
		
		//background
				
		//ready button 
		double readyBtnWidth = (20*this.getWidth())/100;
		double readyBtnHeight = (20*this.getHeight())/100;
		
		g.drawImage(readyBtn.getImage(),
				(int) ((this.getWidth()/4) - (readyBtnWidth/2)),
				(int) ((this.getHeight()/2) - (readyBtnHeight/2)),
				(int)readyBtnWidth,(int)readyBtnHeight,null);
		
		double playerListWidth = (40*this.getWidth())/100;
		double playerListHeight = (75 * this.getHeight())/100;
		
		double playerListX = (3*this.getWidth()/4);
		double playerListY = (this.getHeight()/2);
		
		g.drawImage(background.getImage(),(int)(playerListX- (playerListWidth/2)),
				(int)(playerListY - (playerListHeight/2)), (int)playerListWidth,(int)playerListHeight,
				null);
		
		drawPlayerList(g,(int)playerListX,
				(int)(playerListY - (playerListHeight/2) + (this.getHeight()/10)),
				(int)playerListHeight/9);
	}
	
	private void drawPlayerList(Graphics g,int x, int y,int spaceBetween){
		
		for(int p = 0; p < players.size(); p++){
			
			g.drawString(players.get(p), x, y+(p*spaceBetween));
		}
	}
	
	private void mousePlayerName(double x, double y){
		
		//go button
		if(inRect(0.40043923865300146,0.649645390070922,0.5988286969253295,
				0.8439716312056738,x,y) && text.getText().length() > 0){
			
			cmsg.addMessage("name " + text.getText());
			writePlayerName = false;
			text.setVisible(false);
			
		}
	}
	
	private void mouseGameLobby(double x, double y) {
		// TODO Auto-generated method stub
		//System.out.println(x + " " + y);
//		0.1493411420204978 0.3971631205673759
//		0.34773060029282576 0.5971631205673759
		
		if(inRect(0.1493411420204978,0.3971631205673759,0.34773060029282576,
				0.5971631205673759,x,y)){
			
			cmsg.addMessage("ENTERGAME");
		}
	}
	
	private boolean inRect(double xs, double ys, double xl,double yl,double xa,double ya){
		
		return (xa >= xs && xa <= xl && ya >= ys && ya <= yl);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		double x = (double)e.getX()/(double)this.getWidth();
		double y = (double)e.getY()/(double)this.getHeight();
		//System.out.println(x + " " + y);
		
		if(writePlayerName){
			
			mousePlayerName(x,y);
		
		}else{
			
			mouseGameLobby(x,y);
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
}
