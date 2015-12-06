package Buildings;

import java.util.ArrayList;

import Units.Worker;

public class BuildingSite {
	
	private Building building;
	private ArrayList<Worker> creators;
	private int progress;
	
	public BuildingSite(Building building, ArrayList<Worker> creators){
		
		this.building = building;
		this.creators = creators;
		progress = 0;
	}
	
	public Building getBuilding(){
		
		return building;
	}
	
	public void addWorker(Worker creator){
		
		creators.add(creator);
	}
//	b + " " + buildings.getBuildingType(b) + " " 
//	+ buildings.getBuildingX(b) + " " +
//	buildings.getBuildingY(b) + "\n";
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		return  building.getBuildingNo() + " " + building.getType() 
				+ " " + ((float)building.getX()) + " " + ((float)building.getY()) 
				+ " " + building.getPlayer() + "\n";
		
	}
	
	public boolean progress(){
		
		double tempProgress = (double) progress;
		
		for(int i = 0; i < creators.size(); i++){
			
			if(creators.get(i).isCreating() == building.getBuildingNo()
					&& creators.get(i).nearBuilding(building)){
				
				creators.get(i).attack(building.getX(), building.getY());
				tempProgress += (double) 1/ (double) (i+1);
				
			}else{
				creators.get(i).stopAttack();
			}
		}
		
		progress = (int) tempProgress;
		
		if(progress < (building.getBuildTime()*10)){
			
			return true;
		
		}else{
			
			for(int c = 0; c < creators.size(); c++){
				
				creators.get(c).stopAttack();
			}
			
			return false;
		}
		
	}

}
