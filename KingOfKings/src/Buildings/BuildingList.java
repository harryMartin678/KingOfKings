package Buildings;

import java.util.ArrayList;

import GameGraphics.IBuildingList;

public class BuildingList implements IBuildingList,IComBuildingBATTList {
	
	private ArrayList<Building> buildings;
	
	public BuildingList(){
		
		buildings = new ArrayList<Building>();
		
	}
	
	public int getBuildingsSize(){
		
		return buildings.size();
	}
	
	public float getBuildingX(int buildingNo){
		
		return buildings.get(buildingNo).getX();
	}
	
	public float getBuildingY(int buildingNo){
		
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
	
	public Building getBuilding(int index){
		
		return buildings.get(index);
	}
	
	public int getUnitQueueSize(int buildingNo){
		
		return buildings.get(buildingNo).getUnitQueueSize();
	}
	
	public boolean nextUnit(int buildingNo){
		
		boolean ret = buildings.get(buildingNo).progressUnitQueue();
		//System.out.println(ret + " nextUnit building list");
		return ret;
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
	
	public void addUnitToBuildingQueue(int buildingNo, String unitType){
		
		buildings.get(buildingNo).addToUnitQueue(unitType);
	}
	
	public String getBuildingQueue(int buildingNo){
		
		return buildings.get(buildingNo).getUnitQueue();
	}
	
	public void addBuilding(int player, int map, int x, int y, String type){
		
		int buildingNo = 0;
		
		if(buildings.size() != 0){
			
			buildingNo = buildings.get(buildings.size()-1).getBuildingNo()+1;
		}
		
		Building building = GameGraphics.Building.GetBuildingClass(type);
		buildings.add(building);
		buildings.get(buildings.size()-1).setMap(map);
		buildings.get(buildings.size()-1).setPos(x, y);
		buildings.get(buildings.size()-1).setPlayer(player);
		buildings.get(buildings.size()-1).SetBuildingNo(buildingNo);
		
		
	}
	
	public void addBuilding(Building building){
		
		//System.out.println("addBuilding");
		buildings.add(building);
	}

	public int getBuildingQueueSize(int index) {
		// TODO Auto-generated method stub
		return buildings.get(index).getUnitQueueSize();
	}
	
	public boolean isBuildingDestroyed(int index){
		
		return buildings.get(index).destroyed();
	}

	@Override
	public void changeBuildingsPlayer(int unitPlayer, int map) {
		// TODO Auto-generated method stub
		for(int b = 0; b < buildings.size(); b++){
			
			if(buildings.get(b).getMap() == map){
				
				buildings.get(b).setPlayer(unitPlayer);
			}
		}
	}

}
