package Units;

public class Worker extends Unit {
	
	private int buildingNo;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return super.getName() + ":Worker";
	}
	
	@Override
	public int getMaxHealth() {
		// TODO Auto-generated method stub
		return super.getMaxHealth() + 25;
	}
	
	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return super.getAttack() + 1;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return super.getSpeed() + 5;
	}
	
	@Override
	public int getProductivity() {
		// TODO Auto-generated method stub
		return super.getProductivity() + 5;
	}
	
	public int isCreating(){
		
		return buildingNo;
	}
	
	public void build(int buildingNo){
		
		this.buildingNo = buildingNo;
	}

}
