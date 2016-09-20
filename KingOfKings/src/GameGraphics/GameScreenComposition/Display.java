package GameGraphics.GameScreenComposition;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import GameGraphics.Building;
import GameGraphics.Face;
import GameGraphics.IBoundingBoxes;
import GameGraphics.Model;
import GameGraphics.Unit;
import GameGraphics.Vertex;
import GameGraphics.Menu.DrawMenuModel;
import GameGraphics.Menu.Menu;
import GameGraphics.Menu.UnitIconSelection;
import Map.CollisionMap;
import Map.GraphicsCollisionMap;

public class Display implements IComFrameProcessDisplay,IComDisplayMouseKeyboard,IComMapUpdateDisplayFrame {

	private IComUnitListDisplay units;
	private IComBuildingListDisplay buildings;
	private IComMouseKeyboard mouseKeyboard;
	private IComMapDisplay map;
	private DrawMenuModel menuModels;
	//private DisplayMenus menus;
	private TextureRepo textures;
	private int playerNumber;
	
	private final float scaleFactor = 1.0f; 
	public static final float WIDTH_CONST = 27.97f;//20.3f
	public static final float HEIGHT_CONST = 7.85f;//14.21f
	private final int FRAME_Y_SIZE = 22;//28
	private final int FRAME_X_SIZE = 57;//40
	
	private int frameX = 0;
	private int frameY = 0;
	
	private int delayedFrameX = 0;
	private int delayedFrameY = 0;
	
	public static final float[][] LookAtMatrix = new float[][]{{0.0f, 0.0f, 10.0f}, 
    		{0.0f, 1.0f, 0.0f}, 
    		{0.0f, 0.0f, 0.0f}};
    		
    public static float[] modelview;
    public static float[] projection;
    public static int[] viewport;
	
	private float w;
	private float h;
	
	private BuildingModelList buildingModels;
	private UnitModelList unitModels;
	
	public enum TileState{
		
		None,
		Selected,
		Empty,
		White,
		Black,
		Center
	}
	
	//private ButtonList buttons;
	
	//private HoverPanelGraphic hoverPanel;
	
	private int food;
	private int gold;
	private Menu menu;

	
	public Display(Menu menu){
		
		textures = new TextureRepo();
		//buttons = new ButtonList();
		
		this.menu = menu;
		
		
		//System.out.println(WIDTH_CONST + " " + HEIGHT_CONST + " DISPLAY");
	}
	
	public void setUpDisplay(IComUnitListDisplay units, IComBuildingListDisplay buildings,
			IComMouseKeyboard mouseKeyboard,IComMapDisplay map,int playerNumber) throws IOException{
		
		this.units = units;
		this.buildings = buildings;
		this.mouseKeyboard = mouseKeyboard;
		this.map = map;
		
		buildingModels = new BuildingModelList(HEIGHT_CONST,WIDTH_CONST,scaleFactor
				,textures);
		unitModels = new UnitModelList(HEIGHT_CONST,WIDTH_CONST,scaleFactor,
				textures);
		
		this.playerNumber = playerNumber;
		menuModels = new DrawMenuModel(units, buildings, buildingModels, unitModels);
		
		//System.out.println((this.map.getMap() == null) + " Display");
		
		
	//	menus = new DisplayMenus(scaleFactor,units,buildings,buildingModels,unitModels,map,
			//	FRAME_X_SIZE,FRAME_Y_SIZE,playerNumber,buttons);
		//mouseKeyboard.setButtonList(buttons);
		//buildingModels.SetUpBuildingModelList(menus);
		///unitModels.SetUpUnitModels(menus);
		
	}
	
	
	private void drawWayPoints(GL2 draw){
		
		for(int w = 0; w < units.getNumberOfWayPoints(); w++){
	    	
	    	if(units.getWayPoint(w)[2] == map.getViewedMap()){
		    	Unit flagUn = new Unit(units.getWayPoint(w)[0],units.getWayPoint(w)[1],"flag", 1, 0);
		    	
		    	unitModels.drawFlag(draw, flagUn, frameX, frameY);
	    	}
	    }
	}
	
	public IBoundingBoxes getUnitBBoxes(){
		
		return unitModels;
	}
	
	public IBoundingBoxes getBuildingBBoxes() {
		// TODO Auto-generated method stub
		return buildingModels;
	}
	
