package Units;

import java.util.ArrayList;

public class UnitList {
	
	private ArrayList<Unit> units;
	
	public UnitList(){
		
		units = new ArrayList<Unit>();
	}
	
	public void addUnit(int unitType,int map, int x, int y, int player){
		
		if(unitType == 0){
			
			units.add(new Slave());
			units.get(units.size()-1).setPlayer(player);
			units.get(units.size()-1).setPos(x, y);
			units.get(units.size()-1).setMap(map);
			
		}else if(unitType == 1){
			
			units.add(new Servant());
			units.get(units.size()-1).setPlayer(player);
			units.get(units.size()-1).setPos(x, y);
			units.get(units.size()-1).setMap(map);
		
		}
	}

}
