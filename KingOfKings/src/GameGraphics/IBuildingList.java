package GameGraphics;

import GameGraphics.GameScreenComposition.IComBuildingList;

public interface IBuildingList {
	
	public int getBuildingsSize();
	public boolean inBuilding(int x, int y, int unitNo);

}
