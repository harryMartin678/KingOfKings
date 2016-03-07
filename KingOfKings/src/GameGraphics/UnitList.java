package GameGraphics;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import Buildings.Names;
import GameGraphics.GameScreenComposition.IComUnitListDisplay;
import GameGraphics.GameScreenComposition.IComUnitListFrameProcess;
import GameGraphics.GameScreenComposition.IComUnitListMouseKeyboard;

public class UnitList implements IComUnitListDisplay,IComUnitListMouseKeyboard,IComUnitListFrameProcess {
	
	private ArrayList<Unit> units;
	private boolean used;
	private ArrayList<int[]> wayPoints;
	private ArrayList<SelectedUnit> selectedUnits;
	private int myPlayerNumber;
	private boolean wayPointSetting;
	private Semaphore lock;
	
	//private Thread currentThread;
	
	public UnitList(int myPlayerNumber){
		
		units = new ArrayList<Unit>();
		used = false;
		
		wayPoints = new ArrayList<int[]>();
		selectedUnits = new ArrayList<SelectedUnit>();
		this.myPlayerNumber = myPlayerNumber;
		lock = new Semaphore(1);
	}
	
	public class SelectedUnit{
		
		public int unitNo;
		public boolean isWorker;
	}
	
	public void begin(){
		
		//boolean alreadyHaveUnits = Thread.currentThread() == currentThread;
//		while(used){ //&& !alreadyHaveUnits){
//			
//			try {
//				Thread.sleep(2);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		//currentThread = Thread.currentThread();
//		
//		used = true;
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Unit get(int index){
		
		return units.get(index);
	}
	
	public void clear(){
		
		units.clear();
	}
	
	public void remove(int index){
		
		units.remove(index);
	}
	
	public void removeByUnitNo(int unitNo){

		for(int r = 0; r < units.size(); r++){
			
			//System.out.println("remove " + unitNo + " " + units.get(r).getUnitNo() + " " + r);
			if(unitNo == units.get(r).getUnitNo()){
				
				units.remove(r);
				break;
			}
		}
	}
	
	public Unit getUnitByUnitNo(int unitNo){
		
		for(int a = 0; a < units.size(); a++){
			
			if(unitNo == units.get(a).getUnitNo()){
				
				return units.get(a);
			}
		}
		
		return null;
	}
	
	public int size(){
		
		return units.size();
	}
	
	public void add(Unit unit){
		
		units.add(unit);
	}
	
	public void end(){
		
		//used = false;
		//currentThread = null;
		lock.release();
	}

	@Override
	public int getUnitListSize() {
		// TODO Auto-generated method stub
		return size();
	}

	@Override
	public boolean checkInUnit(int x, int y,int unitNo) {
		// TODO Auto-generated method stub
		//System.out.println(units + " UnitList");
		//System.out.println(units.get(unitNo) + " UnitList");
		
//		if(units.get(unitNo) == null){
//			
//			System.out.println("units.get(unitNos) is null unitlist graphics");
//		}
		
//		try {
//			Thread.sleep(2);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		//hack to solve a thread currency issue
		if(units.get(unitNo) != null){
			return units.get(unitNo).inUnit(x,y);
		}
		return true;
	}

	@Override
	public int getNumberOfWayPoints() {
		// TODO Auto-generated method stub
		return wayPoints.size();
	}

	@Override
	public int[] getWayPoint(int index) {
		// TODO Auto-generated method stub
		return wayPoints.get(index);
	}

	@Override
	public boolean inFrame(int unitNo, int frameX, int frameY,
			int FRAME_X_SIZE, int FRAME_Y_SIZE) {
		// TODO Auto-generated method stub
		return !(units.get(unitNo).getX() >= frameX 
		&& units.get(unitNo).getX() < (frameX + FRAME_X_SIZE)
		&& units.get(unitNo).getY() >= frameY 
		&& units.get(unitNo).getY() < (frameY + FRAME_Y_SIZE));
		
	}

	@Override
	public boolean isUnitSelected(int unitNo) {
		// TODO Auto-generated method stub
		
		for(int u = 0; u < selectedUnits.size(); u++){
			
			if(selectedUnits.get(u).unitNo == unitNo){
				
				return true;
			}
		}
		
		return false;
		
	}

	@Override
	public boolean workSelected() {
		// TODO Auto-generated method stub
	
//		if(selectedUnits == null || selectedUnits.size() == 0){
//			
//			return false;
//		}
//		
//		Unit unit = getUnitByUnitNo(selectedUnits.get(0));
//		
//		if(unit == null){
//			
//			return false;
//		}
//		
//		return  (unit.getUnitType().equals(Names.SLAVE)
//					 	|| unit.getUnitType().equals(Names.SERVANT));
		
		return this.isWorkerSelected();
	}

	@Override
	public int getBaseSelectedUnit() {
		// TODO Auto-generated method stub
		return selectedUnits.get(0).unitNo;
	}

	@Override
	public void addSelectedUnits(int[] startDB, int[] lastDB) {
		// TODO Auto-generated method stub
		selectedUnits.clear();
		
		for(int u = 0; u < units.size(); u++){
			
	
			if(units.get(u).getX() <= Math.max(startDB[0], lastDB[0])
					&& units.get(u).getX() >= Math.min(startDB[0],lastDB[0])
					&&units.get(u).getY() <= Math.max(startDB[1], lastDB[1])
	    					&& units.get(u).getY() >= Math.min(startDB[1],lastDB[1])
	    						&& units.get(u).getPlayer() == this.myPlayerNumber){
				
				SelectedUnit su = new SelectedUnit();
				su.unitNo = units.get(u).getUnitNo();
				su.isWorker = units.get(u).getUnitType().equals(Names.SLAVE)
						|| units.get(u).getUnitType().equals(Names.SERVANT);
				
				selectedUnits.add(su);
				
			}
		}
	}

	@Override
	public void addSelectedUnit(int[] click) {
		// TODO Auto-generated method stub
		for(int u = 0; u < units.size(); u++){
			  
			  if(((int) units.get(u).getX()) == click[0] 
					  && ((int) units.get(u).getY()) == click[1]
							  && units.get(u).getPlayer() == this.myPlayerNumber){
			  
				  SelectedUnit su = new SelectedUnit();
					su.unitNo = units.get(u).getUnitNo();
					su.isWorker = units.get(u).getUnitType().equals(Names.SLAVE)
							|| units.get(u).getUnitType().equals(Names.SERVANT);
				  selectedUnits.add(su);
			  	}
		  }
	}

	@Override
	public int getSelectedUnitsSize() {
		// TODO Auto-generated method stub
		return selectedUnits.size();
	}

	@Override
	public boolean areWayPointSetting() {
		// TODO Auto-generated method stub
		return wayPointSetting;
	}

	@Override
	public void clearSelectedUnits() {
		// TODO Auto-generated method stub
		selectedUnits.clear();
	}

	@Override
	public int getFollowUnit() {
		// TODO Auto-generated method stub
		return selectedUnits.get(1).unitNo;
	}

	@Override
	public int getFollowingUnit() {
		// TODO Auto-generated method stub
		return selectedUnits.get(0).unitNo;
	}

	@Override
	public boolean canAttackUnit(int[] click, int unitNo) {
		// TODO Auto-generated method stub
		return ((int) units.get(unitNo).getX()) == click[0] 
		  && ((int) units.get(unitNo).getY()) == click[1]
				  && units.get(unitNo).getPlayer() != this.myPlayerNumber;
		
	}

	@Override
	public void removeBaseUnit() {
		// TODO Auto-generated method stub
		selectedUnits.remove(0);
	}

	@Override
	public void addWayPoints(int[] point) {
		// TODO Auto-generated method stub
		wayPoints.add(point);
	}

	@Override
	public String getWayPoints() {
		// TODO Auto-generated method stub
		String points = "";
		  
		for(int w = 0; w < wayPoints.size(); w++){
			  
			points += wayPoints.get(w)[0] + " " + wayPoints.get(w)[1] + " "
					+ wayPoints.get(w)[2] + " ";
		}
		  
		return points;
	}

	@Override
	public void setWayPointSetting(boolean flag) {
		// TODO Auto-generated method stub
		wayPointSetting = flag;
	}

	@Override
	public void clearWayPoints() {
		// TODO Auto-generated method stub
		wayPoints.clear();
	}

	@Override
	public String getUnitsSelectedString() {
		// TODO Auto-generated method stub
		String units = "";
		
		for(int u = 0; u < selectedUnits.size(); u++){
		
			units += selectedUnits.get(u).unitNo + " ";
		}
		
		
		return units;
	}
	
	//units.get(i).changeCurrentFrame();
	public void changeCurrentFrame(int unitNo){
		
		units.get(unitNo).changeCurrentFrame();
	}

	@Override
	public int getSelectedUnitSize() {
		// TODO Auto-generated method stub
		return selectedUnits.size();
	}

	@Override
	public boolean isWorkerSelected() {
		// TODO Auto-generated method stub
		
		for(int w = 0; w < selectedUnits.size(); w++){
			
			if(selectedUnits.get(w).isWorker){
				
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void clearNonAttackSelectedUnits() {
		// TODO Auto-generated method stub
		for(int u = 0; u < selectedUnits.size(); u++){
			
			if(selectedUnits.get(u).isWorker){
				
				///System.out.println("Remove: " + selectedUnits.get(u).unitNo);
				selectedUnits.remove(u);
			}
		}
		
		//System.out.println(selectedUnits.size() + " unit size in unit list");
	}

	@Override
	public float getUnitX(int unitNo) {
		// TODO Auto-generated method stub
		return units.get(unitNo).getX();
	}

	@Override
	public float getUnitY(int unitNo) {
		// TODO Auto-generated method stub
		return units.get(unitNo).getY();
	}

	@Override
	public int getUnitMap(int unitNo) {
		// TODO Auto-generated method stub
		return -1;
	}


}
