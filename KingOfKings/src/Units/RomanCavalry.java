package Units;

public class RomanCavalry extends Cavalry{
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":Roman Cavalry";
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 1;
	}
	
	@Override
	public int getDefence() {
		// TODO Auto-generated method stub
		return super.getDefence() + 1;
	}

}
