package GameGraphics;

import java.util.ArrayList;

public class BuildingList {
	
	private ArrayList<Building> buildings;
	private boolean used;
	
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

}
