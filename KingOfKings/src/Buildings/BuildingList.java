package Buildings;

import java.util.ArrayList;

public class BuildingList {
	
	private ArrayList<Building> buildings;
	
	public BuildingList(){
		
		buildings = new ArrayList<Building>();
		
	}
	
	public int getBuildingsSize(){
		
		return buildings.size();
	}
	
	public boolean inBuilding(int x, int y, int buildingNo){
		
		return buildings.get(buildingNo).inBuilding(x, y);
	}
	
	public void addBuilding(int player, int map, int x, int y, int type){
		
		int buildingNo = 0;
		
		if(buildings.size() != 0){
			
			buildingNo = buildings.get(buildings.size()-1).getBuildingNo()+1;
		}
		
		if(type == 0){
			
			buildings.add(new RoyalPalace(buildingNo));
			buildings.get(buildings.size()-1).setMap(map);
			buildings.get(buildings.size()-1).setPos(x, y);
			buildings.get(buildings.size()-1).setPlayer(player);
			
		}else if(type == 1){
			
			buildings.add(new Stockpile(buildingNo));
			buildings.get(buildings.size()-1).setMap(map);
			buildings.get(buildings.size()-1).setPos(x, y);
			buildings.get(buildings.size()-1).setPlayer(player);
			
		}
		
	}
	
	public void addBuilding(Building building){
		
		buildings.add(building);
	}

}
