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
		
		int distance = (int) Math.sqrt(Math.pow((double) ((towerOne.getX() - unit.getX()) + (towerOne.getY() - unit.getY())),2));
		
		if(turn && count%unit.turnsPerAHit() == 0 && distance <= unit.getRange()){
			
			//hit is attack + the bias 
			int hit = unit.getAttack();
			//remove the health 
			unit.removeHealth(hit);
	
		
		}else if(!turn && count%towerOne.turnsPerAHit() == 0 && distance <= towerOne.getRange()){
			
			int bias = (towerOne.getAttack() - unit.getDefence());
			if(bias < 0){
				
				bias = 0;
			}
			
			int hit = towerOne.getAttack() + bias;
			unit.removeHealth(hit);
	
		}
		
		if(!turn) count++;
		//battle is turn based hits 
		turn =! turn;
		
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
