package Buildings;

public class Stockpile extends Resource {

	public Stockpile(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() + 100;
	}
	
	@Override
	public int getBuildTime() {
		// TODO Auto-generated method stub
		return super.getBuildTime()+20;
	}
	
	public String getUnitBuild(){
		
		return "slave;servant";
	}
}
