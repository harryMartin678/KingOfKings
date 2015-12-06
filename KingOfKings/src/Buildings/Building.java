package Buildings;

import java.util.ArrayList;

import GameGraphics.UnitList;
import Map.CollisionMap;
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
	
	
	public Building(int buildingNo){
		
		hitpoints = this.getMaxHitpoint();
		unitQueue = new ArrayList<QueueItem>();
		this.buildingNo = buildingNo;
		
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
		
		public QueueItem(String type){
			
			this.type = type;
			progress = 0;
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
	
	public String getUnitQueue(){
		
		String list = "";
		
		for(int i = 0; i < unitQueue.size(); i++){
			
			list += unitQueue.get(i) + ";";
		}
		
		return list;
	}
	
	public boolean emptyUnitQueue(){
		
		return (unitQueue.size() == 0);
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
	
	
	public int[] getFreeSpace(CollisionMap map,int unitX, int unitY,ArrayList<int[]> taken){
		
		//the corners of the building 
		int[] corners = new int[]{x - this.getSizeX(), y - this.getSizeY(),
				x + this.getSizeX(),y - this.getSizeY()
				,x + this.getSizeX(),y + this.getSizeY(),
				x - this.getSizeX(),y + this.getSizeY()};
		
		//the directions between the corners 
		int[] directions = new int[]{1,0,0,1,-1,0,0,-1};
		
		
		int closestDistance = Integer.MAX_VALUE;
		int[] closestPos = new int[]{-1,-1};
		
		int[][] mapArray = map.getCollisionMap();
		
		//go though each corner
		for(int c = 0; c < corners.length; c+=2){
			
			//Abs(directions[c] + direction[c+1]) <= 1 
			//travel between corners checking for the closet free square as you go 
			for(int l = 0; l < (directions[c] * this.getSizeX()) 
					+ (directions[c+1] * this.getSizeY())+1; l+= directions[c] + directions[c+1]){
				
				//calculates the currently checked square 
				int cx = corners[c] + (directions[c] * l);
				int cy = corners[c+1] + (directions[c+1] * l);
				
				//if this square is free and on the map
				if(cx >= 0 && cy >= 0 && cx < mapArray.length && cy < mapArray[0].length &&
						mapArray[cx][cy] == 0 ){
					
					int distance = Math.abs(cx - unitX) + Math.abs(cy - unitY);

					//if it is the closest so far seen then set it as the current closet point
					if(distance < closestDistance && !OnTakenList(taken,new int[]{cx,cy})){
						
						closestDistance = distance;
						closestPos[0] = cx;
						closestPos[1] = cy;
					}
				}
			}
		}
		
		return closestPos;
	}
	
	public static boolean OnTakenList(ArrayList<int[]> list,int[] obj){
		
		for(int p = 0; p < list.size(); p++){
			
			if(obj[0] == list.get(p)[0] && obj[1] == list.get(p)[1]){
				
				return true;
			}
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		
//		RoyalPalace building = new RoyalPalace(0);
//		
//		building.setPos(2, 2);
//		
//		BuildingList buildings = new BuildingList();
//		
//		int[] pos = building.getFreeSpace(new CollisionMap(new BuildingList(),new UnitList(0),new Map(10,10)),
//				7, 7);
//		
//		
//		System.out.println(pos[0] + " " + pos[1] + " closestPos");
		
	}
	
	

}
