package Buildings;

public class Spearyard extends UnitCreator{

	public Spearyard(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Names.SPEARYARD;
	}
	
	@Override
	public String unitcreated() {
		// TODO Auto-generated method stub
		return Names.SPEARMAN;
	}
	
	public int getSizeX() {
		// TODO Auto-generated method stub
		return super.getSizeX() + 2;
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
		return super.FoodNeeded()+600;
	}
	
	@Override
	public int GoldNeeded() {
		// TODO Auto-generated method stub
		return super.GoldNeeded()+400;
	}

}
