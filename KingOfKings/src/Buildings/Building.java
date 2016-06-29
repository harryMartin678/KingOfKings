package Buildings;

import java.util.ArrayList;

import GameGraphics.UnitList;
import GameServer.AddUnitModule;
import GameServer.GameEngineContext;
import Map.CollisionMap;
import Map.GameEngineCollisionMap;
import Map.Map;

public class Building {
	
	private int x;
	private int y;
	private int hitpoints;
	private int player;
	private int map;
	private ArrayList<QueueItem> unitQueue;
	private int buildingNo;
	private boolean justBuilt;
	private AddUnitModule addUnit;
	
	
	public Building(int buildingNo){
		
		hitpoints = this.getMaxHitpoint();
		unitQueue = new ArrayList<QueueItem>();
		this.buildingNo = buildingNo;
		addUnit = new AddUnitModule();
		addUnit.setBuilding(this);
		
		justBuilt = true;
	}
	
	public boolean destroyed(){
		
		return (hitpoints <= 0);
	}
	
	public void SetBuildingNo(int buildingNo){
		
		this.buildingNo = buildingNo;
	}
	
	public class QueueItem{
		
		private String type;
		private int progress;
		private boolean delayed;
		
		public QueueItem(String type){
			
			this.type = type;
			progress = 0;
			delayed = false;
		}
		
		public QueueItem(String type, int progress){
			
			this.type = type;
			this.progress = progress;
			delayed = true;
		}
		
		public boolean isDelayed(){
			
			if(delayed){
				
				delayed = false;
				return true;
				
			}else{
				
				return false;
			}
		}
		
		public void progress(){
			
			progress++;
		}
		
		public int getProgress(){
			
			return progress;
		}
		
		public String getType(){
			
			return type;
		}
		
		public boolean finished(){
			
			return (progress >= 100);
		}
	}
	
	public String getType(){
		
		return "";
	}
	
	public void addToUnitQueue(String unit){
		
		unitQueue.add(new QueueItem(unit));
	}
	
	public boolean justBuilt(){
		
		boolean temp = justBuilt;
		justBuilt = false;
		
		return temp;
	}
	
	public int[] getFreeSpace(int unitX, int unitY,ArrayList<int[]> taken){
		
		return addUnit.getFreeSpace(map,unitX, unitY,taken);
	}
	
	public String getUnitQueue(){
		
		String list;
		
		if(unitQueue.size() > 0){
			list = new Integer(unitQueue.get(0).getProgress()).toString() + " ";
		}else{
			list = "";
		}
		
		for(int i = 0; i < unitQueue.size(); i++){
			
			list += unitQueue.get(i).getType() + " ";
		}
		
		if(list != ""){
			
			list += "\n";
		}
		
		
		//System.out.println("building queue list " + list);
		return list;
	}
	
	public boolean emptyUnitQueue(){
		
		 if(unitQueue.size() == 0){
			 
			 return true;
			 
		 }else{
			 
			 return unitQueue.get(unitQueue.size()-1).isDelayed();
		 }
	}
	
	public boolean progressUnitQueue(){
		
		unitQueue.get(0).progress();
		
		//System.out.println(unitQueue.get(0).getProgress());
		
		return unitQueue.get(0).finished();
		
	}
	
	public String removeUnit(){
		
		String lastUnit = unitQueue.get(0).getType();
		unitQueue.remove(0);
		
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
		
		if(destroyed()){
			
			GameEngineCollisionMap.removeBuilding(this.buildingNo, this.map);
		}
	}
	
	public void addUnit(String unit,GameEngineContext context){
		
		addUnit.AddUnit(unit, this, context);
	}
	
	public int getSizeX(){
		
		return 0;
	}
	
	public int getSizeY(){
		
		return 0;
	}
	
	public boolean inBuilding(int cx, int cy){
		
		return (Math.abs(x-cx) < this.getSizeX() && Math.abs(y-cy) < this.getSizeY());
	}
	
	public void setPos(int x, int y){
		
		this.x = x;
		this.y = y;
	}

	public int getBuildingNo() {
		// TODO Auto-generated method stub
		return buildingNo;
	}
	
	public String[] getUnitQueueItem(int index){
		
		return new String[]{unitQueue.get(index).getType(),
				new Integer(unitQueue.get(index).getProgress()).toString()};
	}
	
	public int getUnitQueueSize(){
		
		return unitQueue.size();
	}
	
	public String unitcreated(){
		
		return "";
	}
	
	public int GoldNeeded(){
		
		return 0;
	}
	
	public int FoodNeeded(){
		
		return 0;
	}
	
	
	
	public static void main(String[] args) {
		
//		Stockpile building = new Stockpile(0);
//		
//		building.setPos(5, 5);
//		
//		BuildingList buildings = new BuildingList();
//		
//		ArrayList<int[]> taken = new ArrayList<int[]>();
//		
//		//for(int i = 0; i < 10; i++){
//			taken.add(building.getFreeSpace(new CollisionMap(new BuildingList(),
//					new UnitList(0),new Map(10,10)),0, 0,taken));
//		//}
//		
//		int[][] map = new int[10][10];
//		
//		for(int x = 0; x < map.length; x++){
//			for(int y = 0; y < map[0].length; y++){
//				
//				if(building.inBuilding(x, y)){
//					
//					map[x][y] = 2;
//					
//				}else if(Building.OnTakenList(taken,new int[]{x,y})){
//					
//					map[x][y] = 1;
//					
//				}else{
//					
//					map[x][y] = 0;
//				}
//			}
//		}
//		
//		for(int y = 0; y < map.length; y++){
//			for(int x = 0; x < map[0].length; x++){
//				
//				System.out.print(map[y][x]);
//			}
//			
//			System.out.println();
//		}
		
		//System.out.println(pos[0] + " " + pos[1] + " closestPos");
		
	}

	public void putBackOnUnitQueue(String unit) {
		// TODO Auto-generated method stub
		unitQueue.add(0,new QueueItem(unit,75));
	}

	public void regenerate() {
		// TODO Auto-generated method stub
		hitpoints = this.getMaxHitpoint();
	}
	
	

}
