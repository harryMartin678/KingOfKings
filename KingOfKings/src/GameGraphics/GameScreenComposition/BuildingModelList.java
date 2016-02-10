package GameGraphics.GameScreenComposition;

import java.io.IOException;
import java.nio.FloatBuffer;

import javax.media.opengl.GL2;

import Buildings.Names;
import GameGraphics.Building;
import GameGraphics.BuildingModel;
import GameGraphics.Colour;
import GameGraphics.Face;
import GameGraphics.Model;
import GameGraphics.Unit;
import GameGraphics.Vertex;
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
	
	public BuildingModelList(float HEIGHT_CONST, float WIDTH_CONST,float scaleFactor) throws IOException{
		
		this.HEIGHT_CONST = HEIGHT_CONST;
		this.WIDTH_CONST = WIDTH_CONST;
		this.scaleFactor = scaleFactor;
		
		site = new BuildingModel(Names.SITE,"Models",1);
		archeryTower = new BuildingModel(Names.ARCHERYTOWER,"Models",1);
		archeryTower.setSize(0.25f, 0.25f, 0.25f);
		ballisticTower = new BuildingModel(Names.BALLISTICTOWER,"Models",1);
		ballisticTower.setSize(0.25f, 0.25f, 0.25f);
		barrack = new BuildingModel(Names.BARRACK,"Models",1); //2 2
		barrack.setSize(0.15f, 0.15f, 0.15f);
		castle = new BuildingModel(Names.CASTLE,"Models",1);//3 3
		castle.setSize(0.05f, 0.05f, 0.1f);
		dock = new BuildingModel(Names.DOCK,"Models",1);//2 2
		dock.setSize(0.075f, 0.075f, 0.075f);
		farm = new BuildingModel(Names.FARM,"Models",1);//2 2
		farm.setSize(0.15f, 0.15f, 0.15f);
		fort = new BuildingModel(Names.FORT,"Models",1);//3 3
		fort.setSize(0.2f, 0.2f, 0.25f);
		royalPalace = new BuildingModel(Names.ROYALPALACE,"Models",1);//4 4 
		stable = new BuildingModel(Names.STABLE,"Models",1); //2 2
		stockpile = new BuildingModel(Names.STOCKPILE,"Models",1);//2 2
		wall = new BuildingModel(Names.WALL,"Models",1);//1 1
		wall.setSize(0.15f, 0.15f, 0.1f);
		wall.setAngle(90.0f);
		mine = new BuildingModel(Names.MINE,"Models",1);//1 1
		mine.setSize(0.15f, 0.15f, 0.15f);
		
		tree = new BuildingModel("tree1","Models",3);
		gold = new BuildingModel("gold","Models",1);
		gold.setSize(0.3f, 0.3f, 0.3f);
		rock = new BuildingModel("rocks","Models",1);
		rock.setSize(0.35f, 0.35f, 0.35f);
		flag = new BuildingModel("flag","Models",1);
		flag.setSize(0.3f,0.3f, 0.3f);
		
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

		if(onMap) draw.glTranslatef(building.getX()-width-frameX, building.getY()-height-frameY, z);
		else draw.glTranslatef(building.getX(), building.getY(), z);
		draw.glScalef(model.sizeX()*scaleFactor*extraScalefactor,
				model.sizeY()*scaleFactor*extraScalefactor,model.sizeZ()*scaleFactor*extraScalefactor);
		draw.glRotatef(90.0f, 1, 0, 0);
		draw.glRotatef(model.getAngle(), 0, 1, 0);

		while((next = model.popFace(0,0)) != null){
			
			if(building.cantBuild()) 
				draw.glColor3f(1.0f, 0.0f, 0.0f);
			else{ 
				Colour colour = model.getColour(0,0); 
				draw.glColor3fv(FloatBuffer.wrap(colour.getDiffuse()));
			}

			draw.glBegin(draw.GL_POLYGON);

			float[] normal = Display.getNormal(next,model, 0,0);
			draw.glNormal3f(normal[0],normal[1],normal[2]);
			
				for(int i = 0; i < next.getSize(); i++){
					
					Vertex vertex = model.getVertex(next.getFace(i)-1,0,0);
					draw.glVertex3f(vertex.getX(),vertex.getY(),vertex.getZ());
				}
				
				
			draw.glEnd();
			
		}
		
		
	}
	
	public void drawTiles(GL2 draw,UnitModelList models,Map map,int x, int y,
			int frameX, int frameY){
		
		if(map.getTile(x, y) == 1){
			
			Unit treeUn =new Unit((float) x,(float) y,"tree",0,-1);
			models.drawModel(tree,draw,treeUn,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
		
		}else if(map.getTile(x, y) == 2){
			
			Unit rockUn =new Unit((float) x,(float) y,"rock",0,-1);
			models.drawModel(rock,draw,rockUn,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
		
		}else if(map.getTile(x, y) == 3){
			
			Unit goldUn =new Unit((float) x,(float) y,"gold",0,-1);
			models.drawModel(gold,draw,goldUn,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
		
		}else if(map.getTile(x,y) == 4){
			
			//transition node
		
		}else if(map.getTile(x, y) == 5){
			
			//0.11f,0.42f,0.63f

			drawTile(draw,(float) x,(float) y
					,0.11f,0.42f,0.63f,WIDTH_CONST,HEIGHT_CONST,frameX,frameY);
		}	
	}
	
	private void drawTile(GL2 draw,float x, float y, float red, float green, float blue,
			float width, float height,int frameX,int frameY){
		
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
	
	public void drawBuildingIcon(GL2 draw,float x, float y,float z,int frameX,int frameY,
			int food, int gold){
		
		//System.out.println(food + " " + gold + " BuildingModelList drawBuildingIcon");
		
		Building archTower = new Building(x+2.0f, y - 1.0f, Names.ARCHERYTOWER,-1,0);
		
		
		SetEnoughRes(archTower, gold, food);
		
		drawBuildingModel(archeryTower, draw,archTower,
				WIDTH_CONST,HEIGHT_CONST,z,false,0.3f,frameX,frameY);
		
		Building ballisTower = new Building(x + 3.0f, y - 1.0f, Names.BALLISTICTOWER,-1,0);
		
		
		SetEnoughRes(ballisTower, gold, food);
		
		drawBuildingModel(ballisticTower, draw,ballisTower,
				WIDTH_CONST,HEIGHT_CONST,z,false,0.3f,frameX,frameY);
		
		Building barr = new Building(x + 4.0f, y - 1.0f, Names.BARRACK,-1,0);
		
		
		SetEnoughRes(barr, gold, food);
		
		drawBuildingModel(barrack, draw,barr,
				WIDTH_CONST,HEIGHT_CONST,z,false,0.3f,frameX,frameY);
		
		Building cast = new Building(x + 2.0f, y + 0.5f, Names.CASTLE,-1,0);
		
		
		SetEnoughRes(cast, gold, food);
		
		//System.out.println(castBuild.FoodNeeded() + " " + castBuild.GoldNeeded() + " BuildingModelList drawBuildingIcon");
		
		drawBuildingModel(castle, draw,cast,
				WIDTH_CONST,HEIGHT_CONST,z,false,0.3f,frameX,frameY);
		
		Building doc = new Building(x + 3.0f, y + 0.30f, Names.DOCK,-1,0);
		
		
		SetEnoughRes(doc, gold, food);
		
		drawBuildingModel(dock, draw,doc,
				WIDTH_CONST,HEIGHT_CONST,z,false,0.3f,frameX,frameY);
		
		Building far = new Building(x + 4.0f, y + 0.5f, Names.FARM,-1,0);
		
		
		SetEnoughRes(far, gold, food);
		
		drawBuildingModel(farm, draw,far,
				WIDTH_CONST,HEIGHT_CONST,z,false,0.3f,frameX,frameY);
		
		Building fortb = new Building(x + 1.5f, y + 1.5f, Names.FORT,-1,0);
		
		SetEnoughRes(fortb, gold, food);
		
		drawBuildingModel(fort, draw,fortb,
				WIDTH_CONST,HEIGHT_CONST,z,false,0.2f,frameX,frameY);
		
		Building royalPala = new Building(x + 3.0f, y + 1.75f, Names.ROYALPALACE,-1,0);
		
		SetEnoughRes(royalPala, gold, food);
		
		drawBuildingModel(royalPalace, draw,royalPala,
				WIDTH_CONST,HEIGHT_CONST,z,false,0.15f,frameX,frameY);
		
		Building stab = new Building(x + 4.0f, y + 1.5f, Names.STABLE,-1,0);
		
		SetEnoughRes(stab, gold, food);
		
		drawBuildingModel(stable, draw,stab,
				WIDTH_CONST,HEIGHT_CONST,z,false,0.3f,frameX,frameY);
		
		Building stock = new Building(x + 2.0f, y + 3.0f, Names.STOCKPILE,-1,0);
		
		SetEnoughRes(stock, gold, food);
		
		drawBuildingModel(stockpile, draw,stock,
				WIDTH_CONST,HEIGHT_CONST,z,false,0.3f,frameX,frameY);
		
		Building wal = new Building(x + 3.0f, y + 3.0f, Names.WALL,-1,0);
		
		SetEnoughRes(wal, gold, food);
		
		drawBuildingModel(wall, draw,wal,
				WIDTH_CONST,HEIGHT_CONST,z,false,0.4f,frameX,frameY);
		
		Building min = new Building(x + 4.0f, y + 3.0f, Names.MINE,-1,0);
		
		SetEnoughRes(min, gold, food);
		
		drawBuildingModel(mine, draw,min,
				WIDTH_CONST,HEIGHT_CONST,z,false,0.4f,frameX,frameY);
	}
	
	public static boolean SetEnoughRes(Building building,int gold,int food){
		
		Buildings.Building buildingModel = Building.GetBuildingClass(building.getName());
		if(buildingModel.GoldNeeded() > gold || buildingModel.FoodNeeded() > food){
			building.NotEnoughResourcesToBuilding();
			return true;
		}
		
		return false;
	}



}
