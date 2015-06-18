package Units;

public class Warship extends Naval {
	
	@Override
	public int boatSize() {
		// TODO Auto-generated method stub
		return super.boatSize()+3;
	}
	
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return super.getMaxHealth() + 200;
	}
	
	@Override
	public int getDefence() {
		// TODO Auto-generated method stub
		return super.getDefence() + 2;
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 3;
	}
	
	@Override
	public int getRange() {
		// TODO Auto-generated method stub
		return super.getRange() + 1;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() - 1;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":Warship";
	}

}
