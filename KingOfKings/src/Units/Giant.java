package Units;

import Buildings.Names;

public class Giant extends Unit{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":" + Names.GIANT;
	}

	
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return super.getMaxHealth() + 1500;
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack()  + 4;
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
		return super.getDefence() + 4;
	}
	
	@Override
	public int turnsPerAHit() {
		// TODO Auto-generated method stub
		return super.turnsPerAHit()+1;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() + 1;
	}
	
	@Override
	public int goldNeeded() {
		// TODO Auto-generated method stub
		return super.goldNeeded() + 150;
	}
	
	@Override
	public int foodNeeded() {
		// TODO Auto-generated method stub
		return super.foodNeeded() + 100;
	}
}
