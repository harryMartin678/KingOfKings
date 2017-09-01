package Buildings;

public class GiantLiar extends UnitCreator {

	public GiantLiar(int buildingNo){
		
		super(buildingNo);
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
	public String getType() {
		// TODO Auto-generated method stub
		return Names.GIANTLIAR;
	}
	
	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint()+1000;
	}
	
	@Override
	public int getBuildTime() {
		// TODO Auto-generated method stub
		return super.getBuildTime()+200;
	}
	
	@Override
	public String unitcreated() {
		// TODO Auto-generated method stub
		return Names.GIANT;
	}
	
	@Override
	public int FoodNeeded() {
		// TODO Auto-generated method stub
		return super.FoodNeeded()+400;
	}
	
	@Override
	public int GoldNeeded() {
		// TODO Auto-generated method stub
		return super.GoldNeeded()+300;
	}
}
