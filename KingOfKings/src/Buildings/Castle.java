package Buildings;

public class Castle extends Tower {
	
	public Castle(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Names.CASTLE;
	}

	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 7;
	}
	
	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() + 2000;
	}
	
	@Override
	public int getBuildTime() {
		// TODO Auto-generated method stub
		return super.getBuildTime()+200;
	}
	
//	@Override
//	public String unitcreated() {
//		// TODO Auto-generated method stub
//		return super.unitcreated() + Names.BATTERINGRAM + ";" + Names.HEAVYARCHER;
//	}
	
	
	@Override
	public int getSizeX() {
		// TODO Auto-generated method stub
		return super.getSizeX();
	}
	
	@Override
	public int getSizeY() {
		// TODO Auto-generated method stub
		return super.getSizeY();
	}
	
	@Override
	public int GoldNeeded() {
		// TODO Auto-generated method stub
		return super.GoldNeeded()+1000;
	}
	
	@Override
	public int FoodNeeded() {
		// TODO Auto-generated method stub
		return super.FoodNeeded()+2000;
	}

}
