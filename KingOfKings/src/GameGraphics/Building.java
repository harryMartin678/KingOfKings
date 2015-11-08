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
import Map.CollisionMap;

public class Building {
	
	private float x;
	private float y;
	private String name;
	private int buildingNo;
	private boolean cantBuild;
	
	public Building(String name){
		
		this.name = name;
		cantBuild = false;
	}
	
	public Building(float x, float y, String name,int buildingNo){
		
		this.x = x;
		this.y = y;
		this.buildingNo = buildingNo;
		
		this.name = name;
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
		
		Buildings.Building check = null;
		
		if(name.equals(Names.ARCHERYTOWER)){
			
			check  = new ArcheryTower(0);
		
		}else if(name.equals(Names.BALLISTICTOWER)){
			
			check = new BallistaTower(0);
		
		}else if(name.equals(Names.CASTLE)){
			
			check = new Castle(0);
		
		}else if(name.equals(Names.DOCK)){
			
			check = new Dock(0);
		
		}else if(name.equals(Names.FARM)){
			
			check = new Farm(0);
		
		}else if(name.equals(Names.FORT)){
			
			check = new Fort(0);
		
		}else if(name.equals(Names.ROYALPALACE)){
			
			check = new RoyalPalace(0);
		
		}else if(name.equals(Names.STABLE)){
			
			check = new Stable(0);
		
		}else if(name.equals(Names.CASTLE)){
			
			check = new Castle(0);
		
		}else if(name.equals(Names.DOCK)){
			
			check = new Dock(0);
		
		}else if(name.equals(Names.FARM)){
			
			check = new Farm(0);
		
		}else if(name.equals(Names.ARCHERYTOWER)){
			
			check = new ArcheryTower(0);
		
		}else if(name.equals(Names.BALLISTICTOWER)){
			
			check = new BallistaTower(0);
		
		}else if(name.equals(Names.BARRACK)){
			
			check = new Barrack(0);
		}
		
		if(check == null){
			
			check = new Buildings.Building(0);
		}
		
		return check;
	}


}
