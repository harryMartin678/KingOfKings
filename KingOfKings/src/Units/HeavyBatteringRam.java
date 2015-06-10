package Units;

public class HeavyBatteringRam extends BatteringRam {
	
	@Override
	public int getDefence() {
		// TODO Auto-generated method stub
		return super.getDefence() + 1;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() - 1;
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 2;
	}

}
