package Units;

import java.util.ArrayList;

import Buildings.Tower;
import Buildings.TowerBattle;

public class UnitBattleList {
	
	private ArrayList<Battle> battles;
	private ArrayList<TowerBattle> towerBattles;
	private ArrayList<int[]> unitsToFollow;
	private UnitList units;
	
	public UnitBattleList(UnitList units){
		
		battles = new ArrayList<Battle>();
		towerBattles = new ArrayList<TowerBattle>();
		unitsToFollow = new ArrayList<int[]>();
		this.units = units;
	}
	
	public void addBattle(int one, int two){
		
		boolean noBattle = true;
		
		for(int b = 0; b < battles.size(); b++){
			
			if(battles.get(b).getOneID() == one && battles.get(b).getTwoID() == two){
				
				noBattle = false;
			}
		}
		
		if(noBattle){
			battles.add(new Battle(units.getUnits(one),units.getUnits(two)));
		}
		
	}
	
	public void addTowerUnitBattle(Unit one, Tower two){
		
		towerBattles.add(new TowerBattle(one,two));
	}
	
	public void addTowerTowerBattle(Tower one, Tower two){
		
		towerBattles.add(new TowerBattle(one,two));
	}
	
	public void simulateTowerHit(){
		
		for(int u = 0; u < towerBattles.size(); u++){
			
			if(towerBattles.get(u).twoTowers()){
				
				if(towerBattles.get(u).deathTowers()){
					towerBattles.get(u).similuateHitTowers();
				}else{
					
					towerBattles.remove(u);
				}
			
			}else{
				
				if(towerBattles.get(u).death()){
					towerBattles.get(u).similuateHit();
				}else{
					
					towerBattles.remove(u);
				}
			}
		}
	}
	
	public void simulateHit(){
		
		for(int u = 0; u < battles.size(); u++){
			
			if(battles.get(u).death()){
						
				units.stopAttack(battles.get(u).getOneID());
				units.stopAttack(battles.get(u).getTwoID());
				removeBattle(u);

				continue;
			}
		
			if(units.collision(battles.get(u).getOneID(),
					battles.get(u).getTwoID())){
				
				battles.get(u).similuateHit();
				
				if(!units.isAttacking(battles.get(u).getOneID())){
					
					units.attack(battles.get(u).getOneID(),battles.get(u).getUnitPosTwo()[0],
							battles.get(u).getUnitPosTwo()[1]);
				
				}else if(units.getUnitMoving(battles.get(u).getOneID())){
					
					units.stopAttack(battles.get(u).getOneID());
				}
				
				if(!units.isAttacking(battles.get(u).getTwoID())){
					
					units.attack(battles.get(u).getTwoID(),battles.get(u).getUnitPosOne()[0],
							battles.get(u).getUnitPosOne()[1]);
					
					unitsToFollow.add(new int[]{battles.get(u).getTwoID(),battles.get(u).getOneID()});
				
				}else if(units.getUnitMoving(battles.get(u).getTwoID())){
					
					units.stopAttack(battles.get(u).getTwoID());
				}
				
				
			
			}else{
				
				units.stopAttack(battles.get(u).getOneID());
				units.stopAttack(battles.get(u).getTwoID());
			}
		}
	}
	
	public int[] getUnitsToFollow(){
		
		if(unitsToFollow.size() == 0){
			
			return null;
		}
		
		int[] dqueue = unitsToFollow.get(0);
		
		unitsToFollow.remove(0);
		
		return dqueue;
	}
	
	public boolean unitDead(int id){
		
		return battles.get(id).death();
	}
	
	public void removeBattle(int battleId){
		
		/*for(int i = 0; i < battles.size(); i++){
			
			if(battles.get(i).thisId(battleId)){
				
				battles.remove(i);
				break;
			}
		}*/
		
		battles.remove(battleId);
	}

}
