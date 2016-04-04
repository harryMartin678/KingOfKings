package IntermediateAI;

import java.util.ArrayList;

import Buildings.BuildingList;
import Buildings.BuildingProgress;
import Buildings.BuildingSite;
import Map.CollisionMap;
import Map.MapList;
import Units.UnitList;

public class MapRouteFinder {
	
	private UnitList units;
	private BuildingList buildings;
	private MapList maps;
	private BuildingProgress sites;
	private ArrayList<int[]> minPath;
	private int ignoreUnit;
	
	/*
	 * use units, buildings and maps to correctly navigate the map 
	 */
	public MapRouteFinder(UnitList units, BuildingList buildings, MapList maps,
			BuildingProgress sites){
		
		this.units = units;
		this.buildings = buildings;
		this.maps = maps;
		this.sites = sites;
		minPath = new ArrayList<int[]>();
		ignoreUnit = -1;
	}
	
	public MapRouteFinder(UnitList units, BuildingList buildings, MapList maps,int ignoreUnit){
		
		this.units = units;
		this.buildings = buildings;
		this.maps = maps;
		minPath = new ArrayList<int[]>();
		this.ignoreUnit = ignoreUnit;
	}
	
	/*
	 * finds the the smallest path and adds it to min path 
	 */
	public void getRoute(int startX, int startY,int targetX, int targetY, 
			int currentMap, int targetMap,ArrayList<Integer> closedList
			,ArrayList<int[]> pathsf){
		
		//base case - if this map is the target map
		if(currentMap == targetMap){
			
			
			ArrayList<int[]> path = new ArrayList<int[]>();
			
			if(ignoreUnit == -1){

				//System.out.println(startX + " " + startY + " " + targetX + " " + targetY + " MapRouteFinder");
				//System.out.println("Pre PathFinder MapRouteFinder");
				//find a path to the target node from the transition point 
				//if(sites == null){
					path.addAll(reverseList(new Pathfinder(new CollisionMap(buildings,units
							,maps.getMap(currentMap),currentMap).getCollisionMap()
							).getPath(startX, startY, targetX, targetY)));
//				}else{
//					
//					path.addAll(reverseList(new Pathfinder(new CollisionMap(buildings,units
//							,maps.getMap(currentMap),sites).getCollisionMap()
//							).getPath(startX, startY, targetX, targetY)));
//				}
				//System.out.println("Post PathFinder MapRouteFinder");
			}else{
				//find a path to the target node from the transition point 
				path.addAll(reverseList(new Pathfinder(new CollisionMap(buildings,units
						,maps.getMap(currentMap),ignoreUnit,currentMap).getCollisionMap()
						).getPath(startX, startY, targetX, targetY)));
				
			}
			
			//end of path
			path.add(new int[]{-2,-2});
			
			pathsf.addAll((ArrayList<int[]>) path.clone());
			
			//if the total path is the first or the smallest then make it min path
			if(minPath.size() == 0 || pathsf.size() < minPath.size()){
				
				minPath.clear();
				minPath = (ArrayList<int[]>) pathsf.clone();
			}
			
			
		}else{
		
			//add the current map to the closed list to stop it from going in circles 
			closedList.add(currentMap);
		
			ArrayList<int[]> path = new ArrayList<int[]>();
			
			//-no where else to go
			//recurse into every possible map
			for(int m = 0; m < maps.getMap(currentMap).getTransitionSize(); m++){
				
				//finds the next transition point 
				int mapTo = maps.getMap(currentMap).getTransitionPointByIndex(m)[2]-1;
		
				//-not into a map on the closedlist 
				if(!onClosedList(closedList,mapTo)){
	
						//find a path from the start transition point to the transition point 
						//of the next map
						path.addAll(reverseList(new Pathfinder(new CollisionMap(buildings,units
						,maps.getMap(currentMap),currentMap).getCollisionMap()
						).getPath(startX, startY, 
								maps.getMap(currentMap).getTransitionPointByIndex(m)[0],
								maps.getMap(currentMap).getTransitionPointByIndex(m)[1])));
						
						//we have moved maps 
						path.add(new int[]{-1,mapTo});
	
						//record path so far
						ArrayList<int[]> next = new ArrayList<int[]>();
						next.addAll((ArrayList<int[]>) pathsf.clone());
						next.addAll((ArrayList<int[]>) path.clone());
					
						//look for next map to move to
						getRoute(maps.getMap(mapTo
										).getTransitionPoint(currentMap+1)[0],
								maps.getMap(mapTo).getTransitionPoint(currentMap+1)[1],
										targetX,targetY,mapTo,targetMap
										,(ArrayList<Integer>) closedList.clone()
										,(ArrayList<int[]>)next.clone());
						
						//clear the path for the next iteration
						path.clear();
				}
			}
		}
		

	}
	
	/*
	 * checks if an map number is on the closed list
	 */
	public boolean onClosedList(ArrayList<Integer> closedList, int value){
		
		for(int i = 0; i < closedList.size(); i++){
			
			if(closedList.get(i).intValue() == value){
				
				return true;
			}
		}
		
		return false;
	}
	
	/*
	 * reverses a list 
	 */
	private ArrayList<int[]> reverseList(ArrayList<int[]> list){
		
		ArrayList<int[]> reverse = new ArrayList<int[]>();
		
		for(int l = list.size()-1; l >= 0; l--){
			
			reverse.add(list.get(l));
		}
		
		return reverse;
	}
	
	
	/*
	 * gets the minimum path
	 */
	public ArrayList<int[]> getPath(int startX, int startY, int targetX, int targetY,
			int startMap, int targetMap){
		
		getRoute(startX, startY,targetX, targetY, startMap
				,targetMap,new ArrayList<Integer>(), new ArrayList<int[]>());
		if(minPath.size() > 0){
			minPath.remove(minPath.size()-1);
		}
		return minPath;
	}
	
	/*public static void main(String[] args) {
		
		MapRouteFinder mrf = new MapRouteFinder(new UnitList(),
		new BuildingList(), new MapList("game1"));
		
		ArrayList<int[]> path = mrf.getPath(17,17,17,24,1,1);

		for(int i = 0; i < path.size(); i++){
			
			System.out.println(path.get(i)[0] + " " + path.get(i)[1]);
		}
	
 	}*/

}
