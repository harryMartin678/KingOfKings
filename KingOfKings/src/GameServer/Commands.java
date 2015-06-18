package GameServer;

import java.util.ArrayList;

public interface Commands {
	
	public void moveUnit(int unitNo, int targetX, int targetY, int targetMap);
	public void unitInBoat(int unitNo, int boatNo);
	public void setWayPoints(int unitNo, int[] targetX, int[] targetY, int[] targetMap);
	public void attackUnit(int unitNo, int targetNo);
	public void attackBuilding(int unitNo, int buildingNo);
	
	public void buildBuilding(int x, int y, int buildingType);
	public void destroyBuilding(int buildingNo);
	public void addUnitToBuildingQueue(int buildingNo, int unitType);
	public void removeUnitFromBuildingQueue(int buildingNo, int queueNo);
	public void attackUnitFromTower(int towerNo, int unitNo);
	
	public void requestedUnitInformation(int unitNo);
	public void requestedBuildingInformation(int buildingNo);
	public void getMapInformation(int mapNo);
	
	public void updateGraphicalFramePos(int playerNo, int frameX, int frameY);
	public void newViewMap(int mapNo);
	public void saveGame();
	public void addMessageToChat(String message);
	
}
