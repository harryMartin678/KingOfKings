package GameGraphics.GameScreenComposition;

import java.io.IOException;

import javax.media.opengl.GL2;

import Buildings.Names;
import Buildings.UnitCreator;
import GameGraphics.Building;
import GameGraphics.Face;
import GameGraphics.TextModel;
import GameGraphics.Vertex;
import Map.Map;
import Map.MapList;

public class DisplayMenus {
	
	private float HEIGHT_CONST;
	private float WIDTH_CONST;
	private float scaleFactor = 1.0f;
	private IComUnitListDisplay units;
	private IComBuildingListDisplay buildings;
	private BuildingModelList buildingModels;
	private UnitModelList unitModels;
	private IComMapDisplay map;
	private int playerNumber;
	
	private int FRAME_X_SIZE;
	private int FRAME_Y_SIZE;
	
	private TextModel[] symbols;
	
	public DisplayMenus(float HEIGHT_CONST,float WIDTH_CONST,float scaleFactor,IComUnitListDisplay units,
			IComBuildingListDisplay buildings,BuildingModelList buildingModels,UnitModelList unitModels,
			IComMapDisplay map,int FRAME_X_SIZE,int FRAME_Y_SIZE
			,int playerNumber) throws IOException{
		
		this.HEIGHT_CONST = HEIGHT_CONST;
		this.WIDTH_CONST = WIDTH_CONST;
		
		this.units = units;
		this.buildings = buildings;
		this.buildingModels = buildingModels;
		this.unitModels = unitModels;
		symbols = new TextModel[37];
		this.playerNumber = playerNumber;
		
		this.map = map;
		
		this.FRAME_X_SIZE = FRAME_X_SIZE;
		this.FRAME_Y_SIZE = FRAME_Y_SIZE;
		
		String alpha = "abcdefghijklmnopqrstuvwxyz0123456789";
		
		for(int s = 0; s < 36; s ++){
			
			symbols[s] = new TextModel(alpha.charAt(s)+"uc","Models/Alphabet");
		}
		
		symbols[36] = new TextModel("cluc","Models/Alphabet");
	}
	
	
	public void drawMenus(GL2 draw,int frameX,int frameY,int food,int gold){
		
		drawLeftPanel(draw,frameX,frameY,food,gold);
		drawRightPanel(draw,frameX,frameY);
		drawTopPanel(draw,food,gold);
		drawBottomFill(draw);
	}
	
	private void drawLeftPanel(GL2 draw,int frameX,int frameY,int food,int gold){
		
		//back panel
		drawMenuQuad(draw,-16.0f,0.0f,-20.0f,0.65f,0.5f,0.39f,2.7f,3.0f,10.0f);
	
		//top panel
		drawMenuQuad(draw,-15.25f,4.0f, -19.0f,0.93f, 0.37f, 0.0f,2.0f, 3.0f, 3.0f);
		
		drawBuildingIcons(-15.25f,-4.5f,-18.0f,draw,frameX,frameY,food,gold);
		drawUnitIcons(-15.45f,-4.5f,-18.0f,draw,playerNumber,
				buildings.getSelectedBuilding(),food,gold);
		//bottom panel
		drawMenuQuad(draw,-15.25f,-4.5f, -19.0f,0.93f, 0.37f, 0.0f,2.0f, 3.0f, 3.0f);
		
		
	}
	
	private void drawBuildingIcons(float x, float y,float z, GL2 draw,
			int frameX,int frameY,int food,int gold){
		
		units.begin();
		buildings.begin();
		if(units.workSelected()){
			
			buildingModels.drawBuildingIcon(draw, x, y, z, frameX, frameY,food,gold);
		}
		units.end();
		buildings.end();
	}
	
	private void drawRightPanel(GL2 draw,int frameX, int frameY){
		
		//back panel
		drawMenuQuad(draw,16.0f,0.0f,-20.0f,0.65f,0.5f,0.39f,3.5f,3.0f,10.0f);
		
		//top panel
		drawMenuQuad(draw,14.5f,4.0f, -19.0f,0.93f, 0.37f, 0.0f,2.0f, 3.0f, 3.0f);
		drawMapInfo(draw,11.0f,1.0f, -19.0f);
		
		drawMiniMap(draw,13.5f,-4.25f,-18.0f,frameX,frameY);
		//bottom panel
		drawMenuQuad(draw,14.5f,-4.5f, -19.0f,0.93f, 0.37f, 0.0f,2.0f, 3.0f, 3.0f);
		
	}
	
	private void drawMapInfo(GL2 draw,float x, float y, float z){
		
		for(int m = 0; m < map.getMapListSize(); m++){
			
			drawString(draw,x,y+0.5f*m,z,0.0f,0.0f,0.0f,0.4f, map.getMapName(m)
					+ " " + new Integer(map.getMapPlayer(m)));
		}
	}
	
