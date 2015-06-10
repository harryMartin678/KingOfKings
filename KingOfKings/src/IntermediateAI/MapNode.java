package IntermediateAI;

import java.util.ArrayList;

import Map.Map;

public class MapNode {
	
	private Map map;
	private ArrayList<PathNode> path;
	private int mapNo;
	
	public MapNode(Map map,int startX, int startY,int mapNo){
		
		this.map = map;
		this.mapNo = mapNo;
		
		path = new ArrayList<PathNode>();
		
		for(int t = 0; t < map.getTransitionSize(); t++){
			
			int[] transPoint = map.getTransitionPointByIndex(t);
			path.add(new PathNode(new Pathfinder(map.toArray()).getPath(
					startX, startY,transPoint[0],transPoint[1])
					,transPoint[2]));
		}
	}
	
	public PathNode getSmallestPath(int targetMap,ArrayList<PathNode> closedList){
		
		int index = -1;
		int size = Integer.MAX_VALUE;
		
		for(int i = 0; i < path.size(); i++){
			
			if(path.get(i).getTNo() == targetMap){
				
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
	
	private boolean onClosedList(ArrayList<PathNode> closedList,PathNode pathNode) {
		// TODO Auto-generated method stub
		
		for(int c = 0; c < closedList.size(); c++){
			
			if(closedList.get(c).getTNo() == pathNode.getTNo() 
					&& closedList.get(c).getPath().equals(pathNode.getPath())){
				
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
