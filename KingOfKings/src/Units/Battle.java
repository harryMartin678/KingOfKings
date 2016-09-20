package Units;

import Map.CollisionMap;

public class Battle {
	
	private Unit one;
	private Unit two;
//	private boolean turn;
	private int count;
	private boolean onePullOut;
	private boolean twoPullOut;
//	private int reactionOne;
//	private int reactionTwo;

	
	public Battle(Unit one, Unit two){
		
		this.one = one;
		this.two = two;
		
		onePullOut = false;
		twoPullOut = false;
		
		count = 0;
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
	
	public void simulateHit(){
		
		count++;
		int distance = calculateDistance(one, two);
				
		
		if(!one.exitBattle()){
			
			OperateUnit(one, two, distance);
		
		}else{
			
			one.stopAttack(false);
		}
		
		if(!two.exitBattle()){
			
			OperateUnit(two, one, distance);
		
		}else{
			
			two.stopAttack(false);
		}
		
		if(count > 10000){
			
			count = 0;
		}
		
		//System.out.println(one.getHealth() + " " + two.getHealth() + " Battle");
	}
	

	private int calculateDistance(Unit a, Unit b){
		
		return (int)(Math.sqrt(Math.pow(a.getX() - b.getX(), 2) 
						+ Math.pow(a.getY() - b.getY(), 2)));
		
	}
	
	private void OperateUnit(Unit attacker, Unit opponent,int distance){

		//System.out.println((count % attacker.turnsPerAHit()) + " Battle");
		//retreat
		if((attacker.getRange() > opponent.getRange() && distance <= opponent.getRange())
				|| attacker.isWorker()){
			
			//System.out.println("RETREAT " + attacker.getName() + " Battle");
			attacker.Retreat(opponent.getX(), opponent.getY());
			attacker.stopAttack(false);
			attacker.stopFollow();
			//attack = 0;
			
		}
		//chase
		else if(attacker.getRange() < distance){
			
			//System.out.println("CHASE " + attacker.getName() + " Battle");
			attacker.setRetreat(false, false);
			attacker.stopAttack(false);
			//long range
//			if(Math.abs(attacker.getX() - opponent.getX()) > 2 
//					|| Math.abs(attacker.getY() - opponent.getY()) > 2){
//				
			attacker.follow(opponent.getUnitNo(), (int)opponent.getX(), (int)opponent.getY(),
					opponent.getMap());
				
				//attack = 0;
			
			//}
//			//short range
//			}else{
//				
//				attacker.stopFollow();
//				attacker.chase(opponent.getX(),opponent.getY());
//				
//				int newDist = calculateDistance(attacker, opponent);
//				//System.out.println(newDist + " " + attacker.getRange() + " Battle");
//				if(attacker.getRange() <= newDist){
//					
//					attack = 1;
//					
//				}else{
//
//					attack = 0;
//					attacker.move();
//					
//				}
//			}
			
			//System.out.println((attacker.turnsPerAHit() % count) + " Battle");
		}
		//attack
		else {
			
			
			attacker.setRetreat(false, false);
			attacker.stopFollow();
			
			attacker.attack(Math.round(opponent.getX()), Math.round(opponent.getY()));
			
			if(count % attacker.turnsPerAHit() == 0){
				int bias = (attacker.getAttack() - opponent.getDefence());
				if(bias < 0){
					//can't be negative
					bias = 0;
				}
				
				//attacker.setRetreat(false,false);
				//hit is attack + the bias 
				int hit = attacker.getAttack() + bias;
				//remove the health 
//				System.out.println("Health From: " + opponent.getUnitNo() + " " + opponent.getName() 
//				+ " Health taken by: " + attacker.getUnitNo() + " " + attacker.getName() + " " + opponent.dead()
//				+ " " + attacker.dead() + " Battle");
				opponent.removeHealth(hit);
			}
				
		}
		
//		System.out.println(attacker.getUnitNo() + " " + attacker.getFollow() + " " + attacker.getName() 
//			+ " "  + opponent.getUnitNo() + " Battle");
		
		
	}
	
//	public void similuateHit(){//CollisionMap map){
//		
//		//int distance = (int) Math.sqrt(Math.pow((double) ((one.getX() - two.getX()) + (one.getY() - two.getY())),2));
//		
//		//int distance = (int) Math.max(Math.abs(one.getX() - two.getX()), Math.abs(one.getY() - two.getY()));
//		int distance = (int)(Math.sqrt(Math.pow(one.getX() - two.getX(), 2) + Math.pow(one.getY() - two.getY(), 2)));
//		//System.out.println((int) Math.max(Math.abs(one.getX() - two.getX()), Math.abs(one.getY() - two.getY())) +
//				//" distance " + one.getUnitNo() + " " + two.getUnitNo());
//
//		if(turn && count%two.turnsPerAHit() == 0 && distance <= two.getRange() && !twoPullOut){
//			
//			if( one.getRange() > two.getRange() && distance <= one.getRange()){
//				
//				
//				if(reactionOne == 5){
//					one.Retreat(two.getX(),two.getY());
//				}else{
//					one.attack((int)two.getX(), (int)two.getY());
//					reactionOne ++;
//				}
//			
//			}else{
//				//defence's effect on the attack 
//				reactionOne = 0;
//				int bias = (two.getAttack() - one.getDefence());
//				if(bias < 0){
//					//can't be negative
//					bias = 0;
//				}
//				
//				two.setRetreat(false,false);
//				//hit is attack + the bias 
//				int hit = two.getAttack() + bias;
//				//remove the health 
//				one.removeHealth(hit);
//				
//			}
//			
//		}else if(!turn && count%one.turnsPerAHit() == 0 && distance <= one.getRange() && !onePullOut){
//			
//			
//			if(two.getRange() > one.getRange() && distance <= two.getRange()){
//				
//				System.out.println(two.getName() + "  Retreat Battle");
//				if(reactionTwo == 5){
//					two.Retreat(one.getX(),one.getY());
//				}else{
//					two.attack((int)one.getX(), (int)one.getY());
//					reactionTwo++;
//				}
//			
//			}else{
//				
//				System.out.println(two.getName() + "  Attack Battle");
//				reactionTwo = 0;
//				int bias = (one.getAttack() - two.getDefence());
//				if(bias < 0){
//					
//					bias = 0;
//				}
//				
//				one.setRetreat(false,false);
//				
//				int hit = one.getAttack() + bias;
//				two.removeHealth(hit);
//	
//			}
//		}
//		
//		if(!turn) count++;
//		//battle is turn based hits 
//		turn =! turn;
//		
//	}
	

}
