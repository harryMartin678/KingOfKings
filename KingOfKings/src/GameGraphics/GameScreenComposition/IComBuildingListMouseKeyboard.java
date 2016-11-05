package GameGraphics.GameScreenComposition;

import java.util.ArrayList;

import GameGraphics.Building;
import GameGraphics.IBoundingBoxes;

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
	public int setSelectedBuilding(int buildingNo, int playerNumber);
	public void clearSelectedBuilding();
	public boolean isBuildingSelected();
	public void unitIconSelected(int selected,int food, int gold);
	public int getAttackBuildingNo();
	public int getBuildBuildingBuildingNo();
	public boolean selectedBuildingIsTower();
	public int getSelectedBuildingNo();
	public String getUnitType(int index);
	public boolean isUnitCreatorSelected();
	public int getSelectedBuilding(double x, double y, int width,
			int height, IBoundingBoxes boxes, int frameX,int frameY);
	public boolean isBuildingGhostWall();
	public void createWallTowers(int thisPlayer,float x, float y);
	public void increaseWallTowerRadius(int size,float x, float y);
	public void clearGhostWalls();
	public int getWallSize();
	public float[] getGhostWallCenter();
}
