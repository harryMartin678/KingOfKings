package GameGraphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import Buildings.Names;
import Buildings.UnitCreator;
import GameClient.ClientMessages;
import GameGraphics.GameScreenComposition.ClientWrapper;
import GameGraphics.GameScreenComposition.Display;
import GameGraphics.GameScreenComposition.IComBuildingListDisplay;
import GameGraphics.GameScreenComposition.IComBuildingListFrameProcess;
import GameGraphics.GameScreenComposition.IComBuildingListMouseKeyboard;
import GameGraphics.Menu.IComMenuBuildingList;
import Map.CollisionMap;
import Map.GraphicsCollisionMap;
import Util.Vector3;

public class BuildingList implements IComBuildingListDisplay, IComBuildingListMouseKeyboard, 
IComBuildingListFrameProcess {
	
	private ArrayList<Building> buildings;
	//private boolean used;
	private Building ghostBuilding;
	private int SelectedBuilding;
	private int AttackBuilding;
	private int BuildBuilding;
	private ClientWrapper cmsgs;
	private Semaphore lock;
	private IComMouseKeyboardBuildingList mouseKeyboard;
	private HashMap<Integer,Integer> buildingNosIndex;
	
	private ArrayList<Building> ghostWallTowers;
	private int wallSize;
	private float[] wallCenter;
	
	public BuildingList(){
		
		buildings = new ArrayList<Building>();
		SelectedBuilding = -1;
		AttackBuilding = -1;
		BuildBuilding = -1;
		//used = false;
		lock = new Semaphore(1);
		buildingNosIndex = new HashMap<Integer, Integer>();
		ghostWallTowers = new ArrayList<Building>();
	}
	
	public void SetUpBuildingList(IComMouseKeyboardBuildingList mouseKeyboard){
		
		this.mouseKeyboard = mouseKeyboard;
		
	}
	
	public void setClientMessager(ClientWrapper cmsgs){
		
		this.cmsgs = cmsgs;
	}
	
	public void begin(){
		
//		while(used){
//			
//			try {
//				Thread.sleep(2);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
//		used = true;
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void add(Building building){
		
		Buildings.Building SizeInfo = Building.GetBuildingClass(building.getName());

		GraphicsCollisionMap.addBuilding(Math.round(building.getX()), Math.round(building.getY()),
				SizeInfo.getSizeX(),SizeInfo.getSizeY(),building.getBuildingNo());
		buildings.add(building);
		buildingNosIndex.put(building.getBuildingNo(), buildings.size()-1);
	}
	
	public synchronized void remove(int index){
		
		Buildings.Building SizeInfo = Building.GetBuildingClass(buildings.get(index).getName());
		//System.out.println(buildings.get(index).getBuildingNo() + " BuildingList");
		GraphicsCollisionMap.removeBuilding(buildings.get(index).getBuildingNo()
				,SizeInfo.getSizeX(),SizeInfo.getSizeY());
		buildings.remove(index);
		
	}
	
//	public void createVisiblity(int playerNumber){
//		
//		for(int b = 0; b < buildings.size(); b++){
//			
//			if(buildings.get(b).getPlayer() == playerNumber){
//				
//				GraphicsCollisionMap.addVisiblilty(x, y);
//			}
//		}
//	}
	
	public synchronized int size(){
		
		return buildings.size();
	}
	
	public synchronized Building get(int index){
		
		return buildings.get(index);
	}
	
	public synchronized void addUnitToBuildingQueue(int building,String unit){
		
		//System.out.println("add selected unit " + building + " " + unit);
//		Building toAdd = this.getBuildingByBuildingNo(building);
//		toAdd.addUnitQueue(unit);
		
		if(SelectedBuilding != -1 && SelectedBuilding == building){
			
			buildings.get(buildingNosIndex.get(SelectedBuilding)).addUnitQueue(unit);
		}
	}
	
	public synchronized void clear(){
		
		buildings.clear();
	}
	
	public void createWallTowers(int thisPlayer,float x, float y){
		
		wallCenter = new float[]{x,y};
		
		if(ghostWallTowers.size() == 0){
			
			wallSize=0;
			for(int w = 0; w < 4; w++){
				
				this.ghostWallTowers.add(new Building(x,y,
						Names.WALLTOWER, 0, thisPlayer));
			}
			
			setWallSize(1,x,y);
		
		}else{
			
			setWallSize(0, x, y);
		}
		
		
		
	}
	
	private void setWallSize(int delta,float x, float y) {
		// TODO Auto-generated method stub
		
		if(!(wallSize + delta < 0 && wallSize + delta > 150)){
			
			wallSize += delta;
			
			int[] lc = new int[]{1,1,-1,-1,-1,1,1,-1};
			
			for(int w = 0; w < 8; w+=2){
				
				this.ghostWallTowers.get(w/2).setXY(
						x + (lc[w] * wallSize), 
						y + (lc[w+1] * wallSize));
			}
			
		}
		
	}
	
	@Override
	public boolean isWallTowerGhosts() {
		// TODO Auto-generated method stub
		return ghostWallTowers.size() > 0;
	}

	public synchronized void clearSelectedBuildingQueue(int buildingNo){
		
		if(SelectedBuilding != -1 && SelectedBuilding == buildingNo){
			buildings.get(buildingNosIndex.get(SelectedBuilding)).clearUnitQueue();
		}
	}
	
	public void end(){
		
		//used = false;
		lock.release();
		
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public synchronized int getBuildingsSize() {
		// TODO Auto-generated method stub
		return size();
	}

	@Override
	public synchronized boolean inBuilding(int x, int y, int unitNo) {
		// TODO Auto-generated method stub
		if(buildings.get(unitNo) == null){
			
			return false;
			
		}
		return buildings.get(unitNo).inBuilding(x,y);
	}

	@Override
	public synchronized boolean isBuildingGhost() {
		// TODO Auto-generated method stub
		return (ghostBuilding != null);
	}

	@Override
	public synchronized boolean canBuildGhost() {
		// TODO Auto-generated method stub
		return (ghostBuilding != null && !ghostBuilding.cantBuild());
	}

	@Override
	public synchronized void drawGhostBuilding() {
		// TODO Auto-generated method stub
		if(isBuildingGhost()){
			buildings.add(ghostBuilding);
		}
	}

	@Override
	public synchronized void removeGhostBuilding() {
		// TODO Auto-generated method stub
		if(isBuildingGhost()){
			buildings.remove(ghostBuilding);
		}
		
	}

	@Override
	public synchronized boolean inFrame(int buildingNo, int frameX, int frameY,
			int FRAME_X_SIZE, int FRAME_Y_SIZE) {
		// TODO Auto-generated method stub
		Buildings.Building SizeInfo = GameGraphics.Building.GetBuildingClass(
				buildings.get(buildingNo).getName());
		return !(buildings.get(buildingNo).getX() + SizeInfo.getSizeX() >= frameX 
			&& buildings.get(buildingNo).getX() - SizeInfo.getSizeX() < (frameX + FRAME_X_SIZE)
			&& buildings.get(buildingNo).getY() + SizeInfo.getSizeY() >= frameY 
			&& buildings.get(buildingNo).getY() - SizeInfo.getSizeY() < (frameY + FRAME_Y_SIZE));
	}

	@Override
	public synchronized boolean canBuildGhostBuilding() {
		// TODO Auto-generated method stub
		return ghostBuilding != null && !ghostBuilding.cantBuild();
	}

	@Override
	public synchronized int getGhostBuildingX() {
		// TODO Auto-generated method stub
		return (int) ghostBuilding.getX();
	}

	@Override
	public synchronized int getGhostBuildingY() {
		// TODO Auto-generated method stub
		return (int) ghostBuilding.getY();
	}

	@Override
	public synchronized String getGhostBuildingName() {
		// TODO Auto-generated method stub
		return ghostBuilding.getName();
	}

	@Override
	public synchronized void moveGhostBuilding(int[] square) {
		// TODO Auto-generated method stub
		if(ghostBuilding != null && square[0] != -1 && square[1] != -1){
		
			ghostBuilding.setXY(square[0], square[1]);
		}
	}

	@Override
	public synchronized void setGhostBuilding(Building building) {
		// TODO Auto-generated method stub

		ghostBuilding = building;
	}

	@Override
	public synchronized void endGhostBuildingSession() {
		// TODO Auto-generated method stub
		ghostBuilding = null;
	}
	
	public synchronized int setSelectedBuilding(int buildingNo, int playerNumber){
		
		
		if(buildingNo == -1){
			
			return 0;
		}
		
		int index = buildingNosIndex.get(buildingNo);
		
		//this.begin();
//		for(int b = 0; b < buildings.size(); b++){
//			
//			Buildings.Building type = Building.GetBuildingClass(buildings.get(b).getName());
//			
//			if(clickX < buildings.get(b).getX() + type.getSizeX()
//			   && clickX > buildings.get(b).getX() - type.getSizeX()
//			   && clickY < buildings.get(b).getY() + type.getSizeY()
//			   && clickY > buildings.get(b).getY() - type.getSizeY()
//			   ){
				
				
			if(buildings.get(index).getPlayer() == playerNumber){
				
				if(buildings.get(index).isSite()){
					
					BuildBuilding = buildingNo;
					this.end();
					return 3;
					
				}else{
					
					SelectedBuilding = buildingNo;
					this.end();
					return 2;
				}
				
				
			}else{
				
				AttackBuilding = buildingNo;
				this.end();
				return 1;
				
			}
				
//			}
//		}
		//this.end();
		//return 0;
	}

	@Override
	public synchronized boolean isSelectedBuilding(Building building) {
		// TODO Auto-generated method stub
		return SelectedBuilding != -1 && SelectedBuilding == building.getBuildingNo();
	}

	@Override
	public synchronized void clearSelectedBuilding() {
		// TODO Auto-generated method stub
		SelectedBuilding = -1;
	}

	@Override
	public synchronized Building getSelectedBuilding() {
		// TODO Auto-generated method stub
		if(SelectedBuilding == -1){
			
			return null;
		}
		return buildings.get(buildingNosIndex.get(SelectedBuilding));
	}

	@Override
	public synchronized boolean isBuildingSelected() {
		// TODO Auto-generated method stub
		return SelectedBuilding != -1;
	}

	//handle the selection of units to be added to the queue
	@Override
	public synchronized void unitIconSelected(int selected,int food,int gold) {
		// TODO Auto-generated method stub
		UnitCreator type = (UnitCreator) Building.GetBuildingClass(
				buildings.get(buildingNosIndex.get(SelectedBuilding)).getName());
		String[] listOfUnits = type.unitcreated().split(";");
		
		Units.Unit unitDes = Units.Unit.GetUnit(listOfUnits[selected]);
		
		if(selected < listOfUnits.length && unitDes.goldNeeded() <= gold && unitDes.foodNeeded() <= food){
			
			cmsgs.addMessage("auq " + SelectedBuilding + " " + listOfUnits[selected]
					 + " " +  buildings.get(buildingNosIndex.get(SelectedBuilding)).getPlayer());
		}

	}
	
	@Override
	public String getUnitType(int index) {
		// TODO Auto-generated method stub
		UnitCreator type = (UnitCreator) Building.GetBuildingClass(buildings.get(
				buildingNosIndex.get(SelectedBuilding)).getName());
		return type.unitcreated().split(";")[index];
	}

	@Override
	public synchronized GameGraphics.Building getBuildingByBuildingNo(int buildingNo) {
		// TODO Auto-generated method stub
		
//		for(int b = 0; b < buildings.size(); b++){
//			
//			if(buildings.get(b).getBuildingNo() == buildingNo){
//				
//				return buildings.get(b);
//			}
//		}
//		
//		return null;
		
		return buildings.get(buildingNosIndex.get(buildingNo));
	}

	@Override
	public synchronized int getGhostBuildingSelected() {
		// TODO Auto-generated method stub
		return buildings.get(buildingNosIndex.get(SelectedBuilding)).getBuildingNo();
	}

	@Override
	public synchronized int getAttackBuildingNo() {
		// TODO Auto-generated method stub
		return buildings.get(buildingNosIndex.get(AttackBuilding)).getBuildingNo();
	}

	@Override
	public synchronized void canBuildThere(int playerNumber) {
		// TODO Auto-generated method stub
		if(isBuildingGhost()){
			ghostBuilding.CanBuildThere(playerNumber);
		}else{
			
			//System.out.println("NULL GHOSTBUILDING buildingList");
		}
	}

	@Override
	public synchronized int getBuildingMap(int buildingNo) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public synchronized float getBuildingX(int buildingNo) {
		// TODO Auto-generated method stub
		return buildings.get(buildingNo).getX();
	}

	@Override
	public synchronized float getBuildingY(int buildingNo) {
		// TODO Auto-generated method stub
		return buildings.get(buildingNo).getY();
	}

	@Override
	public synchronized int getBuildingDiameterX(int buildingNo) {
		// TODO Auto-generated method stub
		return Building.GetBuildingClass(buildings.get(buildingNo).getName()).getSizeX();
	}

	@Override
	public synchronized int getBuildingDiameterY(int buildingNo) {
		// TODO Auto-generated method stub
		return Building.GetBuildingClass(buildings.get(buildingNo).getName()).getSizeY();
	}

	@Override
	public synchronized void clearAllQueues() {
		// TODO Auto-generated method stub
		
		for(int b = 0; b < buildings.size(); b++){
			
			buildings.get(b).clearUnitQueue();
		}
		
//		if(SelectedBuilding != -1){
//			
//			buildings.get(SelectedBuilding).clearUnitQueue();
//		}
	}

	@Override
	public int getBuildBuildingBuildingNo() {
		// TODO Auto-generated method stub
		return BuildBuilding;
	}

	@Override
	public void setBuildings(ArrayList<Building> buildingsTemp,boolean isMapRefresh) {
		// TODO Auto-generated method stub
		HashMap<Integer,Building.GraphicalState> graphicalstates = new HashMap<Integer,
					Building.GraphicalState>();
		
		//System.out.println(buildingsTemp.size() + " BuildingList");
		
		if(isMapRefresh){
			
			this.clear();
		
		}else{
			
			int buildingRemove = 0;
			
			while(this.buildings.size() > buildingRemove){
				
				if(this.get(buildingRemove).hasCollapsed() && 
						!this.get(buildingRemove).hasCollapaseAnimationFinished()){
					
					buildingRemove++;
				}
				
				graphicalstates.put(this.buildings.get(buildingRemove).getBuildingNo(),
						this.buildings.get(buildingRemove).getGraphicalState());
				this.remove(buildingRemove);
			}
			
			
		}
		
		for(int n = 0; n < buildingsTemp.size(); n++){
			
			this.add(buildingsTemp.get(n));
			
			if(graphicalstates.containsKey(this.buildings.get(this.buildings.size()-1).getBuildingNo())){
				this.buildings.get(this.buildings.size()-1).setGraphicalState(
						graphicalstates.get(this.buildings.get(this.buildings.size()-1).getBuildingNo()));
			}
			
			
		}
		
	}

	@Override
	public int getBuildingTarget(int b) {
		// TODO Auto-generated method stub
		return buildings.get(b).getAttack();
	}

	@Override
	public boolean selectedBuildingIsTower() {
		// TODO Auto-generated method stub
		if(SelectedBuilding == -1){
			
			//System.out.println("Selected building is null BuildingList");
			return false;
		
		}else{
			
			//System.out.println((SelectedBuilding.getName() == Names.ARCHERYTOWER) + " BuildingList");
			return buildings.get(buildingNosIndex.get(SelectedBuilding)).getName().equals(Names.ARCHERYTOWER)
					|| buildings.get(buildingNosIndex.get(SelectedBuilding)).getName().equals(Names.BALLISTICTOWER);
			
		}
	}

	@Override
	public int getSelectedBuildingNo() {
		// TODO Auto-generated method stub
		return SelectedBuilding;
	}

	@Override
	public Building getGhostBuilding() {
		// TODO Auto-generated method stub
		return ghostBuilding;
	}

	@Override
	public boolean isUnitCreatorSelected() {
		// TODO Auto-generated method stub
		return SelectedBuilding != -1 && 
				Building.GetBuildingClass(buildings.get(buildingNosIndex.get(SelectedBuilding)).getName()) 
				instanceof UnitCreator;
	}

	@Override
	public int getUnitQueueSize() {
		// TODO Auto-generated method stub
		
		if(SelectedBuilding == -1 || 
				!(Building.GetBuildingClass(buildings.get(buildingNosIndex.get(SelectedBuilding)).getName())
						instanceof UnitCreator)){
			
			return 0;
		}
		
		UnitCreator type = (UnitCreator)Building.GetBuildingClass(
				buildings.get(buildingNosIndex.get(SelectedBuilding)).getName());
		String unitsPossible = type.unitcreated();
		
		return unitsPossible.split(";").length;
	}

	@Override
	public int getBuildingPlayer(int b) {
		// TODO Auto-generated method stub
		return buildings.get(b).getPlayer();
	}

	@Override
	public void moveGhostBuildingGraphics() {
		// TODO Auto-generated method stub
		mouseKeyboard.moveGhostBuilding();
	}

	@Override
	public int getSelectedBuilding(double x, double y, int width, int height, IBoundingBoxes boxes, int frameX,
			int frameY) {
		// TODO Auto-generated method stub
		
		x = x * width;
		y = y * height;
		
		MousePicker picking = new MousePicker();
		Vector3 ray = picking.CalculateMouseRay((int)x, (int)y, width, height);
		
		for(int b = 0; b < buildings.size(); b++){
			
			if(picking.IsSelected(boxes.GetBoundingBox(buildings.get(b).getName(),
					 0), ray,buildings.get(b).getX() - Display.WIDTH_CONST - frameX,
					buildings.get(b).getY() - Display.HEIGHT_CONST - frameY)){
				
				return buildings.get(b).getBuildingNo();
			}
		}
		
		return -1;
	}

	@Override
	public boolean isBuildingGhostWall() {
		// TODO Auto-generated method stub
		return ghostBuilding != null && ghostBuilding.isWall();
	}

	@Override
	public void collapseByBuildingNo(int buildingNo) {
		// TODO Auto-generated method stub
		buildings.get(buildingNosIndex.get(buildingNo)).collapse();
		
	}

	@Override
	public int getNoOfWallTowers() {
		// TODO Auto-generated method stub
		return ghostWallTowers.size();
	}

	@Override
	public Building getGhostWallTower(int index) {
		// TODO Auto-generated method stub
		return ghostWallTowers.get(index);
	}

	@Override
	public void increaseWallTowerRadius(int size,float x, float y) {
		// TODO Auto-generated method stub
		setWallSize(size,x,y);
	}

	@Override
	public void clearGhostWalls() {
		// TODO Auto-generated method stub
		ghostWallTowers.clear();
	}

	@Override
	public int getWallSize() {
		// TODO Auto-generated method stub
		return wallSize;
	}

	@Override
	public float[] getGhostWallCenter() {
		// TODO Auto-generated method stub
		return wallCenter;
	}


}
