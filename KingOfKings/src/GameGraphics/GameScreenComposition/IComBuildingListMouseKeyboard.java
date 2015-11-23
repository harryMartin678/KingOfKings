package GameGraphics.GameScreenComposition;

import GameGraphics.Building;

public interface IComBuildingListMouseKeyboard extends IComBuildingList {

	public boolean canBuildGhostBuilding();
	public void removeGhostBuilding();
	public int getGhostBuildingX();
	public int getGhostBuildingY();
	public String getGhostBuildingName();
//	if(buildingSelection != null && square[0] != -1 && square[1] != -1){
//		
//		buildingSelection.setXY(square[0], square[1]);
//	}
	public void moveGhostBuilding(int[] square);
	public void setGhostBuilding(Building building);
}
