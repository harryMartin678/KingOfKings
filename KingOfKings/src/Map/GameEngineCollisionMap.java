package Map;

public class GameEngineCollisionMap {

	public static CollisionMap[] maps;
	
	public static void SetUpCollisionMaps(MapList mapsList){
		
		GameEngineCollisionMap.maps = new CollisionMap[mapsList.getSize()];
		
		for(int m = 0; m < mapsList.getSize(); m++){
			
			GameEngineCollisionMap.maps[m] = new CollisionMap();
			GameEngineCollisionMap.maps[m].RefreshCollisionMap(mapsList.getMap(m).toArray());
		}
	}
	
	public static void addUnit(int x, int y, int unitNo, int mapNo){
		
		GameEngineCollisionMap.maps[mapNo].addUnit(x, y, unitNo);
	}
	
	public static void removeUnit(int unitNo, int mapNo){
		
		GameEngineCollisionMap.maps[mapNo].RemoveUnit(unitNo);
	}
	
	public static void addBuilding(int x, int y, int buildingNo, int mapNo){
		
		GameEngineCollisionMap.maps[mapNo].addBuilding(x, y, buildingNo);
	}
	
	public static void removeBuilding(int buildingNo,int mapNo){
		
		GameEngineCollisionMap.maps[mapNo].removeBuilding(buildingNo);
	}
	
	public static boolean InArea(int x, int y, int SizeX,int SizeY, int mapNo){
		
		return GameEngineCollisionMap.maps[mapNo].InArea(x, y, SizeX, SizeY);
	}
	
	public static int getTile(int x, int y,int mapNo){
		
		return GameEngineCollisionMap.maps[mapNo].getTile(x, y);
	}
	
	public static int[][] toArray(int mapNo){
		
		return GameEngineCollisionMap.maps[mapNo].ToArray();
	}
	
	public static int[][] toArray(int mapNo,int ignoreUnit){
		
		return GameEngineCollisionMap.maps[mapNo].ToArray(ignoreUnit);
	}
}
