package Units;

public class HeavyArcher extends Archer {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":Crossbow";
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 2;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() - 1; 
	}
	
	@Override
	public int getRange() {
		// TODO Auto-generated method stub
		return super.getRange() + 2;
	}
	
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return super.getMaxHealth() + 25;
	}

}
