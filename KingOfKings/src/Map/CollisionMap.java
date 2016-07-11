package Map;

import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class CollisionMap {

	public int[][] CollisionMap;
	private HashMap<Integer,int[]> UnitToPos;
	private HashMap<Integer,Integer> PosToUnit;
	private HashMap<Integer,int[]> BuildingToPos;
	private Semaphore lock;
	
	
	public CollisionMap(){
		
		lock = new Semaphore(3);
		UnitToPos = new HashMap<Integer, int[]>();
		PosToUnit = new HashMap<Integer, Integer>();
		BuildingToPos = new HashMap<Integer, int[]>();
	}
	
	public void RefreshCollisionMap(int[][] map){
		
		System.out.println("REFRESH COLLISIONMAP");
		CollisionMap = new int[map.length][map[0].length];
		
		for(int x = 0; x < map.length; x++){
			for(int y = 0; y < map[x].length; y++){
				
				CollisionMap[x][y] = map[x][y];
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
	
	public void RemoveUnit(int unitNo){
		
		int[] pos = UnitToPos.get(unitNo);
//		System.out.println("Start Remove CollisionMap " + unitNo);
//		for(int u = 0; u < PosToUnit.size(); u++){
//			
//			System.out.println(PosToUnit.values().toArray()[u] + " CollisionMap");
//		}
//		System.out.println("End Remove CollisionMap");
		UnitToPos.remove(unitNo);
		PosToUnit.remove(GetUniqueNo(pos));
		
		CollisionMap[pos[0]][pos[1]] = 0;
	}
	
	public void moveUnit(int unitNo,int newX,int newY){
		
		int[] pos = UnitToPos.get(unitNo);
		
		if(!(pos[0] == newX && pos[1] == newY)){
			
			CollisionMap[pos[0]][pos[1]] = 0;
			
			UnitToPos.replace(unitNo, new int[]{newX,newY});
			PosToUnit.remove(GetUniqueNo(pos));
			PosToUnit.put(GetUniqueNo(new int[]{newX,newY}), unitNo);
			
			CollisionMap[newX][newY] = 7;
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
	}
	
	public void addBuilding(int x, int y,int SizeX, int SizeY,int buildingNo){
		
		BuildingToPos.put(buildingNo, new int[]{x,y});
		
		
		for(int mx = -SizeX + x; mx < SizeX + x; mx++){
			for(int my = -SizeY + y; my < SizeY + y; my++){
				
				CollisionMap[mx][my] = 8;
			}
		}
	}
	
	public void removeBuilding(int buildingNo){
		
		int[] pos = BuildingToPos.get(buildingNo);
		BuildingToPos.remove(buildingNo);
		
		CollisionMap[pos[0]][pos[1]] = 0;
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
			
				if(CollisionMap[ax][ay] != 0){
					
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
		
		copy[pos[0]][pos[1]] = 0;
		
		return copy;
	}

	public int IsUnitInFront(int unitNo,int[] direction) {
		// TODO Auto-generated method stub
		int[] pos = UnitToPos.get(unitNo);
		
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
}
