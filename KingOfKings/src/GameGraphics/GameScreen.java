package GameGraphics;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

import Map.LoadMap;
import Map.Map;

public class GameScreen implements GLEventListener {
	
	private Model slave;
	private Model servant;
	private Model axeman;
	private Model swordsman;
	private Model spearman;
	private Model fishingBoat;
	private Model warship;
	private Model flagship;
	private Model lightChariot;
	private Model heavyChariot;
	private Model archer;
	private Model heavyarcher;
	private BuildingModel archeryTower;
	private BuildingModel ballisticTower;
	private BuildingModel barrack;
	private BuildingModel castle;
	private GLU glu;
	private ArrayList<Unit> units;
	private ArrayList<Building> buildings;
	private Map map;
	
	public GameScreen(){
		
		slave = null;
		servant = null;
		axeman = null;
		swordsman = null;
		spearman = null;
		fishingBoat = null;
		warship = null;
		flagship = null;
		lightChariot = null;
		heavyChariot = null;
		archer = null;
		heavyarcher = null;
		archeryTower = null;
		ballisticTower = null;
		barrack = null;
		
		units = new ArrayList<Unit>();
		buildings = new ArrayList<Building>();
		
		map = new LoadMap().getMap();
		
		try {
			servant = new Model("servant","Models",3);
			slave = new Model("slave","Models",3);
			axeman = new Model("axeman","Models",3);
			swordsman = new Model("swordsman","Models",3);
			spearman = new Model("spearman","Models",3);
			fishingBoat = new Model("fishingboat","Models",1);
			warship = new Model("warship","Models",1);
			flagship = new Model("flagship","Models",1);
			lightChariot = new Model("lightchariot","Models",3);
			heavyChariot = new Model("heavychariot","Models",3);
			archer = new Model("archer","Models",3);
			heavyarcher = new Model("heavyarcher","Models",3);
			archeryTower = new BuildingModel("archerytower","Models",1);
			ballisticTower = new BuildingModel("ballisticTower","Models",1);
			barrack = new BuildingModel("barrack","Models",1);
			castle = new BuildingModel("castle","Models",1);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//long time = System.currentTimeMillis();
		for(int y = map.getHeight()-1; y >= 0; y--){
			for(int x = map.getWidth()-1; x >= 0; x--){
				
				//units.add(new Unit(x-(map.getWidth()/2),y-(map.getHeight()/2),"lightchariot"));
				//units.get(units.size()-1).moving();
			
				
				if(map.getTile(x, y) == 1){

					units.add(new Unit(x-(map.getWidth()/2),y-(map.getHeight()/2),"slave"));
					units.get(units.size()-1).setFiring();
					
				}else if(map.getTile(x, y) == 2){
					
					units.add(new Unit(x-(map.getWidth()/2),y-(map.getHeight()/2),"servant"));

				}else if(map.getTile(x,y) == 3){
					
					units.add(new Unit(x-(map.getWidth()/2),y-(map.getHeight()/2),"axeman"));
					units.get(units.size()-1).setFiring();
					
				}else if(map.getTile(x,y) == 4){
					
					units.add(new Unit(x-(map.getWidth()/2),y-(map.getHeight()/2),"swordsman"));

				}else if(map.getTile(x,y) == 5){
					
					units.add(new Unit(x-(map.getWidth()/2),y-(map.getHeight()/2),"spearman"));
					units.get(units.size()-1).moving();
					//units.get(units.size()-1).setFiring();
				}else if(map.getTile(x,y) == 6){
					
					units.add(new Unit(x-(map.getWidth()/2),y-(map.getHeight()/2),"lightchariot"));
					units.get(units.size()-1).moving();
					//units.get(units.size()-1).setFiring();
				}else if(map.getTile(x,y) == 7){
					
					units.add(new Unit(x-(map.getWidth()/2),y-(map.getHeight()/2),"heavychariot"));
					units.get(units.size()-1).moving();
					//units.get(units.size()-1).setFiring();
				}else if(map.getTile(x,y) == 8){
					
					units.add(new Unit(x-(map.getWidth()/2),y-(map.getHeight()/2),"archer"));
					//units.get(units.size()-1).moving();
					units.get(units.size()-1).setFiring();
				}else if(map.getTile(x,y) == 9){
					
					units.add(new Unit(x-(map.getWidth()/2),y-(map.getHeight()/2),"heavyarcher"));
					//units.get(units.size()-1).moving();
					units.get(units.size()-1).setFiring();
				}else if(map.getTile(x,y) == 10){
					
					units.add(new Unit(x-(map.getWidth()/2),y-(map.getHeight()/2),"fishingboat"));
		
				}else if(map.getTile(x,y) == 11){
					
					units.add(new Unit(x-(map.getWidth()/2),y-(map.getHeight()/2),"warship"));
				
				}else if(map.getTile(x,y) == 12){
					
					units.add(new Unit(x-(map.getWidth()/2),y-(map.getHeight()/2),"flagship"));

				}else if(map.getTile(x,y) == 20){
					
					buildings.add(new Building(x-(map.getWidth()/2),y-(map.getHeight()/2),"archerytower"));
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
						
						long sleepTime = 200 - (System.currentTimeMillis() - startTime);
						
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
		
		
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
		GL2 draw = drawable.getGL().getGL2();
		draw.glClear(draw.GL_COLOR_BUFFER_BIT | draw.GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
	    draw.glLoadIdentity();  // reset the model-view matrix
	    
	    glu.gluLookAt(0.0f, 0.0f, 10.0f, 
	    		0.0f, 10.0f, 0.0f, 
	    		0.0f, 0.0f, 0.0f);
	    
	  //  draw.glDisable(draw.GL_LIGHTING);
	    for(int i = 0; i < units.size(); i++){
	    	
	    	if(units.get(i).getUnitType().equals("slave")){
	    		
				drawModel(slave,draw,units.get(i).getX(),units.get(i).getY(),units.get(i));
	    	
	    	}else if(units.get(i).getUnitType().equals("servant")){
	    		
	    		drawModel(servant,draw,units.get(i).getX(),units.get(i).getY(),units.get(i));
	    	
	    	}else if(units.get(i).getUnitType().equals("axeman")){
	    		
	    		drawModel(axeman,draw,units.get(i).getX(),units.get(i).getY(),units.get(i));
	    	
	    	}else if(units.get(i).getUnitType().equals("swordsman")){
	    		
	    		drawModel(swordsman,draw,units.get(i).getX(),units.get(i).getY(),units.get(i));
	    
	    	}else if(units.get(i).getUnitType().equals("spearman")){
	    		
	    		drawModel(spearman,draw,units.get(i).getX(),units.get(i).getY(),units.get(i));
	    
	    	}else if(units.get(i).getUnitType().equals("fishingboat")){
	    		
	    		drawModel(fishingBoat,draw,units.get(i).getX(),units.get(i).getY(),units.get(i));
	    	
	    	}else if(units.get(i).getUnitType().equals("warship")){
	    		
	    		drawModel(warship,draw,units.get(i).getX(),units.get(i).getY(),units.get(i));
	    		
	    	}else if(units.get(i).getUnitType().equals("flagship")){
	    		
	    		drawModel(flagship,draw,units.get(i).getX(),units.get(i).getY(),units.get(i));
	    	
	    	}else if(units.get(i).getUnitType().equals("lightchariot")){
	    		
	    		drawModel(lightChariot,draw,units.get(i).getX(),units.get(i).getY(),units.get(i));
	    	
	    	}else if(units.get(i).getUnitType().equals("heavychariot")){
	    		
	    		drawModel(heavyChariot,draw,units.get(i).getX(),units.get(i).getY(),units.get(i));
	    	
	    	}else if(units.get(i).getUnitType().equals("archer")){
	    		
	    		drawModel(archer,draw,units.get(i).getX(),units.get(i).getY(),units.get(i));
	    	
	    	}else if(units.get(i).getUnitType().equals("heavyarcher")){
	    		
	    		drawModel(heavyarcher,draw,units.get(i).getX(),units.get(i).getY(),units.get(i));
	    	}
			
	    }	
	    
	    draw.glEnable(draw.GL_LIGHTING);
	    drawBuildingModel(castle,draw,0,10,new Building(0,100,"barrack"));


	    drawable.swapBuffers();
	
	}
	
	public void drawModel(Model model, GL2 draw,float x, float y, Unit unit){
		
		Face next;

		draw.glLoadIdentity();

		draw.glTranslatef(x, y, -35); //-35
		draw.glScalef(0.2f, 0.2f, 0.2f);
		draw.glRotatef(45, 1, 0, 0);
		
		int currentFrame = unit.getCurrentFrame();
		boolean firing = unit.getFiring();

		while((next = model.popFace(currentFrame,firing)) != null){
			
			Colour colour = model.getColour(currentFrame,firing);
			draw.glColor3fv(FloatBuffer.wrap(colour.getDiffuse()));

			draw.glBegin(draw.GL_POLYGON);

				float[] normal = getNormal(next,model, currentFrame,firing);
				draw.glNormal3f(normal[0],normal[1],normal[2]);
				
				for(int i = 0; i < next.getSize(); i++){
					
					Vertex vertex = model.getVertex(next.getFace(i)-1,currentFrame,firing);
					draw.glVertex3f(vertex.getX(),vertex.getY(),vertex.getZ());
				}
				
				
			draw.glEnd();
			
		}
		
		
	}
	
	public void drawBuildingModel(BuildingModel model, GL2 draw,float x, float y, Building building){
		
		Face next;

		draw.glLoadIdentity();

		draw.glTranslatef(x, y, -35); //-35
		draw.glScalef(0.1f, 0.1f, 0.1f);
		draw.glRotatef(45, 1, 0, 0);

		while((next = model.popFace(0,false)) != null){
			
			Colour colour = model.getColour(0,false);
			draw.glColor3fv(FloatBuffer.wrap(colour.getDiffuse()));

			draw.glBegin(draw.GL_POLYGON);

			float[] normal = getNormal(next,model, 0,false);
			draw.glNormal3f(normal[0],normal[1],normal[2]);
			
				for(int i = 0; i < next.getSize(); i++){
					
					Vertex vertex = model.getVertex(next.getFace(i)-1,0,false);
					draw.glVertex3f(vertex.getX(),vertex.getY(),vertex.getZ());
				}
				
				
			draw.glEnd();
			
		}
		
		
	}

	

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
	    draw.glClearColor(0.0f, 1.0f, 0.0f, 0.0f); // set background (clear) color
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
	    
	   
	    draw.glHint(draw.GL_PERSPECTIVE_CORRECTION_HINT, draw.GL_NICEST);


	}
	
	public Texture loadTexture(String file) throws GLException, IOException
	{
	    ByteArrayOutputStream os = new ByteArrayOutputStream();
	    ImageIO.write(ImageIO.read(new File(file)), "png", os);
	    InputStream fis = new ByteArrayInputStream(os.toByteArray());
	    return TextureIO.newTexture(fis, true, TextureIO.PNG);
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
