package Units;

public class Spearman extends Swordsman {
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":Swordsman";
	}
	
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return super.getMaxHealth() + 75;
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 6;
	}
	
	@Override
	public int getBiasAttack(String unitType) {
		// TODO Auto-generated method stub
		int bias = 0;
		if(unitType.equals("chariot")){
			
			bias++;
		}
		
		return super.getBiasAttack(unitType)+bias;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() - 3;
	}
	
	@Override
	public int getDefence() {
		// TODO Auto-generated method stub
		return super.getDefence() + 2;
	}

}
