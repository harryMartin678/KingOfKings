package Units;

import Map.CollisionMap;

public class Battle {
	
	private Unit one;
	private Unit two;
	private boolean turn;
	private int count;
	private boolean onePullOut;
	private boolean twoPullOut;
	
	public Battle(Unit one, Unit two){
		
		this.one = one;
		this.two = two;
		
		onePullOut = false;
		twoPullOut = false;
		
		turn = true;
		count = 1;
	}
	
	public boolean CanAttackOne(){
		
		return onePullOut;
	}
	
	public boolean CanAttackTwo(){
		
		return twoPullOut;
	}
	
	public void onePullOut(){
		
		onePullOut = true;
	}
	
	public void twoPullOut(){
		
		twoPullOut = true;
	}
	
	public void onePushIn(){
		
		onePullOut = false;
	}
	
	public void twoPushIn(){
		
		twoPullOut = false;
	}
	
	public int getOneID(){
		
		return one.getUnitNo();
	}
	
	public int getTwoID(){
		
		return two.getUnitNo();
	}
	
	public int[] getUnitPosOne(){
		
		return new int[]{(int) one.getX(),(int) one.getY()};
	}
	
	public int[] getUnitPosTwo(){
		
		return new int[]{(int) two.getX(), (int) two.getY()};
	}
	

	public boolean death(){
		
		return one.dead() || two.dead();
	}
	
	public boolean retreatTwo(){
		
		return two.getRetreat();
	}
	
	public boolean retreatOne(){
		
		return one.getRetreat();
	}
	
	public void similuateHit(){//CollisionMap map){
		
		//int distance = (int) Math.sqrt(Math.pow((double) ((one.getX() - two.getX()) + (one.getY() - two.getY())),2));
		
		//int distance = (int) Math.max(Math.abs(one.getX() - two.getX()), Math.abs(one.getY() - two.getY()));
		int distance = (int)(Math.sqrt(Math.pow(one.getX() - two.getX(), 2) + Math.pow(one.getY() - two.getY(), 2)));
		//System.out.println((int) Math.max(Math.abs(one.getX() - two.getX()), Math.abs(one.getY() - two.getY())) +
				//" distance " + one.getUnitNo() + " " + two.getUnitNo());
		
		if(turn && count%two.turnsPerAHit() == 0 && distance <= two.getRange() && !twoPullOut){
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
			
			if(one.getRange() < two.getRange()){
				
				two.Retreat(two.getX(),two.getY());
			
			}else{
				
				two.setRetreat(false,true);
			}
	
		
		}else if(!turn && count%one.turnsPerAHit() == 0 && distance <= one.getRange() && !onePullOut){
			
			int bias = (one.getAttack() - two.getDefence());
			if(bias < 0){
				
				bias = 0;
			}
			
			int hit = one.getAttack() + bias;
			two.removeHealth(hit);
			
			if(two.getRange() < one.getRange()){
				
				one.Retreat(one.getX(),one.getY());
			
			}else{
				
				one.setRetreat(false,true);
			}

		}
		
		if(!turn) count++;
		//battle is turn based hits 
		turn =! turn;
		
	}
	

}
