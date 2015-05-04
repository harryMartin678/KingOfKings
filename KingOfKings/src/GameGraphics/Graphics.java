package GameGraphics;
import java.awt.Container;
import java.awt.GridLayout;

import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLDrawableFactory;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLJPanel;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jogamp.opengl.util.FPSAnimator;


public class Graphics{
	
	
	public Graphics(){
		
		JFrame window = new JFrame();
		window.setSize(500,500);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.setTitle("King of Kings");
		
		Container pane = window.getContentPane();
		
		final GLProfile profile = GLProfile.get(GLProfile.GL2);
	    GLCapabilities capabilities = new GLCapabilities(profile);
		GLJPanel canvas = new GLJPanel(capabilities);
		
		FPSAnimator animator = new FPSAnimator(canvas,30);
		animator.start();
		
		GameScreen gs = new GameScreen();
		
		canvas.addGLEventListener(gs);
		
		
		pane.setLayout(new GridLayout(1,2));
		pane.add(canvas);

		
		window.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		new Graphics();
	}

}
