package GameGraphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import Buildings.Names;
import GameGraphics.GameScreenComposition.Display;
import GameGraphics.GameScreenComposition.IComUnitListDisplay;
import GameGraphics.GameScreenComposition.IComUnitListFrameProcess;
import GameGraphics.GameScreenComposition.IComUnitListMouseKeyboard;
import Map.GraphicsCollisionMap;
import Util.Vector3;

public class UnitList implements IComUnitListDisplay,IComUnitListMouseKeyboard,
IComUnitListFrameProcess {
	
	private ArrayList<Unit> units;
	//private boolean used;
	private ArrayList<int[]> wayPoints;
	private ArrayList<SelectedUnit> selectedUnits;
	private int myPlayerNumber;
	private boolean wayPointSetting;
	private Semaphore lock;
	private HashMap<Integer,Integer> unitNoToIndex;
	
	//private Thread currentThread;
	
	public UnitList(){
		
		units = new ArrayList<Unit>();
		//used = false;
		
		wayPoints = new ArrayList<int[]>();
		selectedUnits = new ArrayList<SelectedUnit>();
		unitNoToIndex = new HashMap<Integer, Integer>();
		lock = new Semaphore(1);
	}
	
	public void setMyPlayerNumber(int myPlayerNumber){
	
		this.myPlayerNumber = myPlayerNumber;
	}
	
	public class SelectedUnit{
		
		public int unitNo;
		public boolean isWorker;
		public double distance;
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
			GraphicsCollisionMap.Begin();
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
	
		GraphicsCollisionMap.removeUnit(units.get(index).getUnitNo());
		units.remove(index);
		
	}
	
	public int getUnitPlayerByUnitNo(int unitNo){

		return units.get(unitNoToIndex.get(unitNo)).getPlayer();
	}
	
	public void removeByUnitNo(int unitNo){

//		for(int r = 0; r < units.size(); r++){
//			
//			//System.out.println("remove " + unitNo + " " + units.get(r).getUnitNo() + " " + r);
//			if(unitNo == units.get(r).getUnitNo()){
//				
//				units.remove(r);
//				GraphicsCollisionMap.removeUnit(unitNo);
//				break;
//			}
//		}
		//System.out.println(units.get(unitNoToIndex.get(unitNo)).getUnitNo() + " " + unitNo  + " UnitList");
		System.out.println("Remove units UnitList");
		units.remove((int)unitNoToIndex.get(unitNo));
		unitNoToIndex.remove(unitNo);
		GraphicsCollisionMap.removeUnit(unitNo);
		//System.out.println(units.size() + " UnitList After");
		
	}
	
	public Unit getUnitByUnitNo(int unitNo){
		
//		for(int a = 0; a < units.size(); a++){
//			
//			if(unitNo == units.get(a).getUnitNo()){
//				
//				return units.get(a);
//			}
//		}
//		
//		return null;
		
//		if(!unitNoToIndex.containsKey(unitNo)){
//			
//			System.out.println("Start UnitList ///////" + unitNo);
//			
//			for(int u = 0; u < unitNoToIndex.size(); u++){
//				
//				System.out.println(unitNoToIndex.keySet().toArray()[u]);
//			}
//			
//			System.out.println("End UnitList ///////");
//		}
		
		if(!unitNoToIndex.containsKey(unitNo)){
			
			return null;
		}
		return units.get(unitNoToIndex.get(unitNo));
		
//		}else{
//			
//			return null;
//		}
	}
	
	public int size(){
		
		return units.size();
	}
	
	public void add(Unit unit){
		
		GraphicsCollisionMap.addUnit(Math.round(unit.getX()), Math.round(unit.getY()), unit.getUnitNo(),
				unit.getUnitType().equals(Names.WORKER));
		units.add(unit);
		unitNoToIndex.put(unit.getUnitNo(),units.size()-1);
	}
	
	public void end(){
		
		//used = false;
		//currentThread = null;
		GraphicsCollisionMap.End();
		lock.release();
	}

	@Override
	public int getUnitListSize() {
		// TODO Auto-generated method stub
		return size();
	}
	
//	public void createVisiblity(int playerNumber){
//		
//		for(int u = 0; u < units.size(); u++){
//			
//			if(units.get(u).getPlayer() == playerNumber){
//				
//				GraphicsCollisionMap.addVisiblilty(Math.round(units.get(u).getX()),
//						Math.round(units.get(u).getY()));
//			}
//		}
//	}

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
	public boolean outOfFrame(int unitNo, int frameX, int frameY,
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
				su.isWorker = units.get(u).getUnitType().equals(Names.WORKER);
				
				selectedUnits.add(su);
				
			}
		}
	}

	@Override
	public void addSelectedUnit(int unit) {
		// TODO Auto-generated method stub
		
		if(unit > -1){
			int unitNo = 0;
			
			for(int u = 0; u < units.size(); u++){
				
				if(units.get(u).getUnitNo() == unit){
					
					unitNo = u;
				}
			}
			
			SelectedUnit su = new SelectedUnit();
			su.unitNo = unit;
			su.isWorker = units.get(unitNo).getUnitType().equals(Names.WORKER);
			
			selectedUnits.clear();
			selectedUnits.add(su);
		}
		
//		MousePicking picking = new MousePicking(new float[]{(float)click[0],(float)click[1]}
//					,ScreenWidth, ScreenHeight);
//		
//		for(int u = 0; u < units.size(); u++){
//			
//			if(picking.IsSelected(boxes.GetBoundingBox(units.get(u).getUnitType(),
//					units.get(u).getCurrentFrame()))){
//				
//				SelectedUnit su = new SelectedUnit();
//				su.unitNo = units.get(u).getUnitNo();
//				su.isWorker = units.get(u).getUnitType().equals(Names.WORKER);
//				selectedUnits.clear();
//				selectedUnits.add(su);
//			}
//		}
//		for(int u = 0; u < units.size(); u++){
//			  
//			  if(ClickInUnit(click,units.get(u)) && units.get(u).getPlayer() == this.myPlayerNumber){
//				
//				  
//				  SelectedUnit su = new SelectedUnit();
//					su.unitNo = units.get(u).getUnitNo();
//					su.isWorker = units.get(u).getUnitType().equals(Names.WORKER);
//					su.distance = Math.abs(units.get(u).getX() - click[0]) 
//							+ Math.abs(units.get(u).getY() - click[1]) + ((double)click[2]/1000.0);
//				  selectedUnits.add(su);
//			  	}
//		  }
//		
//		if(selectedUnits.size() > 0){
//			int selected = 0;
//			for(int s = 1; s < selectedUnits.size(); s++){
//				
//				if(selectedUnits.get(selected).distance > selectedUnits.get(s).distance){
//					
//					selected = s;
//				}
//			}
//		
//	
//			SelectedUnit select = selectedUnits.get(selected);
//			selectedUnits.clear();
//			selectedUnits.add(select);
//		}
	}
	
	private boolean ClickInUnit(int[] click, Unit unit){
		
		double SelectionSize = 1.75;
		
		return unit.getX() < click[0] + SelectionSize
				&& unit.getX() > click[0] - SelectionSize
				&& unit.getY() < click[1] + SelectionSize
				&& unit.getY() > click[1] - SelectionSize;
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

//	@Override
//	public boolean canAttackUnit(int[] click, int unitNo) {
//		// TODO Auto-generated method stub
//		return ((int) units.get(unitNo).getX()) == click[0] 
//		  && ((int) units.get(unitNo).getY()) == click[1]
//				  && units.get(unitNo).getPlayer() != this.myPlayerNumber;
//		
//	}

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
//	public void changeCurrentFrame(int unitNo){
//		
//		units.get(unitNo).changeCurrentFrame();
//	}

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
				u--;
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

	@Override
	public void clearAttackSelectedUnits() {
		// TODO Auto-generated method stub
		for(int u = 0; u < selectedUnits.size(); u++){
			
			if(!selectedUnits.get(u).isWorker){
				
				selectedUnits.remove(u);
				u--;
			}
		}
	}

	@Override
	public int getUnitAtttack(double x, double y, int ScreenWidth, int ScreenHeight,
			IBoundingBoxes boxes,int frameX,int frameY) {
		// TODO Auto-generated method stub
		
		return getSelectedUnit(x, y, ScreenWidth, ScreenHeight, boxes, frameX, frameY);
//		ArrayList<SelectedUnit> unitsToAttack = new ArrayList<SelectedUnit>();
//		int selected = 0;
//		for(int u = 0; u < units.size(); u++){
//			
//			if(ClickInUnit(click, units.get(u)) && units.get(u).getPlayer() != this.myPlayerNumber){
//				
//				SelectedUnit su = new SelectedUnit();
//				su.unitNo = units.get(u).getUnitNo();
//				su.isWorker = units.get(u).getUnitType().equals(Names.WORKER);
//				su.distance = Math.abs(units.get(u).getX() - click[0]) 
//						+ Math.abs(units.get(u).getY() - click[1]) + ((double)click[2]/1000.0);
//				unitsToAttack.add(su);
//			}
//		}
//		if(unitsToAttack.size() > 0){
//			
//			for(int s = 1; s < unitsToAttack.size(); s++){
//				
//				if(unitsToAttack.get(selected).distance > unitsToAttack.get(s).distance){
//					
//					selected = s;
//				}
//			}
//		}
//		
//		return unitsToAttack.get(selected).unitNo;
	}

	@Override
	public int getUnitPlayer(int unitNo) {
		// TODO Auto-generated method stub
		return units.get(unitNo).getPlayer();
	}

	@Override
	public void setUnits(ArrayList<Unit> units,boolean isMapRefresh) {
		// TODO Auto-generated method stub
		HashMap<Integer,Unit.GraphicalState> graphicalstates = new HashMap<Integer, Unit.GraphicalState>();
		
		if(isMapRefresh){
			
			this.clear();
			
		}else{
			int unitRemove = 0;
			while(this.units.size() > unitRemove){
				
				if(this.get(unitRemove).hasDied() && !this.get(unitRemove).getDieingFinished()){
					
					unitRemove++;
					
				}
				
				graphicalstates.put(this.units.get(unitRemove).getUnitNo(),
						this.units.get(unitRemove).getGraphicalState());
				this.remove(unitRemove);
			}
		}
			
		//this.units.clear();
		
		for(int n = 0; n < units.size(); n++){
			
			this.add(units.get(n));
			
			if(graphicalstates.containsKey(this.units.get(this.units.size()-1).getUnitNo())){
				this.units.get(this.units.size()-1).setGraphicalState(
						graphicalstates.get(this.units.get(this.units.size()-1).getUnitNo()));
			}else{
				this.units.get(this.units.size()-1).updateState();
			}
		}
		
	}


	@Override
	public int getSelectedUnit(double x, double y, int ScreenWidth, int ScreenHeight,
			IBoundingBoxes boxes,int frameX,int frameY) {
		// TODO Auto-generated method stub
		
		x = x * ScreenWidth;
		y = y * ScreenHeight;
		MousePicker picking = new MousePicker();
		Vector3 ray = picking.CalculateMouseRay((int)x, (int)y, ScreenWidth, ScreenHeight);

		for(int u = 0; u < units.size(); u++){
		
			if(picking.IsSelected(boxes.GetBoundingBox(units.get(u).getUnitType(),
					units.get(u).getCurrentFrame()),ray,units.get(u).getX() - Display.WIDTH_CONST - frameX,
					units.get(u).getY() - Display.HEIGHT_CONST - frameY)){
				
				return units.get(u).getUnitNo();
				
			}
		
		}
		return -1;
	}

	@Override
	public boolean canAttackUnit(int[] click, int unitNo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void killByUnitNo(int unitNo) {
		// TODO Auto-generated method stub
		units.get(unitNoToIndex.get(unitNo)).die();
	}


}
