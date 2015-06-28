package Buildings;

public class Wall extends Defence {

	public Wall(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "wall";
	}

	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() + 2000;
	}
	
	@Override
	public int getBuildTime() {
		// TODO Auto-generated method stub
		return super.getBuildTime()+1;
	}
}
