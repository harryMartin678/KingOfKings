package Buildings;

public class Tower extends Defence {

	public Tower(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
	}

	public int getAttack(){
		
		return 0;
	}
	
	public int turnsPerAHit(){
		
		return 1;
	}
	
	public int getRange(){
		
		return 5;
	}
	
	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() + 200;
	}
}
