package GameGraphics.GameScreenComposition;

import java.util.ArrayList;

import GameGraphics.Unit;

public interface IComUnitListFrameProcess extends IComUnitList {
	
	public void clear();
	public void removeByUnitNo(int unitNo);
	public void add(Unit unit);
	public void setUnits(ArrayList<Unit> units,boolean isMapRefresh);
	
}
