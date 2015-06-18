package IntermediateAI;

import java.util.ArrayList;

import Buildings.BuildingList;
import Map.CollisionMap;
import Map.Map;
import Map.MapList;
import Units.UnitList;

public class MapNode {
	
	private ArrayList<PathNode> path;
	private int mapNo;
	
	public MapNode(MapList maps,int startX, int startY,int mapNo, 
			UnitList units, BuildingList buildings, int posX){
		
		this.mapNo = mapNo;
		
		path = new ArrayList<PathNode>();
		
		//get all the transition points
		for(int t = 0; t < maps.getMap(mapNo).getTransitionSize(); t++){
			
			int[] transPoint = maps.getMap(mapNo).getTransitionPointByIndex(t);
			//get the path to that transition point
			//using a collision map to take into account the units and the buildings
			/*path.add(new PathNode(
					new FormationMovement(new CollisionMap(buildings,units,
									maps.getMap(transPoint[2]-1)).getCollisionMap()).getPath(
					startX, startY,transPoint[0],transPoint[1],posX)
					,transPoint[2]));*/
			if(mapNo == 4){
				System.out.println(startX + " " + startY + " " + transPoint[0] + " " + transPoint[1]
					+ " " + transPoint[2]);
			}
			path.add(new PathNode(
					new Pathfinder(new CollisionMap(buildings,units,
									maps.getMap(transPoint[2]-1)).getCollisionMap()).getPath(
					startX, startY,transPoint[0],transPoint[1])
					,transPoint[2]));

		}
	}
	
	public PathNode getSmallestPath(int targetMap,ArrayList<Integer> closedList){
		
		int index = -1;
		int size = Integer.MAX_VALUE;
		
		//get the path node with the smallest path unless it's on the closed list
		//if the path goes to the target map then return that path node 
		for(int i = 0; i < path.size(); i++){
			
			if(path.get(i).getTNo() == targetMap+1){
				
				return path.get(i);
				
			}else if(path.get(i).getPath().size() < size && !onClosedList(closedList,path.get(i))){
				
				size = path.get(i).getPath().size();
				index = i;
			}
		}
		
		if(index == -1){
			
			return null;
		}
		
		return path.get(index);
	}
	
	private boolean onClosedList(ArrayList<Integer> closedList,PathNode pathNode) {
		// TODO Auto-generated method stub
		
		//is the map on the closed list 
		for(int c = 0; c < closedList.size(); c++){
			

			if(closedList.get(c) == pathNode.getTNo()){
				
				return true;
			}
		}
		return false;
	}

	public int getNoOfPaths(){
		
		return path.size();
	}
	
	public int getPathSize(){
		
		return path.size();
	}
	
	public int getMapNo(){
		
		return mapNo;
	}
	
}
