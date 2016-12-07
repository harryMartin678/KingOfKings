package GameServer;

import java.util.ArrayList;
import java.util.HashMap;

import Buildings.Building;
import Buildings.Farm;
import Map.CollisionMap;
import Map.GameEngineCollisionMap;
import Util.Point;
import Util.Vector;

public class AddUnitModule {

	//private ArrayList<int[]> taken;
	private int buildingNo;
	private Building building;
	
	public AddUnitModule(){
		
		//taken = new ArrayList<int[]>();
	}
	
	public void AddUnit(String unit, Building building,GameEngineContext context){
		
		this.building = building;
//		CollisionMap map = new CollisionMap(context.buildings,context.units,
//				context.maps.getMap(building.getMap()),building.getMap());
		//map.printCollisionMap();
		//context.units.printUnits(building.getMap());
		HashMap<Integer,int[]> taken = new HashMap<Integer, int[]>();
		int[] pos = getFreeSpace(building.getMap(), building.getX() + building.getSizeX()+1, 
					building.getY() + building.getSizeY() + 1, taken);
		
		this.buildingNo = building.getBuildingNo();
		
		//taken.add(pos);
		//System.out.println(unit + " " + pos[0] + " " + pos[1]);
		//add check for no space for unit 
		if(!(pos[0] == -1 || pos[1] == -1)){
			context.units.addUnit(unit, building.getMap(), pos[0],pos[1], building.getPlayer());
		}else{
			//System.out.println("put back  addUnitModule");
			building.putBackOnUnitQueue(unit);
		}
	}
	
	public void setBuilding(Building building){
		
		this.building = building;
	}
	
	
	public int[] getFreeSpace(int mapNo,int unitX, int unitY, HashMap<Integer,int[]> taken){
		
		Point[] corners = new Point[]{
				new Point(building.getX() - building.getSizeX(), building.getY()- building.getSizeY()),
				new Point(building.getX() + building.getSizeX(),building.getY() - building.getSizeY()),
				new Point(building.getX() + building.getSizeX(),building.getY() + building.getSizeY()),
				new Point(building.getX() - building.getSizeX(),building.getY() + building.getSizeY())};
		
		Vector[] lines = new Vector[]{
				new Vector(corners[0],corners[1]),
				new Vector(corners[1],corners[2]),
				new Vector(corners[2], corners[3]),
				new Vector(corners[3],corners[0])
		};
		
		ArrayList<int[]> toLook = new ArrayList<int[]>();
		
		for(int t = 0; t < lines.length; t++){
			
			toLook.addAll(lines[t].getStages());
		}
		
		int closestDistance = Integer.MAX_VALUE;
		int[] closestPos = new int[]{-1,-1};
		
		for(int t = 0; t < toLook.size(); t++){
			
			int cx = toLook.get(t)[0];
			int cy = toLook.get(t)[1];
			int distance = Math.abs(cx - unitX) + Math.abs(cy - unitY);
			if(cx >= 0 && cy >= 0 && cx < GameEngineCollisionMap.getSizeX(mapNo) && 
					cy < GameEngineCollisionMap.getSizeY(mapNo) && 
					GameEngineCollisionMap.getTile(cx, cy, mapNo) == 0 && 
					!taken.containsKey(Point.GetUniqueNo(toLook.get(t))) && distance < closestDistance){
				
				closestDistance = distance;
				closestPos = toLook.get(t);
			}
		}
		
		return closestPos;
		
		
	}
	
//	//not working needs debugging, only shows two sides of a building
//	public int[] getFreeSpace(int mapNo,int unitX, int unitY,ArrayList<int[]> taken,
//			int[][] mapArray){
//		
//
//		//the corners of the building 
//		int[] corners = new int[]{
//				building.getX() - building.getSizeX(), building.getY()- building.getSizeY(),
//				building.getX() + building.getSizeX(),building.getY() - building.getSizeY(),
//				building.getX() + building.getSizeX(),building.getY() + building.getSizeY(),
//				building.getX() - building.getSizeX(),building.getY() + building.getSizeY()};
//		
//		//the directions between the corners 
//		int[] directions = new int[]{1,0,0,1,-1,0,0,-1};
//		
//		
//		int closestDistance = Integer.MAX_VALUE;
//		int[] closestPos = new int[]{-1,-1};
//		
//		//int[][] mapArray = GameEngineCollisionMap.toArray(mapNo);
//		ArrayList<int[]> onMap = new ArrayList<int[]>();
//		
//		//go though each corner
//		for(int c = 0; c < corners.length; c+=2){
//			//Abs(directions[c] + direction[c+1]) <= 1 
//			//travel between corners checking for the closet free square as you go 
//			for(int l = 0; Math.abs(l) < Math.abs((directions[c] * (building.getSizeX())))
//					+ Math.abs((directions[c+1] * (building.getSizeY()))); l+= directions[c] 
//							+ directions[c+1]){
//				
//				//calculates the currently checked square 
//				int cx = corners[c] + (directions[c] * l);
//				int cy = corners[c+1] + (directions[c+1] * l);
//			
//				//System.out.println("{" + cx + "," + cy + "}");
//				//mapArray[cy][cx] = 3;
//				onMap.add(new int[]{cx,cy});
//				//if this square is free and on the map
//				if(cx >= 0 && cy >= 0 && cx < mapArray.length && cy < mapArray[0].length &&
//						mapArray[cx][cy] == 0 ){
//					int distance = Math.abs(cx - unitX) + Math.abs(cy - unitY);
//					//if it is the closest so far seen then set it as the current closet point
//					if(distance < closestDistance && !OnTakenList(taken,new int[]{cx,cy})){
//						
//						closestDistance = distance;
//						closestPos[0] = cx;
//						closestPos[1] = cy;
//					}
//				}
//			}
//		}
//		
////		System.out.println("ADDUNITMODULE START///////");
////		for(int y = 0; y < mapArray.length; y++){
////			for(int x = 0; x < mapArray[0].length; x++){
////				
////				System.out.print(mapArray[y][x]);
////			}
////			
////			System.out.println();
////		}
////		System.out.println("ADDUNITMODULE END//////");
//		
//		return closestPos;
//			
//	}
		
