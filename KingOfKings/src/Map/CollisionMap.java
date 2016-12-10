package Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Semaphore;

import Buildings.Building;
import Buildings.Farm;
import Units.UnitList;
import Util.Point;

public class CollisionMap {

	private int[][] CollisionMap;
	private boolean[][] VisibleMap;
	private HashMap<Integer,int[]> UnitToPos;
	private HashMap<Integer,Integer> PosToUnit;
	private HashMap<Integer,int[]> BuildingToPos;
	private HashMap<Integer,Boolean> visibilityCheck;
	private HashMap<Integer,Integer> overlap;
	private HashMap<Integer,int[]> buildingNoToMineSpot;
	private HashMap<Integer,Integer> mineSpotToBuildingNo;
	private Semaphore lock;
	private boolean fogOfWar;
	private int mapNo;
	
	
	public CollisionMap(boolean fogOfWar,int mapNo){
		
		lock = new Semaphore(3);
		UnitToPos = new HashMap<Integer, int[]>();
		PosToUnit = new HashMap<Integer, Integer>();
		BuildingToPos = new HashMap<Integer, int[]>();
		mineSpotToBuildingNo = new HashMap<Integer, Integer>();
		buildingNoToMineSpot = new HashMap<Integer,int[]>();
		visibilityCheck = new HashMap<Integer, Boolean>();
		overlap = new HashMap<Integer, Integer>();
		this.fogOfWar = fogOfWar;
		this.mapNo = mapNo;
		
	}
	
	public int getMapNo(){
		
		return mapNo;
	}
	
	public void RefreshCollisionMap(int[][] map,boolean[][] VisibleMap){
		
		//System.out.println("REFRESH COLLISIONMAP");
		CollisionMap = new int[map.length][map[0].length];
		
		for(int x = 0; x < map.length; x++){
			for(int y = 0; y < map[x].length; y++){
				
				CollisionMap[x][y] = map[x][y];
			}
		}
		
		if(fogOfWar){
			if(VisibleMap == null){
				
				this.VisibleMap = new boolean[CollisionMap.length][CollisionMap[0].length];
			
			}else{
				
				if(this.VisibleMap == null){
					
					this.VisibleMap = new boolean[CollisionMap.length][CollisionMap[0].length];
				}
				
				for(int x = 0; x < VisibleMap.length; x++){
					for(int y = 0; y < VisibleMap[x].length; y++){
						
						this.VisibleMap[x][y] = VisibleMap[x][y];
					}
				}
			}
		}
	}
	
