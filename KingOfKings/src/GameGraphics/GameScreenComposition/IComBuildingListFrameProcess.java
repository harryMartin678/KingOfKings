package GameGraphics.GameScreenComposition;

import java.util.ArrayList;

import GameGraphics.Building;

public interface IComBuildingListFrameProcess extends IComBuildingList {

	public void clear();
	public void addUnitToBuildingQueue(int building,String unit);
	public Building getBuildingByBuildingNo(int buildingNo);
	public void clearSelectedBuildingQueue(int buildingNo);
	public void clearAllQueues();
	public void setBuildings(ArrayList<Building> buildingsTemp,boolean isMapRefresh);
	public void collapseByBuildingNo(int buildingNo);
}