	public static boolean OnTakenList(ArrayList<int[]> list,int[] obj){
		
		for(int p = 0; p < list.size(); p++){
			
			if(obj[0] == list.get(p)[0] && obj[1] == list.get(p)[1]){
				
				return true;
			}
		}
		
		return false;
	}
	
//	public int getBuildingNo(){
//		
//		return buildingNo;
//	}
//	
//	public void takeAwayFromTaken(int[] pos){
//		
//		for(int t = 0; t < taken.size(); t++){
//			
//			if(taken.get(t)[0] == pos[0] && taken.get(t)[1] == pos[1]){
//				
//				taken.remove(t);
//			}
//		}
//	}
	
//	public static void main(String[] args) {
//		
//		AddUnitModule addUnit = new AddUnitModule();
//		Farm farm = new Farm(0);
//		farm.setPos(5, 5);
//		addUnit.setBuilding(farm);
//		HashMap<Integer,Boolean> taken = new HashMap<Integer,Boolean>();
//		int[][] map = new int[][]{
//			{0,0,0,0,0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0,0,0,0,0},
//			{0,0,0,0,0,0,0,0,0,0,0,0}
//		};
//		
//		for(int t = 0; t < 20; t++){
//			taken.put(Point.GetUniqueNo(addUnit.alternativeFreeSpace(0, 0, 0, taken,
//					map)),true);
//			//System.out.println(taken.get(taken.size()-1)[0] + " " + taken.get(taken.size()-1)[1]);
//		}
//		
//		for(int x = 0; x < map.length; x++){
//			for(int y = 0; y < map[x].length; y++){
//				
//				if(taken.containsKey(Point.GetUniqueNo(new int[]{x,y}))){
//					
//					System.out.print("* ");
//				
//				}else if(farm.inBuilding(x, y)){
//				
//					System.out.print("X ");
//				}else{
//					
//					System.out.print(map[x][y] + " ");
//				}
//			}
//			System.out.println();
//		}
//		
//		//System.out.println(pos[0] + " " + pos[1] + " AddUnitModule");
//	}
}