	private void drawMiniMap(GL2 draw, float x, float y, float z,int frameX,int frameY){

		drawMenuQuad(draw,x,y,z,0.85f,0.82f,0.55f,1.5f,2.5f,2.5f);
		
		//square on mini-map
		draw.glLoadIdentity();
		draw.glTranslatef((x-2.70f) + (frameX*2.55f)/map.getWidth()
				, (y-1.50f) + (4.15f*frameY)/map.getHeight(), z);
		draw.glRotatef(90.0f, 1, 0, 0);
		draw.glColor3f(0.0f, 0.0f, 0.0f);
		draw.glScalef((FRAME_X_SIZE*1.5f)/map.getWidth(), 
				(FRAME_Y_SIZE*1.8f)/map.getHeight(), (FRAME_Y_SIZE*1.8f)/map.getHeight());
		
		draw.glBegin(draw.GL_LINE_LOOP);
			draw.glVertex3f(1.0f, -1.0f, 1.0f);
			draw.glVertex3f(-1.0f, -1.0f, 1.0f);
			draw.glVertex3f(-1.0f, -1.0f, -1.0f);
			draw.glVertex3f(1.0f, -1.0f, -1.0f);
		draw.glEnd();
		
		units.begin();
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
		
		units.end();
		
		buildings.begin();
		//draw buildings 
		for(int b = 0; b < buildings.size(); b++){
			
			if(map.getTile((int) buildings.get(b).getX(), (int) buildings.get(b).getY()) == -1){
				
				continue;
			}
			
			if(buildings.get(b).getName().equals(Names.ROYALPALACE)){
				
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
		
		buildings.end();
		
		//draw the gold on the mini map
		for(float xs = 0; xs < map.getWidth(); xs++){
			for(float ys = 0; ys < map.getHeight(); ys++){
				
				//map changes during loop
				if(xs >= map.getWidth() || ys >= map.getHeight()){
					
					break;
				}
				
				if(map.getTile((int) xs, (int) ys) == 3){
					
					drawMenuQuad(draw,(x-2.85f) + (2.55f*xs)/map.getWidth()
							,(y-1.70f) + (4.35f*ys)/map.getHeight() ,z,0.85f
							,0.65f,0.13f,20.0f/(2*map.getWidth()),20.0f/(2*map.getHeight()), 
							20.0f/(2*map.getHeight()));
				}
			}
		}
	}
	
	private void drawTopPanel(GL2 draw,int food,int gold){

		//top panel
		drawMenuQuad(draw,0.0f,8.5f, -20.0f,0.65f, 0.5f, 0.39f,15.0f, 3.0f, 1.15f);
		
		drawString(draw,-1.5f,7.75f,-21.0f,0.0f,0.0f,0.0f,0.75f,"pause");
		//pause game button
		drawMenuQuad(draw,0.0f,8.5f, -20.0f,0.93f, 0.37f, 0.0f,2.0f, 3.0f, 0.75f);
		
		drawString(draw,-6.5f,7.75f,-21.0f,0.0f,0.0f,0.0f,0.75f,new Integer(food).toString());
		//food panel
		drawMenuQuad(draw,-6.0f,8.5f, -20.0f,0.45f, 0.15f, 0.0f,2.0f, 3.0f, 0.75f);
		
		drawString(draw,-11.25f,7.75f, -21.0f,0.0f,0.0f,0.0f,0.75f,"save");
		//save game button
		drawMenuQuad(draw,-11.0f,8.5f, -20.0f,0.93f, 0.37f, 0.0f,2.0f, 3.0f, 0.75f);
		
		drawString(draw,4.0f,7.75f,-21.0f,0.0f,0.0f,0.0f,0.75f,new Integer(gold).toString());
		//gold panel
		drawMenuQuad(draw,5.5f,8.5f, -20.0f,0.45f, 0.15f, 0.0f,2.0f, 3.0f, 0.75f);
		
		drawString(draw,8.0f,7.75f, -21.0f,0.0f,0.0f,0.0f,0.75f,"quit");
		//quit game button
		drawMenuQuad(draw,10.0f,8.5f, -20.0f,0.93f, 0.37f, 0.0f,2.0f, 3.0f, 0.75f);
		
	}

	
	private void drawBottomFill(GL2 draw){
		
		drawMenuQuad(draw,0.0f,-9.5f, -20.0f,0.65f, 0.5f, 0.39f,15.0f, 3.0f, 0.5f);

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
	
		
		public void drawUnitIcons(float x, float y,float z, GL2 draw,
				int playerNumber,Building SelectedBuilding,int food,int gold){
			
				
				if(SelectedBuilding != null && Building.GetBuildingClass(SelectedBuilding.getName())
						instanceof UnitCreator){
//					float offsetX = 1.75f;
//					float offsetY = 2.7f;
					drawMenuQuad(draw,-13.52f,-5.2f, -18.0f,0.5f, 0.0f, 0.5f,1.75f,1.5f, 1.5f);
					units.begin();
					unitModels.drawBuildingUnitIcons(x, y, z, draw, 
							playerNumber, SelectedBuilding,food,gold);
					units.end();
					drawMenuQuad(draw,-13.52f,-2.85f, -18.0f,1.0f, 0.84f, 0.0f,1.75f,1.5f, 1.5f);
					buildings.begin();
					unitModels.drawBuildingQueue(x + 2.0f,y,z,draw,SelectedBuilding,playerNumber);
					buildings.end();
				}
		}

}
