package GameGraphics.GameScreenComposition;

import Map.CollisionMap;

public interface IComBuildingListDisplay extends IComBuildingList {

	
	public void drawGhostBuilding();
	public void removeGhostBuilding();
	public boolean inFrame(int buildingNo,int frameX,int frameY,int FRAME_X_SIZE,int FRAME_Y_SIZE);
}
