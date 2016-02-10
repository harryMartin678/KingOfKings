package Units;

import Buildings.Names;

public class Archer extends Unit {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":" + Names.ARCHER;
	}
	@Override
	public int getRange() {
		// TODO Auto-generated method stub
		return super.getRange() + 3;
	}
	
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return super.getMaxHealth() + 500;
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack()  + 2;
	}
	
	public int getBiasAttack(String unitType){
		
		int bias = 0;
		if(unitType.equals("Siege")){
			
			bias = - 1;
		}
		
		return this.getAttack() + bias;
	}
	
	@Override
	public int getDefence() {
		// TODO Auto-generated method stub
		return super.getDefence() + 1;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() + 3;
	}

}
