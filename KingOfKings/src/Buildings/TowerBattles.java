package Buildings;

import java.util.ArrayList;

import Units.Unit;

public class TowerBattles {
	
	private ArrayList<TowerBattle> battles;
	
	public TowerBattles(){
		
		battles = new ArrayList<TowerBattle>();
	}
	
	public void SimulateHits(){
		
		for(int b = 0; b < battles.size(); b++){
			
			if(battles.get(b).death()){
				
				battles.remove(b);
				battles.get(b).stopAttacking();
				b--;
				continue;
			}
			
			battles.get(b).similuateHit();
		}
		
	}
	
	public void addBattle(Unit unit, Tower tower){
		
		battles.add(new TowerBattle(unit,tower));
	}
	

}
