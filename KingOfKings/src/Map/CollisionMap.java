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

}
