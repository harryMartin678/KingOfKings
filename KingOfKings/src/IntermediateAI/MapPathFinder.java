package IntermediateAI;


import java.util.ArrayList;

import Buildings.BuildingList;
import Map.CollisionMap;
import Map.Map;
import Map.MapList;
import Units.UnitList;

public class MapPathFinder {
	
	MapList maps; 
	ArrayList<PathNode> path;
	ArrayList<Integer> closedList;
	UnitList units;
	BuildingList buildings;
	
	public MapPathFinder(MapList maps,UnitList units, BuildingList buildings){
		
		this.units = units;
		this.buildings = buildings;
		this.maps = maps;
		path = new ArrayList<PathNode>();
		closedList = new ArrayList<Integer>();
	}
	
	private ArrayList<int[]> reverseList(ArrayList<int[]> list){
		
		ArrayList<int[]> reverse = new ArrayList<int[]>();
		
		for(int l = list.size()-1; l >= 0; l--){
			
			reverse.add(list.get(l));
		}
		
		return reverse;
	}
	
	public ArrayList<int[]> getPath(int startX, int startY, int startMap, int targetX,
			int targetY, int targetMap, int posX){
		
		if(targetMap == startMap){
			
			return reverseList(new Pathfinder(new CollisionMap(buildings,units,maps.getMap(startMap)).getCollisionMap()
			).getPath(startX, startY, targetX, targetY));
		}
		
		path.clear();
		closedList.clear();

		//start map node, clear path and closedlist
		MapNode start = new MapNode(maps,startX, startY,startMap, units, buildings, posX);	
		MapNode node = start;
		MapNode lastNode = null;
		
		//add the first map to the closed list 
		closedList.add(new Integer(startMap+1));
		
		while(true){
		
			System.out.println((node.getMapNo()+1) + " mapNo");
			
			//get the next map with the smallest path 
			path.add(node.getSmallestPath(targetMap,closedList));
			
			//if the path is in a corner 
			if(path.get(path.size()-1) == null){
				
				for(int i = 0; i < closedList.size(); i++){
					
					System.out.println(closedList.get(i).intValue() + " closed");
				}
				
				System.out.println(path.get(path.size()-2).getTNo());
				
				//remove the last two maps
				path.remove(path.size()-1);
				path.remove(path.size()-1);
				//make the node equal to the last node to back track
				node = lastNode;
				
				continue;
			}
			
			//add the last map to the closed list 
			closedList.add(new Integer(path.get(path.size()-1).getTNo()));

			//[0] - x position of transition point [1] - y position of transition point 
			//[2] - the map that is transitions to
			int[] transPoint = 
					maps.getMap(path.get(path.size()-1).getTNo()-1).getTransitionPoint(node.getMapNo()+1);
			
			
			//keep track of the last node for back tracking
			lastNode = node;
			//set the node to the next map where the unit starts in the corresponding transition point
			//in the next map
			node = new MapNode(maps
					,transPoint[0],transPoint[1],path.get(path.size()-1).getTNo()-1
					,units, buildings, posX);
			
			//if the path is in the target map 
			if(node.getMapNo() == targetMap){
				
				//path.add(node.getSmallestPath(targetMap,closedList));
				break;
			}
				
		}
		
		ArrayList<int[]> pathInt = new ArrayList<int[]>();

		//create path and add {-1, map number} when a map is transitioned to 
		for(int p = 0; p < path.size(); p++){

			pathInt.addAll(reverseList(path.get(p).getPath()));
			pathInt.add(new int[]{-1,path.get(p).getTNo()});
		}
		
		//add the path in the final map to the target node
		int[] transPoint = 
				maps.getMap(targetMap).getTransitionPoint(path.get(path.size()-1).getTNo()-1);
		pathInt.addAll(reverseList(
				new Pathfinder(maps.getMap(targetMap).toArray()).getPath(transPoint[0], transPoint[1]
						, targetX, targetY)));
		
		return pathInt;
		
	}
	
	public static void main(String[] args) {
		
		MapList maps = new MapList("game1");
		
		/*ArrayList<PathNode> path = new MapPathFinder(maps).getPath(0, 0, 0, 15, 15, 2);
		
		for(int i = 0; i < path.size(); i++){
			
			System.out.println(path.get(i).getTNo());
			
			for(int x = 0; x < path.get(i).getPath().size(); x++){
				
				System.out.println(path.get(i).getPath().get(x)[0] + " " +
						path.get(i).getPath().get(x)[1]);
			}
		}*/
		
		ArrayList<int[]> path = new MapPathFinder(maps,new UnitList(), 
				new BuildingList()).getPath(0, 0, 2, 15, 15, 3,0);
		
		for(int i = 0; i < path.size(); i++){
			
			System.out.println(path.get(i)[0] + " " + path.get(i)[1]);
		}
	}
	
	
	
	
	
}
