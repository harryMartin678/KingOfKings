package Buildings;

import java.util.ArrayList;

import Units.Unit;

public class BuildingAttackList {
	
	private ArrayList<BuildingDestruction> buildingDesList;
	private IComMapBATTList ChangeMapsPlayer;
	private IComBuildingBATTList ChangeBuildingsOnMap;

	public BuildingAttackList(IComMapBATTList ChangeMapsPlayer,
			IComBuildingBATTList ChangeBuildingsOnMap){
		
		buildingDesList = new ArrayList<BuildingDestruction>();
		this.ChangeMapsPlayer = ChangeMapsPlayer;
		this.ChangeBuildingsOnMap = ChangeBuildingsOnMap;
	}
	
	public void add(Unit unit, Building building){
		
		buildingDesList.add(new BuildingDestruction(building,unit));
	}
	
	public void simulateDestruction(){
		
		for(int d = 0; d < buildingDesList.size(); d++){
			
			//System.out.println("BUILDING ATTACK BUILDINGATTACKLIST");
			if(!buildingDesList.get(d).buildingDestroyed()){
				buildingDesList.get(d).simulateHit();
			}else{
				System.out.println("DESTROYED BUILDINGATTACKLIST");
				
				if(buildingDesList.get(d).getBuildingType().equals(Names.ROYALPALACE)){
					
					ChangeMapsPlayer.changeMapPlayer(buildingDesList.get(d).getUnitPlayer(),
							buildingDesList.get(d).getMap());
					ChangeBuildingsOnMap.changeBuildingsPlayer(buildingDesList.get(d).getUnitPlayer(),
							buildingDesList.get(d).getMap());
					buildingDesList.get(d).ReGeneratePalace();
					
				}
				
				buildingDesList.get(d).stopAttacking();
				buildingDesList.remove(d);
				d--;
					
			}
		}
	}
}
