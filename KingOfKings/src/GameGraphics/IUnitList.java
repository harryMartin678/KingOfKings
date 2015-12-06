package GameGraphics;

import GameGraphics.GameScreenComposition.IComUnitList;

//used for collision map
public interface IUnitList{
	
	public int getUnitListSize();
	public boolean checkInUnit(int x, int y, int unitNo);
	public void begin();
	public void end();
	

}
