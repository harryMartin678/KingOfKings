package GameGraphics.GameScreenComposition;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.util.HashMap;

import com.jogamp.opengl.GL2;

import Buildings.Names;
import GameGraphics.Building;
import GameGraphics.BuildingModel;
import GameGraphics.Colour;
import GameGraphics.Face;
import GameGraphics.Unit;
import GameGraphics.Vertex;
import GameGraphics.VertexTex;
import Map.Map;

public class BuildingModelList {
	
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
	private BuildingModel site;
	
	private BuildingModel tree;
	private BuildingModel gold;
	private BuildingModel rock;
	private BuildingModel flag;
	
	private float HEIGHT_CONST;
	private float WIDTH_CONST;
	private float scaleFactor;
	private TextureRepo textures;
	private boolean isHoverPanel;
	
	private HashMap<String,Building> buildingIcons;
	//private ButtonList buttons;
	private IDrawButton drawButton;
	
	public BuildingModelList(float HEIGHT_CONST, float WIDTH_CONST,float scaleFactor,
			TextureRepo textures) throws IOException{
		
		this.HEIGHT_CONST = HEIGHT_CONST;
		this.WIDTH_CONST = WIDTH_CONST;
		this.scaleFactor = scaleFactor;
		this.textures = textures;
		//this.buttons = buttons;
		
		site = new BuildingModel(Names.SITE,"Models",1);
		site.setSize(0.075f,0.025f, 0.075f);
		archeryTower = new BuildingModel(Names.ARCHERYTOWER,"Models",1);
		archeryTower.setSize(0.25f, 0.25f, 0.25f);
		archeryTower.setTrans(0.1f, 0.1f);
		ballisticTower = new BuildingModel(Names.BALLISTICTOWER,"Models",1);
		ballisticTower.setSize(0.25f, 0.25f, 0.25f);
		ballisticTower.setTrans(0.2f, 0.2f);
		barrack = new BuildingModel(Names.BARRACK,"Models",1); //2 2
		barrack.setSize(0.25f, 0.25f, 0.25f);
		barrack.setTrans(0.1f, -0.175f);
		castle = new BuildingModel(Names.CASTLE,"Models",1);//3 3
		castle.setSize(0.095f, 0.12f, 0.1f);
		castle.setTrans(0.25f, 0.65f);
		dock = new BuildingModel(Names.DOCK,"Models",1);//2 2
		dock.setSize(0.075f, 0.075f, 0.075f);
		farm = new BuildingModel(Names.FARM,"Models",1);//2 2
		farm.setSize(0.25f, 0.24f, 0.24f);
		farm.setTrans(0.18f, 0.05f);
		fort = new BuildingModel(Names.FORT,"Models",1);//3 3
		fort.setSize(0.35f, 0.37f, 0.35f);
		fort.setTrans(-1.7f, -1.6f);
		royalPalace = new BuildingModel(Names.ROYALPALACE,"Models",1);//4 4
		royalPalace.setSize(0.45f, 0.4f, 0.38f);
		royalPalace.setTrans(0.4f, 0.55f);
		stable = new BuildingModel(Names.STABLE,"Models",1); //2 2
		stable.setTrans(0.2f, -1.0f);
		stable.setSize(0.32f, 0.32f, 0.32f);
		stockpile = new BuildingModel(Names.STOCKPILE,"Models",1);//2 2
		stockpile.setTrans(0.15f, 0.05f);
		stockpile.setSize(0.32f, 0.32f, 0.32f);
		wall = new BuildingModel(Names.WALL,"Models",1);//1 1
		wall.setSize(0.15f, 0.15f, 0.1f);
		//wall.setTrans(-5.0f, -5.0f);
		//wall.setAngle(90.0f);
		mine = new BuildingModel(Names.MINE,"Models",1);//1 1
		mine.setSize(0.15f, 0.15f, 0.15f);
		mine.setTrans(0.1f, 0.0f);
		
		tree = new BuildingModel("tree1","Models",3);
		gold = new BuildingModel("gold","Models",1);
		gold.setSize(0.3f, 0.3f, 0.3f);
		rock = new BuildingModel("rocks","Models",1);
		rock.setSize(0.35f, 0.35f, 0.35f);
		flag = new BuildingModel("flag","Models",1);
		flag.setSize(0.3f,0.3f, 0.3f);
		
		CreateBuildingIcons();
		
	}
	
