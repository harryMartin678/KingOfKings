package Buildings;

public class ArcheryTower extends Tower {
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "archerytower";
	}
	
	public ArcheryTower(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 2;
	}
	
	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() + 200;
	}
	
	@Override
	public int getBuildTime() {
		// TODO Auto-generated method stub
		return super.getBuildTime()+20;
	}

}