	private void drawTiles(GL2 draw){
		
		
		boolean checked = true;
		 //draw tiles for landscape
	    for(int y = frameY; y < frameY+FRAME_Y_SIZE; y++){
	    	for(int x = frameX; x < frameX+FRAME_X_SIZE; x++){
	    	
	    		if(x >= map.getWidth() || y >= map.getHeight() || map.getTile(x,y) == -1 ||
	    				x < 0 || y < 0){
	    			
	    			drawTile(draw,(float) x,(float) y,
	    					TileState.Empty,WIDTH_CONST,HEIGHT_CONST);
	    		}
	    		//}else{
	    			
	    			//0.93f,0.68f,0.79f
	    		else if(mouseKeyboard.isInDragBox(x,y)){
	    			
	    			drawTile(draw,(float) x,(float) y
    						,TileState.Selected,WIDTH_CONST,HEIGHT_CONST);
	    			
	    		}else{
		    		//0.93f,0.68f,0.79f
	    			
	    			if(GraphicsCollisionMap.getTile(x, y) == 0){
	    			
		    			drawTile(draw,(float) x,(float) y,TileState.None,WIDTH_CONST,HEIGHT_CONST);
		    			
	    			}else{
	    				
	    				drawTile(draw,(float) x,(float) y,TileState.Center,WIDTH_CONST,HEIGHT_CONST);
	    			}
//	    			
//	    			if(x == map.getWidth()/2 && y == map.getHeight()/2){
//	    				
//	    				drawTile(draw,(float)x,(float)y,
//	    						TileState.Center,WIDTH_CONST,HEIGHT_CONST);
//	    			}else{
//	    			
//		    			if(checked){
//			    			drawTile(draw,(float) x,(float) y
//					    			,TileState.Black,WIDTH_CONST,HEIGHT_CONST);
//		    			}else{
//			    					
//			    			drawTile(draw,(float) x,(float) y
//			    					,TileState.White,WIDTH_CONST,HEIGHT_CONST);
		    			}
	    			}
//		    			
	    			//}
	    			
	    			//checked =! checked;
	    		}
//	    	}
	    	
	    	//checked =! checked;
	  //  }
	}
	
	private void drawMapFeatures(GL2 draw){
		
		//draw landscape features in view 
	    for(int y = frameY; y < frameY+FRAME_Y_SIZE; y++){
	    	for(int x = frameX; x < frameX+FRAME_X_SIZE; x++){
	    		
	    		if(x >= map.getWidth() || y >= map.getHeight() ||
	    				x < 0 || y < 0){
	    			
	    			break;
	    		}
	    		
	    		buildingModels.drawTiles(draw, unitModels, map.getMap(), x,y, frameX, frameY);
	    	}

	    }
	}
	