	public void SetUpBuildingModelList(IDrawButton drawButton){
		
		this.drawButton = drawButton;
	}
	
	private void CreateBuildingIcons() {
		// TODO Auto-generated method stub
		buildingIcons = new HashMap<String, Building>();
		//-15.25f,-4.5f,-18.0f
		float x = -12.9f;
		float y = -5.25f;
		
		float xConst = 1.25f;
		float yConst = 1.25f;
		
		Building royalPala = new Building(x + 2*xConst, y + 1.75f, Names.ROYALPALACE,-1,0);
										//2.0 , 1.0
		Building archTower = new Building(x, y, Names.ARCHERYTOWER,-1,0);
		Building ballisTower = new Building(x + xConst, y, Names.BALLISTICTOWER,-1,0);
		Building barr = new Building(x + 2*xConst, y, Names.BARRACK,-1,0);
		Building cast = new Building(x + 3*xConst, y, Names.CASTLE,-1,0);
		Building doc = new Building(x + 4*xConst, y, Names.DOCK,-1,0);
		Building far = new Building(x + 5*xConst, y, Names.FARM,-1,0);
		Building fortb = new Building(x  + 6*xConst, y, Names.FORT,-1,0);
		Building stab = new Building(x + 7*xConst, y, Names.STABLE,-1,0);
		Building stock = new Building(x + 8*xConst, y, Names.STOCKPILE,-1,0);
		Building wal = new Building(x + 2*xConst, y - yConst, Names.WALL,-1,0);
		Building min = new Building(x + 6*xConst, y - yConst, Names.MINE,-1,0);
		
		buildingIcons.put(Names.ARCHERYTOWER, archTower);
		buildingIcons.put(Names.BALLISTICTOWER, ballisTower);
		buildingIcons.put(Names.BARRACK, barr);
		buildingIcons.put(Names.CASTLE, cast);
		buildingIcons.put(Names.DOCK, doc);
		buildingIcons.put(Names.FARM, far);
		buildingIcons.put(Names.FORT, fortb);
		buildingIcons.put(Names.ROYALPALACE, royalPala);
		buildingIcons.put(Names.STABLE, stab);
		buildingIcons.put(Names.STOCKPILE, stock);
		buildingIcons.put(Names.WALL, wal);
		buildingIcons.put(Names.MINE, min);
	}

	public BuildingModel getBuildingModel(String name,Building building){
		
		if(building.getName().equals(Names.ARCHERYTOWER)){
    		
    		return archeryTower;
    		
    	}else if(building.getName().equals(Names.BALLISTICTOWER)){
    		
    		return ballisticTower;
    		
    	}else if(building.getName().equals(Names.BARRACK)){
    		
    		return barrack;
    		
    	}else if(building.getName().equals(Names.CASTLE)){
    		
    		return castle;
    		
    	}else if(building.getName().equals(Names.DOCK)){
    		
    		return dock;
    		
    	}else if(building.getName().equals(Names.FARM)){
    		
    		return farm;
    		
    	}else if(building.getName().equals(Names.FORT)){
    		
    		return fort;
    		
    	}else if(building.getName().equals(Names.ROYALPALACE)){
    		
    		return royalPalace;
    		
    	}else if(building.getName().equals(Names.STABLE)){
    		
    		return stable;
    		
    	}else if(building.getName().equals(Names.STOCKPILE)){
    		
    		return stockpile;
    		
    	}else if(building.getName().equals(Names.WALL)){
    		
    		return wall;
    		
    	}else if(building.getName().equals(Names.MINE)){
    		
    		return mine;
    	}
		
		return null;
	}
	
