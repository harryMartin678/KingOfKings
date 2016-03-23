package Units;

import Buildings.Names;

public class BatteringRam extends SiegeWeapon{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":" + Names.BATTERINGRAM;
	}
	
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return super.getMaxHealth() + 25;
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 25;
	}
	
	@Override
	public int getDefence() {
		// TODO Auto-generated method stub
		return super.getDefence() + 1;
	}
	
	@Override
	public int goldNeeded() {
		// TODO Auto-generated method stub
		return super.goldNeeded()+200;
	}
	
	@Override
	public int foodNeeded() {
		// TODO Auto-generated method stub
		return super.foodNeeded()+100;
	}
}
