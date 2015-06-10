package IntermediateAI;


import java.util.ArrayList;

import Map.Map;
import Map.MapList;

public class MapPathFinder {
	
	MapList maps; 
	ArrayList<PathNode> path;
	ArrayList<PathNode> closedList;
	
	public MapPathFinder(MapList maps){
		
		this.maps = maps;
		path = new ArrayList<PathNode>();
		closedList = new ArrayList<PathNode>();
	}
	
	private ArrayList<int[]> reverseList(ArrayList<int[]> list){
		
		ArrayList<int[]> reverse = new ArrayList<int[]>();
		
		for(int l = list.size()-1; l >= 0; l--){
			
			reverse.add(list.get(l));
		}
		
		return reverse;
	}
	
	public ArrayList<int[]> getPath(int startX, int startY, int startMap, int targetX, int targetY, int targetMap){
		
		MapNode start = new MapNode(maps.getMap(startMap),startX, startY,startMap);
		path.clear();
		closedList.clear();
		
		MapNode node = start;
		
		while(true){
			
			
			path.add(node.getSmallestPath(targetMap,closedList));
			
			ArrayList<int[]> debug = path.get(path.size()-1).getPath();
			
			System.out.println(debug.get(debug.size()-1)[0] + " "+ debug.get(debug.size()-1)[1] 
					+ " " + debug.size() + " debug");
			
			if(path.get(path.size()-1) == null){
				
				System.out.println("ERROR");
				path.remove(path.size()-1);
				int[] transPoint = 
					maps.getMap(path.get(path.size()-2).getTNo()-1).getTransitionPointByIndex(node.getMapNo());
				
				node = new MapNode(maps.getMap(path.get(path.size()-2).getTNo()-1)
						,transPoint[0],transPoint[1],path.get(path.size()-2).getTNo()-1);
				continue;
			}
	
			//System.out.println(path.get(path.size()-1).getTNo()-1 + " map");
			int[] transPoint = 
					maps.getMap(path.get(path.size()-1).getTNo()-1).getTransitionPoint(node.getMapNo());
			
			node = new MapNode(maps.getMap(path.get(path.size()-1).getTNo()-1)
					,transPoint[0],transPoint[1],path.get(path.size()-1).getTNo()-1);
			
			if(node.getMapNo() == targetMap){
				
				path.add(node.getSmallestPath(targetMap,closedList));
				break;
			}
			
			
		}
		
		ArrayList<int[]> pathInt = new ArrayList<int[]>();
		
		for(int p = 0; p < path.size(); p++){
			pathInt.addAll(reverseList(path.get(p).getPath()));
			pathInt.add(new int[]{-1,path.get(p).getTNo()});
		}
		
		int[] transPoint = 
				maps.getMap(targetMap).getTransitionPoint(path.get(path.size()-1).getTNo());
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
		
		ArrayList<int[]> path = new MapPathFinder(maps).getPath(0, 0, 0, 15, 15, 4);
		
		for(int i = 0; i < path.size(); i++){
			
			System.out.println(path.get(i)[0] + " " + path.get(i)[1]);
		}
	}
	
	
	
	
	
}
