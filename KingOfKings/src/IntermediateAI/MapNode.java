package IntermediateAI;

import java.util.ArrayList;

import Buildings.BuildingList;
import Map.CollisionMap;
import Map.GameEngineCollisionMap;
import Map.Map;
import Units.UnitList;

public class MapNode {
	
	private ArrayList<int[]> path;
	private int mapNo;
	
	public MapNode(int startX, int startY, int targetX, int targetY,int mapNo){
		
		path = new Pathfinder(GameEngineCollisionMap.toArray(mapNo),
				0).getPath(startX, startY, targetX, targetY);
		this.mapNo = mapNo;
	}
	
	public int getScore(){
		
		return path.size();
	}
	
	public ArrayList<int[]> getPath(){
		
		return path;
	}
	
	public int getMapNo(){
		
		return mapNo;
	}

}
