package Buildings;

import java.util.ArrayList;

public class BuildingList {
	
	private ArrayList<Building> buildings;
	
	public BuildingList(){
		
		buildings = new ArrayList<Building>();
		
	}
	
	public void addBuilding(int player, int map, int x, int y, int type){
		
		if(type == 0){
			
			buildings.add(new RoyalPalace());
			buildings.get(buildings.size()-1).setMap(map);
			buildings.get(buildings.size()-1).setPos(x, y);
			buildings.get(buildings.size()-1).setPlayer(player);
			
		}else if(type == 1){
			
			buildings.add(new Stockpile());
			buildings.get(buildings.size()-1).setMap(map);
			buildings.get(buildings.size()-1).setPos(x, y);
			buildings.get(buildings.size()-1).setPlayer(player);
			
		}
		
	}

}
