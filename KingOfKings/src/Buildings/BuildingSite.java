package Buildings;

import java.util.ArrayList;

import Units.Worker;

public class BuildingSite {
	
	private Building building;
	private ArrayList<Worker> creators;
	private int progress;
	
	public BuildingSite(Building building, Worker creator){
		
		this.building = building;
		creators = new ArrayList<Worker>();
		creators.add(creator);
		progress = 0;
	}
	
	public Building getBuilding(){
		
		return building;
	}
	
	public void addWorker(Worker creator){
		
		creators.add(creator);
	}
	
	public boolean progress(){
		
		double tempProgress = (double) progress;
		
		for(int i = 0; i < creators.size(); i++){
			
			if(creators.get(i).isCreating() == building.getBuildingNo()){
				tempProgress += (double) 1/ (double) (i+1);
			}
		}
		
		progress = (int) tempProgress;
		
			if(progress < (building.getBuildTime()*10)){
				
				return true;
			
			}else{
				
				return false;
			}
		
	}

}
