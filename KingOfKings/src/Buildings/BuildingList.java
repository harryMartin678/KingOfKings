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
	
	public int getBuildingX(int buildingNo){
		
		return buildings.get(buildingNo).getX();
	}
	
	public int getBuildingY(int buildingNo){
		
		return buildings.get(buildingNo).getY();
	}
	
	public int getBuildingMap(int buildingNo){
		
		return buildings.get(buildingNo).getMap();
	}
	
	public String getBuildingType(int buildingNo){
		
		return buildings.get(buildingNo).getType();
	}
	
	public String[] getUnitQueueItem(int buildingNo, int index){
		
		return buildings.get(buildingNo).getUnitQueueItem(index);
	}
	
	public int getUnitQueueSize(int buildingNo){
		
		return buildings.get(buildingNo).getUnitQueueSize();
	}
	
	public boolean nextUnit(int buildingNo){
		
		return buildings.get(buildingNo).progressUnitQueue();
	}
	
	public boolean empty(int buildingNo){
		
		return buildings.get(buildingNo).emptyUnitQueue();
	}
	
	public String getFinishedUnit(int buildingNo){
		
		return buildings.get(buildingNo).removeUnit();
	}
	
	public int getBuildingDiameterX(int buildingNo){
		
		return buildings.get(buildingNo).getSizeX();
	}
	
	public int getBuildingDiameterY(int buildingNo){
		
		return buildings.get(buildingNo).getSizeX();
	}
	
	public boolean isBuildlingJustBuilt(int buildingNo){
		
		return buildings.get(buildingNo).justBuilt();
	}
	
	public int getBuildingPlayer(int buildingNo){
		
		return buildings.get(buildingNo).getPlayer();
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
