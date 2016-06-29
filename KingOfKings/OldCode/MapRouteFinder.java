package IntermediateAI;

import java.util.ArrayList;

import Buildings.BuildingList;
import Map.CollisionMap;
import Map.MapList;
import Units.UnitList;

public class MapRouteFinder {
	
	private UnitList units;
	private BuildingList buildings;
	private MapList maps;
	private ArrayList<ArrayList<int[]>> paths;
	
	public MapRouteFinder(UnitList units, BuildingList buildings, MapList maps){
		
		this.units = units;
		this.buildings = buildings;
		this.maps = maps;
		paths = new ArrayList<ArrayList<int[]>>();
	}
	
	
	public ArrayList<ArrayList<int[]>> getRoute(int startX, int startY,int targetX, int targetY, 
			int currentMap, int targetMap,ArrayList<Integer> closedList){

		System.out.println(currentMap);
		//two base cases 
		if(currentMap == targetMap){
			//-at target map 
			
			ArrayList<int[]> path = new ArrayList<int[]>();

			path.add(new int[]{-1,currentMap});
			path.addAll(reverseList(new Pathfinder(new CollisionMap(buildings,units
					,maps.getMap(currentMap)).getCollisionMap()
					).getPath(startX, startY, targetX, targetY)));
			
			path.add(new int[]{-2,-2});
			
			ArrayList<ArrayList<int[]>> ret = new ArrayList<ArrayList<int[]>>();
			
			ret.add(path);

			return ret;
		}
		
		closedList.add(currentMap);
		
		ArrayList<ArrayList<int[]>> ret = new ArrayList<ArrayList<int[]>>();
		
		ArrayList<int[]> path = new ArrayList<int[]>();
		
		
		//-no where else to go
		//recurse into every possible map
		for(int m = 0; m < maps.getMap(currentMap).getTransitionSize(); m++){
			
			
			int mapTo = maps.getMap(currentMap).getTransitionPointByIndex(m)[2]-1;
			
			if(currentMap == 0){
				
				System.out.println(currentMap + " " + mapTo + " check");
			}
			
			//-not into a map on the closedlist 
			if(!onClosedList(closedList,mapTo)){

					path.add(new int[]{-1,currentMap});
					path.addAll(reverseList(new Pathfinder(new CollisionMap(buildings,units
					,maps.getMap(currentMap)).getCollisionMap()
					).getPath(startX, startY, 
							maps.getMap(currentMap).getTransitionPointByIndex(m)[0],
							maps.getMap(currentMap).getTransitionPointByIndex(m)[1])));
					
					path.add(new int[]{-1,mapTo});
					
					ret.add(path);
					
					path = new ArrayList<int[]>();
				
					ret.addAll(getRoute
							(maps.getMap(mapTo
									).getTransitionPoint(currentMap+1)[0],
							maps.getMap(mapTo).getTransitionPoint(currentMap+1)[1],
									targetX,targetY,mapTo,targetMap,closedList));
			}
		}
		
		if(ret.size() == 0){
			
			ArrayList<int[]> end = new ArrayList<int[]>();
			
			end.add(new int[]{-3,-3});
			
			ret.add(end);
		}
			
		
		return ret;
		
	}
	
	public boolean onClosedList(ArrayList<Integer> closedList, int value){
		
		for(int i = 0; i < closedList.size(); i++){
			
			if(closedList.get(i).intValue() == value){
				
				return true;
			}
		}
		
		return false;
	}
	
	private ArrayList<int[]> reverseList(ArrayList<int[]> list){
		
		ArrayList<int[]> reverse = new ArrayList<int[]>();
		
		for(int l = list.size()-1; l >= 0; l--){
			
			reverse.add(list.get(l));
		}
		
		return reverse;
	}
	
	private ArrayList<int[]> backtrack(int index,int startMap,ArrayList<ArrayList<int[]>> route){
		
		ArrayList<int[]> next = route.get(index);
		
		int map = route.get(index).get(0)[1];

		for(int b = index-1; b >=0; b--){
			
			if(map != route.get(b).get(route.get(b).size()-1)[1]){
				
				continue;
			}
			
			map = route.get(b).get(0)[1];
			
			next.addAll(route.get(b));
			
			if(map == startMap){
				
				break;
			}
		}
		
		return next;
	}
	
	public ArrayList<int[]> getBestRoute(int startX, int startY, int targetX, int targetY
			,int startMap, int targetMap){
		
		ArrayList<ArrayList<int[]>> routes = getRoute(startX, startY,targetX, targetY, startMap,
				targetMap,new ArrayList<Integer>());
		
		ArrayList<ArrayList<int[]>> formatRoutes = new ArrayList<ArrayList<int[]>>();
		ArrayList<int[]> next = new ArrayList<int[]>();
		
		for(int r = 0; r < routes.size(); r++){
			
			if(routes.get(r).get(routes.get(r).size()-1)[0] == -2){
				
				formatRoutes.add(backtrack(r,startMap,routes));
			}
		}
		
		for(int f = 0; f < routes.size(); f++){
			System.out.println("next route");
			for(int i = 0; i < routes.get(f).size(); i++){
				
				System.out.println(routes.get(f).get(i)[0] + " " 
						+ routes.get(f).get(i)[1]);
			}
		}
		
		int minScore = Integer.MAX_VALUE;
		int index = 0;
		
		for(int b = 0; b < formatRoutes.size(); b++){
			
			if(formatRoutes.get(b).size() < minScore){
				
				index = b;
				minScore = formatRoutes.get(b).size();
			}
		}
		
		return formatRoutes.get(index);
	}
	
	public static void main(String[] args) {
		
		ArrayList<int[]> path = new MapRouteFinder(new UnitList(),
		new BuildingList(), new MapList("game1")).getBestRoute(0,0,15,15,0,3);
		
		System.out.println("END RESULT //////");
		//for(int p = 0; p < path.size(); p++){
			
			//System.out.println(path.get(p)[0] + " " + path.get(p)[1]);
		//}
 	}

}
