package GameGraphics.GameScreenComposition;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import com.jogamp.opengl.GL2;

import Buildings.Names;
import Buildings.UnitCreator;
import GameGraphics.Building;
import GameGraphics.BuildingModel;
import GameGraphics.Colour;
import GameGraphics.Face;
import GameGraphics.IBoundingBoxes;
import GameGraphics.Model;
import GameGraphics.Unit;
import GameGraphics.Vertex;
import GameGraphics.VertexTex;

public class UnitModelList implements IBoundingBoxes {

	private Model worker;
	private Model swordsman;
	private Model spearman;
	private Model hound;
	private Model giant;
	private Model archer;
	private Model arrow;
	private BuildingModel flag;
	//private Model[] unitModels;
	private Hashtable<String,Model> models;
	
	private float HEIGHT_CONST;
	private float WIDTH_CONST;
	private float scaleFactor;
	private TextureRepo textures;
	
	private ArrayList<ArrowAnimation> arrowAnim; 
	//private ButtonList buttons;
	//private IDrawButton drawButton;
	
	
	public UnitModelList(float HEIGHT_CONST, float WIDTH_CONST,float scaleFactor,TextureRepo textures)
			//ButtonList buttons)
	throws IOException{
		
		this.HEIGHT_CONST = HEIGHT_CONST;
		this.WIDTH_CONST = WIDTH_CONST;
		this.scaleFactor = scaleFactor;
		this.textures = textures;
		//this.buttons = buttons;
		
		//buttons.AddButtonGroup("UnitIcons");
		
		models = new Hashtable<String,Model>();
		
		arrowAnim = new ArrayList<ArrowAnimation>();
		
		swordsman = new Model(Names.SWORDSMAN,"Models",3,0);
		swordsman.setSize(1.0f, 1.0f, 1.0f);
		spearman = new Model(Names.SPEARMAN,"Models",3,0);
		spearman.setSize(1.0f, 1.0f, 1.0f);
		archer = new Model(Names.ARCHER,"Models",3,0);
		archer.setSize(1.0f, 1.0f, 1.0f);
		worker = new Model(Names.WORKER, "Models", 3, 0);
		//worker.setAngle(45);
		worker.setSize(1.0f, 1.0f, 1.0f);
		giant = new Model(Names.GIANT, "Models", 3,0);
		giant.setSize(1.0f, 1.0f, 3.0f);
		giant.setTrans(0.0f, 0.0f, 1.0f);
		hound = new Model(Names.HOUND, "Models", 3,0);
		hound.setSize(1.0f, 1.0f, 1.0f);

		arrow = new Model(Names.ARROW,"Models",1,3);
		arrow.setSize(0.075f, 0.075f, 0.075f);
		
		flag = new BuildingModel("flag","Models",1,true);
		flag.setSize(0.3f,0.3f, 0.3f);
		
		models.put(Names.ARCHER, archer);
		models.put(Names.SPEARMAN, spearman);
		models.put(Names.SWORDSMAN, swordsman);
		models.put(Names.GIANT, giant);
		models.put(Names.HOUND, hound);
		models.put(Names.WORKER, worker);
		//unitModels = new Model[]{servant,slave,axeman,swordsman,spearman,fishingBoat,warship
			//	,flagship,lightChariot,heavyChariot,archer,heavyarcher,batteringRam,heavyBatteringRam};
	}
	
//	public void SetUpUnitModels(IDrawButton drawButton){
//		
//		this.drawButton = drawButton;
//	}
	
