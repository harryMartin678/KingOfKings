package Buildings;

public class Tower extends Defence {

	private int attack;
	
	public Tower(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
		attack = -1;
	}
	
	public void setAttacking(int unitNo){
		
		attack = unitNo;
	}
	
	public int getAttacking(){
		
		return attack;
	}

	public int getAttack(){
		
		return 3;
	}
	
	public int turnsPerAHit(){
		
		return 1;
	}
	
	public int getRange(){
		
		return 4;
	}
	
	@Override
	public int getMaxHitpoint() {
		// TODO Auto-generated method stub
		return super.getMaxHitpoint() + 200;
	}

	public void stopAttack() {
		// TODO Auto-generated method stub
		attack = -1;
	}
}
