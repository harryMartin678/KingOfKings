package GameGraphics.GameScreenComposition;

import GameGraphics.Unit;

public interface IComUnitListFrameProcess extends IComUnitList {
	
	public void clear();
	public void removeByUnitNo(int unitNo);
	public void add(Unit unit);
	
}
