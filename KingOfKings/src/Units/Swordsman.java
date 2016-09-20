package Units;

import Buildings.Names;

public class Swordsman extends Unit {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":" + Names.SWORDSMAN;
	}
	
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return super.getMaxHealth() + 500;
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 3;
	}
	
	public int getBiasAttack(String unitType){
		
		int bias = 0;
		
		if(unitType.equals("Archer")){
			
			bias = 2;
		}
		
		return this.getAttack() + bias;
	}
	
	@Override
	public int getDefence() {
		// TODO Auto-generated method stub
		return super.getDefence() + 5;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() + 5;
	}
	
	@Override
	public int foodNeeded() {
		// TODO Auto-generated method stub
		return super.foodNeeded()+150;
	}
	
	@Override
	public int goldNeeded() {
		// TODO Auto-generated method stub
		return super.goldNeeded()+75;
	}
}
