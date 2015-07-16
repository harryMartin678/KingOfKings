package GameGraphics;

import java.util.ArrayList;

public class UnitList {
	
	private ArrayList<Unit> units;
	private boolean used;
	
	public UnitList(){
		
		units = new ArrayList<Unit>();
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
	
	public Unit get(int index){
		
		return units.get(index);
	}
	
	public void clear(){
		
		units.clear();
	}
	
	public void remove(int index){
		
		units.remove(index);
	}
	
	public int size(){
		
		return units.size();
	}
	
	public void add(Unit unit){
		
		units.add(unit);
	}
	
	public void end(){
		
		used = false;
	}

}
