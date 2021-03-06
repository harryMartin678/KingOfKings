package Units;

import Buildings.Names;

public class Servant extends Worker {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":" + Names.SERVANT;
	}
	
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return super.getMaxHealth() + 25;
	}
	
	@Override
	public int getProductivity() {
		// TODO Auto-generated method stub
		return super.getProductivity() + 5;
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 2;
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
	
	@Override
	public int foodNeeded() {
		// TODO Auto-generated method stub
		return super.foodNeeded()+100;
	}
	
	@Override
	public int goldNeeded() {
		// TODO Auto-generated method stub
		return super.goldNeeded()+25;
	}

}
