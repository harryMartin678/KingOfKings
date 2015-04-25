package Units;

public class Slave extends Worker {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":Slave";
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() + 1;
	}
}
