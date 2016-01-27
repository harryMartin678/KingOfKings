package Units;

import Buildings.Names;

public class Axeman extends Swordsman {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":" + Names.AXEMAN;
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
		return super.getSpeed() - 1;
	}

}
