package Buildings;

public class Dock extends Naval{

	public Dock(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "dock";
	}
	
	@Override
	public String unitcreated() {
		// TODO Auto-generated method stub
		return super.unitcreated() + "flagship;warship;fishingboat";
	}

	@Override
	public int getBuildTime() {
		// TODO Auto-generated method stub
		return super.getBuildTime()+30;
	}
	
	@Override
	public int getSizeX() {
		// TODO Auto-generated method stub
		return super.getSizeX()+2;
	}
	
	@Override
	public int getSizeY() {
		// TODO Auto-generated method stub
		return super.getSizeY()+2;
	}
	
	@Override
	public int GoldNeeded() {
		// TODO Auto-generated method stub
		return super.GoldNeeded()+10000;
	}
}
