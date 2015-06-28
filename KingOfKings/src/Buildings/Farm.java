package Buildings;

public class Farm extends Resource {

	public Farm(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "farm";
	}

	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() - 200;
	}
	
	@Override
	public int getBuildTime() {
		// TODO Auto-generated method stub
		return super.getBuildTime()+10;
	}
}
