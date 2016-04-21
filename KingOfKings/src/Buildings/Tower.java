package Buildings;

import java.util.ArrayList;

public class Tower extends Defence {

	private ArrayList<Integer> attack;
	
	public Tower(int buildingNo) {
		super(buildingNo);
		// TODO Auto-generated constructor stub
		attack = new ArrayList<Integer>();
	}
	
	public void setAttacking(int unitNo){
		
		attack.add(unitNo);
	}
	
	public void stopAttacking(){
		
		attack.remove(attack.size()-1);
	}
	
	public int getAttacking(){
		
		if(attack.size() == 0){
			
			return -1;
		}
		
		return attack.get(attack.size()-1);
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

}
