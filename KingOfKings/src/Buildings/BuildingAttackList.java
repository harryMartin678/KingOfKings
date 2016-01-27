package Buildings;

import java.util.ArrayList;

import Units.Unit;

public class BuildingAttackList {
	
	private ArrayList<BuildingDestruction> buildingDesList;

	public BuildingAttackList(){
		
		buildingDesList = new ArrayList<BuildingDestruction>();
	}
	
	public void add(Unit unit, Building building){
		
		buildingDesList.add(new BuildingDestruction(building,unit));
	}
	
	public void simulateDestruction(){
		
		for(int d = 0; d < buildingDesList.size(); d++){
			
			if(!buildingDesList.get(d).buildingDestroyed()){
				buildingDesList.get(d).simulateHit();
			}else{
				
				buildingDesList.get(d).stopAttacking();
				buildingDesList.remove(d);
				d--;
			}
		}
	}
}
