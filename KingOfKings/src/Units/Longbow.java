package Units;

public class Longbow extends Archer {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":Longbow";
	}
	
	@Override
	public int getRange() {
		// TODO Auto-generated method stub
		return super.getRange() + 2;
	}
	
	@Override
	public int getDefence() {
		// TODO Auto-generated method stub
		return super.getDefence() + 1;
	}

}
