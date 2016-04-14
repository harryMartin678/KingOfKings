package Units;

import java.util.ArrayList;

import Buildings.BuildingList;
import Buildings.Tower;
import Buildings.TowerBattle;
import Map.CollisionMap;
import Map.MapList;

public class UnitBattleList {
	
	private ArrayList<Battle> battles;
	private ArrayList<TowerBattle> towerBattles;
	private ArrayList<int[]> unitsToFollow;
	private ArrayList<int[]> pastUnitsToFollow;
	private UnitList units;
	
	public UnitBattleList(UnitList units){
		
		battles = new ArrayList<Battle>();
		towerBattles = new ArrayList<TowerBattle>();
		unitsToFollow = new ArrayList<int[]>();
		pastUnitsToFollow = new ArrayList<int[]>();
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
		
		two.setAttacking(one.getUnitNo());
		towerBattles.add(new TowerBattle(one,two));
	}
	
	public void addTowerTowerBattle(Tower one, Tower two){
		
		towerBattles.add(new TowerBattle(one,two));
	}
	
	public void simulateTowerHit(){
		
		//System.out.println(towerBattles.size() + " UnitBattleList");
		
		for(int u = 0; u < towerBattles.size(); u++){
			
//			if(towerBattles.get(u).twoTowers()){
//				
//				if(towerBattles.get(u).deathTowers()){
//					towerBattles.get(u).similuateHitTowers();
//				}else{
//					
//					towerBattles.remove(u);
//				}
//			
//			}else{
			//things to do:
			//stop towers attacking when they are out of range
			//towers can only attack one unit at a time
			if(!towerBattles.get(u).death()){
				
				if(towerBattles.get(u).InRange()){
					
					//System.out.println("Attacking UnitBattleList");
					towerBattles.get(u).attack();
					
				}else{
					//System.out.println("Stop Attacking UnitBattleList");
					units.stopAttack(towerBattles.get(u).getUnitNo());
				}
				
				towerBattles.get(u).similuateHit();
				
			}else{
				
				towerBattles.get(u).stopAttacking();
				towerBattles.remove(u);
				u--;
			}
			//}
		}
	}
	
	public void simulateHit(BuildingList buildings, MapList maps){
		
		for(int u = 0; u < battles.size(); u++){
			
			boolean simulateHit = false;
			
			if(battles.get(u).death()){
						
				units.begin();
				units.stopAttack(battles.get(u).getOneID());
				units.stopAttack(battles.get(u).getTwoID());
				units.stopRetreat(battles.get(u).getOneID());
				units.stopRetreat(battles.get(u).getTwoID());
				units.unfollow(battles.get(u).getOneID());
				units.unfollow(battles.get(u).getTwoID());
				removeBattle(u);
				u--;
				units.end();
				
				continue;
			}
		
			if(units.collision(battles.get(u).getOneID(),
					battles.get(u).getTwoID(),
					units.getUnits(battles.get(u).getOneID()).getRange())){
				simulateHit = true;
				
				if(!units.isAttacking(battles.get(u).getOneID())){
					
					units.attack(battles.get(u).getOneID(),battles.get(u).getUnitPosTwo()[0],
							battles.get(u).getUnitPosTwo()[1]);
					
					if(!onUnitsToFollow(battles.get(u).getOneID(), battles.get(u).getTwoID())){
						//System.out.println("Add To unit Follow: " + battles.get(u).getTwoID() + " "
							//	+ battles.get(u).getOneID() + " in unitbattleslist");
						unitsToFollow.add(new int[]{battles.get(u).getTwoID(),battles.get(u).getOneID()});
					}
				
				}else if(units.getUnitMoving(battles.get(u).getOneID())){
					
					units.stopAttack(battles.get(u).getOneID(),true);
				}
				
			}else{
				
				units.stopAttack(battles.get(u).getOneID(),true);
				
			}
			
			if(units.collision(battles.get(u).getOneID(),
					battles.get(u).getTwoID(),
					units.getUnits(battles.get(u).getTwoID()).getRange())){
				
				simulateHit = true;
				
				if(!units.isAttacking(battles.get(u).getTwoID())){
					
					units.attack(battles.get(u).getTwoID(),battles.get(u).getUnitPosOne()[0],
							battles.get(u).getUnitPosOne()[1]);
					
					
				
				}else if(units.getUnitMoving(battles.get(u).getTwoID())){
					
					units.stopAttack(battles.get(u).getTwoID(),true);
				}
	
			}else{
				
				units.stopAttack(battles.get(u).getTwoID(),true);
			}
			
			if(simulateHit){
				battles.get(u).similuateHit(new CollisionMap(buildings,units,
						maps.getMap(units.getUnitMap(battles.get(u).getOneID())),
								units.getUnitMap(battles.get(u).getOneID())));
			}
		}
	}
	
	private boolean onUnitsToFollow(int one, int two){
		
		for(int u = 0; u < pastUnitsToFollow.size(); u++){
			
			if(pastUnitsToFollow.get(u)[0] == two && 
					pastUnitsToFollow.get(u)[1] == one){
				
				return true;
			}
		}
		
		return false;
		
	}
	
	public int[] getUnitsToFollow(){
		
		if(unitsToFollow.size() == 0){
			
			return null;
		}
		
		int[] dqueue = unitsToFollow.get(0);
		pastUnitsToFollow.add(dqueue);
		
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
