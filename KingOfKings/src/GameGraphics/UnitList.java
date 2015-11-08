package GameGraphics;

import java.util.ArrayList;

public class UnitList implements IUnitList {
	
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
		
		used = false;
	}

	@Override
	public int getUnitListSize() {
		// TODO Auto-generated method stub
		return size();
	}

	@Override
	public boolean checkInUnit(int x, int y,int unitNo) {
		// TODO Auto-generated method stub
		return units.get(unitNo).inUnit(x,y);
	}

}
