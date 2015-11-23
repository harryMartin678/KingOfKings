package GameGraphics.GameScreenComposition;

import GameGraphics.Building;
import GameGraphics.IBuildingList;
import Map.CollisionMap;

public interface IComBuildingList extends IBuildingList {

	public void begin();
	public void end();
	public int size();
	public void add(Building building);
	public Building get(int index);
	
	public boolean isBuildingGhost();
	public boolean canBuildGhost(CollisionMap map);
}
