package GameGraphics.GameScreenComposition;

public interface IComUnitListMouseKeyboard extends IComUnitList {

	public int getBaseSelectedUnit();
//	selectedUnits.clear();
//	
//	for(int u = 0; u < units.size(); u++){
//		
//
//		if(units.get(u).getX() <= Math.max(startDB[0], lastDB[0])
//				&& units.get(u).getX() >= Math.min(startDB[0],lastDB[0])
//				&&units.get(u).getY() <= Math.max(startDB[1], lastDB[1])
//    					&& units.get(u).getY() >= Math.min(startDB[1],lastDB[1])
//    						&& units.get(u).getPlayer() == this.myPlayerNumber){
//			
//			selectedUnits.add(units.get(u).getUnitNo());
//			
//		}
//	}
	public void addSelectedUnits(int[] startDB,int[] lastDB);
	//method in monolith
	public void addSelectedUnit(int[] click);
	public int getSelectedUnitsSize();
	public boolean areWayPointSetting();
	public void clearSelectedUnits();
	//selectedUnits.get(1);
	public int getFollowUnit();
	//selectedUnits.get(0);
	public int getFollowingUnit();
//	((int) units.get(u).getX()) == click[0] 
//			  && ((int) units.get(u).getY()) == click[1]
//					  && units.get(u).getPlayer() != this.myPlayerNumber
	public boolean canAttackUnit(int click[],int unitNo);
	//selectedUnits.remove(0);
	public void removeBaseUnit();
	public void addWayPoints(int[] point);
//private String getWayPoints(){
//		
//		String points = "";
//		  
//		for(int w = 0; w < wayPoints.size(); w++){
//			  
//			points += wayPoints.get(w)[0] + " " + wayPoints.get(w)[1] + " "
//					+ wayPoints.get(w)[2] + " ";
//		}
//		  
//		return points;
//	}
	public String getWayPoints();
	public void setWayPointSetting(boolean flag);
	//wayPoints.clear();
	public void clearWayPoints();
	
//private String getUnitsSelectedString(){
//		
//		String units = "";
//		
//		for(int u = 0; u < selectedUnits.size(); u++){
//		
//			units += selectedUnits.get(u) + " ";
//		}
//		
//		
//		return units;
//	}
	public String getUnitsSelectedString();
	public boolean isWorkerSelected();
	public void clearNonAttackSelectedUnits();
}
