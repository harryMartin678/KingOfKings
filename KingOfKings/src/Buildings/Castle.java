package Buildings;

public class Castle extends Fort {
	
	public Castle(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}

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
	
	@Override
	public int getBuildTime() {
		// TODO Auto-generated method stub
		return super.getBuildTime()+60;
	}

}
