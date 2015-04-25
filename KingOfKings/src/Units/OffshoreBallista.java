package Units;

public class OffshoreBallista extends Naval {
	
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return super.getMaxHealth() - 100;
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 100;
	}
	
	@Override
	public int turnsPerAHit() {
		// TODO Auto-generated method stub
		return super.turnsPerAHit() + 3;
	}
	
	@Override
	public int getDefence() {
		// TODO Auto-generated method stub
		return super.getDefence() + 1;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed()-4;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":OffshoreBallista";
	}
	
	@Override
	public int getRange() {
		// TODO Auto-generated method stub
		return super.getRange() + 4;
	}

}
