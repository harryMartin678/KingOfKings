package Units;
import Buildings.Names;

public class Slave extends Worker {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":" + Names.SLAVE;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() + 1;
	}
	
	@Override
	public int foodNeeded() {
		// TODO Auto-generated method stub
		return super.foodNeeded() + 400;
	}
	
	@Override
	public int goldNeeded() {
		// TODO Auto-generated method stub
		return super.goldNeeded() + 400;
	}
}
