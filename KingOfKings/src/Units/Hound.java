package Units;

import Buildings.Names;

public class Hound extends Unit {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":" + Names.HOUND;
	}
//	@Override
//	public int getRange() {
//		// TODO Auto-generated method stub
//		return super.getRange() + 1;
//	}
//	
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return super.getMaxHealth() + 750;
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
		return super.getSpeed() + 6;
	}
	
	@Override
	public int goldNeeded() {
		// TODO Auto-generated method stub
		return super.goldNeeded() + 50;
	}
	
	@Override
	public int foodNeeded() {
		// TODO Auto-generated method stub
		return super.foodNeeded() + 100;
	}
}
