package Units;

public class Catapult extends SiegeWeapon {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":Catapult";
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() - 25;
	}
	
	@Override
	public int getRange() {
		// TODO Auto-generated method stub
		return super.getRange() + 10;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() + 1;
	}
}
