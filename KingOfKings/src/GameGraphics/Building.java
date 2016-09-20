package GameGraphics;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import Buildings.ArcheryTower;
import Buildings.BallistaTower;
import Buildings.Castle;
import Buildings.Farm;
import Buildings.GiantLiar;
import Buildings.HoundPit;
import Buildings.Mine;
import Buildings.Names;
import Buildings.RoyalPalace;
import Buildings.Spearyard;
import Buildings.Stockpile;
import Buildings.SwordsSmith;
import Buildings.Wall;
import Buildings.WallTower;
import Map.CollisionMap;
import Map.GraphicsCollisionMap;

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
	private int attack;
	private int attackX;
	private int attackY;
	
	private ArrayList<String> unitQueue;
	private Semaphore lock;
	
	public Building(String name,int player){
		
		this.player = player;
		this.name = name;
		cantBuild = false;
		unitQueue = new ArrayList<String>();
		lock = new Semaphore(2);
		
	}

	public Building(float x, float y, String name,int buildingNo,int player){
		
		this.x = x;
		this.y = y;
		this.buildingNo = buildingNo;
		this.player = player;
		unitQueue = new ArrayList<String>();
		
		this.name = name;
		lock = new Semaphore(2);
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
		
//		System.out.println("BUILDING QUEUE START BUILDING");
//		for(int q = 0; q < unitQueue.size(); q++){
//			
//			System.out.println(unitQueue.get(q));
//		}
//		System.out.println("BUILDING QUEUE END BUILDING");
		
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String unit = unitQueue.get(no);
		lock.release();
		
		return unit;
	}
	
	public void addUnitQueue(String unit){
		
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		unitQueue.add(unit);
		lock.release();
	}
	
	public void clearUnitQueue(){
		
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		unitQueue.clear();
		lock.release();
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
		check.setPos(Math.round(this.x), Math.round(this.y));
		
		return check.inBuilding(x, y);
		
	}
	
	public void CanBuildThere(int playerNumber){
		
		Buildings.Building check = GetBuildingClass(getName());
//		System.out.println(map.getPlayer() + " map player " + this.getPlayer() +
//				" Building CanBuildThere");
		if(!name.equals(Names.MINE)){
			int sizeX = check.getSizeX();
			int sizeY = check.getSizeY();
		  
			cantBuild = GraphicsCollisionMap.InArea((int)this.x,(int)this.y,sizeX,sizeY) 
						|| playerNumber != this.getPlayer();
			
		}else{
			//System.out.println(map.getCollisionMap()[(int)this.x][(int)this.y] + " building");
			cantBuild = !(GraphicsCollisionMap.getTile((int)this.x, (int)this.y) == 3)
						|| playerNumber != this.getPlayer();
		}
	}
	
	public boolean cantBuild(){
		
		return this.cantBuild;
	}
	
	public int getAttack(){
		
		return attack;
	}
	
	public void setAttack(int attack,int attackX,int attackY){
		
		this.attack = attack;
		this.attackX = attackX;
		this.attackY = attackY;
	}
	
	public int getAttackX(){
		
		return attackX;
	}
	
	public int getAttackY(){
		
		return attackY;
	}
	
	public boolean IsTowerAttack(){
		
		return attack != -1 && !site;
	}
	
	public void NotEnoughResourcesToBuilding(){
		
		this.cantBuild = true;
	}
	
	public static Buildings.Building GetBuildingClass(String name){
		
		return GetBuildingClass(name,0);
	}
	
	public static Buildings.Building GetBuildingClass(String name,int buildingNo){
		
		Buildings.Building check = null;
		if(name.equals(Names.CASTLE)){
			
			check = new Castle(buildingNo);
		
		}else if(name.equals(Names.ROYALPALACE)){
			
			check = new RoyalPalace(buildingNo);
		
		}else if(name.equals(Names.CASTLE)){
			
			check = new Castle(buildingNo);
		
		}else if(name.equals(Names.FARM)){
			
			check = new Farm(buildingNo);
		
		}else if(name.equals(Names.MINE)){
			
			check = new Mine(buildingNo);
		
		}else if(name.equals(Names.ARCHERYTOWER)){
			
			check = new ArcheryTower(buildingNo);
		
		}else if(name.equals(Names.BALLISTICTOWER)){
			
			check = new BallistaTower(buildingNo);
		
		}else if(name.equals(Names.STOCKPILE)){
			
			check = new Stockpile(buildingNo);
		
		}else if(name.equals(Names.GIANTLIAR)){
			
			check = new GiantLiar(buildingNo);
		
		}else if(name.equals(Names.SPEARYARD)){
			
			check = new Spearyard(buildingNo);
		
		}else if(name.equals(Names.HOUNDPIT)){
			
			check = new HoundPit(buildingNo);
		
		}else if(name.equals(Names.SWORDSSMITH)){
			
			check = new SwordsSmith(buildingNo);
		
		}else if(name.equals(Names.WALL)){
			
			check = new Wall(buildingNo);
		
		}else if(name.equals(Names.WALLTOWER)){
			
			check = new WallTower(buildingNo);
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

	public boolean isWall() {
		// TODO Auto-generated method stub
		return name.equals(Names.WALL);
	}


}
