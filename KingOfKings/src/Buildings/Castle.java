package Buildings;

public class Castle extends Fort {
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 7;
	}
	
	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() + 2000;
	}

}
