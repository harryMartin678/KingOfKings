package GameGraphics;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLDrawableFactory;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;

import GameClient.ClientMessages;

import com.jogamp.opengl.util.FPSAnimator;


public class Graphics{
	
	private ClientMessages cmsg;
	

	public Graphics(){

		
		JFrame window = new JFrame();
		window.setSize(500,500);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.setTitle("King of Kings");
		
		cmsg = new ClientMessages();
		
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
	    GLCapabilities capabilities = new GLCapabilities(profile);
		GLJPanel canvas = new GLJPanel(capabilities);
		
		FPSAnimator animator = new FPSAnimator(canvas,10);
		animator.start();
		
		
		GameScreen gs = new GameScreen(cmsg);
		
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
		
		Container pane = window.getContentPane();
		 
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
		
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		new Graphics();
	}

}