	public void addArrow(float startX,float startY,float targetX, float targetY,int unitNo){
		
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
		
		drawModel(models.get(unit.getUnitType()),draw,unit,
				WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
		
		if(attack != null){ //&& !onArrowList(unit.getUnitNo())){
			
			addArrow(unit.getX(), unit.getY(),attack.getX(), attack.getY(), unit.getUnitNo());
		
		}

	}
	
	public void towerBattle(int startX,int startY,int targetX,int targetY){
		
		if(arrowAnim.size() < 5){
			addArrow(startX,startY,targetX,targetY,0);
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
		
//		Random random = new Random(unit.getUnitNo());
//		FloatBuffer cBuffer = FloatBuffer.wrap(new float[]{random.nextFloat(),random.nextFloat(),
//				random.nextFloat()});

		draw.glLoadIdentity();

		//move the unit in relation to the width and height of the map, and the frame position
		draw.glTranslatef(unit.getX()-(width+0.1f)-frameX, unit.getY()-(height+0.1f)-frameY, z + model.getTransZ()); //-35
		//scales the model's size
		draw.glScalef(model.sizeX()*scaleFactor*extraScalefactor, model.sizeY()*scaleFactor*extraScalefactor,
				model.sizeZ()*scaleFactor*extraScalefactor);
		//draw.glRotatef(90, 1, 0, 0);
		draw.glRotatef((float) unit.getAngle(), 0, 0, 1);
		
//		if(unit.checkAnimBroken()){
//			
//			System.out.println("BROKEN ANIM UNITMODELLIST");
//		}
		
		//gets the frame and state of a unit 
		int currentFrame = unit.getCurrentFrame();
		int state = unit.getState();

		while((next = model.popFace(currentFrame,state)) != null){
			
			//get the colour of the face
			Colour colour = model.getColour(currentFrame,state);
			
			if(next.IsTextured() && colour.getTexturePath() != null){
				
				draw.glEnable(draw.GL_TEXTURE_2D);
			}
			
			float[] check = colour.getDiffuse();
			if(next.IsTextured() && colour.getTexturePath() != null){
				
				draw.glBindTexture(draw.GL_TEXTURE_2D, textures.getTexture(colour.getTexturePath()));
			}
			//if a indicate colour is since then substitute the player's colour 
			if(cantAfford){
				
				draw.glColor3fv(FloatBuffer.wrap(new float[]{1.0f,0.0f,0.0f}));
				
			}else if(check[0] == 0.098400f && check[1] == 0.098400f && check[2] == 0.098400f){
				
				draw.glColor3fv(Display.getPlayerColour(unit.getPlayer()));
				
			}else{
				draw.glColor3fv(FloatBuffer.wrap(colour.getDiffuse()));
				//draw.glColor3fv(cBuffer);
			}

			draw.glBegin(draw.GL_POLYGON);

				//get the face's normal [
				float[] normal = Display.getNormal(next,model, currentFrame,state);
				draw.glNormal3f(normal[0],normal[1],normal[2]);
				
				for(int i = 0; i < next.getSize(); i++){
					
					
					Vertex vertex = model.getVertex(next.getFace(i)-1,currentFrame,state);
					draw.glVertex3f(vertex.getX(),vertex.getY(),vertex.getZ());
					
					if(next.IsTextured() && colour.getTexturePath() != null){
						
						VertexTex vertexT = model.getVertexTex(next.getTextureFace(i)-1,state);
						draw.glTexCoord2d(vertexT.getX(), vertexT.getY());
					}
				}
				
				
			draw.glEnd();
			
			if(next.IsTextured() && colour.getTexturePath() != null){
				
				draw.glDisable(draw.GL_TEXTURE_2D);
			}
			
		}
		
	}
	
//	private void startPicking(GL2 gl,GLU glu,int xCursor, int yCursor,
//			float panelWidth,float panelHeight) 
//	{ 
//		
//	  // initialize the selection buffer 
//	  int selectBuf[] = new int[BUFSIZE]; 
//	  selectBuffer = BufferUtil.newIntBuffer(BUFSIZE); 
//	  gl.glSelectBuffer(BUFSIZE, selectBuffer); 
//	  gl.glRenderMode(GL2.GL_SELECT);  // switch to selection mode 
//	  gl.glInitNames();   // make an empty name stack 
//	  // save the original projection matrix 
//	  gl.glMatrixMode(GL2.GL_PROJECTION); 
//	  gl.glPushMatrix(); 
//	  gl.glLoadIdentity(); 
//	  // get the current viewport 
//	  int viewport[] = new int[4]; 
//	  gl.glGetIntegerv(GL.GL_VIEWPORT, viewport, 0); 
//	  // create a 5x5 pixel picking volume near the cursor location 
//	  glu.gluPickMatrix((double) xCursor, 
//	                    (double) (viewport[3] - yCursor),  
//	                    5.0, 5.0, viewport, 0); 
//	  /* set projection (perspective or orthogonal) exactly as it is in  
//	     normal rendering (i.e. duplicate the gluPerspective() call 
//	     in resizeView()) */ 
//	  glu.gluPerspective(45.0,  
//	         (float)panelWidth/(float)panelHeight, 1, 100); 
//	  gl.glMatrixMode(GL2.GL_MODELVIEW);   // restore model view 
//	}  
	
	public void drawFlag(GL2 draw,Unit flagUn,int frameX,int frameY){
		
		drawModel(flag,draw,flagUn,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
	}
	
//	public void CreateUnitIconsButton(Building SelectedBuilding,float x, float y){
//		
//		ButtonGroup group = buttons.GetGroup("UnitIcons");
//		group.Clear();
//		if(Building.GetBuildingClass(SelectedBuilding.getName()) instanceof UnitCreator){
//			UnitCreator type = (UnitCreator)Building.GetBuildingClass(SelectedBuilding.getName());
//			String unitsPossible = type.unitcreated();
//			
//			String[] unitTypes = unitsPossible.split(";");
//			
//			for(int u = 0; u < unitTypes.length; u++){
//				
//				//1.75f 2.5f
//				float ux = getUnitIconX(x,u)-0.25f;//((u % 4) * 0.75f) + x + 1.5f; 
//				//float uy = ((u / 4) * -1.0f) + y + 2.5f;
//				
//				group.AddButton(ux, y-0.075f, 0.25f, 0.25f, "", unitTypes[u]);
//			}
//		}
//		
//	}
	
	public static float getUnitIconX(float x,int unitNo){
		
		return (unitNo * 1.25f) + x + 2.75f;
	}
	
	public void drawBuildingUnitIcons(float x, float y,float z
			,GL2 draw,int playerNumber,Building SelectedBuilding,int food,int gold){
		
		UnitCreator type = (UnitCreator)Building.GetBuildingClass(SelectedBuilding.getName());
		String unitsPossible = type.unitcreated();
		
		String[] unitTypes = unitsPossible.split(";");
		
		//ButtonGroup group = buttons.GetGroup("UnitIcons");
		
		for(int u = 0; u < unitTypes.length; u++){
			
//			float ux = ((u % 4) * 0.75f) + x + 1.75f; 
//			float uy = ((u / 4) * -1.0f) + y + 2.5f;
			float ux = getUnitIconX(x,u);
			
			//drawButton.DrawButton(draw, group.GetButton(unitTypes[u]), z);
			
			Model model = models.get(unitTypes[u]);
			if(model == null){
				
				continue;
			}
			
			Units.Unit unitDes = Units.Unit.GetUnit(model.getName());
			
			Unit unit = new Unit(ux,y,model.getName(),playerNumber,0);
			
			boolean cantAfford = false;
			if(unitDes.goldNeeded() > gold && unitDes.foodNeeded() > food){
				
				cantAfford = true;
			}
			
			drawModel(model,draw,unit,z,cantAfford);
		}
		
//		
//		float offsetX = 1.75f;
//		float offsetY = 2.7f;
//		
//		for(int u = 0; u < unitModels.length; u++){
//			
//			if(offsetX > 4.0f){
//				
//				offsetX = 1.5f;
//				offsetY = 2.5f;
//			}
//			
//			if(unitsPossible.contains(unitModels[u].getName())){
//				
//				System.out.println(unitModels[u].getName() + " UnitModelList");
//				Unit unit = new Unit((float)x + offsetX,(float)y + offsetY,unitModels[u].getName(),
//						playerNumber,0);
//				Units.Unit unitDes = Units.Unit.GetUnit(unitModels[u].getName());
//				boolean cantAfford = false;
//				
//				if(unitDes.goldNeeded() > gold && unitDes.foodNeeded() > food){
//					
//					cantAfford = true;
//				}
//				
//				drawModel(unitModels[u],draw,unit,z,cantAfford);
//				
//				offsetX += 1.0f;
//				//offsetY -= 1.5f;
//			}
//		}
		
	}
	
//	public Model getUnitModel(String name){
//		
//		//System.out.println(name + " UnitModeList getUnitModel");
//		for(int u = 0; u < unitModels.length; u++){
//			
//			if(unitModels[u].getName().equals(name)){
//				
//				return unitModels[u];
//			}
//		}
//		
//		return null;
//	}
	

	public void drawBuildingQueue(float x, float y, float z, GL2 draw,
			Building selectedBuilding,int playerNumber) {
		// TODO Auto-generated method stub

		///System.out.println("building queue " + selectedBuilding.getSize()/3
			//	+ " " + selectedBuilding.getSize()%3);
		
		int queueNo = 0;
	
		outerLoop:
		for(int uy = 0; uy < 4; uy++){
			for(int ux = 0; ux < 5; ux++){
				//System.out.println(selectedBuilding.getSize() + " building queue UnitModeList");
				if(selectedBuilding == null || queueNo >= selectedBuilding.getSize()){
					
					break outerLoop;
				}

				Model model = models.get(selectedBuilding.getUnitFromQueue(queueNo));
				//System.out.println(selectedBuilding.getUnitFromQueue(queueNo) + " UnitModelList");
				Unit unit = new Unit((float) x + (ux*0.5f),(float) y + (-uy*0.5f),
									model.getName(), playerNumber,0);
				drawModel(model,draw,unit,z);
				
				queueNo++;
			}
		}
		
	}
	
	public float[] getXY(int unitNo,float x, float y){
		
		return new float[]{(float) x + ((unitNo%5)*0.5f),(float) y + (-(unitNo/4)*0.5f)};

	}
	
	
	public Model GetModel(String unitType){
		
		if(Names.WORKER.equals(unitType)){
			
			return worker;
		
		}else if(Names.ARCHER.equals(unitType)){
			
			return archer;
		
		}else if(Names.GIANT.equals(unitType)){
			
			return giant;
		
		}else if(Names.HOUND.equals(unitType)){
			
			return hound;
		
		}else if(Names.SPEARMAN.equals(unitType)){
			
			return spearman;
		
		}else if(Names.SWORDSMAN.equals(unitType)){
			
			return swordsman;
			
		}else{
			
			return null;
		}
	}

	@Override
	public float[] GetBoundingBox(String type,int state) {
		// TODO Auto-generated method stub
		return GetModel(type).getBB(state);
	}

	
}
