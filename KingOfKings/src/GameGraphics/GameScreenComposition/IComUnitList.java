package GameGraphics.GameScreenComposition;

import GameGraphics.IUnitList;
import GameGraphics.Unit;

public interface IComUnitList extends IUnitList {

	
	public int size();
	public void begin();
	public void end();
	public Unit get(int unitNo);
	public void remove(int unitNo);
	public Unit getUnitByUnitNo(int unitNo);
	public int getUnitPlayerByUnitNo(int unitNo);
	
	
	
}
