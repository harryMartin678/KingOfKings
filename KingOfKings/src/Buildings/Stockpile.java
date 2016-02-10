package Buildings;

public class Stockpile extends Resource {

	public Stockpile(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return "stockpile";
	}

	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() + 100;
	}
	
	@Override
	public int getBuildTime() {
		// TODO Auto-generated method stub
		return super.getBuildTime()+20;
	}
	
	public String getUnitBuild(){
		
		return "slave;servant";
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
		return super.GoldNeeded() + 600;
	}
	
	@Override
	public int FoodNeeded() {
		// TODO Auto-generated method stub
		return super.FoodNeeded() + 600;
	}
}
