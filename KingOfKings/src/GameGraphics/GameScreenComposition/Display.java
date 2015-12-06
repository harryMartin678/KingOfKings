package GameGraphics.GameScreenComposition;

import java.io.IOException;
import java.nio.FloatBuffer;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

import Buildings.Names;
import GameGraphics.Building;
import GameGraphics.BuildingModel;
import GameGraphics.ChariotModel;
import GameGraphics.Face;
import GameGraphics.Model;
import GameGraphics.TextModel;
import GameGraphics.Unit;
import GameGraphics.Vertex;
import Map.CollisionMap;
import Map.Map;
import Map.MapList;

public class Display implements IComFrameProcessDisplay,IComDisplayMouseKeyboard {

	private IComUnitListDisplay units;
	private IComBuildingListDisplay buildings;
	private IComMouseKeyboard mouseKeyboard;
	private IComMapDisplay map;
	private DisplayMenus menus;
	
	private final float scaleFactor = 1.0f; 
	private final float WIDTH_CONST = 1.25f;
	private final float HEIGHT_CONST = 3;
	private final int FRAME_Y_SIZE = 25;
	private final int FRAME_X_SIZE = 40;
	
	private int frameX = 0;
	private int frameY = 0;
	
	private float w;
	private float h;
	
	private BuildingModelList buildingModels;
	private UnitModelList unitModels;
	
	public Display(){
		
	}
	
	public void setUpDisplay(IComUnitListDisplay units, IComBuildingListDisplay buildings,
			IComMouseKeyboard mouseKeyboard,IComMapDisplay map,int playerNumber) throws IOException{
		
		this.units = units;
		this.buildings = buildings;
		this.mouseKeyboard = mouseKeyboard;
		this.map = map;
		
		buildingModels = new BuildingModelList(FRAME_X_SIZE/HEIGHT_CONST,FRAME_Y_SIZE/WIDTH_CONST,scaleFactor);
		unitModels = new UnitModelList(FRAME_X_SIZE/HEIGHT_CONST,FRAME_Y_SIZE/WIDTH_CONST,scaleFactor);
		
		menus = new DisplayMenus(FRAME_X_SIZE/HEIGHT_CONST,FRAME_Y_SIZE/WIDTH_CONST,scaleFactor,
				units,buildings,buildingModels,unitModels,map,FRAME_X_SIZE,FRAME_Y_SIZE,playerNumber);
		
		
	}
	
	private void drawWayPoints(GL2 draw){
		
		for(int w = 0; w < units.getNumberOfWayPoints(); w++){
	    	
	    	if(units.getWayPoint(w)[2] == map.getViewedMap()){
		    	Unit flagUn = new Unit(units.getWayPoint(w)[0],units.getWayPoint(w)[1],"flag", 1, 0);
		    	
		    	unitModels.drawFlag(draw, flagUn, frameX, frameY);
	    	}
	    }
	}
	
	private void drawTiles(GL2 draw){
		
		
		//boolean checked = true;
		 //draw tiles for landscape
	    for(int y = frameY; y < frameY+FRAME_Y_SIZE; y++){
	    	for(int x = frameX; x < frameX+FRAME_X_SIZE; x++){
	    	
	    		if(x >= map.getWidth() || y >= map.getHeight() || map.getTile(x,y) == -1 ||
	    				x < 0 || y < 0){
	    			
	    			drawTile(draw,(float) x,(float) y,
	    					0.0f,0.0f,0.0f,FRAME_Y_SIZE/WIDTH_CONST,FRAME_X_SIZE/HEIGHT_CONST);
	    		}else{
	    			
	    			//0.93f,0.68f,0.79f
	    		if(mouseKeyboard.isInDragBox(x,y)){
	    			
	    			drawTile(draw,(float) x,(float) y
    						,0.0f,0.0f,1.0f,FRAME_Y_SIZE/WIDTH_CONST,FRAME_X_SIZE/HEIGHT_CONST);
	    			
	    		}else{
		    		
		    		drawTile(draw,(float) x,(float) y
		    			,0.93f,0.68f,0.79f,FRAME_Y_SIZE/WIDTH_CONST,FRAME_X_SIZE/HEIGHT_CONST);
//	    			
//	    			if(checked){
//		    			drawTile(draw,(float) x,(float) y
//				    			,0.0f,0.0f,0.0f,FRAME_Y_SIZE/WIDTH_CONST,FRAME_X_SIZE/HEIGHT_CONST);
//	    			}else{
//		    					
//		    			drawTile(draw,(float) x,(float) y
//		    					,1.0f,1.0f,1.0f,FRAME_Y_SIZE/WIDTH_CONST,FRAME_X_SIZE/HEIGHT_CONST);
//	    			}
		    			
	    		}
	    			
	    			//checked =! checked;
	    		}
	    	}
	    	
	    	//checked =! checked;
	    }
	}
	
