package Units;

public class Naval extends Unit {

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return super.getMaxHealth() + 200;
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 5;
	}
	
	@Override
	public int getDefence() {
		// TODO Auto-generated method stub
		return super.getDefence() + 5;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":Naval";
	}
	
	@Override
	public int getRange() {
		// TODO Auto-generated method stub
		return super.getRange() + 5;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() + 5;
	}
}
