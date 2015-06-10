package Buildings;

import java.util.ArrayList;

import Units.UnitList;
import Units.Worker;

public class BuildingProgress {
	
	private ArrayList<BuildingSite> sites;

	public BuildingProgress(){
		
		sites = new ArrayList<BuildingSite>();
	}
	
	public void addSite(Building building, Worker creator){
		
		sites.add(new BuildingSite(building, creator));
	}
	
	public void addWorker(Worker creator){
		
		for(int i = 0; i < sites.size(); i++){
			
			if(sites.get(i).getBuilding().getBuildingNo() == creator.isCreating()){
				
				sites.get(i).addWorker(creator);
			}
		}
	}
	
	public void checkSites(BuildingList buildings){
		
		for(int i = 0; i < sites.size(); i++){
			
			if(!sites.get(i).progress()){
				
				System.out.println("yes");
				buildings.addBuilding(sites.get(i).getBuilding());
				sites.remove(i);
			}
		}
	}
}
