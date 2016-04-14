package Buildings;

import Units.Unit;

public class TowerBattle {
	
	private Unit unit;
	private Tower towerOne;
	private Tower towerTwo;
	private boolean turn;
	private int count;
	
	public TowerBattle(Unit unit, Tower tower){
		
		this.unit = unit;
		this.towerOne = tower;
	}
	
	public TowerBattle(Tower one, Tower two){
		
		this.towerOne = one;
		this.towerTwo = two;
	}
	
	public boolean deathTowers(){
		
		return (towerOne.destroyed()) || (towerTwo.destroyed());
	}
	
	public boolean death(){
		
		return (towerOne.destroyed()) || (unit.dead());
	}
	
	public boolean InRange(){
		
		int distance = (int) Math.sqrt(Math.pow((double)(unit.getX() - towerOne.getX()), 2)
				+ Math.pow((double)(unit.getY() - towerOne.getY()), 2));
		
		return distance <= unit.getRange();
	}
	
	public boolean twoTowers(){
		
		return !(towerTwo == null);
	}

	
	public void similuateHitTowers(){
		
		int distance = (int) Math.sqrt(Math.pow((double) ((towerOne.getX() - towerTwo.getX()) + (towerOne.getY() - towerTwo.getY())),2));
		
		if(turn && count%towerTwo.turnsPerAHit() == 0 && distance <= towerTwo.getRange()){
			
			//hit is attacks 
			int hit = towerTwo.getAttack();
			//remove the health 
			towerOne.removeHitpoints(hit);
	
		
		}else if(!turn && count%towerOne.turnsPerAHit() == 0 && distance <= towerOne.getRange()){
		
			
			int hit = towerOne.getAttack();
			towerTwo.removeHitpoints(hit);

		}
		
		if(!turn) count++;
		//battle is turn based hits 
		turn =! turn;
		
	}

	public void similuateHit(){
		
		int distance = (int) Math.sqrt(Math.pow((double)(unit.getX() - towerOne.getX()), 2)
				+ Math.pow((double)(unit.getY() - towerOne.getY()), 2));
		
		if(turn && count%unit.turnsPerAHit() == 0 && distance <= unit.getRange()){
			
			//hit is attack + the bias 
			int hit = unit.getAttack();
			//remove the health 
			towerOne.removeHitpoints(hit);
	
		
		}else if(!turn && count%towerOne.turnsPerAHit() == 0 && distance <= towerOne.getRange()){
			
			int bias = (towerOne.getAttack() - unit.getDefence());
			if(bias < 0){
				
				bias = 0;
			}
			
			int hit = towerOne.getAttack() + bias;
			unit.removeHealth(hit);
	
		}
		
		//System.out.println(towerOne.getHitpoints() + " " + unit.getHealth() + " TowerBattle");
		if(!turn) count++;
		//battle is turn based hits 
		turn =! turn;
		
	}
	
	public int getUnitNo(){
		
		return unit.getUnitNo();
	}
	
	public void stopAttacking() {
		// TODO Auto-generated method stub
		unit.stopAttack(false);
		towerOne.stopAttack();
	}
	
	public void attack() {
		// TODO Auto-generated method stub
		unit.attack(towerOne.getX(), towerOne.getY());
	}
	
	public static void main(String[] args) {
		
		Castle ct = new Castle(0);
		BallistaTower bt = new BallistaTower(0);
		TowerBattle tb = new TowerBattle(ct,bt);
		
		ct.setPos(0, 0);
		bt.setPos(1, 1);
		
		tb.similuateHitTowers();
	}

	

	

}
