package Units;

import java.util.ArrayList;
import java.util.HashMap;

import Buildings.BuildingList;
import Buildings.Tower;
import Buildings.TowerBattle;
import Map.CollisionMap;
import Map.GameEngineCollisionMap;
import Map.MapList;

public class UnitBattleList {
	
	private ArrayList<Battle> battles;
	private ArrayList<TowerBattle> towerBattles;
	//private ArrayList<int[]> unitsToFollow;
	//private ArrayList<int[]> pastUnitsToFollow;
	private UnitList units;
	private HashMap<Integer,Integer> inBattle; 
	
	public UnitBattleList(UnitList units){
		
		battles = new ArrayList<Battle>();
		towerBattles = new ArrayList<TowerBattle>();
		inBattle = new HashMap<Integer, Integer>();
	//	unitsToFollow = new ArrayList<int[]>();
		//pastUnitsToFollow = new ArrayList<int[]>();
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
		
		for(int u = 0; u < towerBattles.size(); u++){
			
			if(!towerBattles.get(u).death()){
				
				if(towerBattles.get(u).InUnitRange()){
					
					towerBattles.get(u).unitAttack();
					
				}else{

					units.stopAttack(towerBattles.get(u).getUnitNo());
				}
				
				if(towerBattles.get(u).TowerInRange()){
					
					towerBattles.get(u).towerAttackIfNotAttacking();
					
				}else{
					
					towerBattles.get(u).stopAttacking();
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
	
//	private boolean checkDead(Battle battle){
//		
//		if(battle.death()){
//			
//			units.stopAttack(battle.getOneID());
//			units.stopAttack(battle.getTwoID());
//			units.stopRetreat(battle.getOneID());
//			units.stopRetreat(battle.getTwoID());
//			units.unfollow(battle.getOneID());
//			units.unfollow(battle.getTwoID());
//			///removeUnitToFollow(battle.getTwoID(),battle.getOneID());
//			//removeUnitToFollow(battle.getOneID(),battle.getTwoID());
//			//unitsToFollow.remove(UniqueNo(battle.getTwoID(),battle.getOneID()));
//			
//			units.end();
//			
//			return true;
//		}
//		
//		return false;
//	}
	
	public void simulateHit(){
		
		for(int u = 0; u < battles.size(); u++){
			
			battles.get(u).simulateHit();
			
			if(battles.get(u).death()){
				
				units.stopAttack(battles.get(u).getOneID());
				units.stopAttack(battles.get(u).getTwoID());
				units.stopRetreat(battles.get(u).getOneID());
				units.stopRetreat(battles.get(u).getTwoID());
				units.unfollow(battles.get(u).getOneID());
				units.unfollow(battles.get(u).getTwoID());				
//				System.out.println(battles.get(u).getOneID() + " " +
//				units.getUnitDead(battles.get(u).getOneID()) + " " + battles.get(u).getTwoID() + " " +
//				units.getUnitDead(battles.get(u).getTwoID()) + " UnitBattleList");
				registerBattleEnd(battles.get(u).getOneID());
				registerBattleEnd(battles.get(u).getTwoID());
				//inBattle.remove(battles.get(u).getOneID());
				
//				System.out.println("UnitBattleList //////// Start Battle: " 
//				+ battles.get(u).getOneID() + " " + battles.get(u).getTwoID());
//				for(int i = 0; i < inBattle.size(); i++){
//					
//					System.out.println(inBattle.keySet().toArray()[i] + " " + 
//							inBattle.get(inBattle.keySet().toArray()[i]));
//				}
//				System.out.println("UnitBattleList //////// End Battle");
				
				battles.remove(u);
				u--;
			}
		}
	}
	
	private void registerBattleEnd(int id){
		
		if(inBattle.get(id) == 1){
			
			inBattle.remove(id);
		
		}else{
			
			inBattle.replace(id, inBattle.get(id)-1);
		}
	}
	
	public ArrayList<int[]> createBattles(){
		
		ArrayList<int[]> battles = new ArrayList<int[]>();
		
//		System.out.println("START IN BATTLE UNITBATTLELIST////////");
//		for(int b = 0; b < inBattle.size(); b++){
//			
//			System.out.println(inBattle.keySet().toArray()[b]);
//		}
//		System.out.println("END IN BATTLE UNITBATTLELIST////////");
//		
		//System.out.println(inBattle.size() + " start UNITBATTLELIST");
		
		for(int u = 0; u < units.getUnitListSize(); u++){
			
			if(units.getUnitIsIdle(u) && !units.isWorker(u) 
					&& !inBattle.containsKey(u)){
				
				//System.out.println(u + " " + inBattle.containsKey(u) + " " + 
				//inBattle.size() + " UnitBattleList");
				int enemyNo = findEnemies(u);
				
				if(enemyNo != -1){
					
					//System.out.println(u + " " + enemyNo + " addBattle UnitBattleList");
					registerBattleStart(u);
					registerBattleStart(enemyNo);
//					registerBattleStartOld(u);
//					registerBattleStartOld(enemyNo);
					
					battles.add(new int[]{u,enemyNo});
				}
			}
		}
		
		return battles;
	}
	
	private void registerBattleStartOld(int id){
		
		if(!inBattle.containsKey(id)){
			
			inBattle.put(id, 1);
		}
	}
	
	private void registerBattleStart(int id){
		
		if(!inBattle.containsKey(id)){
			
			inBattle.put(id, 1);
		
		}else{
			
			inBattle.replace(id, inBattle.get(id)+1);
		}
	}
	
	private int findEnemies(int unitNo){
		
		return GameEngineCollisionMap.FindEnemy(unitNo,units);
	}


//	public void simulateHit(BuildingList buildings, MapList maps){
//		
//		units.begin();
//		for(int u = 0; u < battles.size(); u++){
//			
//			boolean simulateHit = false;
//			
//			if(checkDead(battles.get(u))){
//				
//				System.out.println("DEAD UnitBattleList");
//				removeBattle(u);
//				u--;
//				continue;
//			}
//			
//		
//			if(units.collision(battles.get(u).getOneID(),
//					battles.get(u).getTwoID(),
//					units.getUnits(battles.get(u).getOneID()).getRange())){
//				simulateHit = true;
//				
//				if(!units.isAttacking(battles.get(u).getOneID())){
//					
//					units.attack(battles.get(u).getOneID(),battles.get(u).getUnitPosTwo()[0],
//							battles.get(u).getUnitPosTwo()[1]);
//					
//					if(!onUnitsToFollow(battles.get(u).getOneID(), battles.get(u).getTwoID())){
//						//System.out.println("Add To unit Follow: " + battles.get(u).getTwoID() + " "
//							//	+ battles.get(u).getOneID() + " in unitbattleslist");
//						unitsToFollow.add(new int[]{battles.get(u).getTwoID(),battles.get(u).getOneID()});
//					}
//				
//				}else if(units.getUnitMoving(battles.get(u).getOneID())){
//					
//					units.stopAttack(battles.get(u).getOneID(),true);
//				}
//				
//			}else{
//				
//				units.stopAttack(battles.get(u).getOneID(),true);
//				
//			}
//			
//			if(units.collision(battles.get(u).getOneID(),
//					battles.get(u).getTwoID(),
//					units.getUnits(battles.get(u).getTwoID()).getRange())){
//				
//				simulateHit = true;
//				
//				if(!units.isAttacking(battles.get(u).getTwoID())){
//					
//					units.attack(battles.get(u).getTwoID(),battles.get(u).getUnitPosOne()[0],
//							battles.get(u).getUnitPosOne()[1]);
//					
//					
//				
//				}else if(units.getUnitMoving(battles.get(u).getTwoID())){
//					
//					units.stopAttack(battles.get(u).getTwoID(),true);
//				}
//	
//			}else{
//				
//				units.stopAttack(battles.get(u).getTwoID(),true);
//			}
//			
//			if(simulateHit){
//				battles.get(u).similuateHit();
//			}
//			
//			if(checkDead(battles.get(u))){
//				
//				System.out.println("DEAD UnitBattleList");
//				removeBattle(u);
//				u--;
//				continue;
//			}
//			
//			units.end();
//		}
//	}
	
//	private Integer UniqueNo(int a, int b){
//		
//		return (a + b) * (a + b + 1) / 2 + a;
//	}
	
//	private void removeUnitToFollow(int one,int two) {
//		// TODO Auto-generated method stub
//		for(int u = 0; u < pastUnitsToFollow.size(); u++){
//			
//			if(pastUnitsToFollow.get(u)[0] == two && 
//					pastUnitsToFollow.get(u)[1] == one){
//				
//				pastUnitsToFollow.remove(u);
//			}
//		}
//	}
//	
//	private boolean onUnitsToFollow(int one, int two){
//		
//		for(int u = 0; u < pastUnitsToFollow.size(); u++){
//			
//			if(pastUnitsToFollow.get(u)[0] == two && 
//					pastUnitsToFollow.get(u)[1] == one){
//				
//				return true;
//			}
//		}
//		
//		return false;
//		
//	}
	
//	public int[] getUnitsToFollow(){
//		
//		if(unitsToFollow.size() == 0){
//			
//			return null;
//		}
//		
//		int[] dqueue = unitsToFollow.get(0);
//		//pastUnitsToFollow.add(dqueue);
//		
//		unitsToFollow.remove(0);
//		
//		return dqueue;
//	}
	
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

	public String getBattleStates() {
		// TODO Auto-generated method stub
		
		String line = "";
		for(int bt = 0; bt < battles.size(); bt++){
			
			line += battles.get(bt).getOneID() + " " + battles.get(bt).getTwoID() +
					" " + battles.get(bt).getCount() + "\n";
		}
		
		return line;
	}

	public void addAll(ArrayList<Battle> battles) {
		// TODO Auto-generated method stub
		this.battles.addAll(battles);
	}

}