	private void drawMapFeatures(GL2 draw){
		
		//draw landscape features in view 
	    for(int y = frameY; y < frameY+FRAME_Y_SIZE; y++){
	    	for(int x = frameX; x < frameX+FRAME_X_SIZE; x++){
	    		
	    		if(x >= map.getWidth() || y >= map.getHeight() ||
	    				x < 0 || y < 0){
	    			
	    			break;
	    		}
	    		
	    		buildingModels.drawTiles(draw, unitModels, map.getMap(), x, y, frameX, frameY);
	    	}

	    }
	}
	
	private void drawUnits(GL2 draw){
		
		 units.begin();
		    
		    //draw units in view 
		    for(int u = 0; u < units.size(); u++){
		    	
		    	if(units.inFrame(u, frameX, frameY, FRAME_X_SIZE, FRAME_Y_SIZE)
		    			|| map.getTile((int) units.get(u).getX(),(int) units.get(u).getY()) == -1){
		    		
		    		continue;
		    	}
		    	
		    	unitModels.drawUnit(draw, units.get(u), frameX, frameY);
		    	
		    	if(u < units.size() && units.isUnitSelected(units.get(u).getUnitNo())){
		    		
		    		draw.glLoadIdentity();
		    		draw.glTranslatef(units.get(u).getX()-(FRAME_Y_SIZE/WIDTH_CONST)-frameX, 
		    				units.get(u).getY()-(FRAME_X_SIZE/HEIGHT_CONST)-frameY, -34.0f);
		    		draw.glColor3f(0.0f, 0.0f, 1.0f);
		    		draw.glScalef(0.5f, 0.5f, 0.5f);
		    		
		    		
		    		draw.glBegin(draw.GL_LINE_LOOP);
		    			draw.glVertex3f(1.0f, 1.0f, -1.0f); //bottom face
		    			draw.glVertex3f(-1.0f, 1.0f, -1.0f);
		    			draw.glVertex3f(-1.0f, -1.0f, -1.0f);
		    			draw.glVertex3f(1.0f, -1.0f, -1.0f);
		    		draw.glEnd();
		    	}
		    }
		    
		    units.end();
	}
	
	
	private void drawBuildings(GL2 draw){
		
		buildings.begin();
	    
	    
	    if(buildings.isBuildingGhost()){
	    	CollisionMap collMap = new CollisionMap(buildings,units,map.getMap());
	    	//map.printCollisionMap((int)buildingSelection.getX(),(int)buildingSelection.getY());
	    	buildings.canBuildGhost(collMap);
	    	//System.out.println(buildingSelection.cantBuild());
	    	buildings.drawGhostBuilding();
	    }
	    
	    //draw buildings in view
	    for(int b = 0; b < buildings.size(); b++){

	    	if(buildings.inFrame(b, frameX, frameY, FRAME_X_SIZE, FRAME_Y_SIZE)
	    			|| map.getTile((int) buildings.get(b).getX(),(int) buildings.get(b).getY()) == -1){
	    		
	    		continue;
	    	}
	    	
	    	if(buildings.isSelectedBuilding(buildings.get(b))){
	    		
	    		Buildings.Building type = Building.GetBuildingClass(buildings.get(b).getName());
	    		
	    		draw.glLoadIdentity();
	    		draw.glTranslatef(buildings.get(b).getX()-(FRAME_Y_SIZE/WIDTH_CONST)-frameX, 
	    				buildings.get(b).getY()-(FRAME_X_SIZE/HEIGHT_CONST)-frameY, -34.0f);
	    		draw.glColor3f(0.0f, 0.0f, 1.0f);
	    		draw.glScalef(0.5f*type.getSizeX(), 0.5f*type.getSizeY(), 0.5f);
	    		
	    		
	    		draw.glBegin(draw.GL_LINE_LOOP);
	    			draw.glVertex3f(1.0f, 1.0f, -1.0f); //bottom face
	    			draw.glVertex3f(-1.0f, 1.0f, -1.0f);
	    			draw.glVertex3f(-1.0f, -1.0f, -1.0f);
	    			draw.glVertex3f(1.0f, -1.0f, -1.0f);
	    		draw.glEnd();
	    	}
	    	
	    	if(buildings.get(b) != null){
	    		buildingModels.drawBuilding(draw, buildings.get(b), frameX, frameY);
	    	}
	    }
	    buildings.removeGhostBuilding();
	    buildings.end();
	}
	
