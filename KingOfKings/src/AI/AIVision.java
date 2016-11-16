package AI;

import java.util.ArrayList;
import java.util.HashMap;

import Buildings.BuildingList;
import Map.GameEngineCollisionMap;
import Units.UnitList;

public class AIVision {
	
	public enum Type{
		
		UNIT,
		BUILDING,
		SITE
	}
	
	public UnitList units;
	
	public AIVision(UnitList units, BuildingList buildings){
		
		this.units = units;
	}
	
	public int[] getUnitPos(int unitNo){
		
		return this.units.getUnits(unitNo).getPos();
	}
	
	public int[] findBuildingSpot(int mapNo,String name){
		
		return GameEngineCollisionMap.FindBuildingSpot(mapNo,name);
	}
	
	public int[] findMineSpot(int mapNo){
		
		return GameEngineCollisionMap.FindMineSpot(mapNo);
	}

	public ArrayList<Integer> getWorker(int mapNo,int aiNum) {
		// TODO Auto-generated method stub
		ArrayList<Integer> workers = GameEngineCollisionMap.getWorkers(mapNo);
		
		for(int w = 0; w < workers.size(); w++){
			
			if(units.getUnitPlayer(workers.get(w)) != aiNum || 
					!units.getUnitIsIdle(workers.get(w))){
				
				workers.remove(w);
				w--;
			}
		}
		
		return workers;
	}

	public float getUnitX(int unitNo) {
		// TODO Auto-generated method stub
		return this.units.getUnitX(unitNo);
	}
	
	public float getUnitY(int unitNo){
		
		return this.units.getUnitY(unitNo);
	}
	

}