	public void Begin(){
		
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void End(){
		
		lock.release();
	}
	
	public boolean inFog(int x, int y){
		
		return VisibleMap == null || VisibleMap[x][y];
	}
	
	public void addVisiblilty(int newX,int newY){
		
		if(!visibilityCheck.containsKey(new int[]{newX,newY})){
			for(int x = newX - 10; x < newX + 10; x++){
				for(int y = newY - 10; y < newY + 10; y++){
					
					if(x >= 0 && y >=0 && x < VisibleMap.length && y < VisibleMap[0].length){
						VisibleMap[x][y] = true;
					}
					
				}
			}
			
			visibilityCheck.put(Point.GetUniqueNo(new int[]{newX,newY}),true);
		}
	}
	
	public void RemoveUnit(int unitNo){
		
		int[] pos = UnitToPos.get(unitNo);
//		System.out.println("Start Remove CollisionMap " + unitNo);
//		for(int u = 0; u < UnitToPos.size(); u++){
//			
//			System.out.println(UnitToPos.keySet().toArray()[u] + " CollisionMap");
//		}
//		System.out.println("End Remove CollisionMap");
		if(pos != null){
			UnitToPos.remove(unitNo);
			PosToUnit.remove(Point.GetUniqueNo(pos));
			
			if(overlap.containsKey(Point.GetUniqueNo(new int[]{pos[0],pos[1]}))){
				
				overlap.remove(Point.GetUniqueNo(new int[]{pos[0],pos[1]}));
				
			}else{
				
				CollisionMap[pos[0]][pos[1]] = 0;
			}
			
			
		}else{
			
			System.out.println("ERROR NULL POS IN REMOVE UNIT COLLISION MAP: " + unitNo);
		}
	}
	
	public void moveUnit(int unitNo,int newX,int newY,boolean isWorker){
		
		int[] pos = UnitToPos.get(unitNo);
		
		if(!(pos[0] == newX && pos[1] == newY) && pos != null){
			
			if(CollisionMap[pos[0]][pos[1]] == 9 || CollisionMap[pos[0]][pos[1]] == 7){
				
				CollisionMap[pos[0]][pos[1]] = 0;
				
			}else{
				
				overlap.remove(Point.GetUniqueNo(new int[]{pos[0],pos[1]}));
			}
			
			UnitToPos.replace(unitNo, new int[]{newX,newY});
			PosToUnit.remove(Point.GetUniqueNo(pos));
			PosToUnit.put(Point.GetUniqueNo(new int[]{newX,newY}), unitNo);
			
			if(CollisionMap[newX][newY] == 0){
				
				if(isWorker){
					
					CollisionMap[newX][newY] = 9;
					
				}else{
					
					CollisionMap[newX][newY] = 7;
					
				}
				
			}
			
			if(fogOfWar){
				
				addVisiblilty(newX, newY);
				
			}
		}
	}
	
	public void addUnit(int x, int y,int unitNo,boolean isWorker){
	
		UnitToPos.put(unitNo, new int[]{x,y});
		PosToUnit.put(Point.GetUniqueNo(new int[]{x,y}), unitNo);
		
//		if(isGraphics){
//			System.out.println("Start Add CollisionMap " + unitNo);
//			for(int u = 0; u < UnitToPos.size(); u++){
//				
//				System.out.println(UnitToPos.keySet().toArray()[u] + " CollisionMap");
//			}
//			System.out.println("End Add CollisionMap");
//		}

		if(CollisionMap[x][y] == 0){
			if(isWorker){
				
				CollisionMap[x][y] = 9;
				
			}else{
				
				CollisionMap[x][y] = 7;
			}
		}
		
		
		if(fogOfWar){
			
			addVisiblilty(x, y);
		}
	}
	
	public void addBuilding(int x, int y,int SizeX, int SizeY,int buildingNo,
			boolean isWall,int playerNo){
		
//		System.out.println("AddBuilding Start CollisionMap");
//		for(int b = 0; b < BuildingToPos.size(); b++){
//			
//			System.out.println(BuildingToPos.keySet().toArray()[b]);
//		}
//		System.out.println("AddBuilding End");
		BuildingToPos.put(buildingNo, new int[]{x,y});
		
		
		for(int mx = -SizeX + x; mx < SizeX + x; mx++){
			for(int my = -SizeY + y; my < SizeY + y; my++){
				
//				if(mx >= 0 && my >= 0 && mx < CollisionMap.length 
//						&& my < CollisionMap[mx].length){
//					
//					continue;
//				}
				
				//System.out.println(mx + " " + my + " " + buildingNo + " CollisionMap");
				
				if(CollisionMap[mx][my] == 7 || CollisionMap[mx][my] == 9){
				
					overlap.put(Point.GetUniqueNo(new int[]{mx,my}),CollisionMap[mx][my]);
					
				}else if(CollisionMap[mx][my] == 3){
					
					//takenMineSpots.put(buildingNo, Point.GetUniqueNo(new int[]{mx,my}));
					System.out.println("mine at: " + mx + " " + my + " CollisionMap");
					buildingNoToMineSpot.put(buildingNo, new int[]{mx,my});
					mineSpotToBuildingNo.put(Point.GetUniqueNo(new int[]{mx,my}), buildingNo);
				}
			
				
				if(isWall){
					
					CollisionMap[mx][my] = new Integer("8"+new Integer(playerNo).toString()).intValue();
					
				}else{
					
					CollisionMap[mx][my] = 8;
				}
				
				
			}
		}
		if(fogOfWar){
			//System.out.println("ADD BUILDING VISIBILITY COLLISIONMAP");
			addVisiblilty(x, y);
		}
	}
	
	public void removeBuilding(int buildingNo,int SizeX,int SizeY){

		//System.out.println(buildingNo + " RemoveBuilding CollisionMap");
		int[] pos = BuildingToPos.get(buildingNo);
		BuildingToPos.remove(buildingNo);
		
		if(buildingNoToMineSpot.containsKey(buildingNo)){
			
			int[] minPos = buildingNoToMineSpot.get(buildingNo);
			buildingNoToMineSpot.remove(buildingNo);
			mineSpotToBuildingNo.remove(Point.GetUniqueNo(minPos));
		}
		
		for(int mx = -SizeX + pos[0]; mx < SizeX + pos[0]; mx++){
			for(int my = -SizeY + pos[1]; my < SizeY + pos[1]; my++){
				
				CollisionMap[mx][my] = 0;
			}
		}
	}
	
	public int getTile(int x,int y){
		
		return CollisionMap[x][y];
	}
	
	public boolean InArea(int x, int y, int SizeX, int SizeY){
		
		if(x - SizeX < 0 || x + SizeX >= CollisionMap.length 
				|| y - SizeY < 0 || y + SizeY >= CollisionMap[0].length){

			return true;
		}
		
		for(int ay = -SizeY + y; ay <= SizeY + y; ay++){
			for(int ax = -SizeX + x; ax <= SizeX + x; ax++){
			
				if(CollisionMap[ax][ay] != 0 || (fogOfWar && !VisibleMap[ax][ay])){
					
					return true;
				}
			}
		}
		
		return false;
	}

	public int[][] ToArray() {
		// TODO Auto-generated method stub
		return CollisionMap;
	}
	
	public int[][] ToArray(int ignoreUnit){
		
		int[][] copy = new int[CollisionMap.length][CollisionMap[0].length];
		
		for(int r = 0;r < CollisionMap.length; r++){
			
			copy[r] = CollisionMap[r].clone();
		}
		
		int[] pos = UnitToPos.get(ignoreUnit);
		
//		if(pos == null){
//			
//			for(int u = 0; u < UnitToPos.size(); u++){
//				
//				System.out.println(UnitToPos.keySet().toArray()[u] + " CollisionMap");
//			}
//		}
		
		if(pos != null){
			copy[pos[0]][pos[1]] = 0;
		}
		
		return copy;
	}

	public int IsUnitInFront(int unitNo,int[] direction) {
		// TODO Auto-generated method stub
		int[] pos = UnitToPos.get(unitNo);
		
		if(pos == null){
			
			return -1;
		}
		
		//System.out.println(CollisionMap[pos[0] + direction[0]][pos[0] + direction[1]]
			//	+ " CollisionMap");
//		System.out.println("start unit");
//		for(int u = 0; u < UnitToPos.size(); u++){
//			
//			System.out.println((UnitToPos.keySet().toArray()[u]) + " Unit");
//		}
//		System.out.println("end unit");
		
		//System.out.println((pos == null) + " " + (direction == null) + " Unit");
		int lx = pos[0] + direction[0];
		int ly = pos[1] + direction[1];
		if(lx >= 0 && lx < CollisionMap.length && ly >= 0 && ly < CollisionMap[0].length &&
				CollisionMap[lx][ly] == 7){
			
			int[] targetPos = new int[]{lx,ly};

			return PosToUnit.get(Point.GetUniqueNo(targetPos));
			
		}else{
			return -1;
		}
		//return CollisionMap[pos[0] + direction[0]][pos[0] + direction[1]] != 0;
		
	}
	
	
	
	public void PrintCollisionMap(){
		
		PrintCollisionMap(0,CollisionMap.length,0,CollisionMap[0].length);
	}
	
	public void PrintCollisionMap(int fromX,int toX,int fromY,int toY){
		
		for(int x = fromX; x <= toX; x++){
			for(int y = fromY; y <= toY; y++){
				
				System.out.print(CollisionMap[x][y] + "|");
			}
			
			System.out.println();
		}
	}

	public int getWidth() {
		// TODO Auto-generated method stub
		return CollisionMap.length;
	}

	public int getHeight() {
		// TODO Auto-generated method stub
		return CollisionMap[0].length;
	}

	public int FindEnemy(int unitNo, UnitList units) {
		// TODO Auto-generated method stub
		
		int unitX = Math.round(units.getUnitX(unitNo));
		int unitY = Math.round(units.getUnitY(unitNo));
		
		int enemyNo = -1;
		int distance = Integer.MAX_VALUE;
		
		for(int x = unitX - 25; x < unitX + 25; x++){
			for(int y = unitY - 25; y < unitY + 25; y++){
				
				if(x < 0 || x >= CollisionMap.length || y < 0 || y >= CollisionMap[0].length
						|| (x == unitX && y == unitY)){
					
					continue;
				}
				
				if(PosToUnit.containsKey(Point.GetUniqueNo(new int[]{x,y}))){
					
					int found = PosToUnit.get(Point.GetUniqueNo(new int[]{x,y}));
					
					if(units.getUnitPlayer(found) != units.getUnitPlayer(unitNo)
							&& Math.abs(x) + Math.abs(y) < distance){
						
//						System.out.println("FOUND ENEMY: " + unitNo + " " + units.getUnitPlayer(unitNo) 
//						+ " " + units.getUnits(unitNo).getName() + " " + found + " " +
//								units.getUnitPlayer(found) + " " + units.getUnits(found).getName() + 
//								" " + unitX + " " + unitY + " " + x + " " + y + " CollisionMap");
						enemyNo = found;
						distance = Math.abs(x) + Math.abs(y);
					}
				}
			}
		}
		
		//System.out.println(units.getUnits(enemyNo).getName() + " " + units.getUnits(unitNo).getName()
			//	+ " CollisionMap");
		return enemyNo;
	}
	
	public int getRequiredWallSize(){
		
		int centerX = CollisionMap.length/2;
		int centerY = CollisionMap[0].length/2;
		int[] center = new int[]{centerX,centerY};
		
		int furthestPoint = 0;
		
		for(int[] position : BuildingToPos.values()){
			
			furthestPoint = Math.max(furthestPoint, getStraightDis(center,position));
		}
		
		return furthestPoint;
	}
	
	private int getStraightDis(int[] center, int[] pos){
		
		return (int) Math.round(Math.sqrt(Math.pow(center[0] - pos[0], 2) + Math.pow(center[1] - pos[1], 2)));
	}
	
	public ArrayList<Integer> getWorkers(){
		
		ArrayList<Integer> workers = new ArrayList<Integer>();
		
		Set<Entry<Integer,int[]>> units = UnitToPos.entrySet();
		for(Entry<Integer,int[]> unit:units){
			
			int x = unit.getValue()[0];
			int y = unit.getValue()[1];
			if(CollisionMap[x][y] == 9 || (overlap.containsKey(Point.GetUniqueNo(new int[]{x,y})) &&
							overlap.get(Point.GetUniqueNo(new int[]{x,y})) == 9)){
				
				workers.add(unit.getKey());
			}
		}
		
		return workers;
	}
	
	public ArrayList<Integer> FindBuildings(){
		
		return new ArrayList<Integer>(BuildingToPos.keySet());
	}

	public ArrayList<Integer> FindWorkers(int sx, int sy, int sizeX, int sizeY) {
		// TODO Auto-generated method stub
		ArrayList<Integer> workers = new ArrayList<Integer>();
		
		for(int x = sx - sizeX - 25; x < sx + sizeX + 25; x++){
			for(int y = sy - sizeY - 25; y < sy + sizeY + 25; y++){
				
				if(x < 0 || x >= CollisionMap.length || y < 0 || y >= CollisionMap[0].length
						|| (x == sx && y == sy)){
					
					continue;
				}
				
				if(CollisionMap[x][y] == 9 || 
						(overlap.containsKey(Point.GetUniqueNo(new int[]{x,y})) &&
								overlap.get(Point.GetUniqueNo(new int[]{x,y})) == 9)){
					
					workers.add(PosToUnit.get(Point.GetUniqueNo(new int[]{x,y})));
				}
			}
		}
		
		return workers;
	}

	public boolean[][] getVisibleMap() {
		// TODO Auto-generated method stub
		return VisibleMap;
	}

	public int[] FindBuildingSpot(String name,Random generator) {
		// TODO Auto-generated method stub
		int cx;
		int cy;
		do{
			
			cx = (int)(generator.nextGaussian() * 8) + (CollisionMap.length/2);
			cy = (int)(generator.nextGaussian() * 8) + (CollisionMap[0].length/2);
			
		}while(!isLegalBuildingSpot(cx,cy,name));
		
		return new int[]{cx,cy};
	}
	
	private boolean isLegalBuildingSpot(int x, int y,String name){
		
		Building building = GameGraphics.Building.GetBuildingClass(name);
		return x + building.getSizeX() < CollisionMap.length && x - building.getSizeX() >= 0 
				&& y + building.getSizeX() < CollisionMap[0].length && y - building.getSizeX() >=0
				&& !InArea(x, y, building.getSizeX(), building.getSizeY());
	}

	public int[] FindMineSpot() {
		// TODO Auto-generated method stub
		
		int cx = CollisionMap.length/2;
		int cy = CollisionMap[0].length/2;
		
//		for(int buildingNo : buildingNoToMineSpot.keySet()){
//			
//			System.out.println(buildingNo + " " + buildingNoToMineSpot.get(buildingNo)[0] + " "
//			+ buildingNoToMineSpot.get(buildingNo)[1] + " CollisionMap");
//		}
//		
		int ry = 0;
		int rx = 0;
		while(true){
			
			for(int x = cx - rx; x < cx + rx; x++){
				for(int y = cy - ry; y < cy + ry; y++){
					//System.out.print(CollisionMap[x][y] + " ");
					if(CollisionMap[x][y] == 3 && 
							!mineSpotToBuildingNo.containsKey(Point.GetUniqueNo(new int[]{x,y}))){
						
						return new int[]{x,y};
					}
				}
				//System.out.println();
			}
			
			boolean xBool = cx - rx >= 0 && cx + rx < CollisionMap.length;
			if(xBool){
				
				rx++;
			}
			
			boolean yBool = cy - ry >= 0 && cy + ry < CollisionMap[0].length;
			if(yBool){
				
				ry ++;
			}
			
			
			if(!xBool && !yBool){
				
				break;
			}
			
		}
		
		return new int[]{-1,-1};
	}

	public int[] getCenter() {
		// TODO Auto-generated method stub
		return new int[]{CollisionMap.length/2,CollisionMap[0].length/2};
	}

}
