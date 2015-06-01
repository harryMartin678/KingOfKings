package GameServer;

import java.util.ArrayList;

public interface Commands {
	
	public void moveUnit(int unitNo, int targetX, int targetY);
	public void unitInBoat(int unitNo, int boatNo);
	public void setWayPoints(int unitNo, ArrayList<int[]> positions);
	public void attackUnit(int unitNo, int targetNo);
	public void attackBuilding(int unitNo, int buildingNo);
	
	public void buildBuilding(int x, int y, int buildingType);
	public void addUnitToBuildingQueue(int buildingNo, int unitType);
	public void removeUnitFromBuildingQueue(int buildingNo, int queueNo);
	public void attackUnitFromTower(int towerNo, int unitNo);
	
	public void requestedUnitInformation(int unitNo);
	public void requestedBuildingInformation(int buildingNo);
	public void getMapInformation(int mapNo);
	
	public void updateFramePos(int playerNo, int frameX, int frameY);

}
