package Buildings;

public class Barrack extends Army {
	
	public Barrack(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Names.BARRACK;
	}

	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() + 500;
	}
	
	@Override
	public int getBuildTime() {
		// TODO Auto-generated method stub
		return super.getBuildTime()+50;
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
	public String unitcreated() {
		// TODO Auto-generated method stub
		return super.unitcreated() +Names.SPEARMAN + ";" + Names.AXEMAN + ";" + Names.SWORDSMAN + 
					";" + Names.ARCHER + ";" + Names.HEAVYARCHER;
	}
	
	public String getUnitBuild(){
		
		
		return Names.SPEARMAN + ";" + Names.AXEMAN + ";" + 
				Names.SWORDSMAN + ";" + Names.ARCHER + ";" + Names.HEAVYARCHER;
	}
	
	@Override
	public int GoldNeeded() {
		// TODO Auto-generated method stub
		return super.GoldNeeded()+250;
	}
	
	@Override
	public int FoodNeeded() {
		// TODO Auto-generated method stub
		return super.FoodNeeded()+400;
	}

}
