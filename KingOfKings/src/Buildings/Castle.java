package Buildings;

public class Castle extends Fort {
	
	public Castle(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "castle";
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
	
	@Override
	public String unitcreated() {
		// TODO Auto-generated method stub
		return super.unitcreated() + "batteringram;heavybatteringram";
	}
	
	@Override
	public int getSizeX() {
		// TODO Auto-generated method stub
		return super.getSizeX()+3;
	}
	
	@Override
	public int getSizeY() {
		// TODO Auto-generated method stub
		return super.getSizeY()+3;
	}

}
