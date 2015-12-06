package Units;

import Buildings.Building;

public class Worker extends Unit {
	
	private int buildingNo;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":Worker";
	}
	
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return super.getMaxHealth() + 25;
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 1;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() + 5;
	}
	
	@Override
	public int getProductivity() {
		// TODO Auto-generated method stub
		return super.getProductivity() + 5;
	}
	
	public int isCreating(){
		
		return buildingNo;
	}
	
	public boolean nearBuilding(Building building){
		
		return Math.abs(this.getX() - building.getX()) <= building.getSizeX() &&
				Math.abs(this.getY() - building.getY()) <= building.getSizeY();
	}
	
	public void build(int buildingNo){
		
		this.buildingNo = buildingNo;
	}

}
