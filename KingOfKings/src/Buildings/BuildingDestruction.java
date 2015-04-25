package Buildings;

import Units.Unit;

public class BuildingDestruction {
	
	Building building;
	Unit unit;
	
	public BuildingDestruction(Building building, Unit unit){
		
		this.building = building;
		this.unit = unit;
	}
	
	//Simulates destruction of a building 
	public void simulateHit(){
		
		int distance = (int) Math.sqrt(Math.pow((double) ((building.getX() - unit.getX()) + (building.getY() - unit.getY())),2));
		
		if(distance <= unit.getRange()){
			building.removeHitpoints(unit.getAttack()*2);
		}
	}

}
