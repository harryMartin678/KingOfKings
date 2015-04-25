package Units;

public class Crossbow extends Archer {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":Crossbow";
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 2;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() - 1; 
	}

}
