package GameGraphics.GameScreenComposition;

import java.io.IOException;
import java.nio.FloatBuffer;

import javax.media.opengl.GL2;

import Buildings.Names;
import GameGraphics.BuildingModel;
import GameGraphics.ChariotModel;
import GameGraphics.Colour;
import GameGraphics.Face;
import GameGraphics.Model;
import GameGraphics.Unit;
import GameGraphics.Vertex;
import Map.Map;

public class UnitModelList {

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
	private BuildingModel flag;
	
	private float HEIGHT_CONST;
	private float WIDTH_CONST;
	private float scaleFactor;
	
	
	public UnitModelList(float HEIGHT_CONST, float WIDTH_CONST,float scaleFactor)
	throws IOException{
		
		this.HEIGHT_CONST = HEIGHT_CONST;
		this.WIDTH_CONST = WIDTH_CONST;
		this.scaleFactor = scaleFactor;
		
		servant = new Model(Names.SERVANT,"Models",3,0);
		slave = new Model(Names.SLAVE,"Models",3,0);
		axeman = new Model(Names.AXEMAN,"Models",3,0);
		swordsman = new Model(Names.SWORDSMAN,"Models",3,0);
		spearman = new Model(Names.SPEARMAN,"Models",3,0);
		fishingBoat = new Model(Names.FISHINGBOAT,"Models",1,1);
		fishingBoat.setSize(0.1f, 0.1f, 0.2f);
		warship = new Model(Names.WARSHIP,"Models",1,1);
		warship.setSize(0.1f, 0.1f, 0.2f);
		flagship = new Model(Names.FLAGSHIP,"Models",1,1);
		warship.setSize(0.1f, 0.1f, 0.2f);
		lightChariot = new ChariotModel(Names.LIGHTCHARIOT,"Models",3);
		lightChariot.setSize(0.15f, 0.15f, 0.2f);
		heavyChariot = new ChariotModel(Names.HEAVYCHARIOT,"Models",3);
		heavyChariot.setSize(0.15f, 0.15f, 0.2f);
		archer = new Model(Names.ARCHER,"Models",3,0);
		heavyarcher = new Model(Names.HEAVYARCHER,"Models",3,0);
		batteringRam = new Model(Names.BATTERINGRAM,"Models",3,0);
		batteringRam.setSize(0.1f, 0.1f, 0.2f);
		heavyBatteringRam = new Model(Names.HEAVYBATTERINGRAM,"Models",3,0);
		heavyBatteringRam.setSize(0.1f, 0.2f, 0.2f);
	}
	
	public void drawUnit(GL2 draw, Unit unit,int frameX,int frameY){
		
		if(unit.getUnitType().equals(Names.SERVANT)){
    		
    		drawModel(servant,draw,unit,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
    		
    	}else if(unit.getUnitType().equals(Names.SLAVE)){
    		
    		drawModel(slave,draw,unit,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
    		
    	}else if(unit.getUnitType().equals(Names.AXEMAN)){
    		
    		drawModel(axeman,draw,unit,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
    		
    	}else if(unit.getUnitType().equals(Names.SWORDSMAN)){
    		
    		drawModel(swordsman,draw,unit,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
    		
    	}else if(unit.getUnitType().equals(Names.SPEARMAN)){
    		
    		drawModel(spearman,draw,unit,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
    		
    	}else if(unit.getUnitType().equals(Names.ARCHER)){
    		
    		drawModel(archer,draw,unit,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
    		
    	}else if(unit.getUnitType().equals(Names.HEAVYARCHER)){
    		
    		drawModel(heavyarcher,draw,unit,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
    		
    	}else if(unit.getUnitType().equals(Names.BATTERINGRAM)){
    		
    		drawModel(batteringRam,draw,unit,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
    		
    	}else if(unit.getUnitType().equals(Names.HEAVYBATTERINGRAM)){
    		
    		drawModel(heavyBatteringRam,draw,unit,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
    		
    	}else if(unit.getUnitType().equals(Names.LIGHTCHARIOT)){
    		
    		drawModel(lightChariot,draw,unit,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
    		
    	}else if(unit.getUnitType().equals(Names.HEAVYCHARIOT)){
    		
    		drawModel(heavyChariot,draw,unit,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
    		
    	}else if(unit.getUnitType().equals(Names.FISHINGBOAT)){
    		
    		drawModel(fishingBoat,draw,unit,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
    		
    	}else if(unit.getUnitType().equals(Names.WARSHIP)){
    		
    		drawModel(warship,draw,unit,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
    		
    	}else if(unit.getUnitType().equals(Names.FLAGSHIP)){
    		
    		drawModel(flagship,draw,unit,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
    	}
	}
	
	public void drawModel(Model model, GL2 draw, Unit unit,float width, float height
			,int frameX, int frameY){
		
		Face next;

		draw.glLoadIdentity();

		//move the unit in relation to the width and height of the map, and the frame position
		draw.glTranslatef(unit.getX()-width-frameX, unit.getY()-height-frameY, -35); //-35
		//scales the model's size
		draw.glScalef(model.sizeX()*scaleFactor, model.sizeY()*scaleFactor, model.sizeZ()*scaleFactor);
		draw.glRotatef(45, 1, 0, 0);
		draw.glRotatef((float) unit.getAngle(), 0, 1, 0);
		
		//gets the frame and state of a unit 
		int currentFrame = unit.getCurrentFrame();
		int state = unit.getState();

		while((next = model.popFace(currentFrame,state)) != null){
			
			//get the colour of the face
			Colour colour = model.getColour(currentFrame,state);
			
			float[] check = colour.getDiffuse();
			//if a indicate colour is since then substitute the player's colour 
			if(check[0] == 0.098400f && check[1] == 0.098400f && check[2] == 0.098400f){
				
				draw.glColor3fv(Display.getPlayerColour(unit.getPlayer()));
				
			}else{
				draw.glColor3fv(FloatBuffer.wrap(colour.getDiffuse()));
			}

			draw.glBegin(draw.GL_POLYGON);

				//get the face's normal [
				float[] normal = Display.getNormal(next,model, currentFrame,state);
				draw.glNormal3f(normal[0],normal[1],normal[2]);
				
				for(int i = 0; i < next.getSize(); i++){
					
					Vertex vertex = model.getVertex(next.getFace(i)-1,currentFrame,state);
					draw.glVertex3f(vertex.getX(),vertex.getY(),vertex.getZ());
				}
				
				
			draw.glEnd();
			
		}
		
	}
	
	
	
	public void drawFlag(GL2 draw,Unit flagUn,int frameX,int frameY){
		
		drawModel(flag,draw,flagUn,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
	}
	
}
