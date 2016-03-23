package Buildings;

import Units.Unit;

public class BuildingDestruction {
	
	Building building;
	Unit unit;
	
	public BuildingDestruction(Building building, Unit unit){
		
		this.building = building;
		this.unit = unit;
	}
	
	public void stopAttacking(){
		
		unit.stopAttack(false);
	}
	
	public String getBuildingType(){
		
		return building.getType();
	}
	
	public boolean buildingDestroyed(){
		
		return building.destroyed();
	}
	
	//Simulates destruction of a building 
	public void simulateHit(){
		
		int buildingSize = (int)(building.getSizeX() * 
				((building.getX() - unit.getX()))/Math.abs((building.getX() - unit.getX()))) + 
				(int)(building.getSizeY() * 
						((building.getY() - unit.getY()))/Math.abs((building.getY() - unit.getY())));
		int distance = (int) Math.sqrt(Math.pow((double) ((building.getX() - unit.getX()) + 
				(building.getY() - unit.getY())),2)) - Math.abs(buildingSize);
		//System.out.println(distance + " " + buildingSize + " " + unit.getRange() + " BuildingDestruction");
		if(distance <= unit.getRange()){
			//System.out.println("IN RANGE BUILDINGDESTRUCTION");
			//if(!unit.isAttacking()){
				//System.out.println("UNIT ATTACK in buildingdestruction");
				unit.attack(building.getX() - building.getSizeX()/2, 
						building.getY() - building.getSizeY()/2);
			//}
			building.removeHitpoints(unit.getAttack());
		}else{
			
			if(unit.isAttacking()){
				//System.out.println("UNIT STOP ATTACK in buildingdestruction");
				unit.stopAttack(false);
			}
		}
	}

	public int getUnitPlayer() {
		// TODO Auto-generated method stub
		return unit.getPlayer();
	}
	
	public int getMap(){
		
		return building.getMap();
	}

	public void ReGeneratePalace() {
		// TODO Auto-generated method stub
		building.regenerate();
	}

}
