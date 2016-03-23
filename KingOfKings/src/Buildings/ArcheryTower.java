package Buildings;

public class ArcheryTower extends Tower {
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return Names.ARCHERYTOWER;
	}
	
	public ArcheryTower(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 2;
	}
	
	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() + 200;
	}
	
	@Override
	public int getBuildTime() {
		// TODO Auto-generated method stub
		return super.getBuildTime()+100;
	}
	
	@Override
	public int getSizeX() {
		// TODO Auto-generated method stub
		return super.getSizeX()+1;
	}
	
	@Override
	public int getSizeY() {
		// TODO Auto-generated method stub
		return super.getSizeY()+1;
	}
	
	@Override
	public int GoldNeeded() {
		// TODO Auto-generated method stub
		return super.GoldNeeded() + 150;
	}
	
	@Override
	public int FoodNeeded() {
		// TODO Auto-generated method stub
		return super.FoodNeeded() + 250;
	}

}
