package Units;

public class Battle {
	
	private Unit one;
	private Unit two;
	private boolean turn;
	private int count;
	
	public Battle(Unit one, Unit two){
		
		this.one = one;
		this.two = two;
		
		turn = true;
		count = 1;
	}
	
	public void similuateHit(){
		
		int distance = (int) Math.sqrt(Math.pow((double) ((one.getX() - two.getX()) + (one.getY() - two.getY())),2));
		
		if(turn && count%two.turnsPerAHit() == 0 && distance <= two.getRange()){
			
			//defence's effect on the attack 
			int bias = (two.getAttack() - one.getDefence());
			if(bias < 0){
				//can't be negative
				bias = 0;
			}
			//hit is attack + the bias 
			int hit = two.getAttack() + bias;
			//remove the health 
			one.removeHealth(hit);
	
		
		}else if(!turn && count%one.turnsPerAHit() == 0 && distance <= one.getRange()){
			
			int bias = (one.getAttack() - two.getDefence());
			if(bias < 0){
				
				bias = 0;
			}
			
			int hit = one.getAttack() + bias;
			two.removeHealth(hit);

		}
		
		if(!turn) count++;
		//battle is turn based hits 
		turn =! turn;
		
	}

}