	public void display(GLAutoDrawable drawable,GLU glu, GLUT glut) {
		
		GL2 draw = drawable.getGL().getGL2();
		draw.glClear(draw.GL_COLOR_BUFFER_BIT | draw.GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
	    draw.glLoadIdentity();  // reset the model-view matrix
	   
	    
	    glu.gluLookAt(0.0f, 0.0f, 10.0f, 
	    		0.0f, 10.0f, 0.0f, 
	    		0.0f, 0.0f, 0.0f);
	    
	    //draw.glDisable(draw.GL_LIGHTING);
	    
	    drawWayPoints(draw);
	    drawMapFeatures(draw);
	    drawTiles(draw);
	    
	    drawUnits(draw);
	    drawBuildings(draw);
	   
	    draw.glDisable(draw.GL_LIGHTING);
	    //draw menus 
	    menus.drawMenus(draw, frameX, frameY);
	    draw.glEnable(draw.GL_LIGHTING);

	    drawable.swapBuffers();
	
	}
	
	public static float[] getNormal(Face next, Model model, int currentFrame,int state){
		
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
	
	public static FloatBuffer getPlayerColour(int player){
		
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
	
	private void drawTile(GL2 draw,float x, float y, float red, float green, float blue,
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
	
	public void setScreenWidth(float width){
		
		w = width;
	}
	
	public void setScreenHeight(float height){
		
		h = height;
	}

	@Override
	public float getScreenWidth() {
		// TODO Auto-generated method stub
		return w;
	}

	@Override
	public float getScreenHeight() {
		// TODO Auto-generated method stub
		return h;
	}

	@Override
	public int getFrameX() {
		// TODO Auto-generated method stub
		return frameX;
	}

	@Override
	public int getFrameY() {
		// TODO Auto-generated method stub
		return frameY;
	}

	@Override
	public void setFrameX(int frameX) {
		// TODO Auto-generated method stub
		this.frameX = frameX;
	}

	@Override
	public void setFrameY(int frameY) {
		// TODO Auto-generated method stub
		this.frameY = frameY;
	}
	
	public int getFrameXSize(){
		
		return FRAME_X_SIZE;
	}
	
	public int getFrameYSize(){
		
		return FRAME_Y_SIZE;
	}

	@Override
	public void moveMap(int[] square) {
		// TODO Auto-generated method stub	
		if(square[1] < (FRAME_Y_SIZE+frameY) && square[1] > (FRAME_Y_SIZE-6+frameY)){
			
			if(square[1] < (FRAME_Y_SIZE+frameY) && square[1] > (FRAME_Y_SIZE-3+frameY)){	
				if(frameY < map.getHeight()-FRAME_Y_SIZE){
					frameY+=2;
				}
			}else{
				
				if(frameY < map.getHeight()-FRAME_Y_SIZE){
					frameY++;
				}
			}
		
		}
		
		if(square[1] >= frameY && square[1] < frameY + 6){
			
			if((square[1] >= frameY && square[1] < frameY + 3)){
				if(frameY > 1){
					frameY-=2;
				}
			}else{
				
				if(frameY > 0){
					frameY--;
				}
			}
		}
		
		if(square[0] < (FRAME_X_SIZE+frameX) && square[0] > (FRAME_X_SIZE-6+frameX)){
			
			if(square[0] < (FRAME_X_SIZE+frameX) && square[0] > (FRAME_X_SIZE-3+frameX)){
				if(frameX < map.getWidth()-FRAME_X_SIZE){
					frameX+=2;
				}
			}else{
				
				if(frameX < map.getWidth()-FRAME_X_SIZE){
					frameX++;
				}
			}
		
		}
		
		if(square[0] >= frameX && square[0] < frameX + 6){
			
			if(square[0] >= frameX && square[0] < frameX + 3){
				if(frameX > 1){
					frameX-=2;
				}
			}else{
				
				if(frameX > 0){
					frameX--;
				}
			}
		}
	}

	@Override
	public int[] getFrameAdjustedPos(int[] pos) {
		// TODO Auto-generated method stub
		return new int[]{pos[0]+frameX,pos[1]+frameY};
	}

	@Override
	public void handleMiniMapSelection(int squareX, int squareY) {
		// TODO Auto-generated method stub
		
		
		if(units.getSelectedUnitSize() > 0){
			
			mouseKeyboard.moveUnit(squareX,squareY);
		
		}else{
		
			frameX = squareX;
			frameY = squareY;
		}
	}
	
	

	
	
}
