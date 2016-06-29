package Map;

public class GraphicsCollisionMap {

	public static CollisionMap map;
	
	public static void RefreshCollisionMap(int[][] map){
		
		GraphicsCollisionMap.map = new CollisionMap();
		GraphicsCollisionMap.map.RefreshCollisionMap(map);
	}
	
	public static void addUnit(int x, int y,int unitNo){
		
		GraphicsCollisionMap.map.addUnit(x, y,unitNo);
	}
	
	public static void removeUnit(int unitNo){
		
		GraphicsCollisionMap.map.RemoveUnit(unitNo);
	}
	
	public static void addBuilding(int x, int y,int buildingNo){
		
		GraphicsCollisionMap.map.addBuilding(x, y,buildingNo);
	}
	
	public static void removeBuilding(int buildingNo){
		
		GraphicsCollisionMap.map.removeBuilding(buildingNo);
	}
	
	public static int getTile(int x,int y){
		
		return GraphicsCollisionMap.getTile(x, y);
	}
	
	public static boolean InArea(int x, int y, int SizeX, int SizeY){
		
		return GraphicsCollisionMap.InArea(x, y, SizeX, SizeY);
	}
	
	
}
