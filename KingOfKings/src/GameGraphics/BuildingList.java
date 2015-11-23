package GameGraphics;

import java.util.ArrayList;

import GameGraphics.GameScreenComposition.IComBuildingListDisplay;
import GameGraphics.GameScreenComposition.IComBuildingListFrameProcess;
import GameGraphics.GameScreenComposition.IComBuildingListMouseKeyboard;
import Map.CollisionMap;

public class BuildingList implements IComBuildingListDisplay, IComBuildingListMouseKeyboard, 
IComBuildingListFrameProcess {
	
	private ArrayList<Building> buildings;
	private boolean used;
	private Building ghostBuilding;
	
	public BuildingList(){
		
		buildings = new ArrayList<Building>();
		used = false;
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
	
	public void clear(){
		
		buildings.clear();
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

}