	private void drawUnits(GL2 draw){
		
		units.begin();
		    //draw units in view 
	    for(int u = 0; u < units.size(); u++){
	    	
	    	if(units.outOfFrame(u, frameX, frameY, FRAME_X_SIZE, FRAME_Y_SIZE)
	    			|| map.getTile((int) units.get(u).getX(),(int) units.get(u).getY()) == -1){
	    		
	    		continue;
	    	}
	    	
	    	
	    	unitModels.drawUnit(draw, units.get(u),frameX, frameY,
	    			units.getUnitByUnitNo(units.get(u).getAttacking()));
	    	unitModels.drawArrows(draw, frameX, frameY);
	    	
	    	if(u < units.size() && units.isUnitSelected(units.get(u).getUnitNo())){
	    		
	    		draw.glLoadIdentity();
	    		draw.glTranslatef(units.get(u).getX()-(WIDTH_CONST)-frameX, 
	    				units.get(u).getY()-(HEIGHT_CONST)-frameY, -34.0f);
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
	    unitModels.progressArrowAnim();
	    units.end();
	}
	
	
	private void drawBuildings(GL2 draw){
		
		buildings.begin();
	    
	    if(buildings.isBuildingGhost()){
	    	//System.out.println(buildings.isBuildingGhost() + " before coll map display");
	    	//CollisionMap collMap = new CollisionMap(buildings,units,map.getMap(),map.getViewedMap());
	    	//System.out.println(buildings.isBuildingGhost() + " after coll map display");
	    	//map.printCollisionMap((int)buildingSelection.getX(),(int)buildingSelection.getY());
	    	buildings.canBuildThere(playerNumber);
	    	buildings.canBuildGhost();
	    	buildings.moveGhostBuildingGraphics();
	    	//System.out.println(buildingSelection.cantBuild());
	    	//buildings.drawGhostBuilding();
	    	buildingModels.drawBuilding(draw, buildings.getGhostBuilding(), frameX, frameY);
	    }
	    
	    //draw buildings in view
	    for(int b = 0; b < buildings.size(); b++){

	    	if( buildings.inFrame(b, frameX, frameY, FRAME_X_SIZE, FRAME_Y_SIZE) 
	    			|| map.getTile((int) buildings.get(b).getX(),(int) buildings.get(b).getY()) == -1){
	    		
	    		continue;
	    	}
	    	
	    	if(buildings.isSelectedBuilding(buildings.get(b))){
	    		
	    		Buildings.Building type = Building.GetBuildingClass(buildings.get(b).getName());
	    		draw.glLoadIdentity();
	    		draw.glTranslatef(buildings.get(b).getX()-(WIDTH_CONST)-frameX, 
	    				buildings.get(b).getY()-(HEIGHT_CONST)-frameY, -34.9f);
	    		draw.glColor3f(0.0f, 0.0f, 1.0f);
	    		draw.glScalef(type.getSizeX(),type.getSizeY(), 1.0f);
	    		
	    		
	    		draw.glBegin(draw.GL_LINE_LOOP);
	    			draw.glVertex3f(1.0f, 1.0f, 0.0f); //bottom face
	    			draw.glVertex3f(-1.0f, 1.0f, 0.0f);
	    			draw.glVertex3f(-1.0f, -1.0f, 0.0f);
	    			draw.glVertex3f(1.0f, -1.0f, 0.0f);
	    		draw.glEnd();
	    	}
	    	
	    	if(buildings.get(b).IsTowerAttack()){
	    		
	    		unitModels.towerBattle((int)buildings.getBuildingX(b),(int)buildings.getBuildingY(b),
	    				buildings.get(b).getAttackX(),buildings.get(b).getAttackY());
	    	}
	    	
	    	//if(buildings.get(b) != null){
    		buildingModels.drawBuilding(draw, buildings.get(b), frameX, frameY);
	    	//}
	    }
	   // buildings.removeGhostBuilding();
	    buildings.end();
	}
	
	//to get textures working
//	public void showSquare(GL2 draw,int texture){
//		
//		
//		
//		draw.glColor3f(1.0f,1.0f,1.0f);
//		draw.glBindTexture(GL2.GL_TEXTURE_2D, texture);
//		//draw.glTranslatef(0.0f, 0.0f, -15.0f);
//		draw.glScalef(5.0f, 5.0f, 1.0f);
//		draw.glRotatef(90, 1, 0, 0);
//
//		draw.glBegin(GL2.GL_QUADS);
//			
//        	draw.glTexCoord2f(1.0f, 1.0f); draw.glVertex3f(-1.0f, -1.0f, -1.0f);  // Top Right Of The Texture and Quad
//        	draw.glTexCoord2f(0.0f, 1.0f); draw.glVertex3f( 1.0f, -1.0f, -1.0f);  // Top Left Of The Texture and Quad
//        	draw.glTexCoord2f(0.0f, 0.0f); draw.glVertex3f( 1.0f, -1.0f,  1.0f);  // Bottom Left Of The Texture and Quad
//        	draw.glTexCoord2f(1.0f, 0.0f); draw.glVertex3f(-1.0f, -1.0f,  1.0f);  // Bottom Right Of The Texture and 
//		draw.glEnd();
//	}
	
	public void display(GLAutoDrawable drawable,GLU glu, GLUT glut) {
		
		GL2 draw = drawable.getGL().getGL2();
		draw.glClear(draw.GL_COLOR_BUFFER_BIT | draw.GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
	    draw.glLoadIdentity();  // reset the model-view matrix
	    
	    modelview = new float[16];
		projection = new float[16];
		viewport = new int[4];
		draw.glGetFloatv(draw.GL_MODELVIEW_MATRIX,modelview,0);
		draw.glGetFloatv(draw.GL_PROJECTION_MATRIX,projection,0);
		draw.glGetIntegerv(draw.GL_VIEWPORT, viewport,0);
//	    float right[]    = { 1, 0, 0, 0 };
//		float up[]       = { 0, 1, 0, 0 };
//		float forward[]  = { 0, 0, 1, 0 };
//		float position[] = { 0, 0, 0, 1 };
//		 
//		float ViewMatrix[][] = {
//		    {   right[0],    right[1],    right[2],    right[3] }, // First column
//		    {      up[0],       up[1],       up[2],       up[3] }, // Second column
//		    { forward[0],  forward[1],  forward[2],  forward[3] }, // Third column
//		    {position[0], position[1], position[2], position[3] }  // Forth column
//		};
	    
	    glu.gluLookAt(LookAtMatrix[0][0], LookAtMatrix[0][1], LookAtMatrix[0][2], 
	    		LookAtMatrix[1][0], LookAtMatrix[1][1], LookAtMatrix[1][2], 
	    		LookAtMatrix[2][0], LookAtMatrix[2][1], LookAtMatrix[2][2]);
	    
	    Unit.AnimateUnits(units);
	    
//	    for(int m = 0; m < Unit.MovingUnits.size(); m++){
//			
//			System.out.println(Unit.MovingUnits.get(m).getCurrentFrame() +
//					" " + Unit.MovingUnits.size() + " Display");
//		}
	    
//	    for(int u = 0; u < units.getUnitListSize(); u++){
//	    	
//	    	if(units.get(u).getCurrentFrame() != 0){
//	    		System.out.println(units.get(u).getCurrentFrame());
//	    	}
//	    }
	    //draw.glDisable(draw.GL_LIGHTING);
	    
	    draw.glEnable(draw.GL_LIGHTING);
	    drawWayPoints(draw);
	    drawMapFeatures(draw);
	    drawTiles(draw);
	    
	    drawUnits(draw);
	    drawBuildings(draw);
	    
	    
	    frameX = delayedFrameX;
	    frameY = delayedFrameY;
	    
	    
	  //  draw.glEnable(GL2.GL_TEXTURE_2D);
//	    draw.glLoadIdentity();
//	    draw.glTranslatef(4.0f, 0.0f, -15.0f);
//	    
//	    showSquare(draw,textures.getTexture("sandstonewall.jpg"));
//	    //draw.glDisable(GL2.GL_TEXTURE_2D);
//	    draw.glLoadIdentity();
//	    draw.glTranslatef(-4.0f, 0.0f, -15.0f);
//	    draw.glEnable(GL2.GL_TEXTURE_2D);
//	    showSquare(draw,textures.getTexture("rooftiles.png"));
//	    draw.glDisable(GL2.GL_TEXTURE_2D);
//	    
	    
	    
//	    buildings.begin();
//	    //shows collision map on the map
//	    CollisionMap mapC = new CollisionMap(buildings,units,map.getMap(),map.getViewedMap());
//	    mapC.setDebug();
//	    int[][] coll = mapC.getCollisionMap();
//	    
//	    for(int y = 0; y < coll.length; y++){
//	    	for(int x = 0; x < coll[y].length; x++){
//	    		
//	    		if(coll[y][x] > 0){
//		    		drawTile(draw,x,y,0.0f,1.0f,0.0f,
//		    				FRAME_Y_SIZE/WIDTH_CONST,FRAME_X_SIZE/HEIGHT_CONST);
//	    		}
//	    	}
//	    }
//	    
//	    buildings.end();
	   
	    draw.glDisable(draw.GL_LIGHTING);
	    //draw menus
//	   // System.out.println(food + " " + gold + " display");
//	    
//	  //  menus.drawMenus(draw, frameX, frameY,food,gold,
//	    ///		(int)this.getScreenWidth(),(int)this.getScreenHeight(),hoverPanel != null,
//	    	//	showOffIndex);
//	    drawHoverPanel(draw);
	    //mapDiagram.DrawMapDiagram(draw, this.getScreenWidth(),this.getScreenHeight());
	    
	    menu.DrawMenu(draw, (int)this.getScreenWidth(), (int)this.getScreenHeight(),
	    		new UnitIconSelection(buildings.isUnitCreatorSelected(),
	    				buildings.getUnitQueueSize()),units.workSelected(),
	    		units,buildings,map,playerNumber,FRAME_X_SIZE,FRAME_Y_SIZE,frameX,frameY,
	    		food,gold);
	    
	    draw.glDisable(draw.GL_LIGHTING);
	    menuModels.drawUnitIcons(-15.1f,-5.0f,-18.0f, draw, playerNumber, 
	    		buildings.getSelectedBuilding(),food, gold);
	    menuModels.drawBuildingIcons(-15.25f,-4.5f,-18.9f, draw, frameX,
	    		frameY, food, gold, false, -1);
	    
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
		
		if(player == 0){
			
			return FloatBuffer.wrap(new float[]{0.0f,0.0f,1.0f});
		
		}else if(player == 1){
			
			return FloatBuffer.wrap(new float[]{1.0f,0.0f,0.0f});
		
		}else if(player == 2){
			
			return FloatBuffer.wrap(new float[]{0.0f,1.0f,0.0f});
		}else if(player == 3){
			
			return FloatBuffer.wrap(new float[]{1.0f,1.0f,1.0f});
		
		}else if(player == 4){
			
			return FloatBuffer.wrap(new float[]{1.0f,1.0f,0.0f});
		
		}else if(player == 5){
			
			return FloatBuffer.wrap(new float[]{0.0f,1.0f,1.0f});
			
		}else if(player == 6){
			
			return FloatBuffer.wrap(new float[]{1.0f,0.0f,1.0f});
		
		}

		return FloatBuffer.wrap(new float[]{0.0f,0.0f,0.0f});
	}
	
	private void drawTile(GL2 draw,float x, float y, TileState state,
			float width, float height){
		
		draw.glLoadIdentity();
		
		if(state == TileState.None || state == TileState.White){
			
			draw.glColor3f(1.0f, 1.0f, 1.0f);
			
		}else if(state == TileState.Selected){
			
			draw.glColor3f(0.0f, 0.0f, 1.0f);
			
		}else if(state == TileState.Empty){
			
			draw.glColor3f(0.0f, 0.0f, 0.0f);
			
		}else if(state == TileState.Center){
			
			draw.glColor3f(1.0f, 0.0f, 0.0f);
			
		}else if(state == TileState.Black){
			
			draw.glColor3f(0.0f, 0.0f, 0.0f);
			
		}
		draw.glEnable(draw.GL_TEXTURE_2D);
		//draw.glDisable(draw.GL_LIGHTING);
		draw.glBindTexture(draw.GL_TEXTURE_2D, textures.getTexture("floortile.png"));
		
		draw.glTranslatef(x-width-frameX, y-height-frameY, -35.0f);
		draw.glScalef(0.75f, 0.75f, 0.75f);
		draw.glRotatef(90.0f, 1, 0, 0);
		//draw.glColor3f(red, green, blue);
		
		draw.glNormal3f(0.0f, 1.0f, 0.0f);
		draw.glBegin(draw.GL_QUADS);
			draw.glTexCoord2f(0.0f, 0.0f);
			draw.glVertex3f(1.0f, -1.0f, 1.0f);
			draw.glTexCoord2f(1.0f, 0.0f);
			draw.glVertex3f(-1.0f, -1.0f, 1.0f);
			draw.glTexCoord2f(1.0f, 1.0f);
			draw.glVertex3f(-1.0f, -1.0f, -1.0f);
			draw.glTexCoord2f(0.0f, 1.0f);
			draw.glVertex3f(1.0f, -1.0f, -1.0f);
		draw.glEnd();
		
		draw.glDisable(draw.GL_TEXTURE_2D);
		//draw.glEnable(draw.GL_LIGHTING);
		
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
		return delayedFrameX;
	}

	@Override
	public int getFrameY() {
		// TODO Auto-generated method stub
		return delayedFrameY;
	}

	@Override
	public void setFrameX(int frameX) {
		// TODO Auto-generated method stub
		this.delayedFrameX = frameX;
	}

	@Override
	public void setFrameY(int frameY) {
		// TODO Auto-generated method stub
		this.delayedFrameY = frameY;
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
		if(square[1] < (FRAME_Y_SIZE+delayedFrameY) && square[1] > (FRAME_Y_SIZE-6+delayedFrameY)){
			
			if(square[1] < (FRAME_Y_SIZE+frameY) && square[1] > (FRAME_Y_SIZE-3+delayedFrameY)){	
				if(delayedFrameY < map.getHeight()-FRAME_Y_SIZE){
					delayedFrameY+=2;
				}
			}else{
				
				if(delayedFrameY < map.getHeight()-FRAME_Y_SIZE){
					delayedFrameY++;
				}
			}
		
		}
		
		if(square[1] >= delayedFrameY && square[1] < delayedFrameY + 6){
			
			if((square[1] >= delayedFrameY && square[1] < delayedFrameY + 3)){
				if(delayedFrameY > 1){
					delayedFrameY-=2;
				}
			}else{
				
				if(delayedFrameY > 0){
					delayedFrameY--;
				}
			}
		}
		
		if(square[0] < (FRAME_X_SIZE+delayedFrameX) && square[0] > (FRAME_X_SIZE-6+delayedFrameX)){
			
			if(square[0] < (FRAME_X_SIZE+delayedFrameX) && square[0] > (FRAME_X_SIZE-3+delayedFrameX)){
				if(delayedFrameX < map.getWidth()-FRAME_X_SIZE){
					delayedFrameX+=2;
				}
			}else{
				
				if(delayedFrameX < map.getWidth()-FRAME_X_SIZE){
					delayedFrameX++;
				}
			}
		
		}
		
		if(square[0] >= delayedFrameX && square[0] < delayedFrameX + 6){
			
			if(square[0] >= delayedFrameX && square[0] < delayedFrameX + 3){
				if(delayedFrameX > 1){
					delayedFrameX-=2;
				}
			}else{
				
				if(delayedFrameX > 0){
					delayedFrameX--;
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
		
			delayedFrameX = squareX;
			delayedFrameY = squareY;
		}
	}

	@Override
	public void setResources(int food, int gold) {
		// TODO Auto-generated method stub
		this.food = food;
		this.gold = gold;
	}

	@Override
	public void setFrameXAdjusted(int x) {
		// TODO Auto-generated method stub
		if(x - FRAME_X_SIZE > 0){
			
			this.delayedFrameX = x - FRAME_X_SIZE/2;
			
		}else{
			
			this.delayedFrameX = 0;
		}
		
	}

	@Override
	public void setFrameYAdjusted(int y) {
		// TODO Auto-generated method stub
		if(y - FRAME_Y_SIZE > 0){
			
			this.delayedFrameY = y - FRAME_Y_SIZE/2;
			
		}else{
			
			this.delayedFrameY = 0;
		}
	}
	
	
	public void init(GL2 gl,GLU glu){
		
		
		gl.glEnable(gl.GL_TEXTURE_2D);
		textures.LoadTextures(new String[]{"floortile.png"});
		gl.glDisable(gl.GL_TEXTURE_2D);
		
      
	}

//	@Override
//	public void CreateBuildingIconButtons() {
//		// TODO Auto-generated method stub
//		//menus.CreateButtonIconButtons();
//	}

//	@Override
//	public void CreateUnitIcons() {
//		// TODO Auto-generated method stub
//		unitModels.CreateUnitIconsButton(buildings.getSelectedBuilding(), -16.0f,-5.0f);
//	}

	@Override
	public int getFrameSizeY() {
		// TODO Auto-generated method stub
		return FRAME_Y_SIZE;
	}

	@Override
	public int getFrameSizeX() {
		// TODO Auto-generated method stub
		return FRAME_X_SIZE;
	}

	

//	@Override
//	public void CreateHoverPanel(String type,String name, int index,double cornerX, double cornerY) {
//		// TODO Auto-generated method stub
//		//System.out.println(type + " " + index + " Display");
//		HoverPanelGraphic panel = new HoverPanelGraphic(index);
//		panel.SetColour(1.0f, 1.0f, 1.0f);
//		
//		if(type == HoverPanelGraphic.UnitIconPanel){
//			float ux = ((index % 4) * 0.75f) - 12.7f; 
//			float uy = ((index / 4) * -1.0f) - 3.25f;
//			//float[] pos = unitModels.getXY(index,-13.45f,-4.5f);
//			panel.SetPos(ux,uy, 1.0f, 1.0f);
//		}else{
//			panel.SetPos((float)cornerX, (float)cornerY, 1.0f, 1.0f);
//		}
//		
//		
//		hoverPanel = panel;
//	}




	
}
