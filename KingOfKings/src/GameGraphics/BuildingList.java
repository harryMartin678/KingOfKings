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
	
	public void add(Building building){
		
		buildings.add(building);
	}
	
	public void remove(int index){
		
		buildings.remove(index);
	}
	
	public int size(){
		
		return buildings.size();
	}
	
	public Building get(int index){
		
		return buildings.get(index);
	}
	
	public void addUnitToBuildingQueue(int building,String unit){
		
		//System.out.println("add selected unit " + building + " " + unit);
		Building toAdd = this.getBuildingByBuildingNo(building);
		toAdd.addUnitQueue(unit);
		
		if(SelectedBuilding != null && SelectedBuilding.getBuildingNo() == building){
			
			SelectedBuilding.addUnitQueue(unit);
		}
	}
	
	public void clear(){
		
		buildings.clear();
	}
	
	public void clearSelectedBuildingQueue(int buildingNo){
		
		if(SelectedBuilding != null && SelectedBuilding.getBuildingNo() == buildingNo){
			SelectedBuilding.clearUnitQueue();
		}
	}
	
	public void end(){
		
		//used = false;
		lock.release();
	}

	@Override
	public int getBuildingsSize() {
		// TODO Auto-generated method stub
		return size();
	}

	@Override
	public boolean inBuilding(int x, int y, int unitNo) {
		// TODO Auto-generated method stub
		if(buildings.get(unitNo) == null){
			
			return false;
			
		}
		return buildings.get(unitNo).inBuilding(x,y);
	}

	@Override
	public boolean isBuildingGhost() {
		// TODO Auto-generated method stub
		return (ghostBuilding != null);
	}

	@Override
	public boolean canBuildGhost(CollisionMap map) {
		// TODO Auto-generated method stub
		return (ghostBuilding != null && !ghostBuilding.cantBuild());
	}

	@Override
	public void drawGhostBuilding() {
		// TODO Auto-generated method stub
		if(isBuildingGhost()){
			buildings.add(ghostBuilding);
		}
	}

	@Override
	public void removeGhostBuilding() {
		// TODO Auto-generated method stub
		if(isBuildingGhost()){
			buildings.remove(ghostBuilding);
		}
		
	}

	@Override
	public boolean inFrame(int buildingNo, int frameX, int frameY,
			int FRAME_X_SIZE, int FRAME_Y_SIZE) {
		// TODO Auto-generated method stub
		return !(buildings.get(buildingNo).getX() >= frameX 
			&& buildings.get(buildingNo).getX() < (frameX + FRAME_X_SIZE)
			&& buildings.get(buildingNo).getY() >= frameY 
			&& buildings.get(buildingNo).getY() < (frameY + FRAME_Y_SIZE));
	}

	@Override
	public boolean canBuildGhostBuilding() {
		// TODO Auto-generated method stub
		return !ghostBuilding.cantBuild();
	}

	@Override
	public int getGhostBuildingX() {
		// TODO Auto-generated method stub
		return (int) ghostBuilding.getX();
	}

	@Override
	public int getGhostBuildingY() {
		// TODO Auto-generated method stub
		return (int) ghostBuilding.getY();
	}

	@Override
	public String getGhostBuildingName() {
		// TODO Auto-generated method stub
		return ghostBuilding.getName();
	}

	@Override
	public void moveGhostBuilding(int[] square) {
		// TODO Auto-generated method stub
		if(ghostBuilding != null && square[0] != -1 && square[1] != -1){
		
			ghostBuilding.setXY(square[0], square[1]);
		}
	}

	@Override
	public void setGhostBuilding(Building building) {
		// TODO Auto-generated method stub
		ghostBuilding = building;
	}

	@Override
	public void endGhostBuildingSession() {
		// TODO Auto-generated method stub
		ghostBuilding = null;
	}
	
	public int setSelectedBuilding(int clickX, int clickY, int playerNumber){
		
		//this.begin();
		for(int b = 0; b < buildings.size(); b++){
			
			Buildings.Building type = Building.GetBuildingClass(buildings.get(b).getName());
			
			if(clickX < buildings.get(b).getX() + type.getSizeX()
			   && clickX > buildings.get(b).getX() - type.getSizeX()
			   && clickY < buildings.get(b).getY() + type.getSizeY()
			   && clickY > buildings.get(b).getY() - type.getSizeY()
			   ){
				
				
				if(buildings.get(b).getPlayer() == playerNumber){
					SelectedBuilding = buildings.get(b);
					this.end();
					return 2;
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
	public boolean isSelectedBuilding(Building building) {
		// TODO Auto-generated method stub
		return SelectedBuilding != null && SelectedBuilding.getBuildingNo() == building.getBuildingNo();
	}

	@Override
	public void clearSelectedBuilding() {
		// TODO Auto-generated method stub
		SelectedBuilding = null;
	}

	@Override
	public Building getSelectedBuilding() {
		// TODO Auto-generated method stub
		return SelectedBuilding;
	}

	@Override
	public boolean isBuildingSelected() {
		// TODO Auto-generated method stub
		return SelectedBuilding != null;
	}

	//handle the selection of units to be added to the queue
	@Override
	public void unitIconSelected(int selected,int food,int gold) {
		// TODO Auto-generated method stub
		UnitCreator type = (UnitCreator) Building.GetBuildingClass(SelectedBuilding.getName());
		String[] listOfUnits = type.unitcreated().split(":");
		
		Units.Unit unitDes = Units.Unit.GetUnit(listOfUnits[selected]);
		
		if(selected < listOfUnits.length && unitDes.goldNeeded() <= gold && unitDes.foodNeeded() <= food){
			
			cmsgs.addMessage("auq " + SelectedBuilding.getBuildingNo() + " " + listOfUnits[selected]);
		}
		
		
	}

	@Override
	public GameGraphics.Building getBuildingByBuildingNo(int buildingNo) {
		// TODO Auto-generated method stub
		
		for(int b = 0; b < buildings.size(); b++){
			
			if(buildings.get(b).getBuildingNo() == buildingNo){
				
				return buildings.get(b);
			}
		}
		
		return null;
	}

	@Override
	public int getGhostBuildingSelected() {
		// TODO Auto-generated method stub
		return SelectedBuilding.getBuildingNo();
	}

	@Override
	public int getAttackBuildingNo() {
		// TODO Auto-generated method stub
		return AttackBuilding.getBuildingNo();
	}

	@Override
	public void canBuildThere(CollisionMap collMap) {
		// TODO Auto-generated method stub
		if(isBuildingGhost()){
			ghostBuilding.CanBuildThere(collMap);
		}else{
			
			System.out.println("NULL GHOSTBUILDING buildingList");
		}
	}

	@Override
	public int getBuildingMap(int buildingNo) {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public float getBuildingX(int buildingNo) {
		// TODO Auto-generated method stub
		return buildings.get(buildingNo).getX();
	}

	@Override
	public float getBuildingY(int buildingNo) {
		// TODO Auto-generated method stub
		return buildings.get(buildingNo).getY();
	}

	@Override
	public int getBuildingDiameterX(int buildingNo) {
		// TODO Auto-generated method stub
		return Building.GetBuildingClass(buildings.get(buildingNo).getName()).getSizeX();
	}

	@Override
	public int getBuildingDiameterY(int buildingNo) {
		// TODO Auto-generated method stub
		return Building.GetBuildingClass(buildings.get(buildingNo).getName()).getSizeY();
	}

	@Override
	public void clearAllQueues() {
		// TODO Auto-generated method stub
		
		for(int b = 0; b < buildings.size(); b++){
			
			buildings.get(b).clearUnitQueue();
		}
		
		if(SelectedBuilding != null){
			
			SelectedBuilding.clearUnitQueue();
		}
	}



}
