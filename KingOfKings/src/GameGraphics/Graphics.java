package GameGraphics;
import java.awt.CardLayout;
import java.awt.Container;

import javax.swing.JFrame;

import GameClient.ClientMessages;
import GameLobbyGUI.LobbyGUI;


public class Graphics{
	
	private ClientMessages cmsg;
	private JFrame window;
	private Container pane;
	private LobbyGUI lobby;

	public Graphics(JFrame frame,boolean isHost){

		window = frame;
		
		//window.setSizet(500,500);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.setTitle("King of Kings");
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		pane = window.getContentPane();
		pane.setLayout(new CardLayout(2,1));
		cmsg = new ClientMessages();
		lobby = new LobbyGUI(cmsg,window,isHost);
		pane.add(lobby);
		
		window.setVisible(true);
	}
	
	
	//public void startGame(){
		
//		final GLProfile profile = GLProfile.get(GLProfile.GL2);
//	    GLCapabilities capabilities = new GLCapabilities(profile);
//		GLJPanel canvas = new GLJPanel(capabilities);
//		
//		FPSAnimator animator = new FPSAnimator(canvas,10);
//		animator.start();
//		
//		
//		GameGraphics.GameScreenComposition.GameScreen gs = 
//				new GameGraphics.GameScreenComposition.GameScreen(cmsg);
//		
//		canvas.addMouseListener(gs.getMouseListener());
//		canvas.addMouseMotionListener(gs.getMouseMotionListener());
//		canvas.addKeyListener(gs.getKeyboardListener());
//		//JButton button = new JButton("test");
//		//canvas.add(button);
//		
//		canvas.addGLEventListener(gs);
//		//canvas.setBackground(new Color(1,1,1,0));
//		
//		/*MouseButtons mb = new MouseButtons();
//		mb.setOpaque(false);
//		mb.setBackground(new Color(1,1,1,0));*/
//		
//		
//		/*JPanel main = new JPanel();
//		main.setLayout(new OverlayLayout(main));
//		main.add(mb);
//		main.add(canvas);
//		main.revalidate();
//		main.repaint();*/
//		
//		pane.add(canvas);
//		pane.revalidate();
//		pane.repaint();
//		
//		window.revalidate();
		
	
		//JLayeredPane main = window.getLayeredPane();
		//main.add(canvas,Integer.valueOf(1));
		//main.add(mb,Integer.valueOf(2));
		
		//pane.add(main);
		
		
	//}
	
//	public static void main(String[] args) {
//		
//		new Graphics(new JFrame(),false);
//	}

}
