package Buildings;

public class Dock extends Naval{

	public Dock(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getBuildTime() {
		// TODO Auto-generated method stub
		return super.getBuildTime()+30;
	}
}
