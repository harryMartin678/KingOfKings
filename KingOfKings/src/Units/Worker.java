package Units;

import java.util.ArrayList;

import Buildings.Building;
import Buildings.Names;

public class Worker extends Unit {
	
	private int buildingNo;
	private boolean goingToBuild;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":" + Names.WORKER;
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
		return super.getSpeed() + 3;
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
		
		//System.out.println("Build Worker");
		goingToBuild = true;
		this.buildingNo = buildingNo;
	}
	
	@Override
	public void setPath(ArrayList<int[]> path) {
		// TODO Auto-generated method stub
		super.setPath(path);
		stopBuild();
	}
	
	@Override
	public boolean idle() {
		// TODO Auto-generated method stub
		if(this.getMap() == 1){
			//System.out.println(!goingToBuild + " " + this.getUnitNo() + " Worker");
		}
		return super.idle() && !goingToBuild;
	}
	
	public boolean getGoingToBuild(){
		
		return goingToBuild;
	}
	
	public boolean buildingThisBuilding(int buildingNo){
		
		return buildingNo == this.buildingNo && goingToBuild;
	}

	public void stopBuild() {
		// TODO Auto-generated method stub
		//System.out.println("Stop Build Worker");
		goingToBuild = false;
	}
	
	@Override
	public int foodNeeded() {
		// TODO Auto-generated method stub
		return super.foodNeeded()+100;
	}

	

}
