package GameGraphics.GameScreenComposition;

import GameGraphics.Building;

public interface IComBuildingListFrameProcess extends IComBuildingList {

	public void clear();
	public void addUnitToBuildingQueue(int building,String unit);
	public Building getBuildingByBuildingNo(int buildingNo);
	public void clearSelectedBuildingQueue(int buildingNo);
	public void clearAllQueues();
}
