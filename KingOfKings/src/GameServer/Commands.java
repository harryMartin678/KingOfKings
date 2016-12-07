package GameServer;

import java.util.ArrayList;

import Util.Point;

public interface Commands {
	
	public void moveUnit(int unitNo, int targetX, int targetY, int targetMap,boolean follow);
	public void setWayPoints(int unitNo, int[] targetX, int[] targetY, int[] targetMap);
	public void groupMovement(int[] unitNo, int targetX, int targetY, int targetMap);
	public void groupWayPointsMovement(int[] unitNo,int[] targetX, int[] targetY,int[] targetMap);
	public void groupFollow(int[] unitNo,int unitFollow);
	public void attackGroup(int[] unitNo,int targetUnit);
	public void followUnit(int unitNo, int unitFollow);
	public void attackUnit(int unitNo, int targetNo);
	public void attackBuilding(int[] unitNos, int buildingNo);
	public void towerAttackUnit(int unitNo, int buildingNo);
	
	public void buildBuilding(int x, int y,int player,int map, String buildingType,int unitNo[]);
	public void buildWalls(Point center,int size, int[] workers,int mapNo,int playerNumber);
	public void addWorkerToSite(int buildingNo, int[] unitNo);
	public void destroyBuilding(int buildingNo);
	public void addUnitToBuildingQueue(int buildingNo, String unitType,int player);
	public void removeUnitFromBuildingQueue(int buildingNo, int queueNo);
	public void attackUnitFromTower(int towerNo, int unitNo);
	
	public void requestedUnitInformation(int unitNo);
	public void requestedBuildingInformation(int buildingNo);
	public void getMapInformation(int mapNo);
	
	public void updateGraphicalFramePos(int playerNo, int frameX, int frameY);
	public void newViewMap(int mapNo,int player);
	public void saveGame(String filename,int playerNo,int noOfPlayers);
	public void addMessageToChat(String message);
	
	public void alliance(int player1, int player2);
	public void atWar(int player1, int player2);
	
	public void addAI(int AINum, String AIName,long Seed);
	
}
