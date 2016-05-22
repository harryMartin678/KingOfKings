package GameGraphics.GameScreenComposition;

import GameGraphics.Building;

public interface IComBuildingListMouseKeyboard extends IComBuildingList {

	public boolean canBuildGhostBuilding();
	public void removeGhostBuilding();
	public int getGhostBuildingX();
	public int getGhostBuildingY();
	public int getGhostBuildingSelected();
	public String getGhostBuildingName();
//	if(buildingSelection != null && square[0] != -1 && square[1] != -1){
//		
//		buildingSelection.setXY(square[0], square[1]);
//	}
	public void moveGhostBuilding(int[] square);
	public void setGhostBuilding(Building building);
	public void endGhostBuildingSession();
	public int setSelectedBuilding(int clickX, int clickY, int playerNumber);
	public void clearSelectedBuilding();
	public boolean isBuildingSelected();
	public void unitIconSelected(int selected,int food, int gold);
	public int getAttackBuildingNo();
	public int getBuildBuildingBuildingNo();
	public boolean selectedBuildingIsTower();
	public int getSelectedBuildingNo();
	public String getUnitType(int index);
	public boolean isUnitCreatorSelected();
}
