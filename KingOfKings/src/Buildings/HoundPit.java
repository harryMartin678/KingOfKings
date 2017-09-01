package Buildings;

public class HoundPit extends UnitCreator {

	public HoundPit(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Names.HOUNDPIT;
	}
	
	public int getSizeX() {
		// TODO Auto-generated method stub
		return super.getSizeX() + 2;
	}
	
	@Override
	public String unitcreated() {
		// TODO Auto-generated method stub
		return Names.HOUND;
	}
	
	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint()+1000;
	}
	
	@Override
	public int getSizeY() {
		// TODO Auto-generated method stub
		return super.getSizeY() + 2;
	}
	
	@Override
	public int getBuildTime() {
		// TODO Auto-generated method stub
		return super.getBuildTime()+200;
	}
	
	@Override
	public int FoodNeeded() {
		// TODO Auto-generated method stub
		return super.FoodNeeded()+750;
	}
	
	@Override
	public int GoldNeeded() {
		// TODO Auto-generated method stub
		return super.GoldNeeded()+600;
	}

}
