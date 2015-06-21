package Units;

import java.util.ArrayList;

import Buildings.Tower;
import Buildings.TowerBattle;

public class UnitBattleList {
	
	private ArrayList<Battle> battles;
	private ArrayList<TowerBattle> towerBattles;
	
	public UnitBattleList(){
		
		battles = new ArrayList<Battle>();
		towerBattles = new ArrayList<TowerBattle>();
	}
	
	public void addBattle(Unit one, Unit two, int id){
		
		battles.add(new Battle(one,two,id));
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
			
			if(!battles.get(u).death()){
				
				battles.get(u).similuateHit();
			
			}else{
			
					removeBattle(u);
			}
		}
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
