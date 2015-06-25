package GameGraphics;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;
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
	private ChariotModel lightChariot;
	private ChariotModel heavyChariot;
	private Model archer;
	private Model heavyarcher;
	private Model batteringRam;
	private Model heavyBatteringRam;
	
	private BuildingModel archeryTower;
	private BuildingModel ballisticTower;
	private BuildingModel barrack;
	private BuildingModel castle;
	private BuildingModel dock;
	private BuildingModel farm;
	private BuildingModel fort;
	private BuildingModel royalPalace;
	private BuildingModel stable;
	private BuildingModel stockpile;
	private BuildingModel wall;
	private BuildingModel mine;
	
	private BuildingModel tree;
	private BuildingModel gold;
	private BuildingModel rock;
	
	private TextModel[] symbols;
	
	private GLU glu;
	private GLUT glut;
	private NumberFormat format;
	private ArrayList<Unit> units;
	private ArrayList<Building> buildings;
	private Map map;
	
	private final float scaleFactor = 1.0f; 
	private final float WIDTH_CONST = 1.25f;
	private final float HEIGHT_CONST = 3;
	private final int FRAME_X_SIZE = 25;
	private final int FRAME_Y_SIZE = 40;
	
	private int frameX = 0;
	private int frameY = 0;
	
	private float w;
	private float h;
	
	private int treeTime = 0;
	
	private MouseEvent mouse;
	private boolean drag = false;
	private boolean dragBox = false;
	private float sx,sy,lx,ly;
	private int[] startDB,lastDB;
	
	public GameScreen(){
		
		
		//load meshes
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
		batteringRam = null;
		heavyBatteringRam = null;
		
		archeryTower = null;
		ballisticTower = null;
		barrack = null;
		dock = null;
		farm = null;
		fort = null;
		royalPalace = null;
		stable = null;
		stockpile = null;
		wall = null;
		mine = null;
		
		tree = null;
		gold = null;
		rock = null;
		
		units = new ArrayList<Unit>();
		buildings = new ArrayList<Building>();
		
		format = NumberFormat.getNumberInstance();
		glut = new GLUT();
		
		symbols = new TextModel[37];
		
		map = new LoadMap("map1").getMap();
		
		try {
			servant = new Model("servant","Models",3,0);
			slave = new Model("slave","Models",3,0);
			axeman = new Model("axeman","Models",3,0);
			swordsman = new Model("swordsman","Models",3,0);
			spearman = new Model("spearman","Models",3,0);
			fishingBoat = new Model("fishingboat","Models",1,1);
			fishingBoat.setSize(0.1f, 0.1f, 0.2f);
			warship = new Model("warship","Models",1,1);
			warship.setSize(0.1f, 0.1f, 0.2f);
			flagship = new Model("flagship","Models",1,1);
			warship.setSize(0.1f, 0.1f, 0.2f);
			lightChariot = new ChariotModel("lightchariot","Models",3);
			lightChariot.setSize(0.15f, 0.15f, 0.2f);
			heavyChariot = new ChariotModel("heavychariot","Models",3);
			heavyChariot.setSize(0.15f, 0.15f, 0.2f);
			archer = new Model("archer","Models",3,0);
			heavyarcher = new Model("heavyarcher","Models",3,0);
			batteringRam = new Model("batteringram","Models",3,0);
			batteringRam.setSize(0.1f, 0.1f, 0.2f);
			heavyBatteringRam = new Model("heavybatteringram","Models",3,0);
			heavyBatteringRam.setSize(0.1f, 0.2f, 0.2f);
			
			archeryTower = new BuildingModel("archerytower","Models",1);
			archeryTower.setSize(0.25f, 0.25f, 0.25f);
			ballisticTower = new BuildingModel("ballisticTower","Models",1);
			ballisticTower.setSize(0.25f, 0.25f, 0.25f);
			barrack = new BuildingModel("barrack","Models",1); //2 2
			barrack.setSize(0.15f, 0.15f, 0.15f);
			castle = new BuildingModel("castle","Models",1);//3 3
			castle.setSize(0.05f, 0.05f, 0.1f);
			dock = new BuildingModel("dock","Models",1);//2 2
			dock.setSize(0.075f, 0.075f, 0.075f);
			farm = new BuildingModel("farm","Models",1);//2 2
			farm.setSize(0.15f, 0.15f, 0.15f);
			fort = new BuildingModel("fort","Models",1);//3 3
			fort.setSize(0.2f, 0.2f, 0.25f);
			royalPalace = new BuildingModel("royalPalace","Models",1);//4 4 
			stable = new BuildingModel("stable","Models",1); //2 2
			stockpile = new BuildingModel("stockpile","Models",1);//2 2
			wall = new BuildingModel("wall","Models",1);//1 1
			wall.setSize(0.15f, 0.15f, 0.1f);
			wall.setAngle(90.0f);
			mine = new BuildingModel("mine","Models",1);//1 1
			mine.setSize(0.15f, 0.15f, 0.15f);
			
			tree = new BuildingModel("tree1","Models",3);
			gold = new BuildingModel("gold","Models",1);
			gold.setSize(0.3f, 0.3f, 0.3f);
			rock = new BuildingModel("rocks","Models",1);
			rock.setSize(0.35f, 0.35f, 0.35f);
			
			String alpha = "abcdefghijklmnopqrstuvwxyz0123456789";
			
			for(int s = 0; s < 36; s ++){
				
				symbols[s] = new TextModel(alpha.charAt(s)+"uc","Models/Alphabet");
			}
			
			symbols[36] = new TextModel("cluc","Models/Alphabet");
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Unit one = new Unit(0,1,"slave",3);
		Unit two = new Unit(0,0,"slave",3);
		Unit three = new Unit(1,0,"slave",3);
		//Unit swordsman = new Unit(23,0,"swordsman",2);
		
		units.add(one);
		units.add(two);
		units.add(three);
		//units.add(swordsman);
		
		Building mine = new Building(10,10,"royalPalace");
		
		buildings.add(mine);
		
		
		//deals with animations 
		Thread animation = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				while(true){
					
					long startTime = System.currentTimeMillis();
					
					for(int i = 0; i < units.size(); i++){
						
						units.get(i).changeCurrentFrame();
					}

					/*
					treeTime ++;
					
					if(treeTime > 4){
						
						treeUn.changeCurrentFrame();
						
						if(treeTime == 8){
							
							treeTime = 0;
						}
					}*/
					
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
	    
	    
	    if(frameX >= 500){
	    	
	    	frameX = 0;
	    
	    }else if(frameY >= 500){
	    	
	    	frameY = 0;
	    }
	    
	    //frameX++;
	    //frameY++;
	    
	    glu.gluLookAt(0.0f, 0.0f, 10.0f, 
	    		0.0f, 10.0f, 0.0f, 
	    		0.0f, 0.0f, 0.0f);
	    
	    //draw.glDisable(draw.GL_LIGHTING);
	    
	    boolean checked = true;

	    //draw tiles for landscape
	    for(int y = frameX; y < frameX+FRAME_X_SIZE; y++){
	    	for(int x = frameY; x < frameY+FRAME_Y_SIZE; x++){
	    	
	    		if(x >= map.getWidth() || y >= map.getHeight() || map.getTile(x,y) == -1 ||
	    				x < 0 || y < 0){
	    			
	    			drawTile(draw,(float) x,(float) y,
	    					0.0f,0.0f,0.0f,FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		}else{
	    			
	    			//0.93f,0.68f,0.79f
	    		if(dragBox &&
	    				lastDB != null && startDB != null 
	    				&&y <= Math.max(lastDB[1],startDB[1]) 
	    				&& y >= Math.min(lastDB[1],startDB[1])
	    				&& x <= Math.max(lastDB[0],startDB[0]) 
	    				&& x >= Math.min(lastDB[0],startDB[0])){
	    			
	    			drawTile(draw,(float) x,(float) y
    						,0.0f,0.0f,1.0f,FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    			
	    		}else{
		    		
		    		drawTile(draw,(float) x,(float) y
		    			,0.93f,0.68f,0.79f,FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
		    			
	    		}
	    			
	    			checked =! checked;
	    		}
	    	}
	    	
	    	checked =! checked;
	    }
	
	    
	    //draw landscape features in view 
	    for(int y = frameX; y < frameX+FRAME_X_SIZE; y++){
	    	for(int x = frameY; x < frameY+FRAME_Y_SIZE; x++){
	    		
	    		if(x >= map.getWidth() || y >= map.getHeight() ||
	    				x < 0 || y < 0){
	    			
	    			break;
	    		}
	    		
	    		if(map.getTile(x, y) == 1){
	    			
	    			Unit treeUn =new Unit((float) x,(float) y,"tree",0);
	    			drawModel(tree,draw,treeUn,FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    		}else if(map.getTile(x, y) == 2){
	    			
	    			Unit rockUn =new Unit((float) x,(float) y,"rock",0);
	    			drawModel(rock,draw,rockUn,FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    		}else if(map.getTile(x, y) == 3){
	    			
	    			Unit goldUn =new Unit((float) x,(float) y,"gold",0);
	    			drawModel(gold,draw,goldUn,FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    		}else if(map.getTile(x,y) == 4){
	    			
	    			//transition node
	    		
	    		}else if(map.getTile(x, y) == 5){
	    			
	    			//0.11f,0.42f,0.63f

	    			drawTile(draw,(float) x,(float) y
	    					,0.11f,0.42f,0.63f,FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		}	
	    	}

	    }
	    
	    //draw units in view 
	    for(int u = 0; u < units.size(); u++){
	    	
	    	if(!(units.get(u).getX() >= frameX 
	    			&& units.get(u).getX() < (frameX + FRAME_X_SIZE)
	    			&& units.get(u).getY() >= frameY 
	    			&& units.get(u).getY() < (frameY + FRAME_Y_SIZE)
	    			|| map.getTile((int) units.get(u).getX(),(int) units.get(u).getY()) == -1)){
	    		
	    		continue;
	    	}
	    	
	    	if(units.get(u).getUnitType().equals("servant")){
	    		
	    		drawModel(servant,draw,units.get(u),FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(units.get(u).getUnitType().equals("slave")){
	    		
	    		drawModel(slave,draw,units.get(u),FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(units.get(u).getUnitType().equals("axeman")){
	    		
	    		drawModel(axeman,draw,units.get(u),FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(units.get(u).getUnitType().equals("swordsman")){
	    		
	    		drawModel(swordsman,draw,units.get(u),FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(units.get(u).getUnitType().equals("spearman")){
	    		
	    		drawModel(spearman,draw,units.get(u),FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(units.get(u).getUnitType().equals("archer")){
	    		
	    		drawModel(archer,draw,units.get(u),FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(units.get(u).getUnitType().equals("heavyarcher")){
	    		
	    		drawModel(heavyarcher,draw,units.get(u),FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(units.get(u).getUnitType().equals("batteringram")){
	    		
	    		drawModel(batteringRam,draw,units.get(u),FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(units.get(u).getUnitType().equals("heavybatteringram")){
	    		
	    		drawModel(heavyBatteringRam,draw,units.get(u),FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(units.get(u).getUnitType().equals("lightchariot")){
	    		
	    		drawModel(lightChariot,draw,units.get(u),FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(units.get(u).getUnitType().equals("heavychariot")){
	    		
	    		drawModel(heavyChariot,draw,units.get(u),FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(units.get(u).getUnitType().equals("fishingboat")){
	    		
	    		drawModel(fishingBoat,draw,units.get(u),FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(units.get(u).getUnitType().equals("warship")){
	    		
	    		drawModel(warship,draw,units.get(u),FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(units.get(u).getUnitType().equals("flagship")){
	    		
	    		drawModel(flagship,draw,units.get(u),FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    	}
	    }
	    
	    //draw buildings in view
	    for(int b = 0; b < buildings.size(); b++){
	    	
	    	if(!(buildings.get(b).getX() >= frameX 
	    			&& buildings.get(b).getX() < (frameX + FRAME_X_SIZE)
	    			&& buildings.get(b).getY() >= frameY 
	    			&& buildings.get(b).getY() < (frameY + FRAME_Y_SIZE)
	    			|| map.getTile((int) buildings.get(b).getX(),(int) buildings.get(b).getY()) == -1)){
	    		
	    		continue;
	    	}
	    	
	    	if(buildings.get(b).getName().equals("archerytower")){
	    		
	    		drawBuildingModel(archeryTower,draw,buildings.get(b),
	    				FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(buildings.get(b).getName().equals("ballisticTower")){
	    		
	    		drawBuildingModel(ballisticTower,draw,buildings.get(b),
	    				FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(buildings.get(b).getName().equals("barrack")){
	    		
	    		drawBuildingModel(barrack,draw,buildings.get(b),
	    				FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(buildings.get(b).getName().equals("castle")){
	    		
	    		drawBuildingModel(castle,draw,buildings.get(b),
	    				FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(buildings.get(b).getName().equals("dock")){
	    		
	    		drawBuildingModel(dock,draw,buildings.get(b),
	    				FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(buildings.get(b).getName().equals("farm")){
	    		
	    		drawBuildingModel(farm,draw,buildings.get(b),
	    				FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(buildings.get(b).getName().equals("fort")){
	    		
	    		drawBuildingModel(fort,draw,buildings.get(b),
	    				FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(buildings.get(b).getName().equals("royalPalace")){
	    		
	    		drawBuildingModel(royalPalace,draw,buildings.get(b),
	    				FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(buildings.get(b).getName().equals("stable")){
	    		
	    		drawBuildingModel(stable,draw,buildings.get(b),
	    				FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(buildings.get(b).getName().equals("stockpile")){
	    		
	    		drawBuildingModel(stockpile,draw,buildings.get(b),
	    				FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(buildings.get(b).getName().equals("wall")){
	    		
	    		drawBuildingModel(wall,draw,buildings.get(b),
	    				FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    		
	    	}else if(buildings.get(b).getName().equals("mine")){
	    		
	    		drawBuildingModel(mine,draw,buildings.get(b),
	    				FRAME_X_SIZE/WIDTH_CONST,FRAME_Y_SIZE/HEIGHT_CONST);
	    	}
	    }
	    
	    //deal with mouse input 
	    regulateMouse(draw);

	    draw.glDisable(draw.GL_LIGHTING);
	    //draw menus 
	    drawMenus(draw);
	    draw.glEnable(draw.GL_LIGHTING);

	    drawable.swapBuffers();
	
	}
	
	private void regulateMouse(GL2 gl){
		
		int viewport[] = new int[4];
	    double mvmatrix[] = new double[16];
	    double projmatrix[] = new double[16];
	    int realy = 0;// GL y coord pos
	    double wcoord[] = new double[4];// wx, wy, wz;// returned xyz coords
	    double lcoord[] = new double[4];
	    //http://www.java-tips.org/other-api-tips-100035/112-jogl/1628-how-to-use-gluunproject-in-jogl.html
	    if (mouse != null)
	    {
	      
	      if(mouse.getButton() == MouseEvent.BUTTON1){
	    	  
	    	  int x = mouse.getX(), y = mouse.getY();
		      
	    	  //get world coordinates 
		      gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
	          gl.glGetDoublev(gl.GL_MODELVIEW_MATRIX, mvmatrix, 0);
	          gl.glGetDoublev(gl.GL_PROJECTION_MATRIX, projmatrix, 0);
	          /* note viewport[3] is height of window in pixels */
	          realy = viewport[3] - (int) y - 1;
	          glu.gluUnProject((double) x, (double) realy, 35.0, //
	              mvmatrix, 0,
	              projmatrix, 0, 
	              viewport, 0, 
	              wcoord, 0);
	
		      // System.out.println(wcoord[0] + " " + wcoord[1] + " " + wcoord[2]);
		      if(!selectMenu(wcoord)){
		        	
		    	  selectMap(wcoord);
		       }
	    	   mouse = null;
	      }
	      
	      
	    }
	    
	    //deal with dragging the mouse 
	    if(drag){
	    	
	    	gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
	          gl.glGetDoublev(gl.GL_MODELVIEW_MATRIX, mvmatrix, 0);
	          gl.glGetDoublev(gl.GL_PROJECTION_MATRIX, projmatrix, 0);
	          /* note viewport[3] is height of window in pixels */
	          realy = viewport[3] - (int) sy - 1;
	          glu.gluUnProject((double) sx, (double) realy, 35.0, //
	              mvmatrix, 0,
	              projmatrix, 0, 
	              viewport, 0, 
	              wcoord, 0);
	          
	          gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0);
	          gl.glGetDoublev(gl.GL_MODELVIEW_MATRIX, mvmatrix, 0);
	          gl.glGetDoublev(gl.GL_PROJECTION_MATRIX, projmatrix, 0);
	          /* note viewport[3] is height of window in pixels */
	          realy = viewport[3] - (int) ly - 1;
	          glu.gluUnProject((double) lx, (double) realy, 35.0, //
	              mvmatrix, 0,
	              projmatrix, 0, 
	              viewport, 0, 
	              lcoord, 0);
	    	
	    	startDB = selectMap(wcoord);
	    	lastDB = selectMap(lcoord);
	    	
	    	drag = false;
	    }
	}

	
	private boolean selectMenu(double[] coords){
		
		//quit button
		if(coords[0] >= 49.9923 && coords[0] <= 49.9949
				&& coords[2] <= -16.6606 && coords[2] >= -16.6617){
			
			System.out.println("Quit");
			return true;
		
		//pause button
		}else if(coords[0] >= 49.9986 && coords[0] <= 50.0013
			&& coords[2] <= -16.6606 && coords[2] >= -16.6617){
		
			System.out.println("Pause");
			return true;
		
		//save button
		}else if(coords[0] >= 50.005801 && coords[0] <= 50.008309
				&& coords[2] <= -16.6606 && coords[2] >= -16.6617){
			
			System.out.println("Save");
			return true;
		}
		
		return false;
		
	}
	
	private int[] selectMap(double[] coords){

		//check with square has been selected 
		for(int y = 0; y < 25; y++){
			for(int x = 0; x < 40; x++){
				if(coords[0] >= 50.00810241699 - (0.00041198731*x) 
						&& coords[0] <= 50.00851821899 - (0.000396728115*x)
						&& coords[2] >= -16.672410964965 + (0.0003967285156*y)
						&& coords[2] <= -16.67197799682 + (0.0004215240381*y)){
				
					return new int[]{x,y};
				}
			}
		}
		
		return new int[]{-1,-1};
	}
	
	private void drawMenus(GL2 draw){
		
		drawLeftPanel(draw);
		drawRightPanel(draw);
		drawTopPanel(draw);
		drawBottomFill(draw);
	}
	
	private void drawLeftPanel(GL2 draw){
		
		//back panel
		drawMenuQuad(draw,-16.0f,0.0f,-20.0f,0.65f,0.5f,0.39f,2.7f,3.0f,10.0f);
	
		//top panel
		drawMenuQuad(draw,-15.25f,4.0f, -19.0f,0.93f, 0.37f, 0.0f,2.0f, 3.0f, 3.0f);
		
		//bottom panel
		drawMenuQuad(draw,-15.25f,-4.5f, -19.0f,0.93f, 0.37f, 0.0f,2.0f, 3.0f, 3.0f);
		
		
	}
	
	private void drawRightPanel(GL2 draw){
		
		//back panel
		drawMenuQuad(draw,16.0f,0.0f,-20.0f,0.65f,0.5f,0.39f,3.5f,3.0f,10.0f);
		
		//top panel
		drawMenuQuad(draw,14.5f,4.0f, -19.0f,0.93f, 0.37f, 0.0f,2.0f, 3.0f, 3.0f);
		
		drawMiniMap(draw,13.5f,-4.25f,-18.0f);
		//bottom panel
		drawMenuQuad(draw,14.5f,-4.5f, -19.0f,0.93f, 0.37f, 0.0f,2.0f, 3.0f, 3.0f);
		
	}
	
	private void drawMiniMap(GL2 draw, float x, float y, float z){

		drawMenuQuad(draw,x,y,z,0.85f,0.82f,0.55f,1.5f,2.5f,2.5f);
		
		//square on mini-map
		draw.glLoadIdentity();
		draw.glTranslatef((x-2.6f) + (frameX*2.55f)/map.getWidth()
				, (y-1.5f) + (4.35f*frameY)/map.getHeight(), z);
		draw.glRotatef(90.0f, 1, 0, 0);
		draw.glColor3f(0.0f, 0.0f, 0.0f);
		draw.glScalef((FRAME_X_SIZE*2.55f)/map.getWidth(), 
				(FRAME_Y_SIZE*4.35f)/map.getHeight(), (FRAME_Y_SIZE*4.35f)/map.getHeight());
		
		draw.glBegin(draw.GL_LINE_LOOP);
			draw.glVertex3f(1.0f, -1.0f, 1.0f);
			draw.glVertex3f(-1.0f, -1.0f, 1.0f);
			draw.glVertex3f(-1.0f, -1.0f, -1.0f);
			draw.glVertex3f(1.0f, -1.0f, -1.0f);
		draw.glEnd();
		
		//draw units 
		for(int u = 0; u < units.size(); u++){
			
			if(map.getTile((int) units.get(u).getX(), (int) units.get(u).getY()) == -1){
				
				continue;
			}
			
			drawMenuQuad(draw,(x-2.85f) + (2.55f*units.get(u).getX())/map.getWidth()
					,(y-1.70f) + (4.35f*units.get(u).getY())/map.getHeight() ,z,1.0f,0.0f
					,0.0f,30.0f/(2*map.getWidth())
					,30.0f/(2*map.getHeight()), 30.0f/(2*map.getHeight()));
		}
		
		//draw buildings 
		for(int b = 0; b < buildings.size(); b++){
			
			if(map.getTile((int) buildings.get(b).getX(), (int) buildings.get(b).getY()) == -1){
				
				continue;
			}
			
			if(buildings.get(b).getName().equals("royalPalace")){
				
				drawMenuQuad(draw,(x-2.85f) + (2.55f*buildings.get(b).getX())/map.getWidth()
						,(y-1.70f) + (4.35f*buildings.get(b).getY())/map.getHeight() ,z,0.58f
						,0.13f,0.60f,40.0f/(2*map.getWidth()),40.0f/(2*map.getHeight()), 
						40.0f/(2*map.getHeight()));
				
			}else{
			
				drawMenuQuad(draw,(x-2.85f) + (2.55f*buildings.get(b).getX())/map.getWidth()
						,(y-1.70f) + (4.35f*buildings.get(b).getY())/map.getHeight() ,z,0.5f
						,0.5f,0.5f,30.0f/(2*map.getWidth()),30.0f/(2*map.getHeight()), 
						30.0f/(2*map.getHeight()));
			}
		}
		
		//draw the gold on the mini map
		for(float xs = 0; xs < map.getWidth(); xs++){
			for(float ys = 0; ys < map.getHeight(); ys++){
				
				if(map.getTile((int) xs, (int) ys) == 3){
					
					drawMenuQuad(draw,(x-2.85f) + (2.55f*xs)/map.getWidth()
							,(y-1.70f) + (4.35f*ys)/map.getHeight() ,z,0.85f
							,0.65f,0.13f,20.0f/(2*map.getWidth()),20.0f/(2*map.getHeight()), 
							20.0f/(2*map.getHeight()));
				}
			}
		}
	}
	
	private void drawTopPanel(GL2 draw){
		
		//top panel
		drawMenuQuad(draw,0.0f,8.5f, -20.0f,0.65f, 0.5f, 0.39f,15.0f, 3.0f, 1.15f);
		
		drawString(draw,-1.5f,7.75f,-21.0f,0.0f,0.0f,0.0f,0.75f,"pause");
		//pause game button
		drawMenuQuad(draw,0.0f,8.5f, -20.0f,0.93f, 0.37f, 0.0f,2.0f, 3.0f, 0.75f);
		
		drawString(draw,-11.25f,7.75f, -21.0f,0.0f,0.0f,0.0f,0.75f,"save");
		//save game button
		drawMenuQuad(draw,-11.0f,8.5f, -20.0f,0.93f, 0.37f, 0.0f,2.0f, 3.0f, 0.75f);
		
		drawString(draw,8.0f,7.75f, -21.0f,0.0f,0.0f,0.0f,0.75f,"quit");
		//quit game button
		drawMenuQuad(draw,10.0f,8.5f, -20.0f,0.93f, 0.37f, 0.0f,2.0f, 3.0f, 0.75f);
		
	}

	
	private void drawBottomFill(GL2 draw){
		
		drawMenuQuad(draw,0.0f,-9.5f, -20.0f,0.65f, 0.5f, 0.39f,15.0f, 3.0f, 0.5f);

	}
	
	
	//draws a string using the letter models 
	private void drawString(GL2 draw, float x, float y,float z,float r, 
			float g, float b,float fontSize, String msg){
		
		for(int s = 0; s < msg.length(); s++){
			
			for(int i = 97; i <= 122; i++){
				if(msg.charAt(s) == i){
					drawText(draw,symbols[(i-97)],
							x+(((float) s)*fontSize),y,z,r,g,b,fontSize);
				}
			}
			
			for(int i = 48; i <= 57; i++){
				
				if(msg.charAt(s) == i){
					drawText(draw,symbols[(i-22)],
							x+(((float) s)*fontSize),y,z,r,g,b,fontSize);
				}
			}
		}
	}
	
	private void drawText(GL2 draw, TextModel model, float x, float y,float z
			,float r, float g, float b,float fontSize){
		
		Face next;

		draw.glLoadIdentity();

		draw.glTranslatef(x, y, z); 
		draw.glScalef(model.sizeX()*scaleFactor*fontSize
				, model.sizeY()*scaleFactor*fontSize, 
				model.sizeZ()*scaleFactor*fontSize);
		draw.glRotatef(45.0f, 1, 0, 0);

		while((next = model.popFace(0,0)) != null){
			
			draw.glColor3f(r,g,b);

			draw.glBegin(draw.GL_POLYGON);

			
				for(int i = 0; i < next.getSize(); i++){
					
					Vertex vertex = model.getVertex(next.getFace(i)-1,0,0);
					draw.glVertex3f(vertex.getX(),vertex.getY(),vertex.getZ());
				}
				
				
			draw.glEnd();
			
		}
	}
	
	
	private void drawMenuQuad(GL2 draw, float tx, float ty, float tz, float cx, float cy, float cz,
			float sx, float sy, float sz){
		
		draw.glLoadIdentity();
		
		draw.glTranslatef(tx,ty, tz);
		draw.glColor3f(cx, cy, cz);
		draw.glRotatef(90.0f, 1, 0, 0);
		draw.glScalef(sx, sy, sz);
		
		draw.glNormal3f(0.0f, 0.0f, 0.0f);
		draw.glBegin(draw.GL_QUADS);
			draw.glVertex3f(1.0f, -1.0f, 1.0f);
			draw.glVertex3f(-1.0f, -1.0f, 1.0f);
			draw.glVertex3f(-1.0f, -1.0f, -1.0f);
			draw.glVertex3f(1.0f, -1.0f, -1.0f);
		draw.glEnd();
	}
	

	public void drawTile(GL2 draw,float x, float y, float red, float green, float blue,
			float width, float height){
		
		draw.glLoadIdentity();
		
		draw.glTranslatef(x-width-frameX, y-height-frameY, -35f);
		draw.glScalef(0.5f, 0.5f, 0.5f);
		draw.glRotatef(90.0f, 1, 0, 0);
		draw.glColor3f(red, green, blue);
		
		draw.glNormal3f(0.0f, 1.0f, 0.0f);
		draw.glBegin(draw.GL_QUADS);
			draw.glVertex3f(1.0f, -1.0f, 1.0f);
			draw.glVertex3f(-1.0f, -1.0f, 1.0f);
			draw.glVertex3f(-1.0f, -1.0f, -1.0f);
			draw.glVertex3f(1.0f, -1.0f, -1.0f);
		draw.glEnd();
		
	}
	
	public void drawModel(Model model, GL2 draw, Unit unit,float width, float height){
		
		Face next;

		draw.glLoadIdentity();

		//move the unit in relation to the width and height of the map, and the frame position
		draw.glTranslatef(unit.getX()-width-frameX, unit.getY()-height-frameY, -35); //-35
		//scales the model's size
		draw.glScalef(model.sizeX()*scaleFactor, model.sizeY()*scaleFactor, model.sizeZ()*scaleFactor);
		draw.glRotatef(45, 1, 0, 0);
		
		//gets the frame and state of a unit 
		int currentFrame = unit.getCurrentFrame();
		int state = unit.getState();

		while((next = model.popFace(currentFrame,state)) != null){
			
			//get the colour of the face
			Colour colour = model.getColour(currentFrame,state);
			
			float[] check = colour.getDiffuse();
			//if a indicate colour is since then substitute the player's colour 
			if(check[0] == 0.098400f && check[1] == 0.098400f && check[2] == 0.098400f){
				
				draw.glColor3fv(getPlayerColour(unit.getPlayer()));
				
			}else{
				draw.glColor3fv(FloatBuffer.wrap(colour.getDiffuse()));
			}

			draw.glBegin(draw.GL_POLYGON);

				//get the face's normal [
				float[] normal = getNormal(next,model, currentFrame,state);
				draw.glNormal3f(normal[0],normal[1],normal[2]);
				
				for(int i = 0; i < next.getSize(); i++){
					
					Vertex vertex = model.getVertex(next.getFace(i)-1,currentFrame,state);
					draw.glVertex3f(vertex.getX(),vertex.getY(),vertex.getZ());
				}
				
				
			draw.glEnd();
			
		}
		
		
	}
	
	//get the colour of the player
	private FloatBuffer getPlayerColour(int player){
		
		if(player == 1){
			
			return FloatBuffer.wrap(new float[]{0.0f,0.0f,1.0f});
		
		}else if(player == 2){
			
			return FloatBuffer.wrap(new float[]{1.0f,0.0f,0.0f});
		
		}else if(player == 3){
			
			return FloatBuffer.wrap(new float[]{0.0f,1.0f,0.0f});
		}else if(player == 4){
			
			return FloatBuffer.wrap(new float[]{1.0f,1.0f,1.0f});
		
		}else if(player == 5){
			
			return FloatBuffer.wrap(new float[]{1.0f,1.0f,0.0f});
		
		}else if(player == 6){
			
			return FloatBuffer.wrap(new float[]{0.0f,1.0f,1.0f});
		}else if(player == 7){
			
			return FloatBuffer.wrap(new float[]{1.0f,0.0f,1.0f});
		
		}

		return FloatBuffer.wrap(new float[]{0.0f,0.0f,0.0f});
	}
	
	public void drawBuildingModel(BuildingModel model, GL2 draw, Building building
			,float width, float height){
		
		Face next;

		draw.glLoadIdentity();

		draw.glTranslatef(building.getX()-width-frameX, building.getY()-height-frameY, -35); //-35
		draw.glScalef(model.sizeX()*scaleFactor, model.sizeY()*scaleFactor, model.sizeZ()*scaleFactor);
		draw.glRotatef(90.0f, 1, 0, 0);
		draw.glRotatef(model.getAngle(), 0, 1, 0);

		while((next = model.popFace(0,0)) != null){
			
			Colour colour = model.getColour(0,0);
			draw.glColor3fv(FloatBuffer.wrap(colour.getDiffuse()));

			draw.glBegin(draw.GL_POLYGON);

			float[] normal = getNormal(next,model, 0,0);
			draw.glNormal3f(normal[0],normal[1],normal[2]);
			
				for(int i = 0; i < next.getSize(); i++){
					
					Vertex vertex = model.getVertex(next.getFace(i)-1,0,0);
					draw.glVertex3f(vertex.getX(),vertex.getY(),vertex.getZ());
				}
				
				
			draw.glEnd();
			
		}
		
		
	}

	
	//calculates normals 
	private float[] getNormal(Face next, Model model, int currentFrame,int state){
		
		float[] normal = new float[]{0.0f,0.0f,0.0f};
		
		Vertex lastVertex = model.getVertex(next.getFace(0)-1, currentFrame,state);
		
		for(int i = 0; i < next.getSize()-1; i++){
			
			Vertex vertex = model.getVertex(next.getFace(i+1)-1, currentFrame,state);
			normal[0] += (lastVertex.getY() - vertex.getY()) * (lastVertex.getZ() + vertex.getZ());
			normal[1] += (lastVertex.getZ() - vertex.getZ()) * (lastVertex.getX() + vertex.getX());
			normal[2] += (lastVertex.getX() - vertex.getX()) * (lastVertex.getY() + vertex.getY());
			lastVertex = vertex;
			
		}
		
		Vertex vertex = model.getVertex(next.getFace(0)-1, currentFrame,state);
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

	   
	    draw.glHint(draw.GL_PERSPECTIVE_CORRECTION_HINT, draw.GL_NICEST);


	}
	
	public MouseMotionListener getMouseMotionListener(){
		
		return new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
				if(!dragBox){
					
					sx = e.getX();
					sy = e.getY();
				}else{
					
					lx = e.getX();
					ly = e.getY();
				}
				drag = true;
				dragBox = true;
				mouse = e;
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
		};
	}
	
	public MouseListener getMouseListener(){
		
		return new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

				mouse = e;
				dragBox = false;
				
				//to remove a left over drag box 
				startDB = null;
				lastDB = null;
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
			
			
		};
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
	      
	      w = width;
	      h = height;
	 
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
