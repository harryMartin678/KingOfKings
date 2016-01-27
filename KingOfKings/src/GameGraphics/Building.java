package GameGraphics;

import java.util.ArrayList;

import Buildings.ArcheryTower;
import Buildings.BallistaTower;
import Buildings.Barrack;
import Buildings.Castle;
import Buildings.Dock;
import Buildings.Farm;
import Buildings.Fort;
import Buildings.Names;
import Buildings.RoyalPalace;
import Buildings.Stable;
import Buildings.Stockpile;
import Map.CollisionMap;

public class Building {
	
	private float x;
	private float y;
	private String name;
	private int buildingNo;
	private boolean cantBuild;
	private boolean site;
	private int player;
	private int progress;
	private boolean dead;
	
	private ArrayList<String> unitQueue;
	
	public Building(String name){
		
		this.name = name;
		cantBuild = false;
		unitQueue = new ArrayList<String>();
	}

	public Building(float x, float y, String name,int buildingNo,int player){
		
		this.x = x;
		this.y = y;
		this.buildingNo = buildingNo;
		this.player = player;
		unitQueue = new ArrayList<String>();
		
		this.name = name;
	}
	
	public void setProgress(int progress){
		
		this.progress = progress;
	}
	
	public void setDead(boolean dead){
		
		this.dead = dead;
	}
	
	public int getSize(){
		
		if(unitQueue == null){
			
			return 0;
		}
		
		return unitQueue.size();
	}

	public String getUnitFromQueue(int no){
		
		return unitQueue.get(no);
	}
	
	public void addUnitQueue(String unit){
		
		unitQueue.add(unit);
	}
	
	public void clearUnitQueue(){
		
		unitQueue.clear();
	}
	
	public int getBuildingNo(){
		
		return buildingNo;
	}
	
	public void setXY(float x, float y){
		
		this.y = y;
		this.x = x;
	}
	
	public float getX(){
		
		return x;
	}
	
	public float getY(){
		
		return y;
	}
	
	public String getName(){
		
		return name;
	}
	
	public int getPlayer(){
		
		return player;
	}
	
	public boolean inBuilding(int x, int y){
		
		
		Buildings.Building check = GetBuildingClass(getName());
		
		check.setPos((int) this.x, (int) this.y);
		
		return check.inBuilding(x, y);
		
	}
	
	public void CanBuildThere(CollisionMap map){
		
		Buildings.Building check = GetBuildingClass(getName());
		
		int sizeX = check.getSizeX();
		int sizeY = check.getSizeY();
		
		cantBuild = !map.inArea((int)this.x,(int)this.y,sizeX,sizeY);
	}
	
	public boolean cantBuild(){
		
		return this.cantBuild;
	}
	
	public static Buildings.Building GetBuildingClass(String name){
		
		return GetBuildingClass(name,0);
	}
	
	public static Buildings.Building GetBuildingClass(String name,int buildingNo){
		
		Buildings.Building check = null;
		
		if(name.equals(Names.ARCHERYTOWER)){
			
			check  = new ArcheryTower(buildingNo);
		
		}else if(name.equals(Names.BALLISTICTOWER)){
			
			check = new BallistaTower(buildingNo);
		
		}else if(name.equals(Names.CASTLE)){
			
			check = new Castle(buildingNo);
		
		}else if(name.equals(Names.DOCK)){
			
			check = new Dock(buildingNo);
		
		}else if(name.equals(Names.FARM)){
			
			check = new Farm(buildingNo);
		
		}else if(name.equals(Names.FORT)){
			
			check = new Fort(buildingNo);
		
		}else if(name.equals(Names.ROYALPALACE)){
			
			check = new RoyalPalace(buildingNo);
		
		}else if(name.equals(Names.STABLE)){
			
			check = new Stable(buildingNo);
		
		}else if(name.equals(Names.CASTLE)){
			
			check = new Castle(buildingNo);
		
		}else if(name.equals(Names.DOCK)){
			
			check = new Dock(buildingNo);
		
		}else if(name.equals(Names.FARM)){
			
			check = new Farm(buildingNo);
		
		}else if(name.equals(Names.ARCHERYTOWER)){
			
			check = new ArcheryTower(buildingNo);
		
		}else if(name.equals(Names.BALLISTICTOWER)){
			
			check = new BallistaTower(buildingNo);
		
		}else if(name.equals(Names.BARRACK)){
			
			check = new Barrack(buildingNo);
		
		}else if(name.equals(Names.STOCKPILE)){
			
			check = new Stockpile(buildingNo);
		}
		
		if(check == null){
			
			check = new Buildings.Building(buildingNo);
		}
		
		return check;
	}

	public void SetSite(boolean site) {
		// TODO Auto-generated method stub
		this.site = site;
	}

	public boolean isSite() {
		// TODO Auto-generated method stub
		return site;
	}


}
