package Units;

public class Cavalry extends Unit {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName()+":Cavalry";
	}
	
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return super.getMaxHealth() + 150;
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 5;
	}
	
	public int getBiasAttack(String unitType){
		
		int bias = 0;
		
		if(unitType.equals("Swordsmen")){
			
			bias = 2;
		
		}else if(unitType.equals("Archer")){
			
			bias = 3;
		
		}else if(unitType.equals("Worker")){
			
			bias = 4;
		}
		
		return this.getAttack() + bias;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() + 10;
	}
	
	@Override
	public int getDefence() {
		// TODO Auto-generated method stub
		return super.getDefence()+2;
	}

}
