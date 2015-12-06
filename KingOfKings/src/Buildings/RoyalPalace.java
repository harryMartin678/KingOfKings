package Buildings;

public class RoyalPalace extends UnitCreator {
	
	 public RoyalPalace(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}
	 
	 @Override
	public String getType() {
		// TODO Auto-generated method stub
		return "royalpalace";
	}
	 
	 @Override
	public String unitcreated() {
		// TODO Auto-generated method stub
		return super.unitcreated() + Names.SLAVE;
	}

	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() + 2500;
	}
	 
	@Override
	public int getSizeX() {
		// TODO Auto-generated method stub
		return super.getSizeX() + 3;
	}
	
	@Override
	public int getSizeY() {
		// TODO Auto-generated method stub
		return super.getSizeY() + 3;
	}


}
