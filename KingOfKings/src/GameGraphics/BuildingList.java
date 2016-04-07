package GameGraphics;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import Buildings.UnitCreator;
import GameClient.ClientMessages;
import GameGraphics.GameScreenComposition.ClientWrapper;
import GameGraphics.GameScreenComposition.IComBuildingListDisplay;
import GameGraphics.GameScreenComposition.IComBuildingListFrameProcess;
import GameGraphics.GameScreenComposition.IComBuildingListMouseKeyboard;
import Map.CollisionMap;

public class BuildingList implements IComBuildingListDisplay, IComBuildingListMouseKeyboard, 
IComBuildingListFrameProcess {
	
	private ArrayList<Building> buildings;
	//private boolean used;
	private Building ghostBuilding;
	private Building SelectedBuilding;
	private Building AttackBuilding;
	private Building BuildBuilding;
	private ClientWrapper cmsgs;
	private Semaphore lock;
	
	public BuildingList(){
		
		buildings = new ArrayList<Building>();
		//used = false;
		lock = new Semaphore(3);
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
		
		buildings.add(building);
	}
	
	public synchronized void remove(int index){
		
		buildings.remove(index);
	}
	
	public synchronized int size(){
		
		return buildings.size();
	}
	
	public synchronized Building get(int index){
		
		return buildings.get(index);
	}
	
	public synchronized void addUnitToBuildingQueue(int building,String unit){
		
		//System.out.println("add selected unit " + building + " " + unit);
		Building toAdd = this.getBuildingByBuildingNo(building);
		toAdd.addUnitQueue(unit);
		
		if(SelectedBuilding != null && SelectedBuilding.getBuildingNo() == building){
			
			SelectedBuilding.addUnitQueue(unit);
		}
	}
	
	public synchronized void clear(){
		
		buildings.clear();
	}
	
	public synchronized void clearSelectedBuildingQueue(int buildingNo){
		
		if(SelectedBuilding != null && SelectedBuilding.getBuildingNo() == buildingNo){
			SelectedBuilding.clearUnitQueue();
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
	public synchronized boolean canBuildGhost(CollisionMap map) {
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
		return !(buildings.get(buildingNo).getX() >= frameX 
			&& buildings.get(buildingNo).getX() < (frameX + FRAME_X_SIZE)
			&& buildings.get(buildingNo).getY() >= frameY 
			&& buildings.get(buildingNo).getY() < (frameY + FRAME_Y_SIZE));
	}

	@Override
	public synchronized boolean canBuildGhostBuilding() {
		// TODO Auto-generated method stub
		return !ghostBuilding.cantBuild();
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
	
	public synchronized int setSelectedBuilding(int clickX, int clickY, int playerNumber){
		
		//this.begin();
		for(int b = 0; b < buildings.size(); b++){
			
			Buildings.Building type = Building.GetBuildingClass(buildings.get(b).getName());
			
			if(clickX < buildings.get(b).getX() + type.getSizeX()
			   && clickX > buildings.get(b).getX() - type.getSizeX()
			   && clickY < buildings.get(b).getY() + type.getSizeY()
			   && clickY > buildings.get(b).getY() - type.getSizeY()
			   ){
				
				
				if(buildings.get(b).getPlayer() == playerNumber){
					
					if(buildings.get(b).isSite()){
						
						BuildBuilding = buildings.get(b);
						this.end();
						return 3;
						
					}else{
						SelectedBuilding = buildings.get(b);
						this.end();
						return 2;
					}
					
					
				}else{
					AttackBuilding = buildings.get(b);
					this.end();
					return 1;
					
				}
				
			}
		}
		//this.end();
		return 0;
	}

	@Override
	public synchronized boolean isSelectedBuilding(Building building) {
		// TODO Auto-generated method stub
		return SelectedBuilding != null && SelectedBuilding.getBuildingNo() == building.getBuildingNo();
	}

	@Override
	public synchronized void clearSelectedBuilding() {
		// TODO Auto-generated method stub
		SelectedBuilding = null;
	}

	@Override
	public synchronized Building getSelectedBuilding() {
		// TODO Auto-generated method stub
		return SelectedBuilding;
	}

	@Override
	public synchronized boolean isBuildingSelected() {
		// TODO Auto-generated method stub
		return SelectedBuilding != null;
	}

	//handle the selection of units to be added to the queue
	@Override
	public synchronized void unitIconSelected(int selected,int food,int gold) {
		// TODO Auto-generated method stub
		UnitCreator type = (UnitCreator) Building.GetBuildingClass(SelectedBuilding.getName());
		String[] listOfUnits = type.unitcreated().split(";");
		
		Units.Unit unitDes = Units.Unit.GetUnit(listOfUnits[selected]);
		
		if(selected < listOfUnits.length && unitDes.goldNeeded() <= gold && unitDes.foodNeeded() <= food){
			
			cmsgs.addMessage("auq " + SelectedBuilding.getBuildingNo() + " " + listOfUnits[selected]);
		}
		
		
	}

	@Override
	public synchronized GameGraphics.Building getBuildingByBuildingNo(int buildingNo) {
		// TODO Auto-generated method stub
		
		for(int b = 0; b < buildings.size(); b++){
			
			if(buildings.get(b).getBuildingNo() == buildingNo){
				
				return buildings.get(b);
			}
		}
		
		return null;
	}

	@Override
	public synchronized int getGhostBuildingSelected() {
		// TODO Auto-generated method stub
		return SelectedBuilding.getBuildingNo();
	}

	@Override
	public synchronized int getAttackBuildingNo() {
		// TODO Auto-generated method stub
		return AttackBuilding.getBuildingNo();
	}

	@Override
	public synchronized void canBuildThere(CollisionMap collMap) {
		// TODO Auto-generated method stub
		if(isBuildingGhost()){
			ghostBuilding.CanBuildThere(collMap);
		}else{
			
			System.out.println("NULL GHOSTBUILDING buildingList");
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
		
		if(SelectedBuilding != null){
			
			SelectedBuilding.clearUnitQueue();
		}
	}

	@Override
	public int getBuildBuildingBuildingNo() {
		// TODO Auto-generated method stub
		return BuildBuilding.getBuildingNo();
	}

	@Override
	public void setBuildings(ArrayList<Building> buildingsTemp) {
		// TODO Auto-generated method stub
		buildings = buildingsTemp;
	}



}
