package Buildings;

import java.util.ArrayList;

public class Building {
	
	private int x;
	private int y;
	private int hitpoints;
	private int player;
	private int map;
	private ArrayList<String> unitQueue;
	private int buildingNo;
	
	
	public Building(int buildingNo){
		
		hitpoints = this.getMaxHitpoint();
		unitQueue = new ArrayList<String>();
		this.buildingNo = buildingNo;
	}
	
	public void addToUnitQueue(String unit){
		
		unitQueue.add(unit);
	}
	
	public String getUnitQueue(){
		
		String list = "";
		
		for(int i = 0; i < unitQueue.size(); i++){
			
			list += unitQueue.get(i) + ";";
		}
		
		return list;
	}
	
	public String removeUnit(){
		
		String lastUnit = unitQueue.get(unitQueue.size()-1);
		unitQueue.remove(unitQueue.size()-1);
		
		return lastUnit;	
	}
	
	public int getX(){
		
		return x;
	}
	
	public int getY(){
		
		return y;
	}
	
	public int getMaxHitpoint(){
		
		return 0;
	}
	
	public int getHitpoints(){
		
		return hitpoints;
	}
	
	public int getBuildTime(){
		
		return 1;
	}
	
	public int getMap(){
		
		return map;
	}
	
	public void setMap(int map){
		
		this.map = map;
	}
	
	public int getPlayer(){
		
		return player;
	}
	
	public void setPlayer(int player){
		
		this.player = player;
	}
	
	public void removeHitpoints(int amount){
		
		hitpoints -= amount;
	}
	
	
	
	public int getSizeX(){
		
		return 0;
	}
	
	public int getSizeY(){
		
		return 0;
	}
	
	public boolean inBuilding(int cx, int cy){
		
		return (Math.abs(x-cx) <= this.getSizeX()/2 && Math.abs(y-cy) <= this.getSizeY()/2);
	}
	
	public void setPos(int x, int y){
		
		this.x = x;
		this.y = y;
	}

	public int getBuildingNo() {
		// TODO Auto-generated method stub
		return buildingNo;
	}
	
	

}
