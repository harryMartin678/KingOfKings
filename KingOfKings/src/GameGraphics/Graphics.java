package GameGraphics;
import java.awt.Container;
import java.awt.GridLayout;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.JFrame;

import GameClient.ClientMessages;
import GameLobbyGUI.LobbyGUI;

import com.jogamp.opengl.util.FPSAnimator;


public class Graphics{
	
	private ClientMessages cmsg;
	private JFrame window;
	private Container pane;
	

	public Graphics(){

		window = new JFrame();
		window.setSize(500,500);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.setTitle("King of Kings");
		
		pane = window.getContentPane();
		
		pane.add(new LobbyGUI(this));
		
		window.setVisible(true);
	}
	
	public void connect(){
		
		cmsg = new ClientMessages();
	}
	
	public void startGame(){
		
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
	    GLCapabilities capabilities = new GLCapabilities(profile);
		GLJPanel canvas = new GLJPanel(capabilities);
		
		FPSAnimator animator = new FPSAnimator(canvas,10);
		animator.start();
		
		
		GameGraphics.GameScreenComposition.GameScreen gs = 
				new GameGraphics.GameScreenComposition.GameScreen(cmsg);
		
		canvas.addMouseListener(gs.getMouseListener());
		canvas.addMouseMotionListener(gs.getMouseMotionListener());
		canvas.addKeyListener(gs.getKeyboardListener());
		//JButton button = new JButton("test");
		//canvas.add(button);
		
		canvas.addGLEventListener(gs);
		//canvas.setBackground(new Color(1,1,1,0));
		
		/*MouseButtons mb = new MouseButtons();
		mb.setOpaque(false);
		mb.setBackground(new Color(1,1,1,0));*/
		
		
		/*JPanel main = new JPanel();
		main.setLayout(new OverlayLayout(main));
		main.add(mb);
		main.add(canvas);
		main.revalidate();
		main.repaint();*/
		
		pane.setLayout(new GridLayout(1,1));
		pane.add(canvas);
		
	
		//JLayeredPane main = window.getLayeredPane();
		//main.add(canvas,Integer.valueOf(1));
		//main.add(mb,Integer.valueOf(2));
		
		//pane.add(main);
		
		
	}
	
	public static void main(String[] args) {
		
		new Graphics();
	}

}
