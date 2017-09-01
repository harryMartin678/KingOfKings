package Map;

public class GraphicsCollisionMap {

	public static CollisionMap map;
	private static FogMapCache fogMaps = new FogMapCache();
	public static boolean fogOfWar;
	
	
	public static void Begin(){
		
		GraphicsCollisionMap.map.Begin();
	}
	
	public static void End(){
		
		GraphicsCollisionMap.map.End();
	}
	
	public static void addVisiblilty(int x, int y){
		
		GraphicsCollisionMap.map.addVisiblilty(x, y);
	}
	
	public static void RefreshCollisionMap(int[][] map,int mapNo){
		
		if(GraphicsCollisionMap.map != null){
			fogMaps.setVisibleMap(GraphicsCollisionMap.map.getMapNo(),
					GraphicsCollisionMap.map.getVisibleMap());
		}
		GraphicsCollisionMap.map = new CollisionMap(fogOfWar,mapNo);
		GraphicsCollisionMap.map.RefreshCollisionMap(map,fogMaps.getMap(mapNo));
	}
	
	public static void addUnit(int x, int y,int unitNo,boolean isWorker){
		
		//System.out.println("Add Unit CollisionMap");
		GraphicsCollisionMap.map.addUnit(x, y,unitNo, isWorker);
	}
	
	public static boolean isOnMine(int x, int y){
		
		return GraphicsCollisionMap.map.isOnMine(x, y);
	}
	
	public static void removeUnit(int unitNo){
		
		//System.out.println("Remove Unit GraphicCollisionMap");
		GraphicsCollisionMap.map.RemoveUnit(unitNo);
	}
	
	public static void addBuilding(int x, int y,int SizeX,int SizeY,int buildingNo){
		
		//System.out.println("ADD BUILDING " + fogOfWar + " GraphicsCollisionMap");
		GraphicsCollisionMap.map.addBuilding(x, y,SizeX,SizeY,buildingNo,false,0,true);
	}
	
	public static void removeBuilding(int buildingNo,int sizeX,int sizeY){
		
		GraphicsCollisionMap.map.removeBuilding(buildingNo,sizeX,sizeY);
	}
	
	public static int getTile(int x,int y){
		
		return GraphicsCollisionMap.map.getTile(x, y);
	}
	
	public static boolean InArea(int x, int y, int SizeX, int SizeY){
		
		return GraphicsCollisionMap.map.InArea(x,y, SizeX, SizeY);
	}
	
	public static boolean outOfFog(int x, int y){
		
		return GraphicsCollisionMap.map.outOfFog(x, y);
	}

	public static int IsUnitInFront(int unitNo,int[] direction) {
		// TODO Auto-generated method stub
		GraphicsCollisionMap.Begin();
		int ret = GraphicsCollisionMap.map.IsUnitInFront(unitNo,direction);
		GraphicsCollisionMap.End();
		
		return ret;
	}
	
	public static int[][] toArray(int mapNo){
		
		return GameEngineCollisionMap.maps[mapNo].ToArray();
	}

	public static void printMap(int fromX,int toX,int fromY,int toY) {
		// TODO Auto-generated method stub
		GraphicsCollisionMap.map.PrintCollisionMap(fromX,toX,fromY,toY);
	}
	
	public static void printMap(){
		
		GraphicsCollisionMap.map.PrintCollisionMap();
	}
	
	
}
