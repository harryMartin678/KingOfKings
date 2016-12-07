package Buildings;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

import Map.CollisionMap;
import Units.Worker;
import Util.Point;

public class BuildingSite {
	
	private Building building;
	private ArrayList<Worker> creators;
	private int progress;
	private HashMap<Integer,int[]> takenSpaces;
	
	//for new game
	public BuildingSite(Building building, ArrayList<Worker> creators){
		
		sharedConstructor(building,creators,0);
	}
	
	//for load game
	public BuildingSite(Building building,int progress, ArrayList<Worker> creators){
		
		sharedConstructor(building, creators, progress);
	}
	
	private void sharedConstructor(Building building, ArrayList<Worker> creators,int progress){
		
		this.building = building;
		this.creators = creators;
		takenSpaces = new HashMap<Integer,int[]>();
		this.progress = progress;
	}
	
	public Building getBuilding(){
		
		return building;
	}
	
	public int[] getFreeSpace(int unitX, int unitY,HashMap<Integer,int[]> taken){
		
		HashMap<Integer,int[]> combine = new HashMap<Integer, int[]>();
		if(taken.size() > 0){
			combine.putAll(taken);
		}
		if(takenSpaces.size() > 0){
			combine.putAll(takenSpaces);
		}
		return building.getFreeSpace(unitX, unitY,combine);
	}
	
	public void addWorker(Worker creator){
		
		creators.add(creator);
		takenSpaces.put(Point.GetUniqueNo(creator.getTarget()), creator.getTarget());
	}
//	b + " " + buildings.getBuildingType(b) + " " 
//	+ buildings.getBuildingX(b) + " " +
//	buildings.getBuildingY(b) + "\n";
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		
		return building.getBuildingNo() + " " + building.getType() 
				+ " " + ((float)building.getX()) + " " + ((float)building.getY()) 
				+ " " + building.getPlayer() + "\n";
		
	}
	
	public boolean progress(){
		
		double tempProgress = (double) progress;
		
		for(int i = 0; i < creators.size(); i++){
			
			if(!creators.get(i).getGoingToBuild()){
				
				takenSpaces.remove(creators.get(i).getUnitNo());
				creators.remove(i);
				i--;
				continue;
				
			}
			
			if(creators.get(i).isCreating() == building.getBuildingNo()
					&& creators.get(i).nearBuilding(building) 
					&& !creators.get(i).getMoving()){
				
				creators.get(i).attack(building.getX(), building.getY());
				tempProgress += (double) 1/ (double) (i+1);
				
			}else{
				creators.get(i).stopAttack(false);
			}
		}
		
		progress = (int) tempProgress;

		//*10
		if(progress < (building.getBuildTime()*10)){
			
			return true;
		
		}else{
			
			//System.out.println("FINISH BUILD BuildingSite");
			for(int c = 0; c < creators.size(); c++){
				
				creators.get(c).stopAttack(false);
				creators.get(c).stopBuild();
				creators.get(c).cancelMovement();
			}
			
			return false;
		}
		
	}

	public int getProgress() {
		// TODO Auto-generated method stub
		return progress;
	}

	public String getCreatorStates() {
		// TODO Auto-generated method stub
		String line = "";
		
		for(int c = 0; c < creators.size(); c++){
			
			line += creators.get(c).getUnitNo() + " ";
		}
		
//		if(line.length() > 0){
//			
//			line = line.substring(0,line.length()-1);
//		}
		
		return line;
	}

	public void setPos(float x, float y) {
		// TODO Auto-generated method stub
		this.getBuilding().setPos((int)x, (int)y);
	}

	public ArrayList<Worker> getCreators() {
		// TODO Auto-generated method stub
		return creators;
	}

	public void removeWorker(int unitNo) {
		// TODO Auto-generated method stub
		for(int u = 0; u < creators.size(); u++){
			
			if(creators.get(u).getUnitNo() == unitNo){
				
				creators.get(u).stopBuild();
				creators.remove(u);
				break;
				
			}
		}
	}

}
