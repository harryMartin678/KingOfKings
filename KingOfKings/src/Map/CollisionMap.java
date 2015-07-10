package Map;

import Units.UnitList;
import Buildings.BuildingList;

public class CollisionMap {
	
	private BuildingList buildings;
	private UnitList units;
	private int[][] collisionMap;
	
	
	public CollisionMap(BuildingList buildings, UnitList units, Map map){
		
		this.buildings = buildings;
		this.units = units;
		
		collisionMap = map.toArray();
		createCollisionMap();
		
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
		
		//collisionMap[buildings.getBuildingY(0)][buildings.getBuildingX(0)] = 2;
		System.out.println(buildings.getBuildingsSize());
		
		for(int y = 0; y < collisionMap.length; y++){
			for(int x = 0; x < collisionMap[y].length; x++){
				
				if(collisionMap[y][x] != 0){
					
					continue;
				}
				for(int u = 0; u < units.getUnitListSize(); u++){
					
					if(units.checkInUnit(x, y, u)){
						
						collisionMap[y][x] = 8;
						break;
					}
				}
				
				if(collisionMap[y][x] == 8){
					
					continue;
				}
				
				for(int b = 0; b < buildings.getBuildingsSize(); b++){
					
					if(buildings.inBuilding(x, y, b)){
						
						collisionMap[y][x] = 9;
						break;
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		
		BuildingList buildings = new BuildingList();
		buildings.addBuilding(1, 1,5, 5, 0);
		
		int[][] map = new CollisionMap(buildings,new UnitList()
				,new MapList("game1").getMap(1)).getCollisionMap();
		
		for(int x = 0; x < map.length; x++){
			for(int y = 0; y < map[x].length; y++){
				
				System.out.print(map[x][y]);
			}
			
			System.out.println();
		}
	}

}
