package Buildings;

public class Stockpile extends UnitCreator {

	public Stockpile(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Names.STOCKPILE;
	}

	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() + 100;
	}
	
	@Override
	public int getBuildTime() {
		// TODO Auto-generated method stub
		return super.getBuildTime()+100;
	}
	
//	public String getUnitBuild(){
//		
//		return Names.SLAVE + ";" + Names.SERVANT;
//	}
//	
//	@Override
//	public String unitcreated() {
//		// TODO Auto-generated method stub
//		return super.unitcreated() + Names.SLAVE + ";" + Names.SERVANT;
//	}
	
	@Override
	public String unitcreated() {
		// TODO Auto-generated method stub
		return Names.WORKER;
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
		return super.GoldNeeded() + 600;
	}
	
	@Override
	public int FoodNeeded() {
		// TODO Auto-generated method stub
		return super.FoodNeeded() + 600;
	}
}
