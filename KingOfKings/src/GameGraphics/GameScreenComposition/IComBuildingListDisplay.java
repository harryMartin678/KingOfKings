package GameGraphics.GameScreenComposition;

import GameGraphics.Building;
import Map.CollisionMap;

public interface IComBuildingListDisplay extends IComBuildingList {

	
	public void drawGhostBuilding();
	public void removeGhostBuilding();
	public boolean inFrame(int buildingNo,int frameX,int frameY,int FRAME_X_SIZE,int FRAME_Y_SIZE);
	public boolean isSelectedBuilding(Building building);
	public Building getSelectedBuilding();
	public void canBuildThere(CollisionMap collMap);
	public int getBuildingTarget(int b);
	public Building getGhostBuilding();
	public boolean isUnitCreatorSelected();
	public int getUnitQueueSize();
	public int getBuildingPlayer(int b);
}
