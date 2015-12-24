package GameGraphics;

import java.util.ArrayList;

import Buildings.UnitCreator;
import GameClient.ClientMessages;
import GameGraphics.GameScreenComposition.IComBuildingListDisplay;
import GameGraphics.GameScreenComposition.IComBuildingListFrameProcess;
import GameGraphics.GameScreenComposition.IComBuildingListMouseKeyboard;
import Map.CollisionMap;

public class BuildingList implements IComBuildingListDisplay, IComBuildingListMouseKeyboard, 
IComBuildingListFrameProcess {
	
	private ArrayList<Building> buildings;
	private boolean used;
	private Building ghostBuilding;
	private Building SelectedBuilding;
	private ClientMessages cmsgs;
	
	public BuildingList(){
		
		buildings = new ArrayList<Building>();
		used = false;
	}
	
	public void setClientMessager(ClientMessages cmsgs){
		
		this.cmsgs = cmsgs;
	}
	
	public void begin(){
		
		while(used){
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		used = true;
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
		
		System.out.println("add selected unit " + building + " " + unit);
		Building toAdd = this.getBuildingByBuildingNo(building);
		toAdd.addUnitQueue(unit);
		
		if(SelectedBuilding.getBuildingNo() == building){
			
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
		
		used = false;
	}

	@Override
	public int getBuildingsSize() {
		// TODO Auto-generated method stub
		return size();
	}

	@Override
	public boolean inBuilding(int x, int y, int unitNo) {
		// TODO Auto-generated method stub
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
		return !ghostBuilding.cantBuild();
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
	
	public boolean setSelectedBuilding(int clickX, int clickY, int playerNumber){
		
		for(int b = 0; b < buildings.size(); b++){
			
			Buildings.Building type = Building.GetBuildingClass(buildings.get(b).getName());
			
			if(clickX <= buildings.get(b).getX() + type.getSizeX()
			   && clickX >= buildings.get(b).getX() - type.getSizeX()
			   && clickY <= buildings.get(b).getY() + type.getSizeY()
			   && clickY >= buildings.get(b).getY() - type.getSizeY()
			   && buildings.get(b).getPlayer() == playerNumber){
				
				SelectedBuilding = buildings.get(b);
				return true;
			}
		}
		
		return false;
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
	public void unitIconSelected(int selected) {
		// TODO Auto-generated method stub
		UnitCreator type = (UnitCreator) Building.GetBuildingClass(SelectedBuilding.getName());
		String[] listOfUnits = type.unitcreated().split(":");
		
		if(selected < listOfUnits.length){
			
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

}
