package GameGraphics.GameScreenComposition;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import javax.media.opengl.GL2;

import Buildings.Names;
import Buildings.UnitCreator;
import GameGraphics.Building;
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
	private Model arrow;
	private BuildingModel flag;
	private Model[] unitModels;
	
	private float HEIGHT_CONST;
	private float WIDTH_CONST;
	private float scaleFactor;
	
	private ArrayList<ArrowAnimation> arrowAnim; 
	
	
	public UnitModelList(float HEIGHT_CONST, float WIDTH_CONST,float scaleFactor)
	throws IOException{
		
		this.HEIGHT_CONST = HEIGHT_CONST;
		this.WIDTH_CONST = WIDTH_CONST;
		this.scaleFactor = scaleFactor;
		
		arrowAnim = new ArrayList<ArrowAnimation>();
		
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
		arrow = new Model(Names.ARROW,"Models",1,0);
		arrow.setSize(0.075f, 0.075f, 0.075f);
		
		flag = new BuildingModel("flag","Models",1);
		flag.setSize(0.3f,0.3f, 0.3f);
		
		
		unitModels = new Model[]{servant,slave,axeman,swordsman,spearman,fishingBoat,warship
				,flagship,lightChariot,heavyChariot,archer,heavyarcher,batteringRam,heavyBatteringRam};
	}
	
	public void addArrow(float startX,float startY,float targetX, float targetY,int unitNo){
		
		System.out.println(unitNo + " UnitModeList");
		arrowAnim.add(new ArrowAnimation(startX, startY, targetX, targetY,unitNo));
	}
	
//	private boolean onArrowList(int unitNo){
//		
//		for(int u = 0; u < arrowAnim.size(); u++){
//			
//			if(arrowAnim.get(u).isUnitsArrow(unitNo)){
//				
//				return true;
//			}
//		}
//		
//		return false;
//	}
	
	public void progressArrowAnim(){
		
		for(int r = 0; r < arrowAnim.size(); r++){
			arrowAnim.get(r).progress();
			
			if(arrowAnim.get(r).finished()){
				
				arrowAnim.remove(r);
				r--;
			}
		}
	}
	
	public void drawArrows(GL2 draw,int frameX, int frameY){
		
		//(Model model, GL2 draw, Unit unit,float width, float height
				//,int frameX, int frameY,float z,float extraScalefactor,boolean cantAfford)
		//System.out.println("arrow  ArrowAnimation");
		for(int a = 0; a < arrowAnim.size(); a++){
			Unit info = new Unit(arrowAnim.get(a).getArrowX(),arrowAnim.get(a).getArrowY(),"Arrow",
					1,0);
			info.setAngle(arrowAnim.get(a).getAngle());
			drawModel(arrow,draw,info,WIDTH_CONST,HEIGHT_CONST,frameX,frameY,-33.0f,0.5f,false);
		}
	}
	
	public void drawUnit(GL2 draw, Unit unit,int frameX,int frameY,Unit attack){
		
		//System.out.println(unit.getUnitType() + " unitModeList");
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
		
		if(attack != null){ //&& !onArrowList(unit.getUnitNo())){
			
			addArrow(unit.getX(), unit.getY(),attack.getX(), attack.getY(), unit.getUnitNo());
		
		}

	}
	
	public void drawModel(Model model, GL2 draw, Unit unit,float width, float height
			,int frameX, int frameY){
		
		drawModel(model,draw,unit,width,height,frameX,frameY,-35.0f,1.0f,false);
	}
	
	//used for drawing the unit models for adding units to a buidling's unit queue 
	public void drawModel(Model model, GL2 draw, Unit unit,
			float z){
		
		drawModel(model,draw,unit,0,0,0,0,z,0.5f,false);
	}
	
	public void drawModel(Model model, GL2 draw, Unit unit,
			float z,boolean cantAfford){
		
		drawModel(model,draw,unit,0,0,0,0,z,0.5f,cantAfford);
	}
	
	public void drawModel(Model model, GL2 draw, Unit unit,float width, float height
			,int frameX, int frameY,float z,float extraScalefactor,boolean cantAfford){
		
		Face next;

		draw.glLoadIdentity();

		//move the unit in relation to the width and height of the map, and the frame position
		draw.glTranslatef(unit.getX()-width-frameX, unit.getY()-height-frameY, z); //-35
		//scales the model's size
		draw.glScalef(model.sizeX()*scaleFactor*extraScalefactor, model.sizeY()*scaleFactor*extraScalefactor,
				model.sizeZ()*scaleFactor*extraScalefactor);
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
			if(cantAfford){
				
				draw.glColor3fv(FloatBuffer.wrap(new float[]{1.0f,0.0f,0.0f}));
				
			}else if(check[0] == 0.098400f && check[1] == 0.098400f && check[2] == 0.098400f){
				
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
	
	public void drawBuildingUnitIcons(float x, float y,float z
			,GL2 draw,int playerNumber,Building SelectedBuilding,int food,int gold){
		
		UnitCreator type = (UnitCreator) Building.GetBuildingClass(SelectedBuilding.getName());
		
		String unitsPossible = type.unitcreated();
		
		float offsetX = 1.75f;
		float offsetY = 2.7f;
		
		for(int u = 0; u < unitModels.length; u++){
			
			if(offsetX > 4.0f){
				
				offsetX = 1.5f;
				offsetY = 2.5f;
			}
			
			if(unitsPossible.contains(unitModels[u].getName())){
				
				Unit unit = new Unit((float)x + offsetX,(float)y + offsetY,unitModels[u].getName(),
						playerNumber,0);
				Units.Unit unitDes = Units.Unit.GetUnit(unitModels[u].getName());
				boolean cantAfford = false;
				
				if(unitDes.goldNeeded() > gold && unitDes.foodNeeded() > food){
					
					cantAfford = true;
				}
				
				drawModel(unitModels[u],draw,unit,z,cantAfford);
				
				offsetX += 1.0f;
				offsetY -= 1.5f;
			}
		}
		
	}
	
	public Model getUnitModel(String name){
		
		for(int u = 0; u < unitModels.length; u++){
			
			if(unitModels[u].getName().equals(name)){
				
				return unitModels[u];
			}
		}
		
		return null;
	}
	

	public void drawBuildingQueue(float x, float y, float z, GL2 draw,
			Building selectedBuilding,int playerNumber) {
		// TODO Auto-generated method stub

		///System.out.println("building queue " + selectedBuilding.getSize()/3
			//	+ " " + selectedBuilding.getSize()%3);
		
		int queueNo = 0;
	
		outerLoop:
		for(int uy = 0; uy < 4; uy++){
			for(int ux = 0; ux < 5; ux++){
				
				if(queueNo >= selectedBuilding.getSize()){
					
					break outerLoop;
				}
				
				Model model = getUnitModel(selectedBuilding.getUnitFromQueue(queueNo));
				Unit unit = new Unit((float) x + (ux*0.5f),(float) y + (-uy*0.5f), model.getName(),
									playerNumber,0);
				drawModel(model,draw,unit,z);
				
				queueNo++;
			}
		}
	}
	
}
