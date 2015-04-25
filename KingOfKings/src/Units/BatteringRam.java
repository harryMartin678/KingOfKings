package Units;

public class BatteringRam extends SiegeWeapon{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":BatteringRam";
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
}
