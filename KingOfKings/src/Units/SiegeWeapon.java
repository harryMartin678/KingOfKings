package Units;

public class SiegeWeapon extends Unit {
	
	
	@Override
	public int turnsPerAHit() {
		// TODO Auto-generated method stub
		return super.turnsPerAHit() + 2;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":SiegeWeapon";
	}
	
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return super.getMaxHealth() + 50;
	}
	
	public int getBiasAttack(String unitType){
		
		int bias = 0;
		
		if(unitType.equals("Building")){
			
			bias = 100;
		}
		
		return this.getAttack() + bias;
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 100;
	}
	
	@Override
	public int getDefence() {
		// TODO Auto-generated method stub
		return super.getDefence() + 1;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() + 1;
	}

}
