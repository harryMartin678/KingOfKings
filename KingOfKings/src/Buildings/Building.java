package Buildings;

import java.util.ArrayList;

public class Building {
	
	private int x;
	private int y;
	private int hitpoints;
	private int player;
	private int map;
	private ArrayList<QueueItem> unitQueue;
	private int buildingNo;
	private boolean justBuilt;
	private String type;
	
	
	public Building(int buildingNo){
		
		hitpoints = this.getMaxHitpoint();
		unitQueue = new ArrayList<QueueItem>();
		this.buildingNo = buildingNo;
		
		justBuilt = true;
	}
	
	public boolean destroyed(){
		
		return (hitpoints <= 0);
	}
	
	public class QueueItem{
		
		private String type;
		private int progress;
		
		public QueueItem(String type){
			
			this.type = type;
			progress = 0;
		}
		
		public void progress(){
			
			progress++;
		}
		
		public String getType(){
			
			return type;
		}
		
		public boolean finished(){
			
			return (progress >= 100);
		}
	}
	
	public String getType(){
		
		return type;
	}
	
	public void addToUnitQueue(String unit){
		
		unitQueue.add(new QueueItem(unit));
	}
	
	public boolean justBuilt(){
		
		boolean temp = justBuilt;
		justBuilt = false;
		
		return temp;
	}
	
	public String getUnitQueue(){
		
		String list = "";
		
		for(int i = 0; i < unitQueue.size(); i++){
			
			list += unitQueue.get(i) + ";";
		}
		
		return list;
	}
	
	public boolean progressUnitQueue(){
		
		unitQueue.get(0).progress();
		
		return unitQueue.get(0).finished();
	}
	
	public String removeUnit(){
		
		String lastUnit = unitQueue.get(unitQueue.size()-1).getType();
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
	
	public String unitcreated(){
		
		return "";
	}
	
	

}
