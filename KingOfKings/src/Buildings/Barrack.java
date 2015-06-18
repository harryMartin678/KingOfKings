package Buildings;

public class Barrack extends Army {
	
	public Barrack(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() + 500;
	}
	
	@Override
	public int getBuildTime() {
		// TODO Auto-generated method stub
		return super.getBuildTime()+30;
	}
	
	@Override
	public String unitcreated() {
		// TODO Auto-generated method stub
		return super.unitcreated() +"archer;heavyarcher;axeman;spearman;swordsman";
	}
	
	public String getUnitBuild(){
		
		return "Spearman;Axeman;Swordsman;Archer;HeavyArcher";
	}

}
