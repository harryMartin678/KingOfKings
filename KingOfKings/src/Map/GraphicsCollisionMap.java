package Map;

public class GraphicsCollisionMap {

	public static CollisionMap map;
	
	
	public static void Begin(){
		
		GraphicsCollisionMap.map.Begin();
	}
	
	public static void End(){
		
		GraphicsCollisionMap.map.End();
	}
	
	public static void RefreshCollisionMap(int[][] map){
		
		GraphicsCollisionMap.map = new CollisionMap();
		GraphicsCollisionMap.map.RefreshCollisionMap(map);
	}
	
	public static void addUnit(int x, int y,int unitNo){
		
		//System.out.println("Add Unit CollisionMap");
		GraphicsCollisionMap.map.addUnit(x, y,unitNo,true);
	}
	
	public static void removeUnit(int unitNo){
		
		//System.out.println("Remove Unit GraphicCollisionMap");
		GraphicsCollisionMap.map.RemoveUnit(unitNo);
	}
	
	public static void addBuilding(int x, int y,int SizeX,int SizeY,int buildingNo){
		
		GraphicsCollisionMap.map.addBuilding(x, y,SizeX,SizeY,buildingNo);
	}
	
	public static void removeBuilding(int buildingNo){
		
		GraphicsCollisionMap.map.removeBuilding(buildingNo);
	}
	
	public static int getTile(int x,int y){
		
		return GraphicsCollisionMap.map.getTile(x, y);
	}
	
	public static boolean InArea(int x, int y, int SizeX, int SizeY){
		
		return GraphicsCollisionMap.map.InArea(x,y, SizeX, SizeY);
	}

	public static int IsUnitInFront(int unitNo,int[] direction) {
		// TODO Auto-generated method stub
		GraphicsCollisionMap.Begin();
		int ret = GraphicsCollisionMap.map.IsUnitInFront(unitNo,direction);
		GraphicsCollisionMap.End();
		
		return ret;
	}

	public static void printMap(int fromX,int toX,int fromY,int toY) {
		// TODO Auto-generated method stub
		GraphicsCollisionMap.map.PrintCollisionMap(fromX,toX,fromY,toY);
	}
	
	public static void printMap(){
		
		GraphicsCollisionMap.map.PrintCollisionMap();
	}
	
	
}
