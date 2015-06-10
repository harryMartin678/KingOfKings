package Buildings;

public class UnitCreator extends Building {
	
	public UnitCreator(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() + 1000;
	}

}
