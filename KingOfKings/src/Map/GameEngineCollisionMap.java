package Map;

import java.util.ArrayList;

import Units.UnitList;

public class GameEngineCollisionMap {

	public static CollisionMap[] maps;
	
	public static void SetUpCollisionMaps(MapList mapsList){
		
		GameEngineCollisionMap.maps = new CollisionMap[mapsList.getSize()];
		
		for(int m = 0; m < mapsList.getSize(); m++){
			
			GameEngineCollisionMap.maps[m] = new CollisionMap(false,m);
			GameEngineCollisionMap.maps[m].RefreshCollisionMap(mapsList.getMap(m).toArray(),null);
		}
	}
	
	public static void addUnit(int x, int y, int unitNo, int mapNo,boolean isWorker){
		
		GameEngineCollisionMap.maps[mapNo].addUnit(x, y, unitNo,isWorker);
	}
	
	public static void removeUnit(int unitNo, int mapNo){
		
		GameEngineCollisionMap.maps[mapNo].RemoveUnit(unitNo);
	}
	
	public static void addBuilding(int x, int y,int SizeX,int SizeY, int buildingNo, int mapNo,
			boolean isWall, int playerNo){
		
		GameEngineCollisionMap.maps[mapNo].addBuilding(x, y,SizeX,SizeY, buildingNo,isWall,playerNo);
	}
	
	public static void removeBuilding(int buildingNo,int mapNo,int sizeX,int sizeY){
		
		GameEngineCollisionMap.maps[mapNo].removeBuilding(buildingNo,sizeX,sizeY);
	}
	
	public static void moveUnit(int unitNo,int newX,int newY,int mapNo,boolean isWorker){
		
		GameEngineCollisionMap.maps[mapNo].moveUnit(unitNo, newX, newY,isWorker);
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

	public static int IsUnitInFront(int unitNo, int[] direction, int mapNo) {
		// TODO Auto-generated method stub
		return GameEngineCollisionMap.maps[mapNo].IsUnitInFront(unitNo, direction);
	}
	
	public static int FindEnemy(int unitNo, UnitList units){
		
		return GameEngineCollisionMap.maps[units.getUnitMap(unitNo)].FindEnemy(unitNo,units);
	}
	
	
	public static ArrayList<Integer> FindWorkers(int x, int y, int sizeX,int sizeY,int mapNo){
		
		return GameEngineCollisionMap.maps[mapNo].FindWorkers(x,y,sizeX,sizeY);
	}

	public static int getSizeX(int mapNo) {
		// TODO Auto-generated method stub
		return GameEngineCollisionMap.maps[mapNo].getWidth();
	}

	public static int getSizeY(int mapNo) {
		// TODO Auto-generated method stub
		return GameEngineCollisionMap.maps[mapNo].getHeight();
	}

}
