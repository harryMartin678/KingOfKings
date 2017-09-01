package GameLobbyGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

import GameClient.ClientMessages;

public class LobbyGUI extends JPanel implements MouseListener {

	private ClientMessages cmsg;
	private ImageIcon readyBtn;
	private ImageIcon background;
	private ImageIcon go;
	private ImageIcon loadingScreen;
	private ImageIcon addAIBtn;
	private ImageIcon lobbyPanel;
	private final String filepath = "ImageBank/GameLobby/";
	private ArrayList<String> players;
	private boolean killLookForPlayers;
	private boolean enterGame;
	private boolean writePlayerName; 
	private TextField text;
	private TextField ipText;
	private int thisPlayer;
	private GameGraphics.GameScreenComposition.GameScreen gs;
	private boolean loading;
	
	private int bsyIdtX;
	private int bsyIdtY;
	private double angle;

	private boolean isHost;
	private String loadGame;
	
	private JFrame frame;
	
	public LobbyGUI(ClientMessages cmsg,final JFrame frame,boolean isHost,
			String loadGame){
		
		this.setLayout(null);
		this.cmsg = cmsg;
		this.loadGame = loadGame;
		readyBtn = new ImageIcon(filepath + "ReadyButton.png");
		background = new ImageIcon(filepath + "backgroundImage.png");
		loadingScreen = new ImageIcon(filepath + "LoadingScreen.png");
		addAIBtn = new ImageIcon(filepath + "AddAIButton.png");
		go = new ImageIcon(filepath + "GoButton.png");
		lobbyPanel = new ImageIcon(filepath + "lobbyPanel.png");
		
		players = new ArrayList<String>();
		killLookForPlayers = false;
		writePlayerName = true;
		enterGame = false;
		loading = false;
		thisPlayer = -1;
		
		this.addMouseListener(this);
		
		//setUpMouse();
		
		this.isHost = isHost;
		
		this.frame = frame;
		
		if(loadGame != null){
			
			try {
				thisPlayer = getPlayerNo(loadGame);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		this.addComponentListener(new ComponentListener(){

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				JPanel panel = (JPanel) e.getComponent();
				bsyIdtX = (3*panel.getWidth())/4;
				bsyIdtY = panel.getHeight()/2;
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		angle = 0;
		
		text = new TextField();
		this.add(text);
		
		
		if(!isHost){
			ipText = new TextField();
			this.add(ipText);
			ipText.setText("127.0.0.1");
		}
				
	}
	

	private int getPlayerNo(String loadGame) throws IOException{
		// TODO Auto-generated method stub
		BufferedReader reader = new BufferedReader(new FileReader(new File("SavedGames/"+loadGame+".sav")));
		reader.readLine();
		return new Integer(reader.readLine()).intValue();
	}

	public void setClMsg(ClientMessages cmsg){
		
		this.cmsg = cmsg;
		startGetPlayers();
	}


	public void startGame(){
		
		System.out.println("Start Game");
		
		bsyIdtX = (3*this.getWidth())/4;
		bsyIdtY = this.getHeight()/2;
		
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
	    GLCapabilities capabilities = new GLCapabilities(profile);
		final GLJPanel canvas = new GLJPanel(capabilities);
		
		FPSAnimator animator = new FPSAnimator(canvas,10);
		animator.start();
		
		//canvas.setFocusable(true);
		 gs = new GameGraphics.GameScreenComposition.GameScreen(cmsg,thisPlayer
					,players.size(),frame,loadGame);

		canvas.addMouseListener(gs.getMouseListener());
		canvas.addMouseMotionListener(gs.getMouseMotionListener());
		canvas.addKeyListener(gs.getKeyboardListener());
		canvas.addMouseWheelListener(gs.getMouseWheelListener());

		canvas.addGLEventListener(gs);
		
		canvas.setFocusable(true);
		canvas.requestFocusInWindow();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(!gs.getStart()){
					
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				loading = false;
				setScreenToGame(canvas);
			}
			
			
			
		}).start();
		
		
		
	}
	
	public void setScreenToGame(GLJPanel canvas){
		
		this.setLayout(new GridLayout(1,1));
		this.removeAll();
		//text.setVisible(false);
		this.add(canvas);
		this.revalidate();
		this.repaint();
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
						
						if(message.equals("StartGame")){

							enterGame = true;
							killLookForPlayers = true;
							loading = true;
							
							startGame();
							
						}else{
						
						
							players.clear();
							String[] playersStr = message.split(" ");
							
							for(int p = 0; p < playersStr.length; p++){
								
								players.add(playersStr[p]);
							}
							
							if(thisPlayer == -1 && loadGame == null){
								
								thisPlayer = players.size()-1;
							}
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
		if(!enterGame){
			super.paintComponent(g);
			
			g.drawImage(background.getImage(), 0, 0,this.getWidth(),this.getHeight(), null);
			
			
			 if(writePlayerName){
				
				drawPlayerName(g);
				
			}else{
				
				drawLobby(g);
			}
			

			repaint();
		
		}else if(loading){
			
			drawLoadingScreen(g);	
			
			try {
				Thread.sleep(175);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			repaint();
			
		}
		
		
	}
	
	private void drawLoadingScreen(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(background.getImage(), 0, 0, this.getWidth(),this.getHeight(),null);
		g.setColor(Color.BLUE);
		g.setFont(new Font("Arial",Font.BOLD,125));
		g.drawString("Loading...", this.getWidth()/4, this.getHeight()/2);
		drawBusyIndicator(g);
	}
	
	private void drawBusyIndicator(Graphics g){
		
		int radius = (5*this.getWidth())/100;
		
		g.fillOval(bsyIdtX, bsyIdtY, radius,radius);
		
		bsyIdtX = (int) (bsyIdtX + Math.sin(angle)*radius);
		bsyIdtY = (int) (bsyIdtY + Math.cos(angle)*radius);
		
		angle+=45;
	}

	private void drawPlayerName(Graphics g){
		
		text.setSize(this.getWidth()/3,this.getHeight()/25);
		text.setLocation((this.getWidth()/2) - text.getWidth()/2, 
				(this.getHeight()/2) - (this.getHeight()/20));
		
		if(!isHost){
			ipText.setSize(this.getWidth()/3, this.getHeight()/25);
			ipText.setLocation(((this.getWidth()/2) - ipText.getWidth()/2), 
					(this.getHeight()/2) + (this.getHeight()/20));
		}
		
		double playerListWidth = (20*this.getWidth())/100;
		double playerListHeight = (20 * this.getHeight())/100;
		
		g.drawImage(go.getImage(), (int)(this.getWidth()/2 - playerListWidth/2), 
				(int)(((3*this.getHeight())/4) - playerListHeight/2) ,
				(int)playerListWidth,(int)playerListHeight, null);
		
	}
	
	private void drawLobby(Graphics g){
				
		//ready button 
		double readyBtnWidth = (20*this.getWidth())/100;
		double readyBtnHeight = (20*this.getHeight())/100;
		
		g.drawImage(readyBtn.getImage(),
				(int) ((this.getWidth()/4) - (readyBtnWidth/2)),
				(int) ((this.getHeight()/3) - (readyBtnHeight/2)),
				(int)readyBtnWidth,(int)readyBtnHeight,null);
	
		
		g.drawImage(addAIBtn.getImage(), (int) ((this.getWidth()/4) - (readyBtnWidth/2)),
				(int) ((2*(this.getHeight()/3)) - (readyBtnHeight/2)), 
				(int)readyBtnWidth,(int)readyBtnHeight,null);
		
		double playerListWidth = (40*this.getWidth())/100;
		double playerListHeight = (75 * this.getHeight())/100;
		
		double playerListX = (3*this.getWidth()/4);
		double playerListY = (this.getHeight()/2);
		
		g.drawImage(lobbyPanel.getImage(),(int)(playerListX- (playerListWidth/2)),
				(int)(playerListY - (playerListHeight/2)), (int)playerListWidth,(int)playerListHeight,
				null);
		
		drawPlayerList(g,(int)playerListX,
				(int)(playerListY - (playerListHeight/2) + (this.getHeight()/10)),
				(int)playerListHeight/9);
	}
	
	private void drawPlayerList(Graphics g,int x, int y,int spaceBetween){
		
		g.setFont(new Font("Arial",Font.BOLD,30));
		g.setColor(Color.WHITE);
		for(int p = 0; p < players.size(); p++){
			
			g.drawString(players.get(p), x, y+(p*spaceBetween));
		}
	}
	
	private void mousePlayerName(double x, double y){
		
		//go button
		if(inRect(0.40043923865300146,0.649645390070922,0.5988286969253295,
				0.8439716312056738,x,y) && text.getText().length() > 0){
			
			enterLobby();
		}
	}
	
	private boolean inRect(double lx,double ly, double hx, double hy,double x, double y){
		
		return (x <= hx && x >= lx && y <= hy && y >= ly);
	}
	
	private void enterLobby(){
		
		if(isHost){
			cmsg.startClient("127.0.0.1");
		}else{
			cmsg.startClient(ipText.getText());
			ipText.setVisible(false);
		}
		startGetPlayers();
		cmsg.addMessage("name " + text.getText());
		writePlayerName = false;
		text.setVisible(false);
		
	}
	
	private void mouseGameLobby(double x, double y) {
		// TODO Auto-generated method stub
		//System.out.println(x + " " + y);
//		0.1493411420204978 0.3971631205673759
//		0.34773060029282576 0.5971631205673759
		
		
		
		//0.14963119072708114 0.43161978661493694
		//0.3487881981032666 0.2318137730358875
		//ready
		if(inRect(0.14963119072708114,0.2318137730358875,0.3487881981032666,
				0.43161978661493694,x,y)){
			
			cmsg.addMessage("ENTERGAME");
			
		//0.15015806111696522 0.7652764306498545
		//0.3487881981032666 0.5664403491755577
		//add ai
		}else if(inRect(0.15015806111696522,0.5664403491755577,0.3487881981032666,
				0.7652764306498545,x,y)){
			
			cmsg.addMessage("ADDAI");
		}
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

}
