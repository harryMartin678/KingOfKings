package GameGraphics;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import Map.LoadMap;
import Map.Map;

public class GameScreen implements GLEventListener {
	
	private Model slave;
	private Model servant;
	private GLU glu;
	private ArrayList<Unit> units;
	private Map map;
	
	public GameScreen(){
		
		slave = null;
		servant = null;
		units = new ArrayList<Unit>();
		
		map = new LoadMap().getMap();
		
		//long time = System.currentTimeMillis();
		for(int y = 0; y < map.getHeight(); y++){
			for(int x = 0; x < map.getWidth(); x++){
				
				if(map.getTile(x, y) == 1){
				//if((y*map.getHeight() + x %map.getWidth())%2 == 0){
					units.add(new Unit(x-(map.getWidth()/2),y-(map.getHeight()/2),"slave"));
					//units.get(units.size()-1).moving();
					units.get(units.size()-1).setFiring();
				}else if(map.getTile(x, y) == 2){
					
					//if((y*map.getHeight() + x %map.getWidth())%2 == 0){
					units.add(new Unit(x-(map.getWidth()/2),y-(map.getHeight()/2),"servant"));
					//units.get(units.size()-1).moving();
					units.get(units.size()-1).setFiring();
				}
			}
		}
		
		Thread animation = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				while(true){
					
					long startTime = System.currentTimeMillis();
					
					for(int i = 0; i < units.size(); i++){
						
						units.get(i).changeCurrentFrame();
					}
					
					try {
						
						long sleepTime = 200 - startTime;
						
						if(sleepTime < 5){
							
							sleepTime = 5;
						}
						
						Thread.sleep(sleepTime);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		});
		
		animation.start();
			
		
		//System.out.println(System.currentTimeMillis() - time + " newTime");
		
		try {
			servant = new Model("servant","Models",3);
			slave = new Model("slave","Models",3);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
		//long time = System.currentTimeMillis();
		GL2 draw = drawable.getGL().getGL2();
		draw.glClear(draw.GL_COLOR_BUFFER_BIT | draw.GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
	    draw.glLoadIdentity();  // reset the model-view matrix
	    
	    glu.gluLookAt(0.0f, 0.0f, 10.0f, 
	    		0.0f, 0.0f, 0.0f, 
	    		0.0f, 0.0f, 0.0f);
		
	    
	    for(int i = 0; i < units.size(); i++){
	    	
	    	
	    	if(units.get(i).getUnitType().equals("slave")){
				drawModel(slave,draw,units.get(i).getX(),units.get(i).getY(),units.get(i));
	    	
	    	}else if(units.get(i).getUnitType().equals("servant")){
	    		
	    		drawModel(servant,draw,units.get(i).getX(),units.get(i).getY(),units.get(i));
	    	}
			
	    }	  
	   // System.out.println(System.currentTimeMillis() - time);
	    
	    drawable.swapBuffers();
	
	}
	
	public void drawModel(Model model, GL2 draw,float x, float y, Unit unit){
		
		Face next;

		draw.glTranslatef(x, y, -35); //-35
		draw.glScalef(0.2f, 0.2f, 0.2f);
		
		int currentFrame = unit.getCurrentFrame();
		boolean firing = unit.getFiring();

		
		while((next = model.popFace(currentFrame,firing)) != null){
			
			Colour colour = model.getColour(currentFrame,firing);
			draw.glColor3fv(FloatBuffer.wrap(colour.getDiffuse()));

			draw.glPushMatrix();
			draw.glBegin(draw.GL_POLYGON);

				float[] normal = getNormal(next,model, currentFrame,firing);
				draw.glNormal3f(normal[0],normal[1],normal[2]);
				
				for(int i = 0; i < next.getSize(); i++){
					
					Vertex vertex = model.getVertex(next.getFace(i)-1,currentFrame,firing);
					draw.glVertex3f(vertex.getX(),vertex.getY(),vertex.getZ());
				}
				
				
			draw.glEnd();
			draw.glPopMatrix();
			
			
		}
		
		
		draw.glLoadIdentity();
		
	}
	
	//todo: create polygon normal calculator 
	private float[] getNormal(Face next, Model model, int currentFrame,boolean firing){
		
		float[] normal = new float[]{0.0f,0.0f,0.0f};
		
		Vertex lastVertex = model.getVertex(next.getFace(0)-1, currentFrame,firing);
		
		for(int i = 0; i < next.getSize()-1; i++){
			
			Vertex vertex = model.getVertex(next.getFace(i+1)-1, currentFrame,firing);
			normal[0] += (lastVertex.getY() - vertex.getY()) * (lastVertex.getZ() + vertex.getZ());
			normal[1] += (lastVertex.getZ() - vertex.getZ()) * (lastVertex.getX() + vertex.getX());
			normal[2] += (lastVertex.getX() - vertex.getX()) * (lastVertex.getY() + vertex.getY());
			lastVertex = vertex;
			
		}
		
		Vertex vertex = model.getVertex(next.getFace(0)-1, currentFrame,firing);
		normal[0] += (lastVertex.getY() - vertex.getY()) * (lastVertex.getZ() + vertex.getZ());
		normal[1] += (lastVertex.getZ() - vertex.getZ()) * (lastVertex.getX() + vertex.getX());
		normal[2] += (lastVertex.getX() - vertex.getX()) * (lastVertex.getY() + vertex.getY());
		lastVertex = vertex;
		
		
		float size = (float) Math.sqrt(Math.pow(normal[0],2) + Math.pow(normal[1],2) + Math.pow(normal[2],2));
		
		normal[0] = normal[0]/size;
		normal[1] = normal[1]/size;
		normal[2] = normal[2]/size;
		
		return normal;
		
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
	    draw.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set background (clear) color
	    draw.glClearDepth(1.0f);      // set clear depth value to farthest
	    draw.glEnable(draw.GL_DEPTH_TEST); // enables depth testing
	    draw.glDepthFunc(draw.GL_LEQUAL);  // the type of depth test to do
	    draw.glShadeModel(draw.GL_SMOOTH); // blends colors nicely, and smoothes out lighting
	    
	    draw.glEnable(draw.GL_LIGHTING);							//enables lighting
	    draw.glEnable(draw.GL_LIGHT0);								//enables light0
	    draw.glEnable(draw.GL_COLOR_MATERIAL);						//enables materials
	    float lightPos[] = {0.0f,2.0f,0.0f}; //sets the position of the light to the position of the camera
	    draw.glLightfv(draw.GL_LIGHT0,draw.GL_POSITION,lightPos,0);//sets up the light
	    float ambient[] = {1.0f,1.0f,1.0f};//ambient, diffuse and specular values 
	    float diffuse[] = {1.0f,1.0f,1.0f};
	    float specular[] = {0.25f,0.25f,0.25f};
	    draw.glMaterialfv(draw.GL_FRONT,draw.GL_AMBIENT,ambient,0);//sets material ambient
	    draw.glMaterialfv(draw.GL_FRONT,draw.GL_DIFFUSE,diffuse,0);//sets material diffuse
	    draw.glMaterialfv(draw.GL_FRONT,draw.GL_SPECULAR,specular,0);//sets material specular
	    
	   
	    draw.glHint(draw.GL_PERSPECTIVE_CORRECTION_HINT, draw.GL_NICEST);

	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		
		  GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
		 
	      if (height == 0) height = 1;   // prevent divide by zero
	      float aspect = (float)width / height;
	 
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

}
