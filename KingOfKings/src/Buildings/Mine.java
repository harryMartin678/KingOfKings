package Buildings;

public class Mine extends Resource {

	public Mine(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "mine";
	}

	public int getMaxHitpoint() {
		
		return super.getMaxHitpoint()-100;
	}
	
	@Override
	public int getBuildTime() {
		// TODO Auto-generated method stub
		return super.getBuildTime()+15;
	}
}
