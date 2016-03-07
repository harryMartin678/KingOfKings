package Map;

import Units.UnitList;
import Buildings.BuildingList;
import Buildings.BuildingProgress;
import GameGraphics.IBuildingList;
import GameGraphics.IUnitList;

public class CollisionMap {
	
	private IBuildingList buildings;
	private IUnitList units;
	private BuildingProgress sites;
	private int[][] collisionMap;
	private int ignoreUnit;
	private int viewedMap;
	
	
	public CollisionMap(IBuildingList buildings, IUnitList units, Map map,int mapNo){
		
		this.buildings = buildings;
		this.units = units;
		ignoreUnit = -1;
		this.viewedMap = mapNo;
		SetCollisionMapToArray(map.toArray());
		createCollisionMap();
		
	}
	
//	public CollisionMap(IBuildingList buildings, IUnitList units, Map map,BuildingProgress sites,int mapNo){
//		
//		this.buildings = buildings;
//		this.units = units;
//		this.sites = sites;
//		this.viewedMap = mapNo;
//		SetCollisionMapToArray(map.toArray());
//		createCollisionMap();
//	}
	
	
	public CollisionMap(IBuildingList buildings, IUnitList units, Map map, int ignoreUnit,int mapNo){
		
		this.buildings = buildings;
		this.units = units;
		this.ignoreUnit = ignoreUnit;
		this.viewedMap = mapNo;
		
		//System.out.println("ignore Unit " + ignoreUnit);
		
		SetCollisionMapToArray(map.toArray());
		createCollisionMap();
		
	}
	
	private void SetCollisionMapToArray(int[][] array){
		
		collisionMap = new int[array.length][array[0].length];
		
		for(int y = 0; y < collisionMap.length; y++){
			for(int x = 0; x < collisionMap[0].length; x++){
				
				collisionMap[x][y] = array[x][y];
			}
		}
	}
	
	public void printCollisionMap(int mx, int my){
		
		for(int y = 0; y < collisionMap.length; y++){
			for(int x = 0; x < collisionMap[y].length; x++){
				
				if(y == my && x == mx){
					
					System.out.print(3 + " ");
				
				}else{
					
					System.out.print(collisionMap[y][x] + " ");
				}
				
				
			}
			
			System.out.println();
		}
		
	}
	
	public void printCollisionMap(){
		
		for(int y = 0; y < collisionMap.length; y++){
			for(int x = 0; x < collisionMap[y].length; x++){
				
				System.out.print(collisionMap[y][x] + " ");
			}
			
			System.out.println();
		}
	}
	
	public int[][] getCollisionMap(){
		
		return collisionMap;
	}
	
	private void createCollisionMap(){
		
		units.begin();
		for(int u = 0; u < units.getUnitListSize(); u++){
			
			if(units.getUnitMap(u) == -1 || units.getUnitMap(u) == this.viewedMap){
				
				collisionMap[(int)units.getUnitY(u)][(int)units.getUnitX(u)] = 8;
			}
		}
		units.end();
		
		for(int b = 0; b < buildings.getBuildingsSize(); b++){
			
			if(buildings.getBuildingMap(b) == -1 || buildings.getBuildingMap(b) == this.viewedMap){
				
				collisionMap[(int)buildings.getBuildingX(b)][(int)buildings.getBuildingY(b)] = 9;
			}
		}
		
//		for(int s = 0; s < sites.getSiteSize();s++){
//			
//			if(sites.getSiteMap(s) == -1 || sites.getSiteMap(s) == this.viewedMap){
//				
//				collisionMap[(int)sites.getSiteX(s)][(int)sites.getSiteY(s)] = 10;
//			}
//		}
	}
	
//	private void createCollisionMap(){
//		
//		//collisionMap[buildings.getBuildingY(0)][buildings.getBuildingX(0)] = 2;
//		//System.out.println(buildings.getBuildingsSize());
//		for(int y = 0; y < collisionMap.length; y++){
//			for(int x = 0; x < collisionMap[y].length; x++){
//				
//				if(collisionMap[y][x] != 0){
//					
//					continue;
//				}
//				
//				
//				units.begin();
//				
//				for(int u = 0; u < units.getUnitListSize(); u++){
//				
//					
//					if(u == ignoreUnit){
//						
//						break;
//					}
//					
//					if(units.checkInUnit(x, y, u)){
//						
//						collisionMap[y][x] = 8;
//						break;
//					}
//					
//				}
//				
//				units.end();
//				
//				if(collisionMap[y][x] == 8){
//					
//					continue;
//				}
//				
//				
//				for(int b = 0; b < buildings.getBuildingsSize(); b++){
//					
//					if(buildings.inBuilding(x, y, b)){
//						
//						collisionMap[y][x] = 9;
//						break;
//					}
//				}
//				
//				if(collisionMap[y][x] == 9 || sites == null){
//		
//					continue;
//				}
//				
//				if(sites.inSite(x,y)){
//					
//					collisionMap[y][x] = 10;
//				}
//				
//			}
//		}
//	}
	
	public boolean inArea(int x, int y, int sizeX, int sizeY){
		
		if(x - sizeX < 0 || x + sizeX > collisionMap[0].length
				|| y - sizeY < 0 || y + sizeY > collisionMap.length){
			
			return false;
		}
		
		for(int cy = y - sizeY; cy < y + sizeY; cy++){
			for(int cx = x - sizeX; cx < x + sizeX; cx++){
				
				if(collisionMap[cy][cx] != 0){
					
					return false;
				}
			}
		}
		
		
		return true;
	}
	
	
//	public static void main(String[] args) {
//		
//		BuildingList buildings = new BuildingList();
//		buildings.addBuilding(1, 1,5, 5, 0);
//		
//		CollisionMap map = new CollisionMap(buildings,new UnitList()
//				,new MapList("game1").getMap(1));
//		
//		boolean canBuild = map.inArea(4,4, 2, 2);
//		
//		System.out.println(canBuild);
//		
////		for(int x = 0; x < map.length; x++){
////			for(int y = 0; y < map[x].length; y++){
////				
////				System.out.print(map[x][y]);
////			}
////			
////			System.out.println();
////		}
//	}

}
