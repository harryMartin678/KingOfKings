package GameGraphics.GameScreenComposition;

import Buildings.Names;
import GameGraphics.Unit;

public interface IComUnitListDisplay extends IComUnitList {

	public int getNumberOfWayPoints();
	public int[] getWayPoint(int index);
	
//	!(units.get(u).getX() >= frameX 
//			&& units.get(u).getX() < (frameX + FRAME_X_SIZE)
//			&& units.get(u).getY() >= frameY 
//			&& units.get(u).getY() < (frameY + FRAME_Y_SIZE)
	public boolean inFrame(int unitNo,int frameX,int frameY,int FRAME_X_SIZE,int FRAME_Y_SIZE);
	public boolean isUnitSelected(int unitNo);
//	selectedUnits.size() == 1 && units.getUnitByUnitNo(selectedUnits.get(0)).getUnitType() != null 
//			&& (units.getUnitByUnitNo(selectedUnits.get(0)).getUnitType().equals(Names.SLAVE)
//			 	|| units.getUnitByUnitNo(selectedUnits.get(0)).getUnitType().equals(Names.SERVANT))
	public boolean workSelected();
	public int getSelectedUnitSize();
	public Unit getUnitByUnitNo(int unitNo);
}
