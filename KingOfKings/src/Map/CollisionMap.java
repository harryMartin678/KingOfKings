package Map;

import java.util.HashMap;

public class CollisionMap {

	public int[][] CollisionMap;
	protected HashMap<Integer,int[]> UnitToPos = new HashMap<Integer, int[]>();
	protected HashMap<Integer,int[]> BuildingToPos = new HashMap<Integer,int[]>();
	
	
	public CollisionMap(){
		
		
	}
	
	public void RefreshCollisionMap(int[][] map){
		
		CollisionMap = new int[map.length][map[0].length];
		
		for(int x = 0; x < map.length; x++){
			for(int y = 0; y < map[x].length; y++){
				
				CollisionMap[x][y] = map[x][y];
			}
		}
	}
	
	public void RemoveUnit(int unitNo){
		
		int[] pos = UnitToPos.get(unitNo);
		
		CollisionMap[pos[0]][pos[1]] = 0;
	}
	
	public void addUnit(int x, int y,int unitNo){
		
		UnitToPos.put(unitNo, new int[]{x,y});
		CollisionMap[x][y] = 7;
	}
	
	public void addBuilding(int x, int y,int buildingNo){
		
		BuildingToPos.put(buildingNo, new int[]{x,y});
		CollisionMap[x][y] = 8;
	}
	
	public void removeBuilding(int buildingNo){
		
		int[] pos = BuildingToPos.get(buildingNo);
		
		CollisionMap[pos[0]][pos[1]] = 0;
	}
	
	public int getTile(int x,int y){
		
		return CollisionMap[x][y];
	}
	
	public boolean InArea(int x, int y, int SizeX, int SizeY){
		
		for(int ax = -SizeX + x; ax <= SizeX + x; ax++){
			for(int ay = -SizeY + y; ay <= SizeY + y; ay++){
				
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
}
