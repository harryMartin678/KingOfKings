package Units;

public class FishingBoat extends Naval{
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() + 3;
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() - 1;
	}
	
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return super.getMaxHealth() - 100;
	}
	
	@Override
	public int getRange() {
		// TODO Auto-generated method stub
		return super.getRange() - 1;
	}
	
	@Override
	public int getDefence() {
		// TODO Auto-generated method stub
		return super.getDefence() - 2;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":Frigate";
	}
	

}
