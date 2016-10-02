package Map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import Units.UnitList;

public class CollisionMap {

	private int[][] CollisionMap;
	private boolean[][] VisibleMap;
	private HashMap<Integer,int[]> UnitToPos;
	private HashMap<Integer,Integer> PosToUnit;
	private HashMap<Integer,int[]> BuildingToPos;
	private HashMap<Integer,Boolean> visibilityCheck;
	private Semaphore lock;
	private boolean fogOfWar;
	private int mapNo;
	
	
	public CollisionMap(boolean fogOfWar,int mapNo){
		
		lock = new Semaphore(3);
		UnitToPos = new HashMap<Integer, int[]>();
		PosToUnit = new HashMap<Integer, Integer>();
		BuildingToPos = new HashMap<Integer, int[]>();
		visibilityCheck = new HashMap<Integer, Boolean>();
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
			
			visibilityCheck.put(GetUniqueNo(new int[]{newX,newY}),true);
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
			PosToUnit.remove(GetUniqueNo(pos));
			
			CollisionMap[pos[0]][pos[1]] = 0;
		}else{
			
			System.out.println("ERROR NULL POS IN REMOVE UNIT COLLISION MAP: " + unitNo);
		}
	}
	
	public void moveUnit(int unitNo,int newX,int newY,boolean isWorker){
		
		int[] pos = UnitToPos.get(unitNo);
		
		if(!(pos[0] == newX && pos[1] == newY) && pos != null){
			
			CollisionMap[pos[0]][pos[1]] = 0;
			
			UnitToPos.replace(unitNo, new int[]{newX,newY});
			PosToUnit.remove(GetUniqueNo(pos));
			PosToUnit.put(GetUniqueNo(new int[]{newX,newY}), unitNo);
			
			if(isWorker){
				
				CollisionMap[newX][newY] = 9;
				
			}else{
				
				CollisionMap[newX][newY] = 7;
				
			}
			
			if(fogOfWar){
				
				addVisiblilty(newX, newY);
				
			}
		}
	}
	
	public void addUnit(int x, int y,int unitNo,boolean isGraphics){
	
		UnitToPos.put(unitNo, new int[]{x,y});
		PosToUnit.put(GetUniqueNo(new int[]{x,y}), unitNo);
		
//		if(isGraphics){
//			System.out.println("Start Add CollisionMap " + unitNo);
//			for(int u = 0; u < UnitToPos.size(); u++){
//				
//				System.out.println(UnitToPos.keySet().toArray()[u] + " CollisionMap");
//			}
//			System.out.println("End Add CollisionMap");
//		}

		CollisionMap[x][y] = 7;
		
		if(fogOfWar){
			
			addVisiblilty(x, y);
		}
	}
	
	public void addBuilding(int x, int y,int SizeX, int SizeY,int buildingNo){
		
		BuildingToPos.put(buildingNo, new int[]{x,y});
		
		for(int mx = -SizeX + x; mx < SizeX + x; mx++){
			for(int my = -SizeY + y; my < SizeY + y; my++){
				
				CollisionMap[mx][my] = 8;
			}
		}
		if(fogOfWar){
			//System.out.println("ADD BUILDING VISIBILITY COLLISIONMAP");
			addVisiblilty(x, y);
		}
	}
	
	public void removeBuilding(int buildingNo,int SizeX,int SizeY){
		
		int[] pos = BuildingToPos.get(buildingNo);
		BuildingToPos.remove(buildingNo);
		
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
		if(CollisionMap[pos[0] + direction[0]][pos[1] + direction[1]] == 7){
			
			int[] targetPos = new int[]{pos[0] + direction[0],pos[1] + direction[1]};

			return PosToUnit.get(GetUniqueNo(targetPos));
		}else{
			return -1;
		}
		//return CollisionMap[pos[0] + direction[0]][pos[0] + direction[1]] != 0;
		
	}
	
	private int GetUniqueNo(int[] pos){
		
		return (int)((0.5 * (pos[0] + pos[1]) * (pos[0] + pos[1] + 1)) + pos[1]);
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
				
				if(PosToUnit.containsKey(GetUniqueNo(new int[]{x,y}))){
					
					int found = PosToUnit.get(GetUniqueNo(new int[]{x,y}));
					
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

	public ArrayList<Integer> FindWorkers(int sx, int sy, int sizeX, int sizeY) {
		// TODO Auto-generated method stub
		ArrayList<Integer> workers = new ArrayList<Integer>();
		
		for(int x = sx - sizeX - 25; x < sx + sizeX + 25; x++){
			for(int y = sy - sizeY - 25; y < sy + sizeY + 25; y++){
				
				if(x < 0 || x >= CollisionMap.length || y < 0 || y >= CollisionMap[0].length
						|| (x == sx && y == sy)){
					
					continue;
				}
				
				if(CollisionMap[x][y] == 9){
					
					workers.add(PosToUnit.get(new int[]{x,y}));
				}
			}
		}
		return workers;
	}

	public boolean[][] getVisibleMap() {
		// TODO Auto-generated method stub
		return VisibleMap;
	}
}
