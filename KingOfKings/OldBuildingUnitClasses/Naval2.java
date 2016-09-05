package Units;

import java.util.ArrayList;

public class Naval extends Unit {
	
	private ArrayList<Integer> unitsInBoat;
	
	public Naval(){
		
		super();
		
		unitsInBoat = new ArrayList<Integer>();
	}
	
	public int boatSize(){
		
		return 1;
	}
	
	public void addUnit(int unitNo){
		
		if(unitsInBoat.size() < this.boatSize()){
			
			unitsInBoat.add(new Integer(unitNo));
		}
	}
	
	public void removeUnit(int unitNo){
		
		for(int b = 0; b < unitsInBoat.size(); b++){
			
			if(unitsInBoat.get(b).intValue() == unitNo){
				
				unitsInBoat.remove(b);
			}
		}
	}
	
	public int getNoOfUnitsInBoat(){
		
		return unitsInBoat.size();
	}
	
	public int getUnitInBoat(int index){
		
		return unitsInBoat.get(index);
	}
	
	public boolean inBoat(int unitNo){
		
		for(int b = 0; b < unitsInBoat.size(); b++){
			
			if(unitsInBoat.get(b).intValue() == unitNo){
				
				return true;
			}
		}
		
		return false;
	}

	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return super.getMaxHealth() + 200;
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 5;
	}
	
	@Override
	public int getDefence() {
		// TODO Auto-generated method stub
		return super.getDefence() + 5;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":Naval";
	}
	
	@Override
	public int getRange() {
		// TODO Auto-generated method stub
		return super.getRange() + 5;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() + 5;
	}
}
