package Buildings;

public class Farm extends Resource {

	public Farm(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Names.FARM;
	}

	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() - 200;
	}
	
	@Override
	public int getBuildTime() {
		// TODO Auto-generated method stub
		return super.getBuildTime()+60;
	}
	
	@Override
	public int getSizeX() {
		// TODO Auto-generated method stub
		return super.getSizeX() + 2;
	}
	
	@Override
	public int getSizeY() {
		// TODO Auto-generated method stub
		return super.getSizeY() + 2;
	}
	
	@Override
	public int GoldNeeded() {
		// TODO Auto-generated method stub
		return super.GoldNeeded()+150;
	}
}
