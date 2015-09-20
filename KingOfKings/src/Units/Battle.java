package Units;

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
	
	public void similuateHit(){
		
		//int distance = (int) Math.sqrt(Math.pow((double) ((one.getX() - two.getX()) + (one.getY() - two.getY())),2));
		
		int distance = (int) Math.max(Math.abs(one.getX() - two.getX()), Math.abs(one.getY() - two.getY()));
		
		System.out.println((int) Math.max(Math.abs(one.getX() - two.getX()), Math.abs(one.getY() - two.getY())) +
				" distance " + one.getUnitNo() + " " + two.getUnitNo());
		
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
	
		
		}else if(!turn && count%one.turnsPerAHit() == 0 && distance <= one.getRange() && !onePullOut){
			
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
