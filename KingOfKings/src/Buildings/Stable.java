package Buildings;

public class Stable extends Army {

	public Stable(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
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
	
	public String getUnitBuild(){
		
		return "LightChariot;HeavyChariot";
	}
}
