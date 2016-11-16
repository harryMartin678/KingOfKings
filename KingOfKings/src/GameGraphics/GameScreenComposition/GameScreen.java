package GameGraphics.GameScreenComposition;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import AI.AIHandler;
import AI.IAI;
import AI.InitialAI;
import GameClient.ClientMessages;

public class GameScreen implements GLEventListener  {
	
	private GLU glu;
	private GLUT glut;
	private GraphicsEngine engine;
	private ClientMessages cmsg;
	private ClientWrapper wrapper;
	private JFrame frame;
	
	public GameScreen(ClientMessages cmsg,int thisPlayer,int noOfPlayer,JFrame frame,String loadGame){
		
		wrapper = new ClientWrapper(cmsg,thisPlayer,noOfPlayer,loadGame);
		this.cmsg = cmsg;
		this.frame = frame;
		
		System.out.println("start GameScreen");
		waitForReady.start();
		System.out.println("finish");
		
		try {
			System.out.println("engine start");
			engine = new GraphicsEngine(wrapper);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	Thread waitForReady = new Thread(new Runnable(){
		

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				
				String message;
				if((message = cmsg.getMessage()) != "null"){
					
					
					if(message.equals("ALLREADY")){
						
						System.out.println(message + " message");
						wrapper.GameStarted();
						Start();
						
						break;
						
					}else{
						
						cmsg.putBackMessage(message);
					}
					
				}
				
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	});
	
	public void Start(){
		
		System.out.println((engine != null) + " null");
		engine.Start();
	}
	
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		
		  GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
		 
	      if (height == 0) height = 1;   // prevent divide by zero
	      float aspect = (float)width / height;
	      
	      engine.SetWidth(width);
	      engine.SetHeight(height);
	 
	      // Set the view port (display area) to cover the entire window
	      gl.glViewport(0, 0, width, height);
	 
	      // Setup perspective projection, with aspect ratio matches viewport
	      gl.glMatrixMode(gl.GL_PROJECTION);  // choose projection matrix
	      gl.glLoadIdentity();             // reset projection matrix
	      glu.gluPerspective(45.0, aspect, 0.1, 100.0); // fovy, aspect, zNear, zFar
	 
	      // Enable the model-view transform
	      gl.glMatrixMode(gl.GL_MODELVIEW);
	      gl.glLoadIdentity(); // reset
		
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		engine.display(drawable, glu, glut);
		frame.revalidate();
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		GL2 draw = drawable.getGL().getGL2();      // get the OpenGL graphics context
		glu = new GLU();
		glut = new GLUT();
	    //draw.glClearColor(0.93f, 0.79f, 0.68f, 0.0f); // set background (clear) color
		draw.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
	    draw.glClearDepth(1.0f);      // set clear depth value to farthest
	    draw.glEnable(draw.GL_DEPTH_TEST); // enables depth testing
	    draw.glDepthFunc(draw.GL_LEQUAL);  // the type of depth test to do
	    draw.glShadeModel(draw.GL_SMOOTH); // blends colors nicely, and smoothes out lighting
	    
	    draw.glEnable(draw.GL_LIGHTING);							//enables lighting
	    draw.glEnable(draw.GL_LIGHT0);								//enables light0
	    draw.glEnable(draw.GL_COLOR_MATERIAL);						//enables materials
	    float lightPos[] = {0.0f,0.0f,10.0f}; //sets the position of the light to the position of the camera
	    draw.glLightfv(draw.GL_LIGHT0,draw.GL_POSITION,lightPos,0);//sets up the light
	    float ambient[] = {0.88f,0.98f,0.05f};//ambient, diffuse and specular values 
	    float diffuse[] = {0.88f,0.98f,0.05f};
	    float specular[] = {0.88f,0.98f,0.05f};
	    draw.glLightfv(draw.GL_FRONT,draw.GL_AMBIENT,ambient,0);//sets material ambient
	    draw.glLightfv(draw.GL_FRONT,draw.GL_DIFFUSE,diffuse,0);//sets material diffuse
	    draw.glLightfv(draw.GL_FRONT,draw.GL_SPECULAR,specular,0);//sets material specular
	    engine.init(drawable,glu);
	    
	   
	    draw.glHint(draw.GL_PERSPECTIVE_CORRECTION_HINT, draw.GL_NICEST);
	}
	
	public MouseListener getMouseListener(){
		
		return engine.getMouseListener();
	}
	
	public MouseWheelListener getMouseWheelListener(){
		
		return engine.getMouseWheelListener();
	}
	
	public MouseMotionListener getMouseMotionListener(){
		
		return engine.getMouseMotionListener();
	}
	
	public KeyListener getKeyboardListener(){
		
		return engine.getKeyboardListener();
	}

	public boolean getStart() {
		// TODO Auto-generated method stub
		return engine.getStart();
	}

}
