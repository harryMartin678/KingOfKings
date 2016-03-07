package Buildings;

import java.util.ArrayList;

import Units.UnitList;
import Units.Worker;

public class BuildingProgress {
	
	private ArrayList<BuildingSite> sites;

	public BuildingProgress(){
		
		sites = new ArrayList<BuildingSite>();
	}
	
	public void addSite(Building building, ArrayList<Worker> creators){
		
		sites.add(new BuildingSite(building, creators));
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
				
				buildings.addBuilding(sites.get(i).getBuilding());
				sites.remove(i);
			}
		}
	}
	
	public String getSiteInfo(int index){
		
		return sites.get(index).toString();
	}
	
	public boolean isOnMap(int map,int index){
		
		return sites.get(index).getBuilding().getMap() == map;
	}
	
	public int size(){
		
		return sites.size();
	}

	public boolean inSite(int x, int y) {
		// TODO Auto-generated method stub
		for(int s = 0; s < sites.size(); s++){
			
			if(sites.get(s).getBuilding().inBuilding(x, y)){
				
				return true;
			}
		}
		
		return false;
	}

	public int getSiteSize() {
		// TODO Auto-generated method stub
		return sites.size();
	}

	public int getSiteMap(int s) {
		// TODO Auto-generated method stub
		return sites.get(s).getBuilding().getMap();
	}

	public int getSiteX(int s) {
		// TODO Auto-generated method stub
		return sites.get(s).getBuilding().getX();
	}

	public int getSiteY(int s) {
		// TODO Auto-generated method stub
		return sites.get(s).getBuilding().getY();
	}
}
