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
		
		unit.stopAttack();
	}
	
	public boolean buildingDestroyed(){
		
		return building.destroyed();
	}
	
	//Simulates destruction of a building 
	public void simulateHit(){
		
		int distance = (int) Math.sqrt(Math.pow((double) ((building.getX() - unit.getX()) + (building.getY() - unit.getY())),2));
		
		if(distance <= unit.getRange()){
			//if(!unit.isAttacking()){
				//System.out.println("UNIT ATTACK in buildingdestruction");
				unit.attack(building.getX() - building.getSizeX()/2, 
						building.getY() - building.getSizeY()/2);
			//}
			building.removeHitpoints(unit.getAttack()*2);
		}else{
			
			if(unit.isAttacking()){
				//System.out.println("UNIT STOP ATTACK in buildingdestruction");
				unit.stopAttack();
			}
		}
	}

}
