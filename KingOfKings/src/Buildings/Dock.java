package Buildings;

public class Dock extends Naval{

	public Dock(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
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
}