	public void drawBuilding(GL2 draw,Building building,int frameX, int frameY){
		
		//System.out.println((building == null) + " " + (building.getName() == null) + " buildingModel");
		BuildingModel model = getBuildingModel(building.getName(),building);
		//System.out.println(model.getName() + " buildingModel");
		if(building.isSite()){
			
			drawBuildingModel(site,draw,building,
					WIDTH_CONST,HEIGHT_CONST,frameX,frameY,
					model.sizeX(),model.sizeY(),model.sizeZ());
			
		}else{
			
			drawBuildingModel(model,draw,building,
					WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
		}
	}
	
	public void drawBuildingModel(BuildingModel model, GL2 draw, Building building
			,float width, float height,int frameX,int frameY,float siteSizeX,float siteSizeY,
			float siteSizeZ){
		
		site.setSize(siteSizeX, siteSizeY, siteSizeZ);
		drawBuildingModel(site,draw,building,
				WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
	}
	
	public void drawBuildingModel(BuildingModel model, GL2 draw, Building building
			,float width, float height,int frameX,int frameY){
		
		drawBuildingModel(model,draw, building
				,width, height,-35.0f,true,1.0f,frameX,frameY);
	}
	
	public void drawBuildingModel(BuildingModel model, GL2 draw, Building building
			,float width, float height,float z,boolean onMap,float extraScalefactor
			,int frameX,int frameY){
		
		Face next;

		draw.glLoadIdentity();

		draw.glEnable(draw.GL_TEXTURE_2D);
		if(onMap) draw.glTranslatef(building.getX()-width-frameX + model.getTransX(),
				building.getY()-height-frameY + model.getTransY(), z);
		else draw.glTranslatef(building.getX() ,building.getY(), z);
		draw.glScalef(model.sizeX()*scaleFactor*extraScalefactor,
				model.sizeY()*scaleFactor*extraScalefactor,model.sizeZ()*scaleFactor*extraScalefactor);
		draw.glRotatef(90.0f, 1, 0, 0);
		draw.glRotatef(model.getAngle(), 0, 1, 0);

		while((next = model.popFace(0,0)) != null){
			
			String texturePath = null;

			if(building.cantBuild()) 
				draw.glColor3f(1.0f, 0.0f, 0.0f);
			else{ 
				Colour colour = model.getColour(0,0);
				texturePath = colour.getTexturePath();
				if(next.IsTextured() && texturePath != null){
					
					draw.glColor3f(1.0f,1.0f,1.0f);
					draw.glBindTexture(draw.GL_TEXTURE_2D, textures.getTexture(texturePath));
				}else{
					
					draw.glColor3fv(FloatBuffer.wrap(colour.getDiffuse()));
				}
				
			}
			
//			if(next.IsTextured() && texturePath != null){
//				
//				draw.glEnable(draw.GL_TEXTURE_2D);
//			}
			
			

			draw.glBegin(draw.GL_POLYGON);

			float[] normal = Display.getNormal(next,model, 0,0);
			draw.glNormal3f(normal[0],normal[1],normal[2]);
			
				for(int i = 0; i < next.getSize(); i++){
					
					if(next.IsTextured() && texturePath != null){
						
						VertexTex vertexT = model.getVertexTex(next.getTextureFace(i)-1,0,0);
						draw.glTexCoord2f(vertexT.getX(), vertexT.getY());
					}
					Vertex vertex = model.getVertex(next.getFace(i)-1,0,0);
					draw.glVertex3f(vertex.getX(),vertex.getY(),vertex.getZ());
					
				}
				
				
			draw.glEnd();
			
//			if(next.IsTextured() && texturePath != null){
//				
//				draw.glDisable(draw.GL_TEXTURE_2D);
//			}
			
		}
		
		draw.glDisable(draw.GL_TEXTURE_2D);
	}
	
	public void drawTiles(GL2 draw,UnitModelList models,Map map,int x, int y,
			int frameX, int frameY){
		
		if(map.getTile(x, y) == 1){
			
			Unit treeUn = new Unit((float) x,(float) y,"tree",0,-1);
			models.drawModel(tree,draw,treeUn,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
		
		}else if(map.getTile(x, y) == 2){
			
			Unit rockUn = new Unit((float) x,(float) y,"rock",0,-1);
			models.drawModel(rock,draw,rockUn,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
		
		}else if(map.getTile(x, y) == 3){
			
			Unit goldUn = new Unit((float) x,(float) y,"gold",0,-1);
			models.drawModel(gold,draw,goldUn,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
		
		}else if(map.getTile(x,y) == 4){
			
			//transition node
			drawTile(draw,(float) x,(float) y
					,0.0f,1.0f,0.0f,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
		
		}else if(map.getTile(x, y) == 5){
			
			//0.11f,0.42f,0.63f

			drawTile(draw,(float) x,(float) y
					,0.11f,0.42f,0.63f,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
		}	
	}
	
	private void drawTile(GL2 draw,float x, float y, float red, float green, float blue,
			float width, float height,int frameX,int frameY){
		
		draw.glLoadIdentity();
		
		draw.glTranslatef(x-width-frameX, y-height-frameY, -33f);
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
	
	public void drawBuildingIcon(GL2 draw,float x, float y,float z,int frameX,int frameY,
			int food, int gold,boolean isHoverPanel,int dontShowIndex){
		
		//System.out.println(food + " " + gold + " BuildingModelList drawBuildingIcon");
		//ButtonGroup group = buttons.GetGroup("BuildingIcons");
		//Building archTower = new Building(x+2.0f, y - 1.0f, Names.ARCHERYTOWER,-1,0);
		Building  archTower = buildingIcons.get(Names.ARCHERYTOWER);
		
		SetEnoughRes(archTower, gold, food);
		
		if(!isHoverPanel || dontShowIndex != 0){
			drawBuildingModel(archeryTower, draw,archTower,
					WIDTH_CONST,HEIGHT_CONST,z,false,0.2f,frameX,frameY);
		}
		
		//drawButton.DrawButton(draw,group.GetButton(Names.ARCHERYTOWER),z);		
		//Building ballisTower = new Building(x + 3.0f, y - 1.0f, Names.BALLISTICTOWER,-1,0);
		
		Building ballisTower = buildingIcons.get(Names.BALLISTICTOWER);
		
		SetEnoughRes(ballisTower, gold, food);
		
		if(!isHoverPanel || dontShowIndex != 1){
			drawBuildingModel(ballisticTower, draw,ballisTower,
					WIDTH_CONST,HEIGHT_CONST,z,false,0.2f,frameX,frameY);
		}
		
	//	drawButton.DrawButton(draw,group.GetButton(Names.BALLISTICTOWER),z);
		
		//Building barr = new Building(x + 4.0f, y - 1.0f, Names.BARRACK,-1,0);
		Building barr = buildingIcons.get(Names.BARRACK);
		
		SetEnoughRes(barr, gold, food);
		
		if(!isHoverPanel || dontShowIndex != 2){
			drawBuildingModel(barrack, draw,barr,
					WIDTH_CONST,HEIGHT_CONST,z,false,0.2f,frameX,frameY);
		}
		
		//drawButton.DrawButton(draw,group.GetButton(Names.BARRACK),z);
		
		//Building cast = new Building(x + 2.0f, y + 0.5f, Names.CASTLE,-1,0);
		Building cast = buildingIcons.get(Names.CASTLE);
		
		SetEnoughRes(cast, gold, food);
		
		//System.out.println(castBuild.FoodNeeded() + " " + castBuild.GoldNeeded() + " BuildingModelList drawBuildingIcon");
		
		if(!isHoverPanel || dontShowIndex != 3){
			drawBuildingModel(castle, draw,cast,
					WIDTH_CONST,HEIGHT_CONST,z,false,0.2f,frameX,frameY);
		}
		
		//drawButton.DrawButton(draw,group.GetButton(Names.CASTLE),z);
		
		//Building doc = new Building(x + 3.0f, y + 0.30f, Names.DOCK,-1,0);
		Building doc = buildingIcons.get(Names.DOCK);
		
		SetEnoughRes(doc, gold, food);
		
		if(!isHoverPanel || dontShowIndex != 4){
			drawBuildingModel(dock, draw,doc,
					WIDTH_CONST,HEIGHT_CONST,z,false,0.2f,frameX,frameY);
		}
		
		//drawButton.DrawButton(draw,group.GetButton(Names.DOCK),z);
		
		//Building far = new Building(x + 4.0f, y + 0.5f, Names.FARM,-1,0);
		Building far = buildingIcons.get(Names.FARM);
		
		SetEnoughRes(far, gold, food);
		
		if(!isHoverPanel || dontShowIndex != 5){
			drawBuildingModel(farm, draw,far,
					WIDTH_CONST,HEIGHT_CONST,z,false,0.2f,frameX,frameY);
		}
		
		//drawButton.DrawButton(draw,group.GetButton(Names.FARM),z);
		
		//Building fortb = new Building(x + 1.5f, y + 1.5f, Names.FORT,-1,0);
		Building fortb = buildingIcons.get(Names.FORT);
		
		SetEnoughRes(fortb, gold, food);
		
		if(!isHoverPanel || dontShowIndex != 6){
			drawBuildingModel(fort, draw,fortb,
					WIDTH_CONST,HEIGHT_CONST,z,false,0.133f,frameX,frameY);
		}
		
		//drawButton.DrawButton(draw,group.GetButton(Names.FORT),z);
		
		//Building royalPala = new Building(x + 3.0f, y + 1.75f, Names.ROYALPALACE,-1,0);
		Building royalPala = buildingIcons.get(Names.ROYALPALACE);
		
//		SetEnoughRes(royalPala, gold, food);
//		
//		drawBuildingModel(royalPalace, draw,royalPala,
//				WIDTH_CONST,HEIGHT_CONST,z,false,0.1f,frameX,frameY);
		
		//Building stab = new Building(x + 4.0f, y + 1.5f, Names.STABLE,-1,0);
		Building stab = buildingIcons.get(Names.STABLE);
		
		SetEnoughRes(stab, gold, food);
		
		if(!isHoverPanel || dontShowIndex != 7){
			drawBuildingModel(stable, draw,stab,
					WIDTH_CONST,HEIGHT_CONST,z,false,0.2f,frameX,frameY);
		}
		
		//drawButton.DrawButton(draw,group.GetButton(Names.STABLE),z);
		
		//Building stock = new Building(x + 2.0f, y + 3.0f, Names.STOCKPILE,-1,0);
		Building stock = buildingIcons.get(Names.STOCKPILE);
		
		SetEnoughRes(stock, gold, food);
		
		if(!isHoverPanel || (dontShowIndex != 8 && dontShowIndex != 7)){
			drawBuildingModel(stockpile, draw,stock,
					WIDTH_CONST,HEIGHT_CONST,z,false,0.2f,frameX,frameY);
		}
		
		//drawButton.DrawButton(draw,group.GetButton(Names.STOCKPILE),z);
		
		//Building wal = new Building(x + 3.0f, y + 3.0f, Names.WALL,-1,0);
		Building wal = buildingIcons.get(Names.WALL);
		
		SetEnoughRes(wal, gold, food);
		
		if(!isHoverPanel || dontShowIndex != 9){
			drawBuildingModel(wall, draw,wal,
					WIDTH_CONST,HEIGHT_CONST,z,false,0.3f,frameX,frameY);
		}
		
		//drawButton.DrawButton(draw,group.GetButton(Names.WALL),z);
		
		//Building min = new Building(x + 4.0f, y + 3.0f, Names.MINE,-1,0);
		Building min = buildingIcons.get(Names.MINE);
		
		SetEnoughRes(min, gold, food);
		
		if(!isHoverPanel || dontShowIndex != 10){
			drawBuildingModel(mine, draw,min,
					WIDTH_CONST,HEIGHT_CONST,z,false,0.3f,frameX,frameY);
		}
		
		//drawButton.DrawButton(draw,group.GetButton(Names.MINE),z);
	}
	
	public static boolean SetEnoughRes(Building building,int gold,int food){
		
		Buildings.Building buildingModel = Building.GetBuildingClass(building.getName());
		if(buildingModel.GoldNeeded() > gold || buildingModel.FoodNeeded() > food){
			building.NotEnoughResourcesToBuilding();
			return true;
		}
		
		return false;
	}

//	public void CreateButtonIconButton(Building selectedBuilding) {
//		// TODO Auto-generated method stub
//		//ButtonGroup group = buttons.GetGroup("BuildingIcons");
//		Object[] icons =  buildingIcons.values().toArray();
//		Object[] names =  buildingIcons.keySet().toArray();
//		
//		for(int b = 0; b < icons.length; b++){
//			
//			group.AddButton(((Building)icons[b]).getX()-0.1f, ((Building)icons[b]).getY()-0.1f, 0.4f,0.4f, "",
//					names[b].toString());
//		}
//	}
	
	public float[] getXYofBuildingIcon(String name){
		
		return new float[]{buildingIcons.get(name).getX()+1.4f,buildingIcons.get(name).getY()-1.4f};
	}

	public void setHoverPanel(boolean isHoverPanel){
		
		this.isHoverPanel = isHoverPanel;
	}


}
