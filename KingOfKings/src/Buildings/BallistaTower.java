package Buildings;

public class BallistaTower extends Tower {
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 4;
	}
	
	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() + 300;
	}

}
