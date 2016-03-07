package GameGraphics;

import GameGraphics.GameScreenComposition.IComBuildingList;

public interface IBuildingList {
	
	public int getBuildingsSize();
	public boolean inBuilding(int x, int y, int unitNo);
	public int getBuildingMap(int buildingNo);
	public float getBuildingX(int buildingNo);
	public float getBuildingY(int buildingNo);

}
