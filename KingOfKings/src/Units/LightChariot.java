package Units;

public class LightChariot extends Chariot {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName()+":Light Cavalry";
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() - 1;
	}
	
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return super.getMaxHealth() - 25;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() + 5;
	}
	
	@Override
	public int getDefence() {
		// TODO Auto-generated method stub
		return super.getDefence() - 1;
	}

}
