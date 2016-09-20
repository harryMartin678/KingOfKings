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
	public String unitcreated() {
		// TODO Auto-generated method stub
		return Names.GIANT;
	}
}
