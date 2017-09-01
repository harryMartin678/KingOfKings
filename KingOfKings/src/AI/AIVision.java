package AI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import Buildings.BuildingList;
import Buildings.Names;
import Map.GameEngineCollisionMap;
import Units.UnitList;
import Util.Point;

public class AIVision {
	
	public enum Type{
		
		UNIT,
		BUILDING,
		SITE
	}
	
	private UnitList units;
	private BuildingList buildings;
	
	
	public AIVision(UnitList units, BuildingList buildings){
		
		this.units = units;
		this.buildings = buildings;
	}
	
	public int[] getUnitPos(int unitNo){
		
		return this.units.getUnits(unitNo).getPos();
	}
	
	public int[] findBuildingSpot(int mapNo,String name,Random generator){
		
		if(name == Names.MINE){
			
			return GameEngineCollisionMap.FindMineSpot(mapNo);
		}
		
		return GameEngineCollisionMap.FindBuildingSpot(mapNo,name,generator);
	}
	
	public int getRequiredWallSize(int mapNo){
		
		return GameEngineCollisionMap.getRequiredWallSize(mapNo);
	}

	public ArrayList<Integer> getWorker(int mapNo,int aiNum) {
		// TODO Auto-generated method stub
		ArrayList<Integer> workers = units.getWorkersOnMap(mapNo,aiNum);
		
		//System.out.println(workers.size() + " AIVision");
		units.begin();
		for(int w = 0; w < workers.size(); w++){

			if(!units.getUnitIsIdle(workers.get(w))){
				
				//System.out.println(units.getUnitIsIdle(workers.get(w)) 
				//		+ " " + workers.get(w) + " AIVision");
				workers.remove(w);
				w--;
			}
		}
		units.end();
		
		return workers;
	}
	
	public ArrayList<Integer> getUnitBuilder(String name,int mapNo){
		
		ArrayList<Integer> buildingNos = GameEngineCollisionMap.getBuildings(mapNo);

		for(int b = 0; b < buildingNos.size(); b++){
			
			if(!buildings.canCreate(name,buildingNos.get(b))){
				
				buildingNos.remove(buildingNos.get(b));
				b--;
			}
		}
		
		return buildingNos;
	}

	public float getUnitX(int unitNo) {
		// TODO Auto-generated method stub
		return this.units.getUnitX(unitNo);
	}
	
	public float getUnitY(int unitNo){
		
		return this.units.getUnitY(unitNo);
	}

	public Point getMapCenter(int mapNo) {
		// TODO Auto-generated method stub
		return GameEngineCollisionMap.getMapCenter(mapNo);
	}
	

}
