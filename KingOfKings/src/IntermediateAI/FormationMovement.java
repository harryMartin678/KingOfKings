package IntermediateAI;

import java.util.ArrayList;

import Buildings.BuildingList;
import Map.CollisionMap;
import Map.GameEngineCollisionMap;
import Map.MapList;
import Units.UnitList;

public class FormationMovement {

	private MapList maps;
	private UnitList units;
	private BuildingList buildings;
	
	/*
	 * use units, buildings and the maps for changing the paths 
	 */
	public FormationMovement(MapList maps,UnitList units, BuildingList buildings){

		this.maps = maps;
		this.units = units;
		this.buildings = buildings;
	}
	
	/*
	 * gets the path of a unit in formation given the original path  
	 */
	public ArrayList<int[]> getPath(ArrayList<int[]> orgPath, int posX){
		
		int map = 0;
		
		for(int i = 0; i < orgPath.size(); i++){
			
			//if we change map
			if(orgPath.get(i)[0] == -1){
				
				map = orgPath.get(i)[1];
			}
			
			int newPos = orgPath.get(i)[0] + posX;
			
//			new CollisionMap(buildings, units,
//					maps.getMap(map),map).getCollisionMap()[orgPath.get(i)[1]]
//							[newPos] != 0
			//move the path point over up to the posX value 
			for(int j = posX; j >= 0; j--){
				if(newPos >= maps.getMapWidth(map) 
						|| GameEngineCollisionMap.getTile(
								orgPath.get(i)[1], newPos, map) != 0){
					
					newPos --;
				
				}else{
					
					break;
				}
				
			}
			
			orgPath.get(i)[0] = newPos;
		}
		
		int i = orgPath.size()-1;
		
		//fill gaps left by shifting the path by differing amounts 
		while(true){
			
			//if we change map 
			if(orgPath.get(i)[0] == -1){
				
				map = orgPath.get(i)[1];
			}
			
			
			if(isGap(orgPath.get(i),orgPath.get(i-1))){
				
				//new CollisionMap(buildings, units,
				//maps.getMap(map),map).getCollisionMap()
						ArrayList<int[]> newSection = 
								new Pathfinder(GameEngineCollisionMap.toArray(map)
										).getPath(orgPath.get(i)[0]
											, orgPath.get(i)[1],orgPath.get(i-1)[0]
												, orgPath.get(i-1)[1]);
						
						//add the new path section to connect the two nodes
						orgPath.addAll(i,newSection);
						i += newSection.size()-1;		
			}
			
			i --;
			
			if(i <= 0){
				
				break;
			}
		}
		
		
		return orgPath;
	}
	
	/*
	 * is there a gap between two nodes 
	 */
	public boolean isGap(int[] first, int[] second){
		
		return (Math.abs(first[0] - second[0]) > 1 || Math.abs(first[1] - second[1]) > 1);
		
	}
	
	
	/*
	 * is the node on the list
	 */
	private boolean onList(int[] pos,ArrayList<int[]> list){
		
		for(int i = 0; i < list.size(); i++){
			
			if(list.get(i)[0]== pos[0] 
					&& list.get(i)[1] == pos[1]){
				
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * for debugging 
	 
	public void printList(ArrayList<int[]> list){
		

		for(int j = 0; j < map.length; j++){
			for(int i = 0; i < map[j].length; i++){
				
				if(onList(new int[]{i,j},list)){
					
					System.out.print('*');
					
				}else if(map[j][i] != '.'){
					
					System.out.print(map[j][i]);
					
				}else{
					
					System.out.print('.');
				}
			}
			System.out.println();
		}
		
		
	}*/
	
	/*public static void main(String[] args) {
		
		int[][] map = new int[][]{{0,0,0,0,0,0},
									{0,0,0,0,0,0},
									{0,0,0,1,0,0},
									{0,0,0,1,0,0},
									{0,0,0,0,0,0},
									{0,0,0,0,0,0}};
		
		FormationMovement fm = new FormationMovement(map);
		
		fm.printList(fm.getPath(0,0,0,5,3));
		System.out.println("\\\\\\\\\\\\");
		fm.printList(fm.getOrgPath(0, 0,0,5));
		
		
	}*/

}
